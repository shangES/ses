package com.mk.recruitprogram.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.mk.framework.grid.util.StringUtils;
import com.mk.recruitprogram.entity.RecruitProgramAudit;

public class RecruitProgramAuditRemoteDao extends JdbcDaoSupport {

	/**
	 * 同步OA信息
	 * 
	 * @return
	 */
	public List<RecruitProgramAudit> getRemoteRecruitProgramAuditRemote() {
		StringBuffer sql = new StringBuffer();
		sql.append("select t1.ID, t1.DEPATRMENT1 ,t1.DEPATRMENT2 ,t1.GW,t1.sqdate,t1.qwdgsj, t1.SQRS, t1.ZJ1,t1.SQRBH, t2.REQUESTID from ecology.formtable_main_163 t1, ecology.workflow_requestbase t2 where t1.requestid=t2.requestid and t2.currentnodetype=3");
		//sql.append("select t1.ID, t1.DEPATRMENT1 ,t1.DEPATRMENT2 ,t1.GW, t1.SQRS, t1.ZJ1,t1.SQRBH, t2.REQUESTID from ecology.formtable_main_163 t1, ecology.workflow_requestbase t2 where t1.requestid=t2.requestid and t2.currentnodetype=3 and t1.SQRBH is not null");
		final List<RecruitProgramAudit> list = new ArrayList<RecruitProgramAudit>();
		this.getJdbcTemplate().query(sql.toString(), new RowMapper<RecruitProgramAudit>() {
			public RecruitProgramAudit mapRow(ResultSet rst, int rowNumber) throws SQLException {
				while (rst.next()) {
					RecruitProgramAudit model = new RecruitProgramAudit();
					model.setDeptname(rst.getString("DEPATRMENT1").replaceAll(" ", ""));
					model.setPostname(rst.getString("GW").replaceAll(" ", ""));
					model.setPostnum(Integer.parseInt(rst.getString("SQRS")));
					if(rst.getDate("sqdate")!=null){
						model.setStartdate(rst.getDate("sqdate"));
					}
					if(rst.getDate("qwdgsj")!=null){
						System.out.println(rst.getDate("qwdgsj"));
						model.setHillockdate(rst.getDate("qwdgsj"));
					}
					if(StringUtils.isNotEmpty(rst.getString("ZJ1"))){
						model.setRankname(rst.getString("ZJ1").replaceAll(" ", ""));
					}
					//model.setRankname(rst.getString("ZJ1").replaceAll(" ", ""));
					model.setInterfacecode(rst.getString("REQUESTID").replaceAll(" ", ""));
					model.setRemainnum(Integer.parseInt(rst.getString("SQRS")));
					if(StringUtils.isNotEmpty(rst.getString("SQRBH"))){
						model.setWordcode(rst.getString("SQRBH").replaceAll(" ", ""));
					}
					model.setRecruitprogramauditguid(rst.getString("ID"));
					list.add(model);
				}
				return null;
			}
		});
		return list;
	}


}
