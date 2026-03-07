<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" /><!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Hotel System Login</title>
    <link rel="stylesheet" href="Css/Styles.css">
    <style>
 
.spinner {
    margin: 0 auto;
    width: 40px;
    height: 40px;
    border: 4px solid #ddd;
    border-top: 4px solid var(--primary-teal);
    border-radius: 50%;
    animation: spin 1s linear infinite;
}

@keyframes spin {
    to { transform: rotate(360deg); }
}
    </style>

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

                

                <button type="submit" class="btn btn-primary btn-block">Login</button>

                <div id="loading" class="loading" style="display:none; text-align:center; margin: 15px 0;">
                    <div class="spinner"></div>
                     <p>Logging in...</p>
                    </div>

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