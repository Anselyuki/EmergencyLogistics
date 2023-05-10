package cn.anselyuki.controller.business;

import cn.anselyuki.business.service.ConsumerService;
import cn.anselyuki.common.annotation.ControllerEndpoint;
import cn.anselyuki.common.response.ResponseBean;
import cn.anselyuki.common.vo.business.ConsumerVO;
import cn.anselyuki.common.vo.system.PageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 去向管理
 *
 * @author AnselYuki
 * @date 2022/9/16 20:18
 **/
@Tag(name = "业务模块-物资去向相关接口")
@RestController
@RequestMapping("/business/consumer")
public class ConsumerController {
    private final ConsumerService consumerService;

    @Autowired
    public ConsumerController(ConsumerService consumerService) {
        this.consumerService = consumerService;
    }

    /**
     * 去向列表
     *
     */
    @Operation(summary = "去向列表", description = "去向列表,根据去向名模糊查询")
    @GetMapping("/findConsumerList")
    public ResponseBean<Object> findConsumerList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                 @RequestParam(value = "pageSize") Integer pageSize,
                                                 ConsumerVO consumerVO) {
        PageVO<ConsumerVO> consumerVoAndPageVo = consumerService.findConsumerList(pageNum, pageSize, consumerVO);
        return ResponseBean.success(consumerVoAndPageVo);
    }

    /**
     * 添加去向
     *
     */
    @ControllerEndpoint(exceptionMessage = "物资去向添加失败", operation = "物资去向添加")
    @RequiresPermissions({"consumer:add"})
    @Operation(summary = "添加去向")
    @PostMapping("/add")
    public ResponseBean<Object> add(@RequestBody @Validated ConsumerVO consumerVO) {
        consumerService.add(consumerVO);
        return ResponseBean.success();
    }

    /**
     * 编辑去向
     *
     */
    @Operation(summary = "编辑去向", description = "编辑去向信息")
    @RequiresPermissions({"consumer:edit"})
    @GetMapping("/edit/{id}")
    public ResponseBean<Object> edit(@PathVariable Long id) {
        ConsumerVO consumerVO = consumerService.edit(id);
        return ResponseBean.success(consumerVO);
    }

    /**
     * 更新去向
     *
     */
    @ControllerEndpoint(exceptionMessage = "物资去向更新失败", operation = "物资去向更新")
    @Operation(summary = "更新去向", description = "更新去向信息")
    @RequiresPermissions({"consumer:update"})
    @PutMapping("/update/{id}")
    public ResponseBean<Object> update(@PathVariable Long id, @RequestBody @Validated ConsumerVO consumerVO) {
        consumerService.update(id, consumerVO);
        return ResponseBean.success();
    }

    /**
     * 删除去向
     *
     */
    @ControllerEndpoint(exceptionMessage = "物资去向删除失败", operation = "物资去向删除")
    @Operation(summary = "删除去向", description = "删除去向信息")
    @RequiresPermissions({"consumer:delete"})
    @DeleteMapping("/delete/{id}")
    public ResponseBean<Object> delete(@PathVariable Long id) {
        consumerService.delete(id);
        return ResponseBean.success();
    }

    /**
     * 所有去向
     *
     */
    @Operation(summary = "所有去向", description = "所有去向列表")
    @GetMapping("/findAll")
    public ResponseBean<Object> findAll() {
        List<ConsumerVO> consumerVOList = consumerService.findAll();
        return ResponseBean.success(consumerVOList);
    }
}
