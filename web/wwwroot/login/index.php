<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8"/>
        <link rel="icon" href="/img/flyaway-logo-filled.ico" type="image/x-icon"/>
        <link rel="stylesheet" href="./index.css"/>
        <title><?php include('../components/titlemode')?> - Login</title>
    </head>
<body>
<div id="root">
    <div class="login-modal">
        <div class="login-modal-content">
            <div class="login-modal-content-header">
                <img class="login-modal-icon" src="img/flyaway-logo.png" alt="flyaway logo"></img>
                <div class="login-modal-title">Sign In</div>
            </div>
            <div class="login-modal-cred-panel">
                <input type="text" class="login-modal-cred-input" placeholder="Username"></input>
                <input type="password" class="login-modal-cred-input" placeholder="Password"></input>
                <button class="login-modal-cred-button">Login</button>
            </div>
        </div>
    </div>
    <div class="footer">Flyaway Version <?php include('../components/version'); ?> | Authored by Aspectious and Sushibirb</div>
</div>
</body>
</html>