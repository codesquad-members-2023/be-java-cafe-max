const replyForm = document.getElementById("reply-form");

replyForm.addEventListener("submit", handleSubmit);

const actionUrl = replyForm.getAttribute("action");

const replyTemplate = `
<article id="replyTemplate" class="comment">
        <div class="article-text">
            <div id="reply-info-box">
                <p class="comment-content comment-author-name">{{nickName}}</p>
                <p class="comment-content comment-time">{{date}}</p>
                <form class = "reply-delete" method="post" action="/articles/reply/{{replyIdx}}">
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

    let queryString = $("#reply-form").serialize();

    $.ajax({
        type: 'post',
        url: actionUrl,
        data: queryString,
        dataType: "json",
        error: function () {
            console.log('failure');
        },
        success: function (data, status) {
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


