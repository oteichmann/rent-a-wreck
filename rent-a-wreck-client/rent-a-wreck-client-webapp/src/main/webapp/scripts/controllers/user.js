'use strict';

rawControllers.controller('userCtrl', function($scope, pilotService, userService, utilService) {
	
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
});