$(document).ready(function() {
    
	//테마 설정
    /*if (localStorage.storedValue == 'dark') { 이건 잘 모르겠음;;
        $('#changeTheme').prop('checked', true);
        $('#changeTheme1').prop('checked', true);
        document.getElementById("theme").className = "dark";
        document.getElementById('login-wrapper').style.backgroundImage = "url('/user/images/login-bg-dark.svg')";
    } else {
        $('#changeTheme').prop('checked', false);
        $('#changeTheme1').prop('checked', false);
        document.getElementById("theme").className = "light";
        document.getElementById('login-wrapper').style.backgroundImage = "url('/user/images/login-bg.svg')";
    }*/
	
	//프로필 이미지 미리보기 & 기본프로필에 사진첨부한 이미지 넣기
	$("#profileImage").change(function() {
	    const file = this.files[0];
	    if (file) {
	        const reader = new FileReader();
	        reader.onload = function(e) {
	            $("#profilePreview")
	                .attr('src', e.target.result)
	                .css('display', 'block'); // 이미지가 확실히 보이도록
	        }
	        // 이미지 파일인지 확인
	        if (file.type.startsWith('image/')) {
	            reader.readAsDataURL(file);
	        } else {
	            alert('이미지 파일을 선택해주세요.');
	            $("#profileImage").val(''); // 파일 선택 초기화
	        }
	    }
	});

    //국가 선택 기능
    const countrySelect = $('#countrySelect');
    
    //로딩 중 표시
    countrySelect.html('<option value="">로딩 중...</option>');

    //REST Countries API에서 모든 국가 정보 가져오기
    $.ajax({
        url: 'https://restcountries.com/v3.1/all',
        method: 'GET',
        success: function(data) {
            // 국가 데이터 가공 (한국어 이름 우선, 없으면 영어 이름 사용)
            const countries = data.map(country => ({
                code: country.cca2,
                name: country.translations.kor?.common || country.name.common
            }));

            // 가나다순 정렬
            countries.sort((a, b) => a.name.localeCompare(b.name, 'ko'));

            // 셀렉트박스 초기화
            countrySelect.html('<option value="">국가 선택</option>');

            // 국가 목록 추가
            countries.forEach(country => {
                countrySelect.append(
                    $('<option>', {
                        value: country.code,
                        text: country.name
                    })
                );
            });

            // 한국 선택
            countrySelect.val('');

            console.log('Countries loaded successfully');
        },
        error: function(xhr, status, error) {
            console.error('Error fetching countries:', error);
            countrySelect.html('<option value="">국가 목록 로드 실패</option>');

            // 에러 발생시 기본 국가 목록 사용
            const fallbackCountries = [
                { code: 'KR', name: '대한민국' },
                { code: 'US', name: '미국' },
                { code: 'JP', name: '일본' },
                { code: 'CN', name: '중국' },
                { code: 'GB', name: '영국' },
                { code: 'DE', name: '독일' },
                { code: 'FR', name: '프랑스' },
                { code: 'CA', name: '캐나다' },
                { code: 'AU', name: '호주' }
            ];

            fallbackCountries.sort((a, b) => a.name.localeCompare(b.name, 'ko'));

            fallbackCountries.forEach(country => {
                countrySelect.append(
                    $('<option>', {
                        value: country.code,
                        text: country.name
                    })
                );
            });
        }
    });

	 // 가입하기 버튼 클릭 이벤트
	    $("#signupBtn").click(function(e) {
	        e.preventDefault();
	        
	        const form = $("#signupForm")[0];
	        if (!form.checkValidity()) {
	            form.classList.add('was-validated');
	            return;
	        }

	        // 비밀번호 일치 확인
	        const password = $("#userPassword").val();
	        const confirmPassword = $("#confirmPassword").val();
	        
	        if (password !== confirmPassword) {
	            $("#confirmPassword").addClass('is-invalid');
	            return;
	        }

	        // 약관 동의 확인
	        if (!$("#flexCheckDefault").prop('checked')) {
	            $("#flexCheckDefault").addClass('is-invalid');
	            return;
	        }

	        // FormData 객체 생성
	        const formData = new FormData(form);
	        
			// 프로필 이미지가 선택되지 않은 경우 기본 이미지 경로 추가
			const profileImage = $("#profileImage")[0].files[0];
			if (!profileImage) {
			    formData.set('profileImage', '/user/images/default-profile.svg');
			}
			
	        // AJAX로 폼 데이터 전송
	        $.ajax({
	            url: '/signok',
	            type: 'POST',
	            data: formData,
	            processData: false,
	            contentType: false,
	            success: function(response) {
	                alert('회원가입이 완료되었습니다.');
	                window.location.href = '/login';
	            },
	            error: function(xhr, status, error) {
	                const errorMessage = xhr.responseJSON?.message || '회원가입 중 오류가 발생했습니다.';
	                alert(errorMessage);
	                console.error('Error:', error);
	            }
	        });
	    });

	    // 폼 제출 이벤트 방지
/*	    $("#signupForm").submit(function(e) {
	        e.preventDefault();
	    });*/
	});