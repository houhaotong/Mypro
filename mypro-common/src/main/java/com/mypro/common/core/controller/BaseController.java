package com.mypro.common.core.controller;

import com.mypro.common.utils.ServletUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * web层通用信息处理
 * @author houhaotong
 */
public class BaseController {

    /**
     * 重定向页面
     * @param url 路径
     * @return 处理后
     */
    public String redirect(String url){
        return "redirect:"+url;
    }

    /**
     * 获取request
     * @return request
     */
    public HttpServletRequest getRequest(){
        return ServletUtil.getRequest();
    }

    /**
     * 获取response
     * @return response
     */
    public HttpServletResponse getResponse(){
        return ServletUtil.getResponse();
    }
}
