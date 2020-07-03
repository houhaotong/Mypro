package com.mypro.system.service;

import com.mypro.system.domain.SysDept;
import com.mypro.system.domain.ZTree;

import java.util.List;

/**
 * 部门服务接口
 * @author houhaotong
 */
public interface ISysDeptService {

    /**
     * 通过部门id获得部门
     * @param deptId 部门id
     * @return 部门
     */
    public SysDept selectDeptById(Long deptId);

    /**
     * 获取部门树
     * @param sysDept 部门信息
     * @return 树
     */
    public List<ZTree> selectDeptTree(SysDept sysDept);

    /**
     * 通过部门名获取部门id
     * @param deptName 部门名称
     * @return 部门id
     */
    public Long selectDeptIdByDeptName(String deptName);
}
