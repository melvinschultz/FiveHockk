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

    $statement = mysqli_prepare($connect, "SELECT * FROM users WHERE email = ?");
    mysqli_stmt_bind_param($statement, "s", $email);
    mysqli_stmt_execute($statement);
    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $colId, $colUsername, $colEmail, $colPassword);

    $response = array();
    $response["success"] = false;

    while (mysqli_stmt_fetch($statement)){
        if (password_verify($password, $colPassword)) {
            $response["success"] = true;
            $response["username"] = $colUsername;
            $response["email"] = $colEmail;
        }
    }

    echo json_encode($response);
?>