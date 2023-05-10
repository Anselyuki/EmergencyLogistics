package cn.anselyuki.business.service.imp;

import cn.anselyuki.business.converter.ProductConverter;
import cn.anselyuki.business.mapper.ProductMapper;
import cn.anselyuki.business.mapper.ProductStockMapper;
import cn.anselyuki.business.service.ProductService;
import cn.anselyuki.common.error.BusinessCodeEnum;
import cn.anselyuki.common.error.BusinessException;
import cn.anselyuki.common.model.business.Product;
import cn.anselyuki.common.vo.business.ProductStockVO;
import cn.anselyuki.common.vo.business.ProductVO;
import cn.anselyuki.common.vo.system.PageVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author AnselYuki
 * @date 2022/9/16 17:19
 **/
@Service
@Transactional(rollbackFor = {Exception.class})
public class ProductServiceImpl implements ProductService {

    private final ProductMapper productMapper;

    private final ProductStockMapper productStockMapper;

    @Autowired
    public ProductServiceImpl(ProductMapper productMapper, ProductStockMapper productStockMapper) {
        this.productMapper = productMapper;
        this.productStockMapper = productStockMapper;
    }

    /**
     * 商品列表
     *
     */
    @Override
    public PageVO<ProductVO> findProductList(Integer pageNum, Integer pageSize, ProductVO productVO) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> products;
        Example o = new Example(Product.class);
        Example.Criteria criteria = o.createCriteria();
        if (productVO.getStatus() != null) {
            criteria.andEqualTo("status", productVO.getStatus());
        }
        if (productVO.getThreeCategoryId() != null) {
            criteria.andEqualTo("oneCategoryId", productVO.getOneCategoryId())
                    .andEqualTo("twoCategoryId", productVO.getTwoCategoryId())
                    .andEqualTo("threeCategoryId", productVO.getThreeCategoryId());
            products = productMapper.selectByExample(o);
            List<ProductVO> categoryVOS = ProductConverter.converterToVOList(products);
            PageInfo<Product> info = new PageInfo<>(products);
            return new PageVO<>(info.getTotal(), categoryVOS);
        }
        if (productVO.getTwoCategoryId() != null) {
            criteria.andEqualTo("oneCategoryId", productVO.getOneCategoryId())
                    .andEqualTo("twoCategoryId", productVO.getTwoCategoryId());
            products = productMapper.selectByExample(o);
            List<ProductVO> categoryVOS = ProductConverter.converterToVOList(products);
            PageInfo<Product> info = new PageInfo<>(products);
            return new PageVO<>(info.getTotal(), categoryVOS);
        }
        if (productVO.getOneCategoryId() != null) {
            criteria.andEqualTo("oneCategoryId", productVO.getOneCategoryId());
            products = productMapper.selectByExample(o);
            List<ProductVO> categoryVOS = ProductConverter.converterToVOList(products);
            PageInfo<Product> info = new PageInfo<>(products);
            return new PageVO<>(info.getTotal(), categoryVOS);
        }
        o.setOrderByClause("sort asc");
        if (productVO.getName() != null && !"".equals(productVO.getName())) {
            criteria.andLike("name", "%" + productVO.getName() + "%");
        }

