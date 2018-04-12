angular.module("starter.exhibitDetailCtrl", [])
  .controller("ExhibitDetailCtrl", ['$scope', '$ionicSlideBoxDelegate', 'exhibitorData', '$stateParams', '$state', function($scope, $ionicSlideBoxDelegate, exhibitorData, $stateParams, $state) {
    exhibitorData.getExhibitProductDetail({
      "exhibitid": $stateParams.id
    }).then(function(res) {
      $scope.exhibitProduct = res.data.info[0].exhibitProduct;
      $scope.exhibit = res.data.info[0].exhibit;
      if ($scope.exhibitProduct.vedioOrPic == "vedio") {
        $scope.videoInfo = {
          src: $scope.exhibitProduct.vedio,
          posterImg: $scope.exhibitProduct.logo

        };
      }
    })


    $scope.changePage = function(index) {
      $ionicSlideBoxDelegate.$getByHandle("exhibitNav").slide(index);
    }
    //goExhibitor
    $scope.goExhibitor = function(id) {
      console.log(id)
      $state.go("exhibitors", {
        "id": id
      })
    }

  }])
