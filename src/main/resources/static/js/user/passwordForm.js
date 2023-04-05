$(document).ready(function () {
  $("#form").submit(async function (e) {
    e.preventDefault()
    await clearErrorMessage()

    const data = {
      "password": $("#password").val()
    }
    const id = $("#id").val()
    const urlPath = `/users/password/${id}`

    $.ajax({
      type: "POST",
      url: urlPath,
      data: JSON.stringify(data),
      contentType: 'application/json; charset=utf-8',
      success: function (resp) {
        if (resp.errorCode === 802) {
          $("#passwordError").text(resp.errorMessage)
          return;
        }
        if (hasFormatError(resp)) {
          writeError(resp)
        }
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
