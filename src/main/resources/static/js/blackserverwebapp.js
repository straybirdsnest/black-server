var blackserverweb = angular.module('blackserverweb', ['mgcrea.ngStrap', 'ngRoute'])
 .config(function($routeProvider, $httpProvider) {

	$routeProvider.when('/home', {
		templateUrl : '/partials/home',
		controller : 'home'
	}).when('/users', {
       	templateUrl : '/partials/users',
       	controller : 'users'
    }).when('/activities', {
        templateUrl : '/partials/activities',
        controller : 'activities'
    }).otherwise('/users');

  })
 .controller('navigation', function($rootScope, $scope, $http, $location) {

 })
 .controller('users', function($rootScope, $scope, $http, $location) {
   var index = 0;
   function getXToken(){
     $http.get('/api/user/token?phone=123456789&vcode=1234').success(function(data, status){
       $scope.xtoken = data.token;
       getAllUsers();
     });
   }
   function getAllUsers(){
     $http({
       url:'/admin/users',
       method: 'GET',
       headers: {
         'Content-Type': 'application/json',
         'X-Token': $scope.xtoken
       }
     }).success(function(data, status){
          // 注意所有http服务器的callback都是异步的，而为了浏览器能响应，异步操作不会block而是直接继续执行
          $scope.users = data;
          getNextAvatarData();
        });
   }
   function getNextAvatarData()
   {
     if($scope.xtoken && $scope.users){
       if(index < $scope.users.length){
         var avatarUrl = '/api/image?q='+$scope.users[index].avatar;
         $http({
           url: avatarUrl,
           method: 'GET',
           headers: {
             'Content-Type': 'application/json',
             'X-Token': $scope.xtoken
           },
           responseType: 'blob'
         }).success(function(data, status){
           var fileReader = new FileReader();
           fileReader.readAsDataURL(data);
           fileReader.onload = function()
           {
             $scope.users[index].avatarData = fileReader.result;
             index++;
             getNextAvatarData();
           }
         });
       }
     }
   }
   getXToken();
 })
 .controller('activities', function($rootScope, $scope, $http, $location){
   var index = 0;
   function getXToken(){
     $http.get('/api/user/token?phone=123456789&vcode=1234').success(function(data, status){
       $scope.xtoken = data.token;
       getAllActivities();
     });
   }
   function getAllActivities(){
     $http({
       url:'/admin/activities',
       method: 'GET',
       headers: {
         'Content-Type': 'application/json',
         'X-Token': $scope.xtoken
         }
       }).success(function(data, status){
         $scope.activities = data;
         getNextCoverData();
       });
   }
   function getNextCoverData()
   {
     if($scope.xtoken && $scope.activities){
       if(index < $scope.activities.length){
         var coverUrl = '/api/image?q='+$scope.activities[index].cover;
         $http({
           url: coverUrl,
           method: 'GET',
           headers: {
             'Content-Type': 'application/json',
             'X-Token': $scope.xtoken
           },
           responseType: 'blob'
         }).success(function(data, status){
           var fileReader = new FileReader();
           fileReader.readAsDataURL(data);
           fileReader.onload = function()
           {
             $scope.activities[index].coverData = fileReader.result;
             index++;
             getNextCoverData();
           }
         });
       }
     }
   }
   getXToken();
 })
