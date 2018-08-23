package com.qg.www.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author net
 * @version 1.0
 * 用户登录拦截器
 */
public class LoginHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String url = httpServletRequest.getRequestURI();
        System.out.println("用户正在访问："+url);
        if (url.indexOf("views") > 0) {
            if (url.indexOf("login.html")>0){
                return true;
            }else {
                HttpSession session = httpServletRequest.getSession();
               Integer user = (Integer) session.getAttribute("privilege");

                System.out.println("拦截权限 is :"+user);
                if (null == user) {
                    httpServletResponse.sendRedirect("login.html");
                    return false;
                }else {
                    return true;
                }
            }
        }else{
            return false;
        }
    }
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
