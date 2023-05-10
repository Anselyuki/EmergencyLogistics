package cn.anselyuki.system.converter;

import cn.anselyuki.common.model.system.Role;
import cn.anselyuki.common.vo.system.RoleTransferItemVO;
import cn.anselyuki.common.vo.system.RoleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AnselYuki
 * @date 2022/9/9 16:26
 **/
public class RoleConverter {

    /**
     * 转vo
     *
     */
    public static List<RoleVO> converterToRoleVOList(List<Role> roles) {
        List<RoleVO> roleVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roles)) {
            for (Role role : roles) {
                RoleVO roleVO = new RoleVO();
                BeanUtils.copyProperties(role, roleVO);
                roleVO.setStatus(role.getStatus() == 0);
                roleVOS.add(roleVO);
            }
        }
        return roleVOS;
    }

    /**
     * 转成前端需要的角色Item
     *
     */
    public static List<RoleTransferItemVO> converterToRoleTransferItem(List<Role> list) {
        List<RoleTransferItemVO> itemVOList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (Role role : list) {
                RoleTransferItemVO item = new RoleTransferItemVO();
                item.setLabel(role.getRoleName());
                item.setDisabled(role.getStatus() == 0);
                item.setKey(role.getId());
                itemVOList.add(item);
            }
        }

        return itemVOList;
    }
}
