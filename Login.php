<?php
/**
 * Created by PhpStorm.
 * User: melvin
 * Date: 22/11/16
 * Time: 22:11
 */

    $connect = mysqli_connect("melvinschultz.fr.mysql", "melvinschultz_fr", "JTuuzCaQ", "melvinschultz_fr");

    $email = $_POST["email"];
    $password = $_POST["password"];

    $statement = mysqli_prepare($connect, "SELECT * FROM user WHERE user_email = ? AND password = ?");
    mysqli_stmt_bind_param($statement, "ss", $email, $password);
    mysqli_stmt_execute($statement);

    mysqli_stmt_store_result($statement);
    mysqli_stmt_bind_result($statement, $id, $name, $email, $password, $age);

    $response = array();
    $response["success"] = false;

    while (mysqli_stmt_fetch($statement)){
        $response["success"] = true;
        $response["name"] = $name;
        $response["email"] = $email;
        $response["password"] = $password;
        $response["age"] = $age;
    }

    echo json_encode($response);
?>