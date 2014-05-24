'use strict';

/* Controllers */

var rawControllers = angular.module('rawControllers', []);

//rawControllers.controller('aircraftListCtrl', [ '$scope', 'aircraftService', function($scope, aircraftService) {
//	$scope.aircrafts = aircraftService.findAll();
//}]);

rawControllers.controller('aircraftListCtrl', [ '$scope', 'aircraftService2', function($scope, aircraftService2) {
	 aircraftService2.findAll(function(data){
		$scope.aircrafts = data;
	    });
}]);

rawControllers.controller('loginCtrl', [ '$scope', '$cookies', '$http', '$q', '$timeout', function($scope, $cookies, $http, $q, $timeout) {
	
	// This is only for demo purposes
    $scope.credentials = {
      username: "guest",
      password: "guest"
    };

    // Check token cookie and try to authenticate
    // Otherwise the user has to log in
    var token = $cookies["XSRF-TOKEN"];
    if (token) {
      $http.post("/login/validate", token).
       success(function(data, status, headers, config) {
      // this callback will be called asynchronously
      // when the response is available
    }).
    error(function(data, status, headers, config) {
      // called asynchronously if an error occurs
      // or server returns response with an error status.
        token = undefined;
        $cookies["XSRF-TOKEN"] = undefined;
        return $q.reject("Token invalid");
    });
//      .success(
//        function(response) {
//          // Token valid, fetch user data
//          //return $http.get("/users/" + response.data.userId);
//        },
//        function(response) {
//          token = undefined;
//          $cookies["XSRF-TOKEN"] = undefined;
//          return $q.reject("Token invalid");
//        }
//      );
//      .then(
//        function(response) {
//          $scope.user = response.data;
//        }, function(response) {
//          // Token invalid or fetching the user failed
//        }
//      );
    }

    /**
     * Login using the given credentials as (email,password).
     * The server adds the XSRF-TOKEN cookie which is then picked up by Play.
     */
    $scope.login = function(credentials) {
      $http.post("/rent-a-wreck-rest/login", $scope.credentials).
      success(function(data, status, headers, config) {
          // this callback will be called asynchronously
          // when the response is available
    	  token = data.token;
      }).
      error(function(data, status, headers, config) {
        $scope.error = data.err;
        // return 'empty' promise so the right `then` function is called
        return $q.reject("Login failed");
      });
//      .then(
//        function(response) { // success
//          token = response.data.token;
//          //var userId = response.data.userId;
//          //return $http.get("/users/" + userId); // return another promise to chain `then`
//        }, function(response) { // error
//          $scope.error = response.data.err;
//          // return 'empty' promise so the right `then` function is called
//          return $q.reject("Login failed");
//        }
//      );
//      .then(
//        function(response) {
//          $scope.user = response.data;
//        },
//        function(response) {
//          console.log(response);
//        }
//      );
    };

    /**
     * Invalidate the token on the server.
     */
    $scope.logout = function() {
      $http.post("/logout").then(function() {
        $scope.user = undefined;
      });
    };

    /**
     * Pings the server. When the request is not authorized, the $http interceptor should
     * log out the current user.
     */
    $scope.validate = function() {
      $http.post("/login/validate", token).then(function() {
        $scope.ok = true;
        $timeout(function() {$scope.ok = false;}, 3000);
      });
    };

    /** Subscribe to the logout event emitted by the $http interceptor. */
    $scope.$on("InvalidToken", function() {
      console.log("InvalidToken!");
      $scope.user = undefined;
    });
	
}]);


