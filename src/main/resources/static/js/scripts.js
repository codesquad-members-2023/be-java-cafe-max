String.prototype.format = function() {
  const args = arguments;
  return this.replace(/{(\d+)}/g, function(match, number) {
    return typeof args[number] != 'undefined'
        ? args[number]
        : match
        ;
  });
};

function post(path, params = null) {
  let form = document.createElement("form");
  form.method = "post";
  form.action = path;
  for (let key in params) {
    let field = document.createElement("input");
    field.type = "hidden";
    field.name = key;
    field.value = params[key];
    form.appendChild(field);
  }
  document.body.appendChild(form);
  form.submit();
}