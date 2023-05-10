package cn.anselyuki.business.converter;

import cn.anselyuki.business.mapper.ConsumerMapper;
import cn.anselyuki.common.model.business.Consumer;
import cn.anselyuki.common.model.business.OutStock;
import cn.anselyuki.common.vo.business.OutStockVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AnselYuki
 * @date 2022/5/10 14:32
 **/
@Component
public class OutStockConverter {

    private final ConsumerMapper consumerMapper;

    @Autowired
    public OutStockConverter(ConsumerMapper consumerMapper) {
        this.consumerMapper = consumerMapper;
    }

    /**
     * 转voList
     *
     */
    public List<OutStockVO> converterToVOList(List<OutStock> outStocks) {
        List<OutStockVO> outStockVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(outStocks)) {
            for (OutStock outStock : outStocks) {
                OutStockVO outStockVO = new OutStockVO();
                BeanUtils.copyProperties(outStock, outStockVO);
                Consumer consumer = consumerMapper.selectByPrimaryKey(outStock.getConsumerId());
                if (consumer != null) {
                    outStockVO.setName(consumer.getName());
                    outStockVO.setPhone(consumer.getPhone());
                }
                outStockVOS.add(outStockVO);
            }
        }
        return outStockVOS;
    }
}
