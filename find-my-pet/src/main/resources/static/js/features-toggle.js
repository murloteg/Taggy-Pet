function toggle() {
    let featuresList = document.getElementById('features-fields');
    let addPetButton = document.getElementById('add-button');
    if(this.checked) {
        featuresList.style.display = 'block';
        addPetButton.style.marginTop = '0px';
    }
    else {
        featuresList.style.display = 'none';
        addPetButton.style.marginTop = '22px'
    }
}
document.getElementById('check').onchange = toggle;
