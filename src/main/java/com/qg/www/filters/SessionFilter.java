package com.qg.www.filters;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author net
 * @version 1.0
 * 过滤处理Session
 */
public class SessionFilter  extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        HttpServletResponse response = (HttpServletResponse) httpServletResponse;

        HttpServletRequest reqs = (HttpServletRequest) httpServletRequest;
        //reqs.getHeader("Origin")可换成ip地址或null
        response.setHeader("Access-Control-Allow-Origin",reqs.getHeader("Origin"));
        //        response.setHeader("Access-Control-Allow-Origin","*");
        //允许发送cookie
        response.setHeader("Access-Control-Allow-Credentials", "true");
        //发送方式
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PATCH, DELETE, PUT");
        //过期时间
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
