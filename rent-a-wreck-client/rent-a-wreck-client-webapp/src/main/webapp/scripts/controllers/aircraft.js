'use strict';

rawControllers.controller('aircraftCtrl', function($scope, aircraftService) {
	aircraftService.query(function(value, responseHeaders) {
		$scope.aircrafts = value;
	}, function(httpHeaders) {
		alert("Failed to load aircraft list");
	});
});