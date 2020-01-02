package org.yzh.web.component;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.yzh.web.config.SessionKey;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        Object loginName = httpServletRequest.getSession().getAttribute(SessionKey.USER_ID);
        if(loginName == null || !(loginName instanceof String))
        {
            httpServletResponse.sendRedirect("/login");
            return false;
        }

        String userName = (String) loginName;
        System.out.println("当前用户已登录，登录用户名为：" + userName);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
