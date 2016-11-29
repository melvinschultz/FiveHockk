<?php
/**
 * Created by PhpStorm.
 * User: melvin
 * Date: 22/11/16
 * Time: 22:08
 */
    include '../mediacenter-v1/inc/htpasswd.php';

    $connect = mysqli_connect($host, $user, $password, $dbname);

    $username = $_POST["username"];
    $email = $_POST["email"];
    $password = $_POST["password"];

//    $password_hash = password_hash($password, PASSWORD_BCRYPT);

    $statement = mysqli_prepare($connect, "INSERT INTO user (username, email, password) VALUES (?, ?, ?)");
    mysqli_stmt_bind_param($statement, "sss", $username, $email, $password);
    mysqli_stmt_execute($statement);

    $response = array();
    $response["username"] = $username;
    $response["email"] = $email;
    $response["password"] = $password;
    $response["success"] = true;

    echo json_encode($response);
?>