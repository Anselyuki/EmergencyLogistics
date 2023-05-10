package cn.anselyuki.controller.business;

import cn.anselyuki.business.service.ProductService;
import cn.anselyuki.common.annotation.ControllerEndpoint;
import cn.anselyuki.common.error.BusinessCodeEnum;
import cn.anselyuki.common.error.BusinessException;
import cn.anselyuki.common.response.ResponseBean;
import cn.anselyuki.common.vo.business.ProductStockVO;
import cn.anselyuki.common.vo.business.ProductVO;
import cn.anselyuki.common.vo.system.PageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author AnselYuki
 * @date 2022/9/17 09:19
 **/
@Tag(name = "业务模块-物资资料相关接口")
@RestController
@RequestMapping("/business/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * 全部物资列表
     *
     */
    @Operation(summary = "物资列表", description = "物资列表,根据物资名模糊查询")
    @GetMapping("/findProductList")
    public ResponseBean<Object> findProductList(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                                @RequestParam(value = "pageSize") Integer pageSize,
                                                @RequestParam(value = "categorys", required = false) String categorys,
                                                ProductVO productVO) {
        buildCategorySearch(categorys, productVO);
        PageVO<ProductVO> productVoAndPageVo = productService.findProductList(pageNum, pageSize, productVO);
        return ResponseBean.success(productVoAndPageVo);
    }

    /**
     * 可入库物资(入库页面使用)
     *
     */
    @Operation(summary = "可入库物资列表", description = "物资列表,根据物资名模糊查询")
    @GetMapping("/findProducts")
    public ResponseBean<Object> findProducts(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                             @RequestParam(value = "pageSize") Integer pageSize,
                                             @RequestParam(value = "categorys", required = false) String categorys,
                                             ProductVO productVO) {
        productVO.setStatus(0);
        buildCategorySearch(categorys, productVO);
        PageVO<ProductVO> productVoAndPageVo = productService.findProductList(pageNum, pageSize, productVO);
        return ResponseBean.success(productVoAndPageVo);
    }

    /**
     * 库存列表
     *
     */
    @Operation(summary = "库存列表", description = "物资列表,根据物资名模糊查询")
    @GetMapping("/findProductStocks")
    public ResponseBean findProductStocks(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                          @RequestParam(value = "pageSize") Integer pageSize,
                                          @RequestParam(value = "categorys", required = false) String categorys,
                                          ProductVO productVO) {

        buildCategorySearch(categorys, productVO);
        PageVO<ProductStockVO> productVoAndPageVo = productService.findProductStocks(pageNum, pageSize, productVO);
        return ResponseBean.success(productVoAndPageVo);
    }

    /**
     * 所有库存(饼图使用)
     *
     */
    @Operation(summary = "全部库存", description = "物资所有库存信息,饼图使用")
    @GetMapping("/findAllStocks")
    public ResponseBean findAllStocks(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                      @RequestParam(value = "pageSize") Integer pageSize,
                                      @RequestParam(value = "categorys", required = false) String categorys,
                                      ProductVO productVO) {
        buildCategorySearch(categorys, productVO);
        List<ProductStockVO> list = productService.findAllStocks(pageNum, pageSize, productVO);
        return ResponseBean.success(list);
    }

    /**
     * 封装物资查询条件
     *
     */
    private void buildCategorySearch(@RequestParam(value = "categorys", required = false) String categorys,
                                     ProductVO productVO) {
        if (categorys != null && !"".equals(categorys)) {
            String[] split = categorys.split(",");
            switch (split.length) {
                case 1:
                    productVO.setOneCategoryId(Long.parseLong(split[0]));
                    break;
                case 2:
                    productVO.setOneCategoryId(Long.parseLong(split[0]));
                    productVO.setTwoCategoryId(Long.parseLong(split[1]));
                    break;
                case 3:
                    productVO.setOneCategoryId(Long.parseLong(split[0]));
                    productVO.setTwoCategoryId(Long.parseLong(split[1]));
                    productVO.setThreeCategoryId(Long.parseLong(split[2]));
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 添加物资
     *
     */
    @ControllerEndpoint(exceptionMessage = "添加物资失败", operation = "物资资料添加")
    @Operation(summary = "添加物资")
    @RequiresPermissions({"product:add"})
    @PostMapping("/add")
    public ResponseBean add(@RequestBody @Validated ProductVO productVO) throws BusinessException {
        if (productVO.getCategoryKeys().length != 3) {
            throw new BusinessException(BusinessCodeEnum.PARAMETER_ERROR, "物资需要3级分类");
        }
        productService.add(productVO);
        return ResponseBean.success(productVO);
    }

    /**
     * 编辑物资
     *
     */
    @Operation(summary = "编辑物资", description = "编辑物资信息")
    @RequiresPermissions({"product:edit"})
    @GetMapping("/edit/{id}")
    public ResponseBean edit(@PathVariable Long id) {
        ProductVO productVO = productService.edit(id);
        return ResponseBean.success(productVO);
    }

    /**
     * 更新物资
     *
     */
    @ControllerEndpoint(exceptionMessage = "更新物资失败", operation = "物资资料更新")
    @Operation(summary = "更新物资", description = "更新物资信息")
    @RequiresPermissions({"product:update"})
    @PutMapping("/update/{id}")
    public ResponseBean update(@PathVariable Long id, @RequestBody ProductVO productVO) throws BusinessException {
        if (productVO.getCategoryKeys().length != 3) {
            throw new BusinessException(BusinessCodeEnum.PARAMETER_ERROR, "物资需要3级分类");
        }
        productService.update(id, productVO);
        return ResponseBean.success(productVO);
    }

    /**
     * 删除物资
     *
     */
    @ControllerEndpoint(exceptionMessage = "删除物资失败", operation = "物资资料删除")
    @Operation(summary = "删除物资", description = "删除物资信息")
    @RequiresPermissions({"product:delete"})
    @DeleteMapping("/delete/{id}")
    public ResponseBean delete(@PathVariable Long id) throws BusinessException {
        productService.delete(id);
        return ResponseBean.success();
    }

    /**
     * 移入回收站
     *
     */
    @ControllerEndpoint(exceptionMessage = "回收物资失败", operation = "物资资料回收")
    @Operation(summary = "移入回收站", description = "移入回收站")
    @RequiresPermissions({"product:remove"})
    @PutMapping("/remove/{id}")
    public ResponseBean remove(@PathVariable Long id) throws BusinessException {
        productService.remove(id);
        return ResponseBean.success();
    }

    /**
     * 物资添加审核
     *
     */
    @ControllerEndpoint(exceptionMessage = "物资添加审核失败", operation = "物资资料审核")
    @Operation(summary = "物资添加审核", description = "物资添加审核")
    @RequiresPermissions({"product:publish"})
    @PutMapping("/publish/{id}")
    public ResponseBean publish(@PathVariable Long id) throws BusinessException {
        productService.publish(id);
        return ResponseBean.success();
    }

    /**
     * 恢复数据从回收站
     *
     */
    @ControllerEndpoint(exceptionMessage = "恢复物资失败", operation = "物资资料恢复")
    @Operation(summary = "恢复物资", description = "从回收站中恢复物资")
    @RequiresPermissions({"product:back"})
    @PutMapping("/back/{id}")
    public ResponseBean back(@PathVariable Long id) throws BusinessException {
        productService.back(id);
        return ResponseBean.success();
    }
}