        products = productMapper.selectByExample(o);
        List<ProductVO> categoryVOS = ProductConverter.converterToVOList(products);
        PageInfo<Product> info = new PageInfo<>(products);
        return new PageVO<>(info.getTotal(), categoryVOS);
    }

    /**
     * 添加商品
     *
     */
    @Override
    public void add(ProductVO ProductVO) {
        Product product = new Product();
        BeanUtils.copyProperties(ProductVO, product);
        product.setCreateTime(new Date());
        product.setModifiedTime(new Date());
        @NotNull(message = "分类不能为空")
        Long[] categoryKeys = ProductVO.getCategoryKeys();
        if (categoryKeys.length == 3) {
            product.setOneCategoryId(categoryKeys[0]);
            product.setTwoCategoryId(categoryKeys[1]);
            product.setThreeCategoryId(categoryKeys[2]);
        }
        product.setStatus(2);// 未审核
        product.setPNum(UUID.randomUUID().toString().substring(0, 32));
        productMapper.insert(product);
    }

    /**
     * 编辑商品
     *
     */
    @Override
    public ProductVO edit(Long id) {
        Product product = productMapper.selectByPrimaryKey(id);
        return ProductConverter.converterToProductVO(product);
    }

    /**
     * 更新商品
     *
     */
    @Override
    public void update(Long id, ProductVO ProductVO) {
        Product product = new Product();
        BeanUtils.copyProperties(ProductVO, product);
        product.setModifiedTime(new Date());
        @NotNull(message = "分类不能为空")
        Long[] categoryKeys = ProductVO.getCategoryKeys();
        if (categoryKeys.length == 3) {
            product.setOneCategoryId(categoryKeys[0]);
            product.setTwoCategoryId(categoryKeys[1]);
            product.setThreeCategoryId(categoryKeys[2]);
        }
        productMapper.updateByPrimaryKey(product);
    }

    /**
     * 删除商品
     *
     */
    @Override
    public void delete(Long id) throws BusinessException {
        Product t = new Product();
        t.setId(id);
        Product product = productMapper.selectByPrimaryKey(t);
        // 只有物资处于回收站,或者待审核的情况下可删除
        if (product.getStatus() != 1 && product.getStatus() != 2) {
            throw new BusinessException(BusinessCodeEnum.PRODUCT_STATUS_ERROR);
        } else {
            productMapper.deleteByPrimaryKey(id);
        }
    }

    /**
     * 物资库存列表
     *
     */
    @Override
    public PageVO<ProductStockVO> findProductStocks(Integer pageNum, Integer pageSize, ProductVO productVO) {
        PageHelper.startPage(pageNum, pageSize);
        List<ProductStockVO> productStockVOList = productStockMapper.findProductStocks(productVO);
        PageInfo<ProductStockVO> info = new PageInfo<>(productStockVOList);
        return new PageVO<>(info.getTotal(), productStockVOList);
    }

    /**
     * 所有库存信息
     *
     */
    @Override
    public List<ProductStockVO> findAllStocks(Integer pageNum, Integer pageSize, ProductVO productVO) {
        PageHelper.startPage(pageNum, pageSize);
        return productStockMapper.findAllStocks(productVO);
    }

    /**
     * 移入回收站
     *
     */
    @Override
    public void remove(Long id) throws BusinessException {
        Product t = new Product();
        t.setId(id);
        Product product = productMapper.selectByPrimaryKey(t);
        if (product.getStatus() != 0) {
            throw new BusinessException(BusinessCodeEnum.PRODUCT_STATUS_ERROR);
        } else {
            t.setStatus(1);
            productMapper.updateByPrimaryKeySelective(t);
        }
    }

    /**
     * 从回收站恢复数据
     *
     */
    @Override
    public void back(Long id) throws BusinessException {
        Product t = new Product();
        t.setId(id);
        Product product = productMapper.selectByPrimaryKey(t);
        if (product.getStatus() != 1) {
            throw new BusinessException(BusinessCodeEnum.PRODUCT_STATUS_ERROR);
        } else {
            t.setStatus(0);
            productMapper.updateByPrimaryKeySelective(t);
        }
    }

    /**
     * 物资审核
     *
     */
    @Override
    public void publish(Long id) throws BusinessException {
        Product t = new Product();
        t.setId(id);
        Product product = productMapper.selectByPrimaryKey(t);
        if (product.getStatus() != 2) {
            throw new BusinessException(BusinessCodeEnum.PRODUCT_STATUS_ERROR);
        } else {
            t.setStatus(0);
            productMapper.updateByPrimaryKeySelective(t);
        }
    }

}
