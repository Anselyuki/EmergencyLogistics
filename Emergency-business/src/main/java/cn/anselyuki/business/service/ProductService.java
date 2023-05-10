package cn.anselyuki.business.service;

import cn.anselyuki.common.error.BusinessException;
import cn.anselyuki.common.vo.business.ProductStockVO;
import cn.anselyuki.common.vo.business.ProductVO;
import cn.anselyuki.common.vo.system.PageVO;

import java.util.List;

/**
 * @author AnselYuki
 * @date 2022/9/16 17:18
 **/
public interface ProductService {

    /**
     * 添加商品
     *
     */
    void add(ProductVO productVO);

    /**
     * 商品列表
     *
     */
    PageVO<ProductVO> findProductList(Integer pageNum, Integer pageSize, ProductVO productVO);

    /**
     * 编辑商品
     *
     */
    ProductVO edit(Long id);

    /**
     * 更新商品
     *
     */
    void update(Long id, ProductVO productVO);

    /**
     * 删除商品
     *
     */
    void delete(Long id) throws BusinessException;

    /**
     * 库存列表
     *
     */
    PageVO<ProductStockVO> findProductStocks(Integer pageNum, Integer pageSize, ProductVO productVO);

    /**
     * 所有库存信息
     *
     */
    List<ProductStockVO> findAllStocks(Integer pageNum, Integer pageSize, ProductVO productVO);

    /**
     * 移入回收站
     *
     */
    void remove(Long id) throws BusinessException;

    /**
     * 从回收站恢复数据
     *
     */
    void back(Long id) throws BusinessException;

    /**
     * 物资添加审核
     *
     */
    void publish(Long id) throws BusinessException;

}
