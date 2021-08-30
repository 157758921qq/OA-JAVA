package com.marcus.oa.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.marcus.oa.config.security.JwtTokenUtil;
import com.marcus.oa.mapper.AdminMapper;
import com.marcus.oa.mapper.RoleMapper;
import com.marcus.oa.pojo.Admin;
import com.marcus.oa.pojo.Menu;
import com.marcus.oa.pojo.RespBean;
import com.marcus.oa.pojo.Role;
import com.marcus.oa.service.IAdminService;
import com.marcus.oa.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author marcus
 * @since 2021-08-20
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    /**
     * 登录后返回token
     * @param username
     * @param password
     * @param code
     * @param request
     * @return
     */
    @Override
    public RespBean login(String username, String password, String code, HttpServletRequest request) {
        System.out.println("username :"+ username);
        System.out.println("password :"+ password);
        System.out.println("code :"+ code);
        //获取验证码
        String captcha = (String) request.getSession().getAttribute("captcha");
        if(StringUtils.isEmpty(code) || !captcha.equalsIgnoreCase(code)){
            return RespBean.error("验证码输入错误，请重新输入！");
        }

        //通过安全框架获取到UserDetails， loadUserByUsername()实现用户登录
        //需要注入UserDetailsService
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        //判断这个UserDetails
        //UserDetails为空  或者密码不正确
        //spring security 提供了加密密码的工具PasswordEncoder进行匹配
        //安全框架从数据库中通过用户名封装的对象的密码值 和 前端输入的密码不匹配时
        if(null == userDetails || !passwordEncoder.matches(password,userDetails.getPassword())){
            return RespBean.error("用户名或密码不正确");
        }
        if(!userDetails.isEnabled()){
            return RespBean.error("账号被禁用，请联系管理员");
        }

        //关键1------------------------------------------------
        //有效的登陆
        //登录成功，需要把用户对象放到spring security全文中
        //更新security安全框架中 登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);


        //关键2------------------------------------------------
        //拿到令牌
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);

        return RespBean.success("登录成功", tokenMap);
    }

    //mybatis-plus中SQL语句的写法
    @Override
    public Admin getAdminByUserName(String username) {
        return adminMapper.selectOne(new QueryWrapper<Admin>().eq("username",username).eq("enabled",true));
    }


    /**
     * 根据用户ID，查角色列表
     * @param adminId
     * @return
     */
    @Override
    public List<Role> getRoles(Integer adminId) {
        return roleMapper.getRoles(adminId);
    }


}