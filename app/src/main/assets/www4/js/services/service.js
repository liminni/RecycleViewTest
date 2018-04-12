angular.module('netWork.services', [])
  .factory("mainRequest", ['$rootScope',"$http","CONFIG","comFunction" ,function($rootScope,$http,CONFIG,comFunction) {
    return {
      request: function(params) {
        $rootScope.$broadcast('loading:show')
        var requestURL = CONFIG.CONNECTION_TYPE + "://" + CONFIG.IP + ":" + CONFIG.PORT + "/" + CONFIG.CONTEXT_URL + "/rs/news/getConnectionNewsDetail";
        var promise = $http({
          method: "post",
          url: requestURL,
          data: params,
          headers: {
            "Content-Type": "application/json;charset=UTF-8"
          },
          timeout: 30000,
          cache: false
        }).success(function(data, status, headers, config) {
            $rootScope.$broadcast('loading:hide')
        }).error(function(data, status, headers, config) {
          $rootScope.$broadcast('loading:hide')
            comFunction.myAlertPopup("数据有误，请联系管理员！")
        });
        return promise;
      }
    }
  }])
  .factory("comFunction", function($ionicPopup) {
    return {
      myAlertPopup: function(title, callback) {
        var myAlert = $ionicPopup.alert({
          title: title,
          okText: '确定',
          okType: "button-assertive"
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
