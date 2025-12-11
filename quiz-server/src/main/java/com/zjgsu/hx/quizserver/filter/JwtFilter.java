package com.zjgsu.hx.quizserver.filter;

import com.zjgsu.hx.quizserver.common.ApiResponse;
import com.alibaba.fastjson.JSONObject;
import com.zjgsu.hx.quizserver.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.StringUtils;

import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class JwtFilter implements Filter {

    //初始化，只调用一次；
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("JwtFilter init");
    }

    @Override  //拦截请求；每当有新请求时；
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws ServletException, IOException {
        System.out.println("JwtFilter拦截到请求");
        //chain.doFilter(req, res);
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String url=request.getRequestURL().toString();

        // 处理 OPTIONS 预检请求
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            System.out.println("OPTIONS预检请求，直接放行");
            chain.doFilter(request, response);
            return;
        }
        System.out.println("请求URL: " + url + ", 方法: " + request.getMethod());

        if(url.contains("login")||url.contains("Register")){
            chain.doFilter(request, response);
            return;
        }
        String token = request.getHeader("token");
        if(!StringUtils.hasLength(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ApiResponse<?> result = ApiResponse.error(401, "NOT_LOGIN");
            String noLogin= JSONObject.toJSONString(result);
            response.setContentType("application/json;charset=UTF-8");
            res.getWriter().write(noLogin);
            return;
        }
        try {
            JwtUtils.parseToken(token);//只要解析不成功，就说明有问题；
        }catch(Exception e){
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            ApiResponse<?> result = ApiResponse.error(401,"NOT_LOGIN");
            //手动将对象转为json，并传回前端；
            String noLogin=JSONObject.toJSONString(result);
            response.setContentType("application/json;charset=UTF-8");
            res.getWriter().write(noLogin);
            return;
        }
        chain.doFilter(req,res);
    }


    //销毁，只调用一次；
    public void destroy() {
        System.out.println("JwtFilter destroy");
    }
}
