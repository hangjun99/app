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

	$statement = mysqli_prepare($con, "select * from User where ID = ?");
	mysqli_stmt_bind_param($statement, "s", $ID);
	mysqli_stmt_execute($statement);

	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $userInd, $ID, $password, $userName, $NickName, $phoneNum, $status, $createAt, $updateAt);

	$response = array();
	$response["success"] = false;

	if(mysqli_stmt_fetch($statement)) 
	{
		//이미 ID이 있다.
		$response["success"] = false;
	} else {
		//ID 없음 써도 됨.
    		$response["success"] = true;
	}

	echo json_encode($response);

	mysqli_close($con);

?>