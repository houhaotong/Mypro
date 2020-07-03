package com.mypro.system.Handler;

import com.mypro.common.utils.RedisUtils;
import com.mypro.system.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 注销处理器
 * @author houhaotong
 */
public class MyLogoutHandler implements LogoutHandler {

    RedisUtils redisUtils;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
        redisUtils=(RedisUtils) applicationContext.getBean("redisUtils");
        User user = (User)authentication.getPrincipal();
        redisUtils.zRem("onlineUser",user.getUsername());
    }
}
