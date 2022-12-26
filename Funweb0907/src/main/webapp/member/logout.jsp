<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
// 로그아웃(= 세션 초기화) 수행 후 index.jsp 페이지로 포워딩
// session.removeAttribute("sId"); // "sId" 세션만 삭제하는 경우
session.invalidate();
response.sendRedirect("../main/main.jsp");
%>