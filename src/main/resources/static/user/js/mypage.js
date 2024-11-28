// Wait for the DOM to be fully loaded
document.addEventListener('DOMContentLoaded', function() {
    // Get all menu items from the sidebar
    const menuItems = document.querySelectorAll('.sidebar-menu ul li a');
    // Get the breadcrumb title element
    const breadcrumbTitle = document.querySelector('.breadcrumb-title');

    // Add click event listener to each menu item
    menuItems.forEach(item => {
        item.addEventListener('click', function(e) {
            e.preventDefault(); // Prevent default link behavior
            
            // Remove active class from all menu items
            menuItems.forEach(menuItem => {
                menuItem.parentElement.classList.remove('active');
            });
            
            // Add active class to clicked menu item
            this.parentElement.classList.add('active');
            
            // Get the text content of the span element within the clicked link
            const menuText = this.querySelector('span').textContent;
            
            // Update the breadcrumb title
            breadcrumbTitle.textContent = menuText;
        });
    });

    // Set initial title based on active menu item (if any)
    const activeMenuItem = document.querySelector('.sidebar-menu ul li.active a span');
    if (activeMenuItem) {
        breadcrumbTitle.textContent = activeMenuItem.textContent;
    }
});