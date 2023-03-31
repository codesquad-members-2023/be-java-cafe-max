/* 회원가입 */
function validateAll() {
    const email = checkEmail();
    const nickname = checkNickname();
    const password = checkPassword();
    const userId = checkUserId();

    if (email && nickname && password && userId) {
        alert("회원가입이 완료되었습니다");
        return true;
    }
    
    return false;
}

function checkEmail() {
    const emailRegex = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    const email = document.getElementById('inputEmail').value;
    const message = document.querySelector('.checkEmail');

    if (!emailRegex.test(email)) {
        message.classList.remove('hide');
        signUp.inputPassword.focus();
        return false;
    }

    message.classList.add('hide');
    return true;
}

function checkNickname() {
    const nickname = document.getElementById('inputNickname').value;
    const message = document.querySelector('.checkNickname');

    if (nickname.length < 2 || nickname.length > 12) {
        message.classList.remove('hide');
        signUp.inputPassword.focus();
        return false;
    }

    message.classList.add('hide');
    return true;
}

function checkPassword() {
    const passwordRegex = /^(?=.*?[a-z])(?=.*?[0-9]).{8,32}$/;
    const password = document.getElementById('inputPassword').value;
    const message = document.querySelector('.checkPassword');

    if (!passwordRegex.test(password)) {
        message.classList.remove('hide');
        signUp.inputPassword.focus();
        return false;
    }

    message.classList.add('hide');
    return true;
}

function checkUserId() {
    const userId = document.getElementById('inputUserId').value;
    const message = document.querySelector('.checkUserId');

    if (userId.length < 2 || userId.length > 12) {
        message.classList.remove('hide');
        signUp.inputPassword.focus();
        return false;
    }

    message.classList.add('hide');
    return true;
}

/* 글쓰기 */
function validatePost() {
    const title = checkTitle();
    const content = checkContent();
    const writer = checkWriter();

    if (title && content && writer) {
        alert("글이 정상적으로 등록되었습니다");
        document.getElementById("posting").submit();
    }
}

function checkTitle() {
    const title = document.getElementById('inputTitle').value;
    const message = document.querySelector('.checkTitle');

    if (title.length < 1) {
        message.classList.remove('hide');
        return false;
    }

    message.classList.add('hide');
    return true;
}

function checkContent() {
    const content = document.getElementById('inputContent').value;
    const message = document.querySelector('.checkContent');

    if (content.length < 3 || content.length > 1000) {
        message.classList.remove('hide');
        return false;
    }

    message.classList.add('hide');
    return true;
}

function checkWriter() {
    const writer = document.getElementById('inputWriter').value;
    const message = document.querySelector('.checkWriter');

    if (writer.length < 1) {
        message.classList.remove('hide');
        return false;
    }

    message.classList.add('hide');
    return true;
}

/* 회원 정보 수정 */
function validateUpdate() {
    const email = checkEmail();
    const nickname = checkNickname();
    const password = checkPassword();

    if (email && nickname && password) {
        return true;
    }

    return false;
}
