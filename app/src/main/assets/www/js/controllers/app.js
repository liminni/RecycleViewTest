angular.module('starter', ['ionic',
    'starter.homeCtrl',
    'starter.exhibitorCtrl',
    'starter.attentionCtrl',
    'starter.exhibitDetailCtrl',
    'starter.messageCtrl',
    'starter.directive',
    'starter.filter',
    'starter.services',
    'starter.common',
    'homeService',
    "messageService",
    "attentionService",
    "exhibitorService"
  ])
  .value("CONFIG", {
    "CONNECTION_TYPE": "http",
    "IP": "10.18.20.193",
    "PORT": "8080",
    "CONTEXT_URL": "gaojiao"
  })

  .run(function($ionicPlatform, $rootScope, $ionicLoading, backKey, $window, $ionicHistory) {
    $rootScope.$on('loading.show', function() {
	    $ionicLoading.show({
        	template: '<div><ion-spinner class="spinnerContent spinner-positive" icon="bubbles"></ion-spinner>'+
	      '<span class="loading-msg1">正在加载</span></div>',
	      noBackdrop: false
	    });
	  })

	  // 请求完成时，隐藏加载框
	  $rootScope.$on('loading.hide', function() {
	      $ionicLoading.hide();
	  })
    //获取源生传参userid
    localStorage.HZ_userid = $window.location.search.replace("?", "");
    //判断设配类型
    var _agent = navigator.userAgent.toLowerCase();
    $rootScope.is_iphone = (0 < _agent.indexOf("iphone") || 0 < _agent.indexOf("ipad"));
    //pc端调试userid
    localStorage.HZ_userid = "manshuang"

    // 处理安卓的物理back键
    $ionicPlatform.registerBackButtonAction(function(e) {
      e.preventDefault();
      backKey.backKeyCallback()
    }, 101);
    window.backCallback = function() {
      var e = document.createEvent("Events");
      e.initEvent("backbutton", false, false);
      document.dispatchEvent(e)
    };

  })

  .config(function($stateProvider, $urlRouterProvider, $ionicConfigProvider) {
    $ionicConfigProvider.platform.ios.navBar.alignTitle('center');
    $ionicConfigProvider.platform.android.navBar.alignTitle('center');

    $ionicConfigProvider.platform.ios.backButton.previousTitleText('').icon('ion-ios-arrow-thin-left');
    $ionicConfigProvider.platform.android.backButton.previousTitleText('').icon('ion-android-arrow-back');
    $ionicConfigProvider.platform.ios.views.transition('ios');
    $ionicConfigProvider.platform.android.views.transition('android');

    $ionicConfigProvider.scrolling.jsScrolling(true);


    $stateProvider


      .state('home', {
        url: '/home',
        templateUrl: 'templates/home.html',
        controller: "HomeCtrl"
      })
      .state("attention", {
        url: "/attention",
        templateUrl: "templates/attention.html",
        controller: "AttentionCtrl"
      })
      .state("exhibitors", {
        url: "/exhibitors",
        params: {
          "id": null
        },
        templateUrl: "templates/exhibitors.html",
        controller: "ExhibitorCtrl"
      })
      .state("exhibitDetail", {
        url: "/exhibitDetail",
        params: {
          "id": null
        },
        templateUrl: "templates/exhibitDetail.html",
        controller: "ExhibitDetailCtrl"
      })
      .state('message', {
        url: "/message",
        params: {
          "exhibitorid": null
        },
        templateUrl: "templates/message.html",
        controller: "MessageCtrl"
      })

    $urlRouterProvider.otherwise('/home');

  });
