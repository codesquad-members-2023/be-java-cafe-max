$(document).ready(function () {
  $("#form").submit(async function (e) {
    e.preventDefault()

    const id = $("#id").val()

    $.ajax({
      type: "DELETE",
      url: `/qna/${id}`,
    }).done(function (resp) {
      alert(`${resp.title} 게시글을 삭제하였습니다.`)
      location.href = `/`
    }).fail(function (response) {
      const errorResponse = response.responseJSON
      alert(errorResponse.errorMessage)
    })
  })
})
