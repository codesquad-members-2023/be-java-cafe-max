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

setRowIdOfUser();
