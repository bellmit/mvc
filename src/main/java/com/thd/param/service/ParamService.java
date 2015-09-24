package com.thd.param.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.thd.base.dao.BaseDao;
import com.thd.base.page.PageData;
import com.thd.base.page.PageParam;
import com.thd.base.page.PageServiceTemplate;
import com.thd.base.util.LoggerUtil;
import com.thd.param.model.Param;
import com.thd.param.model.Supplier;
import com.thd.param.model.Param.ParamType;
import com.thd.system.service.SystemService;

@Service
@SuppressWarnings("unchecked")
public class ParamService {

	Logger logger = LoggerUtil.getLogger();

	@Resource
	private BaseDao baseDao;

	@Resource
	private SystemService systemService;

	/**
	 * 新增参数
	 * @param Param
	 */
	public Param saveParam(Param p) {
		Param param = new Param();
		param.setValue(p.getValue());
		param.setText(p.getText());
		param.setType(p.getType());
		param.setSortNo(p.getSortNo());
		if (p.getParentParam() != null && p.getParentParam().getId() != null) {
			Param parentParam = baseDao.load(Param.class, p.getParentParam().getId());
			parentParam.addChildParam(param);
		}
		baseDao.save(param);
		return param;
	}

	/**
	 * 修改参数
	 * @param Param
	 */
	public Param updateParam(Param p) {
		Param param = baseDao.load(Param.class, p.getId());
		param.setValue(p.getValue());
		param.setText(p.getText());
		param.setSortNo(p.getSortNo());
		if (p.getParentParam() != null && p.getParentParam().getId() != null) {
			Param parentParam = baseDao.load(Param.class, p.getParentParam().getId());
			if (param.getParentParam() != null) {
				param.getParentParam().getChildParams().remove(param);
			}
			parentParam.addChildParam(param);
			param.setParentParam(parentParam);
		} else {
			if (param.getParentParam() != null) {
				param.getParentParam().getChildParams().remove(param);
				param.setParentParam(null);
			}
		}
		baseDao.saveOrUpdate(param);
		return param;
	}

	/**
	 * 删除参数
	 * @param Long
	 */
	public void deleteParam(Long id) {
		Param param = baseDao.load(Param.class, id);
		param.setEnableFlg(false);
		param.clear();
		baseDao.saveOrUpdate(param);
	}

	/**
	 * 根据角色ID，查询出参数信息
	 * @param Long
	 */
	public Param getParamById(Long id) {
		Param param = baseDao.load(Param.class, id);
		baseDao.getHibernateTemplate().initialize(param);
		baseDao.getHibernateTemplate().initialize(param.getParentParam());
		baseDao.getHibernateTemplate().initialize(param.getChildParams());
		return param;
	}

	/**
	 *  获取某一类型的所有参数
	 */
	public List<Param> getParamList(ParamType type, Boolean isOnlyHeadPoint) {
		DetachedCriteria dc = DetachedCriteria.forClass(Param.class);
		dc.add(Restrictions.eq("enableFlg", Boolean.TRUE));
		dc.add(Restrictions.eq("type", type));
		if (isOnlyHeadPoint) {
			dc.add(Restrictions.isNull("parentParam"));
		}
		dc.addOrder(Order.asc("sortNo"));
		List<Param> paramList = baseDao.findByCriteria(dc);

		return paramList;
	}

	/**
	 * 新增供应商
	 */
	public Supplier saveSupplier(Supplier supplier) {
		baseDao.save(supplier);
		return supplier;
	}

	/**
	 * 修改供应商信息
	 */
	public Supplier updateSupplier(Supplier sup) {
		Supplier supplier = baseDao.load(Supplier.class, sup.getId());
		supplier.setSupCode(sup.getSupCode());
		supplier.setSupClassify(sup.getSupClassify());
		supplier.setOrgId(sup.getOrgId());
		supplier.setSupName(sup.getSupName());
		supplier.setSupAddress(sup.getSupAddress());
		supplier.setSupPhone(sup.getSupPhone());
		supplier.setSupPostCode(sup.getSupPostCode());
		baseDao.saveOrUpdate(supplier);
		return supplier;
	}

