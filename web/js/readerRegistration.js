$(document).ready(function(){
    $('#header').load('header.jsp');
});

function validate(regex, input){
    return regex.test(input);
}

function notValid(input, el, message){
    input.classList.add('is-invalid');
    el.innerHTML = message;
}

function valid(input, el, message){
    input.classList.remove('is-invalid');
    input.classList.add('is-valid');
    el.innerHTML = message;
}

function isCorrectBirthday(input){
    input = Number.parseInt(input.toString().substr(input.toString().length - 4));
    if(input > 1900){
        return true;
    }else{
        return false;
    }
}

window.onload = function() {
    let inpName = document.querySelector('#inpName');
    let mistakeName = document.querySelector('#mistakeName');

    let inpSurname = document.querySelector('#inpSurname');
    let mistakeSurname = document.querySelector('#mistakeSurname');

    let inpPatronymic = document.querySelector('#inpPatronymic');
    let mistakePatronymic = document.querySelector('#mistakePatronymic');
    regFio = /[A-Za-z]/;

    let inpBirthday = document.querySelector('#inpBirthday');
    let mistakeBirthday = document.querySelector('#mistakeBirthday');

    document.querySelector('#btn').onclick = function(e){
        e.preventDefault();
        if(!validate(regFio, inpName.value)){
            notValid(inpName, mistakeName, 'Incorrect name');
        }else{
            valid(inpName, mistakeName, '');
        }
        if(!validate(regFio, inpSurname.value)){
            notValid(inpSurname, mistakeSurname, 'Incorrect surname');
        }else{
            valid(inpSurname, mistakeSurname, '');
        }
        if(!validate(regFio, inpPatronymic.value) && inpPatronymic.value !== ""){
            notValid(inpPatronymic, mistakePatronymic, 'Incorrect patronymic');
        }else{
            valid(inpPatronymic, mistakePatronymic, '');
        }
        if(isCorrectBirthday(inpBirthday)){
            valid(inpBirthday, mistakeBirthday, '');
        }else{
            notValid(inpBirthday, mistakeBirthday, 'Incorrect date');
        }
    }
}

