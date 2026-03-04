// Logout.js
BASE_URL = getBaseUrl(); // global function from config.js

function logoutUser() {
    // Remove auth token and user features from localStorage
    localStorage.removeItem("auth_token");
    localStorage.removeItem("user_features");

    // Optional: call backend API to invalidate session
    fetch(BASE_URL+"/api/logout", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": localStorage.getItem("auth_token") || ""
        }
    }).catch(err => {
        console.warn("Logout API call failed", err);
    });

    // Redirect to login page
    window.location.href = "index.jsp";
}

// Optional: attach logout to any button
document.addEventListener("DOMContentLoaded", () => {
    const logoutBtn = document.getElementById("logoutBtn");
    if (logoutBtn) {
        logoutBtn.addEventListener("click", logoutUser);
    }
});

document.addEventListener('DOMContentLoaded', logoutUser)