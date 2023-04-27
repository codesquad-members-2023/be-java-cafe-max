$("#addReplyForm button[type=submit]").click(addAnswer);

function addAnswer(e) {
    e.preventDefault();

    var queryString = $("form[name=addReplyForm]").serialize();

    var url = $("#addReplyForm").attr("action");
    console.log("url : " + url);
    alert("url : " + url);

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
