package cn.anselyuki.controller.system;

import cn.anselyuki.common.annotation.ControllerEndpoint;
import cn.anselyuki.common.error.SystemException;
import cn.anselyuki.common.model.system.Menu;
import cn.anselyuki.common.response.ResponseBean;
import cn.anselyuki.common.vo.system.MenuNodeVO;
import cn.anselyuki.common.vo.system.MenuVO;
import cn.anselyuki.system.service.MenuService;
import com.wuwenze.poi.ExcelKit;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author AnselYuki
 * @date 2022/9/10 11:51
 **/
@Tag(name = "系统模块-菜单权限相关接口")
@RequestMapping("/system/menu")
@RestController
public class MenuController {
    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 加载菜单树
     *
     */
    @Operation(summary = "加载菜单树", description = "获取所有菜单树，以及展开项")
    @GetMapping("/tree")
    public ResponseBean<Map<String, Object>> tree() {
        List<MenuNodeVO> menuTree = menuService.findMenuTree();
        List<Long> ids = menuService.findOpenIds();
        Map<String, Object> map = new HashMap<>(2);
        map.put("tree", menuTree);
        map.put("open", ids);
        return ResponseBean.success(map);
    }

    /**
     * 新增菜单/按钮
     *
     */
    @ControllerEndpoint(exceptionMessage = "新增菜单/按钮失败", operation = "新增菜单/按钮")
    @Operation(summary = "新增菜单")
    @RequiresPermissions({"menu:add"})
    @PostMapping("/add")
    public ResponseBean<Map<String, Object>> add(@RequestBody @Validated MenuVO menuVO) {
        Menu node = menuService.add(menuVO);
        Map<String, Object> map = new HashMap<>(4);
        map.put("id", node.getId());
        map.put("menuName", node.getMenuName());
        map.put("children", new ArrayList<>());
        map.put("icon", node.getIcon());
        return ResponseBean.success(map);
    }

    /**
     * 删除菜单/按钮
     *
     */
    @ControllerEndpoint(exceptionMessage = "删除菜单/按钮失败", operation = "删除菜单/按钮")
    @Operation(summary = "删除菜单", description = "根据id删除菜单节点")
    @RequiresPermissions({"menu:delete"})
    @DeleteMapping("/delete/{id}")
    public ResponseBean<?> delete(@PathVariable Long id) throws SystemException {
        menuService.delete(id);
        return ResponseBean.success();
    }

    /**
     * 菜单详情
     *
     */
    @Operation(summary = "菜单详情", description = "根据id编辑菜单，获取菜单详情")
    @RequiresPermissions({"menu:edit"})
    @GetMapping("/edit/{id}")
    public ResponseBean<MenuVO> edit(@PathVariable Long id) throws SystemException {
        MenuVO menuVO = menuService.edit(id);
        return ResponseBean.success(menuVO);
    }

    /**
     * 更新菜单
     *
     */
    @ControllerEndpoint(exceptionMessage = "更新菜单失败", operation = "更新菜单")
    @Operation(summary = "更新菜单", description = "根据id更新菜单节点")
    @RequiresPermissions({"menu:update"})
    @PutMapping("/update/{id}")
    public ResponseBean<?> update(@PathVariable Long id, @RequestBody @Validated MenuVO menuVO) throws SystemException {
        menuService.update(id, menuVO);
        return ResponseBean.success();
    }

    /**
     * 导出excel
     *
     */
    @Operation(summary = "导出excel", description = "导出所有菜单的excel表格")
    @PostMapping("excel")
    @RequiresPermissions("menu:export")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败", operation = "导出菜单excel")
    public void export(HttpServletResponse response) {
        List<Menu> menus = this.menuService.findAll();
        ExcelKit.$Export(Menu.class, response).downXlsx(menus, false);
    }

}
