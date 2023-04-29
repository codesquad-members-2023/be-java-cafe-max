$(document).ready(function () {
  $("#form").submit(async function (e) {
    e.preventDefault()
    await clearErrorMessage()

    const data = {
      writer: $("#writer").val(),
      title: $("#title").val(),
      content: $("#content").val(),
      userId: $("#userId").val()
    }

    const id = $("#questionId").val()

    $.ajax({
      type: 'put',
      url: `/qna/${id}`,
      data: JSON.stringify(data),
      contentType: 'application/json; charset=utf-8'
    }).done(function () {
      location.href = "/"
    }).fail(function (response) {
      const errorResponse = response.responseJSON

      // QNA 입력 형식 오류
      if (errorResponse.name === 'INVALID_INPUT_FORMAT') {
        errorResponse.errors.forEach(item => {
          $(`#${item.field}Error`).text(item.message)
        })
      }

      // 권한 오류
      if (errorResponse.name === 'PERMISSION_DENIED') {
        alert('접근 권한이 없습니다.')
        location.href = errorResponse.redirectUrl
      }

    })
  })

  function clearErrorMessage() {
    $("#form p").text("")
  }
})
