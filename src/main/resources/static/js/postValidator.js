const titleError = document.getElementById("title-error");
const contentError = document.getElementById("content-error");

const titleRegex = /^\s*[\S\s]+\s*$/;
const contentRegex = /^[\s\S]{3,1000}$/;

function checkTitleValidity(inputField) {

    let inputValue = inputField.value;

    titleError.style.height = "auto";
    titleError.style.fontSize = "15px";
    if (!titleRegex.test(inputValue)) {
        titleError.textContent = "제목이 공란입니다.";
        titleError.style.display = "inline";
    }else{
        titleError.textContent = "";
    }
}

function checkContentValidity(inputField) {
    let inputValue = inputField.value;

    contentError.style.height = "auto";
    contentError.style.fontSize = "15px";
    if (!contentRegex.test(inputValue)) {
        contentError.textContent = "내용은 3~1000자 사이입니다.";
        contentError.style.display = "inline";
    }else{
        contentError.textContent = "";
    }
}

