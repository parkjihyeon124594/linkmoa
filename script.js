const directory = document.querySelector("#link_container");

function login_page() {
  window.location.href = "login.html";
}

function registeration_page() {
  window.location.href = "registeration.html";
}

const createDir = function () {
  const newDir = document.createElement("button");
  directory.appendChild(newDir);
};
