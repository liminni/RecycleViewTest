angular.module('starter.services', [])
  .factory("mainRequest", ["$http", "$httpParamSerializer",'$rootScope', "CONFIG", function($http, $httpParamSerializer,$rootScope, CONFIG) {
    return {
      request: function(params, types, method,isloading) {
        console.log(typeof(isloading));
        if(typeof(isloading)=="undefined"|| isloading){
            $rootScope.$broadcast('loading.show')
        }
        params.userid = localStorage['HZ_userid'];
        var requestURL = CONFIG.CONNECTION_TYPE + "://" + CONFIG.IP + ":" + CONFIG.PORT + "/" + CONFIG.CONTEXT_URL + "/" + types + "?method=" + method;

        var promise = $http({
          method: "post",
          url: requestURL,
          params: params,
          headers: {
            "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8"
          },
          timeout: 30000,
          cache: false
        }).success(function(data, status, headers, config) {
            $rootScope.$broadcast('loading.hide')
        }).error(function(data, status, headers, config) {
            $rootScope.$broadcast('loading.hide')
        });
        return promise;
      }
    }
  }])
