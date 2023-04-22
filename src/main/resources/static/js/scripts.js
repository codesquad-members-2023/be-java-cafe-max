String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};

$('#post-reply').click(() => {
    var queryString = $(".submit-write textarea[name=replyContent]").serialize();
    var url = window.location.pathname + '/replies';
    var articleId = window.location.pathname.split('questions/')[1];

    $.ajax({
        type: 'POST',
        url: url,
        data: queryString,
        dataType: 'json',
    }).done((db) => {
        console.log(db);
        var answerTemplate = $("#answerTemplate").html();
        var template = answerTemplate.format(db.userId, db.replyTime, db.replyContent, articleId, db.id);

        $(".qna-comment-slipp-articles").append(template);
        $("textarea[name=replyContent]").val("");
    }).fail((err) => {
        alert(JSON.stringify(err));
    });
});