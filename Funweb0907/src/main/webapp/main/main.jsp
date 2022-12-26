<%@page import="event.EventDTO"%>
<%@page import="event.EventDAO"%>
<%@page import="board.BoardDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="board.BoardDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%SimpleDateFormat sdf = new SimpleDateFormat("MM월 dd일");
SimpleDateFormat sdf2 = new SimpleDateFormat("MM월 dd일 까지");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>main/main.jsp</title>
<link href="../css/default.css" rel="stylesheet" type="text/css">
<link href="../css/front.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="wrap">
		<!-- 헤더 들어가는곳 -->
		<jsp:include page="../inc/top.jsp"/>
		<!-- 헤더 들어가는곳 -->
		  
		<div class="clear"></div>   
		<!-- 본문들어가는 곳 -->
		<!-- 메인 이미지 -->
		<div id="main_img"><img src="../images/main_img.jpg" width="971px" height="308px"></div>
		<!-- 본문 내용 -->
		<article id="front">
		  	<div id="solution">
		  		<div id="payment">
		  			<h3>♥ Welcome to MOJILOG ♥</h3>
					<p>모지로그에 온 걸 환영해!<br>
						나의 재밌는 일상들을 소개할게~<br>
						나의 팬이 되어줬으면 좋겠어!</p>
		  		</div>
		  	</div>
		  	<div class="clear"></div>
			<div id="sec_news">
				<h3><span class="orange">주간 이벤트</span></h3>
				<%EventDAO eventDAO = new EventDAO();
				EventDTO eventDTO = eventDAO.selectRecentEvent();
				%>
				<table>
					<!-- 가장 최근 이벤트 1개 불러오기 -->
					<tr>
						<%if(eventDTO != null) { %>
						<td width="250px" height="50px"><a href="../event/event_content.jsp?num=<%=eventDTO.getNum() %>">★ <%=eventDTO.getSubject() %> ★</a></td>
						<td width="120px" height="50px"><%=sdf2.format(eventDTO.getEnd_date()) %></td>
						<%} else { %>
						<td colspan="2">지금은 진행 중인 이벤트가 없어!</td>
						<%} %>
					</tr>
				</table>
			</div>
		
			<div id="news_notice">
		  		<h3 class="brown">와글와글</h3>
		  		<table>
		  			<!-- 공지사항(notice) 게시물 중 최근 5개 표시 영역 -->
		  			<%
		  			// BoardDAO 객체의 selectRecentlyBoardList() 메소드 호출하여 최근 게시물 5개 목록 조회
		  			// => 파라미터 : 없음, 리턴타입 : ArrayList<BoardDTO>(boardList)
		  			BoardDAO dao = new BoardDAO();
		  			
		  			ArrayList<BoardDTO> boardList = dao.selectRecentlyBoardList();
		  			
		  			for(BoardDTO board : boardList) {%>
		  			<tr>
		  				<td width="265"><a href="../center/notice_content.jsp?num=<%=board.getNum()%>"><%=board.getSubject() %></a></td>
		  				<td width="55"><%=board.getName() %></td>
		  				<td width="100"><%=sdf.format(board.getDate()) %></td>
		  			</tr>
		  			<%}%>
		  		</table>
				
		  	</div>
	  	</article>
		  
		<div class="clear"></div>  
		<!-- 푸터 들어가는곳 -->
		<jsp:include page="../inc/bottom.jsp"/>
		<!-- 푸터 들어가는곳 -->
	</div>
</body>
</html>


