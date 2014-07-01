'use strict';

rawControllers.controller('loginCtrl', function($rootScope, $scope, $http, $cookies, $location, UserSession) {

	/**
	 * Login using the given credentials as (email,password). The server
	 * adds the XSRF-TOKEN cookie which is then picked up by Play.
	 */
	$scope.login = function(credentials) {
		$http.post("/rent-a-wreck-rest/auth/login", $scope.credentials)
			.success(
				function(data, status, headers, config) {
					$rootScope.loggedIn = true;
					
					UserSession.loggedIn = true;
					UserSession.user = data;
					
					// redirect to home page after successful login
					if (window.location.protocol == "http:") {
						var restOfUrl = window.location.href.substr(5);
						restOfUrl = restOfUrl.substring(0, restOfUrl.indexOf('login'));
						window.location = "https:" + restOfUrl;
					} else {
						$location.path("/");
					}
				}
			).error(
				function(data, status, headers, config) {
					$scope.error = data.err;
					alert(status);
					
					UserSession.loggedIn = false;
					UserSession.username = null;
					// delete $cookies['XSRF-TOKEN'];
				}
			);
	};
});
