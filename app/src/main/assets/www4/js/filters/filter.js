angular.module("netWork.filter", [])
  .filter('trustUrl', ['$sce', function($sce) {
    return function(text) {
      return $sce.trustAsResourceUrl(text);
    }
  }])
  .filter('trustHtml', ['$sce', function($sce) {
    return function(text) {
      return $sce.trustAsHtml(text);
    }
  }])
  .filter("videoTime", function() {
    return function(res) {
      function addZero(num) {
        if (parseInt(num) >= 10) {
          return num;
        } else {
          return "0" + num;
        }
      }
      var str, m, s;
      m = parseInt(res / 60);
      s = parseInt(res % 60);
      str = addZero(m) + ":" + addZero(s);

      return str;

    }
  })
