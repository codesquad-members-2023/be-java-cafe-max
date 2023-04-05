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
      type: "POST",
      url: urlPath,
      data: JSON.stringify(data),
      contentType: 'application/json; charset=utf-8',
      success: function (resp) {
        if (resp.errorCode === 601) {
          $("#emailError").text(resp.errorMessage)
          return;
        }
        if (hasFormatError(resp)) {
          writeError(resp)
          return;
        }
        alert("회원 정보 수정이 완료되었니다.")
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
