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

  $(".submit-write button[type=submit]").on("click", addAnswer);
  $(".qna-comment-slipp-articles").on("click",
      ".delete-answer-form button[type=submit]", deleteAnswer);

})

function addAnswer(e) {
  e.preventDefault(); //submit 이 자동으로 동작하는 것을 막는다.

  let jsonData = {}
  $("form[name=answer]").serializeArray().map(function (x) {
    jsonData[x.name] = x.value;
  });
  const urlPath = $(".submit-write").attr("action");

  $.ajax({
    type: 'post',
    url: urlPath,
    data: JSON.stringify(jsonData),
    contentType: 'application/json; charset=utf-8',
    error: function (resp) {
      alert(resp.responseJSON.errorMessage)
    },
    success: function (data) {
      const answerTemplate = $("#answerTemplate").html();
      const template = answerTemplate.format(
          data.writerName,
          data.createTime,
          data.content,
          data.questionId,
          data.id);
      $(".qna-comment-slipp-articles").prepend(template);
      $("textarea[name=content]").val("");
    }
  });
}

function deleteAnswer(e) {
  e.preventDefault();

  const deleteBtn = $(this);
  const urlPath = $(".delete-answer-form").attr("action");

  $.ajax({
    type: 'delete',
    url: urlPath,
    dataType: 'json',
    error: function (xhr, status) {
      console.log("error");
    },
    success: function (data) {
      deleteBtn.closest("article").remove();
    }
  });
}

