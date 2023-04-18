$(document).ready(function () {
  $("#form").submit(async function (e) {
    e.preventDefault()
    await clearErrorMessage()

    const data = {
      "userId": $("#userId").val(),
      "password": $("#password").val(),
      "name": $("#name").val(),
      "email": $("#email").val()
    }
    const id = $("#id").val()
    const urlPath = "/users/" + id + "/update"

    $.ajax({
      type: 'put',
      url: urlPath,
      data: JSON.stringify(data),
      contentType: 'application/json; charset=utf-8',
    }).done(function () {
      alert("회원정보 수정이 완료되었습니다.")
      location.href = "/login"
    }).fail(function (response) {
      const errorResponse = response.responseJSON
      if (errorResponse.name === 'ALREADY_EXIST_EMAIL') {
        $("#emailError").text(errorResponse.errorMessage)
      }
      if (errorResponse.name === 'INVALID_INPUT_FORMAT') {
        errorResponse.errors.forEach(item => {
          $(`#${item.field}Error`).text(item.message)
        })
      }
      if (errorResponse.name === 'NOT_MATCH_PASSWORD') {
        $("#passwordError").text(errorResponse.errorMessage)
      }
      if (errorResponse.name === 'UNAUTHORIZED') {
        alert("로그인 상태가 아닙니다.")
        location.href = "/login"
      }
      if (errorResponse.name === 'PERMISSION_DENIED') {
        alert("접근 권한이 없습니다.")
        location.href = "/error/client-error"
      }
    })
  })

  function clearErrorMessage() {
    $("#form p").text("")
  }
})
