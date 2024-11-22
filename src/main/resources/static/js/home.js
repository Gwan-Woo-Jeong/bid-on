var swiper = new Swiper(".categorySwiper", {
    spaceBetween: 32,
    autoplay: {
        delay: 1000,
    },
    breakpoints: {
        450: {
            slidesPerView: 2,
        },
        768: {
            slidesPerView: 3,
        },
        992: {
            slidesPerView: 4,
        },
        1400: {
            slidesPerView: 6,
        },
    },
    navigation: {
        nextEl: ".swiper-button-next",
        prevEl: ".swiper-button-prev",
    },
});

const mySwiper = new Swiper('.banner-slider', {
    effect: "cards",
    grabCursor: true,
    slidesPerView: 1,
    autoplay: {
        delay: 2000,
    },
});


var x = 0;
var y = 1;
var currentActiveSlide = $('.swiper-slide-active .banner-card img').attr('src');
$('#current-image').css('background-image', `url(${currentActiveSlide})`);

mySwiper.on('slideChange', function (e) {
    x = e.activeIndex;
    if (y <= x) {
        var currentActiveSlide = $('.swiper-slide-next .banner-card img').attr('src');
        $('#current-image').css('background-image', `url(${currentActiveSlide})`);
    }
    else {
        var currentActiveSlide = $('.swiper-slide-prev .banner-card img').attr('src');
        $('#current-image').css('background-image', `url(${currentActiveSlide})`);
    }
    y = x;
});


