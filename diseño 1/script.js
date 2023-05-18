const wrapper = document.querySelector('.wrapper');
const btnloginp = document.querySelector('.btnLogin');
const iconClose = document.querySelector('.close');

btnloginp.addEventListener('click', ()=> {
    wrapper.classList.add('active-login');
});


iconClose.addEventListener('click', ()=> {
    wrapper.classList.remove('active-login');
});