	/**
	 * 删除供应商
	 * @param Long
	 */
	public void deleteSupplier(Long id) {
		Supplier sup = baseDao.load(Supplier.class, id);
		sup.setEnableFlg(false);

		baseDao.saveOrUpdate(sup);
	}

	/**
	 * 分页查询供应商列表信息
	 * @param pageParam
	 * @param criteria
	 * @return
	 */
	public PageData<Supplier> queryPageSupplierList(PageParam pageParam, Supplier criteria) {
		PageServiceTemplate<Supplier, Supplier> template = new PageServiceTemplate<Supplier, Supplier>(baseDao) {
			@Override
			protected DetachedCriteria constructCriteria(Supplier criteria) {
				DetachedCriteria dc = DetachedCriteria.forClass(Supplier.class);
				// dc.createAlias("organization", "org");
				if (StringUtils.isNotEmpty(criteria.getSupName())) {
					dc.add(Restrictions.ilike("supName", "%" + criteria.getSupName() + "%"));// 根据名称模糊查询
				}
				if (StringUtils.isNotEmpty(criteria.getSupCode())) {
					dc.add(Restrictions.ilike("supCode", "%" + criteria.getSupCode() + "%"));// 根据编号模糊查询
				}
				if (StringUtils.isNotEmpty(criteria.getSupAddress())) {
					dc.add(Restrictions.ilike("supAddress", "%" + criteria.getSupAddress() + "%"));// 根据地址模糊查询
				}
				if (StringUtils.isNotEmpty(criteria.getSupClassify())) {
					dc.add(Restrictions.eq("supClassify", criteria.getSupClassify()));// 根据供应商分类查询
				}
				if (criteria.getOrgId() != null) {
					dc.add(Restrictions.eq("orgId", criteria.getOrgId()));// 根据机构分类查询
				}

				return dc;
			}
		};

		PageData<Supplier> queryResult = template.doPagingQuery(pageParam, criteria);
		List<Supplier> supList = new ArrayList<Supplier>();
		if (queryResult != null && queryResult.getRows().size() > 0) {
			for (Supplier supPo : queryResult.getRows()) {
				Supplier sup = new Supplier();
				sup.setId(supPo.getId());
				sup.setSupCode(supPo.getSupCode());
				sup.setSupClassify(supPo.getSupClassify());
				sup.setOrgId(supPo.getOrgId());
				sup.setSupName(supPo.getSupName());
				sup.setSupAddress(supPo.getSupAddress());
				sup.setSupPhone(supPo.getSupPhone());
				sup.setSupPostCode(supPo.getSupPostCode());
				sup.setOrgText(systemService.getOrgById(supPo.getOrgId()).getOrgName());

				supList.add(sup);
			}
		}
		queryResult.setRows(supList);
		return queryResult;

	}

	/**
	 * 根据id查找Supplier的所有信息
	 * @param id
	 * @return
	 */
	public Supplier getSupplierById(Long id) {
		DetachedCriteria dc = DetachedCriteria.forClass(Supplier.class);
		// dc.add(Restrictions.eq("enableFlg", Boolean.TRUE));
		// dc.add(Restrictions.eq("id", id));
		dc.add(Restrictions.idEq(id));

		List<Supplier> resultlist = baseDao.findByCriteria(dc);
		if (resultlist != null && resultlist.size() == 1) {
			return resultlist.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 根据参数的类型和value返回参数list
	 * @param type
	 * @param value
	 * @return
	 */
	public List<Param> getListByValueAndType(ParamType type, String value) {
		DetachedCriteria dc = DetachedCriteria.forClass(Param.class);
		dc.add(Restrictions.eq("enableFlg", Boolean.TRUE));
		dc.add(Restrictions.eq("type", type));
		dc.add(Restrictions.eq("value", value));

		List<Param> resultlist = baseDao.findByCriteria(dc);

		return resultlist;
	}

}
