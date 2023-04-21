$("#commentForm").submit(function (event) {
    //submit 이 자동으로 동작하는 것을 막는다.
    event.preventDefault();
    addComment();
});


function addComment() {

    //form data들을 자동으로 묶어준다.
    var formData = $("#commentForm").serialize();
    var url = $("#commentForm").attr("action");

    $.ajax({
        type: 'post',
        url: url,
        data: formData,
        dataType: 'json',
        success: function (data) {
            // Create the new comment HTML
            var commentHtml = '<div class="d-flex mb-4">' +
                '<div class="flex-shrink-0"><img class="rounded-circle" src="https://dummyimage.com/50x50/ced4da/6c757d.jpg" alt="..."/></div>' +
                '<div class="ms-3">' +
                '<div class="fw-bold">' + data.userId + '</div>' +
                '<div class="text-muted fst-italic">' + data.createdTime + '</div>' +
                '<div class="comment-content">' + data.contents + '</div>' +
                '<form method="post" class="delete_button" action="/api/comments">' +
                '<input type="hidden" name="_method" value="DELETE"/>' +
                '<input type="hidden" name="commentId" value="' + data.commentId + '"/>' +
                '<input type="hidden" name="userId" value="' + data.userId + '"/>' +
                '<button type="submit" class="btn btn-danger">삭제</button>' +
                '</form>' +
                '</div>' +
                '</div>';

            // Add the new comment to the top of the comments section
            $('.card-body').prepend(commentHtml);

        },
        error: function () {
            alert('통신실패')
        },
    });

}
