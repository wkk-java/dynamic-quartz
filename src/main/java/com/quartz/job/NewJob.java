package com.quartz.job;

import java.util.Date;

import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewJob implements BaseJob {

	private static Logger _log = LoggerFactory.getLogger(NewJob.class);

	public NewJob() {

	}

	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDetail jobDeatail = context.getJobDetail();
		JobKey key = jobDeatail.getKey();
		_log.info("class:" + key.getName() + ",jobGroup:" + key.getGroup() + ",执行时间: " + new Date());
	}
}