angular.module("starter.exhibitorCtrl", [])
  .controller("ExhibitorCtrl", ['$scope', '$rootScope', '$ionicSlideBoxDelegate', '$ionicScrollDelegate', '$ionicModal', '$state', '$ionicPopup', '$timeout', '$stateParams', 'exhibitorData', 'comFunction', function($scope, $rootScope, $ionicSlideBoxDelegate, $ionicScrollDelegate, $ionicModal, $state, $ionicPopup, $timeout, $stateParams, exhibitorData, comFunction) {

    $scope.tipIndex = 0;
    $scope.showNoData = false;
    $scope.searchCont = {};
    $scope.searchInput = {
      autofocus: false
    };
    $scope.isAdd=true;
    // $timeout(function() {
    //   $ionicSlideBoxDelegate.$getByHandle('exhibitor').enableSlide(false)
    // })
    $scope.$on("$ionicView.beforeEnter", function() {
      $scope.activeItem = 0;
      changeTip(0)
      $rootScope.stateExhibitorId = $stateParams.id ? $stateParams.id : $rootScope.stateExhibitorId;
      //展商详情
      exhibitorData.getExhibitorDetile({
        "exhibitorid": $rootScope.stateExhibitorId
      }).then(function(res) {
        $scope.tipIndex = 0;
        $scope.item = res.data.info;

        $scope.attentionState = $scope.item.isfollew === "true" ? {
          state: 1,
          name: "取消关注"
        } : {
          state: 0,
          name: "关注"
        }
      })
      //展品列表
      exhibitorData.getExhibitProductList({
        "exhibitorid": $rootScope.stateExhibitorId,
        "content": ""
      }).then(function(res) {
        $scope.exhibit = res.data.info.exhibitProductList;
        $scope.industryArr = res.data.info.industryList;
      })


      //关注,取消关注
      $scope.attentionExhibit = function(state) {
        if (!state) {
          exhibitorData.getExhibitAttention($stateParams.id).then(function(res) {
            comFunction.myAlertPopup(res.data.message, function() {
              if (res.data.code === "200") {
                $scope.attentionState = {
                  state: 1,
                  name: "取消关注"
                };
              };
            });
          });
        } else {
          exhibitorData.cancelExhibitorAttention($stateParams.id).then(function(res) {
            comFunction.myAlertPopup(res.data.message, function() {
              if (res.data.code === "200") {
                $scope.attentionState = {
                  state: 0,
                  name: "关注"
                };
              };
            });
          });

        }
      };

      function changeTip(index) {
        if (scrollTimer) $timeout.cancel(scrollTimer);
        $scope.tipIndex = index;
        $ionicSlideBoxDelegate.$getByHandle("exhibitor").slide(index);
        if (index == 1) {
          var scrollTimer = $timeout(function() {
            $ionicScrollDelegate.$getByHandle("exhibitorListScroll").scrollTo(0, 0, false)
          }, 500)
        }
      }
      $scope.changeTip = function(index) {
        changeTip(index);
        $ionicSlideBoxDelegate.$getByHandle("exhibitor").slide(index);
      };

      $scope.changeItem = function(index, id) {
        $scope.activeItem = index;
        exhibitorData.getExhibitProductList({
          "exhibitorid": $stateParams.id,
          "industryId": id,
          "content": ""
        }).then(function(res) {
          $scope.exhibit = res.data.info.exhibitProductList;

        })
      };

      $scope.onSlideChanged = function(index) {
        changeTip(index);

      };

      //搜索
      $scope.showSearchModal = function() {
        $scope.searchInput.autofocus = true;
        //搜索modal
        $ionicModal.fromTemplateUrl('./templates/exhibitSearch.html', {
          scope: $scope,
          animation: 'slide-in-right',
          focusFirstInput: true,
          focusFirstDelay: 100
        }).then(function(modal) {
          $scope.searchModal = modal;
          $scope.searchCont = {};
          $scope.searchModal.show();
        });
      };
      $scope.colseSearchModal = function() {
        $scope.searchCont = {};
        $scope.selectExhibit = "";
        $scope.showNoData = false;
        $scope.searchModal.hide();
      };
      $scope.$on('$destroy', function() {
        $scope.searchModal && $scope.searchModal.remove();
      });
      $scope.searchExhibit = function(content) {
        $scope.searchTimer && $timeout.cancel($scope.searchTimer)
        $scope.searchTimer = $timeout(function() {
          exhibitorData.getExhibitProductList({
            "content": content,
            "exhibitorid": $stateParams.id
          }).then(function(res) {
            $scope.selectExhibit = res.data.info.exhibitProductList;
            $scope.showNoData = true;
          })
        }, 1000)
      }

      //搜素页到展品详情
      $scope.goExhibitDetail = function(id) {
        $scope.searchCont = {};
        $scope.selectExhibit = "";
        $scope.showNoData = false;
        $scope.searchModal.hide();
        $rootScope.currentModal.splice(0, 1);
        $state.go("exhibitDetail", {
          "id": id
        })
      }
      //展品介绍页到展品详情
      $scope.goExhibitDetailById = function(id) {
        $state.go("exhibitDetail", {
          "id": id
        })
      }
      //go留言页面
      $scope.goMessage = function() {
        $state.go("message", {
          "exhibitorid": $stateParams.id
        })
      }
      //显示地图
      $scope.myPopup = {
        popupIsShow: false
      }

      $scope.showMapPopup = function() {
        $scope.optionsPopup = $ionicPopup.show({
          templateUrl: 'templates/map.html',
          scope: $scope,
          cssClass: "exhibitorMap"
        })
        $timeout(function() {
          $ionicSlideBoxDelegate.$getByHandle("exhibitNav").update()
        })
        //地图弹窗出现后标记popupIsShow为true
        $scope.myPopup = {
          popupIsShow: true
        }
      };
      $scope.showMap = function() {
        //展商地图
        if ($scope.imgArr) {
          $scope.showMapPopup()
        } else {
          exhibitorData.getExhibitorDetileMap({
            "exhibitorid": $stateParams.id,
          }).then(function(res) {
            $scope.imgArr = res.data.info;
            $scope.showMapPopup()
          })
        }
      };
      //拨打电话
      $scope.callNumber = function(number) {
        if ($scope.is_iphone) {
          MDHPhone.Telephony.call(function() {}, function() {}, {
            phoneNumber: number
          })
        } else {
          window.open("tel:" + number)
        }
      }
      //
      $scope.showAdd=function(){
        $scope.isAdd=!$scope.isAdd;
      }
      //跳转源生参观计划页面
      $scope.goVisitPlant=function(){
        $scope.isAdd=!$scope.isAdd;
        document.location = "eventStatistic://openVisitPlan"
      }
      //添加个人参观计划
    });
  }])
