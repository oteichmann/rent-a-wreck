'use strict';

rawControllers.controller('userCtrl', function($scope, $modal, $log, aircraftTypeService, licenseService, pilotService, userService, utilService) {
	
	var userTemplate = {
		uuid : '',
		username : '',
		firstName : '',
		lastName : '',
		email : '',
		password : ''
	}

	var pilotTemplate = {
		uuid : '',
		user : angular.copy(userTemplate),
		licenses : []
	}

	$scope.model = {
		users : [],
		isNewUser : true,
		showEditForm : false,
		user : {},
		pilot : {}
	}

	resetView();
	
	function resetView() {
		resetForm();
		updateUserList();
	};
	
	function resetForm(){
		$scope.model.isNewUser = true;
		$scope.model.showEditForm = false;
		$scope.model.user = angular.copy(userTemplate);
		$scope.model.pilot = angular.copy(pilotTemplate);
		if($scope.model.userForm) {
			$scope.model.userForm.$setPristine();	
		}
	};
	
	function updateUserList() {
		userService.query(function(value, responseHeaders) {
			$scope.model.users = value;
		}, function(httpHeaders) {
			alert("Failed to load user list!");
		});
	};
	
	$scope.createNewUser = function() {
		$scope.model.showEditForm = true;
	};

	$scope.editUser = function(user) {
		$scope.model.isNewUser = false;
		$scope.model.user = user;
		$scope.model.pilot = pilotService.getByUserUuid({ "user_uuid" : user.uuid});
		$scope.model.showEditForm = true;
	};

	$scope.editUserCancel = function() {
		resetView();
	};
	
	$scope.model.userFormSubmit = function() {
		if($scope.model.isNewUser) {
			addUser();
		} else {
			updateUser();
		} 
	};

	function updateUser() {
		$scope.model.user.$update(function() {
			updateUserList();
		});
		resetForm();
	};

	function addUser() {
		var newUser = $scope.model.user;

		utilService.generateUuid(function(f) {
			newUser.uuid = f.value;
			userService.save(newUser, function() {
				updateUserList();
			});
			resetForm();
		});
	};

	$scope.deleteUser = function(user) {
		user.$delete({}, function(value, responseHeaders) {
			updateUserList();
		}, function(httpResponse) {
			alert("Could not delete user!");
		});
	};
	
	$scope.createPilot = function() {
		var newPilot = {};
		
		utilService.generateUuid(function(f) {
			newPilot.uuid = f.value;
			newPilot.user = $scope.model.user;
			pilotService.save(newPilot, function(value, responseHeaders) {
				$scope.model.pilot = value;
			});
		});
	};
	
	$scope.deletePilot = function() {
		$scope.model.pilot.$delete({}, function(value, responseHeaders) {
			$scope.model.pilot = {};
		}, function(httpResponse) {
			alert("Could not delete pilot!");
		});
	};

	$scope.addLicense = function() {
		var license = {};
		openModalLicenseEditor(license, function(license) {
			$scope.model.pilot.licenses.push(license);
			$scope.model.pilot.$update();
		});
	};

	$scope.editLicense = function(license) {
		licenseService.get({uuid:license.uuid}, function(licenseResource) {
	    	openModalLicenseEditor(licenseResource, function(license) {
	    		pilotService.get({uuid:$scope.model.pilot.uuid}, function(pilot) {
		    		$scope.model.pilot = pilot;
				});
	    	});
		});
	};

	$scope.deleteLicense = function(license) {
		licenseService.delete({ uuid : license.uuid}, function() {
			pilotService.get({uuid:$scope.model.pilot.uuid}, function(pilot) {
	    		$scope.model.pilot = pilot;
			});
		});
	};

	function openModalLicenseEditor(licenseParam,callback) {
 		var modalInstance = $modal.open({
			templateUrl: 'views/modal/pilotLicenseEditor.html',
			controller: ModalLicenseEditorCtrl,
			resolve: {
		        license: function () {
		          return licenseParam;
		        }
      		}
    	});
		
		modalInstance.result.then(function (returnValue) {
			if(callback) {
				callback(returnValue);
			}
		});
	};

	var ModalLicenseEditorCtrl = function($scope, $modalInstance, license) {

		$scope.license = license;
		$scope.aircraftTypes = aircraftTypeService.query();

		$scope.licenseFormSubmit = function() {

			if($scope.license.uuid) {
				updateLicense();
			} else {
				createLicense();
			} 
		};

		function updateLicense() {
			$scope.license.$update(function() {
				$modalInstance.close();
			});
		};

		function createLicense() {
			utilService.generateUuid(function(f) {
				license.uuid = f.value;
				licenseService.save($scope.license, function(value, responseHeaders) {
					$modalInstance.close(value);
				}, function(httpResponse) {
					alert("Could not save license!");
					$modalInstance.close();
				});
			});
		};

		$scope.editLicenseCancel = function() {
			$modalInstance.dismiss('cancel');
		};

		$scope.today = function() {
			$scope.dt = new Date();
		};
		$scope.today();

		$scope.clear = function() {
			$scope.dt = null;
		};

		// Disable weekend selection
		$scope.disabled = function(date, mode) {
			return (mode === 'day' && (date.getDay() === 0 || date.getDay() === 6));
		};

		$scope.minDate = new Date();

		$scope.open = function($event) {
			$event.preventDefault();
			$event.stopPropagation();

			$scope.opened = true;
		};

		$scope.dateOptions = {
			formatYear : 'yyyy',
			startingDay : 1,
			'show-button-bar' : false
		};

		$scope.initDate = new Date();
		$scope.format = 'yyyy-MM-dd';
	};

});	
