'use strict';

/* Controllers */

var rawControllers = angular.module('rawControllers', []);

// rawControllers.controller('aircraftListCtrl', [ '$scope', 'aircraftService',
// function($scope, aircraftService) {
// $scope.aircrafts = aircraftService.findAll();
// }]);

rawControllers.controller('aircraftListCtrl', [ '$scope', 'aircraftService2',
		function($scope, aircraftService2) {
			aircraftService2.findAll(function(data) {
				$scope.aircrafts = data;
			});
		} ]);

rawControllers.controller('loginCtrl', ['$rootScope',
		'$scope',
		'$cookies',
		'$http',
		'$q',
		'$timeout',
		'$location',
		function($rootScope, $scope, $cookies, $http, $q, $timeout, $location) {

			/**
			 * Check token cookie and try to authenticate
			 * Otherwise the user has to log in
			 */
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
					token = undefined;
					$cookies["XSRF-TOKEN"] = undefined;
					return $q.reject("Token invalid");
				});
			}

			/**
			 * Login using the given credentials as (email,password). The server
			 * adds the XSRF-TOKEN cookie which is then picked up by Play.
			 */
			$scope.login = function(credentials) {
				$http.post("/rent-a-wreck-rest/auth/login", $scope.credentials)
						.success(function(data, status, headers, config) {
							// redirect to home page after successful login
							$rootScope.loggedIn = true;
							$location.path( "/" );
						}).error(function(data, status, headers, config) {
							$rootScope.loggedIn = false;
							$scope.error = data.err;
							return $q.reject("Login failed");
						});
			};

			/**
			 * Invalidate the token on the server.
			 */
			$scope.logout = function() {
				$http.post("/rent-a-wreck-rest/auth/logout", JSON.stringify({
					token : token
				})).then(function() {
					$rootScope.loggedIn = false;
					token = undefined;
					$cookies["XSRF-TOKEN"] = undefined;
					return $q.reject("Token invalid");
				});
			};
			
		    $scope.isActive = function(route) {
		        return route === $location.path();
		    }

		} ]);
