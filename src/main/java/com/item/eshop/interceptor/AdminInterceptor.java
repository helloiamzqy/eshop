package com.item.eshop.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession httpSession = request.getSession();
        Integer status = (Integer) httpSession.getAttribute("status");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/text; charset=utf-8");
        PrintWriter out = null ;
        if(status==null||status!=9) {
            try {
                out = response.getWriter();
                out.append("请登录管理员帐号！");
                return false;
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(500);
                return false;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
