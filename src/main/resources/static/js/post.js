function write(e) {
    console.log('asdfasd');
    e.preventDefault();
    e.stopPropagation();

    const formSerializeArray = $('.comment-submit').serializeArray();
    const  object = {};

    for (let i = 0; i < formSerializeArray.length; i++){
        object[formSerializeArray[i]['name']] = formSerializeArray[i]['value'];
    }

    $.ajax({
        type : 'post',
        url : $(".comment-submit").attr("action"),
        data : JSON.stringify(object),
        dataType : 'json',
        contentType : 'application/json; charset=UTF-8',
        error: function () {
            alert("에러가 발생하였습니다.");
        },
        success : function(data, status) {
            const text = $("#comments-size").text();
            const contentsSize = Number(text.replace(/[^0-9]/g, "")) + 1;
            $("#comments-size").text(`댓글 ${contentsSize}개`);
            $(".comments").append(addComment(data));
            $("#content").val('');
        },
    });
}

function addComment(data) {
    return `<div class="comment">
                <strong class="comment-writer">${data.writer.nickname}</strong>
                <pre class="comment-content">${data.content}</pre>
                <span class="comment-date">${data.writeDate}</span>
            </div>`;
}

document.addEventListener("DOMContentLoaded", function () {
    document.querySelector(".comment-submit button[type='submit']").addEventListener('click', write);
});
