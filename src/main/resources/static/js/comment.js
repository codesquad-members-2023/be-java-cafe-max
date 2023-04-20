showComments();
$("#comment-write").click(addAnswer);

function addAnswer(e) {
      var queryString = $("form[name=comment-form]").serialize();

      var url = $("#comment").attr("action");

      $.ajax({
          type : 'post',
          url : url,
          data : queryString,
          dataType : 'json',
          error: function () {
              alert("error");
          },
          success : function (data, status) {
              $('#comment_count').text(data.length);

              var template = format(data);
              $(".comment-box").html(template);

              $("textarea[name=comment]").val("");
          }
      });
}

function createComment(comment) {
    let button = "<form name='question' method='post' action='/comment/update/" + comment.postIndex + "/" + comment.index + "'>"
        + "<input type='hidden' name='_method' value='PATCH'/>"
        + "<button class='button next-writing' type='button' style='border: none;'>수정</button>"
        + "<form name='question' method='post' action='/comment/delete/" + comment.postIndex + "/" + comment.index + "'>"
        + "<input type='hidden' name='_method' value='DELETE'/>"
        + "<button class='button next-writing' type='button' style='border: none;'>삭제</button>";

    var content = "<div class='comment-detail'>"
                    + "<div class='comment-in-wrap'>"
                               + "<div class='comment-nickname'>" + comment.author + "</div>"
                               + "<div class='comment'>" + comment.comment + "</div>"
                               + "<div class='comment-date'>" + comment.createdDate + "</div>"
                    + "</div>"
                    + "<div class='comment-menu'>"
                               + button
                    + "</div>"
                  + "</div>";
    return content;
}

function showComments() {
    const postIndex = window.location.pathname.split("/")[2];
    url = "/comments/" + postIndex;
    $.ajax({
          type : 'get',
          url : url,
          error: function () {
              alert("error");
          },
          success : function (data) {
              $('#comment_count').text(data.length);

              var template = format(data);
              $(".comment-box").html(template);
          }
      });
}

function format(data) {
    var comments = "";
    data.forEach(function(comment) {
        comments += createComment(comment);
    });
    return comments;
};
