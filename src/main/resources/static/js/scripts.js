String.prototype.format = function () {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function (match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};

function setRowIdOfUser() {
    let elements = document.getElementsByClassName("row-id");
    let idx = 1;
    for (elem of elements) {
        elem.innerHTML = idx++;
    }
}

function postRequest() {
    const logoutBtn = document.getElementById("logoutBtn");
    logoutBtn.addEventListener("click", e => {
        e.preventDefault();
        const url = "/logout";
        const xhr = new XMLHttpRequest();

        xhr.open('POST', url);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
                window.location.href = xhr.responseURL;
            }
        }
        xhr.send();
    });
}

setRowIdOfUser();
postRequest();
