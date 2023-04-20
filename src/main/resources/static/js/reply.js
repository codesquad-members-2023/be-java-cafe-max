const replyForm = document.getElementById("reply-form");

replyForm.addEventListener("submit", handleSubmit);

//article.idx 가져오기
const actionUrl = replyForm.getAttribute("action");

const replyTemplate = `
<article id="replyTemplate" class="comment">
        <div class="article-text">
            <div id="reply-info-box">
                <p class="comment-content comment-author-name">{{nickName}}</p>
                <p class="comment-content comment-time">{{date}}</p>
                <form class = "reply-delete" method="post" action="/articles/{{articleIdx}}/{{replyIdx}}">
                    <input type="hidden" name="_method" value="DELETE"/>
                    <button class="post-button reply-button" type="submit">삭제</button>
                </form>
            </div>
            <div class="comment-content comment-doc">
                <p>{{content}}</p>
            </div>
        </div>
</article>
`;

String.prototype.formatUnicorn = function (replacements) {
    return this.replace(/\{\{(\w+)\}\}/g, function (_, key) {
        return replacements[key] || '';
    });
};

function handleSubmit(e) {
    e.preventDefault();

    //serialize를 통해 폼에 입력된 데이터를 직렬화 한다.
    let queryString = $("#reply-form").serialize();

    console.log("query : " + queryString);
    console.log("url : " + actionUrl);


    // jquery에서 제공하는 ajax기능을사용하여 서버에 HTTP요청을 보내고 서버로부터 응답을 받는다.(비동기)
    $.ajax({
        type: 'post',
        url: actionUrl,
        data: queryString,
        dataType: "json",
        error: function () {
            console.log('failure');
        },
        success: function (data, status) {
            console.log(data);
            console.log(status);
            const template = replyTemplate.formatUnicorn({
                nickName: data.nickName,
                date: data.date,
                content: data.content,
                articleIdx: data.articleIdx,
                replyIdx: data.replyIdx,
            });
            $("#comment-box").append(template);
            $("textarea[name=content]").val("");
        },
    });
}


// 삭제버튼

const replyDelete = document.querySelectorAll(".reply-delete");

replyDelete.forEach(btn => btn.addEventListener("submit", handleDelete));


function handleDelete(e) {

    e.preventDefault();

    let deleteBtn = $(this);
    const deleteUrl = deleteBtn.attr("action");

    console.log("url : " + deleteUrl);

    // jquery에서 제공하는 ajax기능을사용하여 서버에 HTTP요청을 보내고 서버로부터 응답을 받는다.(비동기)
    $.ajax({
        type: 'delete',
        url: deleteUrl,
        dataType: "json",
        error: function () {
            console.log('failure');
        },
        success: function (data, status) {
            console.log(data);
            if (data.ok) {
                deleteBtn.closest(".comment").remove()
            }
        },
    });
}