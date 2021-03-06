/*
 *  Login이 필요한 기능 수행시
 *  Session 체크를 하는 Interceptor
 */
package com.creme.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.RequestContextUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
// 상속 필요하다
public class LoginInterceptor extends HandlerInterceptorAdapter{
	// 부모 메서드를 재정의 하는 Override를 사용
	// HandlerInterceptorAdapter에 있는 메서드를 재정의해서 사용하겠다
	// 전처리/후처리 하나만 단독으로 사용해도 된다.(그래서 후처리 주석 함)
	// preHandle == url 전
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// Session 객체 생성
		HttpSession session = request.getSession();
		//String id = (String)session.getAttribute("userid");
		//log.info(">>>>> id Session: " + id);
		// 이동하기 전 있었던 Page URL
		String referer = request.getHeader("referer");
		log.info(">>>>> 이전 URL: " + referer);
		String qString = request.getQueryString(); // 쿼리스트링을 알려준다. ?(물음표)뒤의 값을 알려준다. 쿼리스트링이 없으면 NULL값이 들어온다.
		// 이동하려고 했던 Page URL
		String uri = request.getRequestURI();
		String ctx = request.getContextPath();
		String nextUrl = uri.substring(ctx.length());
		String prevUrl = "";
		String finalUrl = "http://localhost:8081/creme/";
		
		// 비정상적인 접근을 막는 기능!
		if(referer == null) {
			log.info("WARNING>> 비정상적인 접근 :(");
			response.sendRedirect(finalUrl);
			return false;
		} else {
			int indexQuery = referer.indexOf("?");
			if(indexQuery == -1) {
				prevUrl =  referer.substring(finalUrl.length()-1);
			} else {
				prevUrl = referer.substring(finalUrl.length()-1, indexQuery);
			}
			log.info("PREV URL>>>>> " + prevUrl);
			log.info("NEXT URL>>>>> " + nextUrl);
			
			if(nextUrl.equals("/board/update") || nextUrl.equals("/board/delete")) { 
				log.info("" + prevUrl.indexOf("board/view"));
				if(request.getParameter("title") == null) {
					if(prevUrl.indexOf("board/view") == -1) {
						log.info("WARNING>> 비정상적인 접근 :(");
						response.sendRedirect(finalUrl);
						return false;
					}
				}
			}
		}
		//int index = referer.lastIndexOf("/"); // /슬러시가 몇번째에 있는 지 센다.
		//int len = referer.length(); // 문자열 길이
		//log.info(">>>>> 인덱스: " + index);
		//log.info(">>>>> 길이: " + len);
		//String mapWord = referer.substring(index, len); // 인덱스에서 길이까지 자른다. /write만 잘려서 나온다.
		//log.info(">>>>> 수정된 URL: " + referer);
					
		// 정상적인 접근인 경우 실행!
		if(session.getAttribute("userid") == null) {
			if(prevUrl.equals(nextUrl)) {
				log.info("WARNING>> prevUrl == nextUrl :/");
				response.sendRedirect(finalUrl);
				return false;
			}
		}
		
		
		
		
		
		
		// LOGIN NO
		// return 값이 true면 인터셉터가 낚아채서 조건이 충족되면 통과해준다.
		// return 값이 flase면 인터셉터가 이전 페이지로 이동한다.
		if(session.getAttribute("userid") == null) {
			log.info(">>>>> NOLOGIN:(");
			
			//
			//String uri = request.getRequestURI();
			//log.info(">>>>>목적지: " + uri);
			// referer = 바로 직전에 머물렀던 웹링크 주소
			// 이전 페이지 URL을 GET한다
			// URL만 신경쓴다. GET or POST는 중요하지 않다.
			// 예를들어) 회원수정페이지: GET:/member/update
			// 회원수정DB: POST:/member/update 
			// 수정페이지의 공통된 /member/update만 URL에 하나만 넣어두면 GET or POST를 쓰지 않아도 된다. 
			// request(GET, POST) 요청 보냄 > response(forward, sendRedirect) 응답 받음
				
				// Login No
				// 이전 페이지 URL을 GET(referer이 없는 경우) 인덱스로 이동 
				// 외부에서 접근했을 때 오류가 날 때,
				//if(referer == null) {
					// URL로 바로 접근한 경우
					//referer = "http://localhost:8081/creme/";
					//} else {	
					// 내부에서(내 페이지) 접근했을때 오류가 날 때,
					// 게시글 등록, 수정(로그인이 필요한 View단)
					
			
				// 로그인 후 게시판 - 글쓰기에서 로그아웃을 하면 메인페이지로 돌아가는게 아닌, /board/list 페이지로 돌아간다.
				//if(mapWord.equals("/write")) {
				//response.sendRedirect(request.getContextPath() + "/board/list"); // URL을 /board/list로 다시 보낸다.
				//return false;
				//}
				//}	
			
			
			
				FlashMap fMap = RequestContextUtils.getOutputFlashMap(request);
				fMap.put("message", "nologin");
				if(qString != null) {
					uri = uri + "?" + qString;  // 쿼리스트링이 null값이 아니면 uri에 ?를 붙여서 보내라.
				}
				
				fMap.put("uri", uri);	
			
				// URL로 바로 접근한 경우(referer이 없는 경우) 인덱스로 이동 
				RequestContextUtils.saveOutputFlashMap(referer, request, response);
				response.sendRedirect(referer);
			
			//response.sendRedirect(referer+"?message=nologin"); // 값을 보내줘야하므로 쿼리스트링을 쓴다.
			
				return false; // 원래 가려던 곳으로 이동 할 수 없어!	
			
			} else { // LOGIN OK	
				log.info(">>>>> LOGIN:)");
				return true; // 원래 가려던 곳으로 이동해!
			}
		
		}
	}
	
// postHandle == url 후 
//	@Override
//	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
//			ModelAndView modelAndView) throws Exception {
//		// TODO Auto-generated method stub
//		super.postHandle(request, response, handler, modelAndView);
//	}
	
