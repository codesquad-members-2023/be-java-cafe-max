$("#addReplyForm button[type=submit]").click(addComment);
$("#moreCommentsBtn").on("click", addMoreComment);

function addComment(e) {
    e.preventDefault();

    var queryString = $("form[name=addReplyForm]").serialize();

    var url = $("#addReplyForm").attr("action");
    console.log("url : " + url);

    $.ajax({
        type: 'post',
        url: url,
        data: queryString,
        dataType: 'json',
        error: function () {
            console.log('failure');
        },
        success: function (data, status) {
            console.log(data);
            var answerTemplate = $("#answerTemplate").html();
            var template = answerTemplate.format(data.writer, data.registrationDateTime, data.content, data.post_id, data.id);
            $(".qna-comment-slipp-articles").prepend(template);
            $("textarea[name=content]").val("");
        }
    });
}

function addMoreComment(e) {

    let cursor = $("div.qna-comment-slipp-articles article").last().attr("id");
    let queryString = {'cursor': cursor};
    console.log("cursor : " + cursor);

    let url = $("#addReplyForm").attr("action");
    console.log("url : " + url);

    $.ajax({
        type: 'get',
        url: url,
        data: queryString,
        dataType: 'json',
        error: function () {
            console.log('failure');
        },
        success: function (data, status) {
            console.log(data);

            for (let i = 0; i < data.length; i++) {
                var answerTemplate = $("#answerTemplate").html();
                var template = answerTemplate.format(data[i].writer, data[i].registrationDateTime, data[i].content, data[i].post_id, data[i].id);
                $("#moreCommentLine").append(template);
            }
        }
    });
}
