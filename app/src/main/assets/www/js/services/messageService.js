angular.module("messageService", [])
  .factory("messageData", ['mainRequest', function(mainRequest) {
    return {
      messageList: function(exhibitorid, start) {
        var param = {
          "exhibitid": exhibitorid,
          "pageNo": start,
          "pageSize": 20
        }
        return mainRequest.request(param, "exhibitProductAction.do", "getMessages")
      },
      refreshData: function($scope, exhibitorid) {
        $scope.start = 1;
        this.messageList(exhibitorid, 1).then(function(res) {
          console.log(res);
          $scope.hasData = res.data.info[0].messageList.length < 20 ? false : true;
          $scope.items = res.data.info[0].messageList;
          $scope.username = res.data.info[0].username;
          $scope.$broadcast('scroll.refreshComplete');
          $scope.$broadcast("getMessageData", $scope.items)
        })
      },
      moreData: function($scope, exhibitorid) {
        if ($scope.items && $scope.items.length > 0) {
          //$scope.start = $scope.items.length + 1;
          $scope.start = $scope.start + 1;
        };
        this.messageList(exhibitorid, $scope.start).then(function(res) {
          if ($scope.items && $scope.items.length > 0) {
            $scope.items = $scope.items.concat(res.data.info[0].messageList)
          } else {
            $scope.items = res.data.info[0].messageList;
          };
          if (res.data.info[0].messageList.length < 20) {
            $scope.hasData = false;
          }
          $scope.isHasData = res.data.info[0].messageList.length > 0 ? true : false;
          $scope.$broadcast('scroll.infiniteScrollComplete');
          $scope.$broadcast("getMessageData", $scope.items)
        })
      },
      sendMessage: function(message, exhibitid) {
        var param = {
          "exhibitid": exhibitid,
          "message": message
        }
        return mainRequest.request(param, "exhibitProductAction.do", "audienceMessage")
      }
    }
  }])
