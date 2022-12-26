<%@page import="board.ReplyDTO"%>
<%@page import="java.util.List"%>
<%@page import="board.ReplyDAO"%>
<%@page import="board.FileBoardDTO"%>
<%@page import="board.FileBoardDAO"%>
<%@page import="board.BoardDTO"%>
<%@page import="board.BoardDAO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
//날짜 표시 형식 변경을 위한 SimpleDateFormat 객체 생성
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // ex) 2022-08-19 17:35:05

// 폼 파라미터 가져오기
int num = Integer.parseInt(request.getParameter("num"));
String sId = (String)session.getAttribute("sId");
String sName = (String)session.getAttribute("sName");

//데이터베이스 작업을 위한 BoardDAO 객체 생성
FileBoardDAO dao = new FileBoardDAO();

//BoardDAO 객체의 updateReadcount() 메서드를 호출하여 게시물 조회수 증가 작업 수행
//=> 파라미터 : 글번호(num)   리턴타입 : void
dao.updateReadcount(num);

//BoardDAO 객체의 selectBoard() 메서드를 호출하여 게시물 1개 조회 작업 수행
//=> 파라미터 : 글번호(num)   리턴타입 : BoardDTO(board)
FileBoardDTO board = dao.selectFileBoard(num);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>center/driver_content.jsp</title>
<link href="../css/default.css" rel="stylesheet" type="text/css">
<link href="../css/subpage.css" rel="stylesheet" type="text/css">
<script type="text/javascript">
	function forwardReply() {
		var content = document.getElementById("reply_content").value;
		
		// 파라미터 : 글번호, 작성자(세션아이디), 댓글 내용, 댓글게시판타입(notice)
		location.href="driver_content_reply_writePro.jsp?boardType=driver&num=<%=board.getNum()%>&name=<%=sId %>&nickName=<%=sName %>&content=" + content;
	}
</script>
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
				<li><a href="../moji/notice.jsp">모지의 하루</a></li>
				<li><a href="driver.jsp">모지의 앨범</a></li>
			</ul>
		</nav>
		<!-- 본문 내용 -->

		<article>
			<h1>모지의 앨범</h1>
			<table id="notice">
				<tr>
					<td>글번호</td>
					<td><%=board.getNum() %></td>
					<td>글쓴이</td>
					<td><%=board.getName() %></td>
				</tr>
				<tr>
					<td>작성일</td>
					<td><%=sdf.format(board.getDate()) %></td>
					<td>조회수</td>
					<td><%=board.getReadcount() %></td>
				</tr>
				<tr>
					<td>제목</td>
					<td colspan="3"><%=board.getSubject() %></td>
				</tr>
				<tr>
					<td>설명</td>
					<td colspan="3"><%=board.getContent() %></td>
				</tr>
				<tr>
					<td>사진</td>
					<td colspan="3"><img src="../upload/<%=board.getOriginal_file() %>" width="500px" height="500px"></td>
				</tr>
				<tr>
					<td>다운로드</td>
					<td colspan="3">
						<a href="../upload/<%=board.getReal_file() %>" download="<%=board.getOriginal_file() %>"><%=board.getOriginal_file() %></a>
					</td>
				</tr>
				<tr>
					<td>댓글</td>
					<td colspan="2" class="content_reply">
						<textarea rows="4" cols="60" id="reply_content"></textarea></td>
					<td>
						<input type="button" class="reply_btn" value="쓰기" onclick="forwardReply()">
					</td>
				</tr>
				<!-- 댓글 목록 가져와서 출력하기 -->
				<%
				// ReplyDAO 객체 생성 후 selectReplyList() 메소드 호출하여 댓글 목록 가져오기
				// => 파라미터 : 글번호(num)  리턴타입 : List<ReplyDTO> (replyList)
				ReplyDAO replyDAO = new ReplyDAO();
				List<ReplyDTO> replyList = replyDAO.selectReplyList(num);
				
				SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd HH:mm");
				
				for(ReplyDTO reply : replyList) {
					%>
				<tr>
					<%if(sId.equals(reply.getName())) {%>
					<td><input type="button" value="삭제" onclick="location.href='driver_content_reply_deletePro.jsp?num=<%=reply.getNum() %>&refNum=<%=num %>'"></td>
					<%} else { %>
					<td></td>
					<%} %>
					<td class="left"><%=reply.getContent() %></td>
					<td><%=reply.getNickName() %></td>
					<td><%=sdf2.format(reply.getDate()) %></td>
				</tr>
				<%}%>
			</table>

			<div id="table_search">
				<%if(sId.equals("moji")) { %>
				<input type="button" value="글수정" class="btn" onclick="location.href='driver_update.jsp?num=<%=num %>'">
				<input type="button" value="글삭제" class="btn" onclick="location.href='driver_deletePro.jsp?num=<%=num %>'">
				<%} %>
				<input type="button" value="글목록" class="btn" onclick="location.href='driver.jsp'">
			</div>

			<div class="clear"></div>
		</article>

		<div class="clear"></div>
		<!-- 푸터 들어가는곳 -->
		<jsp:include page="../inc/bottom.jsp" />
		<!-- 푸터 들어가는곳 -->
	</div>
</body>
</html>


