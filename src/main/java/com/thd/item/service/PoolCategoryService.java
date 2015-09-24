package com.thd.item.service;

import com.thd.base.dao.BaseDao;
import com.thd.item.model.PoolCategory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigInteger;

/**
 * 题库类别管理持久化层，处理题库列表的增、删、改、查操作
 *
 * @author TaoHuaDeng
 * @version 1.0
 * @since 2015-1-24 15:18:54
 */
@Service("categoryService")
public class PoolCategoryService {
    private static final Log LOG = LogFactory.getLog(PoolCategoryService.class);

    @Resource
    private BaseDao baseDao;

    /**
     * 该方法主要是用于保存题库类别实体
     *
     * @param category 类别实体
     * @return 保存后的主键
     * @since 2015-1-24 15:24:46
     */
    @Transactional(readOnly = false, isolation = Isolation.READ_COMMITTED)
    public String saveCategory(PoolCategory category) {
        if (category == null) {
            throw new IllegalArgumentException("Category is empty!");
        }

        Double maxShowOrder = getMaxShowOrder(category.getParentCategoryId());
        maxShowOrder = maxShowOrder == null ? 0 : maxShowOrder;
        category.setShowOrder(maxShowOrder + 1);

        return baseDao.save(category);
    }

    @Transactional
    public void checkCategory() {
        String hql = "SELECT COUNT(*) FROM PoolCategory";
        Long count = baseDao.countByHql(hql, null);

        if (count > 0) {
            return;
        }

        PoolCategory category = new PoolCategory();
        category.setShowOrder(1);
        category.setCategoryName("题库根类别");
        category.setIdPath("*");
        category.setParentCategoryId(null);
        saveCategory(category);
    }

    private Double getMaxShowOrder(String parentCategoryId) {
        String hql = "SELECT MAX(showOrder) FROM PoolCategory WHERE parentCategoryId=" + parentCategoryId;
        return baseDao.countByHql(hql, null);
    }
}
