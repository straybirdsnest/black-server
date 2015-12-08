angular.module('blackserverweb', ['ui.bootstrap', 'ngRoute'])
 .config(function($routeProvider, $httpProvider) {

	$routeProvider.when('/home', {
		//templateUrl : '/partials/home',
		//controller : 'home'
	}).otherwise('/');

  })
 .controller('navigation', function($rootScope, $scope, $http, $location) {

 })
