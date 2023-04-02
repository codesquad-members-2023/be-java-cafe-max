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
    $.ajax({
      type: "POST",
      url: "/user/create",
      data: JSON.stringify(data),
      contentType: 'application/json; charset=utf-8',
      success: function (resp) {
        // TODO : 회원가입시 입력 정보가 늘어날수록 파일을 수정해야하는 문제가 있음
        if (resp.errorCode === 600) {
          $("#userIdError").text(resp.errorMessage)
          return;
        }
        if (resp.errorCode === 601) {
          $("#emailError").text(resp.errorMessage)
          return;
        }
        if (hasFormatError(resp)) {
          writeError(resp)
          return;
        }
        alert("회원가입이 성공하였습니다.")
        location.href = "/users"
      }
    })
  })

  function clearErrorMessage() {
    $("#form p").text("")
  }

  function hasFormatError(respMap) {
    for (let key in respMap) {
      const value = respMap[key]
      if (value.errorCode !== undefined) {
        return true;
      }
    }
    return false;
  }

  function writeError(respMap) {
    for (let key in respMap) {
      const value = respMap[key]
      $(`#${key}Error`).text(value.errorMessage)
    }
  }

})
