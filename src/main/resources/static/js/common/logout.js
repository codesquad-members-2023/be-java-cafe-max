$(document).ready(function () {
  $("#logout").click(function (e) {
    e.preventDefault()
    $.ajax({
      type: "POST",
      url: "/logout"
    }).done(function (redirectURL) {
      location.href = redirectURL
    }).fail(function () {
      location.href = "/error/server-error"
    })
  })
})
