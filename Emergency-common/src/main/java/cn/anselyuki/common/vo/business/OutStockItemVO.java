package cn.anselyuki.common.vo.business;

import lombok.Data;

/**
 * @author AnselYuki
 * @date 2022/5/25 16:26
 **/
@Data
public class OutStockItemVO {

    private Long id;

    private String pNum;

    private String name;

    private String model;

    private String unit;

    private String imageUrl;

    private Integer count;

}
