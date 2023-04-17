$(document).ready(function () {
  $("#logout").click(function (e) {
    e.preventDefault()
    $.ajax({
      type: "POST",
      url: "/logout"
    }).done(function () {
      location.href = "/login"
    }).fail(function () {
      alert("로그아웃에 실패하였습니다.")
    })
  })
})
