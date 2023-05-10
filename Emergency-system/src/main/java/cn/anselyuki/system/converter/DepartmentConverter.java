package cn.anselyuki.system.converter;

import cn.anselyuki.common.model.system.Department;
import cn.anselyuki.common.vo.system.DepartmentVO;
import org.springframework.beans.BeanUtils;

/**
 * @author AnselYuki
 * @date 2022/9/7 19:56
 **/
public class DepartmentConverter {

    /**
     * 转vo
     *
     */
    public static DepartmentVO converterToDepartmentVO(Department department) {
        DepartmentVO departmentVO = new DepartmentVO();
        BeanUtils.copyProperties(department, departmentVO);
        return departmentVO;
    }
}
