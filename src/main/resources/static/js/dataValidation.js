const check = [false, false, false];
const writeCheck = [false, false];
let user = {};

function verifyEmail() {
    const email = document.getElementById("email").value;
    const regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/;

    if(regExp.test(email)) {
        document.getElementById('emailMessage').innerText = "올바른 이메일 형식입니다.";
        document.querySelector('#emailMessage').style.color = "rgb(186, 75, 238)";
        check[0] = true;
        user.email = email;
    } else {
        document.getElementById('emailMessage').innerText = "잘못된 이메일 형식입니다.";
        document.querySelector('#emailMessage').style.color = "red";
        check[0] = false;
    }
}

function verifyNickname() {
    const nickname = document.getElementById("nickname").value;

    if(nickname.length >= 2 && nickname.length <= 64) {
        document.getElementById('nicknameMessage').innerText = "올바른 닉네임입니다.";
        document.querySelector('#nicknameMessage').style.color = "rgb(186, 75, 238)";
        check[1] = true;
        user.nickname = nickname;
    } else {
        document.getElementById('nicknameMessage').innerText = "닉네임은 2글자 이상 64글자 이하여야 합니다.";
        document.querySelector('#nicknameMessage').style.color = "red";
        check[1] = false;
    }
}

function verifyPassword() {
    const password = document.getElementById("password").value;
    const reg = /(?=.*\d)(?=.*[a-z]).{8,32}/;

    if(reg.test(password)) {
        document.getElementById('passwordMessage').innerText = "올바른 비밀번호 형식입니다.";
        document.querySelector('#passwordMessage').style.color = "rgb(186, 75, 238)";
        check[2] = true;
        user.password = password;
    } else {
        document.getElementById('passwordMessage').innerText = "비밀번호는 8글자 이상 32글자 이하, 영어 소문자 및 숫자를 반드시 포함해야합니다.";
        document.querySelector('#passwordMessage').style.color = "red";
        check[2] = false;
    }
}

function validateData() {
    if(check[0] && check[1] && check[2]) {
        localStorage.setItem(user.email, JSON.stringify(user));
        window.location.href='signUpCompleted.html?user=' + user.email;
    } else {
        document.getElementById('allMessage').innerText = "모든 사항을 올바르게 기입해주세요.";
        document.querySelector('#allMessage').style.color = "red";
    }
}

function verifyTitle() {
    const title = document.getElementById("title").value;

    if(title.length > 0) {
        writeCheck[0] = true;
    } else {
        writeCheck[0] = false;
    }
}

function verifyContent() {
    const title = document.getElementById("content").value;

    if(title.length >= 3 && title.length <= 1000) {
        writeCheck[1] = true;
    } else {
        writeCheck[1] = false;
    }
}

function validateWriting() {
    if(writeCheck[0] && writeCheck[1]) {
        let write = new Object;
        let commentArrayData = new Array;
        let commentData = new Object;

        let board = JSON.parse(localStorage.getItem("board"));
        let loginUser = JSON.parse(localStorage.getItem("user"));
        let comment = JSON.parse(localStorage.getItem("comment"));

        write.title = document.getElementById("title").value;
        write.content = document.getElementById("content").value;
        write.hits = 0;
        write.date = toStringDate();
        write.nickname = loginUser.nickname;

        commentData.nickname = "";
        commentData.input = "";
        commentData.date = "";

        board.push(write);
        commentArrayData.push(comment);
        comment.push(commentArrayData);
        localStorage.setItem("board", JSON.stringify(board));
        localStorage.setItem("comment", JSON.stringify(comment));
        window.location.href='main-member.html'
    } else {
        document.getElementById('writingMessage').innerText = "제목은 공란일 수 없고 글 내용은 3글자 이상 1000 글자 이하여야 합니다.";
        document.querySelector('#writingMessage').style.color = "red";
    }
}
