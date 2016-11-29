<?php
/**
 * Created by PhpStorm.
 * User: melvin
 * Date: 22/11/16
 * Time: 22:11
 */
    include '../mediacenter-v1/inc/htpasswd.php';

    $connect = mysqli_connect($host, $user, $password, $dbname);

    $email = $_POST["email"];
    $password = $_POST["password"];

    $statement = mysqli_prepare($connect, "SELECT * FROM user WHERE email = ? AND password = ?");
    mysqli_stmt_bind_param($statement, "ss", $email, $password);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $id, $username, $email, $password);

    $response = array();
    $response["success"] = false;

    while (mysqli_stmt_fetch($statement)){
        $response["success"] = true;
        $response["username"] = $username;
        $response["email"] = $email;
        $response["password"] = $password;
    }

    echo json_encode($response);
?>