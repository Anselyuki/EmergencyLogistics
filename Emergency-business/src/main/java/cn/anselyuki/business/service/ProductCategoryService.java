package cn.anselyuki.business.service;

import cn.anselyuki.common.error.BusinessException;
import cn.anselyuki.common.vo.business.ProductCategoryTreeNodeVO;
import cn.anselyuki.common.vo.business.ProductCategoryVO;
import cn.anselyuki.common.vo.system.PageVO;

import java.util.List;

/**
 * @author AnselYuki
 * @date 2022/9/16 17:18
 **/
public interface ProductCategoryService {

    /**
     * 添加物资类别
     *
     */
    void add(ProductCategoryVO ProductCategoryVO);

    /**
     * 部门列表
     *
     */
    PageVO<ProductCategoryVO> findProductCategoryList(Integer pageNum, Integer pageSize,
                                                      ProductCategoryVO ProductCategoryVO);

    /**
     * 编辑物资类别
     *
     */
    ProductCategoryVO edit(Long id);

    /**
     * 更新物资类别
     *
     */
    void update(Long id, ProductCategoryVO ProductCategoryVO);

    /**
     * 删除物资类别
     *
     */
    void delete(Long id) throws BusinessException;

    /**
     * 查询所物资类别
     *
     */
    List<ProductCategoryVO> findAll();

    /**
     * 分类树形
     *
     */
    PageVO<ProductCategoryTreeNodeVO> categoryTree(Integer pageNum, Integer pageSize);

    /**
     * 获取父级分类（2级树）
     *
     */
    List<ProductCategoryTreeNodeVO> getParentCategoryTree();

}
