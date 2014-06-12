'use strict';

/* Services */
var serviceBaseURL = '/rent-a-wreck-rest';
var rawServices = angular.module('rawServices', ['ngResource']);

rawServices.factory('aircraftService', [ '$resource', function($resource) {
	return $resource(serviceBaseURL + '/aircrafts/:uuid', {uuid:'@uuid'}, {
		queryCount : {
			url : serviceBaseURL + '/aircrafts/count',
			method : 'GET',
			isArray : false
		}
	});
} ]);

rawServices.factory('utilService', [ '$resource', function($resource) {
	return $resource(serviceBaseURL + '/util', {}, {
		generateUuid : {
			url : serviceBaseURL + '/util/generate-uuid',
			method : 'POST',
			isArray : false
		}
	});
} ]);