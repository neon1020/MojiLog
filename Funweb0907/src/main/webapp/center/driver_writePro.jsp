<%@page import="board.FileBoardDAO"%>
<%@page import="board.FileBoardDTO"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@page import="board.BoardDAO"%>
<%@page import="board.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");

// ----------------------------------------------------------------------------------------
String uploadPath = "/upload";

int fileSize = 1024 * 1024 * 10;

ServletContext context = request.getServletContext();

String realPath = context.getRealPath(uploadPath);
MultipartRequest multi = new MultipartRequest(request, realPath, fileSize, "UTF-8", new DefaultFileRenamePolicy());

FileBoardDTO board = new FileBoardDTO();
board.setName(multi.getParameter("name"));
board.setSubject(multi.getParameter("subject"));
board.setContent(multi.getParameter("content"));

// 1) 파일명을 관리하는 객체에 접근하여 파일명 목록 중 첫번째 파일명 가져오기
String fileElement = multi.getFileNames().nextElement().toString();

// 2) 1번 과정에서 가져온 이름을 활용하여 원본 파일명과 실제 업로드 된 파일명 가져오기
board.setOriginal_file(multi.getOriginalFileName(fileElement));
board.setReal_file(multi.getFilesystemName(fileElement));


// FileBoardDAO 객체의 insertFildBoard() 메소드 호출하여 글쓰기 작업 수행
// => 파라미터 : BoardDTO 객체(board)   리턴타입 : int(insertCount)
FileBoardDAO dao = new FileBoardDAO();

int insertCount = dao.insertFileBoard(board);

if(insertCount > 0) { // 글쓰기 성공 시
	response.sendRedirect("driver.jsp");
} else { // 실패 시
	%>
	<script>
		alert("글쓰기 실패!");
		history.back();
	</script>
	<%
}
%>