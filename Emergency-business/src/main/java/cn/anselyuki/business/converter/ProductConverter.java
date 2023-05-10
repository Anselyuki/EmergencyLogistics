package cn.anselyuki.business.converter;

import cn.anselyuki.common.model.business.Product;
import cn.anselyuki.common.vo.business.ProductVO;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AnselYuki
 * @date 2022/9/17 09:22
 **/
public class ProductConverter {

    /**
     * 转VOList
     *
     */
    public static List<ProductVO> converterToVOList(List<Product> products) {
        List<ProductVO> productVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(products)) {
            for (Product product : products) {
                ProductVO productVO = converterToProductVO(product);
                productVOS.add(productVO);
            }
        }
        return productVOS;
    }

    /**
     * 转VO
     *
     */
    public static ProductVO converterToProductVO(Product product) {
        ProductVO productVO = new ProductVO();
        BeanUtils.copyProperties(product, productVO);
        return productVO;
    }
}
