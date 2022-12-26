<%@page import="java.io.File"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="board.FileBoardDAO"%>
<%@page import="board.FileBoardDTO"%>
<%@page import="board.BoardDAO"%>
<%@page import="board.BoardDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");
FileBoardDTO board = new FileBoardDTO();
// -----------------------------------------------------------------------------------------

String uploadPath = "/upload";
int fileSize = 1024 * 1024 * 10;

ServletContext context = request.getServletContext();
String realPath = context.getRealPath(uploadPath);

MultipartRequest multi = new MultipartRequest(request, realPath, fileSize, "UTF-8", new DefaultFileRenamePolicy());

//폼 파라미터 가져와서 FileBoardDTO 객체에 저장
board.setNum(Integer.parseInt(multi.getParameter("num")));
board.setName(multi.getParameter("name"));
board.setPass(multi.getParameter("pass"));
board.setSubject(multi.getParameter("subject"));
board.setContent(multi.getParameter("content"));
String fileElement = multi.getFileNames().nextElement().toString();
board.setOriginal_file(multi.getOriginalFileName(fileElement));
board.setReal_file(multi.getFilesystemName(fileElement));

// FileBoardDAO 객체 생성 후 updateFileBoard() 메소드 호출하여 게시물 수정 작업 수행
// => 파라미터 : FileBoardDTO 객체, 리턴타입 : int(deleteCount)
FileBoardDAO dao = new FileBoardDAO();
int updateCount = dao.updateFileBoard(board);

if(updateCount > 0) {
	// --------------------------------------------------------------------------------------------
	String realFile = multi.getParameter("real_file"); // 기존 업로드 된 파일명 가져오기
	
	File f = new File(realPath, realFile);
	if(f.exists()) {
		f.delete();
	}
	response.sendRedirect("driver_content.jsp?num=" + board.getNum());
} else { %>
<script>
	alert("글 수정 실패!");
	history.back();
</script>
<%
}
%>