package cn.anselyuki.system.service;

import cn.anselyuki.common.error.SystemException;
import cn.anselyuki.common.model.system.Menu;
import cn.anselyuki.common.vo.system.MenuNodeVO;
import cn.anselyuki.common.vo.system.MenuVO;

import java.util.List;

/**
 * @author AnselYuki
 * @date 2022/9/10 11:55
 **/
public interface MenuService {
    /**
     * 获取菜单树
     *
     */
    List<MenuNodeVO> findMenuTree();

    /**
     * 添加菜单
     *
     */
    Menu add(MenuVO menuVO);

    /**
     * 删除节点
     *
     */
    void delete(Long id) throws SystemException;

    /**
     * 编辑节点
     *
     */
    MenuVO edit(Long id) throws SystemException;

    /**
     * 更新节点
     *
     */
    void update(Long id, MenuVO menuVO) throws SystemException;

    /**
     * 所有展开菜单的ID
     *
     */
    List<Long> findOpenIds();

    /**
     * 获取所有菜单
     *
     */
    List<Menu> findAll();

}
