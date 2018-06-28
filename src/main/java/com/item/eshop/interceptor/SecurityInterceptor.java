package com.item.eshop.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

public class SecurityInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 对来自后台的请求统一进行日志处理
         */
        HttpSession httpSession = request.getSession();
        String phone = (String)httpSession.getAttribute("phone");
        if(phone==null){
//            response.sendRedirect("/");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/text; charset=utf-8");
            PrintWriter out = null ;
                try {
                    out = response.getWriter();
                    out.append("Msg{code='0',content='请登录账号'}");
                    return false;
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendError(500);
                    return false;
                }
        }


//        log(request);
//        String url = request.getRequestURL().toString();
//        String method = request.getMethod();
//        String uri = request.getRequestURI();
//        String queryString = request.getQueryString();
//        System.out.println(request.getParameterMap());
//        logger.info(String.format("请求参数, url: %s, method: %s, uri: %s, params: %s", url, method, uri, queryString));
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    public void log(HttpServletRequest request){
        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String queryString = request.getQueryString();
        System.out.println(request.getParameterMap());
        logger.info(String.format("请求参数, url: %s, method: %s, uri: %s, params: %s", url, method, uri, queryString));
    }

}
