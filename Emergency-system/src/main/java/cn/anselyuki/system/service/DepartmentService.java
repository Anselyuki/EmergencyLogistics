package cn.anselyuki.system.service;

import cn.anselyuki.common.error.SystemException;
import cn.anselyuki.common.model.system.Department;
import cn.anselyuki.common.vo.system.DeanVO;
import cn.anselyuki.common.vo.system.DepartmentVO;
import cn.anselyuki.common.vo.system.PageVO;

import java.util.List;

/**
 * @author AnselYuki
 * @date 2022/9/15 14:12
 **/
public interface DepartmentService {
    /**
     * 部门列表
     *
     */
    PageVO<DepartmentVO> findDepartmentList(Integer pageNum, Integer pageSize, DepartmentVO departmentVO);

    /**
     * 查询所有部门主任
     *
     */
    List<DeanVO> findDeanList();

    /**
     * 添加院部门
     *
     */
    void add(DepartmentVO departmentVO);

    /**
     * 编辑院部门
     *
     */
    DepartmentVO edit(Long id) throws SystemException;

    /**
     * 更新院部门
     *
     */
    void update(Long id, DepartmentVO departmentVO) throws SystemException;

    /**
     * 删除院部门
     *
     */
    void delete(Long id) throws SystemException;

    /**
     * 所有部门
     *
     */
    List<DepartmentVO> findAllVO();

    /**
     * 全部部门
     *
     */
    List<Department> findAll();

}
