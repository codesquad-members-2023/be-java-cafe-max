

function validateForm(event) {
    event.preventDefault(); // Prevent the form from submitting

    const title = document.getElementById("post-title").value.trim();
    const contents = document.getElementById("post-content").value.trim();

    const titleIsValid = validateTitle(title);
    const contentsIsValid = validateContents(contents);

    if (!titleIsValid) {
        alert("제목을 3글자 이상 30글자 이하 여야 합니다.");
        return;
    }
    if (!contentsIsValid) {
        alert("내용을 3글자 이상 1000글자 이하 여야 합니다.");
        return;
    }

    // Submit the form
    alert('글 작성이 완료 되었습니다.');
    document.getElementById("post-form").submit();
    window.location.href = "forum.html";

}

function validateTitle(title) {
    return !(title.length < 3 || title.length > 30);
}

function validateContents(contents) {
    return !(contents.length < 3 || contents.length > 1000);

}
document.getElementById("post-form").addEventListener("submit", validateForm);
