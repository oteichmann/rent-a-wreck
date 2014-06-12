'use strict';

/* Controller Module definition */
var rawControllers = angular.module('rawControllers', []);


/* App Module definition */
var rawApp = angular.module('rawApp', [ 'ngRoute', 'ngCookies', 'rawControllers', 'rawServices' ]);

rawApp.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl : 'views/welcome.html',
	}).when('/login', {
		templateUrl : 'views/login.html',
		controller : 'loginCtrl'
	}).when('/aircrafts', {
		templateUrl : 'views/aircrafts.html',
		controller : 'aircraftCtrl'
	}).otherwise({
		redirectTo : '/'
	});
} ]);


rawApp.factory('interceptor', ["$rootScope", "$q", "$timeout", function($rootScope, $q, $timeout) {
    return function(promise) {
      return promise.then(
        function(response) {
          return response;
        },
        function(response) {
          if (response.status == 401) {
            $rootScope.$broadcast("InvalidToken");
            $rootScope.sessionExpired = true;
            $timeout(function() {$rootScope.sessionExpired = false;}, 5000);
          } else if (response.status == 403) {
            $rootScope.$broadcast("InsufficientPrivileges");
          } else {
            // Here you could handle other status codes, e.g. retry a 404
          }
          return $q.reject(response);
        }
      );
    };
  }]);

//rawApp.factory('sessionInjector', ['SessionService', function(SessionService) {
//    var sessionInjector = {
//        request: function(config) {
//            if (!SessionService.isAnonymus) {
//                config.headers['x-session-token'] = SessionService.token;
//            }
//            return config;
//        }
//    };
//    return sessionInjector;
//}]);

rawApp.config(["$httpProvider", function($httpProvider) {
//	$httpProvider.responseInterceptors.push(interceptor);
//	$httpProvider.interceptors.push('sessionInjector');
}]);