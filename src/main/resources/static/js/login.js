const loginForm = document.getElementById("adminLoginForm");
const loginButton = loginForm.querySelector(".login-button");
const buttonText = loginButton.querySelector("span");
const message = document.getElementById("loginMessage");

loginForm.addEventListener("submit", async (event) => {
    event.preventDefault();

    message.textContent = "";
    loginButton.disabled = true;
    buttonText.textContent = "Signing in...";

    const email = document.getElementById("email").value.trim();
    const password = document.getElementById("password").value;

    try {
        const response = await fetch("/api/admin/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ email, password })
        });

        if (response.status === 401) {
            message.textContent = "Invalid email or password.";
            return;
        }

        if (response.status === 400) {
            message.textContent = "Enter a valid email and password.";
            return;
        }

        if (!response.ok) {
            message.textContent = "Login failed. Please try again.";
            return;
        }

        const admin = await response.json();

        // Store display details only; never store the password.
        sessionStorage.setItem("adminId", admin.adminId);
        sessionStorage.setItem("adminName", admin.name);
        sessionStorage.setItem("adminEmail", admin.email);

        window.location.href = "admin-profile.html";
    } catch (error) {
        message.textContent =
            "Cannot connect to the server. Check that Spring Boot is running.";
    } finally {
        loginButton.disabled = false;
        buttonText.textContent = "Sign in to dashboard";
    }
});