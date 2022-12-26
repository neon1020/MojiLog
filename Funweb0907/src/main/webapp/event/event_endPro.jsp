<%@page import="event.Event_EndDAO"%>
<%@page import="event.Event_EndDTO"%>
<%@page import="event.EventDTO"%>
<%@page import="event.EventDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setCharacterEncoding("UTF-8");
int refNum = Integer.parseInt(request.getParameter("num"));

EventDAO eventDAO = new EventDAO();
EventDTO eventDTO = new EventDTO();

eventDTO = eventDAO.selectEvent(refNum);

// -----------------------------------------------------------------------

Event_EndDTO event_EndDTO = new Event_EndDTO();
Event_EndDAO event_EndDAO = new Event_EndDAO();

// 복사할 값 전달받기
event_EndDTO.setSubject(eventDTO.getSubject());
event_EndDTO.setContent(eventDTO.getContent());
event_EndDTO.setStart_date(eventDTO.getStart_date());
event_EndDTO.setEnd_date(eventDTO.getEnd_date());
event_EndDTO.setRefNum(refNum);
event_EndDTO.setOriginal_file(eventDTO.getOriginal_file());
event_EndDTO.setReal_file(eventDTO.getReal_file());

// event_end 테이블에 글 작성
int insertCount = event_EndDAO.insertEvent_End(event_EndDTO);

// event 테이블에서 글 삭제
int deleteCount = eventDAO.deleteEvent(refNum);

if(insertCount > 0 && deleteCount > 0) { // 글 이동 성공 시
	response.sendRedirect("event.jsp");
} else { // 실패 시
	%>
	<script>
		alert("게시판 이동 실패!");
		history.back();
	</script>
	<%
}
%>
