// 삭제버튼
$("#comment-box").on("submit", ".reply-delete", handleDelete);

function handleDelete(e) {

    e.preventDefault();

    let deleteBtn = $(this);
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
            } else {
                alert(data.errorMessage);
            }
        },
    });
}