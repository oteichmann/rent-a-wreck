'use strict';

/* Services */

var rawServices = angular.module('rawServices', ['ngResource']);

rawServices.factory('Aircraft', ['$resource',
  function($resource){
    return $resource('http://localhost:8080/rent-a-wreck-rest/aircrafts', {}, {
      query: {method:'GET', isArray:true}
    });
  }]);