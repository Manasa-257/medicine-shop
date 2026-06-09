let allMedicines = [];

// Load medicines when page loads
window.onload = function() {
    loadMedicines();
    loadCategories();
}

function loadMedicines() {
    fetch('http://localhost:8080/api/medicines')
        .then(res => res.json())
        .then(medicines => {
            allMedicines = medicines;
            renderMedicines(medicines);
        })
        .catch(err => {
            console.error('Error loading medicines:', err);
        });
}

function loadCategories() {
    fetch('http://localhost:8080/api/medicines/categories')
        .then(res => res.json())
        .then(categories => {
            const container = document.getElementById('categories');
            if(!container) return;
            container.innerHTML = `
                <button class="category-btn active"
                    onclick="filterCategory(null, this)">All</button>
                ${categories.map(c => `
                    <button class="category-btn"
                        onclick="filterCategory(${c.categoryId}, this)">
                        ${c.name}
                    </button>`).join('')}
            `;
        })
        .catch(err => {
            console.error('Error loading categories:', err);
        });
}

function filterCategory(categoryId, btn) {
    document.querySelectorAll('.category-btn')
        .forEach(b => b.classList.remove('active'));
    btn.classList.add('active');

    if (!categoryId) {
        renderMedicines(allMedicines);
        return;
    }

    fetch(`http://localhost:8080/api/medicines/category/${categoryId}`)
        .then(res => res.json())
        .then(medicines => renderMedicines(medicines));
}

function searchMedicines() {
    const name = document.getElementById('searchInput').value.trim();
    if (!name) {
        renderMedicines(allMedicines);
        return;
    }
    fetch(`http://localhost:8080/api/medicines/search?name=${name}`)
        .then(res => res.json())
        .then(medicines => {
            if(medicines.length === 0) {
                renderMedicines(allMedicines);
            } else {
                renderMedicines(medicines);
            }
        });
}

function clearSearch() {
    document.getElementById('searchInput').value = '';
    renderMedicines(allMedicines);
}

function renderMedicines(medicines) {
    const grid = document.getElementById('medicinesGrid');
    if(!grid) return;

    if (medicines.length === 0) {
        grid.innerHTML = `
            <div class="empty-state">
                <h3>No medicines found</h3>
                <p>Try a different search</p>
            </div>`;
        return;
    }

    grid.innerHTML = medicines.map(m => `
        <div class="medicine-card">
            <div style="width:100%;height:180px;
                background:${getColor(m.medicineId)};
                display:flex;align-items:center;
                justify-content:center;flex-direction:column;
                border-radius:8px 8px 0 0;">
                <span style="font-size:40px">
                    ${getEmoji(m.category)}
                </span>
                <span style="color:white;font-weight:bold;
                    font-size:14px;margin-top:5px;">
                    ${m.name}
                </span>
            </div>
            <div class="card-body">
                <h3>${m.name}</h3>
                <p class="brand">${m.brand || ''}</p>
                ${m.prescriptionRequired ?
                    '<span class="rx-badge">Rx Required</span>' : ''}
                <p class="price">₹${m.price}</p>
                <button class="btn-primary"
                    onclick="addToCart(${m.medicineId})">
                    Add to Cart 🛒
                </button>
            </div>
        </div>
    `).join('');
}

function addToCart(medicineId) {
    const userId = localStorage.getItem('userId');
    if (!userId) {
        alert('Please login first!');
        window.location.href = 'login.html';
        return;
    }

    fetch('http://localhost:8080/api/cart/add', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({
            userId: parseInt(userId),
            medicineId: medicineId,
            quantity: 1
        })
    })
    .then(res => res.json())
    .then(data => {
        alert('Medicine added to cart! 🛒');
    })
    .catch(err => {
        console.error('Error adding to cart:', err);
    });
}

function getColor(id) {
    const colors = [
        '#2563eb', '#10b981', '#f59e0b',
        '#ef4444', '#8b5cf6', '#ec4899',
        '#06b6d4', '#84cc16', '#f97316',
        '#6366f1', '#14b8a6', '#e11d48'
    ];
    return colors[id % colors.length];
}

function getEmoji(category) {
    if (!category) return '💊';
    const name = category.name.toLowerCase();
    if (name.includes('tablet')) return '💊';
    if (name.includes('syrup')) return '🍶';
    if (name.includes('vitamin')) return '⚡';
    if (name.includes('first aid')) return '🩹';
    if (name.includes('skin')) return '🧴';
    return '💊';
}