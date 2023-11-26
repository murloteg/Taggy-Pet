function passwordRetypeCheck() {
    let form = document.getElementById("form");
    let password = form.elements.password;
    let retypePassword = form.elements.retypePassword;
    if (password.value !== retypePassword.value) {
        retypePassword.style.borderColor="#f81d1d";
        retypePassword.style.backgroundColor="rgb(253 90 90 / 11%)";
        retypePassword.value = "";
        retypePassword.setAttribute("placeholder", "Пароль не совпадает")
        return false;
    }
    return true;
}