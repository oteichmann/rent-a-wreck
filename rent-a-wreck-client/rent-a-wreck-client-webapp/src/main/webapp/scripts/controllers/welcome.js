'use strict';

rawControllers.controller('welcomeCtrl', 
	[ '$scope', '$log', '$location',
	function($scope, $log, $location) {

		$scope.gotoCharters = function() {
			$location.path('/charters');
		}
	} 
]);