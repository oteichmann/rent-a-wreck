'use strict';

rawControllers.controller('navigationCtrl', function($rootScope, $scope, $cookies, $http, $location) {
	
	$rootScope.loggedIn = false;
	
	/**
	 * Validate authentication cookie on load
	 */
//	$rootScope.$on('$routeChangeSuccess', function ($angularEvent, $current, $previous) {
		var token = $cookies["XSRF-TOKEN"];
		if (token) {
			$http.post("/rent-a-wreck-rest/auth/validate", JSON.stringify({
				token : token
			})).success(function(data, status, headers, config) {
				// TODO: return already logged in message, get user data, ...
				//return $http.get("/users/" + response.data.userId);
				$rootScope.loggedIn = true;
			}).error(function(data, status, headers, config) {
				// Invalidate all existing cookies if token was not valid
				$rootScope.loggedIn = false;
//				$cookies["XSRF-TOKEN"] = undefined;
			});
		} else {
			$scope.loggedIn = false;
		}
//    });

		$rootScope.$on('$routeChangeSuccess', function ($angularEvent, $current, $previous) {
			// TODO: check if user is permissioned
		});
		
	/**
	 * Invalidate the token on the server.
	 */
	$scope.logout = function() {
		var token = $cookies["XSRF-TOKEN"];
		$http.post("/rent-a-wreck-rest/auth/logout", JSON.stringify({
			token : token
		})).success(function() {
			$rootScope.loggedIn = false;
//			$cookies["XSRF-TOKEN"] = undefined;
		});
	};
	
    $scope.isActive = function(route) {
        return route === $location.path();
    };

});
