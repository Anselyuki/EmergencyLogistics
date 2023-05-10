package cn.anselyuki.controller.business;

import cn.anselyuki.business.service.ConsumerService;
import cn.anselyuki.business.service.OutStockService;
import cn.anselyuki.common.annotation.ControllerEndpoint;
import cn.anselyuki.common.error.BusinessCodeEnum;
import cn.anselyuki.common.error.BusinessException;
import cn.anselyuki.common.model.business.Consumer;
import cn.anselyuki.common.response.ResponseBean;
import cn.anselyuki.common.vo.business.ConsumerVO;
import cn.anselyuki.common.vo.business.OutStockDetailVO;
import cn.anselyuki.common.vo.business.OutStockVO;
import cn.anselyuki.common.vo.system.PageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 出库单
 *
 * @author AnselYuki
 * @date 2022/5/10 14:23
 **/
@Tag(name = "业务模块-物资出库相关接口")
@RestController
@RequestMapping("/business/outStock")
public class OutStockController {
    private final OutStockService outStockService;
    private final ConsumerService consumerService;

    @Autowired
    public OutStockController(OutStockService outStockService, ConsumerService consumerService) {
        this.outStockService = outStockService;
        this.consumerService = consumerService;
    }

    /**
     * 提交物资发放单
     *
     */
    @ControllerEndpoint(exceptionMessage = "发放单申请失败", operation = "发放单申请")
    @Operation(summary = "提交发放单")
    @PostMapping("/addOutStock")
    @RequiresPermissions({"outStock:out"})
    public ResponseBean<Object> addOutStock(@RequestBody @Validated OutStockVO outStockVO) throws BusinessException {
        if (outStockVO.getConsumerId() == null) {
            // 说明现在添加物资来源
            ConsumerVO consumerVO = new ConsumerVO();
            BeanUtils.copyProperties(outStockVO, consumerVO);
            if ("".equals(consumerVO.getName()) || consumerVO.getName() == null) {
                throw new BusinessException(BusinessCodeEnum.PARAMETER_ERROR, "物资去向名不能为空");
            }
            if ("".equals(consumerVO.getContact()) || consumerVO.getContact() == null) {
                throw new BusinessException(BusinessCodeEnum.PARAMETER_ERROR, "联系人不能为空");
            }
            if ("".equals(consumerVO.getAddress()) || consumerVO.getAddress() == null) {
                throw new BusinessException(BusinessCodeEnum.PARAMETER_ERROR, "地址不能为空");
            }
            if ("".equals(consumerVO.getPhone()) || consumerVO.getPhone() == null) {
                throw new BusinessException(BusinessCodeEnum.PARAMETER_ERROR, "联系方式不能为空");
            }
            if (consumerVO.getSort() == null) {
                throw new BusinessException(BusinessCodeEnum.PARAMETER_ERROR, "排序不能为空");
            }
            Consumer consumer = consumerService.add(consumerVO);
            outStockVO.setConsumerId(consumer.getId());
        }
        // 提交发放单
        outStockService.addOutStock(outStockVO);
        return ResponseBean.success();
    }

    /**
     * 发放单列表
     *
     */
    @Operation(summary = "出库单列表")
    @GetMapping("/findOutStockList")
    public ResponseBean<PageVO<OutStockVO>> findInStockList(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize,
            OutStockVO outStockVO) {
        PageVO<OutStockVO> outStockList = outStockService.findOutStockList(pageNum, pageSize, outStockVO);
        return ResponseBean.success(outStockList);
    }

    /**
     * 移入回收站
     *
     */
    @ControllerEndpoint(exceptionMessage = "发放单回收失败", operation = "发放单回收")
    @Operation(summary = "移入回收站", description = "移入回收站")
    @RequiresPermissions({"outStock:remove"})
    @PutMapping("/remove/{id}")
    public ResponseBean<Object> remove(@PathVariable Long id) throws BusinessException {
        outStockService.remove(id);
        return ResponseBean.success();
    }

    /**
     * 物资发放单详细
     *
     */
    @RequiresPermissions({"outStock:detail"})
    @Operation(summary = "发放单明细")
    @GetMapping("/detail/{id}")
    public ResponseBean<OutStockDetailVO> detail(@PathVariable Long id,
                                                 @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                 @RequestParam(value = "pageSize", defaultValue = "3") Integer pageSize) throws BusinessException {
        OutStockDetailVO detail = outStockService.detail(id, pageNum, pageSize);
        return ResponseBean.success(detail);
    }

    /**
     * 删除物资发放单
     *
     */
    @ControllerEndpoint(exceptionMessage = "发放单删除失败", operation = "发放单删除")
    @RequiresPermissions({"outStock:delete"})
    @Operation(summary = "删除物资发放单")
    @GetMapping("/delete/{id}")
    public ResponseBean<Object> delete(@PathVariable Long id) throws BusinessException {
        outStockService.delete(id);
        return ResponseBean.success();
    }

    /**
     * 发放审核
     *
     */
    @ControllerEndpoint(exceptionMessage = "发放单审核失败", operation = "发放单审核")
    @Operation(summary = "入库审核")
    @PutMapping("/publish/{id}")
    @RequiresPermissions({"outStock:publish"})
    public ResponseBean<Object> publish(@PathVariable Long id) throws BusinessException {
        outStockService.publish(id);
        return ResponseBean.success();
    }

    /**
     * 恢复数据从回收站
     *
     */
    @ControllerEndpoint(exceptionMessage = "发放单恢复失败", operation = "发放单恢复")
    @Operation(summary = "恢复数据", description = "从回收站中恢复入库单")
    @RequiresPermissions({"outStock:back"})
    @PutMapping("/back/{id}")
    public ResponseBean<Object> back(@PathVariable Long id) throws BusinessException {
        outStockService.back(id);
        return ResponseBean.success();
    }
}
