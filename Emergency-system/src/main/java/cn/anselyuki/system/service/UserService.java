package cn.anselyuki.system.service;

import cn.anselyuki.common.error.SystemException;
import cn.anselyuki.common.model.system.Menu;
import cn.anselyuki.common.model.system.Role;
import cn.anselyuki.common.model.system.User;
import cn.anselyuki.common.vo.system.*;

import java.util.List;

/**
 * @author AnselYuki
 * @date 2022/9/7 15:43
 **/
public interface UserService {

    /**
     * 根据用户名查询用户
     *
     * @param name 用户名
     */
    User findUserByName(String name);

    /**
     * 查询用户角色
     *
     * @param id 用户id
     */
    List<Role> findRolesById(Long id) throws SystemException;

    /**
     * 根据用户角色查询用户的菜单
     * 菜单: menu+button
     *
     * @param roles 用户的角色
     */
    List<Menu> findMenuByRoles(List<Role> roles);

    /**
     * 加载菜单
     *
     */
    List<MenuNodeVO> findMenu();

    /**
     * 用户列表
     *
     */
    PageVO<UserVO> findUserList(Integer pageNum, Integer pageSize, UserVO userVO);

    /**
     * 删除用户
     *
     */
    void deleteById(Long id) throws SystemException;

    /**
     * 更新状态
     *
     */
    void updateStatus(Long id, Boolean status) throws SystemException;

    /**
     * 添加用户
     *
     */
    void add(UserVO userVO) throws SystemException;

    /**
     * 更新用户
     *
     */
    void update(Long id, UserEditVO userVO) throws SystemException;

    /**
     * 编辑用户
     *
     */
    UserEditVO edit(Long id) throws SystemException;

    /**
     * 已拥有的角色ids
     *
     * @param id 用户id
     */
    List<Long> roles(Long id) throws SystemException;

    /**
     * 分配角色
     *
     */
    void assignRoles(Long id, Long[] rids) throws SystemException;

    /**
     * 所有用户
     *
     */
    List<User> findAll();

    /**
     * 用户登入
     *
     */
    String login(String username, String password) throws SystemException;

    /**
     * 用户详情
     *
     */
    UserInfoVO info() throws SystemException;

}
