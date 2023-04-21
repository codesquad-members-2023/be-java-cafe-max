// String.prototype.format = function() {
//   var args = arguments;
//   return this.replace(/{(\d+)}/g, function(match, number) {
//     return typeof args[number] != 'undefined'
//         ? args[number]
//         : match
//         ;
//   });
// };

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
      getInfo();
    }
  })
}

function getInfo(){
  const index = window.location.pathname.split("/")[3];
  let nickname = $("#hidden").val()
  $.ajax({
    url:"/article/getReply/"+index,
    method : "GET",
    success : function(result){
      let answerTemplate = $("#answerTemplate").text();
      let template = "";
      for(let i = 0 ; i<result.length; i++){
      template +=  '<article class="article re">'+
        '	<div class="article-header">'+
        '		<div class="article-header-thumb">'+
        '			<img src="https://graph.facebook.com/v2.3/1324855987/picture" class="article-author-thumb" alt="">'+
        '		</div>'+
        '		<div class="article-header-text">'+
        '			<a href="#" class="article-author-name">'+result[i]["replyWriter"]+'</a>'+
        '			<div class="article-header-time">'+result[i]["date"]+'</div>'+
        '		</div>'+
        '	</div>'+
        '	<div class="article-doc comment-doc">'+
        result[i]["replyContents"]+
        '	</div>'+
        '	<div class="article-util">';
        if(nickname==result[i]["replyWriter"]){
          template +=
              '           <ul class="article-util-list">'+
              '               <li>'+
              '                   <a class="link-modify-article" href="/api/qna/updateAnswer/'+result[i]["articleIdx"]+'">수정</a>'+
              '               </li>'+
              '               <li>'+
              '                   <form class="delete-answer-form" action="/api/questions/'+result[i]["articleIdx"]+'/answers/'+result[i]["index"]+'" method="POST">'+
              '                       <input type="hidden" name="_method" value="DELETE">'+
              '                       <button type="submit" class="delete-answer-button">삭제</button>'+
              '                   </form>'+
              '               </li>'+
              '           </ul>';
        }
          template +=
            '	</div>'+
            '</article>';
      }
      $(".qna-comment-slipp-articles").html(template);
      $("textarea[name=contents]").val("");

    }
  })
}


// function getInfo(){
//   const index = window.location.pathname.split("/")[3];
//   let nickname = $("#hidden").val()
//   $.ajax({
//     url:"/article/getReply/"+index,
//     method : "GET",
//     success : function(result){
//       let answerTemplate = $("#answerTemplate").text();
//       let template = "";
//       for(let i = 0 ; i<result.length; i++){
//         template += answerTemplate.format(result[i]["replyWriter"], result[i]["date"], result[i]["replyContents"], result[i]["articleIdx"], result[i]["index"]);
//       }
//       $(".qna-comment-slipp-articles").html(template);
//       $("textarea[name=contents]").val("");
//
//     }
//   })
// }