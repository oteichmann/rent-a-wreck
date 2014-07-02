'use strict';

/* Services */
var serviceBaseURL = '/rent-a-wreck-rest';
var rawServices = angular.module('rawServices', ['ngResource']);

rawServices.factory('aircraftService', [ '$resource', function($resource) {
	return $resource(serviceBaseURL + '/aircraft/:uuid', {uuid:'@uuid'}, {
		update : { method:'PUT' },
		queryCount : {
			method : 'GET',
			url : serviceBaseURL + '/aircraft/count',
			isArray : false
		},
		getAircraftStatusList : {
			method : 'GET',
			url : serviceBaseURL + '/aircraft/status-list',
			isArray : true
		}
	});
} ]);

rawServices.factory('aircraftTypeService', [function() {
	var aircraftTypes = [{"name" : "BOEING"}, {"name" : "AIRBUS"}, {"name" : "CESSNA"}, {"name" : "PIPER"}, {"name" : "SPITFIRE"}];
	
	var aircraftTypeService = {
		query: function () {
			return aircraftTypes;
		}
	};
	
	return aircraftTypeService;
} ]);

rawServices.factory('charterService', [ '$resource', function($resource) {
	return $resource(serviceBaseURL + '/charter/:uuid', {uuid:'@uuid'}, {
		update : { method:'PUT' },
		queryCount : {
			method : 'GET',
			url : serviceBaseURL + '/charter/count',
			isArray : false
		},
		getAircraftCharters : {
			method : 'GET',
			url : serviceBaseURL + '/charter/aircraft/:aircraft_uuid',
			params : {aircraft_uuid:'@aircraft_uuid'},
			isArray : true
		},
		updatePilotCharterStatus : {
			method : 'PUT',
			url : serviceBaseURL + '/charter/:uuid/state?newCharterStatus=:newStatus',
			params : { uuid:'@uuid', newStatus:'@newStatus' },
			isArray : false
		},
		createCharter : {
			method : 'POST',
			url : serviceBaseURL + '/charter/create/:uuid',
			params : {uuid:'@uuid'},
			isArray : false
		}
	});
} ]);

rawServices.factory('licenseService', [ '$resource', function($resource) {
	return $resource(serviceBaseURL + '/license/:uuid', {uuid:'@uuid'}, {
		update : { method:'PUT' },
		queryCount : {
			method : 'GET',
			url : serviceBaseURL + '/license/count',
			isArray : false
		}
	});
} ]);

rawServices.factory('pilotService', [ '$resource', function($resource) {
	return $resource(serviceBaseURL + '/pilot/:uuid', {uuid:'@uuid'}, {
		update : { method:'PUT' },
		queryCount : {
			method : 'GET',
			url : serviceBaseURL + '/pilot/count',
			isArray : false
		},
		getByUserUuid : {
			method : 'GET',
			url : serviceBaseURL + "/pilot/user/:user_uuid",
			params : {user_uuid:'@user_uuid'}
		}
	});
} ]);

rawServices.factory('reservationService', [ '$resource', function($resource) {
	return $resource(serviceBaseURL + '/reservation', {}, {

	});
} ]);

rawServices.factory('roleService', [ '$resource', function($resource) {
	return $resource(serviceBaseURL + '/role/:uuid', {uuid:'@uuid'}, {
		update : { method:'PUT' },
		queryCount : {
			method : 'GET',
			url : serviceBaseURL + '/role/count',
			isArray : false
		}
	});
} ]);

rawServices.factory('userService', [ '$resource', function($resource) {
	return $resource(serviceBaseURL + '/user/:uuid', {uuid:'@uuid'}, {
		update : { method:'PUT' },
		queryCount : {
			method : 'GET',
			url : serviceBaseURL + '/user/count',
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
			method : 'POST',
			url : serviceBaseURL + '/util/generate-uuid',
			isArray : false
		}
	});
} ]);