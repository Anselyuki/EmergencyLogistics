package cn.anselyuki.controller.business;

import cn.anselyuki.business.service.InStockService;
import cn.anselyuki.business.service.SupplierService;
import cn.anselyuki.common.annotation.ControllerEndpoint;
import cn.anselyuki.common.error.BusinessCodeEnum;
import cn.anselyuki.common.error.BusinessException;
import cn.anselyuki.common.model.business.Supplier;
import cn.anselyuki.common.response.ResponseBean;
import cn.anselyuki.common.vo.business.InStockDetailVO;
import cn.anselyuki.common.vo.business.InStockVO;
import cn.anselyuki.common.vo.business.SupplierVO;
import cn.anselyuki.common.vo.system.PageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author AnselYuki
 * @date 2022/9/19 09:53
 **/
@Tag(name = "业务模块-物资入库相关接口")
@RestController
@RequestMapping("/business/inStock")
public class InStockController {
    private final InStockService inStockService;
    private final SupplierService supplierService;

    @Autowired
    public InStockController(InStockService inStockService, SupplierService supplierService) {
        this.inStockService = inStockService;
        this.supplierService = supplierService;
    }

    /**
     * 入库单列表
     *
     */
    @Operation(summary = "入库单列表")
    @GetMapping("/findInStockList")
    public ResponseBean<Object> findInStockList(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize,
            InStockVO inStockVO) {
        PageVO<InStockVO> inStockList = inStockService.findInStockList(pageNum, pageSize, inStockVO);
        return ResponseBean.success(inStockList);
    }

    /**
     * 物资入库
     *
     */
    @ControllerEndpoint(exceptionMessage = "入库单申请失败", operation = "入库单申请")
    @Operation(summary = "物资入库")
    @PostMapping("/addIntoStock")
    @RequiresPermissions({"inStock:in"})
    public ResponseBean<Object> addIntoStock(@RequestBody @Validated InStockVO inStockVO) throws BusinessException {
        if (inStockVO.getSupplierId() == null) {
            // 说明现在添加物资来源
            SupplierVO supplierVO = new SupplierVO();
            BeanUtils.copyProperties(inStockVO, supplierVO);
            if ("".equals(supplierVO.getName()) || supplierVO.getName() == null) {
                throw new BusinessException(BusinessCodeEnum.PARAMETER_ERROR, "物资提供方名不能为空");
            }
            if ("".equals(supplierVO.getEmail()) || supplierVO.getEmail() == null) {
                throw new BusinessException(BusinessCodeEnum.PARAMETER_ERROR, "邮箱不能为空");
            }
            if ("".equals(supplierVO.getContact()) || supplierVO.getContact() == null) {
                throw new BusinessException(BusinessCodeEnum.PARAMETER_ERROR, "联系人不能为空");
            }
            if ("".equals(supplierVO.getAddress()) || supplierVO.getAddress() == null) {
                throw new BusinessException(BusinessCodeEnum.PARAMETER_ERROR, "地址不能为空");
            }
            if ("".equals(supplierVO.getPhone()) || supplierVO.getPhone() == null) {
                throw new BusinessException(BusinessCodeEnum.PARAMETER_ERROR, "联系方式不能为空");
            }
            if (supplierVO.getSort() == null) {
                throw new BusinessException(BusinessCodeEnum.PARAMETER_ERROR, "排序不能为空");
            }
            Supplier supplier = supplierService.add(supplierVO);
            inStockVO.setSupplierId(supplier.getId());
        }
        inStockService.addIntoStock(inStockVO);
        return ResponseBean.success();
    }

    /**
     * 入库审核
     *
     */
    @ControllerEndpoint(exceptionMessage = "入库单审核失败", operation = "入库单审核")
    @Operation(summary = "入库审核")
    @PutMapping("/publish/{id}")
    @RequiresPermissions({"inStock:publish"})
    public ResponseBean<Object> publish(@PathVariable Long id) throws BusinessException {
        inStockService.publish(id);
        return ResponseBean.success();
    }

    /**
     * 物资入库单详细
     *
     */
    @RequiresPermissions({"inStock:detail"})
    @Operation(summary = "入库单明细")
    @GetMapping("/detail/{id}")
    public ResponseBean<Object> detail(@PathVariable Long id,
                                       @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                       @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize) throws BusinessException {
        InStockDetailVO detail = inStockService.detail(id, pageNum, pageSize);
        return ResponseBean.success(detail);
    }

    /**
     * 删除物资入库单
     *
     */
    @ControllerEndpoint(exceptionMessage = "入库单删除失败", operation = "入库单删除")
    @RequiresPermissions({"inStock:delete"})
    @Operation(summary = "删除物资入库单")
    @GetMapping("/delete/{id}")
    public ResponseBean<Object> delete(@PathVariable Long id) throws BusinessException {
        inStockService.delete(id);
        return ResponseBean.success();
    }

    /**
     * 移入回收站
     *
     */
    @ControllerEndpoint(exceptionMessage = "入库单回收失败", operation = "入库单回收")
    @Operation(summary = "移入回收站", description = "移入回收站")
    @RequiresPermissions({"inStock:remove"})
    @PutMapping("/remove/{id}")
    public ResponseBean<Object> remove(@PathVariable Long id) throws BusinessException {
        inStockService.remove(id);
        return ResponseBean.success();
    }

    /**
     * 恢复数据从回收站
     *
     */
    @ControllerEndpoint(exceptionMessage = "入库单恢复失败", operation = "入库单恢复")
    @Operation(summary = "恢复数据", description = "从回收站中恢复入库单")
    @RequiresPermissions({"inStock:back"})
    @PutMapping("/back/{id}")
    public ResponseBean<Object> back(@PathVariable Long id) throws BusinessException {
        inStockService.back(id);
        return ResponseBean.success();
    }
}
