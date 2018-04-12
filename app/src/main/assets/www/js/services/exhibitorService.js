angular.module("exhibitorService", [])
  .factory("exhibitorData", ['mainRequest', function(mainRequest) {
    return {
      //展商介绍
      getExhibitorDetile: function(param) {
        return mainRequest.request(param, "exhibitorProject.do", "getExhibitorDetile")
      },
      //展品介绍
      getExhibitProductList: function(param) {
        return mainRequest.request(param, "exhibitProductAction.do", "getExhibitProductList")
      },
      //关注展商
      getExhibitAttention: function(exhibitorid) {
        var param = {
          "exhibitorid": exhibitorid,
          "type":1
        }
        return mainRequest.request(param, "exhibitorProject.do", "followExhibitor")
      },
      //取消关注
      cancelExhibitorAttention: function(exhibitorid) {
        var param = {
          "exhibitorid": exhibitorid,
          "type":1
        }
        return mainRequest.request(param, "exhibitorProject.do", "cancelExhibitor")
      },
      //展品详情
      getExhibitProductDetail: function(param) {
        return mainRequest.request(param, "exhibitProductAction.do", "getExhibitProductDetail")
      },
      //展商地图
      getExhibitorDetileMap: function(param) {
        return mainRequest.request(param, "exhibitorProject.do", "getExhibitorDetileMap")
      },
      //添加参观计划
      addVisitPlant:function(){
        var param = {
          "exhibitorid": exhibitorid,
          "type":2
        }
        return mainRequest.request(param, "exhibitorProject.do", "followExhibitor")
      }
    }
  }])
