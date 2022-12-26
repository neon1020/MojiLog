<%@page import="event.Event_EndDTO"%>
<%@page import="event.Event_EndDAO"%>
<%@page import="event.EventDTO"%>
<%@page import="event.EventDAO"%>
<%@page import="moji.MojiDTO"%>
<%@page import="moji.MojiDAO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="board.BoardDTO"%>
<%@page import="board.BoardDAO"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
SimpleDateFormat sdf = new SimpleDateFormat("MM월 dd일");

String keyword = request.getParameter("keyword");

//검색어 파라미터 없을 경우 기본값을 널스트링("")으로 설정
if(keyword == null) {
	keyword = "";
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>event/event_end.jsp</title>
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
			<h1>Closed Event</h1>
			<table id="notice">
				<tr>
					<th class="tno">No.</th>
					<th class="ttitle">Title</th>
					<th class="twrite">Write</th>
					<th class="tdate">Start</th>
					<th class="tread">End</th>
				</tr>
				
				<%
				int listLimit = 10;
				
				int pageNum = 1; // 기본값
				if(request.getParameter("pageNum") != null) {
					pageNum = Integer.parseInt(request.getParameter("pageNum"));
				}
				
				int startRow = (pageNum - 1) * listLimit;
				// ------------------------------------------------------------------------------
				
				// 게시물 목록 조회를 위해 Event_EndDAO 객체 생성
				Event_EndDAO dao = new Event_EndDAO();
				
				// ---------------------------------------------------------------------------------------------
				
				// BoardDAO 객체의 selectBoardList() 메소드를 호출하여 게시물 목록 조회
				// => 파라미터 : 시작 행번호, 페이지 당 게시물 목록 수   리턴타입 : ArrayList<BoardDTO>(boardList)
				ArrayList<Event_EndDTO> eventList = dao.selectEventList(startRow, listLimit, keyword);
				
				for(Event_EndDTO event : eventList) {
				%>
				
				<tr onclick="location.href='event_end_content.jsp?num=<%=event.getNum() %>'">
					<td><%=event.getNum() %></td>
					<td class="left"><%=event.getSubject() %></td>
					<td>모지</td>
					<td><%=sdf.format(event.getStart_date()) %></td>
					<td><%=sdf.format(event.getEnd_date()) %></td>
				</tr>
				<%} %>
			</table>
			<div id="table_search">
				<form action="event_end_search.jsp" method="get">
					<input type="text" name="keyword" value="<%=keyword %>" class="input_box">
					<input type="submit" value="Search" class="btn">
				</form>
			</div>

			<div class="clear"></div>
			<div id="page_control">
			
			<%
			int eventListCount = dao.selectEventListCount(keyword);
			
			int pageListLimit = 10;
			
			int maxPage = eventListCount / pageListLimit + (eventListCount % pageListLimit == 0 ? 0 : 1);
			
			int startPage = (pageNum - 1) / pageListLimit * pageListLimit + 1;
			
			int endPage = startPage + pageListLimit - 1;
			
			if(endPage > maxPage) { endPage = maxPage; }
			%>
			<!-- 가진 게시글 수만큼 페이지 리스트 표시 -->
			<!-- 단, 현재페이지가 1일 경우 하이퍼링크 제거 -->
				<%if(pageNum == 1) { %>
					<a href="javascript:void(0)">Prev</a>
				<%} else { %>
					<a href="event.jsp?pageNum=<%=pageNum - 1 %>">Prev</a>
				<%} %>
				
				<!-- for문 사용하여 startPage ~ endPage 까지 목록 표시 -->
				<%for(int i = startPage; i <= endPage; i++) { %>
				<!-- 현재 페이지 번호(pageNum)가 i 값과 같을 경우 하이퍼링크 제거 -->
					<%if(pageNum == i) { %>
						<a href="javascript:void(0)"><%=i %></a>
					<%} else { %>
						<a href="event.jsp?pageNum=<%=i %>"><%=i %></a>
					<%} %>
				<%} %>
				
				<!-- 단, 현재페이지가 최대페이지일 경우 하이퍼링크 제거 -->
				<%if(pageNum == maxPage) { %>
				<a href="javascript:void(0)">Next</a>
				<%} else { %>
				<a href="event.jsp?pageNum=<%=pageNum + 1 %>">Next</a>
				<%} %>

			</div>
		</article>

		<div class="clear"></div>
		<!-- 푸터 들어가는곳 -->
		<jsp:include page="../inc/bottom.jsp" />
		<!-- 푸터 들어가는곳 -->
	</div>
</body>
</html>

