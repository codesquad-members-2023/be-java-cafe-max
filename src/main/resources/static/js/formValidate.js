const idError = document.getElementById("id-error");
const emailError = document.getElementById("email-error");
const nickNameError = document.getElementById("nickName-error");
const passwordError = document.getElementById("password-error");

const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+/;
const nickNameRegex = /^\w{2,12}$/;
const idRegex = /^\w{2,12}$/;
const passwordRegex = /^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]{2,5}/;

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