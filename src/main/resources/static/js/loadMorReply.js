// 댓글 더보기를 위한 ajax
$(document).ready(function () {
    $('#button-for-more-comment').on('click', function () {
        findCountOfRepliesInHTML();
        let articleIdx = document.getElementById("articleIdx").dataset.myValue;
        $.ajax({
            url: '/articles/reply/loadMoreReply',
            type: 'GET',
            dataType: 'json',
            data: ({
                "articleIdx": articleIdx,
                "countOfRepliesInHtml": countOfRepliesInHtml
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
                hideLoadMoreButton();
                ;
            },
            error: function (status) {
                console.log('Error:', status);

            }
        });
    });
});

let countOfRepliesInHtml = 0;

function findCountOfRepliesInHTML() {
    const commentBox = document.getElementById('comment-box');
    const articles = commentBox.querySelectorAll('article');
    countOfRepliesInHtml = articles.length;
}

// 더보기 버튼을 없애기 위한 기능.
const loadMoreButton = document.getElementById("button-for-more-comment");

function hideLoadMoreButton() {
    let countOfReplyInDbWithText = countOfReply.textContent;
    let countOfReplyInDb = parseInt(countOfReplyInDbWithText.replace(/[^0-9]/g, ""));
    if (countOfReplyInDb === countOfRepliesInHtml) {
        loadMoreButton.style.display = "none";
    }
}