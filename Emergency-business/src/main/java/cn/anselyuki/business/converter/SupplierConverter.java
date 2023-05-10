package cn.anselyuki.business.converter;

import cn.anselyuki.common.model.business.Supplier;
import cn.anselyuki.common.vo.business.SupplierVO;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AnselYuki
 * @date 2022/9/16 20:27
 **/
public class SupplierConverter {

    /**
     * 转voList
     *
     */
    public static List<SupplierVO> converterToVOList(List<Supplier> suppliers) {
        List<SupplierVO> supplierVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(suppliers)) {
            for (Supplier supplier : suppliers) {
                SupplierVO supplierVO = converterToSupplierVO(supplier);
                supplierVOS.add(supplierVO);
            }
        }
        return supplierVOS;
    }

    /***
     * 转VO
     *
     */
    public static SupplierVO converterToSupplierVO(Supplier supplier) {
        SupplierVO supplierVO = new SupplierVO();
        BeanUtils.copyProperties(supplier, supplierVO);
        return supplierVO;
    }
}
