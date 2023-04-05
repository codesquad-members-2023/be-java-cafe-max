$(document).ready(function () {
  $("#form").submit(async function (e) {
    e.preventDefault()
    await clearErrorMessage()

    const data = {
      "writer": $("#writer").val(),
      "title": $("#title").val(),
      "content": $("#content").val(),
      "userId": $("#userId").val()
    }

    $.ajax({
      type: "POST",
      url: "/qna",
      data: JSON.stringify(data),
      contentType: 'application/json; charset=utf-8',
      success: function (resp) {
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
