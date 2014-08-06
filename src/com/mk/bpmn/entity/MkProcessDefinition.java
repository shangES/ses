package com.mk.bpmn.entity;

import java.util.Date;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;

import com.mk.framework.grid.util.DateUtil;

public class MkProcessDefinition {

	private String id;
	private String deploymentid;
	private String name;
	private String key;
	private Integer version;
	private String resourcename;
	private String diagramresourcename;
	private Date deploymenttime;
	private boolean issuspended;

	// 临时
	private String deploymenttimename;

	public MkProcessDefinition() {

	}

	public MkProcessDefinition(ProcessDefinition processDefinition, Deployment deployment) {
		this.setId(processDefinition.getId());
		this.setDeploymentid(processDefinition.getDeploymentId());
		this.setName(processDefinition.getName());
		this.setKey(processDefinition.getKey());
		this.setVersion(processDefinition.getVersion());
		this.setResourcename(processDefinition.getResourceName());
		this.setDiagramresourcename(processDefinition.getDiagramResourceName());
		this.setDeploymenttime(deployment.getDeploymentTime());
		this.setIssuspended(processDefinition.isSuspended());
	}

	public String getDeploymenttimename() {
		if (deploymenttime != null)
			return DateUtil.formatDateYMDHHmmSS(deploymenttime);
		return deploymenttimename;
	}

	public void setDeploymenttimename(String deploymenttimename) {
		this.deploymenttimename = deploymenttimename;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeploymentid() {
		return deploymentid;
	}

	public void setDeploymentid(String deploymentid) {
		this.deploymentid = deploymentid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getResourcename() {
		return resourcename;
	}

	public void setResourcename(String resourcename) {
		this.resourcename = resourcename;
	}

	public String getDiagramresourcename() {
		return diagramresourcename;
	}

	public void setDiagramresourcename(String diagramresourcename) {
		this.diagramresourcename = diagramresourcename;
	}

	public Date getDeploymenttime() {
		return deploymenttime;
	}

	public void setDeploymenttime(Date deploymenttime) {
		this.deploymenttime = deploymenttime;
	}

	public boolean isIssuspended() {
		return issuspended;
	}

	public void setIssuspended(boolean issuspended) {
		this.issuspended = issuspended;
	}

}
