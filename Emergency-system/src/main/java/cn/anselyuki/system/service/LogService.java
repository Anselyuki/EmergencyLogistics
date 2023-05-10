package cn.anselyuki.system.service;

import cn.anselyuki.common.error.SystemException;
import cn.anselyuki.common.model.system.Log;
import cn.anselyuki.common.vo.system.LogVO;
import cn.anselyuki.common.vo.system.PageVO;
import org.springframework.scheduling.annotation.Async;

import java.util.List;

/**
 * 系统日志
 */
public interface LogService {

    /**
     * 异步保存操作日志
     */
    @Async("CodeAsyncThreadPool")
    void saveLog(Log log);


    /**
     * 删除登入日志
     *
     */
    void delete(Long id) throws SystemException;


    /**
     * 登入日志列表
     *
     */
    PageVO<LogVO> findLogList(Integer pageNum, Integer pageSize, LogVO logVO);

    /**
     * 批量删除登入日志
     *
     */
    void batchDelete(List<Long> list) throws SystemException;
}
