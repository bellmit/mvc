package com.thd.base.common.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.thd.base.common.model.Attachment;
import com.thd.base.dao.BaseDao;
import com.thd.base.page.PageData;
import com.thd.base.page.PageParam;
import com.thd.base.page.PageServiceTemplate;
import com.thd.system.model.Organization;

@Service
public class CommonService {
	@Resource
	private BaseDao baseDao;

	/**
	 * 获得投保机构集合
	 */
	@SuppressWarnings("unchecked")
	public List<Organization> getInsuranceOrg() {
		List<Organization> resultList = new ArrayList<Organization>();
		DetachedCriteria dc = DetachedCriteria.forClass(Organization.class);
		dc.add(Restrictions.eq("enableFlg", Boolean.TRUE));
		dc.add(Restrictions.isNull("parentOrganization"));
		dc.addOrder(Order.asc("orgName"));
		List<Organization> orgList = baseDao.findByCriteria(dc);
		if (orgList != null && orgList.size() > 0) {
			for (Organization org : orgList) {
				for (Organization childOrg : org.getChildOrgs()) {
					Organization organization = new Organization();
					organization.setId(childOrg.getId());
					organization.setOrgName(childOrg.getOrgName());
					resultList.add(organization);
				}
			}
		}
		return resultList;
	}

	/**
	 * 保存附件
	 * @param attachment
	 */
	public void saveAttachment(Attachment attachment) {
		baseDao.saveOrUpdate(attachment);
	}

	/**
	 * 查看附件
	 */
	@SuppressWarnings("unchecked")
	public List<Attachment> getAttachmentList() {
		String hql = "from Attachment where enableFlg = ?";
		List<Attachment> resultList = baseDao.find(hql, new Object[] { Boolean.TRUE });
		return resultList;
	}

	/**
	 * 根据id查询附件
	 * @param id
	 * @return
	 */
	public Attachment getAttachmentById(String id) {
		Attachment attachment = baseDao.load(Attachment.class, id);
		baseDao.getHibernateTemplate().initialize(attachment);
		return attachment;
	}

	/**
	 * 删除附件
	 * @param id
	 * @return
	 */
	public void deleteAttachment(String id) {
		Attachment attachment = baseDao.load(Attachment.class, id);
		baseDao.delete(attachment);
	}

	/**
	 * 分页查询附件列表
	 */
	public PageData<Attachment> queryPageAttachmentList(PageParam pageParam, Attachment criteria) {
		PageServiceTemplate<Attachment, Attachment> template = new PageServiceTemplate<Attachment, Attachment>(baseDao) {
			@Override
			protected DetachedCriteria constructCriteria(Attachment criteria) {
				DetachedCriteria dc = DetachedCriteria.forClass(Attachment.class);
				return dc;
			}
		};

		PageData<Attachment> queryResult = template.doPagingQuery(pageParam, criteria);
		List<Attachment> attachmentList = new ArrayList<Attachment>();
		for (Attachment attachmentPo : queryResult.getRows()) {
			Attachment attachment = new Attachment();
			attachment.setId(attachmentPo.getId());
			attachment.setAttachmentPath(attachmentPo.getAttachmentPath());
			attachment.setOriginalFileName(attachmentPo.getOriginalFileName());
			attachmentList.add(attachment);
		}
		queryResult.setRows(attachmentList);
		return queryResult;
	}
}
