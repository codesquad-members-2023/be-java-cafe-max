$(document).ready(function () {
  $("#logout").click(function (e) {
    e.preventDefault()
    $.ajax({
      type: "POST",
      url: "/users/logout"
    }).done(function () {
      location.href = "/user/login"
    }).fail(function () {
      alert("로그아웃에 실패하였습니다.")
    })
  })
})
