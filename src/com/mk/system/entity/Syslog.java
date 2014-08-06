package com.mk.system.entity;

import java.sql.Timestamp;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.mk.framework.context.ContextFacade;
import com.mk.framework.context.UserContext;
import com.mk.framework.custom.CustomDateSerializer;

public class Syslog {
	private String logguid;
	private String subject;
	private String source;
	private String target;
	private String description;
	private String modiuser;
	private Timestamp moditimestamp;
	private String modimemo;

	public Syslog() {

	}

	public Syslog(String rmk) {
		UserContext uc = ContextFacade.getUserContext();

		this.setSubject("操作日志");
		this.setDescription(rmk);
		this.setModiuser(uc.getUserId());
		this.setModitimestamp(new Timestamp(System.currentTimeMillis()));
	}

	public String getLogguid() {
		return logguid;
	}

	public void setLogguid(String logguid) {
		this.logguid = logguid;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getModiuser() {
		return modiuser;
	}

	public void setModiuser(String modiuser) {
		this.modiuser = modiuser;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Timestamp getModitimestamp() {
		return moditimestamp;
	}

	public void setModitimestamp(Timestamp moditimestamp) {
		this.moditimestamp = moditimestamp;
	}

	public String getModimemo() {
		return modimemo;
	}

	public void setModimemo(String modimemo) {
		this.modimemo = modimemo;
	}

	@Override
	public String toString() {
		return "Syslog [logguid=" + logguid + ", subject=" + subject + ", source=" + source + ", target=" + target + ", description=" + description + ", modiuser=" + modiuser + ", moditimestamp="
				+ moditimestamp + ", modimemo=" + modimemo + "]";
	}

}
