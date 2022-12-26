<%@page import="board.ReplyDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
int num = Integer.parseInt(request.getParameter("num"));
int refNum = Integer.parseInt(request.getParameter("refNum"));
String sId = (String) session.getAttribute("sId");

if(sId == null) {
	%>
	<script>
		alert("권한이 없습니다!");
	</script>
	<%
}

ReplyDAO replyDAO = new ReplyDAO();
int deleteCount = replyDAO.deleteReply(num, sId);

if(deleteCount > 0) {
	response.sendRedirect("event_content.jsp?num=" + refNum);
} else {
	%>
	<script>
		alert("댓글 삭제 실패");
	</script>
	<%
}
%>