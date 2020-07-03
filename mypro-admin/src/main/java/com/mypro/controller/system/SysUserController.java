package com.mypro.controller.system;

import com.alibaba.fastjson.JSON;
import com.mypro.common.core.controller.BaseController;
import com.mypro.system.domain.SysRole;
import com.mypro.system.domain.SysUser;
import com.mypro.system.service.ISysDeptService;
import com.mypro.system.service.ISysRoleService;
import com.mypro.system.service.ISysUserService;
import com.mypro.common.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户控制器
 * @author houhaotong
 */
@Controller
@RequestMapping("/system/user")
public class SysUserController extends BaseController {

    private final String prefix="/system/user/";

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysDeptService deptService;

    @Autowired
    private ISysRoleService roleService;

    /**
     * 获取所有用户页面
     */
    @GetMapping()
    public String allUser(){
        return "/system/user/user";
    }

    @GetMapping("/all")
    @ResponseBody
    public String selectAll(){
        List<SysUser> users = userService.selectAllUser();
        String s = JSON.toJSONString(users);
        return s;
    }
    /**
     * 修改用户页面
     */
    @GetMapping("/{id}")
    public String userById(ModelMap map, @PathVariable("id") Long userId){
        SysUser user = userService.selectUserByUserId(userId);
        List<SysRole> roles = roleService.selectRolesByUserId(userId);
        map.addAttribute("user",user);
        map.addAttribute("roles",roles);
        return "/system/user/edit";
    }
    /**
     * 修改保存用户
     */
    @ResponseBody
    @PostMapping("/edit")
    public String updateUser(SysUser user, HttpServletRequest request){
        user.setUpdateBy(SecurityUtils.getCurrentUserName(getRequest()));
        userService.updateUser(user);
        return "ok";
    }

    /**
     * 修改用户状态
     */
    @ResponseBody
    @PostMapping("/changeStatus")
    public String changeStatus(@RequestBody Long userId){
        SysUser user = userService.selectUserByUserId(userId);
        String status="1".equals(user.getStatus())?"0":"1";
        user.setStatus(status);
        userService.updateUser(user);
        String done = JSON.toJSONString("change done");
        return done;
    }

    /**
     *删除用户
     */
    @PostMapping("/delete")
    @ResponseBody
    public String deleteByUserIds(String ids){
        userService.deleteByUserIds(ids);
        return "done";
    }

    @GetMapping("/add")
    public String add(ModelMap map){
        List<SysRole> roles = roleService.selectAllRole();
        map.addAttribute("roles",roles);
        return "/system/user/add";
    }

    @PostMapping("/add")
    @ResponseBody
    public ModelMap insertSave(SysUser user){
        ModelMap map = new ModelMap();
        if(!userService.checkLoginNameUnique(user.getLoginName())){
            map.put("error","登录名重复");
            return map;
        }
        Long deptId = deptService.selectDeptIdByDeptName(user.getDeptName());
        user.setDeptId(deptId);
        //加密
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setCreateBy(SecurityUtils.getCurrentUserName(getRequest()));
        userService.insertUser(user);
        map.put("res","done");
        return map;
    }
}
