$(document).ready(function () {
    $(document).ready(function () {
        if (localStorage.storedValue == 'dark') {
            $('#changeTheme').prop('checked', true);
            $('#changeTheme1').prop('checked', true);
            document.getElementById("theme").className = "dark";
            document.getElementById('login-wrapper').style.backgroundImage = "url('/images/login-bg-dark.svg')";
        } else {
            $('#changeTheme').prop('checked', false);
            $('#changeTheme1').prop('checked', false);
            document.getElementById("theme").className = "light";
            document.getElementsByClassName('login-wrapper').style.backgroundImage = "url('/images/login-bg.svg')";
        }

    });
});
