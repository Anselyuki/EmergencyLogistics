package cn.anselyuki.business.converter;

import cn.anselyuki.common.model.business.ProductCategory;
import cn.anselyuki.common.vo.business.ProductCategoryTreeNodeVO;
import cn.anselyuki.common.vo.business.ProductCategoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AnselYuki
 * @date 2022/9/16 17:26
 **/
public class ProductCategoryConverter {

    /**
     * 转vo
     *
     */
    public static ProductCategoryVO converterToProductCategoryVO(ProductCategory productCategory) {
        ProductCategoryVO productCategoryVO = new ProductCategoryVO();
        BeanUtils.copyProperties(productCategory, productCategoryVO);
        return productCategoryVO;
    }

    /**
     * 转voList
     *
     */
    public static List<ProductCategoryVO> converterToVOList(List<ProductCategory> productCategories) {
        List<ProductCategoryVO> productCategoryVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(productCategories)) {
            for (ProductCategory productCategory : productCategories) {
                ProductCategoryVO productCategoryVO = new ProductCategoryVO();
                BeanUtils.copyProperties(productCategory, productCategoryVO);
                productCategoryVOS.add(productCategoryVO);
            }
        }
        return productCategoryVOS;
    }

    /**
     * 转树节点
     *
     */
    public static List<ProductCategoryTreeNodeVO> converterToTreeNodeVO(List<ProductCategoryVO> productCategoryVOList) {
        List<ProductCategoryTreeNodeVO> nodes = new ArrayList<>();
        if (!CollectionUtils.isEmpty(productCategoryVOList)) {
            for (ProductCategoryVO productCategoryVO : productCategoryVOList) {
                ProductCategoryTreeNodeVO productCategoryTreeNodeVO = new ProductCategoryTreeNodeVO();
                BeanUtils.copyProperties(productCategoryVO, productCategoryTreeNodeVO);
                nodes.add(productCategoryTreeNodeVO);
            }
        }
        return nodes;
    }
}
