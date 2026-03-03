<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Hotel System Login</title>

  <style>
    body {
      font-family: Arial, sans-serif;
      background: #f4f6f9;
      display: flex;
      height: 100vh;
      justify-content: center;
      align-items: center;
    }

    .login-box {
      background: white;
      padding: 30px;
      width: 320px;
      box-shadow: 0 0 10px rgba(0,0,0,0.1);
      border-radius: 8px;
    }

    h2 { text-align: center; }

    input, select {
      width: 100%;
      padding: 10px;
      margin-bottom: 12px;
      border: 1px solid #ccc;
      border-radius: 5px;
    }

    button {
      width: 100%;
      padding: 10px;
      background: #2c7be5;
      border: none;
      color: white;
      font-size: 16px;
      border-radius: 5px;
      cursor: pointer;
    }

    .error {
      color: red;
      text-align: center;
      margin-top: 10px;
    }
  </style>
</head>

<body>

<div class="login-box">

  <h2>Hotel Login</h2>

  <form id="loginForm">

    <input type="text" id="username" placeholder="Username" required>
    <input type="password" id="password" placeholder="Password" required>

    <select id="role">
      <option value="Admin">Admin</option>
      <option value="Staff">Staff</option>
    </select>

    <button type="submit">Login</button>

  </form>

  <div id="error" class="error"></div>

</div>

<script>
  document.getElementById("loginForm").addEventListener("submit", async function(e) {
    e.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const role = document.getElementById("role").value;
    const errorDiv = document.getElementById("error");

    try {
      const formData = new URLSearchParams();
      formData.append("username", username);
      formData.append("password", password);
      formData.append("role", role);

      // 🔥 Updated endpoint for AuthController
      const response = await fetch("<%=request.getContextPath()%>/api/auth", {
        method: "POST",
        body: formData
      });

      const data = await response.json();

      if (response.ok && data.success) {

        console.log("Login data:", data);

        // Save token for later API calls
        sessionStorage.setItem("authToken", data.token);

        alert(data.message || "Login successful");

        // Redirect after login
        window.location.href = "Billing.html";

      } else {
        errorDiv.textContent = data.message || "Login failed";
      }

    } catch (err) {
      console.error(err);
      errorDiv.textContent = "Server error. Try again.";
    }
  });

  // Optional: logout example
  async function logout() {
    const token = sessionStorage.getItem("authToken");
    if (!token) return;

    const response = await fetch("<%=request.getContextPath()%>/api/auth", {
      method: "DELETE",
      headers: { 'Authorization': token }
    });

    const data = await response.json();
    console.log(data);

    if (data.success) {
      sessionStorage.removeItem("authToken");
      alert("Logged out successfully");
      window.location.href = "Login.jsp";
    }
  }
</script>

</body>
</html>