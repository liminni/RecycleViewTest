angular.module('netWork.detailService', [])
  .factory('detailService', ['mainRequest', function(mainRequest) {
    return {
      detailData: function(id,type,userId) {
        var param = {
          "newId": id,
          "type": type,
          "userId":userId
        }
        console.log("请求参数==");
        console.log(param);
        return mainRequest.request(param)
      }
    }
  }])
