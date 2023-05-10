package cn.anselyuki.business.service.imp;

import cn.anselyuki.business.converter.ConsumerConverter;
import cn.anselyuki.business.mapper.ConsumerMapper;
import cn.anselyuki.business.service.ConsumerService;
import cn.anselyuki.common.model.business.Consumer;
import cn.anselyuki.common.vo.business.ConsumerVO;
import cn.anselyuki.common.vo.system.PageVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author AnselYuki
 * @date 2022/9/16 17:19
 **/
@Service
public class ConsumerServiceImpl implements ConsumerService {

    private final ConsumerMapper consumerMapper;

    @Autowired
    public ConsumerServiceImpl(ConsumerMapper consumerMapper) {
        this.consumerMapper = consumerMapper;
    }

    /**
     * 供应商列表
     *
     */
    @Override
    public PageVO<ConsumerVO> findConsumerList(Integer pageNum, Integer pageSize, ConsumerVO consumerVO) {
        PageHelper.startPage(pageNum, pageSize);
        Example o = new Example(Consumer.class);
        Example.Criteria criteria = o.createCriteria();
        o.setOrderByClause("sort asc");
        if (consumerVO.getName() != null && !"".equals(consumerVO.getName())) {
            criteria.andLike("name", "%" + consumerVO.getName() + "%");
        }
        if (consumerVO.getAddress() != null && !"".equals(consumerVO.getAddress())) {
            criteria.andLike("address", "%" + consumerVO.getAddress() + "%");
        }
        if (consumerVO.getContact() != null && !"".equals(consumerVO.getContact())) {
            criteria.andLike("contact", "%" + consumerVO.getContact() + "%");
        }
        List<Consumer> consumers = consumerMapper.selectByExample(o);
        List<ConsumerVO> consumerVOList = ConsumerConverter.converterToVOList(consumers);
        PageInfo<Consumer> info = new PageInfo<>(consumers);
        return new PageVO<>(info.getTotal(), consumerVOList);
    }

    /**
     * 添加供应商
     *
     * @param consumerVO 用户视图
     */
    @Override
    public Consumer add(ConsumerVO consumerVO) {
        Consumer consumer = new Consumer();
        BeanUtils.copyProperties(consumerVO, consumer);
        consumer.setCreateTime(new Date());
        consumer.setModifiedTime(new Date());
        consumerMapper.insert(consumer);
        return consumer;
    }

    /**
     * 编辑供应商
     *
     */
    @Override
    public ConsumerVO edit(Long id) {
        Consumer consumer = consumerMapper.selectByPrimaryKey(id);
        return ConsumerConverter.converterToConsumerVO(consumer);
    }

    /**
     * 更新供应商
     *
     */
    @Override
    public void update(Long id, ConsumerVO consumerVO) {
        Consumer consumer = new Consumer();
        BeanUtils.copyProperties(consumerVO, consumer);
        consumer.setModifiedTime(new Date());
        consumerMapper.updateByPrimaryKeySelective(consumer);
    }

    /**
     * 删除供应商
     *
     */
    @Override
    public void delete(Long id) {
        consumerMapper.deleteByPrimaryKey(id);
    }

    /**
     * 查询所有
     *
     */
    @Override
    public List<ConsumerVO> findAll() {
        List<Consumer> consumers = consumerMapper.selectAll();
        return ConsumerConverter.converterToVOList(consumers);
    }

}
