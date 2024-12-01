var swiper = new Swiper(".product-swiper", {
    spaceBetween: 32, autoplay: {
        delay: 1500, disableOnInteraction: false,
    }, breakpoints: {
        450: {
            slidesPerView: 1,
        }, 768: {
            slidesPerView: 2,
        }, 992: {
            slidesPerView: 3,
        }, 1400: {
            slidesPerView: 4,
        },
    }
});

	// price Range
	const lowerSlider = document.querySelector('#lower');
	const upperSlider = document.querySelector('#upper');
	const lowerValueInput = document.querySelector('#one');
	const upperValueInput = document.querySelector('#two');

	// 최소 간격
	const minGap = 10000;
	const sliderMin = parseInt(lowerSlider.min);
	const sliderMax = parseInt(upperSlider.max);

	// 초기 값
	lowerValueInput.value = formatCurrency(lowerSlider.value);
	upperValueInput.value = formatCurrency(upperSlider.value);


	upperSlider.oninput = function () {
	    let lowerVal = parseInt(lowerSlider.value);
	    let upperVal = parseInt(upperSlider.value);

	    // 최소 간격 유지
	    if (upperVal < lowerVal + minGap) {
	        upperSlider.value = lowerVal + minGap;
	        upperVal = parseInt(upperSlider.value); // 업데이트된 값
	    }


	    upperValueInput.value = formatCurrency(upperVal);
	};


	lowerSlider.oninput = function () {
	    let lowerVal = parseInt(lowerSlider.value);
	    let upperVal = parseInt(upperSlider.value);

	    // 최소 간격 유지
	    if (lowerVal > upperVal - minGap) {
	        lowerSlider.value = upperVal - minGap;
	        lowerVal = parseInt(lowerSlider.value); // 업데이트된 값
	    }


	    lowerValueInput.value = formatCurrency(lowerVal);
	};

	// 숫자를 원화 형식으로 포맷
	function formatCurrency(value) {
	    return `￦${parseInt(value).toLocaleString('ko-KR')}`;
	}

