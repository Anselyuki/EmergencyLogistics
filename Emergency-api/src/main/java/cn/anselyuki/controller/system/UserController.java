package cn.anselyuki.controller.system;

import cn.anselyuki.common.annotation.ControllerEndpoint;
import cn.anselyuki.common.dto.UserLoginDTO;
import cn.anselyuki.common.error.SystemException;
import cn.anselyuki.common.model.system.Role;
import cn.anselyuki.common.model.system.User;
import cn.anselyuki.common.response.ResponseBean;
import cn.anselyuki.common.vo.system.*;
import cn.anselyuki.system.converter.RoleConverter;
import cn.anselyuki.system.service.LoginLogService;
import cn.anselyuki.system.service.RoleService;
import cn.anselyuki.system.service.UserService;
import com.wuwenze.poi.ExcelKit;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author AnselYuki
 * @date 2022/9/7 16:24
 **/

@RestController
@RequestMapping("/system/user")
@Validated
@Tag(name = "系统模块-用户相关接口")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    private final LoginLogService loginLogService;

    @Autowired
    public UserController(UserService userService, RoleService roleService, LoginLogService loginLogService) {
        this.userService = userService;
        this.roleService = roleService;
        this.loginLogService = loginLogService;
    }

    /**
     * 用户登入
     *
     * @return ResponseBean
     */
    @Operation(summary = "用户登入", description = "接收参数用户名和密码,登入成功后,返回JWTToken")
    @PostMapping("/login")
    public ResponseBean<String> login(@RequestBody UserLoginDTO userLoginDTO, HttpServletRequest request)
            throws SystemException {
        String token = userService.login(userLoginDTO.getUsername(), userLoginDTO.getPassword());
        loginLogService.add(request);
        return ResponseBean.success(token);
    }

    /**
     * 用户列表
     *
     * @return ResponseBean
     */
    @Operation(summary = "用户列表", description = "模糊查询用户列表")
    @GetMapping("/findUserList")
    public ResponseBean<PageVO<UserVO>> findUserList(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "7") Integer pageSize,
            UserVO userVO) {
        PageVO<UserVO> userList = userService.findUserList(pageNum, pageSize, userVO);
        return ResponseBean.success(userList);
    }

    /**
     * 用户信息
     *
     * @return ResponseBean
     */
    @Operation(summary = "用户信息", description = "用户登入信息")
    @GetMapping("/info")
    public ResponseBean<UserInfoVO> info() throws SystemException {
        UserInfoVO userInfoVO = userService.info();
        return ResponseBean.success(userInfoVO);
    }

    /**
     * 加载菜单
     *
     * @return ResponseBean
     */
    @Operation(summary = "加载菜单", description = "用户登入后,根据角色加载菜单树")
    @GetMapping("/findMenu")
    public ResponseBean<List<MenuNodeVO>> findMenu() {
        List<MenuNodeVO> menuTreeVoList = userService.findMenu();
        return ResponseBean.success(menuTreeVoList);
    }

    /**
     * 分配角色
     *
     * @param id   角色id
     * @param rids 角色ids
     * @return ResponseBean
     */
    @ControllerEndpoint(exceptionMessage = "分配角色失败", operation = "分配角色")
    @Operation(summary = "分配角色", description = "角色分配给用户")
    @RequiresPermissions({"user:assign"})
    @PostMapping("/{id}/assignRoles")
    public ResponseBean<Object> assignRoles(@PathVariable Long id, @RequestBody Long[] rids) throws SystemException {
        userService.assignRoles(id, rids);
        return ResponseBean.success();
    }

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @return ResponseBean
     */
    @ControllerEndpoint(exceptionMessage = "删除用户失败", operation = "删除用户")
    @RequiresPermissions({"user:delete"})
    @Operation(summary = "删除用户", description = "删除用户信息，根据用户ID")
    @DeleteMapping("/delete/{id}")
    public ResponseBean<Object> delete(@PathVariable Long id) throws SystemException {
        userService.deleteById(id);
        return ResponseBean.success();
    }

    /**
     * 更新状态
     *
     * @param id     用户id
     * @param status 需要更改的用户状态
     * @return ResponseBean
     */
    @ControllerEndpoint(exceptionMessage = "更新用户状态失败", operation = "用户|禁用/启用")
    @Operation(summary = "用户状态", description = "禁用和启用这两种状态")
    @RequiresPermissions({"user:status"})
    @PutMapping("/updateStatus/{id}/{status}")
    public ResponseBean<Object> updateStatus(@PathVariable Long id, @PathVariable Boolean status) throws SystemException {
        userService.updateStatus(id, status);
        return ResponseBean.success();
    }

    /**
     * 更新用户
     *
     * @param id         用户id
     * @param userEditVO 用户更新vo
     * @return ResponseBean
     */
    @ControllerEndpoint(exceptionMessage = "更新用户失败", operation = "更新用户")
    @Operation(summary = "更新用户", description = "更新用户信息")
    @RequiresPermissions({"user:update"})
    @PutMapping("/update/{id}")
    public ResponseBean<Object> update(@PathVariable Long id, @RequestBody @Validated UserEditVO userEditVO)
            throws SystemException {
        userService.update(id, userEditVO);
        return ResponseBean.success(userEditVO);
    }

    /**
     * 编辑用户
     *
     * @param id 用户id
     * @return ResponseBean
     */
    @Operation(summary = "编辑用户", description = "获取用户的详情，编辑用户信息")
    @RequiresPermissions({"user:edit"})
    @GetMapping("/edit/{id}")
    public ResponseBean<UserEditVO> edit(@PathVariable Long id) throws SystemException {
        UserEditVO userVO = userService.edit(id);
        return ResponseBean.success(userVO);
    }

    /**
     * 添加用户信息
     *
     * @param userVO 用户vo
     * @return ResponseBean
     */
    @ControllerEndpoint(exceptionMessage = "添加用户失败", operation = "添加用户")
    @Operation(summary = "添加用户", description = "添加用户信息")
    @RequiresPermissions({"user:add"})
    @PostMapping("/add")
    public ResponseBean<Object> add(@RequestBody @Validated UserVO userVO) throws SystemException {
        userService.add(userVO);
        return ResponseBean.success(userVO);
    }

    /**
     * 用户角色信息
     *
     * @param id 用户id
     * @return ResponseBean
     */
    @Operation(summary = "已有角色", description = "根据用户id，获取用户已经拥有的角色")
    @GetMapping("/{id}/roles")
    public ResponseBean<Map<String, Object>> roles(@PathVariable Long id) throws SystemException {
        List<Long> values = userService.roles(id);
        List<Role> list = roleService.findAll();
        // 转成前端需要的角色Item
        List<RoleTransferItemVO> items = RoleConverter.converterToRoleTransferItem(list);
        Map<String, Object> map = new HashMap<>(2);
        map.put("roles", items);
        map.put("values", values);
        return ResponseBean.success(map);
    }

    /**
     * 导出excel
     *
     * @param response 返回文件下载请求
     */
    @Operation(summary = "导出excel", description = "导出所有用户的excel表格")
    @PostMapping("/excel")
    @RequiresPermissions("user:export")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败", operation = "导出用户excel")
    public void export(HttpServletResponse response) {
        List<User> users = this.userService.findAll();
        ExcelKit.$Export(User.class, response).downXlsx(users, false);
    }
}
