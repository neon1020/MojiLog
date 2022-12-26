<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String sId = (String)session.getAttribute("sId"); // Object -> String 다운캐스팅 필요
if(sId == null || !sId.equals("moji")) {
	%>
	<script>
		alert("잘못된 접근입니다!");
		location.href = "../main/main.jsp";
	</script>
	<%
}
%>
<!DOCTYPE html>
<link href="../css/default.css" rel="stylesheet" type="text/css">
<link href="../css/subpage.css" rel="stylesheet" type="text/css">
<style>
	body {
		background-color: #FFFFCC;
	}
</style>
<html>
<head>
<meta charset="UTF-8">
<title>admin/admin_main</title>
</head>
<body>
	<h1>관리자 페이지</h1>
	<ul>
		<li><a href="admin_memberInfo.jsp">회원 관리</a></li>
	</ul>
</body>
</html>