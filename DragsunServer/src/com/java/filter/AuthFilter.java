package com.java.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * Created by zhuangjiesen on 2017/9/16.
 */
public class AuthFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        System.out.println(" i am doing auth filter......");


        filterChain.doFilter(servletRequest , servletResponse);
    }

    public void destroy() {

    }
}
