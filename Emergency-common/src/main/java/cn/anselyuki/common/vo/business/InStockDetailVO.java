package cn.anselyuki.common.vo.business;

import lombok.Data;

import java.util.List;

/**
 * @author AnselYuki
 * @date 2022/9/20 16:51
 **/
@Data
public class InStockDetailVO {

    private String inNum;

    private Integer status;

    private Integer type;

    private String operator;

    private SupplierVO supplierVO;

    private Long total;
    /**
     * 总数
     **/

    private List<InStockItemVO> itemVOList;

}
