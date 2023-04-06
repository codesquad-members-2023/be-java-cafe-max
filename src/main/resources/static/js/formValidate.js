const idError = document.getElementById("id-error");
const emailError = document.getElementById("email-error");
const nickNameError = document.getElementById("nickName-error");
const passwordError = document.getElementById("password-error");

const emailRegex = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/;
const nickNameRegex = /^\w{2,12}$/;
const idRegex = /^\w{2,12}$/;
const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;

function checkIdValidity(inputField) {

    let inputValue = inputField.value;

    idError.style.height = "auto";
    idError.style.fontSize = "5px";
    if (!idRegex.test(inputValue)) {
        idError.textContent = "올바르지 않은 ID 형식입니다. 2~12자 사이로 입력해 주세요";
        idError.style.display = "inline";
    }else{
        idError.textContent = "";
    }
}
function checkEmailValidity(inputField) {

    let inputValue = inputField.value;

    emailError.style.height = "auto";
    emailError.style.fontSize = "5px";
    if (!emailRegex.test(inputValue)) {
        emailError.textContent = "올바르지 않은 email 형식입니다.잘좀 입력해 주세요";
        emailError.style.display = "inline";
    }else{
        emailError.textContent = "";
    }
}

function checkNickNameValidity(inputField) {

    let inputValue = inputField.value;

    nickNameError.style.height = "auto";
    nickNameError.style.fontSize = "5px";
    if (!nickNameRegex.test(inputValue)) {
        nickNameError.textContent = "올바르지 않은 닉네임 형식입니다. 2~12자 사이로 입력해 주세요";
        nickNameError.style.display = "inline";
    }else{
        nickNameError.textContent = "";
    }
}
function checkPasswordValidity(inputField) {

    let inputValue = inputField.value;

    passwordError.style.height = "auto";
    passwordError.style.fontSize = "5px";
    if (!passwordRegex.test(inputValue)) {
        passwordError.textContent = "올바르지 않은 비밀번호 형식입니다. 소문자와 숫자를 조합해 8자 이상으로 입력해 주세요";
        passwordError.style.display = "inline";
    }else{
        passwordError.textContent = "";
    }
}