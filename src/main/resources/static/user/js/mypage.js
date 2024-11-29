// DOM이 로드된 후 실행
document.addEventListener('DOMContentLoaded', function() {
    // 필요한 요소들 선택
    const menuLinks = document.querySelectorAll('.menu-link');
    const breadcrumbTitle = document.querySelector('.breadcrumb-title');
    const contentSections = document.querySelectorAll('.content-section');
    const menuItems = document.querySelectorAll('.sidebar-menu li');
    
    // 메뉴 클릭 이벤트 핸들러
    function handleMenuClick(e) {
        e.preventDefault();
        
        const targetId = this.getAttribute('data-target');
        const menuText = this.querySelector('span').textContent;
        
        // 모든 섹션 숨기기 & 메뉴 활성화 상태 제거
        contentSections.forEach(section => section.classList.add('d-none'));
        menuItems.forEach(item => item.classList.remove('active'));
        
        // 선택된 섹션 표시 & 메뉴 활성화
        document.getElementById(targetId).classList.remove('d-none');
        this.parentElement.classList.add('active');
        
        // 브레드크럼 타이틀 업데이트
        breadcrumbTitle.textContent = menuText;
    }
    
    // 메뉴 클릭 이벤트 리스너 등록
    menuLinks.forEach(link => {
        link.addEventListener('click', handleMenuClick);
    });
    
    // 초기 브레드크럼 타이틀 설정
    const activeMenuItem = document.querySelector('.sidebar-menu li.active a span');
    if (activeMenuItem) {
        breadcrumbTitle.textContent = activeMenuItem.textContent;
    }
    
});




document.getElementById('userEditForm').addEventListener('submit', async function(e) {
    e.preventDefault();

    // 비밀번호 검증
    const password = $("#userPassword").val();
    const confirmPassword = $("#confirmPassword").val();
    
    if (password && password !== confirmPassword) {  // 비밀번호가 입력된 경우만 검증
        alert("비밀번호가 일치하지 않습니다.");
        return;
    }

    const formData = new FormData(this);
    
    // confirmPassword 필드 제거
    formData.delete('confirmPassword');

    try {
        const response = await fetch('/api/user/update', {
            method: 'POST',
            body: formData,
            headers: {
                'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]')?.content
            }
        });

        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }

        const data = await response.json();

        if (data.success) {
            alert('정보가 성공적으로 수정되었습니다.');
            // 수정된 정보를 바로 반영하기 위해 페이지 새로고침
            window.location.reload();
        } else {
            alert(data.message || '정보 수정에 실패했습니다.');
        }
    } catch (error) {
        console.error('Error:', error);
        alert('정보 수정 중 오류가 발생했습니다.');
    }
});





