package cn.anselyuki.business.service;

import cn.anselyuki.common.model.business.Consumer;
import cn.anselyuki.common.vo.business.ConsumerVO;
import cn.anselyuki.common.vo.system.PageVO;

import java.util.List;

/**
 * @author AnselYuki
 * @date 2022/10/5 09:53
 **/
public interface ConsumerService {

    /**
     * 添加物资去向
     *
     */
    Consumer add(ConsumerVO consumerVO);

    /**
     * 物资去向列表
     *
     */
    PageVO<ConsumerVO> findConsumerList(Integer pageNum, Integer pageSize, ConsumerVO consumerVO);

    /**
     * 编辑物资去向
     *
     */
    ConsumerVO edit(Long id);

    /**
     * 更新物资去向
     *
     */
    void update(Long id, ConsumerVO consumerVO);

    /**
     * 删除物资去向
     *
     */
    void delete(Long id);

    /**
     * 查询所有物资去向
     *
     */
    List<ConsumerVO> findAll();

}
