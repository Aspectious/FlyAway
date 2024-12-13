<?php
    include("../loader.php");
    include("../validateToken.php");

    $code = validateToken( $_GET["token"],session_id());
    if ($code == 200) {
        $_SESSION["loggedin"] = true;
        $_SESSION["token"] = $_GET["token"];
        header("Location: /");
    } else {
        header("Location: /login/");
    }
?>