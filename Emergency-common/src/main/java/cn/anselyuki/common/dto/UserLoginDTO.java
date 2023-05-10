package cn.anselyuki.common.dto;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author AnselYuki
 * @date 2022/12/16 21:40
 **/
@Data
@Tag(name = "用户登入表单")
public class UserLoginDTO {
    @NotBlank(message = "用户名不能为空")
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;
}
