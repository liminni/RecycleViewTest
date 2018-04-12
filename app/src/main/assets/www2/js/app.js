
angular.module('netWork', ['ionic',
  'netWork.directives',
  'netWork.detailCtrl',
  "netWork.messageCtrl"
])

.run(function($ionicPlatform,$window,$rootScope,$state) {
    // var searchURL=$window.location.search;
    // searchURL = searchURL.substring(1, searchURL.length).split("&");
    //  $rootScope.detailInfo={};
    // for(var i=0;i<searchURL.length;i++){
    //   var searchKey=searchURL[i].split("=")[0];
    //   var searchValue=searchURL[i].split("=")[1]
    //     $rootScope.detailInfo[searchKey]=searchValue;
    // }
    // if($rootScope.detailInfo.type==='0'){
    //   $state.go('detailText')
    // }else if($rootScope.detailInfo.type==="1"){
    //   $state.go('detailPic')
    // }

})
.config(function($stateProvider, $urlRouterProvider,$ionicConfigProvider) {
  $ionicConfigProvider.scrolling.jsScrolling(true);
  $ionicConfigProvider.backButton.text("");
   $ionicConfigProvider.backButton.previousTitleText(false);
  $ionicConfigProvider.platform.ios.navBar.alignTitle('center');
  $ionicConfigProvider.platform.android.navBar.alignTitle('center');
  $ionicConfigProvider.platform.ios.backButton.previousTitleText('').icon('ion-ios-arrow-back');
  $ionicConfigProvider.platform.android.backButton.previousTitleText('').icon('ion-ios-arrow-back');
  $ionicConfigProvider.platform.ios.views.transition('ios');
  $ionicConfigProvider.platform.android.views.transition('android');
  $stateProvider
  .state("detailPic",{
      url:"/detailPic",
      templateUrl:'templates/detailPic.html',
      controller:'DetailCtrl'
  })
  .state("detailText",{
      url:"/detailText",
      templateUrl:'templates/detailText.html',
      controller:'DetailCtrl'
  })
  .state("message",{
    url:"/message",
    templateUrl:"templates/message.html",
    controller:"MessageCtrl"
  })
$urlRouterProvider.otherwise('/detailText');
})
