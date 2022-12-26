<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@page import="moji.MojiDAO"%>
<%@page import="moji.MojiDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");

String uploadPath = "/upload";

int fileSize = 1024 * 1024 * 10;

ServletContext context = request.getServletContext();

String realPath = context.getRealPath(uploadPath);
MultipartRequest multi = new MultipartRequest(request, realPath, fileSize, "UTF-8", new DefaultFileRenamePolicy());

// 폼 파라미터 가져오기
String subject = multi.getParameter("subject");
String content = multi.getParameter("content");

//INSERT 작업에 필요한 데이터를 MojiDTO 객체 생성 후 저장
MojiDTO moji = new MojiDTO();

moji.setSubject(subject);
moji.setContent(content);

//1) 파일명을 관리하는 객체에 접근하여 파일명 목록 중 첫번째 파일명 가져오기
String fileElement = multi.getFileNames().nextElement().toString();

//2) 1번 과정에서 가져온 이름을 활용하여 원본 파일명과 실제 업로드 된 파일명 가져오기
moji.setOriginal_file(multi.getOriginalFileName(fileElement));
moji.setReal_file(multi.getFilesystemName(fileElement));

//데이터베이스 작업을 위한 MojiDAO 객체 생성
MojiDAO dao = new MojiDAO();

// 글쓰기 작업을 수행할 MojiDAO 객체의 insertMoji() 메소드 호출
//=> 파라미터 : MojiDTO 객체(moji)   리턴타입 : int(insertCount)
int insertCount = dao.insertMoji(moji);

if(insertCount > 0) { // 글쓰기 성공 시
	response.sendRedirect("notice.jsp");
} else { // 실패 시
	%>
	<script>
		alert("글쓰기 실패!");
		history.back();
	</script>
	<%
}
%>