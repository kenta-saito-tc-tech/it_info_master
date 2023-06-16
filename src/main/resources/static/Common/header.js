var menuIcon = document.getElementById("js-menu-icon");
var dropdownMenu = document.getElementById("js-dropdown-menu");

const adminAccount = document.getElementById("js-adminAccount");
const adminQuestion = document.getElementById("js-adminQuestion");
const inquiry = document.getElementById("js-inquiry");
const userRole = document.getElementById("js-userRole");

var userRoleText = userRole.value;
console.log(userRoleText);

var isDropdownVisible = false;

menuIcon.addEventListener('click', function() {
    isDropdownVisible = !isDropdownVisible;

    if (isDropdownVisible) {
        dropdownMenu.style.display = "block";
        if(userRoleText == "user"){
            adminAccount.style.display = "none";
            adminQuestion.style.display = "none";
        }
    } else {
        dropdownMenu.style.display = "none";
    }
});


inquiry.addEventListener("click", function () {
    if(userRoleText == "admin"){
        window.location.href = "/admin_inquiry";
    }else{
        window.location.href = "/user_inquiry";
    }
});
