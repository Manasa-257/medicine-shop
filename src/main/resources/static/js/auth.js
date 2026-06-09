function handleLogin() {
    const email = document.getElementById('loginEmail').value;
    const password = document.getElementById('loginPassword').value;

    if (!email || !password) {
        showAlert('loginAlert', 'Please fill all fields!', 'error');
        return;
    }

    API.login({ email, password })
        .then(res => res.json())
        .then(data => {
            if (data.token) {
                localStorage.setItem('token', data.token);
                localStorage.setItem('userId', data.userId);
                localStorage.setItem('user', JSON.stringify({
                    name: data.name,
                    role: data.role
                }));
                showAlert('loginAlert', 'Login successful!', 'success');
                setTimeout(() => window.location.href = 'index.html', 1000);
            } else {
                showAlert('loginAlert', data.message, 'error');
            }
        });
}

function handleRegister() {
    const name = document.getElementById('regName').value;
    const email = document.getElementById('regEmail').value;
    const password = document.getElementById('regPassword').value;
    const phone = document.getElementById('regPhone').value;
    const address = document.getElementById('regAddress').value;

    if (!name || !email || !password) {
        showAlert('registerAlert', 'Please fill all required fields!', 'error');
        return;
    }

    API.register({ name, email, password, phone, address })
        .then(res => res.json())
        .then(data => {
            showAlert('registerAlert', data.message, 'success');
            setTimeout(() => showLogin(), 1500);
        });
}

function showRegister() {
    document.getElementById('loginForm').style.display = 'none';
    document.getElementById('registerForm').style.display = 'block';
}

function showLogin() {
    document.getElementById('registerForm').style.display = 'none';
    document.getElementById('loginForm').style.display = 'block';
}

function showAlert(id, message, type) {
    document.getElementById(id).innerHTML =
        `<div class="alert alert-${type}">${message}</div>`;
}