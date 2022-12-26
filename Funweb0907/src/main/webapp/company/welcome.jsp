<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>aboutmoji.jsp</title>
<link href="../css/default.css" rel="stylesheet" type="text/css">
<link href="../css/subpage.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="wrap">
		<!-- 헤더 들어가는곳 -->
		<jsp:include page="../inc/top.jsp"/>
		<!-- 헤더 들어가는곳 -->

		<!-- 본문들어가는 곳 -->
		<!-- 본문 메인 이미지 -->
		<div id="sub_img"></div>
		<!-- 왼쪽 메뉴 -->
		<nav id="sub_menu">
			<ul>
				<li><a href="welcome.jsp">About Moji</a></li>
				<li><a href="../moji/notice.jsp">Diary</a></li>
				<li><a href="../center/notice.jsp">My Fans</a></li>
			</ul>
		</nav>
		<!-- 본문 내용 -->
		<article>
			<h1>안녕 나는 모지!</h1>
			<figure class="ceo">
				<img src="../images/company/CEO.jpg" width="200px" height="200px">
			</figure>
			<p>모지로그에 온 걸 환영해!	나는 모지야!<br>
			나는 2살이고 흰 색 드워프 햄스터야.<br>
			나의 최애 음식은 밀웜과 해바라기씨!!!<br>
			주인들이 모두 잠든 야심한 밤에 쳇바퀴에서<br>
			전력질주하는게 내 취미야!
			</p>
		</article>
		<div class="clear"></div>
		<!-- 푸터 들어가는곳 -->
		<jsp:include page="../inc/bottom.jsp"/>
		<!-- 푸터 들어가는곳 -->
	</div>
</body>
</html>


