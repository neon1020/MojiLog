<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String sId = (String)session.getAttribute("sId");
String sName = (String)session.getAttribute("sName");
%>
<header>
  <!-- login join -->
  <div id="login">
  <%if(sId == null) { %>
  <a href="../member/login.jsp">로그인</a> | <a href="../member/join.jsp">회원가입</a>
  <%} else { %>
  <a href="../member/memberInfo.jsp?id=<%=sId %>"><%=sName %>님</a> | <a href="../member/logout.jsp">로그아웃</a>
  		<%if(sId.equals("moji")) {%>
  		| <a href="../admin/admin_main.jsp">관리자페이지</a>
  		<%} 
  	} %>
  </div>
  <div class="clear"></div>
  <!-- 로고들어가는 곳 -->
  <div id="logo"><img src="../images/logo.png"></div>
  <!-- 메뉴들어가는 곳 -->
  <nav id="top_menu">
  	<ul>
  		<li><a href="../main/main.jsp">HOME</a></li>
  		<li><a href="../company/welcome.jsp">ABOUT MOJI</a></li>
  		<li><a href="../moji/notice.jsp">DIARY</a></li>
  		<li><a href="../center/notice.jsp">MY FANS</a></li>
  		<li><a href="../event/event.jsp">EVENT</a></li>
  	</ul>
  </nav>
</header>