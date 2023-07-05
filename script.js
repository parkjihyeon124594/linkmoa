const directory = document.querySelector("#link_container");
const addBtn = document.querySelector("#add_btn");

function login_page() {
  window.location.href = "login.html";
}

function registeration_page() {
  window.location.href = "registeration.html";
}

const createDir = function () {
  const newDir = document.createElement("button");
  newDir.className = "dir_or_link";
  directory.appendChild(newDir);
};

function openMenubar() {
  if (document.getElementById("menubars").style.display === "block") {
    document.getElementById("menubars").style.display = "none";
  } else {
    document.getElementById("menubars").style.display = "block";
  }
}
