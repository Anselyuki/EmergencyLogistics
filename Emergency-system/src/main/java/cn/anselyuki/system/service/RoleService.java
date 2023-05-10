package cn.anselyuki.system.service;

import cn.anselyuki.common.error.SystemException;
import cn.anselyuki.common.model.system.Role;
import cn.anselyuki.common.vo.system.PageVO;
import cn.anselyuki.common.vo.system.RoleVO;

import java.util.List;

/**
 * @author AnselYuki
 * @date 2022/9/7 15:52
 **/
public interface RoleService {

    /**
     * 角色列表
     *
     */
    PageVO<RoleVO> findRoleList(Integer pageNum, Integer pageSize, RoleVO roleVO);

    /**
     * 添加角色
     *
     */
    void add(RoleVO roleVO) throws SystemException;

    /**
     * 删除角色
     *
     */
    void deleteById(Long id) throws SystemException;

    /**
     * 编辑角色
     *
     */
    RoleVO edit(Long id) throws SystemException;

    /**
     * 更新角色
     *
     */
    void update(Long id, RoleVO roleVO) throws SystemException;

    /**
     * 根据角色状态
     *
     */
    void updateStatus(Long id, Boolean status) throws SystemException;

    /**
     * 查询所有的角色
     *
     */
    List<Role> findAll();

    /**
     * 查询角色拥有的菜单权限id
     *
     */
    List<Long> findMenuIdsByRoleId(Long id) throws SystemException;

    /**
     * 角色授权
     *
     */
    void authority(Long id, Long[] mids) throws SystemException;
}
