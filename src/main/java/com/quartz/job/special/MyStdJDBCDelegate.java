package com.quartz.job.special;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.quartz.JobDetail;
import org.quartz.impl.jdbcjobstore.StdJDBCDelegate;

public class MyStdJDBCDelegate extends StdJDBCDelegate {

	private final String UPDATE_JOB_DETAIL_SQL = "UPDATE " + TABLE_PREFIX_SUBST + TABLE_JOB_DETAILS + " SET "
			+ COL_DESCRIPTION + " = ?, " + COL_JOB_CLASS + " = ?, " + COL_IS_DURABLE + " = ?, " + COL_IS_NONCONCURRENT
			+ " = ?, " + COL_IS_UPDATE_DATA + " = ?, " + COL_REQUESTS_RECOVERY + " = ?, " + COL_JOB_DATAMAP + " = ? "
			+ " WHERE " + COL_SCHEDULER_NAME + " = " + SCHED_NAME_SUBST + " AND " + COL_JOB_NAME + " = ? AND "
			+ COL_JOB_GROUP + " = ?";

	private final String INSERT_JOB_DETAIL_SQL = "INSERT INTO " + TABLE_PREFIX_SUBST + TABLE_JOB_DETAILS + " ("
			+ COL_SCHEDULER_NAME + ", " + COL_JOB_NAME + ", " + COL_JOB_GROUP + ", " + COL_DESCRIPTION + ", "
			+ COL_JOB_CLASS + ", " + COL_IS_DURABLE + ", " + COL_IS_NONCONCURRENT + ", " + COL_IS_UPDATE_DATA + ", "
			+ COL_REQUESTS_RECOVERY + ", " + COL_JOB_DATAMAP + "," + MyConstants.ID + ") " + " VALUES("
			+ SCHED_NAME_SUBST + ", ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

	@Override
	public int updateJobDetail(Connection conn, JobDetail job) throws IOException, SQLException {
		ByteArrayOutputStream baos = serializeJobData(job.getJobDataMap());

		PreparedStatement ps = null;

		int insertResult = 0;

		try {
			ps = conn.prepareStatement(rtp(UPDATE_JOB_DETAIL_SQL));
			ps.setString(1, job.getDescription());
			ps.setString(2, job.getJobClass().getName());
			setBoolean(ps, 3, job.isDurable());
			setBoolean(ps, 4, job.isConcurrentExectionDisallowed());
			setBoolean(ps, 5, job.isPersistJobDataAfterExecution());
			setBoolean(ps, 6, job.requestsRecovery());
			setBytes(ps, 7, baos);
			ps.setString(8, job.getKey().getName());
			ps.setString(9, job.getKey().getGroup());

			insertResult = ps.executeUpdate();
		} finally {
			closeStatement(ps);
		}

		return insertResult;
	}

	@Override
	public int insertJobDetail(Connection conn, JobDetail job) throws IOException, SQLException {
		ByteArrayOutputStream baos = serializeJobData(job.getJobDataMap());

		PreparedStatement ps = null;

		int insertResult = 0;

		try {
			ps = conn.prepareStatement(rtp(INSERT_JOB_DETAIL_SQL));
			ps.setString(1, job.getKey().getName());
			ps.setString(2, job.getKey().getGroup());
			ps.setString(3, job.getDescription());
			ps.setString(4, job.getJobClass().getName());
			setBoolean(ps, 5, job.isDurable());
			setBoolean(ps, 6, job.isConcurrentExectionDisallowed());
			setBoolean(ps, 7, job.isPersistJobDataAfterExecution());
			setBoolean(ps, 8, job.requestsRecovery());
			setBytes(ps, 9, baos);
			ps.setString(10, ((MyJobDetail) job).getId());

			insertResult = ps.executeUpdate();
		} finally {
			closeStatement(ps);
		}

		return insertResult;
	}
}
