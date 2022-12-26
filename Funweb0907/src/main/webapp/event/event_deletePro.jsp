<%@page import="event.EventDAO"%>
<%@page import="moji.MojiDAO"%>
<%@page import="board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 글번호(num), 패스워드(pass) 파라미터 가져오기
int num = Integer.parseInt(request.getParameter("num"));

EventDAO dao = new EventDAO();
int deleteCount = dao.deleteEvent(num);

if(deleteCount > 0) {
	response.sendRedirect("event.jsp");
} else { %>
<script>
	alert("삭제 실패!");
	history.back()
</script>

<%
}
%>