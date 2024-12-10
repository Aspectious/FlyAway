<?php include("loader.php");


session_destroy();
unset($_SESSION["loggedin"]);
unset($_SESSION["token"]);

header("location: /login/");
?>

