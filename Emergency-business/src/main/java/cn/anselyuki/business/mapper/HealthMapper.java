package cn.anselyuki.business.mapper;

import cn.anselyuki.common.model.business.Health;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author AnselYuki
 * @date 2022/5/7 10:16
 **/
public interface HealthMapper extends Mapper<Health> {
    /**
     * 今日是否打卡
     *
     */
    @Select("select * from biz_health where create_time < (CURDATE()+1) " +
            " and create_time>CURDATE() and user_id=#{id}")
    List<Health> isReport(@Param("id") Long id);
}
