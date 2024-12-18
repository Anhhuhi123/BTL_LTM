(function() {
    "use strict";
    
    // Header toggle function
    const headerToggleBtn = document.querySelector('.header-toggle');
    function headerToggle() {
        document.querySelector('#header').classList.toggle('header-show');
        headerToggleBtn.classList.toggle('bi-list');
        headerToggleBtn.classList.toggle('bi-x');
    }
    headerToggleBtn.addEventListener('click', headerToggle);

    // Load content function
    function loadContent(url) {
        $.ajax({
            url: url,
            method: 'GET',
            success: function(response) {
                $('#content-container').html(response);
            },
            error: function() {
                $('#content-container').html('<p>Error loading content</p>');
            }
        });
    }

    // Navigation click handlers
    $('.nav-link').click(function(e) {
        e.preventDefault();
        
        // Remove active class from all links
        $('.nav-link').removeClass('active');
        // Add active class to clicked link
        $(this).addClass('active');
        
        // Load content
        const page = $(this).data('page'); // Lấy giá trị từ thuộc tính data-page
        if (page) {
            loadContent(page);
        }

        // Close mobile menu if open
        if (document.querySelector('.header-show')) {
            headerToggle();
        }
    });

    // Load home content by default
    loadContent('convert.jsp');
})();
