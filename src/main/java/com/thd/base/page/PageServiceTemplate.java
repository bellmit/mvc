package com.thd.base.page;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import com.thd.base.dao.BaseDao;
import com.thd.base.page.PageParam.OrderDir;

/**
 * @param <PT> 查询条件泛型
 * @param <RT> 分页返回值泛型
 */
public abstract class PageServiceTemplate<PT, RT> {
	private final BaseDao baseDao;

	/**
	 * @param <PT> 查询条件泛型
	 * @param <RT> 分页返回值泛型
	 */
	public PageServiceTemplate(BaseDao baseDao) {
		super();
		this.baseDao = baseDao;
	}

	/**
	 * 分页查询模板类，
	 * @param pageParam 翻页、排序参数model
	 * @param criteria 泛型查询条件
	 * 默认添加dc.add(Restrictions.eq("enableFlg", true))以便只查询有效数据。
	 * @return 泛型分页返回值，包含总记录数和当前页List数据
	 */
	@SuppressWarnings("unchecked")
	public PageData<RT> doPagingQuery(PageParam pageParam, PT criteria) {
		DetachedCriteria dc = constructCriteria(criteria);
		//dc.add(Restrictions.eq("enableFlg", true));
		int total = calculateTotalReords(dc);
		PageData<RT> result = new PageData<RT>();
		result.setTotal(total);
		if (pageParam.getSortBy() != null && pageParam.getSortDir() != null) {
			if (pageParam.getSortDir() == OrderDir.asc) {
				dc.addOrder(Order.asc(pageParam.getSortBy()));
			} else {
				dc.addOrder(Order.desc(pageParam.getSortBy()));
			}
		} else {// 不指定排序，默认按id降序
			dc.addOrder(Order.desc("id"));
		}
		dc.setProjection(null);
		result.setRows(baseDao.findByCriteria(dc, pageParam.getStart(),
				pageParam.getRows()));
		return result;
	}

	/**
	 * 供子类OverWrite，根据传入的查询条件构造Hibernate DetachedCriteria
	 * @param criteria 泛型查询条件model
	 */
	abstract protected DetachedCriteria constructCriteria(PT criteria);

	/**
	 * 默认按主键id字段统计，如果主键不叫id，需overwrite此方法
	 */
	protected int calculateTotalReords(DetachedCriteria dc) {
		return baseDao.countRecordsNumber(dc, "id");
	}

}