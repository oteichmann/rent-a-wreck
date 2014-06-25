'use strict';

rawControllers.controller('userCtrl', function($scope, $modal, $log, aircraftTypeService, licenseService, pilotService, userService, utilService) {
	
	resetView();
	
	function resetView() {
		resetForm();
		updateUserList();
	};
	
	function resetForm(){
		$scope.isNewUser = true;
		$scope.showEditForm = false;
		$scope.user = {};
		$scope.pilot = {};
	};
	
	function updateUserList() {
		userService.query(function(value, responseHeaders) {
			$scope.users = value;
		}, function(httpHeaders) {
			alert("Failed to load user list!");
		});
	};
	
	$scope.createNewUser = function() {
		$scope.showEditForm = true;
	};

	$scope.editUser = function(user) {
		$scope.isNewUser = false;
		$scope.user = user;
		$scope.pilot = pilotService.getByUserUuid({ "user_uuid" : user.uuid});
		$scope.showEditForm = true;
	};

	$scope.editUserCancel = function() {
		resetView();
	};
	
	$scope.userFormSubmit = function() {
		if($scope.isNewUser) {
			addUser();
		} else {
			updateUser();
		} 
	};

	function updateUser() {
		$scope.user.$update(function() {
			updateUserList();
		});
		resetForm();
	};

	function addUser() {
		var newUser = $scope.user;

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
			newPilot.user = $scope.user;
			pilotService.save(newPilot, function(value, responseHeaders) {
				$scope.pilot = value;
			});
		});
	};
	
	$scope.deletePilot = function() {
		$scope.pilot.$delete({}, function(value, responseHeaders) {
			$scope.pilot = {};
		}, function(httpResponse) {
			alert("Could not delete pilot!");
		});
	};

	$scope.addLicense = function() {
		var license = {};
		openModalLicenseEditor(license, function(license) {
			$scope.pilot.licenses.push(license);
			$scope.pilot.$update();
		});
	};

	$scope.editLicense = function(license) {
		licenseService.get({uuid:license.uuid}, function(licenseResource) {
	    	openModalLicenseEditor(licenseResource, function(license) {
	    		pilotService.get({uuid:$scope.pilot.uuid}, function(pilot) {
		    		$scope.pilot = pilot;
				});
	    	});
		});
	};

	$scope.deleteLicense = function(license) {
		licenseService.delete({ uuid : license.uuid}, function() {
			pilotService.get({uuid:$scope.pilot.uuid}, function(pilot) {
	    		$scope.pilot = pilot;
			});
		});
	};

	function openModalLicenseEditor(licenseParam,callback) {
 		var modalInstance = $modal.open({
			templateUrl: 'modalLicenseEditor.html',
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
			$scope.license.$update();
			$modalInstance.close();
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
			formatYear : 'yy',
			startingDay : 1,
			'show-button-bar' : false
		};

		$scope.initDate = new Date();
		$scope.format = 'mediumDate';
	};

});	
