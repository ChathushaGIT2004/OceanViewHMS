<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hotel System Login</title>
    <link rel="stylesheet" href="Css/Styles.css">

</head>
<body>

<div class="app-container" style="justify-content: center; align-items: center; display: flex; height: 100vh;">

    <div class="card" style="width: 360px; padding: var(--space-xl); box-shadow: var(--shadow-lg);">

        <div class="card-header">
            <h2>Ocean View HMS</h2>
        </div>
        <div class="card-header">
            <h3>Login</h3>
        </div>

        <div class="card-body">
            <form id="loginForm">

                <div class="form-group">
                    <label class="form-label" for="username">Username</label>
                    <input type="text" id="username" class="form-control" placeholder="Enter username" required>
                </div>

                <div class="form-group">
                    <label class="form-label" for="password">Password</label>
                    <input type="password" id="password" class="form-control" placeholder="Enter password" required>
                </div>

                <div class="form-group" style="visibility: hidden;">
                    <label class="form-label" for="role">Role</label>
                    <select id="role" class="form-control">
                        <option value="Admin">Admin</option>
                        <option value="Staff">Staff</option>
                    </select>
                </div>

                <button type="submit" class="btn btn-primary btn-block">Login</button>

                <div id="error" class="form-text error"></div>
            </form>
        </div>
    </div>
</div>

<!-- JS extracted to separate file -->
<script src="Js/Config.js"></script>
<script src="Js/FeatureManager.js"></script>
<script src="Js/Login.js"></script>

</body>
</html>