var blackserverweb = angular.module("blackserverweb", ["mgcrea.ngStrap", "ngRoute"])
  .config(function($routeProvider, $httpProvider) {

    $routeProvider.when("/home", {
      templateUrl: "/partials/home",
      controller: "home"
    }).when("/users", {
      templateUrl: "/partials/users",
      controller: "users"
    }).when("/activities", {
      templateUrl: "/partials/activities",
      controller: "activities"
    }).when("/chatroom", {
      templateUrl: "/partials/chatroom",
      controller: "chatroom"
    }).when("/login", {
      templateUrl: "/partials/login",
      controller: "login"
    }).otherwise("/");

  })
  .controller("navigation", function($rootScope, $scope, $http, $location) {

  })
  .controller("users", function($rootScope, $scope, $http, $location, $log) {
    if ($rootScope.xtoken) {
      var index = 0;

      function getAllUsers() {
        $http({
          url: "/admin/users",
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            "X-Token": $rootScope.xtoken
          }
        }).success(function(data, status) {
          // 注意所有http服务器的callback都是异步的，而为了浏览器能响应，异步操作不会block而是直接继续执行
          $scope.users = data;
          getNextAvatarData();
        });
      }

      function getNextAvatarData() {
        if ($scope.xtoken && $scope.users) {
          if (index < $scope.users.length) {
            var avatarUrl = "/api/image?q=" + $scope.users[index].avatar.split("~")[0];
            $log.log(avatarUrl);
            $http({
              url: avatarUrl,
              method: "GET",
              headers: {
                "Content-Type": "application/json",
                "X-Token": $rootScope.xtoken
              },
              responseType: "blob"
            }).success(function(data, status) {
              var fileReader = new FileReader();
              fileReader.readAsDataURL(data);
              fileReader.onload = function() {
                $scope.users[index].avatarData = fileReader.result;
                index++;
                getNextAvatarData();
              }
            });
          }
        }
      }
      getAllUsers();
    } else {
      $location.path("/login");
    }
  })
  .controller("activities", function($rootScope, $scope, $http, $location) {
    if ($rootScope.xtoken) {
      var index = 0;

      function getAllActivities() {
        $http({
          url: "/admin/activities",
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            "X-Token": $rootScope.xtoken
          }
        }).success(function(data, status) {
          $scope.activities = data;
          getNextCoverData();
        });
      }

      function getNextCoverData() {
        if ($scope.xtoken && $scope.activities) {
          if (index < $scope.activities.length) {
            var coverUrl = "/api/image?q=" + $scope.activities[index].cover.split("~")[0];
            $http({
              url: coverUrl,
              method: "GET",
              headers: {
                "Content-Type": "application/json",
                "X-Token": $rootScope.xtoken
              },
              responseType: "blob"
            }).success(function(data, status) {
              var fileReader = new FileReader();
              fileReader.readAsDataURL(data);
              fileReader.onload = function() {
                $scope.activities[index].coverData = fileReader.result;
                index++;
                getNextCoverData();
              }
            });
          }
        }
      }
      getAllActivities();
    } else {
      $location.path("/login");
    }
  })
  .controller("login", function($rootScope, $scope, $http, $location, $log) {
    $scope.userLogin = function() {
      if ($scope.login) {
        var tokenUrl = "/api/users/token?phone=" + $scope.login.phone + "&vcode=" + $scope.login.vcode;
        $http.get(tokenUrl).success(function(data, status) {
          $rootScope.xtoken = data.token;
          $log.log("login success");
        }).error(function(data, status) {
          $scope.alert = {
            "title": "message",
            "content": "something went wrong",
            "type": "danger"
          };
          $log.log($scope.alert);
        });
      }
    }
  })
  .controller("chatroom", function($rootScope, $scope, $http, $location, $log) {
    if ($rootScope.xtoken) {} else {
      $location.path("/login");
    }
  })
