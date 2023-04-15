$(document).ready(function () {
  $("#form").submit(async function (e) {
    e.preventDefault()
    await clearErrorMessage()

    const data = {
      password: $("#password").val()
    }

    const id = $("#id").val()
    const urlPath = `/users/password/${id}`

    $.ajax({
      type: "POST",
      url: urlPath,
      data: JSON.stringify(data),
      contentType: 'application/json; charset=utf-8',
    }).done(function () {
      location.href = `/user/form/${id}`
    }).fail(function (response) {
      const errorResponse = response.responseJSON
      // 비밀번호 일치하지 않는 경우
      if (errorResponse.name === 'NOT_MATCH_PASSWORD') {
        $("#passwordError").text(errorResponse.errorMessage)
      }
      // 유저 입력 형식 오류
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
