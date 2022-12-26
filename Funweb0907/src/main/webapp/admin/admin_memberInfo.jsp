<%@page import="java.text.SimpleDateFormat"%>
<%@page import="member.MemberDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="member.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
<html>
<head>
<meta charset="UTF-8">
<title>admin/memberInfo</title>
</head>
<body>
	<table border="1">
		<tr>
			<td width="100px">id</td><td width="100px">이름</td><td width="180px">이메일</td><td width="300px">주소</td>
			<td width="150px">휴대전화</td><td width="120px">가입일자</td>
		</tr>
		<%
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		// 회원 목록 조회를 위해 MemberDAO 객체 생성
		MemberDAO dao = new MemberDAO();
		
		// ---------------------------------------------------------------------------------------------
		
		// MemberDAO 객체의 selectMemberList() 메소드를 호출하여 게시물 목록 조회
		// => 파라미터 : 없음   리턴타입 : ArrayList<MemberDTO>(memberList)
		ArrayList<MemberDTO> memberList = dao.selectMemberList();
		
		for(MemberDTO member : memberList) {
		%>
		
		<tr onclick="location.href='admin_memberDetail.jsp?id=<%=member.getId() %>&name=<%=member.getName() %>'">
			<td><%=member.getId() %></td>
			<td><%=member.getName() %></td>
			<td><%=member.getEmail() %></td>
			<td><%=member.getAddress1() %> <%=member.getAddress2() %></td>
			<td><%=member.getMobile() %></td>
			<td><%=sdf.format(member.getDate()) %></td>
		</tr>
		<%} %>
	</table>
</body>
</html>