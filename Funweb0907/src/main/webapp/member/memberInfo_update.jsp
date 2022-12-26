<%@page import="member.MemberDAO"%>
<%@page import="member.MemberDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member/memberInfo_update.jsp</title>
<link href="../css/default.css" rel="stylesheet" type="text/css">
<link href="../css/subpage.css" rel="stylesheet" type="text/css">
<style type="text/css">
	form#join #id, form#join #name {
		background-color: #CCCCCC;
	}
</style>
</head>
<%
request.setCharacterEncoding("UTF-8");

// 파라미터 가져오기
String id = request.getParameter("id");

// MemberDAO 클래스의 selectMember() 메소드 호출
// => 파라미터 : Id(id)   리턴타입 : MemberDTO(member)
MemberDTO member = new MemberDTO();
MemberDAO dao = new MemberDAO();

member = dao.selectMember(id);
%>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
    function execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                document.getElementById('post_code').value = data.zonecode; // 우편번호
                document.getElementById("address1").value = data.roadAddress; // 주소
                document.getElementById("address2").focus();
            }
        }).open();
    }
</script>
<body>
	<div id="wrap">
		<!-- 헤더 들어가는곳 -->
		<jsp:include page="../inc/top.jsp"/>
		<!-- 헤더 들어가는곳 -->
		  
		<!-- 본문들어가는 곳 -->
		  <!-- 본문 메인 이미지 -->
		  <div id="sub_img_member"></div>
		  <!-- 왼쪽 메뉴 -->
		  <nav id="sub_menu">
		  	<ul>
		  		<li><a href="#">Join us</a></li>
		  		<li><a href="#">Privacy policy</a></li>
		  	</ul>
		  </nav>
		  <!-- 본문 내용 -->
		  <article>
		  	<h1>Member Information</h1>
		  	<form action="memberInfo_updatePro.jsp" method="post" id="join" name="fr">
		  		<fieldset>
		  			<legend>Basic Info</legend>
		  			<label>User Id</label>
		  			<input type="text" name="id" class="id" id="id" value="<%=member.getId() %>" readonly="readonly"><br>
		  			
		  			<label>Password</label>
		  			<input type="text" name="pass" class="pass" id="pass" value="<%=member.getPass() %>"><br>
		  			
		  			<label>Name</label>
		  			<input type="text" name="name" id="name" value="<%=member.getName() %>" readonly="readonly"><br>
		  			
		  			<label>E-Mail</label>
		  			<input type="email" name="email" id="email" value="<%=member.getEmail() %>"><br>
		  		</fieldset>
		  		
		  		<fieldset>
		  			<legend>Optional</legend>
		  			<label>Post Code</label>
		  			<input type="text" name="post_code" id="post_code" value="<%=member.getPost_code() %>">
		  			<input type="button" value="주소검색" onclick="execDaumPostcode()"><br>
		  			<label>Address</label>
		  			<input type="text" name="address1" id="address1" value="<%=member.getAddress1() %>">
		  			<input type="text" name="address2" id="address2" value="<%=member.getAddress2() %>"><br>
		  			<label>Phone Number</label>
		  			<input type="text" name="phone" value="<%=member.getPhone() %>"><br>
		  			<label>Mobile Phone Number</label>
		  			<input type="text" name="mobile" value="<%=member.getMobile() %>"><br>
		  		</fieldset>
		  		<div class="clear"></div>
		  		<div id="buttons">
		  			<input type="submit" value="Update" class="submit">
		  		</div>
		  	</form>
		  </article>
		  
		  
		<div class="clear"></div>  
		<!-- 푸터 들어가는곳 -->
		<jsp:include page="../inc/bottom.jsp"/>
		<!-- 푸터 들어가는곳 -->
	</div>
</body>
</html>
