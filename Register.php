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
    $level = 1;
    $experience = 0;
    $hock_coins = 0;

    function registerUser() {
        global $connect, $username, $email, $password, $level, $experience, $hock_coins;

        $passwordHash = password_hash($password, PASSWORD_BCRYPT);
        
        $statement = mysqli_prepare($connect, "INSERT INTO user (username, email, password, level, experience, hock_coins) VALUES (?, ?, ?, ?, ?, ?)");
        mysqli_stmt_bind_param($statement, "sssiii", $username, $email, $passwordHash, $level, $experience, $hock_coins);
        mysqli_stmt_execute($statement);
        mysqli_stmt_close($statement);
    }

    function usernameAvailable() {
        global $connect, $username;

        $statement = mysqli_prepare($connect, "SELECT * FROM user WHERE username = ?");
        mysqli_stmt_bind_param($statement, "s", $username);
        mysqli_stmt_execute($statement);
        mysqli_stmt_store_result($statement);
        $count = mysqli_stmt_num_rows($statement);
        mysqli_stmt_close($statement);

        if ($count < 1) {
            return true;
        } else {
            return false;
        }
    }

    function emailAvailable() {
        global $connect, $email;

        $statement = mysqli_prepare($connect, "SELECT * FROM user WHERE email = ?");
        mysqli_stmt_bind_param($statement, "s", $email);
        mysqli_stmt_execute($statement);
        mysqli_stmt_store_result($statement);
        $count = mysqli_stmt_num_rows($statement);
        mysqli_stmt_close($statement);

        if ($count < 1) {
            return true;
        } else {
            return false;
        }
    }

    $response = array();
    //$response["username"] = $username;
    //$response["email"] = $email;
    //$response["password"] = $password;
    //$response["level"] = $level;
    //$response["experience"] = $experience;
    //$response["hock_coins"] = $hock_coins;
    $response["success"] = false;

    if (usernameAvailable() && emailAvailable()) {
        registerUser();
        $response["success"] = true;
        //$response["username"] = $username;
        //$response["email"] = $email;
        //$response["password"] = $password;
        //$response["level"] = $level;
        //$response["experience"] = $experience;
        //$response["hock_coins"] = $hock_coins;
    }

    echo json_encode($response);
?>