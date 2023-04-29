
let start = 0;
const index = window.location.pathname.split("/")[3];
let nickname = $("#hidden").val();
let count = 0;


function createArticleTemplate(reply) {
  $(".qna-comment-count").html(count+"개 의견");
  let template = '<article class="article re">'+
      '  <div class="article-header">'+
      '    <div class="article-header-thumb">'+
      '      <img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">'+
      '    </div>'+
      '    <div class="article-header-text">'+
      '      <a href="#" class="article-author-name">'+ reply["replyWriter"] +'</a>'+
      '      <div class="article-header-time">'+ reply["date"] +'</div>'+
      '    </div>'+
      '  </div>'+
      '  <div class="article-doc comment-doc">'+ reply["replyContents"] +'</div>'+
      '  <div class="article-util">';
  if(nickname == reply["replyWriter"]) {
    template += '<ul class="article-util-list">'+
        '  <li>'+
        '    <form class="delete-answer-form" action="/article/reply/'+ reply["index"] +'" method="POST">'+
        '      <input type="hidden" name="_method" value="DELETE">'+
        '      <button type="button" class="delete-answer-button">삭제</button>'+
        '    </form>'+
        '  </li>'+
        '</ul>';
  }
  template += '</div>'+
      '</article>';
  return template;
}
// let replyCount = $("#replyCount").val();
getInfo()
$(".submit-write button").click(writeReply);
function writeReply() {
  const form = $(".submit-write").serialize();
  const index = window.location.pathname.split("/")[3];
  $.ajax({
    url:"/article/"+index+"/reply",
    method : "POST",
    data : form,
    success : function(){
      start = 0;
      getInfo();
    }
  })
}

function getInfo(){
  getReplyCount()
  $.ajax({
    url:"/article/reply/"+index,
    method : "GET",
    data : {"start" : start},
    success : function(result){
      let template = "";
      for(let i = 0 ; i<result.length; i++){
        template += createArticleTemplate(result[i]);
      }
      if(count > 5) {
        template += '<button id="more">더보기</button>';
      }
      $(".qna-comment-slipp-articles").html(template);
      $("textarea[name=contents]").val("");
    }
  })
}

$(document).on("click", ".delete-answer-button", deleteReply)
  function deleteReply() {
    const url = $(this).parent().attr("action");
    $.ajax({
      url: url,
      type: 'delete',
      success: function(result) {
        if (result) {
          start = 0;
          getInfo();
          return;
        }
        alert("삭제 실패 본인만 삭제 가능");
      }
    })
  }

$(document).on("click", "#more", more)

function more (){
  $("#more").remove();
  start += 5;
  $.ajax({
    url:"/article/reply/"+index,
    method : "GET",
    data : {"start" : start},
    success : function(result){
      let template = "";
      for(let i = 0 ; i<result.length; i++){
        template += createArticleTemplate(result[i]);
      }

        template += '<button id="more">더보기</button>';

      $(".qna-comment-slipp-articles").append(template);
      $("textarea[name=contents]").val("");

      if(start+5>=count) {
        start-=5;
        $("#more").remove();
      }
    }
  })

}

function getReplyCount() {
  $.ajax({
    url : "/getReplyCount/"+index,
    method:"get",
    success(result){
      count = result;
    }
  })
}

