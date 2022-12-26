<%@page import="member.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%
String id = request.getParameter("idCheck");

MemberDAO dao = new MemberDAO();
boolean isDup = dao.checkID(id);
String result = "";
if(isDup) {
	%>
	<script>
		alert("이미 사용 중인 아이디입니다.");
		location.href="check_id.jsp";
	</script>
	<%
} else {
	if(id.length() < 4 || id.length() > 10) {
		%>
		<script>
			alert("아이디는 4 ~ 10 글자만 가능합니다.");
			location.href="check_id.jsp";
		</script>
		<%
	} else {
		result = "사용 가능한 아이디입니다.";%>
		<script>
		window.opener.document.getElementById("id").value = "<%=id %>";
		</script>
	<%}
	}%>
</head>
<body>
	<h1>ID 중복 확인</h1>
		<input type="text" name="idCheck" id="idCheck" value="<%=id %>"><br>
		<span id="checkIdResult"><%=result %></span><br>
		<input type="button" value="닫기" onclick="window.close()">
</body>
</html>