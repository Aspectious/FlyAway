<?php
header("Access-Control-Allow-Origin: *");
session_save_path(__DIR__ . "\sessions\\");
session_start();
if ((!isset($_SESSION["loggedin"]) || $_SESSION["loggedin"] !== true)&&($_SERVER["REQUEST_URI"]!="/login/")) {
    header("location: /login/");
}
/*

*/
?>