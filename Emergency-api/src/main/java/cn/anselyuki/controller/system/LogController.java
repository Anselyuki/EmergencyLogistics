package cn.anselyuki.controller.system;

import cn.anselyuki.common.error.SystemException;
import cn.anselyuki.common.response.ResponseBean;
import cn.anselyuki.common.vo.system.LogVO;
import cn.anselyuki.common.vo.system.PageVO;
import cn.anselyuki.system.service.LogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统日志
 *
 * @author AnselYuki
 * @date 2022/9/22 21:03
 **/
@Tag(name = "系统模块-操作日志相关接口")
@RestController
@RequestMapping("/system/log")
public class LogController {

    private final LogService logService;

    @Autowired
    public LogController(LogService logService) {
        this.logService = logService;
    }

    /**
     * 日志列表
     *
     */
    @Operation(summary = "日志列表", description = "系统日志列表，模糊查询")
    @GetMapping("/findLogList")
    public ResponseBean<PageVO<LogVO>> findLogList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                   @RequestParam(value = "pageSize") Integer pageSize,
                                                   LogVO logVO) {
        PageVO<LogVO> logList = logService.findLogList(pageNum, pageSize, logVO);
        return ResponseBean.success(logList);
    }

    /**
     * 删除日志
     *
     */
    @Operation(summary = "删除日志")
    @RequiresPermissions({"log:delete"})
    @DeleteMapping("/delete/{id}")
    public ResponseBean<String> delete(@PathVariable Long id) throws SystemException {
        logService.delete(id);
        return ResponseBean.success("删除系统日志成功");
    }

    /**
     * 批量删除
     *
     */
    @Operation(summary = "批量删除")
    @RequiresPermissions({"log:batchDelete"})
    @DeleteMapping("/batchDelete/{ids}")
    public ResponseBean<String> batchDelete(@PathVariable String ids) throws SystemException {
        String[] idList = ids.split(",");
        List<Long> list = new ArrayList<>();
        if (idList.length > 0) {
            for (String s : idList) {
                list.add(Long.parseLong(s));
            }
        }
        logService.batchDelete(list);
        return ResponseBean.success("批量删除成功");
    }

}
