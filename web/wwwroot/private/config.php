<?php
include("guid.php");

if (isset($_SESSION)) {
    session_start();
    $_SESSION["ssid"] = gen_GUIDv4();
    echo "session not found, starting session: " + $_SESSION["ssid"];
}
?>