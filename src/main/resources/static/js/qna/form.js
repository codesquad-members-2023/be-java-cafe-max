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

    $.ajax({
      type: "POST",
      url: "/qna",
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
    })
  })

  function clearErrorMessage() {
    $("#form p").text("")
  }
})
