<?php
function validateToken($token, $sessionid) {
    $url = "https://localhost:8000/";
    $data = "{\"ValidateToken\":{\"token\":\"" . $token . "\",\"sessionID\":\"" . $sessionid . "\"}}";

    $options = array(
        'Content-Type:application/json',
    );

    $ch = curl_init($url);
    curl_setopt($ch, CURLOPT_POSTFIELDS, $data );
    curl_setopt($ch, CURLOPT_HTTPHEADER, $options);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
    curl_setopt($ch, CURLOPT_SSL_VERIFYHOST, false);
    curl_setopt($ch, CURLOPT_SSL_VERIFYSTATUS, false);
    curl_setopt($ch, CURLOPT_POST, true);

    $result = curl_exec($ch);

    $code = json_decode($result)->{"ValidationResponse"}->{"result"};
    return $code;
}
?>