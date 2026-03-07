// Login.js



    function fetchUser(token ) {

        fetch( BASE_URL + "api/users/me" , {
            method: "GET",
            headers: {
                'Authorization': `Bearer `+token
            }
        })
            .then(res => {
                if (!res.ok) throw new Error("Failed to fetch user");
                return res.json();
            })
            .then(user => {

                sessionStorage.setItem("userName", user.fullName);
                sessionStorage.setItem("userRole", user.role);

               
            })
            .catch(err => {
                showAlert(err.message, "danger");
            });
    }
 




clearAuthData() ;
     BASE_URL = getBaseUrl();  
  document.addEventListener("DOMContentLoaded", function () {

    const loginForm = document.getElementById("loginForm");
    const errorDiv = document.getElementById("error");
    const loadingDiv = document.getElementById("loading");
    const loginButton = loginForm.querySelector("button[type='submit']");

    loginForm.addEventListener("submit", async function (e) {
        e.preventDefault();

        const username = document.getElementById("username").value.trim();
        const password = document.getElementById("password").value.trim();

        errorDiv.textContent = "";
        loadingDiv.style.display = "block";  
        loginButton.disabled = true;         

        try {
            const formData = new URLSearchParams();
            formData.append("username", username);
            formData.append("password", password);

            const response = await fetch("api/auth", {
                method: "POST",
                body: formData
            });

            const data = await response.json();

            if (response.ok && data.success) {
                sessionStorage.setItem("token", data.token);
                await fetchUser(data.token);
                await loadRoleFeatures();

                // small delay so spinner is visible
                await new Promise(resolve => setTimeout(resolve, 500));

                window.location.href = BASE_URL + "Dashboard.html";
            } else {
                errorDiv.textContent = data.message || "Login failed";
            }

        } catch (err) {
            console.error(err);
            errorDiv.textContent = "Server error. Try again.";
        } finally {
            loadingDiv.style.display = "none"; // hide spinner
            loginButton.disabled = false;       // re-enable button
        }
    });

});
