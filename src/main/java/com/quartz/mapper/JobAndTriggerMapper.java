package com.quartz.mapper;

import java.util.List;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.quartz.dto.JobAndTriggerDto;

public interface JobAndTriggerMapper {
	
	List<JobAndTriggerDto> getJobAndTriggerDetails(Pagination page);
	
	JobAndTriggerDto getJobAndTriggerDto();
}
