
$(document).ready(function () {
    $(document).ready(function () {
        if (localStorage.storedValue == 'dark') {
            $('#changeTheme').prop('checked', true);
            $('#changeTheme1').prop('checked', true);
            document.getElementById("theme").className = "dark";
            document.getElementById('win-wrapper').style.backgroundImage = "url('/images/model-bg-dark.svg')";
        } else {
            $('#changeTheme').prop('checked', false);
            $('#changeTheme1').prop('checked', false);
            document.getElementById("theme").className = "light";
            document.getElementsByClassName('win-wrapper').style.backgroundImage = "url('/images/model-bg.svg')";
        }
    });
});
