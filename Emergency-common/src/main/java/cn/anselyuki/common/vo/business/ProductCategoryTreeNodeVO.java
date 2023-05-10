package cn.anselyuki.common.vo.business;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author AnselYuki
 * @date 2022/9/17 12:18
 **/
@Data
public class ProductCategoryTreeNodeVO {
    private Long id;

    private String name;

    private String remark;

    private Integer sort;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date modifiedTime;

    private Long pid;

    private Integer lev;

    private List<ProductCategoryTreeNodeVO> children;

    /*
     * 排序,根据order排序
     */
    public static Comparator<ProductCategoryTreeNodeVO> order() {
        return (o1, o2) -> {
            if (!o1.getSort().equals(o2.getSort())) {
                return o1.getSort() - o2.getSort();
            }
            return 0;
        };
    }
}
