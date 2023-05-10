package cn.anselyuki.controller.system;

import cn.anselyuki.common.annotation.ControllerEndpoint;
import cn.anselyuki.common.error.SystemException;
import cn.anselyuki.common.response.ResponseBean;
import cn.anselyuki.common.vo.system.LoginLogVO;
import cn.anselyuki.common.vo.system.PageVO;
import cn.anselyuki.common.vo.system.UserVO;
import cn.anselyuki.system.service.LoginLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登入日志
 *
 * @author AnselYuki
 * @date 2022/9/22 21:03
 **/
@Tag(name = "系统模块-登入日志相关接口")
@RestController
@RequestMapping("/system/loginLog")
public class LoginLogController {

    private final LoginLogService loginLogService;

    @Autowired
    public LoginLogController(LoginLogService loginLogService) {
        this.loginLogService = loginLogService;
    }

    /**
     * 日志列表
     *
     */
    @Operation(summary = "日志列表", description = "登入日志列表，模糊查询")
    @GetMapping("/findLoginLogList")
    public ResponseBean<PageVO<LoginLogVO>> findLoginLogList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestParam(value = "pageSize") Integer pageSize, LoginLogVO loginLogVO) {
        PageVO<LoginLogVO> loginLogList = loginLogService.findLoginLogList(pageNum, pageSize, loginLogVO);
        return ResponseBean.success(loginLogList);
    }

    /**
     * 删除日志
     *
     */
    @ControllerEndpoint(exceptionMessage = "删除登入日志失败", operation = "删除登入日志")
    @Operation(summary = "删除日志")
    @RequiresPermissions({"loginLog:delete"})
    @DeleteMapping("/delete/{id}")
    public ResponseBean<Object> delete(@PathVariable Long id) throws SystemException {
        loginLogService.delete(id);
        return ResponseBean.success();
    }

    /**
     * 批量删除
     *
     */
    @ControllerEndpoint(exceptionMessage = "批量删除登入日志失败", operation = "批量删除登入日志")
    @Operation(summary = "批量删除")
    @RequiresPermissions({"loginLog:batchDelete"})
    @DeleteMapping("/batchDelete/{ids}")
    public ResponseBean<Object> batchDelete(@PathVariable String ids) throws SystemException {
        String[] idList = ids.split(",");
        List<Long> list = new ArrayList<>();
        if (idList.length > 0) {
            for (String s : idList) {
                list.add(Long.parseLong(s));
            }
        }
        loginLogService.batchDelete(list);
        return ResponseBean.success();
    }

    /**
     * 登入报表
     *
     */
    @PostMapping("/loginReport")
    @Operation(summary = "登入报表", description = "用户登入报表")
    public ResponseBean<Map<String, Object>> loginReport(@RequestBody UserVO userVO) {
        List<Map<String, Object>> mapList = loginLogService.loginReport(userVO);
        Map<String, Object> map = new HashMap<>(2);
        userVO.setUsername(null);
        List<Map<String, Object>> meList = loginLogService.loginReport(userVO);
        map.put("me", mapList);
        map.put("all", meList);
        return ResponseBean.success(map);
    }

}
