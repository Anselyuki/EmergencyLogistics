package cn.anselyuki.system.service.impl;

import cn.anselyuki.common.error.SystemCodeEnum;
import cn.anselyuki.common.error.SystemException;
import cn.anselyuki.common.model.system.Log;
import cn.anselyuki.common.model.system.LoginLog;
import cn.anselyuki.common.vo.system.LogVO;
import cn.anselyuki.common.vo.system.PageVO;
import cn.anselyuki.system.mapper.LogMapper;
import cn.anselyuki.system.service.LogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AnselYuki
 * @date 2022/10/2 20:24
 **/
@Service
public class LogServiceImpl implements LogService {
    private final LogMapper logMapper;

    @Autowired
    public LogServiceImpl(LogMapper logMapper) {
        this.logMapper = logMapper;
    }

    /**
     * 保存登入日志
     *
     */
    @Override
    public void saveLog(Log log) {
        logMapper.insert(log);
    }

    /**
     * 删除操作日志
     *
     */
    @Override
    public void delete(Long id) throws SystemException {
        Log log = logMapper.selectByPrimaryKey(id);
        if (log == null) {
            throw new SystemException(SystemCodeEnum.PARAMETER_ERROR, "要删除的操作日志不存在");
        }
        logMapper.deleteByPrimaryKey(id);
    }

    @Override
    public PageVO<LogVO> findLogList(Integer pageNum, Integer pageSize, LogVO logVO) {
        PageHelper.startPage(pageNum, pageSize);
        Example o = new Example(LoginLog.class);
        Example.Criteria criteria = o.createCriteria();
        o.setOrderByClause("create_time desc");
        if (logVO.getLocation() != null && !"".equals(logVO.getLocation())) {
            criteria.andLike("location", "%" + logVO.getLocation() + "%");
        }
        if (logVO.getIp() != null && !"".equals(logVO.getIp())) {
            criteria.andLike("ip", "%" + logVO.getIp() + "%");
        }
        if (logVO.getUsername() != null && !"".equals(logVO.getUsername())) {
            criteria.andLike("username", "%" + logVO.getUsername() + "%");
        }
        List<Log> loginLogs = logMapper.selectByExample(o);
        List<LogVO> logVOList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(loginLogs)) {
            for (Log loginLog : loginLogs) {
                LogVO loggingVO = new LogVO();
                BeanUtils.copyProperties(loginLog, loggingVO);
                logVOList.add(loggingVO);
            }
        }
        PageInfo<Log> info = new PageInfo<>(loginLogs);
        return new PageVO<>(info.getTotal(), logVOList);
    }

    /**
     * 批量删除
     *
     */
    @Override
    public void batchDelete(List<Long> list) throws SystemException {
        for (Long id : list) {
            Log log = logMapper.selectByPrimaryKey(id);
            if (log == null) {
                throw new SystemException(SystemCodeEnum.PARAMETER_ERROR, "id=" + id + ",操作日志不存在");
            }
            delete(id);
        }
    }
}
