angular.module('netWork.detailCtrl', [])
  .controller('DetailCtrl', ['$scope', '$rootScope', '$ionicPopup','$state','$ionicSlideBoxDelegate','detailService', function($scope, $rootScope, $ionicPopup,$state,$ionicSlideBoxDelegate,detailService) {
    $rootScope.textareaFocus = false;
    $scope.sharePopup={"ispopup":false};
    detailService.detailData('ff8080815f684133015f6c4b2cd60005','newInteractive','18201125988').then(function(res){
      console.log(res);
      $scope.detailList=res.data;
      //  $ionicSlideBoxDelegate.$getByHandle('picBox').update()
      // if($rootScope.detailInfo.type==="newImage"){
      //     $ionicSlideBoxDelegate.$getByHandle('picBox').update()
      //   }
    })
    $scope.myPanorama=function(url){
      console.log(url);
    }
    $scope.showMore = function() {
      $scope.sharePopup.ispopup=true;
      $scope.mySharePopup = $ionicPopup.show({
        template: "<ul class='moreBox'>"+
          "<i class='icon ion-android-arrow-dropup'></i>"+
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
