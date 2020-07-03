package com.mypro.common.utils;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * servlet工具类
 * @author houhaotong
 */
public class ServletUtil {
    /**
     * 获取request
     * @return request
     */
    public static HttpServletRequest getRequest(){
        return getRequstAttributes().getRequest();
    }

    /**
     * 获取response
     * @return response
     */
    public static HttpServletResponse getResponse(){
        return getRequstAttributes().getResponse();
    }

    public static ServletRequestAttributes getRequstAttributes(){
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }
}
