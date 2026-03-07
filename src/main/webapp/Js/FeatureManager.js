// featureManager.js



BASE_URL = getBaseUrl();



function getAuthToken() {
    return sessionStorage.getItem("token");
}


// ==========================
// 🎯 Load Role Features
// ==========================
async function loadRoleFeatures() {

    const token = getAuthToken();

    if (!token) {
        console.error("No auth token found");
        return [];
    }

    try {
        const response = await fetch(`${BASE_URL}api/role/features`, {
            method: "GET",
            headers: {
                "Authorization": token
            }
        });

        if (!response.ok) {
            console.error("Invalid or expired session");

            // Clear everything
            localStorage.removeItem("authToken");
            localStorage.removeItem("features");

            window.location.href = "/login.jsp";
            return [];
        }

        const features = await response.json();
        console.log(features);

        // ✅ Store features in localStorage
        sessionStorage.setItem("features", JSON.stringify(features));

        return features;

    } catch (error) {
        console.error("Error loading features:", error);
        return [];
    }
}


// ==========================
// 📖 Get Stored Features
// ==========================
function getStoredFeatures() {
    const data = sessionStorage.getItem("features");
    return data ? JSON.parse(data) : [];
}


// ==========================
// ✅ Check Permission
// ==========================
function hasFeature(featureName) {
    return getStoredFeatures().includes(featureName);
}


// ==========================
// 🚪 Logout Helper
// ==========================
function clearAuthData() {
    sessionStorage.removeItem("authToken");
    sessionStorage.removeItem("features");
}