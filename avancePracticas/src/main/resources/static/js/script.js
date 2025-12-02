document.addEventListener('DOMContentLoaded', function () {
    const sidebar = document.getElementById('sidebar');
    const collapseBtn = document.getElementById('collapse-btn');
    const expandBtn = document.getElementById('expand-btn');
    const filterBtn = document.getElementById('filter-btn');
    const filterMenu = document.getElementById('filter-menu');
    const selectAllCheckbox = document.getElementById('select-all');
    const expenseCheckboxes = document.querySelectorAll('.expense-checkbox');
    const expandExpensesBtns = document.querySelectorAll('.expand-expenses-btn');
    const themeToggleCheckBox = document.getElementById('theme-checkbox');

    collapseBtn.addEventListener('click', () => {
        sidebar.classList.add('collapsed');
        collapseBtn.style.display = 'none';
        expandBtn.style.display = 'inline-block';
        localStorage.setItem('sidebarState', 'collapsed');
    });

    expandBtn.addEventListener('click', () => {
        sidebar.classList.remove('collapsed');
        expandBtn.style.display = 'none';
        collapseBtn.style.display = 'inline-block';
        localStorage.removeItem('sidebarState');
    });

    filterBtn.addEventListener('click', (event) => {
        event.stopPropagation();
        filterMenu.style.display = filterMenu.style.display === 'block' ? 'none' : 'block';
    });

    document.addEventListener('click', (event) => {
        if (!filterMenu.contains(event.target) && event.target !== filterBtn) {
            filterMenu.style.display = 'none';
        }
    });

    selectAllCheckbox.addEventListener('change', function () {
        expenseCheckboxes.forEach(checkbox => {
            checkbox.checked = this.checked;
        });
    });

    expandExpensesBtns.forEach(btn => {
        btn.addEventListener('click', function () {
            const expensesRow = this.parentElement.parentElement.nextElementSibling;
            if (expensesRow.style.display === 'table-row') {
                expensesRow.style.display = 'none';
                this.textContent = '▼';
            } else {
                expensesRow.style.display = 'table-row';
                this.textContent = '▲';
            }
        });
    });

    themeToggleCheckBox.addEventListener('change', () => {
        document.body.classList.toggle('dark-mode');
        // Save theme preference to localStorage
        if (document.body.classList.contains('dark-mode')) {
            localStorage.setItem('theme', 'dark-mode');
        } else {
            localStorage.removeItem('theme');
        }
    });

    // Check for saved theme preference
    if (localStorage.getItem('theme') === 'dark-mode') {
        document.body.classList.add('dark-mode');
        themeToggleCheckBox.checked = true;
    }

    if (localStorage.getItem('sidebarState') === 'collapsed') {
        sidebar.classList.add('collapsed');
        collapseBtn.style.display = 'none';
        expandBtn.style.display = 'inline-block';
    }

});
