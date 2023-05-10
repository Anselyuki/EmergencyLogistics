package cn.anselyuki.business.service;

import cn.anselyuki.common.error.BusinessException;
import cn.anselyuki.common.vo.business.OutStockDetailVO;
import cn.anselyuki.common.vo.business.OutStockVO;
import cn.anselyuki.common.vo.system.PageVO;

/**
 * @author AnselYuki
 * @date 2022/5/10 14:26
 **/
public interface OutStockService {

    /**
     * 出库单列表
     *
     */
    PageVO<OutStockVO> findOutStockList(Integer pageNum, Integer pageSize, OutStockVO outStockVO);

    /**
     * 提交物资发放单
     *
     */
    void addOutStock(OutStockVO outStockVO) throws BusinessException;

    /**
     * 移入回收站
     *
     */
    void remove(Long id) throws BusinessException;

    /**
     * 恢复发放单
     *
     */
    void back(Long id) throws BusinessException;

    /**
     * 发放单详情
     *
     */
    OutStockDetailVO detail(Long id, Integer pageNum, Integer pageSize) throws BusinessException;

    /**
     * 删除发放单
     *
     */
    void delete(Long id) throws BusinessException;

    /**
     * 发放单审核
     *
     */
    void publish(Long id) throws BusinessException;
}
