

/* Controller Module definition */
var rawControllers = angular.module('rawControllers', []);


/* App Module definition */
var rawApp = angular.module('rawApp', [ 'ngRoute', 'ngCookies', 'ui.bootstrap', 'rawControllers', 'rawServices', 'rawDirectives.datePicker' ]);

rawApp.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/', {
		templateUrl : 'views/welcome.html',
	}).when('/login', {
		templateUrl : 'views/login.html',
		controller : 'loginCtrl'
	}).when('/charters', {
    templateUrl : 'views/charters.html',
    controller : 'chartersCtrl'
  }).when('/aircrafts', {
    templateUrl : 'views/aircrafts.html',
    controller : 'aircraftCtrl'
  }).when('/users', {
		templateUrl : 'views/users.html',
		controller : 'userCtrl'
	}).otherwise({
		redirectTo : '/'
	});
} ]);


rawApp.factory('httpInterceptor', ['$rootScope', '$q', '$cookies', '$location', 'UserSession',
    function($rootScope, $q, $cookies, $location, UserSession) {
    return function(promise) {
      return promise.then(
        function(response) {
          return response;
        },
        function(response) {
          if (response.status == 401) {
            $rootScope.$broadcast('InvalidToken');

            UserSession.loggedIn = false;
            UserSession.user = {};
//            $cookies['XSRF-TOKEN'] = undefined;
            $location.path('/login');

          } else if (response.status == 403) {
            $rootScope.$broadcast('InsufficientPrivileges');
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

rawApp.config(['$httpProvider', function($httpProvider) {
    $httpProvider.responseInterceptors.push('httpInterceptor');
//	$httpProvider.interceptors.push('sessionInjector');
}]);