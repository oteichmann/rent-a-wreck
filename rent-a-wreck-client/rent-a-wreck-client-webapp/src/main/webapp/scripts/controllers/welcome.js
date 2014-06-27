'use strict';

rawControllers.controller('welcomeCtrl', 
	[ '$scope', '$log', '$location',
	function($scope, $log, $location) {

		$scope.gotoBookings = function() {
			$location.path('/bookings');
		}
	} 
]);