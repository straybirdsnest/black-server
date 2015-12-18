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
  .controller("users", function($scope, $http, $location, $log, authService, userService) {
    if (authService.getXToken()) {
      userService.requestAllUsers().then(function(data) {
        $scope.users = data;
      }, function(reason) {
        $log.log(reason);
      });
    } else {
      $location.path("/login");
    }
  })
  .controller("activities", function($scope, $http, $location, $log, authService) {
    if (authService.getXToken()) {
      var current = 0;

      function getAllActivities() {
        $http({
          url: "/admin/activities",
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            "X-Token": authService.getXToken()
          }
        }).success(function(data, status) {
          $scope.activities = data;
          getNextCoverData();
        });
      }

      function getNextCoverData() {
        if (authService.getXToken() && $scope.activities) {
          if (current < $scope.activities.length) {
            var coverUrl = "/api/image?q=" + $scope.activities[current].cover.split("~")[0];
            $http({
              url: coverUrl,
              method: "GET",
              headers: {
                "Content-Type": "application/json",
                "X-Token": authService.getXToken()
              },
              responseType: "blob"
            }).success(function(data, status) {
              var fileReader = new FileReader();
              fileReader.readAsDataURL(data);
              fileReader.onload = function() {
                $scope.activities[current].coverData = fileReader.result;
                current++;
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
  .controller("login", function($scope, $location, $log, authService) {
    $scope.userLogin = function() {
      var promise = authService.auth($scope.login);
      promise.success(function(data, status) {
        authService.setXToken(data.token);
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
  })
  .controller("chatroom", function($scope, $location, $log, authService) {
    if (authService.getXToken()) {
      $log.log(authService.getXToken());
    } else {
      $location.path("/login");
    }
  })
  .factory("authService", function($http, $log) {
    var xToken;
    var serviceInstance = {
      auth: function(login) {
        var authUrl = "/api/users/token?phone=" + login.phone + "&vcode=" + login.vcode;
        return $http.get(authUrl);
      },
      setXToken: function(token) {
        xToken = token;
      },
      getXToken: function() {
        return xToken;
      }
    };
    return serviceInstance;
  })
  .factory("imageService", function($q, $http, $log, authService) {
    var serviceInstance = {
      requestImage: function(url) {
        var defferred = $q.defer();
        $http({
          url: url,
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            "X-Token": authService.getXToken()
          },
          responseType: "blob"
        }).success(function(data, status) {
          var fileReader = new FileReader();
          fileReader.onload = function() {
            defferred.resolve(fileReader.result);
          };
          fileReader.readAsDataURL(data);
        }).error(function(data, status) {
          defferred.reject("Fail with status " + status);
        });
        return defferred.promise;
      }
    }
    return serviceInstance;
  })
  .factory("userService", function($q, $http, $log, authService, imageService) {
    function requestBasicAll() {
      if (authService.getXToken()) {
        var defferred = $q.defer();
        if (authService.getXToken()) {
          $http({
            url: "/admin/users",
            method: "GET",
            headers: {
              "Content-Type": "application/json",
              "X-Token": authService.getXToken()
            }
          }).success(function(data, status) {
            // 注意所有http服务器的callback都是异步的，而为了浏览器能响应，异步操作不会block而是直接继续执行
            defferred.resolve(data);
          }).error(function(data, status) {
            defferred.reject("Fail with status " + status);
          });
        }
        return defferred.promise;
      }
    }

    function requestAvatarAll(mutilUrl) {
      return $q.all(mutilUrl.map(function(url) {
        return imageService.requestImage(url);
      }));
    }

    var serviceInstance = {
      requestAllUsers: function() {
        var defferred = $q.defer();
        var users;
        requestBasicAll().then(function(data) {
          users = data;
        }).then(function(data) {
          var avatarUrlList = [];
          for (var index = 0; index < users.length; index++) {
            var avatarUrl = "/api/image?q=" + users[index].avatar.split("~")[0];
            avatarUrlList.push(avatarUrl);
          }
          requestAvatarAll(avatarUrlList).then(function(results) {
            results.forEach(function(value, index) {
              users[index].avatarData = value;
            });
            $log.log(users);
            defferred.resolve(users);
          });
        });
        return defferred.promise;
      }
    }
    return serviceInstance;
  })
