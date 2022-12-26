<%@page import="board.ReplyDTO"%>
<%@page import="java.util.List"%>
<%@page import="board.ReplyDAO"%>
<%@page import="event.EventDTO"%>
<%@page import="event.EventDAO"%>
<%@page import="moji.MojiDTO"%>
<%@page import="moji.MojiDAO"%>
<%@page import="board.BoardDTO"%>
<%@page import="board.BoardDAO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // ex) 2022-08-19

// 폼 파라미터 가져오기
int num = Integer.parseInt(request.getParameter("num"));
String sId = (String)session.getAttribute("sId");
String sName = (String)session.getAttribute("sName");

//데이터베이스 작업을 위한 MojiDAO 객체 생성
EventDAO dao = new EventDAO();

//MojiDAO 객체의 selectMoji() 메서드를 호출하여 게시물 1개 조회 작업 수행
//=> 파라미터 : 글번호(num)   리턴타입 : MojiDTO(moji)
EventDTO event = dao.selectEvent(num);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>event/event_content.jsp</title>
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
			<table id="notice">
				<tr>
					<td>글번호</td>
					<td><%=event.getNum() %></td>
					<td>글쓴이</td>
					<td>모지</td>
				</tr>
				<tr>
					<td>시작</td>
					<td><%=sdf.format(event.getStart_date()) %></td>
					<td>종료</td>
					<td><%=sdf.format(event.getEnd_date()) %></td>
				</tr>
				<tr>
					<td>제목</td>
					<td colspan="3"><%=event.getSubject() %></td>
				</tr>
				<tr>
					<td rowspan="2">내용</td>
					<td colspan="3"><%=event.getContent() %></td>
				</tr>
				<tr>
					<td colspan="3"><embed src="../upload/<%=event.getOriginal_file() %>" width="500px" height="500px"></td>
				</tr>
				<tr>
					<td>참여하기</td>
					<td colspan="3"><a href="<%=event.getLink() %>" target="_blank">여기를 클릭해서 참여해봐!</a></td>
				</tr>
			</table>

			<div id="table_search">
				<input type="button" value="글목록" class="btn" onclick="location.href='event.jsp'">
				<%if(session.getAttribute("sId").equals("moji")){%>
				<input type="button" value="글삭제" class="btn" onclick="location.href='event_deletePro.jsp?num=<%=num %>'">
				<%} %>
				<input type="button" value="이벤트 종료" class="btn" onclick="location.href='event_endPro.jsp?num=<%=num %>'">
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


