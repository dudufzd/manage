package com.generate;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class MySchedule {
    public static void main(String[] args) {
        //创建JobDetail对象
        JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
//                .withIdentity("myjob", "group1")//指定名称和组名
                .build();

        //创建时间表
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(2)//每2秒执行一次
                .withRepeatCount(5);//一共执行5次

        //创建触发器组件，并传入时间表
        Trigger trigger = TriggerBuilder.newTrigger()
//                .withIdentity("mytrigger", "group1")//指定名称和组名
                .startNow()//现在开始执行定时器
                .withSchedule(simpleScheduleBuilder)//传入定时器
                .build();

        //创建调度器工厂
        StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();

        Scheduler scheduler = null;
        try {
            scheduler = schedulerFactory.getScheduler();
            scheduler.scheduleJob(jobDetail,trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
