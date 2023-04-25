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

    const queryString = $("#reply-form").serialize();

    $.ajax({
        type: 'post',
        url: actionUrl,
        data: queryString,
        dataType: "json",
        error: function () {
            console.log('failure');
        },
        success: function (data) {
            const template = replyTemplate.formatUnicorn({
                nickName: data.nickName,
                date: data.date,
                content: data.content,
                articleIdx: data.articleIdx,
                replyIdx: data.replyIdx,
            });
            increaseCommentCount();
            $("#comment-box").append(template);
            $("textarea[name=content]").val("");
        },
    });
}

let articleCount = 0;

//todo 이거 지금은 보여지고 있는 개수로 초기화되서 추후 server에서 model을 통해 개수를 보내줘야함
function findCountOfRepliesInHTML() {
    const commentBox = document.getElementById('comment-box');
    const articles = commentBox.querySelectorAll('article');
    articleCount = articles.length;
}

let countOfReply = document.getElementById('count-of-reply');

function increaseCommentCount() {
    let preCountOfReply = countOfReply.textContent;
    let postCountOfReply = parseInt(preCountOfReply.replace(/[^0-9]/g, "")) + 1;
    countOfReply.textContent = "댓글 " + postCountOfReply + "개";
}


// 댓글 더보기를 위한 ajax
$(document).ready(function () {
    $('#button-for-more-comment').on('click', function () {
        findCountOfRepliesInHTML();
        let articleIdx = document.getElementById("articleIdx").dataset.myValue;
        console.log(articleCount);
        console.log(articleIdx);
        $.ajax({
            url: '/articles/reply/loadMoreReply',
            type: 'GET',
            dataType: 'json',
            data: ({
                "articleIdx": articleIdx,
                "countOfRepliesInHtml": articleCount
            }),
            success: function (data) {
                for (let i = data.length - 1; i >= 0; i--) {
                    const template = replyTemplate.formatUnicorn({
                        nickName: data[i].nickName,
                        date: data[i].date,
                        content: data[i].content,
                        articleIdx: data[i].articleIdx,
                        replyIdx: data[i].replyIdx,
                    })
                    $("#comment-box").prepend(template);
                    findCountOfRepliesInHTML();
                }
                ;
            },
            error: function (status) {
                console.log('Error:', status);
            }
        });
    });
});