let page = 1;

function commentTemplate(data) {
    return `<div class="comment">
                <strong class="comment-writer">${data.writer.nickname}</strong>
                <pre class="comment-content">${data.content}</pre>
                <span class="comment-date">${data.writeDateTime}</span>
                <form class="comment-delete" action="/api/posts/${data.postId}/comments/${data.id}">
                     <button class="btn btn-primary btn-sm" type="submit">삭제</button>
                </form>
            </div>`;
}

function updateCommentsSize(plus) {
    const text = $("#comments-size").text();
    const contentsSize = Number(text.replace(/[^0-9]/g, "")) + plus;
    $("#comments-size").text(`댓글 ${contentsSize}개`);
}

function errorAlert(request) {
    let messages = JSON.stringify(request.responseJSON.message, null, 2);
    alert(`에러 상태 코드 : ${request.responseJSON.statusCode}\n에러 상태 메세지 : ${request.responseJSON.status}\n에러 메시지 : ${messages}`);
}

function writeAjax(e) {
    e.preventDefault();
    e.stopPropagation();

    const formSerializeArray = $('.comment-submit').serializeArray();
    const object = {};

    for (let i = 0; i < formSerializeArray.length; i++){
        object[formSerializeArray[i]['name']] = formSerializeArray[i]['value'];
    }

    $.ajax({
        type : 'post',
        url : $(".comment-submit").attr("action"),
        data : JSON.stringify(object),
        dataType : 'json',
        contentType : 'application/json; charset=UTF-8',
        error: function (request, status, error) {
            errorAlert(request);
        },
        success : function(data, status) {
            updateCommentsSize(+1);
            $(".comments").append(commentTemplate(data));
            $("#content").val('');
        },
    });
}

function deleteAjax(e) {
    e.preventDefault();
    e.stopPropagation();

    const form = $(this).closest(".comment-delete");

    $.ajax({
        type: 'delete',
        url: form.attr("action"),
        dataType: 'json',
        contentType: 'application/json; charset=UTF-8',
        error: function (request, status, error) {
            errorAlert(request);
        },
        success: function (data, status) {
            if (data.deleted) {
                updateCommentsSize(-1);
                form.closest('.comment').remove();
                return;
            }

            alert("삭제가 되지 않았습니다.");
        },
    });
}

function seeMore(e) {
    e.preventDefault();
    e.stopPropagation();

    const url = $("#see_more").attr("href") + "?page=" + ++page;

    $.ajax({
        type : 'get',
        url : url,
        dataType : 'json',
        contentType : 'application/json; charset=UTF-8',
        error: function (request, status, error) {
            errorAlert(request);
        },
        success : function(data, status) {
            append(data);
        },
    });
}

function append(data) {
    for (let index = 0; index < data.commentResponses.length; index++) {
        $(".comments").append(commentTemplate(data.commentResponses[index]));
    }

    if (!data.next) {
        $("#see_more").parent().remove();
    }
}


document.addEventListener("DOMContentLoaded", function () {
    $(".comment-submit button[type='submit']").on("click", writeAjax);
    $(document).on("click", ".comment-delete button[type='submit']", deleteAjax);
    $(document).on("click", "#see_more", seeMore);
});
