<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%String id = (String)session.getAttribute("sId"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>event/event_write.jsp</title>
<link href="../css/default.css" rel="stylesheet" type="text/css">
<link href="../css/subpage.css" rel="stylesheet" type="text/css">
</head>
<body>
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
				<li><a href="event.jsp">진행중인 이벤트</a></li>
				<li><a href="event_end.jsp">종료된 이벤트</a></li>
			</ul>
		</nav>
		<!-- 본문 내용 -->
		<article>
			<h1>Event</h1>
			<form action="event_writePro.jsp" method="post" enctype="multipart/form-data">
				<table id="notice">
					
					<tr>
						<td>글쓴이</td>
						<td><input type="text" value="모지" readonly="readonly"></td>
					</tr>
					<tr>
						<td>제목</td>
						<td><input type="text" name="subject"></td>
					</tr>
					<tr>
						<td>내용</td>
						<td><textarea rows="10" cols="20" name="content"></textarea></td>
					</tr>
					<tr>
						<td>참여 링크</td>
						<td><input type="text" name="link"></td>
					</tr>
					<tr>
						<td>파일</td>
						<td><input type="file" name="original_file" required="required"></td>
					</tr>
				</table>

				<div id="table_search">
					<input type="submit" value="글쓰기" class="btn">
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


