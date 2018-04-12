angular.module("homeService", [])
  .factory("homeData", ['mainRequest', function(mainRequest) {
    return {
      //热门展商
      getHotData: function() {
        var param = {};
        return mainRequest.request(param, "exhibitorProject.do", "getExhibitorHotList")
      },
      //展区
      getExhibitorAreaList: function() {
        var param = {};
        return mainRequest.request(param, "exhibitorProject.do", "getExhibitorAreaList")
      },
      //行业
      getExhibitorIndustryList: function(param) {
        //不传param获取全部行业
        if (!param) {
          var param = {};
          param.areaid = "";
        }
        return mainRequest.request(param, "exhibitorProject.do", "getExhibitorIndustryList")
      },
      //展商列表
      getExhibitList: function(param) {
        if (!param) {
          var param = {
            "areaid": "",
            "industryid": "",
            "content": ""
          };
        };
        param.content = "";
        return mainRequest.request(param, "exhibitProductAction.do", "getExhibitList")
      },
      getExhibitSelectList: function(content) {
        if (!param) {
          var param = {
            "content": content
          };
        };
        return mainRequest.request(param, "exhibitProductAction.do", "getExhibitList")
      }
    }
  }])
