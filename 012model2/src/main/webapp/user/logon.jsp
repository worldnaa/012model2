<%@ page contentType="text/html; charset=UTF-8" %>

<!-- /////////////////////////// 변 경 된 부 분 ///////////////////////////// 
	 - Model2 Web Architecture 적용 시 :: JSP는 View 역할만 한다
	 - Work Flow Control 은 Controller 가 담당
	 - 아래 주석 : Controller 를 담당하는 ControlServlet.java 에서 처리
     ////////////////////////////////////////////////////////////////////// -->

<%-- <% UserVO userVO = (UserVO)session.getAttribute("userVO"); %> --%>

<html>
	<head></head>
	<body>
	
		<%-- <% if(userVO == null || userVO.isActive() != true) { %> --%>
			
			<!-- /////////////////////////// 변 경 된 부 분 /////////////////////////////
			<form id="login" method="post" action="/011model1/user/logonAction.jsp"> 
			////////////////////////////////////////////////////////////////////////// -->
			
			<form id="login" method="post" action="/012model2/logonAction.do">
				
				아이디 : <input id="userId" type="text" name="userId" value=""><br/><br/> 
				패스워드 : <input id="userPwd" type="text" name="userPwd" value=""><br/><br/>
				<input id="submit" type="submit" name="submit" value="Enter"/>
			
			</form>
		
		<%-- <% }else{ %>
				<%= userVO.getUserId() %>님은 이미 로그인 하셨습니다.		
		<% } %> --%>
		
	</body>
</html>