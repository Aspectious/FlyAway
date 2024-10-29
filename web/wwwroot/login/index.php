<?php include("../loader.php")?>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8"/>
        <link rel="icon" href="/img/flyaway-logo-filled.ico" type="image/x-icon"/>
        <link rel="stylesheet" href="/login/index.css"/>
        <script>
            const sessionID = "<?php echo session_id()?>";
        </script>
        <script src="http://cdn.rawgit.com/h2non/jsHashes/master/hashes.js"></script>
        <script src="index.js"></script>
        <title><?php include('../components/titlemode')?> - Login</title>
    </head>
<body>
<div id="root">
    <div class="login-modal">
        <div class="login-modal-content">
            <div class="login-modal-content-header">
                <img class="login-modal-icon" src="/login/img/flyaway-logo.png" alt="flyaway logo"></img>
                <div class="login-modal-title">Sign In</div>
            </div>
            <div class="login-modal-cred-panel">
                <input type="text" id="uname" class="login-modal-cred-input" placeholder="Username"></input>
                <input type="password" id="pwd" class="login-modal-cred-input" placeholder="Password"></input>
                <button class="login-modal-cred-button" onclick="login()">Login</button>
            </div>
        </div>
    </div>
    <div class="footer">Flyaway Version <?php include('../components/version'); ?> | Authored by Aspectious and Sushibirb</div>
</div>
</body>
</html>