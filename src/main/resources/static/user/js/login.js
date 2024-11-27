$(document).ready(function () {
	/*
    $(document).ready(function () {
        if (localStorage.storedValue == 'dark') {
            $('#changeTheme').prop('checked', true);
            $('#changeTheme1').prop('checked', true);
            document.getElementById("theme").className = "dark";
            document.getElementById('login-wrapper').style.backgroundImage = "url('user/images/login-bg-dark.svg')";
        } else {
            $('#changeTheme').prop('checked', false);
            $('#changeTheme1').prop('checked', false);
            document.getElementById("theme").className = "light";
            document.getElementsByClassName('login-wrapper').style.backgroundImage = "url('user/images/login-bg.svg')";
        }

    });*/
	
	$('#loginForm').on('submit', function(e) {
	        e.preventDefault();  // 기본 폼 제출 방지
	        
	        const formData = new FormData(this);
	        
	        $.ajax({
	            url: '/loginok',
	            type: 'POST',
	            data: formData,
	            processData: false,
	            contentType: false,
	            success: function() {
	                // 로그인 성공시 홈으로 이동
	                window.location.href = '/';
	            },
	            error: function(xhr) {
	                if (xhr.responseJSON && xhr.responseJSON.message) {
	                    alert(xhr.responseJSON.message);
	                } else {
	                    alert('로그인에 실패했습니다.');
	                }
	            }
	        });
	    });
	
});
