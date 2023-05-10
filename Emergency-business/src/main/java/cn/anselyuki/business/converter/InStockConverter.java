package cn.anselyuki.business.converter;

import cn.anselyuki.business.mapper.SupplierMapper;
import cn.anselyuki.common.model.business.InStock;
import cn.anselyuki.common.model.business.Supplier;
import cn.anselyuki.common.vo.business.InStockVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AnselYuki
 * @date 2022/9/19 09:58
 **/
@Component
public class InStockConverter {
    private final SupplierMapper supplierMapper;

    @Autowired
    public InStockConverter(SupplierMapper supplierMapper) {
        this.supplierMapper = supplierMapper;
    }

    /**
     * 转voList
     *
     */
    public List<InStockVO> converterToVOList(List<InStock> inStocks) {
        List<InStockVO> inStockVOList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(inStocks)) {
            for (InStock inStock : inStocks) {
                InStockVO inStockVO = new InStockVO();
                BeanUtils.copyProperties(inStock, inStockVO);
                Supplier supplier = supplierMapper.selectByPrimaryKey(inStock.getSupplierId());
                if (supplier != null) {
                    inStockVO.setSupplierName(supplier.getName());
                    inStockVO.setPhone(supplier.getPhone());
                }
                inStockVOList.add(inStockVO);
            }
        }
        return inStockVOList;
    }
}
