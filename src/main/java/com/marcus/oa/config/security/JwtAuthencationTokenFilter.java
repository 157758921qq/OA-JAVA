package com.marcus.oa.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * jwt 登录授权过滤器
 */
public class JwtAuthencationTokenFilter extends OncePerRequestFilter {

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {//
        //从request中拿到  要验证的头
        String authHeader = request.getHeader(tokenHeader);
       //存在token
        if(null != authHeader && authHeader.startsWith(tokenHead)){
            //authHeader.substring 就可以获取到jwt的令牌  -- token
            String authToken = authHeader.substring(tokenHead.length());
            //通过token获得到用户名
            String username = jwtTokenUtil.getUserNameFromToken(authToken);

            //token存在，有用户名但在security全局中拿不到登陆的对象，未登录
            if(null != username && null == SecurityContextHolder.getContext().getAuthentication()){
                //登录
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);



                //登陆完成，重新设置spring security的全局上下文对象中
                //判断Token是否有效
                if(jwtTokenUtil.validateToken(authToken, userDetails)){

                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null , userDetails.getAuthorities()
                            );

                    //token重新设置到用户对象中
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            }
        }
        //放行
        filterChain.doFilter(request,response);
    }
}
