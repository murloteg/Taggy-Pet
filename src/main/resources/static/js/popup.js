const popUp = document.getElementById('pop-up');

const openPopUpButtons = document.querySelectorAll('.open-pop-up');

for (let i = 0; i < openPopUpButtons.length; ++i) {
    openPopUpButtons[i].addEventListener('click', () => {
        popUp.classList.add('active');
    })
}

const closePopUpButtons = document.querySelectorAll('.pop-up-close');

for (let i = 0; i < closePopUpButtons.length; ++i) {
    closePopUpButtons[i].addEventListener('click', () => {
        popUp.classList.remove('active');
    })
}
