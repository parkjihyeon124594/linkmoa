const directory = document.querySelector("#link_container");

function login_page() {
  window.location.href = "login.html";
}

function registeration_page() {
  window.location.href = "registeration.html";
}

const nonClick = document.querySelectorAll(".non-click");
function handleClick(event) {
  nonClick.forEach((e) => {
    e.classList.remove("click");
  });
  event.target.classList.add("click");
}
nonClick.forEach((e) => {
  e.addEventListener("click", handleClick);
});

const createDir = function () {
  const newDir = document.createElement("button");
  directory.appendChild(newDir);
};
