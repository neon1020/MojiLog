<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>member/join.jsp</title>
<script type="text/javascript">
	var isSamePass = false;
	var isSameEmail = false;
	var isValidPassword = false;
	
	// 아이디 중복 체크
	function idCheck(id) {
		window.open("check_id.jsp","check_id","width=500, height=300");
	}
	
	// 패스워드 길이 체크
	function passCheck(pass) {
		var spanResult = document.getElementById("checkPassResult");
		// reCheck(document.fr.passwdCheck.value);
	
		if (pass.length >= 4 && pass.length <= 16) {
			spanResult.innerHTML = "사용 가능한 패스워드";
			spanResult.style.color = "blue";
			isValidPassword = true;
		} else {
			spanResult.innerHTML = "사용 불가능한 패스워드";
			spanResult.style.color = "red";
			isValidPassword = false;
		}
	}

	// 패스워드 일치 여부 체크
	function passCheck2() {
		var pass = document.fr.pass.value;
		var pass2 = document.fr.pass2.value;
		var spanResult = document.getElementById("checkPass2Result");
		
		if (pass == pass2) {
			spanResult.innerHTML = "비밀번호 일치";
			spanResult.style.color = "blue";
			isSamePass = true;
		} else {
			spanResult.innerHTML = "비밀번호 불일치";
			spanResult.style.color = "red";
			isSamePass = false;
		}
	}
	
	function checkAll(fr) { // fr = this = document.fr
		if(!isSamePass) {
			alert("패스워드가 일치하지 않습니다.")
			fr.pass2.select(); // 포커스(영역 선택) 주기
			return false;
		} else if(!isValidPassword) {
			alert("유효하지 않은 패스워드입니다.")
			fr.pass.select(); // 포커스(영역 선택) 주기
			return false;
		}
		// 위 모든 조건 만족하지 않을 때(=입력이 모두 정상) submit 동작 수행
		return true;
	}
</script>
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
		  <div id="sub_img_member"></div>
		  <!-- 왼쪽 메뉴 -->
		  <nav id="sub_menu">
		  	<ul>
		  		<li><a href="join.jsp">회원가입</a></li>
		  		<li><a href="login.jsp">로그인</a></li>
		  	</ul>
		  </nav>
		  <!-- 본문 내용 -->
		  <article>
		  	<h1>Join Us</h1>
		  	<!-- onsubmit="return checkAll(this)" -->
		  	<form action="joinPro.jsp" method="post" id="join" name="fr" onsubmit="return checkAll(this)">
		  		<fieldset>
		  			<legend>Basic Info</legend>
		  			<label>User Id</label>
		  			<input type="text" name="id" class="id" id="id" onclick="idCheck(document.fr.id.value)" placeholder="클릭하세요." required="required"><br>
					<!-- <input type="button" value="dup. check" class="dup" id="btn"><br> -->
		  			
		  			<label>Password</label>
		  			<input type="password" name="pass" id="pass" onkeyup="passCheck(this.value)" onchange="passCheck2()" placeholder="4 ~ 10자리 입력" required="required">
		  			<span id="checkPassResult"></span><br> 			
		  			
		  			<label>Retype Password</label>
		  			<input type="password" name="pass2" onkeyup="passCheck2()" required="required">
		  			<span id="checkPass2Result"></span><br>
		  			
		  			<label>Name</label>
		  			<input type="text" name="name" id="name" required="required"><br>
		  			
		  			<label>E-Mail</label>
		  			<input type="email" name="email" id="email" required="required"><br>
		  			
		  		</fieldset>
		  		
		  		<fieldset>
		  			<legend>Optional</legend>
		  			<label>Post Code</label>
		  			<input type="text" name="post_code" id="post_code" placeholder="우편번호">
		  			<input type="button" class="code" onclick="execDaumPostcode()" value="주소검색"><br>
		  			<label>Address</label>
		  			<input type="text" name="address1" id="address1" placeholder="주소">
		  			<input type="text" name="address2" id="address2" placeholder="상세주소"><br>
		  			<label>Phone Number</label>
		  			<input type="text" name="phone" ><br>
		  			<label>Mobile Number</label>
		  			<input type="text" name="mobile" ><br>
		  		</fieldset>
		  		<div class="clear"></div>
		  		<div id="buttons" align="left">
		  			<input type="submit" value="Submit" class="submit">
		  			<input type="reset" value="Cancel" class="cancel" onclick="location.href='../main/main.jsp'">
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


