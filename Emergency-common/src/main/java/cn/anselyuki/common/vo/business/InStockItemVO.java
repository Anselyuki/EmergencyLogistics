package cn.anselyuki.common.vo.business;

import lombok.Data;

/**
 * @author AnselYuki
 * @date 2022/9/20 16:53
 **/
@Data
public class InStockItemVO {
    private Long id;

    private String pNum;

    private String name;

    private String model;

    private String unit;

    private String imageUrl;

    private Integer count;
}
