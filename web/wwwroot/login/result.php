<?php
    include("../loader.php");

    $url = "https://localhost:8000/";
    $data = "{\"ValidateToken\":{\"token\":\"" . $_GET[token]} . "\",\"sessionID\":\"" . $_SESSION[];

$options = array(
    'http' => array(
        'method'  => 'POST',
        'content' => json_encode( $data ),
        'header'=>  "Content-Type: application/json\r\n" .
            "Accept: application/json\r\n"
    )
);

$context  = stream_context_create( $options );
$result = file_get_contents( $url, false, $context );
$response = json_decode( $result );
if ($response["code"] == 200) {
    $_SESSION["loggedin"] = true;
    $_SESSION["token"] = $_GET["token"];
    header("Location: /");
} else {

}
?>
