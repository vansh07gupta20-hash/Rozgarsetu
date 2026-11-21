const adminId = sessionStorage.getItem("adminId");
const message = document.getElementById("message");
const profileDetails = document.getElementById("profileDetails");

document.getElementById("logoutButton").addEventListener("click", () => {
    sessionStorage.removeItem("adminId");
    sessionStorage.removeItem("adminName");
    sessionStorage.removeItem("adminEmail");

    window.location.href = "../index.html";
});

async function loadAdminProfile() {
    if (!adminId) {
        window.location.href = "admin-login.html";
        return;
    }

    try {
        const response = await fetch(
            `/api/admin/${encodeURIComponent(adminId)}`
        );

        if (response.status === 404) {
            message.textContent = "Admin profile was not found.";
            message.classList.add("error");
            return;
        }

        if (!response.ok) {
            message.textContent = "Could not load the admin profile.";
            message.classList.add("error");
            return;
        }

        const admin = await response.json();

        document.getElementById("adminId").textContent = admin.adminId;
        document.getElementById("adminName").textContent = admin.name;
        document.getElementById("adminEmail").textContent = admin.email;

        message.textContent = "";
        profileDetails.hidden = false;
    } catch (error) {
        message.textContent =
            "Cannot connect to the server. Check that Spring Boot is running.";
        message.classList.add("error");
    }
}

loadAdminProfile();