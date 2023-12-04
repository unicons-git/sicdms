package egovframework.com.sec.security.filter;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.g3way.sicims.exception.SicimsException;
import com.g3way.sicims.services.sicims900Sys.login.LoginService;
import com.g3way.sicims.services.sicims910User.vo.CmUserVo;
import com.g3way.sicims.util.common.StringUtil;
import com.g3way.sicims.util.encrypt.EgovFileScrty;
import com.g3way.sicims.util.encrypt.RsaUtil;

/**
 *
 * @author 공통서비스 개발팀 서준식
 * @since 2011. 8. 29.
 * @version 1.0
 * @see
 *
 * <pre>
 * 개정이력(Modification Information)
 *
 *     수정일                 수정자        	  수정내용
 *  -----------    --------   ---------------------------
 *  2011.08.29    	 서준식        	 최초생성
 *  2011.12.12      유지보수        사용자 로그인 정보 간섭 가능성 문제(멤버 변수 EgovUserDetails userDetails를 로컬변수로 변경)
 *  2014.03.07      유지보수        로그인된 상태에서 다시 로그인 시 미처리 되는 문제 수정 (로그인 처리 URL 파라미터화)
 *
 *  </pre>
 */

public class EgovSpringSecurityLoginFilter implements Filter {

	private FilterConfig config;

	private static final Logger LOGGER = LoggerFactory.getLogger(EgovSpringSecurityLoginFilter.class);

	public void destroy() {/*필터클래스 상속 함수 */}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		LOGGER.info("EgovSpringSecurityLoginFilter called...");

		// web.xml : 로그인 실패시 반환될 될 URL설정 : /login/loginFail.do
		String loginURL = config.getInitParameter("loginURL");
		loginURL = loginURL.replaceAll("\r", "").replaceAll("\n", "");

		// web.xml : 로그인 처리 URL설정 : /login/actionLogin
		String loginProcessURL = config.getInitParameter("loginProcessURL");
		loginProcessURL = loginProcessURL.replaceAll("\r", "").replaceAll("\n", "");

		// web.xml : 세션 타임아웃  URL설정 : /login/sessionTimeout
		String sessionTimeoutUrl = config.getInitParameter("sessionTimeoutUrl");
		sessionTimeoutUrl = sessionTimeoutUrl.replaceAll("\r", "").replaceAll("\n", "");


		//getRequiredWebApplicationContext : 일반적으로 WebApplicationContext를 통해 로드되는 이 웹 앱 의 루트를 찾습니다
		ApplicationContext act = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		LoginService loginService = (LoginService) act.getBean("loginService");
		EgovMessageSource egovMessageSource = (EgovMessageSource) act.getBean("egovMessageSource");

		HttpServletRequest 	httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession 		session = httpRequest.getSession();
		String 				isRemotelyAuthenticated = (String) session.getAttribute("isRemotelyAuthenticated");
		String 				requestURL = ((HttpServletRequest) request).getRequestURI();

		LOGGER.info("requestURL = " + requestURL);
/*
		LOGGER.info("requestURL = " + requestURL);
		LOGGER.info("loginProcessURL = " + loginProcessURL);
		LOGGER.info("getAuthenticatedUser = " + EgovUserDetailsHelper.getAuthenticatedUser());
		LOGGER.info("isRemotelyAuthenticated = " + isRemotelyAuthenticated);
		LOGGER.info("isAuthenticated = " + EgovUserDetailsHelper.isAuthenticated());
*/
		//스프링 시큐리티 인증이 처리 되었는지 EgovUserDetailsHelper.getAuthenticatedUser() 메서드를 통해 확인한다.
		//context-common.xml 빈 설정에 egovUserDetailsSecurityService를 등록 해서 사용해야 정상적으로 동작한다.

