angular.module('netWork.directives',[])
.directive('autoHeight', function($rootScope,$timeout) {
    return {
      link: function(scope, element, attrs) {

        element.bind('focus', function() {
          $timeout(function(){
            angular.element(element[0]).removeClass("height30");
            angular.element(element[0]).toggleClass('height150');
            $rootScope.textareaFocus=true;
          },300)
        });
        element.bind('blur', function() {
          $timeout(function(){
            angular.element(element[0]).removeClass("height150");
            angular.element(element[0]).addClass('height30');
            $rootScope.textareaFocus=false;
          })
        })
      }
    }
  })
  .directive('onRepeatFinishedRender', function($timeout) {
    return {
      restrict: 'A',
      link: function(scope, element, attr) {
        if (scope.$last === true) {
          $timeout(function() {

            scope.$emit('ngRepeatFinished', element);
          });
        }
      }
    };
  })
  .directive('closePopupBackDrop', ['$ionicGesture',function($ionicGesture) {
    return {
      scope: false,
      restrict: 'A',
      replace: false,
      link: function(scope, element, attrs) {
        var $htmlEl = angular.element(document.querySelector('html'));
        $ionicGesture.on("touch", function(event) {

          if (event.target.nodeName === "HTML" && scope.sharePopup.ispopup) {
            scope.sharePopup.ispopup=false;
            scope.mySharePopup.close()
              console.log(event.target.nodeName);
          }
        }, $htmlEl);
      }
    };
  }])
  .directive('myVideo', ['$ionicGesture', '$sce', '$timeout', '$window', function($ionicGesture, $sce, $timeout, $window) {
      return {
        scope: {
          'info':"=info"
        },
        replace: true,
        templateUrl: "templates/video.html",
        link: function(scope, ele, attr) {
          scope.onOff = {
            showPlayButton: false, //中间暂停按钮
            showControl: false, //底部视频控制
            showLoading: true //加载loadingBar
          }
          var video = angular.element(document.querySelector('video'));
          var videoPlay = angular.element(document.querySelector(".videoPlay"));
          var player = angular.element(document.querySelector(".playBotton"));
          var progressWidth = angular.element(document.querySelector(".videoProgress"))[0].clientWidth;
          var currentPosition = angular.element(document.querySelector("#progressDiv"));
          var loaded = angular.element(document.querySelector("#loaded"));
          currentPosition[0].style.left = -6 + "px";
          video[0].src = $sce.trustAsResourceUrl(scope.info);
        //  video[0].poster = scope.videoInfo.posterImg;
          //video[0].preload = "metadata"; //阻止预加载
          video[0].preload = "auto";
          video[0].addEventListener("canplay", function() {

            scope.$apply(function() {
              scope.onOff = {
                showPlayButton: true, //中间暂停按钮
                showControl: true, //底部视频控制
                showLoading: false, //加载loadingBar
                canplay: true
              }
              scope.endTime = video[0].duration.toFixed(1);
              scope.currentTime = 0;
            })

          }, false);
          video[0].addEventListener("timeupdate", function() {

            //progressWidth = angular.element(document.querySelector(".videoProgress"))[0].clientWidth;
            scope.$apply(function() {

              scope.currentTime = video[0].currentTime;
              currentPosition[0].style.left = (scope.currentTime / scope.endTime) * progressWidth - 6 + "px";

              loaded[0].style.width = parseFloat(currentPosition[0].style.left) <= 0 ? 0 : currentPosition[0].style.left;
            })

          }, false)
          video[0].addEventListener("playing", function() {
            scope.$apply(function() {
              scope.onOff.showPlayButton = false;
              scope.onOff.showControl = false;
              scope.onOff.showLoading = false;
            });
          }, false)
          video[0].addEventListener("pause", function() {
              scope.$apply(function() {
                scope.onOff.showPlayButton = true;
                scope.onOff.showControl = true;
                scope.onOff.showLoading = false;
              })
            },
            false)
          video[0].addEventListener("waiting", function() {
            scope.$apply(function() {
              scope.onOff.showLoading = true;
            })
          }, false)
          video[0].addEventListener("error", function(err) {

            console.log(err)
          }, true);
          //进度条拖拽
          var startLeft;
          $ionicGesture.on("touchstart", function(event) {
            startLeft = event.target.style.left;
            progressWidth = angular.element(document.querySelector(".videoProgress"))[0].clientWidth;
          }, currentPosition)
          $ionicGesture.on("drag", function(event) {
            event.target.style.left = parseFloat(startLeft) + event.gesture.deltaX + "px";
            if (parseFloat(event.target.style.left) <= -6) {
              event.target.style.left = -6 + "px";
            } else if (parseFloat(event.target.style.left) >= (progressWidth - 6)) {
              event.target.style.left = (progressWidth - 6) + "px";
            }
            loaded[0].style.width = parseFloat(currentPosition[0].style.left) <= 0 ? 0 : currentPosition[0].style.left;


            video[0].startTime = (parseFloat(event.target.style.left) + 6) / progressWidth * scope.endTime;
            video[0].currentTime = (parseFloat(event.target.style.left) + 6) / progressWidth * scope.endTime;
            scope.$apply(function() {
              scope.onOff.showControl = true;
              scope.currentTime = video[0].startTime;
            })
          }, currentPosition)
          $ionicGesture.on("tap", function() {

            if (controlTimer) $timeout.cancel(controlTimer);
            var isPlaying = video[0].currentTime > 0 && !video[0].paused && !video[0].ended &&
              video[0].readyState >= 2;
            if (!isPlaying) {
              video[0].play();
              var controlTimer = $timeout(function() {
                scope.onOff.showControl = false;
                scope.onOff.showPlayButton = false;
              }, 2000);
            } else {
              $timeout(function() {
                scope.onOff.showLoading = false;
                video[0].pause()
              })
            }
          }, player);
          $ionicGesture.on("tap", function() {
            if (playTimer) $timeout.cancel(playTimer);
            scope.onOff.showPlayButton = false;
            if (!video[0].paused) {

              video[0].pause()
            } else {
              video[0].play()
              var playTimer = $timeout(function() {
                scope.onOff.showControl = false;
              }, 500)
            }
          }, videoPlay);
          $ionicGesture.on("tap", function() {

            scope.$apply(function() {
              scope.onOff.showControl = !scope.onOff.showControl;
              //  var progressWidth = angular.element(document.querySelector(".videoProgress"))[0].clientWidth;
              currentPosition[0].style.left = (scope.currentTime / scope.endTime) * progressWidth - 6 + "px";

            })

          }, video)
        }
      }
    }])
