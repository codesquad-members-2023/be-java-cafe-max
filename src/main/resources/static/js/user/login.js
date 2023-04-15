$(document).ready(function () {
  $("#form").submit(async function (e) {
    e.preventDefault()
    await clearErrorMessage()

    const data = {
      userId: $("#userId").val(),
      password: $("#password").val()
    }

    $.ajax({
      type: "POST",
      url: "/users/login",
      data: JSON.stringify(data),
      contentType: 'application/json; charset=utf-8',
    }).done(function () {
      location.href = "/"
    }).fail(function (response) {
      const errorResponse = response.responseJSON

      // 아이디 또는 비밀번호 일치하지 않는 경우
      if (errorResponse.name === 'NOT_MATCH_LOGIN') {
        $("#loginError").removeClass("hidden").text(errorResponse.errorMessage)
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
    $("#loginError").addClass("hidden")
  }
})
