package cn.anselyuki.system.service.impl;

import cn.anselyuki.common.enums.buisiness.BizUserTypeEnum;
import cn.anselyuki.common.enums.system.UserStatusEnum;
import cn.anselyuki.common.enums.system.UserTypeEnum;
import cn.anselyuki.common.error.SystemCodeEnum;
import cn.anselyuki.common.error.SystemException;
import cn.anselyuki.common.model.system.Department;
import cn.anselyuki.common.model.system.Role;
import cn.anselyuki.common.model.system.User;
import cn.anselyuki.common.model.system.UserRole;
import cn.anselyuki.common.vo.system.DeanVO;
import cn.anselyuki.common.vo.system.DepartmentVO;
import cn.anselyuki.common.vo.system.PageVO;
import cn.anselyuki.system.converter.DepartmentConverter;
import cn.anselyuki.system.mapper.DepartmentMapper;
import cn.anselyuki.system.mapper.RoleMapper;
import cn.anselyuki.system.mapper.UserMapper;
import cn.anselyuki.system.mapper.UserRoleMapper;
import cn.anselyuki.system.service.DepartmentService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author AnselYuki
 * @date 2022/9/15 14:15
 **/
@Service
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentMapper departmentMapper;
    private final UserMapper userMapper;
    private final UserRoleMapper userRoleMapper;
    private final RoleMapper roleMapper;

    @Autowired
    public DepartmentServiceImpl(DepartmentMapper departmentMapper, UserMapper userMapper, UserRoleMapper userRoleMapper, RoleMapper roleMapper) {
        this.departmentMapper = departmentMapper;
        this.userMapper = userMapper;
        this.userRoleMapper = userRoleMapper;
        this.roleMapper = roleMapper;
    }

    /**
     * 系别列表
     *
     */
    @Override
    public PageVO<DepartmentVO> findDepartmentList(Integer pageNum, Integer pageSize, DepartmentVO departmentVO) {
        PageHelper.startPage(pageNum, pageSize);
        Example o = new Example(Department.class);
        if (departmentVO.getName() != null && !"".equals(departmentVO.getName())) {
            o.createCriteria().andLike("name", "%" + departmentVO.getName() + "%");
        }
        List<Department> departments = departmentMapper.selectByExample(o);
        // 转vo
        List<DepartmentVO> departmentVOList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(departments)) {
            for (Department department : departments) {
                DepartmentVO d = new DepartmentVO();
                BeanUtils.copyProperties(department, d);
                Example o1 = new Example(User.class);
                o1.createCriteria().andEqualTo("departmentId", department.getId())
                        .andNotEqualTo("type", UserTypeEnum.SYSTEM_ADMIN.getTypeCode());
                d.setTotal(userMapper.selectCountByExample(o1));
                departmentVOList.add(d);
            }
        }
        PageInfo<Department> info = new PageInfo<>(departments);
        return new PageVO<>(info.getTotal(), departmentVOList);
    }

    /**
     * 查找所有系主任
     *
     */
    @Override
    public List<DeanVO> findDeanList() {
        Example o = new Example(Role.class);
        o.createCriteria().andEqualTo("roleName", BizUserTypeEnum.DEAN.getVal());
        List<Role> roles = roleMapper.selectByExample(o);
        List<DeanVO> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(roles)) {
            Role role = roles.get(0);
            Example o1 = new Example(UserRole.class);
            o1.createCriteria().andEqualTo("roleId", role.getId());
            List<UserRole> userRoleList = userRoleMapper.selectByExample(o1);
            if (!CollectionUtils.isEmpty(userRoleList)) {
                // 存放所有系主任的id
                List<Long> userIds = new ArrayList<>();
                for (UserRole userRole : userRoleList) {
                    userIds.add(userRole.getUserId());
                }
                if (userIds.size() > 0) {
                    for (Long userId : userIds) {
                        User user = userMapper.selectByPrimaryKey(userId);
                        // 所有可用的
                        if (user != null && user.getStatus() == UserStatusEnum.AVAILABLE.getStatusCode()) {
                            DeanVO deanVO = new DeanVO();
                            deanVO.setName(user.getUsername());
                            deanVO.setId(user.getId());
                            list.add(deanVO);
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * 添加院系
     *
     */
    @Override
    public void add(DepartmentVO departmentVO) {
        Department department = new Department();
        BeanUtils.copyProperties(departmentVO, department);
        department.setCreateTime(new Date());
        department.setModifiedTime(new Date());
        departmentMapper.insert(department);
    }

    /**
     * 编辑院系
     *
     */
    @Override
    public DepartmentVO edit(Long id) throws SystemException {
        Department department = departmentMapper.selectByPrimaryKey(id);
        if (department == null) {
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR, "编辑的部门不存在");
        }
        return DepartmentConverter.converterToDepartmentVO(department);
    }

    /**
     * 更新部门
     *
     */
    @Override
    public void update(Long id, DepartmentVO departmentVO) throws SystemException {
        Department dbDepartment = departmentMapper.selectByPrimaryKey(id);
        if (dbDepartment == null) {
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR, "要更新的部门不存在");
        }
        Department department = new Department();
        BeanUtils.copyProperties(departmentVO, department);
        department.setId(id);
        department.setModifiedTime(new Date());
        departmentMapper.updateByPrimaryKeySelective(department);
    }

    /**
     * 删除部门信息
     *
     */
    @Override
    public void delete(Long id) throws SystemException {
        Department department = departmentMapper.selectByPrimaryKey(id);
        if (department == null) {
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR, "要删除的部门不存在");
        }
        departmentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<DepartmentVO> findAllVO() {
        List<Department> departments = departmentMapper.selectAll();
        // 转vo
        List<DepartmentVO> departmentVOList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(departments)) {
            for (Department department : departments) {
                DepartmentVO d = new DepartmentVO();
                BeanUtils.copyProperties(department, d);
                Example o = new Example(User.class);
                o.createCriteria().andEqualTo("departmentId", department.getId())
                        .andNotEqualTo("type", 0);
                d.setTotal(userMapper.selectCountByExample(o));
                departmentVOList.add(d);
            }
        }
        return departmentVOList;
    }

    @Override
    public List<Department> findAll() {
        return departmentMapper.selectAll();
    }
}
