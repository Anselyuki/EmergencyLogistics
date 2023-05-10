package cn.anselyuki.business.converter;

import cn.anselyuki.common.model.business.Consumer;
import cn.anselyuki.common.vo.business.ConsumerVO;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AnselYuki
 * @date 2022/9/16 20:27
 **/
public class ConsumerConverter {

    /**
     * 转voList
     *
     */
    public static List<ConsumerVO> converterToVOList(List<Consumer> consumers) {
        List<ConsumerVO> supplierVoList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(consumers)) {
            for (Consumer supplier : consumers) {
                ConsumerVO supplierVO = converterToConsumerVO(supplier);
                supplierVoList.add(supplierVO);
            }
        }
        return supplierVoList;
    }

    /***
     * 转VO
     *
     */
    public static ConsumerVO converterToConsumerVO(Consumer supplier) {
        ConsumerVO supplierVO = new ConsumerVO();
        BeanUtils.copyProperties(supplier, supplierVO);
        return supplierVO;
    }
}
