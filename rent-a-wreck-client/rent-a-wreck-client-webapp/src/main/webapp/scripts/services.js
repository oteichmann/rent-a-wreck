'use strict';

/* Services */
var serviceBaseURL = '/rent-a-wreck-rest';
var rawServices = angular.module('rawServices', ['ngResource']);

rawServices.factory('aircraftService', [ '$resource', function($resource) {
	return $resource(serviceBaseURL + '/aircraft/:uuid', {uuid:'@uuid'}, {
		update : { method:'PUT' },
		queryCount : {
			url : serviceBaseURL + '/aircraft/count',
			method : 'GET',
			isArray : false
		}
	});
} ]);

rawServices.factory('pilotService', [ '$resource', function($resource) {
	return $resource(serviceBaseURL + '/pilot/:uuid', {uuid:'@uuid'}, {
		update : { method:'PUT' },
		queryCount : {
			url : serviceBaseURL + '/pilot/count',
			method : 'GET',
			isArray : false
		}
	});
} ]);

rawServices.factory('roleService', [ '$resource', function($resource) {
	return $resource(serviceBaseURL + '/role/:uuid', {uuid:'@uuid'}, {
		update : { method:'PUT' },
		queryCount : {
			url : serviceBaseURL + '/role/count',
			method : 'GET',
			isArray : false
		}
	});
} ]);

rawServices.factory('userService', [ '$resource', function($resource) {
	return $resource(serviceBaseURL + '/user/:uuid', {uuid:'@uuid'}, {
		update : { method:'PUT' },
		queryCount : {
			url : serviceBaseURL + '/user/count',
			method : 'GET',
			isArray : false
		}
	});
} ]);

rawServices.factory('UserSession', [ function() {
	var session = {
		loggedIn: false,
		user: null
	};
	return session;
}]);

rawServices.factory('utilService', [ '$resource', function($resource) {
	return $resource(serviceBaseURL + '/util', {}, {
		generateUuid : {
			url : serviceBaseURL + '/util/generate-uuid',
			method : 'POST',
			isArray : false
		}
	});
} ]);