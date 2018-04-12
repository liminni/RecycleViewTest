angular.module('netWork.detailCtrl', [])
  .controller('DetailCtrl', ['$scope', '$rootScope', '$ionicPopup','$state', function($scope, $rootScope, $ionicPopup,$state) {
    $rootScope.textareaFocus = false;
    $scope.sharePopup={"ispopup":false};

    $scope.showMore = function() {
      $scope.sharePopup.ispopup=true;
      $scope.mySharePopup = $ionicPopup.show({
        template: "<ul class='moreBox'>"+
          "<b>♦</b>"+
          "<li ng-click='shareNew()'><i class='icon ion-android-share-alt'></i><span>分享</span></li>"+
          "<li ng-click='collect()'><i class='icon ion-android-star-outline'></i><span>收藏</span></li>"+
        "</ul>",
        scope: $scope,
        cssClass:"moreBox",

      });
    }
    //收藏
    $scope.collect=function(){
      console.log(111);
      $scope.mySharePopup.close()
    }
    //分享
    $scope.shareNew=function(){
      console.log(222);
      $scope.mySharePopup.close()
    }
    //评论页
    $scope.goMessage=function(){
      $state.go('message')
    }
  }])
