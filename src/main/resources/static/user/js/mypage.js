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