package cn.anselyuki.common.vo.business;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author AnselYuki
 * @date 2022/5/7 10:19
 **/
@Data
public class HealthVO {
    private Long id;
    @NotBlank(message = "地址不能为空")
    private String address;
    private Long userId;
    @NotNull(message = "当前情况不能为空")
    private Integer situation;
    @NotNull(message = "是否接触不能为空")
    private Integer touch;
    @NotNull(message = "是否路过不能为空")
    private Integer passerby;
    @NotNull(message = "是否招待不能为空")
    private Integer reception;
    private Date createTime;
}
