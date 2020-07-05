package com.mypro.controller.system;

import com.mypro.common.config.Global;
import com.mypro.system.domain.SysMenu;
import com.mypro.system.service.ISysMenuService;
import com.mypro.system.service.ISysUserService;
import com.mypro.common.utils.SecurityUtils;
import com.mypro.system.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Index控制器
 * @author houhaotong
 */
@Controller
public class SysIndexController {

    @Autowired
    ISysUserService userService;

    @Autowired
    ISysMenuService menuService;
    /**
     * 首页
     */
    @GetMapping({"/index","/"})
    public String index(ModelMap map, HttpServletRequest request){
        //获取当前用户信息
        String loginName = SecurityUtils.getCurrentUserName(request);
        SysUser user = userService.selectUserByLoginName(loginName);
        //获取在线人数
        int onlineNum=userService.getOnlineNum();
        int pageView=userService.getPageView();
        //查询表单
        List<SysMenu> menus = menuService.selectMenuByLoginName(loginName, user.isAdmin());
        map.put("onlineNum",onlineNum);
        map.put("user",user);
        map.put("menus",menus);
        map.put("demoEnabled", Global.isDemoEnabled());
        map.put("pageView",pageView);
        return "index";
    }

    @GetMapping("/system/main")
    public String main(){
        return "main";
    }
}
