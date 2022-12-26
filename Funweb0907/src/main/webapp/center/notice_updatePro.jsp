<%@page import="board.BoardDAO"%>
<%@page import="board.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");

// 글번호, 작성자, 패스워드, 제목, 내용 파라미터 가져와서 BoardDTO 객체에 저장
BoardDTO board = new BoardDTO();
int num = Integer.parseInt(request.getParameter("num"));

board.setNum(num);
board.setName(request.getParameter("name"));
board.setPass(request.getParameter("pass"));
board.setSubject(request.getParameter("subject"));
board.setContent(request.getParameter("content"));

// BoardDAO 객체 생성 후 updateBoard() 메소드 호출하여 게시물 수정 작업 수행
// => 파라미터 : BoardDTO 객체, 리턴타입 : int(deleteCount)
BoardDAO dao = new BoardDAO();
int deleteCount = dao.updateBoard(board);

if(deleteCount > 0) {
	response.sendRedirect("notice_content.jsp?num=" + num);
} else { %>
<script>
	alert("글 수정 실패!");
	history.back();
</script>
<%
}
%>