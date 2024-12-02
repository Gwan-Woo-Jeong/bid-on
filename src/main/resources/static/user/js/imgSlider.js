// DOM에서 모든 슬라이드 아이템을 가져옵니다.
	const sliderItems = document.querySelectorAll(".slider-item");
	let currentIndex = 0;

// 슬라이드 이미지 표시 함수
	function updateImage() {
	    // 모든 이미지 숨기기
	    sliderItems.forEach(item => item.style.display = "none");
	
	    // 현재 인덱스에 해당하는 이미지만 보이도록 설정
	    sliderItems[currentIndex].style.display = "block";
	}

// 이전 버튼 클릭 이벤트
	document.getElementById("prevBtn").addEventListener("click", function() {
	    currentIndex = (currentIndex === 0) ? sliderItems.length - 1 : currentIndex - 1;
	    updateImage();
	});

// 다음 버튼 클릭 이벤트
	document.getElementById("nextBtn").addEventListener("click", function() {
	    currentIndex = (currentIndex === sliderItems.length - 1) ? 0 : currentIndex + 1;
	    updateImage();
	});
	
//순회 막는거
	function updateButtonState() {
	    // 첫 번째 이미지일 때 "이전" 버튼 비활성화
	    if (currentIndex === 0) {
	        document.getElementById("prevBtn").disabled = true;
	    } else {
	        document.getElementById("prevBtn").disabled = false;
	    }

	    // 마지막 이미지일 때 "다음" 버튼 비활성화
	    if (currentIndex === sliderItems.length - 1) {
	        document.getElementById("nextBtn").disabled = true;
	    } else {
	        document.getElementById("nextBtn").disabled = false;
	    }
	}


// 페이지 로드 시 첫 번째 이미지를 보이도록 설정
	updateImage();
