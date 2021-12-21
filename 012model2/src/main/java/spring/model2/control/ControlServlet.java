package spring.model2.control;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import spring.model2.service.user.dao.UserDao;
import spring.model2.service.user.vo.UserVO;

// 1. 단일인입점 (Single Point of Entry)
// 2. Client 요구사항 판단
// 3. 선처리 / 공통처리
//		- Work Flow Control :: 권한, 인증 등등
//		- Client Form Data 한글처리
// 4. Business logic 수행 (Bean Method Call)
// 5. Model 과 View 연결
//		- Business logic 처리 결과를 JSP에 전달 (Object Scope / VO 사용)
// 6. 처리된 결과에 따라, JSP로 forward/sendRedirect : Navigation

public class ControlServlet extends HttpServlet {
	
	// init() Method
	public void init(ServletConfig sc) throws ServletException{
		super.init(sc);
		//==> web.xml에 설정 :: <load-on-startup>1</load-on-startup> 확인
		System.out.println("\n\n======================");
		System.out.println("ControlServlet의 init() Method");
		System.out.println("======================\n");
	}
	
	// service() Method
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		System.out.println("\n[ControlServlet.service() start...]");
		
		//==> Controller :: Client 요구사항 판단 :: URL/URI 이용
		//==> 아래 getURI() method 실행
		String actionPage = this.getURI(req.getRequestURI());
		
		System.out.println(":: URI => " + req.getRequestURI());
		System.out.println(":: client의 요구사항 => " + actionPage);
		
		//==> Controller :: 선처리 / 공통처리 사항이 있다면..
		//==> 본 예제 : 한글처리 / session 관리 및 처리 / 선/공통처리
		req.setCharacterEncoding("utf-8");
		HttpSession session = req.getSession(true);
		
		//==> Controller :: Navigaion (forward / sendRedirect view page 결정)
		//==> Navigation 디폴트 페이지 지정
		String requestPage = "/user/logon.jsp";
		
		//==> Controller :: 권한 / 인증처리
		//==> session Object Scope 에 저장된 UserVO 객체를 이용하여 인증
		//==> 경우 1 : session Object Scope 에 userVO 인스턴스 생성 및 저장
		if(session.isNew() || session.getAttribute("userVO") == null) {
			session.setAttribute("userVO", new UserVO());
		}
		
		//==> 경우 2 : session Object Scope userVO 추출
		UserVO userVO = (UserVO)session.getAttribute("userVO");
		
		//==> UserVO.active 를 이용하여 로그인 유무 판단
		if(userVO != null && userVO.isActive()) {
			requestPage = "/user/home.jsp";
		}
		
		//==> Controller :: Client 요구사항 처리 (Business layer 접근)
		
		//==> 1. logon.do 경우 :: Business Logic 처리할 것 없음 => default page(/user/logon.jsp)로 forward
		else if(actionPage.equals("logon")){
		}
		
		//==> 2. logonAction.do 경우
		//==> Controller :: Client 요구사항 처리 및 Business logic 처리
		//==> Client Form Data 처리
		//==> Client Form Data를 Business Layer로 전송하기 위한 VO Binding
		//==> Business Layer Method 호출 및 결과 값 받아 View(JSP)에서 사용할 수 있도록 ObjectScope에 저장 :: Model/View 연결
		else if(actionPage.equals("logonAction")){
			
			//==> Client Form Data 처리
			String userId = req.getParameter("userId");
			String userPwd = req.getParameter("userPwd");
			
			//==> Controller :: Model 과 View 의 연결 (Binding)
			userVO.setUserId(userId);
			userVO.setUserPwd(userPwd);
			
			//==> Controller :: Business Logic 처리
			UserDao userDao = new UserDao();
			userDao.getUser(userVO);
			
			//==> Controller :: Navigation (forward/sendRedirect view page 결정)
			if(userVO.isActive()) {
				requestPage = "/user/home.jsp";
			}
		}
		
		//==> 3. home.do 경우
		//==> 비 로그인 회원이 home.do Request : default request page(/user/logon.jsp)로 forward
		else if(actionPage.equals("home")){
		}
		
		System.out.println(":: 최종 결정된 View page는 : [["+requestPage+"]]");
		
		//==> Controller :: Navigation (최종 결정된 page forward)
		ServletContext sc = this.getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher(requestPage);
		rd.forward(req, res);
		
		System.out.println("[ControlServlet.service() end...]");
	
	}//end of service

	// Client 의 요구사항 판단 ==> requestURI = "/~~.do" 형식이므로 아래와 같이 진행
	private String getURI(String requestURI) {
		
		// lastIndexOf('/') ==> 문자열에서 '/' 가 포함된 마지막 index 반환 (여러개일 경우 제일 마지막 인덱스 반환)
		int start = requestURI.lastIndexOf('/')+1; 
		int end = requestURI.lastIndexOf(".do");   
		
		System.out.println(":: getURI()의 start : " + start);
		System.out.println(":: getURI()의 end : " + end);
		
		// substring(int beginIndex, int endIndex-1)
		// beginIndex 부터 시작해서 endIndex-1 까지의 문자열 반환
		String actionPage = requestURI.substring(start,end);
		return actionPage;
	}
	
}//end of class

















