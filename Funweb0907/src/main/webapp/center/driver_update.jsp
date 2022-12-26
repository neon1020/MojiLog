<%@page import="board.FileBoardDTO"%>
<%@page import="board.FileBoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
// 글번호(num) 파라미터 가져오기
int num = Integer.parseInt(request.getParameter("num"));

// FileBoardDAO 객체 생성 후 게시물 상세 정보 조회 위해 selectFileBoard() 메소드 호출
// 파라미터 : 글번호, 리턴타입 : BoardDTO(board)
FileBoardDAO dao = new FileBoardDAO();
FileBoardDTO board = dao.selectFileBoard(num);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>center/driver_update.jsp</title>
<link href="../css/default.css" rel="stylesheet" type="text/css">
<link href="../css/subpage.css" rel="stylesheet" type="text/css">
</head>
<body>
<%
String id = (String)session.getAttribute("sId"); 
String name = (String)session.getAttribute("sName");

if(id == null || !id.equals("moji") && !id.equals(board.getName())) {%>
	<script type="text/javascript">
		alert("접근 권한이 없습니다!");
		location.href="../member/login.jsp";
	</script>
<%}%>
	<div id="wrap">
		<!-- 헤더 들어가는곳 -->
		<jsp:include page="../inc/top.jsp" />
		<!-- 헤더 들어가는곳 -->

		<!-- 본문들어가는 곳 -->
		<!-- 본문 메인 이미지 -->
		<div id="sub_img_center"></div>
		<!-- 왼쪽 메뉴 -->
		<nav id="sub_menu">
			<ul>
				<li><a href="../moji/notice.jsp">모지의 하루</a></li>
				<li><a href="driver.jsp">모지의 앨범</a></li>
			</ul>
		</nav>
		<!-- 본문 내용 -->
		<article>
			<h1>앨범 수정</h1>
			<form action="driver_updatePro.jsp" method="post" enctype="multipart/form-data">
				<!-- 입력 받지 않은 글번호(num)도 함께 전달 -->
				<input type="hidden" name="num" value="<%=num %>">
				<!-- 기존 파일 삭제를 위해 실제 업로드 파일명도 함께 전달 -->
				<input type="hidden" name="real_file" value="<%=board.getReal_file() %>">
				<table id="notice">
					<tr>
						<td>글쓴이</td>
						<td><input type="text" name="name" value="<%=board.getName() %>" readonly="readonly" required="required"></td>
					</tr>
					<tr>
						<td>패스워드</td>
						<td><input type="password" name="pass" required="required"></td>
					</tr>
					<tr>
						<td>제목</td>
						<td><input type="text" name="subject" value="<%=board.getSubject() %>" required="required"></td>
					</tr>
					<tr>
						<td>내용</td>
						<td><textarea rows="10" cols="20" name="content" required="required"><%=board.getContent() %></textarea></td>
					</tr>
					<tr>
						<td>파일</td>
						<td>
							<%=board.getOriginal_file() %><br>
							<input type="file" name="original_file" required="required">
						</td>
					</tr>
				</table>

				<div id="table_search">
					<input type="submit" value="글수정" class="btn">
				</div>
			</form>
			<div class="clear"></div>
		</article>


		<div class="clear"></div>
		<!-- 푸터 들어가는곳 -->
		<jsp:include page="../inc/bottom.jsp" />
		<!-- 푸터 들어가는곳 -->
	</div>
</body>
</html>


