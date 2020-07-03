package com.mypro.system.mapper;

import com.mypro.system.domain.SysDept;

import java.util.List;

/**
 * 部门信息 数据层
 * @author houhaotong
 */
public interface ISysDeptMapper {

    /**
     * 通过部门id获取部门
     * @param deptId 部门id
     * @return 部门信息
     */
    public SysDept selectDeptById(Long deptId);

    /**
     * 获取部门信息
     * @param sysDept 部门
     * @return 部门集合
     */
    public List<SysDept> selectDeptByDept(SysDept sysDept);

    /**
     * 通过部门名称获取部门id
     * @param deptName 部门名称
     * @return 部门id
     */
    public Long selectDeptIdByDeptName(String deptName);
}
