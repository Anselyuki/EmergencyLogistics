package cn.anselyuki.business.service;

import cn.anselyuki.common.error.BusinessException;
import cn.anselyuki.common.vo.business.InStockDetailVO;
import cn.anselyuki.common.vo.business.InStockVO;
import cn.anselyuki.common.vo.system.PageVO;

/**
 * @author AnselYuki
 * @date 2022/9/19 09:54
 **/
public interface InStockService {

    /**
     * 入库单列表
     *
     */
    PageVO<InStockVO> findInStockList(Integer pageNum, Integer pageSize, InStockVO inStockVO);

    /**
     * 入库单明细
     *
     */
    InStockDetailVO detail(Long id, int pageNo, int pageSize) throws BusinessException;

    /**
     * 删除入库单
     *
     */
    void delete(Long id) throws BusinessException;

    /**
     * 物资入库
     *
     */
    void addIntoStock(InStockVO inStockVO) throws BusinessException;

    /**
     * 移入回收站
     *
     */
    void remove(Long id) throws BusinessException;

    /**
     * 还原从回收站中
     *
     */
    void back(Long id) throws BusinessException;

    /**
     * 入库审核
     *
     */
    void publish(Long id) throws BusinessException;
}
