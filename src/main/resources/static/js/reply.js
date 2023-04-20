/* 댓글 등록 */
function validateReply() {
    const content = document.getElementById('inputReply').value;
    const message = document.querySelector('.checkReply');

    if (content.length < 3 || content.length > 400) {
        message.classList.remove('hide');
        return false;
    }

    message.classList.add('hide');
    return true;
}

const button = document.getElementById("replyButton");
button.addEventListener("click", submitReply);

let commentCount = parseInt(document.querySelector("#replyContainer > span").innerText.split(" ")[1].slice(0, -1));

function submitReply() {
    if (!validateReply()) {
        return false;
    }

    const form = document.querySelector(".submitReply");
    const formData = new FormData(form);
    const queryString = new URLSearchParams(formData).toString();
    const url = document.querySelector('.submitReply').getAttribute('action');
    console.log("query : " + queryString);

    const xhr = new XMLHttpRequest();
    xhr.open('POST', url);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onreadystatechange = function() {
        if (xhr.readyState === xhr.DONE) {
            if (xhr.status === 200 || xhr.status === 201) {
                const data = JSON.parse(xhr.responseText);
                console.log(data);

                const createdAt = new Date(data.createdAt);
                const formattedCreatedAt = createdAt.toLocaleString('ko-KR', { year: 'numeric', month: '2-digit', day: '2-digit', hour: '2-digit', minute: '2-digit' });

                const answerTemplate = document.querySelector("#answerTemplate").innerHTML;
                const template = answerTemplate.replace("{0}", data.userId)
                                             .replace("{1}", formattedCreatedAt)
                                             .replace("{2}", data.contents);
                commentCount++;
                document.querySelector("#replyContainer > span").innerText = `댓글 ${commentCount}개`;
                document.querySelector("#replyContainer").insertAdjacentHTML("beforeend", template);
                document.querySelector("textarea[name=contents]").value = "";
                document.querySelector("#replyContainer")
            } else {
                console.log("failure");
            }
        }
    };
    xhr.send(queryString);
}
