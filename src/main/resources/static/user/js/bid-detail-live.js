const urlParams = new URLSearchParams(window.location.search);
const itemId = urlParams.get('itemId');

$("#live-enter-btn").click(function () {
    const child = window.open('/live-auction/bid-room?itemId=' + itemId, '_blank');
    child.moveTo(0, 0);
    child.resizeTo(screen.availWidth, screen.availHeight);

    child.addEventListener('load', () => {
        child.connect(myInfo || "Anonymous");
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

let currentIndex = 0;

function showPreviousImage() {
    const galleryItems = document.querySelectorAll('.card-image-container .dcard-image');

    const totalImages = galleryItems.length;

    galleryItems[currentIndex].classList.remove('active');

    // 이전 이미지로 이동 (첫 번째에서 마지막으로 순환)
    currentIndex = (currentIndex - 1 + totalImages) % totalImages;
    galleryItems[currentIndex].classList.add('active');

    console.log('현재 Index:', currentIndex); // 디버깅용
}

function showNextImage() {
    const galleryItems = document.querySelectorAll('.card-image-container .dcard-image');

    const totalImages = galleryItems.length;

    galleryItems[currentIndex].classList.remove('active');

    // 다음 이미지로 이동 (마지막에서 첫 번째로 순환)
    currentIndex = (currentIndex + 1) % totalImages;
    galleryItems[currentIndex].classList.add('active');

    console.log('현재 Index:', currentIndex); // 디버깅용
}
