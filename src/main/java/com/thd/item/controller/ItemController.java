package com.thd.item.controller;

import com.thd.item.model.PoolCategory;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * 试题管理控制层
 *
 * @author TaoHuaDeng
 * @version 1.0
 * @since 2015-1-24 15:42:40
 */
@Controller
@RequestMapping("/item/*")
public class ItemController {

    @RequestMapping("/toCategoryManager")
    public ModelAndView toCategoryManager() {
        return new ModelAndView("/item/categoryManager");
    }

    @RequestMapping("/toAddCategory")
    public ModelAndView toAddCategoryAdd() {
        return new ModelAndView("/item/addCategory");
    }

    /**
     * 验证类别名称
     */
    @RequestMapping("/checkCategoryName")
    @ResponseBody
    public ModelMap checkCategoryName(PoolCategory category) {
        String categoryName = category.getCategoryName();
        ModelMap map = new ModelMap();

        if (categoryName.length() < 2) {
            map.put("message", "类别长度不可小于2");
            map.put("success", "false");
            return map;
        }

        map.put("success", "true");
        return map;
    }
}
