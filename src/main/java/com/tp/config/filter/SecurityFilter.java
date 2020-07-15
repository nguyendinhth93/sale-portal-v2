package com.tp.config.filter;

import static com.tp.util.Const.FACES_REQUEST_HEADER;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.tp.config.SystemConfig;
import com.tp.util.Const;
import com.tp.util.DataUtil;

/**
 * Created by hopnv on 22/07/2017.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityFilter implements Filter {

    @Autowired
    private SystemConfig systemConfig;

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;

        if (request.getServletPath().startsWith(Const.BYPASS_RESOURCE) ||
                systemConfig.getBypassUrls().contains(request.getServletPath())) {
            chain.doFilter(req, res);
            return;
        }

        Boolean isAuthenticated = (Boolean) request.getSession().getAttribute(Const.AUTHENTICATED);
        if (isAuthenticated != null && isAuthenticated) {
            if (systemConfig.getIndexUrl().equals(request.getServletPath())) { //default user has role index
                chain.doFilter(req, res);
                return;
            }
            List<String> roles = (List<String>) request.getSession().getAttribute(Const.ROLE_MODULE);
            if (DataUtil.isNullOrEmpty(roles)) {
                response.sendRedirect(request.getContextPath() + systemConfig.getForbiddenUrl());
                return;
            } else {
                if (roles.contains(request.getServletPath()))
                    chain.doFilter(req, res);
                else {
                    response.sendRedirect(request.getContextPath() + systemConfig.getForbiddenUrl());
                    return;
                }
            }
        } else {

            boolean ajaxRedirect = "partial/ajax".equals(request.getHeader(FACES_REQUEST_HEADER));
            if(!ajaxRedirect) {
                response.sendRedirect(request.getContextPath() + systemConfig.getLoginUrl());
                return;
            } else {
                String ajaxRedirectXml = createAjaxRedirectXml(request.getContextPath() + systemConfig.getLoginUrl());
                response.setContentType("text/xml");
                response.getWriter().write(ajaxRedirectXml);
            }
        }
    }

    private String createAjaxRedirectXml(String redirectUrl) {
        return new StringBuilder().append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
                .append("<partial-response><redirect url=\"")
                .append(redirectUrl)
                .append("\"></redirect></partial-response>").toString();
    }

    public String convertServletPath(String uri, String path){
        return path.replaceFirst(uri, "");
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                filterConfig.getServletContext());
    }

    public void destroy() {

    }

}
