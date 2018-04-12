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
