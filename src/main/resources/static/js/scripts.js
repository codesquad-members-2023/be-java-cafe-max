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
    var replyCounter = $(".qna-comment-count strong");

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

        var replyCount = Number(replyCounter.text());
        replyCounter.text(replyCount + 1);
    }).fail((err) => {
        alert(JSON.stringify(err));
    });
});