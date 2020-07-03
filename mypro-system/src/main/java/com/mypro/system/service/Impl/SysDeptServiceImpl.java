package com.mypro.system.service.Impl;

import com.mypro.system.domain.SysDept;
import com.mypro.system.domain.ZTree;
import com.mypro.system.mapper.ISysDeptMapper;
import com.mypro.system.service.ISysDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 部门服务实现类
 * @author houhaotong
 */
@Service
public class SysDeptServiceImpl implements ISysDeptService {

    @Autowired
    private ISysDeptMapper deptMapper;
    /**
     * 部门id获得部门信息
     * @param deptId 部门id
     * @return 部门信息
     */
    @Override
    public SysDept selectDeptById(Long deptId) {
        return deptMapper.selectDeptById(deptId);
    }

    @Override
    public List<ZTree> selectDeptTree(SysDept sysDept) {
        List<SysDept> depts=deptMapper.selectDeptByDept(sysDept);
        return initTree(depts);
    }

    @Override
    public Long selectDeptIdByDeptName(String deptName) {
        return deptMapper.selectDeptIdByDeptName(deptName);
    }

    /**
     * 对象转部门树
     * @param deptList 部门列表
     * @return 树
     */
    public List<ZTree> initTree(List<SysDept> deptList){
        return initTree(deptList,null);
    }

    /**
     *对象转部门树
     * @param deptList 部门列表
     * @param roleDeptList 角色已存在的菜单列表
     * @return 树
     */
    private List<ZTree> initTree(List<SysDept> deptList, List<String> roleDeptList) {
        List<ZTree> zTrees=new ArrayList<ZTree>();
        for (SysDept dept : deptList) {
            if("0".equals(dept.getStatus())){
                ZTree zTree=new ZTree();
                zTree.setpId(dept.getParentId());
                zTree.setId(dept.getDeptId());
                zTree.setName(dept.getDeptName());
                zTree.setTitle(dept.getDeptName());
                zTrees.add(zTree);
            }
        }
        return zTrees;
    }
}
