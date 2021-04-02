package com.hc.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class MyJob2 implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDetail jobDetail =
                jobExecutionContext.getJobDetail();
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        System.err.println("MyJob2 是一个空的任务计划,时间：" + new Date().toLocaleString() + "-->MyJob2参数传递name=" + jobDataMap.get("name")+",score=" +
                jobDataMap.get("score"));
    }
}