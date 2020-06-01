package com.quartz.job.special;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Trigger;
import org.quartz.utils.Key;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MyJobBuilder extends JobBuilder {

	private String id;
	private String products;
	private String customers;
	private JobKey key;
	private String description;
	private Class<? extends Job> jobClass;
	private boolean durability;
	private boolean shouldRecover;

	private JobDataMap jobDataMap = new JobDataMap();

	protected MyJobBuilder() {
	}

	/**
	 * Create a JobBuilder with which to define a <code>JobDetail</code>.
	 * 
	 * @return a new JobBuilder
	 */
	public static MyJobBuilder newJob() {
		return new MyJobBuilder();
	}

	/**
	 * Create a JobBuilder with which to define a <code>JobDetail</code>, and set
	 * the class name of the <code>Job</code> to be executed.
	 * 
	 * @return a new JobBuilder
	 */
	public static MyJobBuilder newJob(Class<? extends Job> jobClass) {
		MyJobBuilder b = new MyJobBuilder();
		b.ofType(jobClass);
		return b;
	}

	/**
	 * Produce the <code>JobDetail</code> instance defined by this
	 * <code>JobBuilder</code>.
	 * 
	 * @return the defined JobDetail.
	 */
	public MyJobDetail build() {

		MyJobDetail job = new MyJobDetail();
		job.setId(id);
		job.setCustomers(customers);
		job.setProducts(products);
		job.setJobClass(jobClass);
		job.setDescription(description);
		if (key == null)
			key = new JobKey(Key.createUniqueName(null), null);
		job.setKey(key);
		job.setDurability(durability);
		job.setRequestsRecovery(shouldRecover);

		if (!jobDataMap.isEmpty())
			job.setJobDataMap(jobDataMap);

		return job;
	}

	/**
	 * Use a <code>JobKey</code> with the given name and default group to identify
	 * the JobDetail.
	 * 
	 * <p>
	 * If none of the 'withIdentity' methods are set on the JobBuilder, then a
	 * random, unique JobKey will be generated.
	 * </p>
	 * 
	 * @param name the name element for the Job's JobKey
	 * @return the updated JobBuilder
	 * @see JobKey
	 * @see JobDetail#getKey()
	 */
	public JobBuilder withIdentity(String name) {
		key = new JobKey(name, null);
		return this;
	}

	/**
	 * Use a <code>JobKey</code> with the given name and group to identify the
	 * JobDetail.
	 * 
	 * <p>
	 * If none of the 'withIdentity' methods are set on the JobBuilder, then a
	 * random, unique JobKey will be generated.
	 * </p>
	 * 
	 * @param name  the name element for the Job's JobKey
	 * @param group the group element for the Job's JobKey
	 * @return the updated JobBuilder
	 * @see JobKey
	 * @see JobDetail#getKey()
	 */
	public MyJobBuilder withIdentity(String name, String group) {
		key = new JobKey(name, group);
		return this;
	}

	/**
	 * Use a <code>JobKey</code> to identify the JobDetail.
	 * 
	 * <p>
	 * If none of the 'withIdentity' methods are set on the JobBuilder, then a
	 * random, unique JobKey will be generated.
	 * </p>
	 * 
	 * @param jobKey the Job's JobKey
	 * @return the updated JobBuilder
	 * @see JobKey
	 * @see JobDetail#getKey()
	 */
	public JobBuilder withIdentity(JobKey jobKey) {
		this.key = jobKey;
		return this;
	}

	/**
	 * Set the given (human-meaningful) description of the Job.
	 * 
	 * @param jobDescription the description for the Job
	 * @return the updated JobBuilder
	 * @see JobDetail#getDescription()
	 */
	public JobBuilder withDescription(String jobDescription) {
		this.description = jobDescription;
		return this;
	}

	/**
	 * Set the class which will be instantiated and executed when a Trigger fires
	 * that is associated with this JobDetail.
	 * 
	 * @param jobClazz a class implementing the Job interface.
	 * @return the updated JobBuilder
	 * @see JobDetail#getJobClass()
	 */
	public JobBuilder ofType(Class<? extends Job> jobClazz) {
		this.jobClass = jobClazz;
		return this;
	}

	/**
	 * Instructs the <code>Scheduler</code> whether or not the <code>Job</code>
	 * should be re-executed if a 'recovery' or 'fail-over' situation is
	 * encountered.
	 * 
	 * <p>
	 * If not explicitly set, the default value is <code>false</code>.
	 * </p>
	 * 
	 * @return the updated JobBuilder
	 * @see JobDetail#requestsRecovery()
	 */
	public JobBuilder requestRecovery() {
		this.shouldRecover = true;
		return this;
	}

	/**
	 * Instructs the <code>Scheduler</code> whether or not the <code>Job</code>
	 * should be re-executed if a 'recovery' or 'fail-over' situation is
	 * encountered.
	 * 
	 * <p>
	 * If not explicitly set, the default value is <code>false</code>.
	 * </p>
	 * 
	 * @param jobShouldRecover the desired setting
	 * @return the updated JobBuilder
	 */
	public JobBuilder requestRecovery(boolean jobShouldRecover) {
		this.shouldRecover = jobShouldRecover;
		return this;
	}

	/**
	 * Whether or not the <code>Job</code> should remain stored after it is orphaned
	 * (no <code>{@link Trigger}s</code> point to it).
	 * 
	 * <p>
	 * If not explicitly set, the default value is <code>false</code> - this method
	 * sets the value to <code>true</code>.
	 * </p>
	 * 
	 * @return the updated JobBuilder
	 * @see JobDetail#isDurable()
	 */
	public JobBuilder storeDurably() {
		this.durability = true;
		return this;
	}

	/**
	 * Whether or not the <code>Job</code> should remain stored after it is orphaned
	 * (no <code>{@link Trigger}s</code> point to it).
	 * 
	 * <p>
	 * If not explicitly set, the default value is <code>false</code>.
	 * </p>
	 * 
	 * @param jobDurability the value to set for the durability property.
	 * @return the updated JobBuilder
	 * @see JobDetail#isDurable()
	 */
	public JobBuilder storeDurably(boolean jobDurability) {
		this.durability = jobDurability;
		return this;
	}

	/**
	 * Add the given key-value pair to the JobDetail's {@link JobDataMap}.
	 * 
	 * @return the updated JobBuilder
	 * @see JobDetail#getJobDataMap()
	 */
	public JobBuilder usingJobData(String dataKey, String value) {
		jobDataMap.put(dataKey, value);
		return this;
	}

	/**
	 * Add the given key-value pair to the JobDetail's {@link JobDataMap}.
	 * 
	 * @return the updated JobBuilder
	 * @see JobDetail#getJobDataMap()
	 */
	public JobBuilder usingJobData(String dataKey, Integer value) {
		jobDataMap.put(dataKey, value);
		return this;
	}

	/**
	 * Add the given key-value pair to the JobDetail's {@link JobDataMap}.
	 * 
	 * @return the updated JobBuilder
	 * @see JobDetail#getJobDataMap()
	 */
	public JobBuilder usingJobData(String dataKey, Long value) {
		jobDataMap.put(dataKey, value);
		return this;
	}

	/**
	 * Add the given key-value pair to the JobDetail's {@link JobDataMap}.
	 * 
	 * @return the updated JobBuilder
	 * @see JobDetail#getJobDataMap()
	 */
	public JobBuilder usingJobData(String dataKey, Float value) {
		jobDataMap.put(dataKey, value);
		return this;
	}

	/**
	 * Add the given key-value pair to the JobDetail's {@link JobDataMap}.
	 * 
	 * @return the updated JobBuilder
	 * @see JobDetail#getJobDataMap()
	 */
	public JobBuilder usingJobData(String dataKey, Double value) {
		jobDataMap.put(dataKey, value);
		return this;
	}

	/**
	 * Add the given key-value pair to the JobDetail's {@link JobDataMap}.
	 * 
	 * @return the updated JobBuilder
	 * @see JobDetail#getJobDataMap()
	 */
	public JobBuilder usingJobData(String dataKey, Boolean value) {
		jobDataMap.put(dataKey, value);
		return this;
	}

	/**
	 * Add all the data from the given {@link JobDataMap} to the {@code JobDetail}'s
	 * {@code JobDataMap}.
	 * 
	 * @return the updated JobBuilder
	 * @see JobDetail#getJobDataMap()
	 */
	public JobBuilder usingJobData(JobDataMap newJobDataMap) {
		jobDataMap.putAll(newJobDataMap);
		return this;
	}

	/**
	 * Replace the {@code JobDetail}'s {@link JobDataMap} with the given
	 * {@code JobDataMap}.
	 * 
	 * @return the updated JobBuilder
	 * @see JobDetail#getJobDataMap()
	 */
	public JobBuilder setJobData(JobDataMap newJobDataMap) {
		jobDataMap = newJobDataMap;
		return this;
	}
}
