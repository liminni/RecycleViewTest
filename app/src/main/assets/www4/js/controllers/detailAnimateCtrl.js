angular.module('netWork.detailAnimateCtrl', [])
  .controller('DetailAnimateCtrl', ['$scope','detailService', function($scope,detailService) {
    $scope.autoPlay=true;
    detailService.detailData('ff8080815f684133015f6c4b2cd60005','newInteractive','18201125988').then(function(res){
      console.log(res);
      $scope.detailList=res.data;
      $scope.$on('ngRepeatFinished',function(){
        var mySwiper = new Swiper('.swiper-container', {
          direction:"vertical",
          onInit: function(swiper) {
            swiperAnimateCache(swiper);
            swiperAnimate(swiper);
          },
          onSlideChangeEnd: function(swiper) {
            swiperAnimate(swiper);
          },
          onTransitionEnd: function(swiper) {
            swiperAnimate(swiper);
          },


          watchSlidesProgress: true,
          onProgress: function(swiper) {
            for (var i = 0; i < swiper.slides.length; i++) {
              var slide = swiper.slides[i];
              var progress = slide.progress;
              var translate = progress * swiper.height / 4;
              scale = 1 - Math.min(Math.abs(progress * 0.5), 1);
              var opacity = 1 - Math.min(Math.abs(progress / 2), 0.5);
              slide.style.opacity = opacity;
              es = slide.style;
              es.webkitTransform = es.MsTransform = es.msTransform = es.MozTransform = es.OTransform = es.transform = 'translate3d(0,' + translate + 'px,-' + translate + 'px) scaleY(' + scale + ')';
            }
          },

          onSetTransition: function(swiper, speed) {
            for (var i = 0; i < swiper.slides.length; i++) {
              es = swiper.slides[i].style;
              es.webkitTransitionDuration = es.MsTransitionDuration = es.msTransitionDuration = es.MozTransitionDuration = es.OTransitionDuration = es.transitionDuration = speed + 'ms';

            }
          }
        });
      })
    })

    $scope.toggleAudio=function(){
      var myAudio=angular.element(document.querySelector('#myAudio'))
      angular.element(document.querySelector('.musicLogo')).toggleClass('musicLogoPaused')
      if($scope.autoPlay){
        myAudio[0].pause();
      }else{
        myAudio[0].play();
      }
      $scope.autoPlay=!$scope.autoPlay;
      console.log(myAudio);
    }
  }])
