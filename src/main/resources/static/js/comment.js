const main = {
    init: function () {
        $('#comment-btn-save').on('click', function () {
            commentSave();
        });
        commentDelete();
    }
}

function commentSave() {
    const data = {
        articleId: $('#article-id').val(),
        content: $('#comment-content').val()
    };

    $.ajax({
        type: 'POST',
        url: '/comments',
        dataType: 'json',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify(data)
    }).done(function (comment) {
        console.log(comment);
        const answerTemplate = $("#answerTemplate").html();
        const template = answerTemplate.format(comment.writer, comment.createdAt, comment.content, comment.id);

        $(".qna-comment-slipp-articles").append(template);
        $("#comment-content").val("");
    }).fail(function (error) {
        alert('댓글 등록에 실패했습니다!');
    });
}

function commentDelete() {
    $(".delete-answer-form button").on('click', function () {
        const form = $(this).parent();
        const deleteUrl = form.attr('action');

        $.ajax({
            type: 'DELETE',
            url: deleteUrl,
            dataType: 'json',
            contentType: 'application/json charset=utf-8'
        }).done(function () {
            window.location.reload();
        }).fail(function () {
            alert('댓글 삭제에 실패했습니다.');
        });
    });
}

main.init();
