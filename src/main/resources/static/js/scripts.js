String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

$(".submit-write button[type=submit]").click(addAnswer);
function addAnswer(e) {

  e.preventDefault();

  var queryString = $(".submit-write textarea[name='contents']").serialize();
  var url = window.location.pathname + '/reply';
  console.log("url : " + url);

  $.ajax({
    type : 'post',
    url : url,
    data : queryString,
    dataType : 'json',
    error: function () {
      alert("error");
    },
    success : function (data) {
      console.log(data);
      var answerTemplate = $("#answerTemplate").html();
      var template = answerTemplate.format(data.userId, data.createdTime, data.contents, data.articleId, data.id);
      $(".qna-comment-slipp-articles").prepend(template);
      $("textarea[name=contents]").val("");

      // $(".delete-answer-form button[type=button]").last().on("click", deleteAnswer());
      $(".qna-comment-slipp-articles").on("click", ".delete-answer-form button[type=button]", deleteAnswer);

    },
  });
}


$(".delete-answer-form button[type=button]").click(deleteAnswer);
function deleteAnswer(e) {

  e.preventDefault();

  var replyId = e.target.dataset['replyid'];
  var url = window.location.pathname + '/reply/' + replyId;

  console.log("id: "+replyId);
  console.log("url: "+url);

  var $article = $(this).closest("article");

  $.ajax({
    type: "DELETE",
    url: url,
    dataType: "json",
  }).done((data) => {
      alert("댓글이 삭제되었습니다.")
      $article.remove();

  }).fail((error) => {
    alert("댓글을 삭제할 권한이 없습니다.");
    console.log(JSON.stringify(error));
  })
}
