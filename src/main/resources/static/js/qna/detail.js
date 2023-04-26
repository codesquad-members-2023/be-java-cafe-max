$(document).ready(function () {
  $("#form").submit(async function (e) {
    e.preventDefault()

    const id = $("#id").val()

    $.ajax({
      type: "DELETE",
      url: `/qna/${id}`,
    }).done(function (resp) {
      alert(`${resp.title} 게시글을 삭제하였습니다.`)
      location.href = `/`
    }).fail(function (response) {
      const errorResponse = response.responseJSON
      alert(errorResponse.errorMessage)
    })
  })

  $("#addCommentForm").submit(async function (e) {
    e.preventDefault()

    const id = $("#id").val()

    const data = {
      content: $("#addCommentForm #content").val(),
      questionId: $("#questionId").val(),
      userId: $("#userId").val()
    }

    $.ajax({
      type: "POST",
      url: `/qna/${id}/comments`,
      data: JSON.stringify(data),
      contentType: 'application/json; charset=utf-8'
    }).done(function (resp) {
      const commentTemplate = $("#answerTemplate").html()
      const template = commentTemplate.format(
          resp.writerName,
          formatDateTime(resp.createTime),
          resp.content,
          resp.questionId,
          resp.id)

      $(".qna-comment-slipp-articles").append(template);
      $("#content").val("");
    }).fail(function (response) {
      const errorResponse = response.responseJSON
      alert(errorResponse.errorMessage)
    })
  })

  function formatDateTime(dateTimeString) {
    const date = new Date(dateTimeString);

    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const day = String(date.getDate()).padStart(2, "0");
    const hours = String(date.getHours()).padStart(2, "0");
    const minutes = String(date.getMinutes()).padStart(2, "0");

    return `${year}-${month}-${day} ${hours}:${minutes}`
  }
})
