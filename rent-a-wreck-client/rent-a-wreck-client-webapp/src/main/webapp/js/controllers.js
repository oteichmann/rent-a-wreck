'use strict';

/* Controllers */

var rawControllers = angular.module('rawControllers', []);

rawControllers.controller('AircraftListCtrl', [ '$scope', 'Aircraft', function($scope, Aircraft) {
	$scope.aircrafts = Aircraft.query();
}]);
