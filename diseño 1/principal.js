const btn = document.querySelector('#btn');
const sidebar = document.querySelector('.sidebar');
const listItem = document.querySelectorAll('.list-item');

btn.onclick = function (){
    sidebar.classList.toggle('active')
}

function activeLink(){
    listItem.forEach(item=>
    item.classList.remove('active'));
    this.classList.add('active');
}

listItem.forEach(item=>
item.onclick = activeLink);

