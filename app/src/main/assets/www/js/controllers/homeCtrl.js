angular.module('starter.homeCtrl', [])
  .controller('HomeCtrl', ['$scope', '$rootScope', '$ionicScrollDelegate', '$ionicModal', '$state', '$timeout', '$q', 'homeData', "$window", function($scope, $rootScope, $ionicScrollDelegate, $ionicModal, $state, $timeout, $q, homeData, $window) {

    $scope.subTitleIndex = 0;
    $scope.activeTip = 0;
    $scope.activeItem = 0;
    $scope.searchCont = {}; //记录搜索内容
    $scope.areaid = ""; //记录当前展区id
    $scope.showNoData = false; //记录无数据节点是否显示
    $scope.searchInput = {
      autofocus: false
    };
    $q.all([
      //获取热门展商
      homeData.getHotData(),
      //获取展区
      homeData.getExhibitorAreaList(),
      //获取行业
      homeData.getExhibitorIndustryList(),
      //获取展商列表
      homeData.getExhibitList()
    ]).then(function(res) {
      $scope.navItems = res[0].data.info;
      $scope.areaItems = res[1].data.info;
      $scope.industryItems = res[2].data.info;
      $scope.exhibitorLists = res[3].data.info;
    }, function(error) {
      console.log(error);
    })
    //关闭webview
    $scope.colseView = function() {
      if ($scope.is_iphone) {
        MDHMgr.exec("CloseProgram.closeProgram", function() {}, function() {})
      } else {
        CloseProgram.closeProgram()
      }
    }
    //我的关注
    $scope.goAttention = function() {
      $state.go("attention")
    }
    //goExhibitor
    $scope.goExhibitor = function(id) {
      $state.go("exhibitors", {
        "id": id
      })
    }

    //搜索
    $scope.showSearchModal = function() {
      $scope.searchInput.autofocus = true;
      //搜索modal
      $ionicModal.fromTemplateUrl('./templates/search.html', {
        scope: $scope,
        animation: 'slide-in-right',

      }).then(function(modal) {
        $scope.searchModal = modal;
        $scope.searchCont = {};
        $scope.searchModal.show();
      });
    };
    $scope.exhibitorShow = function(id) {
      $scope.selectExhibit = "";
      $scope.searchCont = {};
      $scope.searchModal.hide();
      $scope.goExhibitor(id)
    }
    $scope.colseSearchModal = function() {
      $scope.selectExhibit = "";
      $scope.searchCont = {};
      $scope.showNoData = false;
      $scope.searchModal.hide();
    }
    $scope.$on('$destroy', function() {
      $scope.searchModal && $scope.searchModal.remove();
    });

    $scope.searchExhibit = function(content) {
      $scope.searchTimer && $timeout.cancel($scope.searchTimer)
      $scope.searchTimer = $timeout(function() {
        homeData.getExhibitSelectList(content).then(function(res) {
          $scope.selectExhibit = res.data.info;
          $scope.showNoData = true;
        })
      }, 1000)
    }
    //切换展区
    $scope.changeArea = function(index, id) {
      $scope.activeItem = 0;
      $scope.areaid = index == 0 ? "" : id;
      $scope.subTitleIndex = index;
      $q.all([
        //获取行业
        homeData.getExhibitorIndustryList({
          "areaid": id
        }),
        //获取展商列表
        homeData.getExhibitList({
          "areaid": $scope.areaid,
          "industryid": ""
        })
      ]).then(function(res) {
        $scope.exhibitorLists = null;
        $timeout(function() {
          $scope.industryItems = res[0].data.info;
          $scope.exhibitorLists = res[1].data.info;
        })
      })
    };
    //切换行业
    $scope.changeItem = function(index, id) {
      $scope.activeItem = index;
      homeData.getExhibitList({
        "areaid": $scope.areaid,
        "industryid": id
      }).then(function(res) {
        $scope.exhibitorLists = null;
        $timeout(function() {
          $scope.exhibitorLists = res.data.info;
        })
      })
    }


    $scope.checkPosition = function() {
      $scope.rightTop = $ionicScrollDelegate.$getByHandle('home_mainRight')._instances[3].getScrollPosition().top;
      $scope.$apply(function() {
        $scope.activeTip = parseInt($scope.rightTop / (92 * 20)) < 1 ? 0 : parseInt($scope.rightTop / (92 * 20))
      })
    };

    //tip楼层
    $scope.goTopIndex = function(index) {
      $ionicScrollDelegate.$getByHandle("home_mainRight")._instances[3].scrollTo(0, 92 * index * 20, true)
      $scope.activeTip = index;

    }
  }])
