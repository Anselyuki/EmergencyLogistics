package cn.anselyuki.business.service;

import cn.anselyuki.common.model.business.Supplier;
import cn.anselyuki.common.vo.business.SupplierVO;
import cn.anselyuki.common.vo.system.PageVO;

import java.util.List;

/**
 * @author AnselYuki
 * @date 2022/9/16 17:18
 **/
public interface SupplierService {

    /**
     * 添加供应商
     *
     */
    Supplier add(SupplierVO supplierVO);

    /**
     * 供应商列表
     *
     */
    PageVO<SupplierVO> findSupplierList(Integer pageNum, Integer pageSize, SupplierVO supplierVO);

    /**
     * 编辑供应商
     *
     */
    SupplierVO edit(Long id);

    /**
     * 更新供应商
     *
     */
    void update(Long id, SupplierVO supplierVO);

    /**
     * 删除供应商
     *
     */
    void delete(Long id);

    /**
     * 查询所有供应商
     *
     */
    List<SupplierVO> findAll();

}
