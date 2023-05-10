package cn.anselyuki.system.service;

import cn.anselyuki.common.error.SystemException;
import cn.anselyuki.common.vo.system.LoginLogVO;
import cn.anselyuki.common.vo.system.PageVO;
import cn.anselyuki.common.vo.system.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author AnselYuki
 * @date 2022/9/20 19:10
 **/
public interface LoginLogService {

    /**
     * 添加登入日志
     *
     */
    void add(HttpServletRequest request);

    /**
     * 删除登入日志
     *
     */
    void delete(Long id) throws SystemException;

    /**
     * 登入日志列表
     *
     */
    PageVO<LoginLogVO> findLoginLogList(Integer pageNum, Integer pageSize, LoginLogVO loginLogVO);

    /**
     * 批量删除登入日志
     *
     */
    void batchDelete(List<Long> list) throws SystemException;

    /**
     * 用户登入报表
     *
     */
    List<Map<String, Object>> loginReport(UserVO userVO);
}