		if (EgovUserDetailsHelper.getAuthenticatedUser() == null || requestURL.contains(loginProcessURL)) {
			if (isRemotelyAuthenticated != null && isRemotelyAuthenticated.equals("true")) {
				try {
					//세션 토큰 정보를 가지고 DB로부터 사용자 정보를 가져옴
					CmUserVo cmUserVo = (CmUserVo) session.getAttribute("loginVoForDBAuthentication");
					if (cmUserVo != null && cmUserVo.getUserId() != null && !cmUserVo.getUserId().equals("")) {
						//세션 로그인
						//사용자 IP 기록
						cmUserVo.setUpdusrId(cmUserVo.getUserId());
						cmUserVo.setUpdusrIp(request.getRemoteAddr());

						session.setAttribute("member", cmUserVo);

						//로컬 인증결과 세션에 저장
						session.setAttribute("isLocallyAuthenticated", "true");

						UsernamePasswordAuthenticationFilter springSecurity = null;

						//UsernamePasswordAuthenticationFilter : 아이디, 패스워드 기반의 인증을 담당
						//Spring Security Login Form에서 허용된 기본 username과 password 외에 사용자가 정의한 입력 필드를 추가하고 이를 파라미터로 넘기기
						Map<String, UsernamePasswordAuthenticationFilter> beans = act.getBeansOfType(UsernamePasswordAuthenticationFilter.class);
						if (beans.size() > 0) {
							springSecurity = (UsernamePasswordAuthenticationFilter) beans.values().toArray()[0];
							springSecurity.setUsernameParameter("egov_security_username");
							springSecurity.setPasswordParameter("egov_security_password");
							springSecurity.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(httpRequest.getContextPath() + "/egov_security_login", "POST"));
						} else {
							LOGGER.error("No AuthenticationProcessingFilter");
							throw new IllegalStateException("No AuthenticationProcessingFilter");
						}

						LOGGER.debug("before security filter call....");
						springSecurity.doFilter(new RequestWrapperForSecurity(httpRequest, cmUserVo.getUserId(), cmUserVo.getUserId()), httpResponse, chain);
						LOGGER.debug("after security filter call....");
					}
				} catch (IllegalStateException ex) {
					//DB인증 예외가 발생할 경우 로그를 남기고 로컬인증을 시키지 않고 그대로 진행함.
					LOGGER.debug("Local authentication Fail : {}", ex.getMessage());
				} catch (Exception ex) {
					//DB인증 예외가 발생할 경우 로그를 남기고 로컬인증을 시키지 않고 그대로 진행함.
					LOGGER.debug("Local authentication Fail : {}", ex.getMessage());
				}
			} else if (isRemotelyAuthenticated == null) {
				if (requestURL.contains(loginProcessURL)) {
					String password = httpRequest.getParameter("userPswd");
					String userId   = httpRequest.getParameter("userId");

					if(password != null && password.length() == 512){
						try{
							// 패스워드 암호화시 복호화
							password = RsaUtil.decryptRsa(password, session);
							if(password == null){
								 httpRequest.setAttribute("message", "암호화 유효기간이 종료되었습니다. 새로고침 후 다시 시도해주세요.");
								 RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(loginURL);
								 dispatcher.forward(httpRequest, httpResponse);
								 return;
							}
						} catch(SicimsException e){
							 httpRequest.setAttribute("message", "암호화 유효기간이 종료되었습니다. 새로고침 후 다시 시도해주세요.");
							 RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(loginURL);
							 dispatcher.forward(httpRequest, httpResponse);
							 return;
						}
					}

					if (password == null || password.equals("") || password.length() < 4 || password.length() > 16) {
						httpRequest.setAttribute("message", egovMessageSource.getMessage("fail.common.login.password"));
						RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(loginURL);
						dispatcher.forward(httpRequest, httpResponse);
						return;
					}

					if(password != null && password.length() != 0) {
						try {
							password = EgovFileScrty.encryptPassword(password, userId);
						} catch (NoSuchAlgorithmException e) {
							LOGGER.error("암호화 중 오류가 발생했습니다. NoSuchAlgorithmException");
						}
					}

					CmUserVo cmUserVo = new CmUserVo();
					cmUserVo.setUserId(userId);

					try {
						LOGGER.info("loginService.getLoginInfo(loginVo)");
						//사용자 입력 id, password로 DB 인증을 실행함
						cmUserVo = loginService.getLoginInfo(cmUserVo);

						//사용자 IP 기록
						cmUserVo.setUpdusrIp(request.getRemoteAddr());

						//사용자 정보가 존재하고 사용승인이 되어 있고 비밀번호 오류가 5회 이하인경우 로그인 처리
						if (cmUserVo != null && cmUserVo.getUserId() != null && !cmUserVo.getUserId().equals("")
							&& cmUserVo.getAprvYn() != null && "Y".equals(cmUserVo.getAprvYn())
							&& password.equals(cmUserVo.getUserPswd())
							&& (cmUserVo.getPswdErrCnt() != null && cmUserVo.getPswdErrCnt().compareTo(new BigDecimal(5)) <= 0)
							&& loginService.getCmRhrcInfo(cmUserVo.getUserId(), "ROLE_USER") > 0
							){

							cmUserVo.setUpdusrId(cmUserVo.getUserId());
							cmUserVo.setUpdusrIp(request.getRemoteAddr());

							//세션 로그인
							session.setAttribute("member", cmUserVo);

							//로컬 인증결과 세션에 저장
							session.setAttribute("isLocallyAuthenticated", "true");

							UsernamePasswordAuthenticationFilter springSecurity = null;

							Map<String, UsernamePasswordAuthenticationFilter> beans = act.getBeansOfType(UsernamePasswordAuthenticationFilter.class);
							if (beans.size() > 0) {
								springSecurity = (UsernamePasswordAuthenticationFilter) beans.values().toArray()[0];
								springSecurity.setUsernameParameter("egov_security_username");
								springSecurity.setPasswordParameter("egov_security_password");
								springSecurity.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher(httpRequest.getContextPath() + "/egov_security_login", "POST"));
							} else {
								LOGGER.error("No AuthenticationProcessingFilter");
								throw new IllegalStateException("No AuthenticationProcessingFilter");
							}

							LOGGER.info("before security filter call....");
							springSecurity.doFilter(new RequestWrapperForSecurity(httpRequest, cmUserVo.getUserId(), cmUserVo.getUserPswd()), httpResponse, chain);
							LOGGER.info("after security filter call....");

							cmUserVo.setUserId(userId);
							cmUserVo.setTblNm("cm_user_t");
							loginService.updateLoginSuccess(cmUserVo); //패스워드 오류 초기화및 마지막 로그인날짜 수정
						} else {
							if(!"Y".equals(cmUserVo.getUseYn()))  {
								httpRequest.setAttribute("message", "사용할 수 없는 계정입니다.");
							} else if(cmUserVo != null && cmUserVo.getPswdErrCnt() != null && cmUserVo.getPswdErrCnt().compareTo(new BigDecimal(4)) == 1)  {
								Object[] args = {"5"};
								httpRequest.setAttribute("message", egovMessageSource.getMessage("fail.common.loginIncorrect", args));

								if(cmUserVo != null && (!password.equals(cmUserVo.getUserPswd()))){
									cmUserVo.setUserId(userId);
									// 업무시스템사용자일 경우
									if(!StringUtil.checkExp("emlAddr", cmUserVo.getUserId())){
										loginService.updateLoginFail(cmUserVo); //패스워드 오류 증가
									}
								}
							} else if(cmUserVo != null && cmUserVo.getUserId() == null){
								httpRequest.setAttribute("message", egovMessageSource.getMessage("fail.common.idsearch"));
							} else if(cmUserVo != null && (!password.equals(cmUserVo.getUserPswd()))){
								// 외부사용자일 경우
								if(StringUtil.checkExp("emlAddr", cmUserVo.getUserId())){
									httpRequest.setAttribute("message", egovMessageSource.getMessage("fail.common.login.ecmp"));
								} else {
									int pswdErrCnt = 1;
									if(cmUserVo.getUserPswd() != null  ) {
										pswdErrCnt = cmUserVo.getPswdErrCnt().intValue( )+ 1;
									}

									Object[] args = {pswdErrCnt,  "5"};
									httpRequest.setAttribute("message", egovMessageSource.getMessage("fail.common.login", args));

									cmUserVo.setUserId(userId);
									loginService.updateLoginFail(cmUserVo); //패스워드 오류 증가
								}

							} else if (cmUserVo != null  && (cmUserVo.getAprvDt() == null || "".equals(cmUserVo.getAprvDt().trim())
									|| cmUserVo.getAutzrId() == null || "".equals(cmUserVo.getAutzrId().trim()))) {
								//승인이 안되었거나  승인자 id가 null인 경우 미승인 상태로 간주하여 로그인 화면으로 redirect 시킴
								httpRequest.setAttribute("message", egovMessageSource.getMessage("fail.common.login.aprv"));

								//해당세션에 미승인시 아이디 임시저장
								httpRequest.setAttribute("errorCode", "NotAprvId");
								httpRequest.getSession().setAttribute("encodeUserId", EgovFileScrty.encodeBinary(cmUserVo.getUserId().getBytes()));
							} else if(cmUserVo !=null && (!"ROLE_ADMIN".equals(cmUserVo.getAuthrtCd()) || !"ROLE_KALIS".equals(cmUserVo.getAuthrtCd()) || !"ROLE_MNG".equals(cmUserVo.getAuthrtCd()))){
									httpRequest.setAttribute("message", "해당 계정은 관리자 권한이 없어서 로그인 할 수 없습니다.");
							} else {
								httpRequest.setAttribute("message", egovMessageSource.getMessage("fail.common.login.ecmp"));
							}

							RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(loginURL);
							dispatcher.forward(httpRequest, httpResponse);

							return;
						}
					} catch(SicimsException ex){
						//DB인증 예외가 발생할 경우 로그인 화면으로 redirect 시킴
						LOGGER.error("Login Exception : {}", ex.getCause(), ex);
						httpRequest.setAttribute("message", egovMessageSource.getMessage("fail.common.login.ecmp"));
						RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(loginURL);
						dispatcher.forward(httpRequest, httpResponse);

						return;

					} catch (Exception ex) {
						//DB인증 예외가 발생할 경우 로그인 화면으로 redirect 시킴
						LOGGER.error("Login Exception : {}", ex.getCause(), ex);
						httpRequest.setAttribute("message", egovMessageSource.getMessage("fail.common.login.ecmp"));
						RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(loginURL);
						dispatcher.forward(httpRequest, httpResponse);

						return;
					}
					return;
				}
			}
		}

		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.config = filterConfig;
	}
}



class RequestWrapperForSecurity extends HttpServletRequestWrapper {
	private final String username;
	private final String password ;

	public RequestWrapperForSecurity(HttpServletRequest request, String username, String password) {
		super(request);

		this.username = username;
		this.password = password;
	}

	@Override
	public String getServletPath() {
		return ((HttpServletRequest) super.getRequest()).getContextPath() + "/egov_security_login";
	}

	@Override
	public String getRequestURI() {
		return ((HttpServletRequest) super.getRequest()).getContextPath() + "/egov_security_login";
	}

	@Override
	public String getParameter(String name) {
		if (name.equals("egov_security_username")) {
			return username;
		}

		if (name.equals("egov_security_password")) {
			return password;
		}

		return super.getParameter(name);
	}
}
