var swiper = new Swiper(".testimonialSwiper", {
    spaceBetween: 30,
    loop: true,
    autoplay: {
        delay: 2500,
    },
    breakpoints: {
        992: {
            slidesPerView: 2,
        },
    },
    pagination: {
        el: ".swiper-pagination",
        clickable: true,
    },

});
