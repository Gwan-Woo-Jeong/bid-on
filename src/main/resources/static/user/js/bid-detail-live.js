const userId = $('#user-id').val();
console.log("userId : " + userId);

const urlParams = new URLSearchParams(window.location.search);
const itemId = urlParams.get('itemId');

$("#live-enter-btn").click(function () {
    const child = window.open('/live-auction/bid-room?itemId=' + itemId, '_blank');
    child.moveTo(0, 0);
    child.resizeTo(screen.availWidth, screen.availHeight);

    child.addEventListener('load', () => {
        child.connect(userId || "Anonymous");
    })
})

const swiper = new Swiper(".product-swiper", {
    spaceBetween: 32,
    autoplay: {
        delay: 1500,
        disableOnInteraction: false,
    },
    breakpoints: {
        450: {
            slidesPerView: 1,
        },
        768: {
            slidesPerView: 2,
        },
        992: {
            slidesPerView: 3,
        },
        1400: {
            slidesPerView: 4,
        },
    }
});
