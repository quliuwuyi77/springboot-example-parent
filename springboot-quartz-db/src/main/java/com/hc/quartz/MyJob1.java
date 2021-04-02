package com.hc.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import com.hc.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class MyJob1 implements Job {

    @Autowired
    private ScheduleTriggerParamService scheduleTriggerParamService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail jobDetail = jobExecutionContext.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        System.err.println("MyJob1 是一个空的任务计划,时间：" + new Date().toLocaleString() + "-->携带参数个数:"+jobDataMap.size());
    }
}