document.addEventListener("DOMContentLoaded", function(){
    let phoneInputs = document.querySelectorAll('input[data-tel-input]');

    let getInputNumbersValue = function(input){

    }

    let onPhoneInput = function(e){
        let input = e.target,
            inputValue = input.value;
        console.log('inputValue', inputValue);
    }

    for (i = 0; i < phoneInputs.length; ++i) {
        let input = phoneInputs[i];
        input.addEventListener("input", onPhoneInput);
    }
})