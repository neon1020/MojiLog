<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ID duplication check</title>
</head>
<body>
	<h1>ID 중복 확인</h1>
	<form action="check_idPro.jsp" method="post" name="wfr">
		<input type="text" name="idCheck" required="required"><br>
		<input type="submit" value="중복 확인">
		<span id="checkIdResult"></span>
	</form>
</body>
</html>