let comments = 0;
const commentSize = 15;
let commentLastIndex = 0;
showComments();
moreButton();
$(function(){
    $("#comment-write").click(addComment);
    $(".more-button").click(showComments);
    $(document).on("click", "form[name=comment-delete-form]", deleteComment);
    $(document).on("click", ".updateComment", showUpdateComment);
});

function addComment() {
      var queryString = $("form[name=comment-form]").serialize();

      var url = $("#comment").attr("action");
      commentLastIndex = 0;

      $.ajax({
          type : 'post',
          url : url,
          async : false,
          data : queryString,
          dataType : 'json',
          error: function () {
              alert("error");
          },
          success : function () {
              $(".comment-detail").remove();
              showComments();
          }
      });
}

function createComment(comment) {
    let button = "";
    const nickname = $("input[name=author]").val();
    if(nickname == comment.author) {
        button += "<a class='updateComment button next-writing' style='border: none;'>수정</a>"
                + "<form name='comment-delete-form' method='post' action='/comments/" + comment.commentIndex + "'>"
                + "<input type='hidden' name='_method' value='DELETE'/>"
                + "<button class='deleteComment button next-writing' type='button' style='border: none;'>삭제</button></form>";
    }

    var content = "<div class='comment-detail'>"
                    + "<div class='comment-in-wrap'>"
                               + "<div class='comment-nickname'>" + comment.author + "</div>"
                               + "<div class='comment'>" + comment.comment + "</div>"
                               + "<div class='comment-date'>" + comment.createdDate + "</div>"
                               + "<input type='hidden' name='commentIndex' value='" + comment.commentIndex + "'/>"
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
    console.log(url);
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

              $("textarea[name=comment]").val("");
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
    commentLastIndex = 0;

    $.ajax({
      type : 'delete',
      url : url,
      async : false,
      error: function () {
          alert("다른 사람의 댓글은 삭제할 수 없습니다.");
      },
      success : function () {
          $(".comment-detail").remove();
          showComments();
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

function showUpdateComment() {
    console.log($(this).parent().prev().children('input[name=commentIndex]').val());
    let update = "<div class='write-comment'>"
        + "<form name='comment-update-form' id='comment' method='post' action='/comments/" + $(this).parent().prev().children('input[name=commentIndex]').val() + "'>"
        + "<input type='hidden' name='_method' value='PATCH'/>"
        + "<label for='comment-input' id='comment-label'></label>"
        + "<textarea name='comment' id='comment-input'>" + $(this).parent().prev().children('.comment').text() + "</textarea>"
        + "<button type='button' class='button submit comment-update'>수정</button></form></div>";

    $(this).parent().parent().html(update);
    $(document).on("click", ".comment-update", updateComment);
}

function updateComment() {
    console.log($(this).parent().attr("action"));
    var queryString = $(this).parent().serialize();

    var url = $(this).parent().attr("action");
    commentLastIndex = 0;

    $.ajax({
        type : 'patch',
        url : url,
        async : false,
        data : queryString,
        dataType : 'json',
        error: function () {
            alert("다른 사람의 댓글은 수정할 수 없습니다.");
        },
        success : function () {
            $(".comment-detail").remove();
            showComments();
        }
    });
}
