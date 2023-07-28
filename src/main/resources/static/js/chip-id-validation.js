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