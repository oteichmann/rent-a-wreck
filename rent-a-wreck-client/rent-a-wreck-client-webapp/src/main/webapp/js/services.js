'use strict';

/* Services */
var hostURL = 'https://localhost:8443';
var serviceBaseURL = hostURL + '/rent-a-wreck-rest';
var rawServices = angular.module('rawServices', ['ngResource']);

rawServices.factory('aircraftService', [
		'$resource',
		function($resource) {
			return $resource(
					serviceBaseURL + '/aircrafts', {}, {
						findAll : {
							method : 'GET',
							isArray : true
						}
					});
		} ]);

rawServices.factory('aircraftService2', [
                                		'$http',
                                		function($http) {
                                			return{
                                			      findAll: function(callback){
                                			          $http.get('/rent-a-wreck-rest/aircrafts')
                                			          .success(function(data) {
	                                			          // prepare data here
	                                			          callback(data);
                                			          }.error(function(status){
                                			        	var httpStatus = status;
                                			          }
                                			        )
                                			        );
                                			      }
                                			    };
                                		} ]);

rawServices.factory('loginService', [
		'$resource',
		function($resource) {
			return $resource(
				 serviceBaseURL + '/login', {}, {
						login : {
							method : 'POST',
							isArray : false
						}
					});
		} ]);