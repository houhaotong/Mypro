package com.mypro.controller.system;

import com.alibaba.fastjson.JSON;
import com.mypro.common.core.controller.BaseController;
import com.mypro.common.utils.SecurityUtils;
import com.mypro.system.domain.SysRole;
import com.mypro.system.domain.SysUser;
import com.mypro.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 角色控制器
 * @author houhaotong
 */
@Controller
@RequestMapping("/system/role")
public class SysRoleController extends BaseController {

    @Autowired
    ISysRoleService roleService;

    @GetMapping
    public String roleIndex(){
        return "/system/role/role";
    }

    @GetMapping("/all")
    @ResponseBody
    public String selectAll(){
        List<SysRole> sysRoles = roleService.selectAllRole();
        String s=JSON.toJSONString(sysRoles);
        return s;
    }

    @GetMapping("/{id}")
    public String updateRole(@PathVariable Long id, ModelMap map){
        SysRole role = roleService.selectRoleByRoleId(id);
        map.addAttribute("role",role);
        return "/system/role/edit";
    }

    /** 保存修改 */
    @ResponseBody
    @PostMapping("/save")
    public String saveRole(SysRole role){
        role.setUpdateBy(SecurityUtils.getCurrentUserName(getRequest()));
        roleService.updateRole(role);
        return "ok";
    }

    @PostMapping("/delete")
    @ResponseBody
    public String deleteByRoleIds(String ids){
        roleService.deleteByRoleIds(ids);
        return "ok";
    }
    /**
     * 修改角色状态
     */
    @ResponseBody
    @PostMapping("/changeStatus/{id}")
    public String changeStatus(@PathVariable Long id){
        SysRole role = roleService.selectRoleByRoleId(id);
        String status="1".equals(role.getStatus())?"0":"1";
        role.setStatus(status);
        role.setUpdateBy(SecurityUtils.getCurrentUserName(getRequest()));
        roleService.updateRole(role);
        return "ok";
    }
}
