const directory = document.querySelector("#link_container");
const addBtn = document.querySelector("#add_btn");

function login_page() {
  window.location.href = "login.html";
}

function registeration_page() {
  window.location.href = "registeration.html";
}
//sort_button 부분
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

function openMenubar() {
  if (document.getElementById("menubars").style.display === "block") {
    document.getElementById("menubars").style.display = "none";
  } else {
    document.getElementById("menubars").style.display = "block";
  }
}
