package com.mk.bpmn.dao;

import java.util.List;

import com.mk.bpmn.entity.AuditHistory;

public interface BpmnDao {

	// ===============审核历史记录================

	/**
	 * 得到
	 * 
	 * @param processinstanceid
	 * @return
	 */
	List<AuditHistory> getAuditHistoryByProcessInstanceId(String processinstanceid);

	/**
	 * 保存
	 * 
	 * @param auditHistory
	 */
	void insertAuditHistory(AuditHistory auditHistory);

	/**
	 * 删除
	 * 
	 * @param processinstanceid
	 */
	void delAuditHistoryByProcessInstanceId(String processinstanceid);

}
