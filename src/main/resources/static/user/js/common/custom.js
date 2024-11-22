$(document).ready(function () {
    $('#preloader').fadeOut("slow");
    //back to top js
    $(window).scroll(function () {
        if ($(this).scrollTop() > 600) {
            $('.back-to-top').fadeIn();
        } else {
            $('.back-to-top').fadeOut();
        }
    });
    $(window).scroll(function () {
        if ($(this).scrollTop() > 82) {
            $("header").addClass("stickyHeader");
        } else {
            $("header").removeClass("stickyHeader");
        }
    });

    // scroll body to 0px on click
    $('.back-to-top').click(function () {
        $('body,html').animate({
            scrollTop: 0
        }, 500);
        return false;
    });

    // Responsive Nav
    $('.navbar-toggler').on('click', function () {
        $('.nav-right').toggleClass('show');
        $('.toggle-menu-icon').toggleClass('open');
        $('body').toggleClass('open-menu');
    });
    $("#main-content").click(function () {
        $('.nav-right').removeClass('show');
        $('.toggle-menu-icon').removeClass('open');
        $('body').removeClass('open-menu');
    })


    // Password Show&Hide
    $(".password-show-hide").on('click', function (event) {
        event.preventDefault();
        if ($('.formPassword input').attr("type") == "text") {
            $('.formPassword input').attr('type', 'password');
            $('.formPassword i').addClass("fa-eye-slash");
            $('.formPassword i').removeClass("fa-eye");
        } else if ($('.formPassword input').attr("type") == "password") {
            $('.formPassword input').attr('type', 'text');
            $('.formPassword i').removeClass("fa-eye-slash");
            $('.formPassword i').addClass("fa-eye");
        }
    });
    $(".sidebar-menu ul li").click(function () {
        $(this).addClass('active').siblings().removeClass('active');

    });
    $("#sidebarMenuOpen").click(function () {
        $('.dashboard-sidebar').toggleClass('show');
    });
});

// Modal Increment Decrement
function up(max) {
    document.getElementById("myNumber").value = parseInt(document.getElementById("myNumber").value) + 1;
    if (document.getElementById("myNumber").value >= parseInt(max)) {
        document.getElementById("myNumber").value = max;
    }
}

function down(min) {
    document.getElementById("myNumber").value = parseInt(document.getElementById("myNumber").value) - 1;
    if (document.getElementById("myNumber").value <= parseInt(min)) {
        document.getElementById("myNumber").value = min;
    }
}

// Data Filter Table

// dark vs light mode
var themeWrapper = $('#theme');
var themeLogo = $('.themeLogo');
var footerThemeLogo = $('.fThemeLogo');

$(document).on('change', ".changeTheme", function () {
    var elem = $(this);
    var userColor;

    $(elem).is(":checked") ? userColor = 'dark' : userColor = 'light';
    localStorage.setItem('storedValue', userColor);
    themeWrapper.attr('class', userColor);
    if (userColor == 'dark') {
        document.getElementById('headerLogo').src = 'user/images/sample/logo-white.svg';
        document.getElementById('footerLogo').src = 'user/images/sample/logo.svg';
        document.getElementById('win-wrapper').style.backgroundImage = "url('user/images/sample/model-bg-dark.svg')";
        document.getElementById('login-wrapper').style.backgroundImage = "url('user/images/sample/login-bg-dark.svg')";
    } else {
        document.getElementById('headerLogo').src = 'user/images/sample/logo.svg';
        document.getElementById('footerLogo').src = 'user/images/sample/logo-white.svg';
        document.getElementById('win-wrapper').style.backgroundImage = "url('user/images/sample/model-bg.svg')";
        document.getElementById('login-wrapper').style.backgroundImage = "url('user/images/sample/login-bg.svg')";
    }

});

$(document).ready(function () {
    console.log('local stored value : ', localStorage.storedValue);
    $(document).ready(function () {
        console.log('local stored value : ', localStorage.storedValue);
        const theme = document.getElementById("theme");
        const headerLogo = document.getElementById('headerLogo');
        const footerLogo = document.getElementById('footerLogo');

        if (localStorage.storedValue == 'dark') {
            $('#changeTheme').prop('checked', true);
            $('#changeTheme1').prop('checked', true);
            if (theme) theme.className = "dark";
            if (headerLogo) headerLogo.src = 'user/images/sample/logo-white.svg';
            if (footerLogo) footerLogo.src = 'user/images/sample/logo.svg';
        } else {
            $('#changeTheme').prop('checked', false);
            $('#changeTheme1').prop('checked', false);
            if (theme) theme.className = "light";
            if (headerLogo) headerLogo.src = 'user/images/sample/logo.svg';
            if (footerLogo) footerLogo.src = 'user/images/sample/logo-white.svg';
        }
    });
});

// Auction Timer Start
// Set the date we're counting down to
var countDownDate = new Date("august 30, 2023 12:22:25").getTime();
// Update the count down every 1 second
var x = setInterval(function () {

    // Get today's date and time
    var now = new Date().getTime();

    // Find the distance between now and the count down date
    var distance = countDownDate - now;
    // Time calculations for days, hours, minutes and seconds
    var days = Math.floor(distance / (1000 * 60 * 60 * 24));
    var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
    var seconds = Math.floor((distance % (1000 * 60)) / 1000);

    // Output the result in an element with id="timer-inner"
    var timer = document.querySelectorAll('.timer-inner');
    timer.forEach((times) => {
        times.innerHTML = `<span> ${days}D </span>:<span> ${hours}H </span>:<span>${minutes}M </span>:<span>${seconds} S</span>`;
    })

    // If the count down is over, write some text 
    if (distance < 0) {
        const timerInner = document.querySelector(".timer-inner");
        clearInterval(x);
        if (timerInner) timerInner.innerHTML = "EXPIRED";
    }
}, 1000);
// Auction Timer End

// custom select
let index = 1;

const on = (listener, query, fn) => {
    document.querySelectorAll(query).forEach(item => {
        item.addEventListener(listener, el => {
            fn(el);
        })
    })
}
// on('click', '.selectBtn', item => {
//     const next = item.target.nextElementSibling;
//     next.classList.toggle('toggle');
//     next.style.zIndex = index++;

// });
on('click', '.option', item => {
    item.target.parentElement.classList.remove('toggle');
    const parent = item.target.closest('.select').children[0];
    parent.setAttribute('data-type', item.target.getAttribute('data-type'));
    parent.innerText = item.target.innerText;

});

$(function () {
    $(".selectBtn").on("click", function (a) {
        let elem = $(this).parent().children('div');
        if (elem.hasClass('toggle')) {
            $('.selectDropdown').removeClass('toggle');
            elem.removeClass('toggle');
        } else {
            $('.selectDropdown').removeClass('toggle');
            elem.addClass('toggle');
        }
        a.stopPropagation()
    });
    $(document).on("click", function (a) {
        if ($(a.target).is(".selectDropdown") === false) {
            $(".selectDropdown").removeClass("toggle");
        }
    });
});