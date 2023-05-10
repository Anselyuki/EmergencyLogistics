package cn.anselyuki.common.utils;


import cn.anselyuki.common.vo.system.MenuNodeVO;

import java.util.ArrayList;
import java.util.List;

/**
 * 该类用于递归构建树形菜单
 * @author AnselYuki
 */
public class MenuTreeBuilder {
    /**
     * 构建多级菜单树
     *
     */
    public static List<MenuNodeVO> build(List<MenuNodeVO> nodes) {
        //根节点
        List<MenuNodeVO> rootMenu = new ArrayList<>();
        for (MenuNodeVO nav : nodes) {
            if (nav.getParentId() == 0) {
                rootMenu.add(nav);
            }
        }
        /* 根据Menu类的order排序 */
        rootMenu.sort(MenuNodeVO.order());
        /*为根菜单设置子菜单，getChild是递归调用的*/
        for (MenuNodeVO nav : rootMenu) {
            /* 获取根节点下的所有子节点 使用getChild方法*/
            List<MenuNodeVO> childList = getChild(nav.getId(), nodes);
            //给根节点设置子节点
            nav.setChildren(childList);
        }
        return rootMenu;
    }

    /**
     * 获取子菜单
     *
     */
    private static List<MenuNodeVO> getChild(Long id, List<MenuNodeVO> nodes) {
        //子菜单
        List<MenuNodeVO> childList = new ArrayList<>();
        for (MenuNodeVO nav : nodes) {
            // 遍历所有节点，将所有菜单的父id与传过来的根节点的id比较
            //相等说明：为该根节点的子节点。
            if (nav.getParentId().equals(id)) {
                childList.add(nav);
            }
        }
        //递归
        for (MenuNodeVO nav : childList) {
            nav.setChildren(getChild(nav.getId(), nodes));
        }
        //排序
        childList.sort(MenuNodeVO.order());
        //如果节点下没有子节点，返回一个空List（递归退出）
        if (childList.size() == 0) {
            return new ArrayList<>();
        }
        return childList;
    }
}
