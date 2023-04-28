let comments = 0;
const commentSize = 15;
let commentLastIndex = 0;
showComments();
moreButton();
$("#comment-write").click(addComment);
$(".more-button").click(showComments);
$(document).on("click", "form[name=comment-delete-form]", deleteComment);

function addComment() {
      var queryString = $("form[name=comment-form]").serialize();

      var url = $("#comment").attr("action");
      commentLastIndex = commentSize;

      $.ajax({
          type : 'post',
          url : url,
          async : false,
          data : queryString,
          dataType : 'json',
          error: function () {
              alert("error");
          },
          success : function (data, status) {
              commentsSize();
              moreButton();
              $('#comment_count').text(comments);

              var template = format(data);
              $(".comment-box").html(template);

              $("textarea[name=comment]").val("");
          }
      });
}

function createComment(comment) {
    let button = "";
    const nickname = $("input[name=author]").val();
    if(nickname == comment.author) {
        button += "<form name='comment-update-form' method='post' action='/comments/" + comment.articleIndex + "/" + comment.commentIndex + "'>"
                + "<input type='hidden' name='_method' value='PATCH'/>"
                + "<button class='updateComment button next-writing' type='button' style='border: none;'>수정</button></form>"
                + "<form name='comment-delete-form' method='post' action='/comments/" + comment.articleIndex + "/" + comment.commentIndex + "'>"
                + "<input type='hidden' name='_method' value='DELETE'/>"
                + "<button class='deleteComment button next-writing' type='button' style='border: none;'>삭제</button></form>";
    }

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
    const articleIndex = window.location.pathname.split("/")[2];
    url = "/comments/" + articleIndex + "/" + commentLastIndex;
    if(comments >= commentLastIndex) {
        commentLastIndex += commentSize;
    }
    $.ajax({
          type : 'get',
          url : url,
          async : false,
          error: function () {
              alert("error");
          },
          success : function (data) {
              commentsSize();
              moreButton();
              $('#comment_count').text(comments);

              var template = format(data);
              $(".comment-box").append(template);
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

function deleteComment() {
    var url = $(this).attr("action");
    commentLastIndex = commentSize;

    $.ajax({
      type : 'delete',
      url : url,
      async : false,
      error: function () {
          alert("다른 사람의 댓글은 삭제할 수 없습니다.");
      },
      success : function (data, status) {
          commentsSize();
          moreButton();
          $('#comment_count').text(comments);

          var template = format(data);
          $(".comment-box").html(template);

          $("textarea[name=comment]").val("");
      }
    });
}

function commentsSize() {
    const articleIndex = window.location.pathname.split("/")[2];
    url = "/comments/size/" + articleIndex;
    $.ajax({
          type : 'get',
          url : url,
          async : false,
          error: function () {
              alert("error");
          },
          success(result) {
              comments = result;
          }
    });
}

function moreButton() {
    if(comments <= commentLastIndex) {
        $('.more-button').css('display', 'none');
    }else {
        $('.more-button').css('display', 'block');
    }
}
