package com.tio.app.user.filters;

import com.tio.app.common.oauth.TokenManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * 前端用户登录过滤器
 *
 * @author britton
 * @date <britton@126.com>
 */
@Slf4j
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("进入过滤器");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String token = request.getHeader("user-token");
        if (!StringUtils.isEmpty(token)) {
            Map<String, Object> map = TokenManager.decodeToken(token);
            int userId = (int) map.get("user_id");
            request.setAttribute("user", userId);
        }
        filterChain.doFilter(request, servletResponse);
    }

    @Override
    public void destroy() {
    }
}

