package cn.anselyuki.controller.system;

import cn.anselyuki.common.annotation.ControllerEndpoint;
import cn.anselyuki.common.error.SystemException;
import cn.anselyuki.common.model.system.Department;
import cn.anselyuki.common.response.ResponseBean;
import cn.anselyuki.common.vo.system.DeanVO;
import cn.anselyuki.common.vo.system.DepartmentVO;
import cn.anselyuki.common.vo.system.PageVO;
import cn.anselyuki.system.service.DepartmentService;
import com.wuwenze.poi.ExcelKit;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 部门管理
 *
 * @author AnselYuki
 * @date 2022/9/15 14:11
 **/
@Tag(name = "系统模块-部门相关接口")
@RestController
@RequestMapping("/system/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    @Autowired
    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    /**
     * 部门列表
     *
     */
    @Operation(summary = "部门列表", description = "部门列表,根据部门名模糊查询")
    @GetMapping("/findDepartmentList")
    public ResponseBean<PageVO<DepartmentVO>> findDepartmentList(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize,
            DepartmentVO departmentVO) {
        PageVO<DepartmentVO> departmentsList = departmentService.findDepartmentList(pageNum, pageSize, departmentVO);
        return ResponseBean.success(departmentsList);
    }

    /**
     * 所有部门
     *
     */
    @Operation(summary = "所有部门")
    @GetMapping("/findAll")
    public ResponseBean<List<DepartmentVO>> findAll() {
        List<DepartmentVO> departmentVOList = departmentService.findAllVO();
        return ResponseBean.success(departmentVOList);
    }

    /**
     * 查找部门主任
     *
     */
    @Operation(summary = "部门主任", description = "查找部门主任,排除掉已经禁用的用户")
    @GetMapping("/findDeanList")
    public ResponseBean<List<DeanVO>> findDeanList() {
        List<DeanVO> managerList = departmentService.findDeanList();
        return ResponseBean.success(managerList);
    }

    /**
     * 添加部门
     *
     */
    @ControllerEndpoint(exceptionMessage = "添加部门失败", operation = "添加部门")
    @RequiresPermissions({"department:add"})
    @Operation(summary = "添加部门")
    @PostMapping("/add")
    public ResponseBean<Object> add(@RequestBody @Validated DepartmentVO departmentVO) {
        departmentService.add(departmentVO);
        return ResponseBean.success();
    }

    /**
     * 编辑部门
     *
     */
    @Operation(summary = "编辑部门")
    @RequiresPermissions({"department:edit"})
    @GetMapping("/edit/{id}")
    public ResponseBean<Object> edit(@PathVariable Long id) throws SystemException {
        DepartmentVO departmentVO = departmentService.edit(id);
        return ResponseBean.success(departmentVO);
    }

    /**
     * 更新部门
     *
     */
    @ControllerEndpoint(exceptionMessage = "更新部门失败", operation = "更新部门")
    @Operation(summary = "更新部门")
    @RequiresPermissions({"department:update"})
    @PutMapping("/update/{id}")
    public ResponseBean<Object> update(@PathVariable Long id, @RequestBody @Validated DepartmentVO departmentVO)
            throws SystemException {
        departmentService.update(id, departmentVO);
        return ResponseBean.success();
    }

    /**
     * 删除部门
     *
     */
    @ControllerEndpoint(exceptionMessage = "删除部门失败", operation = "删除部门")
    @Operation(summary = "删除部门")
    @RequiresPermissions({"department:delete"})
    @DeleteMapping("/delete/{id}")
    public ResponseBean<Object> delete(@PathVariable Long id) throws SystemException {
        departmentService.delete(id);
        return ResponseBean.success();
    }

    /**
     * 导出excel
     *
     */
    @Operation(summary = "导出excel", description = "导出所有部门的excel表格")
    @PostMapping("/excel")
    @RequiresPermissions("department:export")
    @ControllerEndpoint(exceptionMessage = "导出Excel失败", operation = "导出部门excel")
    public void export(HttpServletResponse response) {
        List<Department> departments = this.departmentService.findAll();
        ExcelKit.$Export(Department.class, response).downXlsx(departments, false);
    }

}
