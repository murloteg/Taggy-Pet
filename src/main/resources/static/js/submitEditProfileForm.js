function submitEditProfileForm() {
    console.log(passwordRetypeCheck())
    if (passwordRetypeCheck() === false) {
        return false;
    }

    let form = document.getElementById("form");
    let permitToShowEmail = form.elements.hasPermitToShowEmail;
    let permitToShowPhoneNumber = form.elements.hasPermitToShowPhoneNumber;
    if (!permitToShowEmail.checked) {
        permitToShowEmail.checked = true;
        permitToShowEmail.value = false;
    }
    if (!permitToShowPhoneNumber.checked) {
        permitToShowPhoneNumber.checked = true;
        permitToShowPhoneNumber.value = false;
    }

    return true;
}