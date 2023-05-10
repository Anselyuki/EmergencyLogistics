package cn.anselyuki.controller.business;

import cn.anselyuki.business.service.ProductCategoryService;
import cn.anselyuki.common.annotation.ControllerEndpoint;
import cn.anselyuki.common.error.BusinessException;
import cn.anselyuki.common.response.ResponseBean;
import cn.anselyuki.common.vo.business.ProductCategoryTreeNodeVO;
import cn.anselyuki.common.vo.business.ProductCategoryVO;
import cn.anselyuki.common.vo.system.PageVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 物资分类管理
 *
 * @author AnselYuki
 * @date 2022/9/16 17:16
 **/
@Tag(name = "业务模块-物资类别相关接口")
@RestController
@RequestMapping("/business/productCategory")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @Autowired
    public ProductCategoryController(ProductCategoryService productCategoryService) {
        this.productCategoryService = productCategoryService;
    }

    /**
     * 物资分类列表
     */
    @Operation(summary = "分类列表", description = "物资分类列表,根据物资分类名模糊查询")
    @GetMapping("/findProductCategoryList")
    public ResponseBean<PageVO<ProductCategoryVO>> findProductCategoryList(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize,
            ProductCategoryVO productCategoryVO) {

        PageVO<ProductCategoryVO> departmentsList = productCategoryService.findProductCategoryList(pageNum, pageSize,
                productCategoryVO);
        return ResponseBean.success(departmentsList);
    }

    /**
     * 分类树形结构(分页)
     */
    @Operation(summary = "分类树形结构")
    @GetMapping("/categoryTree")
    public ResponseBean<PageVO<ProductCategoryTreeNodeVO>> categoryTree(@RequestParam(value = "pageNum", required = false) Integer pageNum,
                                                                        @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        PageVO<ProductCategoryTreeNodeVO> pageVO = productCategoryService.categoryTree(pageNum, pageSize);
        return ResponseBean.success(pageVO);
    }

    /**
     * 获取父级分类树：2级树
     */
    @Operation(summary = "父级分类树")
    @GetMapping("/getParentCategoryTree")
    public ResponseBean<List<ProductCategoryTreeNodeVO>> getParentCategoryTree() {
        List<ProductCategoryTreeNodeVO> parentTree = productCategoryService.getParentCategoryTree();
        return ResponseBean.success(parentTree);
    }

    /**
     * 查询所有分类
     */
    @Operation(summary = "所有分类")
    @GetMapping("/findAll")
    public ResponseBean<List<ProductCategoryVO>> findAll() {
        List<ProductCategoryVO> productCategoryVOList = productCategoryService.findAll();
        return ResponseBean.success(productCategoryVOList);
    }

    /**
     * 添加物资分类
     */
    @ControllerEndpoint(exceptionMessage = "物资分类添加失败", operation = "物资分类添加")
    @RequiresPermissions({"productCategory:add"})
    @Operation(summary = "添加分类")
    @PostMapping("/add")
    public ResponseBean<Object> add(@RequestBody @Validated ProductCategoryVO productCategoryVO) {
        productCategoryService.add(productCategoryVO);
        return ResponseBean.success();
    }

    /**
     * 编辑物资分类
     */
    @Operation(summary = "编辑分类")
    @RequiresPermissions({"productCategory:edit"})
    @GetMapping("/edit/{id}")
    public ResponseBean<ProductCategoryVO> edit(@PathVariable Long id) {
        ProductCategoryVO productCategoryVO = productCategoryService.edit(id);
        return ResponseBean.success(productCategoryVO);
    }

    /**
     * 更新物资分类
     */
    @ControllerEndpoint(exceptionMessage = "物资分类更新失败", operation = "物资分类更新")
    @Operation(summary = "更新分类")
    @RequiresPermissions({"productCategory:update"})
    @PutMapping("/update/{id}")
    public ResponseBean<Object> update(@PathVariable Long id, @RequestBody @Validated ProductCategoryVO productCategoryVO) {
        productCategoryService.update(id, productCategoryVO);
        return ResponseBean.success();
    }

    /**
     * 删除物资分类
     */
    @ControllerEndpoint(exceptionMessage = "物资分类删除失败", operation = "物资分类删除")
    @Operation(summary = "删除分类")
    @RequiresPermissions({"productCategory:delete"})
    @DeleteMapping("/delete/{id}")
    public ResponseBean<Object> delete(@PathVariable Long id) throws BusinessException {
        productCategoryService.delete(id);
        return ResponseBean.success();
    }
}
