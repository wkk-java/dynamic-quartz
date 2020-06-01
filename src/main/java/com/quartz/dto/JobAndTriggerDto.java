package com.quartz.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JobAndTriggerDto implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String jobName;
	private String jobDescription;
	private String jobGroupName;
	private String jobClassName;
	private String triggerName;
	private String triggerGroupName;
	private String prevFireTime;
	private String nextFireTime;
	private String cronExpression;
	private String triggerState;
	private Object jobData;

	@Override
	public String toString() {
		return "JobAndTriggerDto [jobName=" + jobName + ", jobDescription=" + jobDescription + ", jobGroupName="
				+ jobGroupName + ", jobClassName=" + jobClassName + ", triggerName=" + triggerName
				+ ", triggerGroupName=" + triggerGroupName + ", prevFireTime=" + prevFireTime + ", nextFireTime="
				+ nextFireTime + ", cronExpression=" + cronExpression + ", triggerState=" + triggerState + ", jobData="
				+ jobData + "]";
	}

}
