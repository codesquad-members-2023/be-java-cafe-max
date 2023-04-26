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
          resp.createTime,
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

  $("#deleteCommentForm").submit(async function (e) {
    e.preventDefault()

    const id = $("#id").val()

    $.ajax({
      type: "DELETE",
      url: `/qna/${id}/comments`
    }).done(function (resp) {
      alert("댓글이 삭제되었습니다.")
    }).fail(function (response) {
      const errorResponse = response.responseJSON
      alert(errorResponse.errorMessage)
    })
  })
})
