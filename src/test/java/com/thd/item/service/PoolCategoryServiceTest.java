package com.thd.item.service;

import com.thd.base.util.BaseTxWebTests;
import com.thd.item.model.PoolCategory;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Resource;

public class PoolCategoryServiceTest extends BaseTxWebTests {
    @Resource
    private PoolCategoryService categoryService;

    @Before
    public void beforeEveryTests() {
        super.setCurrentUserAsCaseHandler("admin");
    }

    @Test
    public void testSaveCategory() {
        PoolCategory category = new PoolCategory();
        category.setIdPath("*");
        category.setCategoryName("题库根类别");
        category.setShowOrder(1);
        categoryService.saveCategory(category);
    }

    @Test
    public void testCheckCategory() {
        categoryService.checkCategory();
    }
}
