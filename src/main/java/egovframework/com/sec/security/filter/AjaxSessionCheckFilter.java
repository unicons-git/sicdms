package egovframework.com.sec.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;

public class AjaxSessionCheckFilter implements Filter { 

    private String ajaxHeader;
   
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (isAjaxRequest(req)) {
            try {
                chain.doFilter(req, res);
            } catch (AccessDeniedException e) {
                res.sendError(HttpServletResponse.SC_FORBIDDEN);
            } catch (AuthenticationException e) {
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else {
            chain.doFilter(req, res);
        }
    }

    private boolean isAjaxRequest(HttpServletRequest req) {
        return req.getHeader(ajaxHeader) != null    && req.getHeader(ajaxHeader).equals(Boolean.TRUE.toString());
    }
    
    public void setAjaxHeader(String ajaxHeader){
    	this.ajaxHeader = ajaxHeader;
    }
    
    /**
     * @Method Name		: init
     * @Class Name		: AjaxSessionCheckFilter
     * @Description		: 초기화 함수
     * @Author			: JunHee
     * @Since			: 2021. 3. 18. 오전 11:03:37
     * @param filterConfig
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    public void init(FilterConfig filterConfig) {/*필터클래스 상속 함수 */}
    
    /**
     * @Method Name		: destroy
     * @Class Name		: AjaxSessionCheckFilter
     * @Description		: 인스턴스 종료 함수
     * @Author			: JunHee
     * @Since			: 2021. 3. 18. 오전 11:03:48
     * @see javax.servlet.Filter#destroy()
     */
    public void destroy() {/*필터클래스 상속 함수 */}
}
