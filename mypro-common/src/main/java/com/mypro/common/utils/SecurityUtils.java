package com.mypro.common.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;


/**
 * SpringSecurity 工具类
 * @author houhaotong
 */
public class SecurityUtils {

    /**
     * 获取当前用户认证信息
     * @return 认证对象
     */
    public static Authentication getUserAuthentication(HttpServletRequest request){
        return  ((SecurityContextImpl)request.getSession().getAttribute("SPRING_SECURITY_CONTEXT")).getAuthentication();
    }

    /**
     * 获取当前用户信息
     * @return 用户对象
     */
    public static UserDetails getCurrentPrincipal(HttpServletRequest request){
        return (UserDetails) getUserAuthentication(request).getPrincipal();
    }

    /**
     * 获取当前用户的用户名
     * @param request http请求
     * @return 用户名
     */
    public static String getCurrentUserName(HttpServletRequest request){
         return getCurrentPrincipal(request).getUsername();
    }

}
