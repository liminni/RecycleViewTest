angular.module("starter.common", [])
  .factory("backKey", ['$rootScope', '$ionicPopup', '$location', '$state', '$ionicHistory', 'comFunction', function($rootScope, $ionicPopup, $location, $state, $ionicHistory, comFunction) {
    return {
      backKeyCallback: function() {
        if ($location.path() == "/home") {
          comFunction.myConfirmPopup("确认退出?", function() {
            CloseProgram.closeProgram();
          });
        } else if ($location.path() == "/exhibitors") {
          $state.go('home');
        } else if ($location.path() == "/exhibitDetail") {
          $state.go("exhibitors", {
            "id": $rootScope.stateExhibitorId
          });
        } else {
          $rootScope.$ionicGoBack();
        }
      }
    }
  }])
  .factory("comFunction", function($rootScope, $ionicPopup, $ionicHistory, $state) {
    return {
      myAlertPopup: function(title, callback) {
        var myAlert = $ionicPopup.alert({
          title: title,
          okText: '确定',
        })
        myAlert.then(function(res) {
          if (callback && angular.isFunction(callback)) {
            callback()
          }

        })
      },
      myConfirmPopup: function(title, callback, cancel) {
        var myConfirm = $ionicPopup.confirm({
          title: title,
          okText: "确定",
          cancelText: "取消",
        });
        myConfirm.then(function(res) {
          if (res) {
            if (callback && angular.isFunction(callback)) {
              callback()
            }
          } else {
            if (cancel && angular.isFunction(cancel)) {
              cancel()
            }
          }
        })
      }
    }
  })
