package com.thd.report.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.thd.base.dao.BaseDao;
import com.thd.report.vo.ReportOperatorVo;
import com.thd.system.model.Operator;
import com.thd.system.service.SystemService;

@Service
public class ReportService {
	@Resource
	BaseDao baseDao;
	@Resource
	SystemService systemService;

	@SuppressWarnings("unchecked")
	public List<ReportOperatorVo> getOperatorList() {
		//StringBuffer sb = new StringBuffer();
		List<Operator> list = systemService.getOperList();
		List<ReportOperatorVo> report = new ArrayList();
		if (list.size() > 0) {
			for (Operator op : list) {
				ReportOperatorVo reportOperatorVo = new ReportOperatorVo();
				reportOperatorVo.setName(op.getOperName());
				reportOperatorVo.setEmil(op.getOperMail());
				report.add(reportOperatorVo);
			}
		}
		return report;
	}

	public List<Operator> getOperatorSexList() {
		List<Operator> list = systemService.getOperList();
		return list;
	}

}
