package cn.anselyuki.controller.business;

import cn.anselyuki.business.service.SupplierService;
import cn.anselyuki.common.annotation.ControllerEndpoint;
import cn.anselyuki.common.response.ResponseBean;
import cn.anselyuki.common.vo.business.SupplierVO;
import cn.anselyuki.common.vo.system.PageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 来源管理
 *
 * @author AnselYuki
 * @date 2022/9/16 20:18
 **/
@Tag(name = "业务模块-物资来源相关接口")
@RestController
@RequestMapping("/business/supplier")
public class SupplierController {
    private final SupplierService supplierService;

    @Autowired
    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    /**
     * 来源列表
     *
     */
    @Operation(summary = "来源列表", description = "来源列表,根据来源名模糊查询")
    @GetMapping("/findSupplierList")
    public ResponseBean findSupplierList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize") Integer pageSize,
                                         SupplierVO supplierVO) {
        PageVO<SupplierVO> supplierVoAndPageVo = supplierService.findSupplierList(pageNum, pageSize, supplierVO);
        return ResponseBean.success(supplierVoAndPageVo);
    }

    /**
     * 添加来源
     *
     */
    @ControllerEndpoint(exceptionMessage = "物资来源添加失败", operation = "物资来源添加")
    @RequiresPermissions({"supplier:add"})
    @Operation(summary = "添加来源")
    @PostMapping("/add")
    public ResponseBean add(@RequestBody @Validated SupplierVO supplierVO) {
        supplierService.add(supplierVO);
        return ResponseBean.success();
    }

    /**
     * 编辑来源
     *
     */
    @Operation(summary = "编辑来源", description = "编辑来源信息")
    @RequiresPermissions({"supplier:edit"})
    @GetMapping("/edit/{id}")
    public ResponseBean<SupplierVO> edit(@PathVariable Long id) {
        SupplierVO supplierVO = supplierService.edit(id);
        return ResponseBean.success(supplierVO);
    }

    /**
     * 更新来源
     *
     */
    @ControllerEndpoint(exceptionMessage = "物资来源更新失败", operation = "物资来源更新")
    @Operation(summary = "更新来源", description = "更新来源信息")
    @RequiresPermissions({"supplier:update"})
    @PutMapping("/update/{id}")
    public ResponseBean update(@PathVariable Long id, @RequestBody @Validated SupplierVO supplierVO) {
        supplierService.update(id, supplierVO);
        return ResponseBean.success();
    }

    /**
     * 删除来源
     *
     */
    @ControllerEndpoint(exceptionMessage = "物资来源删除失败", operation = "物资来源删除")
    @Operation(summary = "删除来源", description = "删除来源信息")
    @RequiresPermissions({"supplier:delete"})
    @DeleteMapping("/delete/{id}")
    public ResponseBean delete(@PathVariable Long id) {
        supplierService.delete(id);
        return ResponseBean.success();
    }

    /**
     * 所有来源
     *
     */
    @Operation(summary = "所有来源", description = "所有来源列表")
    @GetMapping("/findAll")
    public ResponseBean<List<SupplierVO>> findAll() {
        List<SupplierVO> supplierVOList = supplierService.findAll();
        return ResponseBean.success(supplierVOList);
    }
}
