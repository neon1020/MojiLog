<%@page import="board.BoardDAO"%>
<%@page import="board.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");
String id = (String)session.getAttribute("sId");

// 폼 파라미터 가져오기
String name = request.getParameter("name");
String pass = request.getParameter("pass");
String subject = request.getParameter("subject");
String content = request.getParameter("content");

//INSERT 작업에 필요한 데이터를 BoardDTO 객체 생성 후 저장
BoardDTO board = new BoardDTO();

board.setName(name);
board.setPass(pass);
board.setSubject(subject);
board.setContent(content);
board.setId(id);

//키워드 포함 여부 판단
boolean bad = false;
String[] contain = new String[]{subject, content};
String[] keyword = new String[]{"돼지", "못생", "뚱뚱", "바보"};

for(int i = 0; i < contain.length; i++) {
	for(int j = 0; j < keyword.length; j++) {
		if(contain[i].contains(keyword[j])) {
			bad = true;
		}
	}
}

if(bad) {
	%>
	<script>
	alert("다음 키워드를 포함한 글은 작성할 수 없습니다!\n \"돼지\", \"못생\", \"뚱뚱\", \"바보\"");
	history.back();
	</script>
	<%
} else {
	//데이터베이스 작업을 위한 BoardDAO 객체 생성
	BoardDAO dao = new BoardDAO();
	
	// 글쓰기 작업을 수행할 BoardDAO 객체의 insertBoard() 메소드 호출
	//=> 파라미터 : BoardDTO 객체(board)   리턴타입 : int(insertCount)
	int insertCount = dao.insertBoard(board);
	
	if(insertCount > 0) { // 글쓰기 성공 시
		response.sendRedirect("notice.jsp");
	} else { // 실패 시
		%>
		<script>
		alert("글쓰기 실패!");
		history.back();
		</script>
	<% }
}%>