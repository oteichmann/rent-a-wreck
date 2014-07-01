'use strict';

rawControllers.controller('chartersCtrl', 
	[ '$scope', '$log', 'aircraftService', 'charterService', 'pilotService', 'utilService',
	function($scope, $log, aircraftService, charterService, pilotService, utilService) {

		$scope.model = {};
		$scope.model.aircraftStatusList = [];
		$scope.model.aircarftCharters = [];

		aircraftService.getAircraftStatusList(function(response) {
			$scope.model.aircraftStatusList = response;
		});

		
		$scope.showAircraftCharters = function (aircraftCharterStatus) {
			charterService.getAircraftCharters({aircraft_uuid: aircraftCharterStatus.aircraft.uuid}, function(response) {
				$scope.model.aircarftCharters = response;
			});
		}
	} 
]);