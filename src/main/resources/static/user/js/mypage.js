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




document.getElementById('userEditForm').addEventListener('submit', function(e) {
    e.preventDefault();
    
    const formData = new FormData(this);
    
    fetch('/api/user/update', {
        method: 'POST',
        body: formData,
        headers: {
            // ContentType은 FormData를 사용할 때는 설정하지 않습니다.
            // multipart/form-data로 자동 설정됨
            'X-CSRF-TOKEN': document.querySelector('meta[name="_csrf"]')?.content
        }
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        return response.json();
    })
    .then(data => {
        if (data.success) {
            alert('정보가 성공적으로 수정되었습니다.');
            window.location.reload();
        } else {
            alert(data.message || '정보 수정에 실패했습니다.');
        }
    })
    .catch(error => {
        console.error('Error:', error);
        alert('정보 수정 중 오류가 발생했습니다.');
    });
});





