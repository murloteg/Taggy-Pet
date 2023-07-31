function toggle() {
    let hiddenFields = document.getElementById('hidden-fields');
    let submitButton = document.getElementById('add-button');
    if(this.checked) {
        hiddenFields.style.display = 'block';
        submitButton.style.marginTop = '0px';
    }
    else {
        hiddenFields.style.display = 'none';
        submitButton.style.marginTop = '22px'
    }
}
document.getElementById('check').onchange = toggle;
