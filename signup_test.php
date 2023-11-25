<?php

error_reporting(E_ALL);
ini_set("display_errors", 1);

// 데이터베이스 연결
$con = mysqli_connect("52.78.52.80", "dy", "dy4482", "User");
if (!$con) {
    die("데이터베이스 연결 실패: " . mysqli_connect_error());
}
mysqli_query($con, 'SET NAMES utf8');

// POST로 전송된 값 받기 (front에서 POST안에 있는 변수명으로 보내주세요)
$ID = $_POST["ID"];
$Password = $_POST["Password"];
$UserName = $_POST["UserName"];
$NickName = $_POST["NickName"];
$PhoneNum = $_POST["PhoneNum"];

// 현재 시간 생성
$now = date('Y-m-d H:i:s');

// 회원 가입 요청 처리
$statement = mysqli_prepare($con, "INSERT INTO User (ID, password, userName, nickName, phoneNum, createAt, updateAt) VALUES (?, ?, ?, ?, ?, ?, ?)");
mysqli_stmt_bind_param($statement, "sssssss", $ID, $Password, $UserName, $NickName, $PhoneNum, $now, $now);

if (mysqli_stmt_execute($statement)) {
    $response = array(
        "success" => true,
        "message" => "회원 가입 요청이 성공적으로 처리되었습니다."
    );
} else {
    $response = array(
        "success" => false,
        "message" => "회원 가입 요청 처리 중 오류가 발생했습니다."
    );
}

// 응답을 JSON 형식으로 출력
header('Content-Type: application/json');
echo json_encode($response);

// 데이터베이스 연결 닫기
mysqli_close($con);

?>
