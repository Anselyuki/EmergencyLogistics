package cn.anselyuki.system.service.impl;

import cn.anselyuki.common.error.SystemCodeEnum;
import cn.anselyuki.common.error.SystemException;
import cn.anselyuki.common.model.system.Menu;
import cn.anselyuki.common.utils.MenuTreeBuilder;
import cn.anselyuki.common.vo.system.MenuNodeVO;
import cn.anselyuki.common.vo.system.MenuVO;
import cn.anselyuki.system.converter.MenuConverter;
import cn.anselyuki.system.mapper.MenuMapper;
import cn.anselyuki.system.service.MenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author AnselYuki
 * @date 2022/9/10 11:56
 **/
@Service
public class MenuServiceImpl implements MenuService {

    private final MenuMapper menuMapper;

    @Autowired
    public MenuServiceImpl(MenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    /**
     * 加载菜单树（按钮和菜单）
     *
     */
    @Override
    public List<MenuNodeVO> findMenuTree() {
        List<Menu> menus = menuMapper.selectAll();
        List<MenuNodeVO> menuNodeVOList = MenuConverter.converterToAllMenuNodeVO(menus);
        return MenuTreeBuilder.build(menuNodeVOList);
    }

    /**
     * 添加菜单
     *
     */
    @Override
    public Menu add(MenuVO menuVO) {
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuVO, menu);
        menu.setCreateTime(new Date());
        menu.setModifiedTime(new Date());
        menu.setAvailable(menuVO.getDisabled() ? 0 : 1);
        menuMapper.insert(menu);
        return menu;
    }

    /**
     * 删除菜单
     *
     */
    @Override
    public void delete(Long id) throws SystemException {
        Menu menu = menuMapper.selectByPrimaryKey(id);
        if (menu == null) {
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR, "要删除的菜单不存在");
        }
        menuMapper.deleteByPrimaryKey(id);
    }

    /**
     * 编辑菜单
     *
     */
    @Override
    public MenuVO edit(Long id) throws SystemException {
        Menu menu = menuMapper.selectByPrimaryKey(id);
        if (menu == null) {
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR, "该编辑的菜单不存在");
        }
        return MenuConverter.converterToMenuVO(menu);
    }

    /**
     * 更新菜单
     *
     */
    @Override
    public void update(Long id, MenuVO menuVO) throws SystemException {
        Menu dbMenu = menuMapper.selectByPrimaryKey(id);
        if (dbMenu == null) {
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR, "要更新的菜单不存在");
        }
        Menu menu = new Menu();
        BeanUtils.copyProperties(menuVO, menu);
        menu.setId(id);
        menu.setAvailable(menuVO.getDisabled() ? 0 : 1);
        menu.setModifiedTime(new Date());
        menuMapper.updateByPrimaryKeySelective(menu);
    }

    /**
     * 获取展开项
     *
     */
    @Override
    public List<Long> findOpenIds() {
        List<Long> ids = new ArrayList<>();
        List<Menu> menus = menuMapper.selectAll();
        if (!CollectionUtils.isEmpty(menus)) {
            for (Menu menu : menus) {
                if (menu.getOpen() == 1) {
                    ids.add(menu.getId());
                }
            }
        }
        return ids;
    }

    /**
     * 获取所有菜单
     *
     */
    @Override
    public List<Menu> findAll() {
        return menuMapper.selectAll();
    }

}
