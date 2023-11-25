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

	$statement = mysqli_prepare($con, "select * from User where ID = ? AND Password = ?");
	mysqli_stmt_bind_param($statement, "ss", $ID, $Password);
	mysqli_stmt_execute($statement);

	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $userInd, $ID, $Password, $userName, $nickName, $phoneNum, $status, $createAt, $updateAt);

	$response = array();
	$response["success"] = false;

	if(mysqli_stmt_fetch($statement)) 
	{
		$response["success"] = true;
		$response["userInd"] = $userInd;
		$response["nickName"] = $nickName;
	} else {
    		$response["success"] = false;
	}

	echo json_encode($response);

	mysqli_close($con);

?>