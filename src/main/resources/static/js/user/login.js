$(document).ready(function () {
  $("#form").submit(async function (e) {
    e.preventDefault()
    await clearErrorMessage()

    const data = {
      "userId": $("#userId").val(),
      "password": $("#password").val()
    }
    $.ajax({
      type: "POST",
      url: "/user/login",
      data: JSON.stringify(data),
      contentType: 'application/json; charset=utf-8',
      success: function (resp) {
        if (resp.errorCode === 801) {
          $("#loginError").removeClass("hidden")
          $("#loginError").text(resp.errorMessage)
          return;
        }
        if (hasFormatError(resp)) {
          writeError(resp)
          return;
        }
        location.href = "/"
      }
    })
  })

  function clearErrorMessage() {
    $("#form p").text("")
    $("#loginError").addClass("hidden")
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
