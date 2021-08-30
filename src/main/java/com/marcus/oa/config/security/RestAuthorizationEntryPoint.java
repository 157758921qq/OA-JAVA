package com.marcus.oa.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marcus.oa.pojo.RespBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 未登录 或  token失效的时候
 * 访问接口时，自定的一个返回结果
 *
 */
@Component
public class RestAuthorizationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json"); //返回json格式
        PrintWriter out = response.getWriter();
        RespBean bean = RespBean.error("未登录 或  token失效的时候，权限不足，请联系管理员");
        bean.setCode(401);
        out.write(new ObjectMapper().writeValueAsString(bean));
        out.flush();
        out.close();
    }
}
