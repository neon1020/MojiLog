<%@page import="board.ReplyDAO"%>
<%@page import="board.ReplyDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
int num = Integer.parseInt(request.getParameter("num"));
String name = request.getParameter("name");
String content = request.getParameter("content");
String boardType = request.getParameter("boardType");
String nickName = request.getParameter("nickName");

// ReplyDTO 객체(reply) 생성하여 파라미터 저장
ReplyDTO reply = new ReplyDTO();

reply.setRefNum(num);
reply.setName(name);
reply.setContent(content);
reply.setBoardType(boardType);
reply.setNickName(nickName);

// ReplyDAO 객체 생성 후 insertReply() 메소드 호출하여 댓글 등록
// => 파라미터 : ReplyDTO 객체 (reply) 리턴타입 : int(insertCount)
ReplyDAO dao = new ReplyDAO();
int insertCount = dao.insertReply(reply);

// 댓글 등록 성공 시 notice_content.jsp로 이동
if(insertCount > 0) {
	response.sendRedirect("event_content.jsp?num=" + num);
} else {
	%>
	<script>
		alert("댓글 등록 실패!");
		history.back();
	</script>
	<%
}
%>