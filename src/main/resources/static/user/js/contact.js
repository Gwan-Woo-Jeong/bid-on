$("#submit").click(function () {
    var name = $('#name').val();
    var email = $('#email').val();
    var message = $('#message').val();
    if (name == '') {
        $('#alert-msg').text('Name is required.');
    } else if (email == '') {
        $('#alert-msg').text('Email ID is required.');
    } else if (message == '') {
        $('#alert-msg').text('Message is required.');
    } else {
        $('#alert-msg').text('Mail sent successfully.');
    }
    $('body').addClass('alert-show');
    setTimeout(function () {
        $('body').removeClass('alert-show');
    }, 3000);
});
