package cn.anselyuki.system.mapper;

import cn.anselyuki.common.model.system.LoginLog;
import cn.anselyuki.common.vo.system.UserVO;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author AnselYuki
 * @date 2022/9/20 19:09
 **/
public interface LoginLogMapper extends Mapper<LoginLog> {

    /**
     * 用户登入报表
     *
     */
    List<Map<String, Object>> userLoginReport(UserVO userVO);

}
