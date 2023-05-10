package cn.anselyuki.business.mapper;

import cn.anselyuki.common.model.business.ProductStock;
import cn.anselyuki.common.vo.business.ProductStockVO;
import cn.anselyuki.common.vo.business.ProductVO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author AnselYuki
 * @date 2022/9/21 19:38
 **/
public interface ProductStockMapper extends Mapper<ProductStock> {

    /**
     * 库存列表
     *
     */
    List<ProductStockVO> findProductStocks(ProductVO productVO);

    /**
     * 库存信息(饼图使用)
     *
     */
    List<ProductStockVO> findAllStocks(ProductVO productVO);
}
