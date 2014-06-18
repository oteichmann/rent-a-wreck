'use strict';

rawControllers.controller('userCtrl', function($scope, userService, utilService) {
	
	resetView();
	
	function resetView() {
		resetForm();
		updateUserList();
	};
	
	function resetForm(){
		$scope.newUser = true;
		$scope.user = {};
	};
	
	function updateUserList() {
		userService.query(function(value, responseHeaders) {
			$scope.users = value;
		}, function(httpHeaders) {
			alert("Failed to load user list");
		});
	};

	$scope.editUser = function(user) {
		$scope.newUser = false;
		$scope.user = user;
	};

	$scope.editUserCancel = function() {
		resetView();
	};
	
	$scope.userFormSubmit = function() {
		if($scope.newUser) {
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
			alert("Could not delete user, because it is still in use.");
		});
	};
});