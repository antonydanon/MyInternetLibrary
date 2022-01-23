$(document).ready(function(){
    $('#header').load('header.jsp');
});

window.onload = function() {
    document.querySelector('#btnGetWindowOfOrderRegistration').onclick = function(e){
        let a = document.getElementsByName('choiceOfBook');
        let newvar = 0;
        let count;
        for(count = 0; count < a.length; count++){
            if(a[count].checked == true){
                newvar = newvar + 1;
            }
        }

        if(newvar >= 6){
            document.getElementById('notvalid').innerHTML = "Please select only five"
            return false;
        }
    }
}



