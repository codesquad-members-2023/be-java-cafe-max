const validSignUpCheck = [false, false, false, false];
const validUpdateCheck = [true, true, true, true];
const validWriteCheck = [false, false];
const validWriteUpdateCheck = [true, true];
const validOutputView = ["올바른 아이디 형식입니다.", "올바른 이메일 형식입니다.", "올바른 닉네임입니다.", "올바른 비밀번호 형식입니다."];
const invalidOutputView = ["아이디는 2글자 이상 64글자 이하여야 합니다.", "잘못된 이메일 형식입니다.", "닉네임은 2글자 이상 64글자 이하여야 합니다."
, "비밀번호는 8글자 이상 32글자 이하, 영어 소문자 및 숫자를 반드시 포함해야합니다."
, "모든 사항을 올바르게 기입해주세요.", "제목은 공란일 수 없고 글 내용은 3글자 이상 1000 글자 이하여야 합니다."];
const USERID_NUM = 0;
const EMAIL_NUM = 1;
const NICKNAME_NUM = 2;
const PASSWORD_NUM = 3;
const ALL_DATA_NUM = 4;
const WRITING_NUM = 5;
let user = {};

function verifyUserID() {
    const userID = document.getElementById("userID").value;

    if(userID.length >= 2 && userID.length <= 64) {
        validView('#userIDMessage', USERID_NUM);
        validSignUpCheck[USERID_NUM] = true;
        user.userID = userID;
    } else {
        invalidView('#userIDMessage', USERID_NUM);
        validSignUpCheck[USERID_NUM] = false;
    }
}

function verifyEmail() {
    const email = document.getElementById("email").value;
    const regExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/;

    if(regExp.test(email)) {
        validView('#emailMessage', EMAIL_NUM);
        validSignUpCheck[EMAIL_NUM] = true;
        validUpdateCheck[EMAIL_NUM] = true;
        user.email = email;
    } else {
        invalidView('#emailMessage', EMAIL_NUM);
        validSignUpCheck[EMAIL_NUM] = false;
        validUpdateCheck[EMAIL_NUM] = false;
    }
}

function verifyNickname() {
    const nickname = document.getElementById("nickname").value;

    if(nickname.length >= 2 && nickname.length <= 64) {
        validView('#nicknameMessage', NICKNAME_NUM);
        validSignUpCheck[NICKNAME_NUM] = true;
        validUpdateCheck[NICKNAME_NUM] = true;
        user.nickname = nickname;
    } else {
        invalidView('#nicknameMessage', NICKNAME_NUM);
        validSignUpCheck[NICKNAME_NUM] = false;
        validUpdateCheck[NICKNAME_NUM] = false;
    }
}

function verifyPassword() {
    const password = document.getElementById("password").value;
    const reg = /(?=.*\d)(?=.*[a-z]).{8,32}/;

    if(reg.test(password)) {
        validView('#passwordMessage', PASSWORD_NUM);
        validSignUpCheck[PASSWORD_NUM] = true;
        validUpdateCheck[PASSWORD_NUM] = true;
        user.password = password;
    } else {
        invalidView('#passwordMessage', PASSWORD_NUM);
        validSignUpCheck[PASSWORD_NUM] = false;
        validUpdateCheck[PASSWORD_NUM] = false;
    }
}

function validateData() {
    if(validSignUpCheck[0] && validSignUpCheck[1] && validSignUpCheck[2] && validSignUpCheck[3]) {
        localStorage.setItem(user.email, JSON.stringify(user));
        return true;
    } else {
        invalidView('#allMessage', ALL_DATA_NUM);
        return false;
    }
}

function validateUpdateData() {
    if(validUpdateCheck[1] && validUpdateCheck[2] && validUpdateCheck[3]) {
        return true;
    } else {
        invalidView('#allMessage', ALL_DATA_NUM);
        return false;
    }
}

function verifyTitle() {
    const title = document.getElementById("title").value;

    if(title.length > 0) {
        validWriteCheck[0] = true;
        validWriteUpdateCheck[0] = true;
    } else {
        validWriteCheck[0] = false;
        validWriteUpdateCheck[0] = false;
    }
}

function verifyContent() {
    const content = document.getElementById("content").value;

    if(content.length >= 3 && content.length <= 1000) {
        validWriteCheck[1] = true;
        validWriteUpdateCheck[1] = true;
    } else {
        validWriteCheck[1] = false;
        validWriteUpdateCheck[1] = false;
    }
}

function validateWriting() {
    if(validWriteCheck[0] && validWriteCheck[1]) {
        return true;
    } else {
        invalidView('#allMessage', WRITING_NUM);
        return false;
    }
}

function validateWritingUpdate() {
    if(validWriteUpdateCheck[0] && validWriteUpdateCheck[1]) {
        return true;
    } else {
        invalidView('#allMessage', WRITING_NUM);
        return false;
    }
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

function validView(message, number) {
    const messageElement = document.querySelector(message);
    messageElement.innerText = validOutputView[number];
    messageElement.style.color = "rgb(186, 75, 238)";
}

function invalidView(message, number) {
    const messageElement = document.querySelector(message);
    messageElement.innerText = invalidOutputView[number];
    messageElement.style.color = "red";
}
