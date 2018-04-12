angular.module("starter.attentionCtrl", [])
  .controller("AttentionCtrl", ['$scope', '$rootScope', '$ionicSlideBoxDelegate', '$ionicScrollDelegate', '$ionicModal', '$state', '$ionicPopup', '$timeout', 'attentionData', '$stateParams', function($scope, $rootScope, $ionicSlideBoxDelegate, $ionicScrollDelegate, $ionicModal, $state, $ionicPopup, $timeout, attentionData, $stateParams) {

    $scope.$on("$ionicView.beforeEnter", function() {
      attentionData.attentionList("userid").then(function(res) {
        $scope.items = res.data.info
      })
    });

    //展商详情
    $scope.goExhibitor = function(id) {
      $state.go("exhibitors", {
        "id": id
      })
    }
  }])
