angular.module("starter.messageCtrl", [])
  .controller("MessageCtrl", ['$scope', '$rootScope', '$timeout', '$stateParams', '$ionicScrollDelegate', 'messageData', '$ionicLoading', 'comFunction', function($scope, $rootScope, $timeout, $stateParams, $ionicScrollDelegate, messageData, $ionicLoading, comFunction) {
    $scope.message = {};
    $scope.start = 1;
    $scope.hasData = false;
    $scope.isHasData = true;
    $scope.refresher = true;

    $scope.$on("$ionicView.beforeEnter", function() {
      messageData.refreshData($scope, $stateParams.exhibitorid)
    });

    $scope.loadMore = function() {
      messageData.moreData($scope, $stateParams.exhibitorid)
      console.info("_____________________loadMore");
    };

    $scope.doRefresh = function() {
      messageData.refreshData($scope, $stateParams.exhibitorid)
    };

    $scope.$on("getMessageData", function(event, data) {
      $scope.setMessage = function() {
        if (!$scope.message.content) {
          return
        } else {
          var messageObj = {
            "audienceName": $scope.username,
            "message": $scope.message.content,
            "createTimes": "刚刚"
          };
          //提交留言
          messageData.sendMessage($scope.message.content, $stateParams.exhibitorid).then(function(res) {
            if (res.data.info[0].success) {
              $ionicLoading.show({
                template: res.data.message,
                duration: 500
              }).then(function() {
                //显示留言
                $scope.items.unshift(messageObj);
                $ionicScrollDelegate.$getByHandle("messageScroll").scrollTop(true);
                //清空上一次留言的数据
                $scope.message = {};
              });
            } else {
              comFunction.myAlertPopup("发送失败,请检查您的网络!")
            }
          })
        }
      };

    })
  }])
