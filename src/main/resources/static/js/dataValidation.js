const validSignUpCheck = [false, false, false];
const validWriteCheck = [false, false];
const validOutputView = ["올바른 이메일 형식입니다.", "올바른 닉네임입니다.", "올바른 비밀번호 형식입니다."];
const invalidOutputView = ["잘못된 이메일 형식입니다.", "닉네임은 2글자 이상 64글자 이하여야 합니다."
, "비밀번호는 8글자 이상 32글자 이하, 영어 소문자 및 숫자를 반드시 포함해야합니다."
, "모든 사항을 올바르게 기입해주세요.", "제목은 공란일 수 없고 글 내용은 3글자 이상 1000 글자 이하여야 합니다."];
const EMAIL_NUM = 0;
const NICKNAME_NUM = 1;
const PASSWORD_NUM = 2;
const ALL_DATA_NUM = 3;
const WRITING_NUM = 4;
let user = {};

function verifyEmail() {
    const email = document.getElementById("email").value;
    const regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/;

    if(regExp.test(email)) {
        validView('#emailMessage', EMAIL_NUM);
        validSignUpCheck[EMAIL_NUM] = true;
        user.email = email;
    } else {
        invalidView('#emailMessage', EMAIL_NUM);
        validSignUpCheck[EMAIL_NUM] = false;
    }
}

function verifyNickname() {
    const nickname = document.getElementById("nickname").value;

    if(nickname.length >= 2 && nickname.length <= 64) {
        validView('#nicknameMessage', NICKNAME_NUM);
        validSignUpCheck[NICKNAME_NUM] = true;
        user.nickname = nickname;
    } else {
        invalidView('#nicknameMessage', NICKNAME_NUM);
        validSignUpCheck[NICKNAME_NUM] = false;
    }
}

function verifyPassword() {
    const password = document.getElementById("password").value;
    const reg = /(?=.*\d)(?=.*[a-z]).{8,32}/;

    if(reg.test(password)) {
        validView('#passwordMessage', PASSWORD_NUM);
        validSignUpCheck[PASSWORD_NUM] = true;
        user.password = password;
    } else {
        invalidView('#passwordMessage', PASSWORD_NUM);
        validSignUpCheck[PASSWORD_NUM] = false;
    }
}

function validateData() {
    if(validSignUpCheck[0] && validSignUpCheck[1] && validSignUpCheck[2]) {
        localStorage.setItem(user.email, JSON.stringify(user));
        window.location.href='signUpCompleted.html?user=' + user.email;
    } else {
        invalidView('#allMessage', ALL_DATA_NUM);
    }
}

function verifyTitle() {
    const title = document.getElementById("title").value;

    if(title.length > 0) {
        validWriteCheck[0] = true;
    } else {
        validWriteCheck[0] = false;
    }
}

function verifyContent() {
    const title = document.getElementById("content").value;

    if(title.length >= 3 && title.length <= 1000) {
        validWriteCheck[1] = true;
    } else {
        validWriteCheck[1] = false;
    }
}

function validateWriting() {
    if(validWriteCheck[0] && validWriteCheck[1]) {
        const write = createWriteObject();
        const commentArrayData = [];
        const commentData = createCommentDataObject();

        const board = JSON.parse(localStorage.getItem("board"));
        const comment = JSON.parse(localStorage.getItem("comment"));

        board.push(write);
        commentArrayData.push(comment);
        comment.push(commentArrayData);

        saveDataToLocalStorage(board, comment);
    } else {
        invalidView('#writingMessage', WRITING_NUM);
    }

    function createWriteObject() {
        const loginUser = JSON.parse(localStorage.getItem("user"));
        return {
            title: document.getElementById("title").value,
            content: document.getElementById("content").value,
            hits: 0,
            date: toStringDate(),
            nickname: loginUser.nickname
        };
    }

    function createCommentDataObject() {
            return {
                nickname: "",
                input: "",
                date: ""
            };
        }

    function saveDataToLocalStorage(board, comment) {
        localStorage.setItem("board", JSON.stringify(board));
        localStorage.setItem("comment", JSON.stringify(comment));
    }

    function validView(const message, const number) {
        const messageElement = document.querySelector(message);
        document.innerText = validOutputView[number];
        document.style.color = "rgb(186, 75, 238)";
    }

    function invalidView(const message, const number) {
        const messageElement = document.querySelector(message);
        messageElement.innerText = invalidOutputView[number];
        messageElement.style.color = "red";
    }
}
