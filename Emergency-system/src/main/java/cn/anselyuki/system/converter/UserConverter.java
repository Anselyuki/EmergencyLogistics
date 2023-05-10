package cn.anselyuki.system.converter;

import cn.anselyuki.common.model.system.Department;
import cn.anselyuki.common.model.system.User;
import cn.anselyuki.common.vo.system.UserVO;
import cn.anselyuki.system.mapper.DepartmentMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AnselYuki
 * @date 2022/9/7 19:56
 **/
@Component
public class UserConverter {
    private final DepartmentMapper departmentMapper;

    @Autowired
    public UserConverter(DepartmentMapper departmentMapper) {
        this.departmentMapper = departmentMapper;
    }

    /**
     * 转voList
     *
     */
    public List<UserVO> converterToUserVOList(List<User> users) {
        List<UserVO> userVOS = new ArrayList<>();
        if (!CollectionUtils.isEmpty(users)) {
            for (User user : users) {
                UserVO userVO = converterToUserVO(user);
                userVOS.add(userVO);
            }
        }
        return userVOS;
    }

    /**
     * 转vo
     *
     */
    public UserVO converterToUserVO(User user) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        userVO.setStatus(user.getStatus() == 0);
        Department department = departmentMapper.selectByPrimaryKey(user.getDepartmentId());
        if (department != null && department.getName() != null) {
            userVO.setDepartmentName(department.getName());
            userVO.setDepartmentId(department.getId());
        }
        return userVO;
    }

}
