/* 회원가입 */
function validateAll() {
    const email = checkEmail();
    const nickname = checkNickname();
    const password = checkPassword();
    const userId = checkUserId();

    if (email && nickname && password && userId) {
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
    const password = checkNewPassword();

    if (email && nickname && password) {
        return true;
    }

    return false;
}

function checkNewPassword() {
    const passwordRegex = /^(?=.*?[a-z])(?=.*?[0-9]).{8,32}$/;
    const newPassword = document.getElementById('newPassword').value;
    const message = document.querySelector('.checkPassword');

    if (!passwordRegex.test(newPassword)) {
        message.classList.remove('hide');
        return false;
    }

    message.classList.add('hide');
    return true;
}

/* 로그인 */
function validateLogin() {
    const password = checkPassword();
    const userId = checkUserId();

    if (password && userId) {
        return true;
    }

    return false;
}

/* 글 수정 */

function validateEdit() {
    const title = checkTitle();
    const content = checkContent();

    if (title && content) {
        const form = document.getElementById("posting");
        const methodInput = document.createElement("input");
        methodInput.setAttribute("type", "hidden");
        methodInput.setAttribute("name", "_method");
        methodInput.setAttribute("value", "PUT");
        form.appendChild(methodInput);

        alert("글이 정상적으로 수정되었습니다");
        document.getElementById("posting").submit();
    }
}

/* 글 삭제 */

function validateDelete() {
    if (confirm("삭제된 게시글은 복구가 어렵습니다. 게시글을 삭제하시겠습니까?")) {
        const form = document.getElementById("posting");
        const methodInput = document.createElement("input");
        methodInput.setAttribute("type", "hidden");
        methodInput.setAttribute("name", "_method");
        methodInput.setAttribute("value", "DELETE");
        form.appendChild(methodInput);

        alert("글이 정상적으로 삭제되었습니다");
        document.getElementById("posting").submit();
    }
}
