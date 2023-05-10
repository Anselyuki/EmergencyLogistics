package cn.anselyuki.common.vo.system;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * @author AnselYuki
 * @date 2022/9/7 17:02
 **/
@Data
@Tag(name = "用户登入信息")
public class UserInfoVO {

    @Schema(name = "用户名")
    private String username;

    @Schema(name = "昵称")
    private String nickname;

    @Schema(name = "头像")
    private String avatar;

    @Schema(name = "菜单")
    private Set<String> url;

    @Schema(name = "权限")
    private Set<String> perms;

    @Schema(name = "角色集合")
    private List<String> roles;

    @Schema(name = "所在部门")
    private String department;

    @Schema(name = "是否是超管")
    private Boolean isAdmin;

}
