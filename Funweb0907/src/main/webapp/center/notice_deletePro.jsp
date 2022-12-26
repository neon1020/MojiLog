<%@page import="board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 글번호(num), 패스워드(pass) 파라미터 가져오기
int num = Integer.parseInt(request.getParameter("num"));
String pass = request.getParameter("pass");


// BoardDAO() 객체 생성 후 deleteBoard() 메소드 호출하여 글 삭제 작업 수행
// => 파라미터 : 글번호, 패스워드     리턴타입 : int(deleteCount)
BoardDAO dao = new BoardDAO();
int deleteCount = dao.deleteBoard(num, pass);

if(deleteCount > 0) {
	response.sendRedirect("notice.jsp");
} else { %>
<script>
	alert("삭제 실패!");
	history.back()
</script>

<%
}
%>