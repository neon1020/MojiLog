<%@page import="member.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
String id = request.getParameter("id");
String name = request.getParameter("name");

MemberDAO dao = new MemberDAO();
int boardCount = dao.boardCount(id);
int replyCount = dao.replyCount(id);
%>
<html>
<link href="../css/default.css" rel="stylesheet" type="text/css">
<link href="../css/subpage.css" rel="stylesheet" type="text/css">
<style>
	body {
		background-color: #FFFFCC;
	}
	
	tr, td {
		text-align: center;
	}
</style>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<table border="1">
	<tr>
		<td width="70px">id</td><td width="100px">이름</td><td width="100px">게시글 수</td><td width="100px">댓글 수</td>
	</tr>
	<tr>
		<td><%=id %></td>
		<td><%=name %></td>
		<td><%=boardCount %> 개</td>
		<td><%=replyCount %> 개</td>
	</tr>
</table>
</body>
</html>