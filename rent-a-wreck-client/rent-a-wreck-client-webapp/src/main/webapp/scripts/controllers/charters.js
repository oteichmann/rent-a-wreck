'use strict';

rawControllers.controller('chartersCtrl', 
	[ '$scope', '$log', '$modal', '$timeout', 'aircraftService', 'charterService', 'pilotService', 'utilService', 'UserSession',
	function($scope, $log, $modal, $timeout, aircraftService, charterService, pilotService, utilService, UserSession) {

		$scope.user = UserSession;
		$scope.model = {};
		$scope.model.aircraftStatusList = [];
		$scope.model.aircarftCharters = [];
		$scope.model.selectedAircraftStatus = {};

		aircraftService.getAircraftStatusList(function(data, httpResponse) {
			$scope.model.aircraftStatusList = data;
		});

		
		$scope.showAircraftCharters = function (aircraftCharterStatus) {
			$scope.model.selectedAircraftStatus = aircraftCharterStatus;
			charterService.getAircraftCharters({aircraft_uuid: aircraftCharterStatus.aircraft.uuid}, function(data, httpResponse) {
				$scope.model.aircarftCharters = data;
			});
		};

		$scope.createAircraftCharter = function (aircraftCharterStatus) {
			openAddCharterEditor(aircraftCharterStatus.aircraft);
		}


		function openAddCharterEditor(aircraft,callback) {
	 		var modalInstance = $modal.open({
				templateUrl: 'views/modal/charterEditor.html',
				controller: AddCharterEditorCtrl,
				resolve: {
			        aircraft: function () {
			          return aircraft;
			        }
	      		}
	    	});
			
			modalInstance.result.then(function (returnValue) {

				if(returnValue) {
					aircraftService.getAircraftStatusList(function(data, httpResponse) {
						$scope.model.aircraftStatusList = data;
					});
					charterService.getAircraftCharters({aircraft_uuid: $scope.model.selectedAircraftStatus.aircraft.uuid}, function(data, httpResponse) {
						$scope.model.aircarftCharters = data;
					});

					if(callback) {
						callback(returnValue);
					}
				}
			});
		};

		// $scope.$on('$viewContentLoaded', function () {
  //           $timeout(function () { // You might need this timeout to be sure its run after DOM render.
  //              $('li.disabled > a').click(function() { return false; });
  //           }, 0, false);
  //       });

		$scope.updateCharterStatus = function(charter, newStatus) {

			// only update if charter is in correct state.
			if ((newStatus === 'LENT' && charter.charterStatus === 'RESERVED') || 
				(newStatus === 'RETURNED' && charter.charterStatus === 'LENT')  || 
				(newStatus === 'CANCELED' && charter.charterStatus === 'RESERVED')) {
	
				$log.debug('Set new status for charter ' + charter.uuid + ' to ' + newStatus);

				charterService.updatePilotCharterStatus({uuid:charter.uuid, newStatus:newStatus}, function() {
					aircraftService.getAircraftStatusList(function(data, httpResponse) {
						$scope.model.aircraftStatusList = data;
					});
					charterService.getAircraftCharters({aircraft_uuid: charter.aircraft.uuid}, function(data, httpResponse) {
						$scope.model.aircarftCharters = data;
					});
				});
			};
		}

		$scope.updateCharterDates = function(charter) {
			if (charter.charterStatus === 'RESERVED') {
				$log.debug('Update dates of charter ' + charter.uuid);

				openUpdateCharterEditor(charter);
			}
		}


		function openUpdateCharterEditor(charter,callback) {
	 		var modalInstance = $modal.open({
				templateUrl: 'views/modal/charterEditor.html',
				controller: UpdateCharterEditorCtrl,
				resolve: {
			        charter: function () {
			          return charter;
			        }
	      		}
	    	});
			
			modalInstance.result.then(function (returnValue) {
				
				if(returnValue) {
					aircraftService.getAircraftStatusList(function(data, httpResponse) {
						$scope.model.aircraftStatusList = data;
					});
					charterService.getAircraftCharters({aircraft_uuid: charter.aircraft.uuid}, function(data, httpResponse) {
						$scope.model.aircarftCharters = data;
					});
				}

				if(callback) {
					callback(returnValue);
				}
			});
		};


		var AddCharterEditorCtrl = function($scope, $modalInstance, aircraft) {

			$scope.model = {};
			$scope.model.charter = createCharter();

			function createCharter () {
				var charter = {
					uuid : '',
					aircraft : aircraft,
					pilot : '',
					charterStart : new Date(),
					charterEnd : new Date(),
					charterStatus : 'RESERVED'
				};

				utilService.generateUuid(function(data, httpResponse) {
					charter.uuid = data.value;
				});
				pilotService.getByUserUuid({user_uuid:UserSession.user.uuid},function(data, httpResponse) {
					charter.pilot = data;
				});

				return charter;
			}
		

			$scope.charterFormSubmit = function() {

				charterService.createCharter($scope.model.charter, function(data, httpResponse) {
					$modalInstance.close(data);
				}, function(httpResponse) {
					alert("Could not save charter! " + httpResponse.data);
				});
			};

			$scope.charterFormCancel = function() {
				$modalInstance.dismiss('cancel');
			};
		};

		var UpdateCharterEditorCtrl = function($scope, $modalInstance, charter) {

			$scope.model = {};
			$scope.model.charter = charter;

			$scope.charterFormSubmit = function() {

				$scope.model.charter.$update(function(data, httpResponse) {
					$modalInstance.close(data);
				}, function(httpResponse) {
					alert("Could not update charter! " + httpResponse.data);
				});
			};

			$scope.charterFormCancel = function() {
				$modalInstance.dismiss('cancel');
			};
		};
	} 
]);