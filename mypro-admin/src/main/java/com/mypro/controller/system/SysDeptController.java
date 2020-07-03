package com.mypro.controller.system;

import com.mypro.common.core.controller.BaseController;
import com.mypro.system.domain.SysDept;
import com.mypro.system.domain.ZTree;
import com.mypro.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 部门控制器
 * @author houhaotong
 */
@Controller
@RequestMapping("/system/dept")
public class SysDeptController extends BaseController {

    @Autowired
    private ISysDeptService deptService;

    /**
     * 选择部门树
     *
     * @param deptId 部门ID
     * @param excludeId 排除ID
     */
    @GetMapping(value = { "/selectDeptTree/{deptId}", "/selectDeptTree/{deptId}/{excludeId}" })
    public String selectDeptTree(@PathVariable("deptId") Long deptId,
                                 @PathVariable(value = "excludeId", required = false) String excludeId, ModelMap map)
    {
        map.addAttribute("dept", deptService.selectDeptById(deptId));
        map.addAttribute("excludeId", excludeId);
        return "/system/dept/tree";
    }

    @GetMapping(value = "/treeData")
    @ResponseBody
    public List<ZTree> treeData()
    {
        return deptService.selectDeptTree(new SysDept());
    }

}
