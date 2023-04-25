$(".submit-write button[type=submit]").click(addComment);

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
            const template = `${commentTemplate.replace('{0}', data.writerId)
                                                .replace('{1}', data.writer)
                                                .replace('{2}', data.writeDateTime)
                                                .replace('{3}', data.contents)
                                                .replace('{4}', data.postId)
                                                .replace('{5}', data.commentId)}`;
            $(".qna-comment-slipp-articles").append(template);
            $("textarea[name=contents]").val("");
        }
    });
}
