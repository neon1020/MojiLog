<%@page import="member.MemberDAO"%>
<%@page import="member.MemberDTO"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");

String id = request.getParameter("id");
String pass = request.getParameter("pass");
String name = request.getParameter("name");
String email = request.getParameter("email");
String post_code = request.getParameter("post_code");
String address1 = request.getParameter("address1");
String address2 = request.getParameter("address2");
String phone = request.getParameter("phone");
String mobile = request.getParameter("mobile");

// --------------------------------------------------------------------------------------
MemberDTO member = new MemberDTO();
member.setId(id);
member.setPass(pass);
member.setName(name);
member.setEmail(email);
member.setPost_code(post_code);
member.setAddress1(address1);
member.setAddress2(address2);
member.setPhone(phone);
member.setMobile(mobile);

//---------------------------------------------------------------------------------------
MemberDAO dao = new MemberDAO();

int joinCount = dao.joinMember(member);

if(joinCount > 0) {%>
	<script>
		alert("회원가입 성공!");
		location.href="login.jsp";
	</script>
<%} else {
	%>
	<script>
		alert("회원가입 실패!");
		history.back();
	</script>
<%}%>