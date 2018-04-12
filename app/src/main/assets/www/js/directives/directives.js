angular.module("starter.directive", [])
  .directive("autoHeight", function($window) {
    return {
      link: function(scope, element, attr) {
        element[0].style.height = ($window.innerHeight - 88) + 'px';
      }
    }
  })
  .directive("autoWidth", function($window) {
    return {
      link: function(scope, element, attr) {
        element[0].style.width = ($window.innerHeight * 0.95) + 'px';
        element[0].style.maxWidth = "355px";
        console.log(element[0].style.maxWidth);
      }
    }
  })
  .directive('autoFocus', function($timeout, $parse) {
    return {
      scope: {
        searchInput: "="
      },
      link: function(scope, element, attrs) {
        if (scope.searchInput.autofocus) {
          $timeout(function() {
            element[0].focus();
          });
        }
        element.bind('blur', function() {
          console.log('blur');
          scope.searchInput.autofocus = false

        });
      }
    };
  })
  .directive('fixedHeader', function() {
    return {
      link: function(scope, element, attrs) {
        var header = element[0].parentElement.parentElement.parentElement.children[0];
        element.bind('focus', function() {
          header.style.position = "fixed";
        });
        element.bind('blur', function() {
          header.style.position = "relative";
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

  .directive("navMove", function($ionicScrollDelegate, $ionicGesture, $interval, $rootScope) {
    return {
      link: function(scope, ele, attr) {
        var onOff = "";
        var srcollTop;
        var headertop = $rootScope.is_iphone ? 64 : 44;
        var nav_boxTop = angular.element(document.querySelector(".nav_box"))[0].clientHeight + 8;

        var srcollTops = nav_boxTop - headertop;
        $ionicGesture.on("dragdown", function(event) {
          onOff = "down";
          srcollTop = ele[0].childNodes[0].style.cssText.split(",")[1];
          srcollTop = srcollTop.substring(0, srcollTop.length - 2);
          if (ele[0].parentElement.parentElement.style.top == "") {
            return
          } else {
            if (parseFloat(ele[0].parentElement.parentElement.style.top) >= headertop) {
              ele[0].parentElement.parentElement.style.top = headertop + "px";
            } else {
              if (srcollTop >= 0) {

                if (event.gesture.deltaY >= nav_boxTop) {
                  event.gesture.deltaY = nav_boxTop
                }
                ele[0].parentElement.parentElement.style.top = -srcollTops + event.gesture.deltaY + "px";
                if (parseFloat(ele[0].parentElement.parentElement.style.top) < -srcollTops) {
                  ele[0].parentElement.parentElement.style.top = -srcollTops + "px"
                }
              }
            }
          }
        }, ele)
        $ionicGesture.on("dragup", function(event) {
          onOff = "up";
          srcollTop = ele[0].childNodes[0].style.cssText.split(",")[1];
          srcollTop = srcollTop.substring(0, srcollTop.length - 2);
          if (ele[0].parentElement.parentElement.style.top == (-srcollTops + "px") || event.gesture.deltaY <= -nav_boxTop) {
            ele[0].parentElement.parentElement.style.top = -srcollTops + "px";
          } else {
            //  ele[0].childNodes[0].style.transform = "translate3d(0px, 0px, 0px) scale(1)";
            ele[0].parentElement.parentElement.style.top = headertop + event.gesture.deltaY + "px";
            if (parseFloat(ele[0].parentElement.parentElement.style.top) > headertop) {
              ele[0].parentElement.parentElement.style.top = headertop + "px"
            }
          }
        }, ele)

        $ionicGesture.on("release", function() {
          var top = ele[0].parentElement.parentElement.style.top;
          if (top) {
            top = parseFloat(top) < headertop && parseFloat(top) > -srcollTops
          } else {
            top = false
          }
          if (onOff == "down" && top) {
            //nav出现在屏幕一半以上则下滑
            var navTop = parseFloat(ele[0].parentElement.parentElement.style.top);

            if (navTop > -30) {
              var downTimer = $interval(function() {
                if (parseFloat(ele[0].parentElement.parentElement.style.top) >= headertop) {
                  ele[0].parentElement.parentElement.style.top = headertop + "px";
                  //  ele[0].childNodes[0].style.transform = "translate3d(0px, 0px, 0px) scale(1)";
                  $interval.cancel(downTimer);
                  return;
                }
                ele[0].parentElement.parentElement.style.top = parseFloat(ele[0].parentElement.parentElement.style.top) + 20 + "px"
              }, 30);
            } else {
              var downTimer = $interval(function() {
                if (parseFloat(ele[0].parentElement.parentElement.style.top) <= -srcollTops) {
                  ele[0].parentElement.parentElement.style.top = -srcollTops + "px";
                  //  ele[0].childNodes[0].style.transform = "translate3d(0px, 0px, 0px) scale(1)";
                  $interval.cancel(downTimer);
                  return;
                }
                ele[0].parentElement.parentElement.style.top = parseFloat(ele[0].parentElement.parentElement.style.top) - 20 + "px"
              }, 30);
            }
          } else if (onOff == "up" && top) {
            var upTimer = $interval(function() {
              if (parseFloat(ele[0].parentElement.parentElement.style.top) <= -104) {

                ele[0].parentElement.parentElement.style.top = -srcollTops + "px";
                $interval.cancel(upTimer);
                return;
              }
              ele[0].parentElement.parentElement.style.top = parseFloat(ele[0].parentElement.parentElement.style.top) - 20 + "px"
            }, 30);
          }
        }, ele)
      }
    }
  })
  .directive('closeMyPopup', ['$ionicGesture', '$rootScope', function($ionicGesture, $rootScope) {
    return {
      scope: false,
      replace: false,
      link: function(scope, element, attrs) {
        var $htmlEl = angular.element(document.querySelector('html'));
        $ionicGesture.on("touch", function(event) {
          if (event.target.nodeName === "HTML" && scope.myPopup.popupIsShow) {
            scope.optionsPopup.close();
            scope.myPopup.popupIsShow = false;
          }
        }, $htmlEl);
      }
    };
  }])

  .directive('myVideo', ['$ionicGesture', '$sce', '$timeout', '$window', function($ionicGesture, $sce, $timeout, $window) {
    return {
      scope: false,
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
        video[0].src = $sce.trustAsResourceUrl(scope.videoInfo.src);
        video[0].poster = scope.videoInfo.posterImg;
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
  //图片懒加载
  .directive('lazyScroll', ['$rootScope', '$timeout',
    function($rootScope, $timeout) {
      return {
        restrict: 'A',
        link: function($scope, $element) {
          var scrollTimeoutId = 0;
          $scope.invoke = function() {
            $rootScope.$broadcast('lazyScrollEvent');
          };
          $element.bind('scroll', function() {

            $timeout.cancel(scrollTimeoutId);
            scrollTimeoutId = $timeout($scope.invoke, 0);
          });
        }
      };
    }
  ])
  .directive('imageLazySrc', ['$document', '$timeout', '$ionicScrollDelegate', '$compile',
    function($document, $timeout, $ionicScrollDelegate, $compile) {
      return {
        restrict: 'A',
        scope: {
          lazyScrollResize: "@lazyScrollResize"
        },
        link: function($scope, $element, $attributes) {
          if (!$attributes.imageLazyDistanceFromBottomToLoad) {
            $attributes.imageLazyDistanceFromBottomToLoad = 0;
          }
          if (!$attributes.imageLazyDistanceFromRightToLoad) {
            $attributes.imageLazyDistanceFromRightToLoad = 0;
          }
          if ($attributes.imageLazyLoader) {
            var loader = $compile('<div class="image-loader-container"><ion-spinner class="image-loader" icon="' + $attributes.imageLazyLoader + '"></ion-spinner></div>')($scope);
            $element.after(loader);
          }

          var deregistration = $scope.$on('lazyScrollEvent', function() {
            //console.log('scroll');

            if (isInView()) {
              loadImage();
              deregistration();
            }
          });

          function loadImage() {
            $element.bind("load", function(e) {
              if ($attributes.imageLazyLoader) {
                loader.remove();
              }
              if ($scope.lazyScrollResize == "true") {
                //Call the resize to recalculate the size of the screen
                $ionicScrollDelegate.resize();
              }
            });
            if ($scope.imageLazyBackgroundImage == "true") {
              var bgImg = new Image();
              bgImg.onload = function() {
                if ($attributes.imageLazyLoader) {
                  loader.remove();
                }
                $element[0].style.backgroundImage = 'url(' + $attributes.imageLazySrc + ')'; // set style attribute on element (it will load image)
                if ($scope.lazyScrollResize == "true") {
                  //Call the resize to recalculate the size of the screen
                  $ionicScrollDelegate.resize();
                }
              };
              bgImg.src = $attributes.imageLazySrc;
            } else {
              $element[0].src = $attributes.imageLazySrc; // set src attribute on element (it will load image)
            }
          }

          function isInView() {
            var clientHeight = $document[0].documentElement.clientHeight;
            var clientWidth = $document[0].documentElement.clientWidth;
            var imageRect = $element[0].getBoundingClientRect();
            return (imageRect.top >= 0 && imageRect.top <= clientHeight + parseInt($attributes.imageLazyDistanceFromBottomToLoad)) &&
              (imageRect.left >= 0 && imageRect.left <= clientWidth + parseInt($attributes.imageLazyDistanceFromRightToLoad));
          }
          $element.on('$destroy', function() {
            deregistration();
          });
          $timeout(function() {
            if (isInView()) {
              loadImage();
              deregistration();
            }
          }, 500);
        }
      };
    }
  ])
