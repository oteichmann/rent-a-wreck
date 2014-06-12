'use strict';

rawControllers.controller('loginCtrl', function($scope, $http, $cookies, $location) {

	/**
	 * Login using the given credentials as (email,password). The server
	 * adds the XSRF-TOKEN cookie which is then picked up by Play.
	 */
	$scope.login = function(credentials) {
		$http.post("/rent-a-wreck-rest/auth/login", $scope.credentials)
			.success(
				function(data, status, headers, config) {
					// redirect to home page after successful login
					var headerValues = headers();
					$cookies["XSRF-TOKEN"] = headerValues["x-xsrf-token"];
					$location.path("/");
				}
			).error(
				function(data, status, headers, config) {
					$scope.error = data.err;
				}
			);
	};
});
