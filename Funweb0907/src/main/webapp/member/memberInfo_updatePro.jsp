<%@page import="member.MemberDAO"%>
<%@page import="member.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");

// 폼 파라미터 가져오기
String id = request.getParameter("id");
String pass = request.getParameter("pass");
String email = request.getParameter("email");
String post_code = request.getParameter("post_code");
String address1 = request.getParameter("address1");
String address2 = request.getParameter("address2");
String phone = request.getParameter("phone");
String mobile = request.getParameter("mobile");

// memberDTO 객체 생성 후 값 저장
MemberDTO member = new MemberDTO();
member.setId(id);
member.setPass(pass);
member.setEmail(email);
member.setPost_code(post_code);
member.setAddress1(address1);
member.setAddress2(address2);
member.setPhone(phone);
member.setMobile(mobile);

//회원 정보 수정 작업 수행하는 updateMemberInfo() 메소드
//=> 파라미터 : MemberDTO 객체, 리턴타입 : int(updateCount)
MemberDAO dao = new MemberDAO();
int updateCount = dao.updateMemberInfo(member);

if(updateCount > 0) {
	response.sendRedirect("memberInfo.jsp?id="+id);
} else {
	%>
	<script>
		alert("회원 정보 수정 실패!");
		history.back();
	</script>
	<%
}

%>