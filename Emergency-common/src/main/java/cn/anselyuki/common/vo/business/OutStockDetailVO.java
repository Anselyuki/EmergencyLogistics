package cn.anselyuki.common.vo.business;

import lombok.Data;

import java.util.List;

/**
 * @author AnselYuki
 * @date 2022/5/25 16:25
 **/
@Data
public class OutStockDetailVO {

    private String outNum;

    private Integer status;

    private Integer type;

    private String operator;

    private ConsumerVO consumerVO;

    private Long total;
    /**
     * 总数
     **/

    private List<OutStockItemVO> itemVOS;

}
