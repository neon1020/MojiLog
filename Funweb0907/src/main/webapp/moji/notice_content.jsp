<%@page import="board.ReplyDTO"%>
<%@page import="java.util.List"%>
<%@page import="board.ReplyDAO"%>
<%@page import="moji.MojiDTO"%>
<%@page import="moji.MojiDAO"%>
<%@page import="board.BoardDTO"%>
<%@page import="board.BoardDAO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // ex) 2022-08-19 17:35:05

// 폼 파라미터 가져오기
int num = Integer.parseInt(request.getParameter("num"));
String sId = (String)session.getAttribute("sId");
String sName = (String)session.getAttribute("sName");

//데이터베이스 작업을 위한 MojiDAO 객체 생성
MojiDAO dao = new MojiDAO();

//MojiDAO 객체의 updateReadcount() 메서드를 호출하여 게시물 조회수 증가 작업 수행
//=> 파라미터 : 글번호(num)   리턴타입 : void
dao.updateReadcount(num);

//MojiDAO 객체의 selectMoji() 메서드를 호출하여 게시물 1개 조회 작업 수행
//=> 파라미터 : 글번호(num)   리턴타입 : MojiDTO(moji)
MojiDTO moji = dao.selectMoji(num);
%>
<script type="text/javascript">
	function forwardReply() {
		var content = document.getElementById("reply_content").value;
		
		// 파라미터 : 글번호, 작성자(세션아이디), 댓글 내용, 댓글게시판타입(notice)
		location.href="notice_content_reply_writePro.jsp?boardType=moji&num=<%=moji.getNum()%>&name=<%=sId %>&nickName=<%=sName %>&content=" + content;
	}
</script>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>moji/notice_content.jsp</title>
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
				<li><a href="notice.jsp">모지의 하루</a></li>
				<li><a href="../center/driver.jsp">모지의 앨범</a></li>
			</ul>
		</nav>
		<!-- 본문 내용 -->

		<article>
			<h1>Notice Content</h1>
			<table id="notice">
				<tr>
					<td>글번호</td>
					<td><%=moji.getNum() %></td>
					<td>글쓴이</td>
					<td>모지</td>
				</tr>
				<tr>
					<td>작성일</td>
					<td><%=sdf.format(moji.getDate()) %></td>
					<td>조회수</td>
					<td><%=moji.getReadcount() %></td>
				</tr>
				<tr>
					<td>제목</td>
					<td colspan="3"><%=moji.getSubject() %></td>
				</tr>
				<tr>
					<td rowspan="2">내용</td>
					<td colspan="3"><%=moji.getContent() %></td>
				</tr>
				<tr>
					<td colspan="3"><img src="../upload/<%=moji.getOriginal_file() %>" width="500px" height="500px"></td>
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
					<td><input type="button" value="삭제" onclick="location.href='notice_content_reply_deletePro.jsp?num=<%=reply.getNum() %>&refNum=<%=num %>'"></td>
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
				<%if(session.getAttribute("sId").equals("moji")){%>
				<input type="button" value="글삭제" class="btn" onclick="location.href='notice_deletePro.jsp?num=<%=num %>'">
				<%} %>
				<input type="button" value="글목록" class="btn" onclick="location.href='notice.jsp'">
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


