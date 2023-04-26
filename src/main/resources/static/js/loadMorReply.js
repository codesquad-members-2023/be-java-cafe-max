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