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
	
	//국가 선택 기능 관련 코드
	const countrySelect = $('#countrySelect');
	const currentNational = countrySelect.attr('value'); // 현재 설정된 national 값 가져오기

	//로딩 중 표시
	countrySelect.html('<option value="">로딩 중...</option>');

	//REST Countries API에서 모든 국가 정보 가져오기
	$.ajax({
	    url: 'https://restcountries.com/v3.1/all',
	    method: 'GET',
	    success: function(data) {
	        const countries = data.map(country => ({
	            code: country.cca2,
	            name: country.translations.kor?.common || country.name.common
	        }));

	        countries.sort((a, b) => a.name.localeCompare(b.name, 'ko'));

	        countrySelect.html('<option value="">국가 선택</option>');

	        countries.forEach(country => {
	            const option = $('<option>', {
	                value: country.code,
	                text: country.name
	            });
	            
	            // 현재 사용자의 national 값과 일치하면 selected 설정
	            if(country.code === currentNational) {
	                option.prop('selected', true);
	            }
	            
	            countrySelect.append(option);
	        });

	        console.log('Countries loaded successfully');
	    },
	    error: function(xhr, status, error) {
	        console.error('Error fetching countries:', error);
	        countrySelect.html('<option value="">국가 목록 로드 실패</option>');

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
	            const option = $('<option>', {
	                value: country.code,
	                text: country.name
	            });
	            
	            // 현재 사용자의 national 값과 일치하면 selected 설정
	            if(country.code === currentNational) {
	                option.prop('selected', true);
	            }
	            
	            countrySelect.append(option);
	        });
	    }
	});
	

	$("#signupForm").submit(function(e) {
	    e.preventDefault();

	    const form = this;
	    const formData = new FormData(form);

	    // 데이터 확인
	    console.log("=== 전송 데이터 확인 ===");
	    for (let pair of formData.entries()) {
	        console.log(pair[0] + ': ' + pair[1]);
	    }

	    // 비밀번호 검증
	    const password = $("#userPassword").val();
	    const confirmPassword = $("#confirmPassword").val();
	    if (password !== confirmPassword) {
	        alert("비밀번호가 일치하지 않습니다.");
	        return;
	    }

	    // 약관 동의 확인
	    if (!$("#flexCheckDefault").prop('checked')) {
	        alert("약관에 동의해주세요.");
	        return;
	    }

	    // 폼 데이터에서 confirmPassword 제거 (서버로 전송 불필요)
	    formData.delete('confirmPassword');

	    const userInfoDTO = {
	        email: formData.get('email'),
	        password: formData.get('password'),
	        name: formData.get('name'),
	        national: formData.get('national'),
	        birth: formData.get('birth'),
	        tel: formData.get('tel')
	    };

	    // 새로운 FormData 생성
	    const sendFormData = new FormData();
	    
	    // userInfoDTO를 Blob으로 변환하여 추가
	    const userInfoBlob = new Blob([JSON.stringify(userInfoDTO)], {
	        type: 'application/json'
	    });
	    sendFormData.append('userInfoDTO', userInfoBlob);

	    // 프로필 파일 추가
	    const fileInput = $('#profileImage')[0]; // 프로필 이미지 input의 id를 확인해주세요
	    if (fileInput && fileInput.files[0]) {
	        sendFormData.append('profileFile', fileInput.files[0]);
	    }

	    $.ajax({
	        url: '/signok',
	        type: 'POST',
	        data: sendFormData,
	        processData: false,
	        contentType: false,
	        cache: false,
	        success: function(response) {
	            console.log('Success Response:', response);
	            if (response && response.message) {
	                alert(response.message);
	                window.location.href = '/login';
	            } else {
	                alert('회원가입이 완료되었습니다.');
	                window.location.href = '/login';
	            }
	        },
	        error: function(xhr, status, error) {
	            console.error("=== 에러 정보 ===");
	            console.error("Status:", status);
	            console.error("Error:", error);
	            console.error("XHR:", xhr);

	            let errorMessage = '회원가입 중 오류가 발생했습니다.';

	            if (xhr.responseText) {
	                try {
	                    const response = JSON.parse(xhr.responseText);
	                    errorMessage = response.message || errorMessage;
	                } catch (e) {
	                    console.error("Response parsing error:", e);
	                }
	            }

	            alert(errorMessage);
	        }
	    });
	});
	

	});