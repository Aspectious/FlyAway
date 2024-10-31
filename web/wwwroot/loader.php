<?php
header("Access-Control-Allow-Origin: *");
session_save_path(__DIR__ . "\sessions\\");
session_start();


if ((!isset($_SESSION["loggedin"]) || $_SESSION["loggedin"] !== true)&&($_SERVER["REQUEST_URI"]!="/login/")&&(!str_starts_with($_SERVER["REQUEST_URI"],"/login/result"))) {
    header("location: /login/");
}
/*

*/
?>