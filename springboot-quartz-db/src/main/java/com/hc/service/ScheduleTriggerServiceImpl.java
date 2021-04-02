package com.hc.service;

import com.hc.mapper.ScheduleTriggerMapper;
import com.hc.mapper.ScheduleTriggerParamMapper;
import com.hc.model.*;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import static org.quartz.JobBuilder.newJob;

@Service
public class ScheduleTriggerServiceImpl implements ScheduleTriggerService {

    @Autowired
    private ScheduleTriggerMapper scheduleTriggerMapper;

    @Autowired
    private ScheduleTriggerParamMapper scheduleTriggerParamMapper;

    @Autowired
    private Scheduler scheduler;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void refreshScheduler(){
        try {
            List<ScheduleTrigger> scheduleTriggers =
                    scheduleTriggerMapper.queryScheduleTriggerList();
            if(null!=scheduleTriggers){
                for (ScheduleTrigger scheduleTrigger : scheduleTriggers) {
                    String cron = scheduleTrigger.getCron();  //表达式
                    String jobName = scheduleTrigger.getJobName(); //任务名称
                    String jobGroup = scheduleTrigger.getJobGroup(); //任务分组
                    String status = scheduleTrigger.getStatus();  //任务状态

                    //JobName+JobGroup=Primary Key   根据jobName和jobGroup生成TriggerKey，唯一的触发器关键字
                    TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
                    //根据TriggerKey到Scheduler调度器中获取触发器
                    CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);

                    if(null==cronTrigger){
                        if(status.equals("0"))
                            continue;
                        System.out.println("创建调度器");
                        //创建任务详情
                        JobDetail jobDetail= newJob((Class<? extends Job>) Class.forName(jobName)).withIdentity(jobName,jobGroup).build();

                        //往Job任务中传递参数
                        JobDataMap jobDataMap = jobDetail.getJobDataMap();
                        List<ScheduleTriggerParam> params = scheduleTriggerParamMapper.queryScheduleParamList(scheduleTrigger.getId());
                        for (ScheduleTriggerParam param : params) {
                            jobDataMap.put(param.getName(),param.getValue());
                        }

                        //创建表达式调度器
                        CronScheduleBuilder cronSchedule = CronScheduleBuilder.cronSchedule(cron);

                        //创建Trigger
                        cronTrigger=TriggerBuilder.newTrigger().withIdentity(jobName,jobGroup).withSchedule(cronSchedule).build();
                        /*
                        SimpleTrigger simpleTrigger = (SimpleTrigger)TriggerBuilder.newTrigger().withIdentity(jobName,jobGroup).withSchedule(cronSchedule).build();
                        */
                        //将jobDetail和Trigger注入到scheduler调度器中
                        scheduler.scheduleJob(jobDetail,cronTrigger);
                    }else{
                        //System.out.println("Quartz 调度任务中已存在该任务");
                        if(status.equals("0")){
                            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);
                            scheduler.deleteJob(jobKey);
                            continue;
                        }
                        //创建任务详情
                        JobDetail jobDetail= newJob((Class<? extends Job>) Class.forName(jobName))
                                        .withIdentity(jobName,jobGroup)
                                        .build();

                        //往Job任务中传递参数
                        JobDataMap jobDataMap = jobDetail.getJobDataMap();
                        List<ScheduleTriggerParam> params =
                                scheduleTriggerParamMapper.queryScheduleParamList(scheduleTrigger.getId());
                        for (ScheduleTriggerParam param : params) {
                            jobDataMap.put(param.getName(),param.getValue());
                        }
                        //调度器中的表达式
                        String cronExpression =
                                cronTrigger.getCronExpression();
                        // 表达式没有发生变化，也要进行刷新 并注入
                        //if(!cron.equals(cronExpression)){
                            //创建表达式调度器
                            CronScheduleBuilder cronSchedule =
                                    CronScheduleBuilder.cronSchedule(cron);

                            //重构
                            cronTrigger=cronTrigger.getTriggerBuilder()
                                    .withIdentity(triggerKey)
                                    .withSchedule(cronSchedule)
                                    .build();

                            //刷新调度器
                            scheduler.rescheduleJob(triggerKey,cronTrigger);
                        //}
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return scheduleTriggerMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(ScheduleTrigger record) {
        return scheduleTriggerMapper.insert(record);
    }

    @Override
    public int insertSelective(ScheduleTrigger record) {
        return scheduleTriggerMapper.insertSelective(record);
    }

    @Override
    public ScheduleTrigger selectByPrimaryKey(Integer id) {
        return scheduleTriggerMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(ScheduleTrigger record) {
        return scheduleTriggerMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(ScheduleTrigger record) {
        return scheduleTriggerMapper.updateByPrimaryKey(record);
    }

    @Override
    public List<ScheduleTrigger> queryScheduleTriggerList() {
        return scheduleTriggerMapper.queryScheduleTriggerList();
    }
}
