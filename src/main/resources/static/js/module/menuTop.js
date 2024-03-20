const menu = document.getElementById("openMenu")
const buttonOpenMenu = document.getElementById("buttonOpenMenu")

let openMenu = false

if (openMenu) {
    menu.style.display = "block";
} else {
    menu.style.display = "none";
}

buttonOpenMenu.addEventListener("click", function () {
    if (openMenu) {
        openMenu = false
        menu.style.display = "none";
    } else {
        menu.style.display = "block";
        openMenu = true
    }
});
