// 삭제버튼
$("#comment-box").on("submit", ".reply-delete", handleDelete);

function handleDelete(e) {

    e.preventDefault();

    const deleteBtn = $(this);
    const deleteUrl = deleteBtn.attr("action");

    $.ajax({
        type: 'delete',
        url: deleteUrl,
        dataType: "json",
        error: function () {
            console.log('failure');
        },
        success: function (data) {
            if (data.ok) {
                deleteBtn.closest(".comment").remove()
                decreaseCommentCount();
            } else {
                alert(data.errorMessage);
            }
        },
    });
}

function decreaseCommentCount() {
    const preCountOfReply = countOfReply.textContent;
    const postCountOfReply = parseInt(preCountOfReply.replace(/[^0-9]/g, "")) - 1;
    countOfReply.textContent = "댓글 " + postCountOfReply + "개";
}