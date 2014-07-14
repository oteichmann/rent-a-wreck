

angular.module('rawDirectives.datePicker', [])
.controller('DatePickerCtrl', ['$scope',function ($scope) {
	'use strict';

	// /** Datepicker **/
	//   $scope.today = function() {
	// 	    $scope.model = new Date();
	//   };

	//   $scope.clear = function () {
	// 	    $scope.model = null;
	//   };

	//   /*$scope.toggleMin = function() {
	// 	    $scope.minDate = $scope.minDate ? null : new Date();
	//   };
	//   $scope.toggleMin();*/

	//   $scope.open = function($event) {
	// 	  	$event.preventDefault();
	// 	    $event.stopPropagation();

	// 	    $scope.opened = true;	    
	//   };

	//   $scope.dateOptions = {
	// 	    formatYear: 'yy',
	// 	    startingDay: 1
	//   };

	//   if (!$scope.format){
	// 	  $scope.format = 'yyyy/MM/dd'; // Possible formats: 'dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'
	//   }

	  	$scope.today = function() {
			$scope.model = new Date();
		};
		// $scope.today();

		$scope.clear = function() {
			$scope.model = null;
		};

		// Disable weekend selection
		$scope.disabled = function(date, mode) {
			return (mode === 'day' && (date.getDay() === 0 || date.getDay() === 6));
		};

		if (!$scope.minDate){
			$scope.minDate = new Date();
		}

		$scope.open = function($event) {
			$event.preventDefault();
			$event.stopPropagation();

			$scope.opened = true;
		};

		$scope.dateOptions = {
			formatYear : 'yy',
			startingDay : 1,
			'show-button-bar' : false
		};

		$scope.initDate = new Date();

		if (!$scope.format){
			$scope.format = 'mediumDate';
		}

		$scope.inputid = '';
}])
.directive('rawDatePicker',function(){
	'use strict';
	
	return {
		restrict: 'E',
		scope: {
			model: '=',
			format: '@',
			// minDate: '=',
			inputId : '@inputId',
			inputLabel : '@inputLabel'
		},
		templateUrl: 'views/directives/datepicker.html',
		controller: 'DatePickerCtrl'
	}
	
});
// .directive("dynamicName", function($compile) {
//   return {
//     restrict: "A",
//     terminal: true,
//     priority: 1000,
//     link: function(scope, element, attrs) {
//       var name = scope.$eval(attrs.dynamicName);
//       if (name) {
//         element.attr('name', name);
//         element.removeAttr("dynamic-name");
//         $compile(element)(scope);
//       }
//     }
//   };
// });
