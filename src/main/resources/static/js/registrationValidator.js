function validateForm(event) {
    // Prevent the form from submitting

    const email = document.getElementById("email").value.trim();
    const name = document.getElementById("name").value.trim();
    const userId = document.getElementById("userId").value.trim();
    const password = document.getElementById("pwd").value.trim();
    const checkbox = document.getElementById("privacyCheck");

    const emailIsValid = validateEmail(email);
    const nameIsValid = validateName(name);
    const userIdIsValid = validateUserId(userId);
    const passwordIsValid = validatePassword(password);

    if (!emailIsValid) {
        alert("이메일 주소를 입력해주세요.");
        event.preventDefault();
        return;
    }
    if (!nameIsValid) {
        alert("이름: 2-16자의 영문, 숫자, 한글을 입력해주세요.");
        event.preventDefault();
        return;
    }
    if (!userIdIsValid) {
        alert("아이디: 2-16자의 영문, 숫자, 한글을 입력해주세요.");
        event.preventDefault();
        return;
    }
    if (!passwordIsValid) {
        alert("비밀번호: 8자 이상의 영문 대소문자와 숫자 조합으로 입력해주세요.");
        event.preventDefault();
        return;
    }
    if (!checkbox.checked) {
        alert("개인정보 수집 및 이용약관에 동의해주세요.");
        event.preventDefault();
        return;
    }

    // Submit the form
    alert('회원가입이 완료되었습니다.');
    document.getElementById("registrationValidation").submit();

}

function validateEmail(email) {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const isValid = emailRegex.test(email);
    console.log("Email validation:", email, isValid);
    return isValid;
}

function validateName(name) {
    const nameRegex = /^[가-힣a-zA-Z0-9]{2,16}$/;
    const isValid = nameRegex.test(name);
    console.log("Name validation:", name, isValid);
    return isValid;
}

function validateUserId(userId) {
    const userIdRegex = /^[a-zA-Z0-9]{2,16}$/;
    const isValid = userIdRegex.test(userId);
    console.log("User ID validation:", userId, isValid);
    return isValid;
}

function validatePassword(password) {
    const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;
    const isValid = passwordRegex.test(password);
    console.log("Password validation:", password, isValid);
    return isValid;
}

document.getElementById("registrationValidation").addEventListener("submit", validateForm);
