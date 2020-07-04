package com.mypro.controller.monitor;

import com.alibaba.fastjson.JSON;
import com.mypro.system.domain.SysUser;
import com.mypro.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 在线用户
 * @author houhaotong
 */
@Controller
@RequestMapping("/monitor/online")
public class OnlineController {

    @Autowired
    ISysUserService userService;

    @Autowired
    SessionRegistry sessionRegistry;

    @GetMapping()
    public String onlineUser(){
        return "/system/monitor/onlineUser";
    }

    @GetMapping("/all")
    @ResponseBody
    public String selectAll(){
        List<SysUser> onlineUsers = userService.getOnlineUser();
        String s = JSON.toJSONString(onlineUsers);
        return s;
    }

    /** 踢出用户 */
    @PreAuthorize("hasRole('管理员')")
    @GetMapping("/logout/{id}")
    @ResponseBody
    public String logout(@PathVariable Long id){
        SysUser sysUser = userService.selectUserByUserId(id);
        List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
        for (Object allPrincipal : allPrincipals) {
            User user=(User)allPrincipal;
            if(user.getUsername().equals(sysUser.getLoginName())){
                List<SessionInformation> allSessions = sessionRegistry.getAllSessions(allPrincipal, false);
                for (SessionInformation session : allSessions) {
                    session.expireNow();
                }
            }
        }
        return "ok";
    }
}
