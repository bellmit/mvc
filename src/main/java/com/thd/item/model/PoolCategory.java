package com.thd.item.model;

import com.thd.base.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 题库类别实体
 *
 * @author TaoHuaDeng
 * @version 1.0
 * @since 2015-1-24 15:15:59
 */
@Entity
public class PoolCategory extends BaseModel {

    /**
     * 类别名称
     */
    @Column(length = 50, nullable = false)
    private String categoryName;

    /**
     * 父类别名称
     */
    @Column
    private String parentCategoryId;

    /**
     * 路径id
     */
    @Column
    private String idPath;

    /**
     * 排序
     */
    @Column
    private double showOrder;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(String parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }

    public String getIdPath() {
        return idPath;
    }

    public void setIdPath(String idPath) {
        this.idPath = idPath;
    }

    public double getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(double showOrder) {
        this.showOrder = showOrder;
    }
}
