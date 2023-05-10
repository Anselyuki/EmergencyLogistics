package cn.anselyuki.system.converter;

import cn.anselyuki.common.model.system.Menu;
import cn.anselyuki.common.vo.system.MenuNodeVO;
import cn.anselyuki.common.vo.system.MenuVO;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AnselYuki
 * @date 2022/9/7 17:22
 **/
public class MenuConverter {

    /**
     * 转成menuVO(只包含菜单)List
     *
     */
    public static List<MenuNodeVO> converterToMenuNodeVO(List<Menu> menus) {
        // 先过滤出用户的菜单
        List<MenuNodeVO> menuNodeVOList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(menus)) {
            for (Menu menu : menus) {
                if (menu.getType() == 0) {
                    MenuNodeVO menuNodeVO = new MenuNodeVO();
                    BeanUtils.copyProperties(menu, menuNodeVO);
                    menuNodeVO.setDisabled(menu.getAvailable() == 0);
                    menuNodeVOList.add(menuNodeVO);
                }
            }
        }
        return menuNodeVOList;
    }

    /**
     * 转成menuVO(菜单和按钮）
     *
     */
    public static List<MenuNodeVO> converterToAllMenuNodeVO(List<Menu> menus) {
        // 先过滤出用户的菜单
        List<MenuNodeVO> menuNodeVOList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(menus)) {
            for (Menu menu : menus) {
                MenuNodeVO menuNodeVO = new MenuNodeVO();
                BeanUtils.copyProperties(menu, menuNodeVO);
                menuNodeVO.setDisabled(menu.getAvailable() == 0);
                menuNodeVOList.add(menuNodeVO);
            }
        }
        return menuNodeVOList;
    }

    /**
     * 转成menuVO(菜单和按钮）
     *
     */
    public static MenuVO converterToMenuVO(Menu menu) {
        MenuVO menuVO = new MenuVO();
        if (menu != null) {
            BeanUtils.copyProperties(menu, menuVO);
            menuVO.setDisabled(menu.getAvailable() == 0);
        }
        return menuVO;
    }

}
