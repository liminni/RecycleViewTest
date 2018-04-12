angular.module("attentionService", [])
  .factory("attentionData", ['mainRequest', function(mainRequest) {
    return {
    	attentionList: function(userid) {
        var param = {
          "userid": userid
        }
        return mainRequest.request(param, "exhibitorProject.do","getFollowExhibitorList")
      }
      
    }
  }])
