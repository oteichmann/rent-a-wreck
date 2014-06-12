'use strict';

rawControllers.controller('navigationCtrl', function($rootScope, $scope, $cookies, $http, $location) {
	
	$scope.loggedIn = false;
	
	/**
	 * Validate authentication cookie on route change
	 */
	$rootScope.$on('$routeChangeSuccess', function ($angularEvent, $current, $previous) {
		var token = $cookies["XSRF-TOKEN"];
		if (token) {
			$http.post("/rent-a-wreck-rest/auth/validate", JSON.stringify({
				token : token
			})).success(function(data, status, headers, config) {
				// TODO: return already logged in message, get user data, ...
				//return $http.get("/users/" + response.data.userId);
				$scope.loggedIn = true;
			}).error(function(data, status, headers, config) {
				// Invalidate all existing cookies if token was not valid
				$scope.loggedIn = false;
				$cookies["XSRF-TOKEN"] = undefined;
			});
		} else {
			$scope.loggedIn = false;
		}
    });

	/**
	 * Invalidate the token on the server.
	 */
	$scope.logout = function() {
		var token = $cookies["XSRF-TOKEN"];
		$http.post("/rent-a-wreck-rest/auth/logout", JSON.stringify({
			token : token
		})).success(function() {
			$scope.loggedIn = false;
			$cookies["XSRF-TOKEN"] = undefined;
		});
	};
	
    $scope.isActive = function(route) {
        return route === $location.path();
    };

});
