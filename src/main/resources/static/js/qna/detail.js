$(document).ready(function () {
  $("#form").submit(async function (e) {
    e.preventDefault()

    const id = $("#id").val()

    $.ajax({
      type: "DELETE",
      url: `/qna/${id}`,
    }).done(function () {
      location.href = `/`
    }).fail(function (response) {
      const errorResponse = response.responseJSON
      alert(errorResponse.errorMessage)
    })
  })

  function clearErrorMessage() {
    $("#form p").text("")
  }
})
