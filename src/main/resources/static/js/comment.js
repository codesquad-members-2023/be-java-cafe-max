String.prototype.format = function() {
  var args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

$(".submit-write button[type=submit]").on("click", addComment);

function addComment(e) {
    e.preventDefault();
    const writeForm = $(".submit-write");
    const queryString = writeForm.serialize();
    console.log("query : " + queryString);

    const url = writeForm.attr("action")
    console.log("url : " + url);

    $.ajax({
        type : "post",
        url : url,
        data : queryString,
        dataType : "json",
        error : function() {
            console.log("failure");
        },
        success : function(data, status) {
            console.log(data);
            const commentTemplate = $("#answerTemplate").html();
            const template = commentTemplate.format(data.writerId, data.writer, data.writeDateTime, data.contents, data.postId, data.commentId);
            $(".qna-comment-slipp-articles").append(template);
            $("textarea[name=contents]").val("");

            const commentCountTemplate = $(".qna-comment-count strong").text();
            const commentCount = Number(commentCountTemplate) + 1;
            $(".qna-comment-count strong").text(commentCount);
        }
    });
}

$(".qna-comment-slipp-articles").on("click", ".delete-answer-form button[type=button]", deleteComment);

function deleteComment(e) {
    e.preventDefault();

    const deleteBtn = $(this);
    const url = deleteBtn.parent().attr("action")
    console.log(url);
    $.ajax({
        type : "delete",
        url : url,
        error : function(xhr, status) {
            alert(xhr.responseText);
        },
        success : function(data, status) {
            deleteBtn.closest("article").remove();

            const commentCountTemplate = $(".qna-comment-count strong").text();
            const commentCount = Number(commentCountTemplate) - 1;
            $(".qna-comment-count strong").text(commentCount);
        }
    });
}
