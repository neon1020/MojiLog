<%@page import="moji.MojiDAO"%>
<%@page import="board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 글번호(num), 패스워드(pass) 파라미터 가져오기
int num = Integer.parseInt(request.getParameter("num"));

MojiDAO dao = new MojiDAO();
int deleteCount = dao.deleteMoji(num);

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