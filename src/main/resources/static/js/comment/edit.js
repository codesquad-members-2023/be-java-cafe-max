$(document).ready(function () {
  $("#form").submit(async function (e) {
    e.preventDefault()

    const commentId = $("#commentId").val()
    const questionId = $("#questionId").val()
    const userId = $("#userId").val()
    const content = $("#content").val()
    const urlPath = `/qna/${questionId}/comments/${commentId}`

    const data = {
      id: commentId,
      content: content,
      questionId: questionId,
      userId: userId
    }

    $.ajax({
      type: "PUT",
      url: urlPath,
      data: JSON.stringify(data),
      contentType: 'application/json; charset=utf-8'
    }).done(function (resp) {
      location.href = `/qna/${questionId}`
    }).fail(function (response) {
      const errorResponse = response.responseJSON
      // 입력 형식 오류
      if (errorResponse.name === 'INVALID_INPUT_FORMAT') {
        errorResponse.errors.forEach(item => {
          $(`#${item.field}Error`).text(item.message)
        })
      }
    })
  })
})
