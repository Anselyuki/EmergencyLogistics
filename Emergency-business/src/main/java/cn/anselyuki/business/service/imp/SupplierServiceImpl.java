package cn.anselyuki.business.service.imp;

import cn.anselyuki.business.converter.SupplierConverter;
import cn.anselyuki.business.mapper.SupplierMapper;
import cn.anselyuki.business.service.SupplierService;
import cn.anselyuki.common.model.business.Supplier;
import cn.anselyuki.common.vo.business.SupplierVO;
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
public class SupplierServiceImpl implements SupplierService {
    private final SupplierMapper supplierMapper;

    @Autowired
    public SupplierServiceImpl(SupplierMapper supplierMapper) {
        this.supplierMapper = supplierMapper;
    }

    /**
     * 供应商列表
     *
     */
    @Override
    public PageVO<SupplierVO> findSupplierList(Integer pageNum, Integer pageSize, SupplierVO supplierVO) {
        PageHelper.startPage(pageNum, pageSize);
        Example o = new Example(Supplier.class);
        Example.Criteria criteria = o.createCriteria();
        o.setOrderByClause("sort asc");
        if (supplierVO.getName() != null && !"".equals(supplierVO.getName())) {
            criteria.andLike("name", "%" + supplierVO.getName() + "%");
        }
        if (supplierVO.getContact() != null && !"".equals(supplierVO.getContact())) {
            criteria.andLike("contact", "%" + supplierVO.getContact() + "%");
        }
        if (supplierVO.getAddress() != null && !"".equals(supplierVO.getAddress())) {
            criteria.andLike("address", "%" + supplierVO.getAddress() + "%");
        }
        List<Supplier> suppliers = supplierMapper.selectByExample(o);
        List<SupplierVO> categoryVOS = SupplierConverter.converterToVOList(suppliers);
        PageInfo<Supplier> info = new PageInfo<>(suppliers);
        return new PageVO<>(info.getTotal(), categoryVOS);
    }

    /**
     * 添加供应商
     *
     */
    @Override
    public Supplier add(SupplierVO SupplierVO) {
        Supplier supplier = new Supplier();
        BeanUtils.copyProperties(SupplierVO, supplier);
        supplier.setCreateTime(new Date());
        supplier.setModifiedTime(new Date());
        supplierMapper.insert(supplier);
        return supplier;
    }

    /**
     * 编辑供应商
     *
     */
    @Override
    public SupplierVO edit(Long id) {
        Supplier supplier = supplierMapper.selectByPrimaryKey(id);
        return SupplierConverter.converterToSupplierVO(supplier);
    }

    /**
     * 更新供应商
     *
     */
    @Override
    public void update(Long id, SupplierVO SupplierVO) {
        Supplier supplier = new Supplier();
        BeanUtils.copyProperties(SupplierVO, supplier);
        supplier.setModifiedTime(new Date());
        supplierMapper.updateByPrimaryKeySelective(supplier);
    }

    /**
     * 删除供应商
     *
     */
    @Override
    public void delete(Long id) {
        supplierMapper.deleteByPrimaryKey(id);
    }

    /**
     * 查询所有
     *
     */
    @Override
    public List<SupplierVO> findAll() {
        List<Supplier> suppliers = supplierMapper.selectAll();
        return SupplierConverter.converterToVOList(suppliers);
    }

}
