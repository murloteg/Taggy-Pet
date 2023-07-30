function customValidationMessages(input) {
    input.setCustomValidity('');
    if (input.validity.valueMissing) {
        input.setCustomValidity("Это поле является обязательным");
    }
    if (input.validity.patternMismatch) {
        input.setCustomValidity("Чип должен состоять из 15 цифр");
    }
}

function getInputNumbersValue(input) {
    return input.value.replace(/\D/g, '');
}

function validateChipInput(e) {
    let input = e.target;
    input.value = getInputNumbersValue(input);
}

document.addEventListener("DOMContentLoaded", function () {
    let chipInput = document.getElementById('chipId');
    chipInput.addEventListener('input', validateChipInput, false);
})