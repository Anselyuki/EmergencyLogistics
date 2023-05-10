package cn.anselyuki.common.vo.system;

import lombok.Data;

import java.util.List;

/**
 * @author AnselYuki
 * @date 2022/9/7 19:41
 **/
@Data
public class PageVO<T> {
    private Long total;

    private List<T> rows;

    public PageVO(long total, List<T> data) {
        this.total = total;
        this.rows = data;
    }
}
