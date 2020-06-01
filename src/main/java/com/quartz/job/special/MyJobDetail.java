package com.quartz.job.special;

import org.quartz.impl.JobDetailImpl;
import org.quartz.utils.Key;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MyJobDetail extends JobDetailImpl {
	private String id;
	private String products;
	private String customers;

	public static void main(String[] args) {
		String createUniqueName = Key.createUniqueName(null);
		System.out.println(createUniqueName);
	}

}
