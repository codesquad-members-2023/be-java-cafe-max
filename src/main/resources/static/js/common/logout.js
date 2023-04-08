$(document).ready(function () {
  $("#logout").click(function (e) {
    e.preventDefault()
    $.ajax({
      type: "POST",
      url: "/users/logout",
      success: function (msg) {
        location.href = "/user/login"
      }
    })
  })
})
