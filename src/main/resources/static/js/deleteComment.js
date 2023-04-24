$('.card-body').on('submit', '.delete_button', function(event) {
    event.preventDefault();
    deleteComment($(this));
});

function deleteComment(deleteForm) {

    var formData = deleteForm.serialize();
    var url = deleteForm.attr("action");

    $.ajax({
        type : 'delete',
        url : url,
        dataType : 'json',
        data: formData,
        success : function (data) {
            console.log("Comment deleted successfully");
            deleteForm.closest(".d-flex.mb-4").remove();
        },
        error : function () {
            alert('통신실패');
        },
    });
}
