'use strict';

rawControllers.controller('aircraftCtrl', function($scope, aircraftService, utilService) {

	$scope.aircraftTypes = [{"name" : "BOEING"}, {"name" : "AIRBUS"}, {"name" : "CESSNA"}, {"name" : "PIPER"}, {"name" : "SPITFIRE"}];
	
	resetView();
	
	function resetView() {
		resetForm();
		updateAircraftList();
	};
	
	function resetForm(){
		$scope.editAircraft = false;
		$scope.aircraft = {};
	};
	
	function updateAircraftList() {
		aircraftService.query(function(value, responseHeaders) {
			$scope.aircrafts = value;
		}, function(httpHeaders) {
			alert("Failed to load aircraft list");
		});
	};

	$scope.editAircraft = function(aircraft) {
		$scope.editAircraft = true;
		$scope.aircraft = aircraft;
	};

	$scope.editAircraftCancel = function() {
		resetView();
	};
	
	$scope.aircraftFormSubmit = function() {
		if($scope.editAircraft) {
			updateAircraft();
		} else {
			addAircraft();
		} 
			
	};

	function updateAircraft() {
		$scope.aircraft.$save();
		resetView();
	};

	function addAircraft() {
		var newAircraft = $scope.aircraft;

		utilService.generateUuid(function(f) {
			newAircraft.uuid = f.value;
			aircraftService.save(newAircraft, function() {
				updateAircraftList();
			});
			resetForm();
		});
	};

	$scope.deleteAircraft = function(aircraft) {
		aircraft.$delete({}, function(value, responseHeaders) {
			updateAircraftList();
		}, function(httpResponse) {
			alert("Could not delete aircraft, because it is still in use.");
		});
	};
});