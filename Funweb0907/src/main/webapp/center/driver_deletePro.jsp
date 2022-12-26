<%@page import="java.io.File"%>
<%@page import="board.FileBoardDAO"%>
<%@page import="board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 글번호(num), 패스워드(pass) 파라미터 가져오기
int num = Integer.parseInt(request.getParameter("num"));

// FileBoardDAO() 객체 생성 후 deleteFileBoard() 메소드 호출하여 글 삭제 작업 수행
// => 파라미터 : 글번호, 패스워드     리턴타입 : int(deleteCount)
FileBoardDAO dao = new FileBoardDAO();

// ------------------------------------------------------------------------------------------

// 삭제 전 업로드 된 파일 삭제를 위해 selectRealFile() 메소드 호출하여 실제 업로드 파일명 조회
String realFile = dao.selectRealFile(num); // real_file 리턴

int deleteCount = dao.deleteFileBoard(num);

if(deleteCount > 0) {
	// 성공 시 해당 실제 파일 삭제 후 글목록으로 이동

	String uploadPath = File.separator + "upload";
	String realPath = request.getServletContext().getRealPath(uploadPath);

	// --------------------------------------------------------------------------------------------
	// java.io.File 클래스 사용하여 파일 존재할 경우 해당 파일 삭제
	// 1. File 객체 생성 (생성자 파라미터로 파일경로 및 파일명 전달)
	File f = new File(realPath, realFile);

	// 2. 지정된 경로의 파일이 존재하는지 여부 판별
	if(f.exists()) {
		// 3. 해당 파일 삭제
		boolean isDeleteSuccess = f.delete();
		System.out.println("파일 삭제 결과 : " + isDeleteSuccess);
	}
	response.sendRedirect("driver.jsp");
} else { %>
<script>
	alert("삭제 실패!");
	history.back()
</script>

<%
}
%>