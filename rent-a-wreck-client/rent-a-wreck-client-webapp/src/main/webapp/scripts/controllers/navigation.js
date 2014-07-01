'use strict';

rawControllers.controller('navigationCtrl', function($rootScope, $scope, $cookies, $http, $location, UserSession) {
	
	$scope.isLoggedIn = function () {
		return UserSession.loggedIn;
	};
	
	/**
	 * Validate authentication cookie on load - (Cookie Auto-Login ;-)
	 */
	var token = $cookies['XSRF-TOKEN'];
	if (token) {
		$http.post('/rent-a-wreck-rest/auth/validate', JSON.stringify({
			token : token
		})).success(function(data, status, headers, config) {
			UserSession.loggedIn = true;
			UserSession.user = data;
		}).error(function(data, status, headers, config) {
			// Invalidate session and cookies if token was not valid
			UserSession.loggedIn = false;
			UserSession.user = {};
			$cookies['XSRF-TOKEN'] = undefined;
		});
	}
	
	$rootScope.$on('$routeChangeSuccess', function ($angularEvent, $current, $previous) {
		// TODO: check if user is permissioned
	});
		
	/**
	 * Invalidate the token on the server.
	 */
	$scope.logout = function() {
		var token = $cookies['XSRF-TOKEN'];
		$http.post('/rent-a-wreck-rest/auth/logout', JSON.stringify({
			token : token
		}));
			
		UserSession.loggedIn = false;
		UserSession.user = {};
		$cookies['XSRF-TOKEN'] = undefined;
		$location.path('/');
	};
	
    $scope.isActive = function(route) {
        return route === $location.path();
    };

});
