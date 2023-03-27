/*
회원가입 form 유효성 검증 script
 */

/**
 * 회원가입 form을 submit 합니다.
 * @param e 회원가입 버튼의 이벤트
 */
document.getElementById("submitButton").onclick = function(e) {
    e.preventDefault();
    if(!validateSignInForm()) return;

    /* submit */
    document.getElementById("signUpForm").submit();

}

/**
 * 회원 가입 form 전체를 검증합니다.
 * @returns {boolean} 유효성 검사 통과 유무를 boolean 타입으로 반환합니다.
 */
function validateSignInForm() {
    if(!validateUserId()) return false;
    if(!validatePassword()) return false;
    if(!validatePasswordCheck()) return false;
    if(!validateName()) return false;
    if(!validateEmail()) return false;
    return true;
}

/**
 * 아이디 유효성을 검사합니다.
 *  - 영어 대/소문자 또는 숫자 조합 4 ~ 20자
 * @returns {boolean} 유효성 검사 통과 유무를 boolean 타입으로 반환합니다.
 */
function validateUserId() {
    let userId = document.getElementById("userId");
    let userIdReg = /^[a-zA-z0-9]{4,20}$/;

    if(!userIdReg.test(userId.value)) {
        alert("아이디는 영문, 숫자 조합 4 ~ 20자를 입력해 주세요.");
        userId.focus();
        return false;
    }
    return true;
}

/**
 * 비밀번호 유효성을 검사합니다.
 *  - 영어 대/소문자, 숫자, 특수문자 조합 8 ~ 32자
 * @returns {boolean} 유효성 검사 통과 유무를 boolean 타입으로 반환합니다.
 */
function validatePassword() {
    let password = document.getElementById("password");
    let passwordReg = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@$!%*?&]).{8,32}$/;

    if(!passwordReg.test(password.value)) {
        alert("비밀번호는 영어 소문자, 숫자 조합 8 ~ 32자를 입력해 주세요.");
        password.focus();
        return false;
    }
    return true;
}

/**
 * 비밀번호 확인 유효성을 검사합니다.
 * - 비밀번호와 일치하는지 검사
 * @returns {boolean} 유효성 검사 통과 유무를 boolean 타입으로 반환합니다.
 */
function validatePasswordCheck() {
    let password = document.getElementById("password");
    let passwordCheck = document.getElementById("passwordCheck");

    if(!passwordCheck.value.length || !passwordCheck.value) {
        alert("비밀번호 확인란을 입력해 주세요.");
        passwordCheck.focus();
        return false;
    }
    if(password.value != passwordCheck.value) {
        alert("비밀번호와 비밀번호 확인란이 일치하지 않습니다.");
        passwordCheck.focus();
        return false;
    }

    return true;
}

/**
 * 이름 유효성을 검사합니다.
 * @returns {boolean} 유효성 검사 통과 유무를 boolean 타입으로 반환합니다.
 */
function validateName() {
    let name = document.getElementById("name");
    let nameReg = /^[a-zA-Z가-힣][\sa-zA-Z가-힣]{2,49}$/;

    if(name.value[0] == " ") {
        alert("이름의 첫 글자는 공백일 수 없습니다.")
        name.focus();
        return false;
    }

    if(!nameReg.test(name.value)) {
        alert("이름은 영어, 한글 3~50글자 사이로 입력해 주세요.");
        name.focus();
        return false;
    }
    return true;
}

/**
 * 이메일 유효성을 검사합니다.
 * @returns {boolean} 유효성 검사 통과 유무를 boolean 타입으로 반환합니다.
 */
function validateEmail() {
    let email = document.getElementById("email");
    let emailReg = /^[\w.%+-]+@[a-z0-9.-]+\.[a-z]{2,}$/i;

    if(!emailReg.test(email.value)) {
        alert("올바른 이메일을 입력해 주세요.");
        email.focus();
        return false;
    }
    return true;
}
