/*
 * Copyright (C) 2022 [www.mobaijun.com]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mobaijun.quartz.service.impl;

import com.mobaijun.quartz.service.QuartzJobService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CalendarIntervalScheduleBuilder;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.DateBuilder;
import org.quartz.DateBuilder.IntervalUnit;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Description: Quartz 调度服务实现，封装任务创建/修改/删除等核心能力，
 * 并提供统一的 JobDetail、Trigger 构建工具方法
 * Author: [mobaijun]
 * Date: [2024/8/20 10:00]
 * IntelliJ IDEA Version: [IntelliJ IDEA 2023.1.4]
 *
 * @param scheduler 调度器
 */
@Slf4j
public record QuartzJobServiceImpl(Scheduler scheduler) implements QuartzJobService {

    /**
     * 优先级在此维护
     * <p>
     * 较高优先级的触发器先触发。如果没有为trigger设置优先级，trigger使用默认优先级，值为5；
     */
    public static final int PRIORITY_DEFAULT = 5;

    /**
     * 默认的cron表达式格式
     */
    public static final String CRON_FORMAT = "ss mm HH dd MM ? yyyy";

    /**
     * 构建 JobDetail，并在构建前检查是否已存在同名任务（存在则删除）
     */
    private JobDetail buildJobDetail(Class<? extends Job> clazz, String name, String group, String desc) {
        // 先检查任务是否存在,存在则删除
        JobKey jobKey = checkExists(name, group);
        return JobBuilder.newJob(clazz)
                .withDescription(desc)
                .withIdentity(jobKey)
                .build();
    }

    /**
     * 构建 JobDataMap，统一处理空参数
     */
    private JobDataMap buildJobDataMap(Map<String, Object> params) {
        if (params == null || params.isEmpty()) {
            return new JobDataMap();
        }
        return new JobDataMap(params);
    }

    /**
     * 构建 TriggerBuilder 的公共部分
     */
    private TriggerBuilder<Trigger> buildTriggerBuilder(String name,
                                                        String group,
                                                        JobDataMap dataMap,
                                                        String desc,
                                                        Date startTime) {
        TriggerKey triggerKey = TriggerKey.triggerKey(name, group);
        return TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .usingJobData(dataMap)
                .withDescription(desc)
                .startAt(startTime)
                .withPriority(PRIORITY_DEFAULT);
    }

    @Override
    public Date addJob(Class<? extends Job> clazz, String name, String group, Map<String, Object> params, Date scheduleTime, String desc) {
        try {
            JobDetail job = buildJobDetail(clazz, name, group, desc);
            JobDataMap dataMap = buildJobDataMap(params);
            // 创建触发器
            SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow();
            TriggerBuilder<Trigger> builder = buildTriggerBuilder(name, group, dataMap, desc, scheduleTime);
            return this.scheduler.scheduleJob(job, builder.withSchedule(simpleScheduleBuilder).build());
        } catch (Exception e) {
            log.error("以指定计划时间的方式新增任务失败!", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 检测定时任务是否存在
     *
     * @param name  任务名称
     * @param group 任务组
     * @return 任务key
     */
    private JobKey checkExists(String name, String group) {
        try {
            JobKey jobKey = JobKey.jobKey(name, group);
            if (this.scheduler.checkExists(jobKey)) {
                deleteJob(name, group);
            }
            return jobKey;
        } catch (Exception e) {
            log.error("检查任务是否存在失败!", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Date addJob(Class<? extends Job> clazz, String name, String group, Map<String, Object> params, int future, IntervalUnit unit, String desc) {
        try {
            JobDetail job = buildJobDetail(clazz, name, group, desc);
            JobDataMap dataMap = buildJobDataMap(params);
            // 创建触发器
            SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow();
            Date startTime = DateBuilder.futureDate(future, unit == null ? IntervalUnit.SECOND : unit);
            TriggerBuilder<Trigger> builder = buildTriggerBuilder(name, group, dataMap, desc, startTime);
            return this.scheduler.scheduleJob(job, builder.withSchedule(simpleScheduleBuilder).build());
        } catch (Exception e) {
            log.error("以指定计划时间的方式新增任务失败!", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Date addJob(Class<? extends Job> clazz, String name, String group, Map<String, Object> params, int future, String desc) {
        return addJob(clazz, name, group, params, future, IntervalUnit.SECOND, desc);
    }

    @Override
    public Date addJob(Class<? extends Job> clazz, String name, String group, Map<String, Object> params, String cronExpression, String desc) {
        return addJob(clazz, name, group, params, cronExpression, desc, new Date(), null);
    }

    @Override
    public Date addJob(Class<? extends Job> clazz, String name, String group, Map<String, Object> params, String cronExpression, String desc, Date endTime) {
        return addJob(clazz, name, group, params, cronExpression, desc, new Date(), endTime);
    }

    public Date addJob(Class<? extends Job> clazz, String name, String group, Map<String, Object> params,
                       String cronExpression, String desc, Date startTime, Date endTime) {
        try {
            JobDetail job = buildJobDetail(clazz, name, group, desc);
            JobDataMap dataMap = buildJobDataMap(params);
            // 创建触发器
            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionFireAndProceed();
            TriggerBuilder<Trigger> builder = buildTriggerBuilder(name, group, dataMap, desc, startTime);
            if (endTime != null) {
                builder = builder.endAt(endTime);
            }
            return this.scheduler.scheduleJob(job, builder.withSchedule(cronScheduleBuilder).build());
        } catch (Exception e) {
            log.error("以cron的方式新增任务失败!", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public Date addJobWithIntervalInSeconds(Class<? extends Job> clazz, String name, String group, Map<String, Object> params, String desc, Date startTime, Date endTime, int interval) {
        try {
            JobDetail job = buildJobDetail(clazz, name, group, desc);
            JobDataMap dataMap = buildJobDataMap(params);
            // 创建触发器
            CalendarIntervalScheduleBuilder cisb = CalendarIntervalScheduleBuilder.calendarIntervalSchedule()
                    .withIntervalInSeconds(interval)
                    .withMisfireHandlingInstructionFireAndProceed();
            TriggerBuilder<Trigger> builder = buildTriggerBuilder(name, group, dataMap, desc, startTime);
            if (endTime != null) {
                builder = builder.endAt(endTime);
            }
            return this.scheduler.scheduleJob(job, builder.withSchedule(cisb).build());
        } catch (Exception e) {
            log.error("以每隔多少秒执行一次方式新增任务失败!", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteJob(String name, String group) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(name, group);
            // 停止触发器
            this.scheduler.pauseTrigger(triggerKey);
            // 移除触发器
            this.scheduler.unscheduleJob(triggerKey);
            JobKey jobKey = JobKey.jobKey(name, group);
            // 删除任务
            return this.scheduler.deleteJob(jobKey);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsJob(String name, String group) {
        try {
            // 先检查任务是否存在,存在则删除
            JobKey jobKey = JobKey.jobKey(name, group);
            return this.scheduler.checkExists(jobKey);
        } catch (Exception e) {
            log.error("检测任务出错错误！", e);
        }
        return false;
    }

    @Override
    public Date modifyJob(String name, String group, Map<String, Object> params, Date scheduleTime) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(name, group);
            CronTrigger trigger = (CronTrigger) this.scheduler.getTrigger(triggerKey);
            if (trigger == null) {
                return null;
            }
            String oldTime = trigger.getCronExpression();
            String newTime = LocalDateTime.ofInstant(scheduleTime.toInstant(), ZoneId.systemDefault())
                    .format(DateTimeFormatter.ofPattern(CRON_FORMAT));
            if (!newTime.equals(oldTime)) {
                JobKey jobKey = JobKey.jobKey(name, group);
                JobDetail jobDetail = this.scheduler.getJobDetail(jobKey);
                if (jobDetail != null) {
                    // 修改时间
                    if (deleteJob(name, group)) {
                        return addJob(jobDetail.getJobClass(), name, group, params, scheduleTime,
                                jobDetail.getDescription());
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void deleteGroupJobs(String group) {
        try {
            GroupMatcher<TriggerKey> triggerMatcher = GroupMatcher.groupEquals(group);
            Set<TriggerKey> triggerKeySet = this.scheduler.getTriggerKeys(triggerMatcher);
            List<TriggerKey> triggerKeyList = new ArrayList<>(triggerKeySet);
            this.scheduler.pauseTriggers(triggerMatcher);
            this.scheduler.unscheduleJobs(triggerKeyList);
            GroupMatcher<JobKey> matcher = GroupMatcher.groupEquals(group);
            Set<JobKey> jobKeySet = this.scheduler.getJobKeys(matcher);
            List<JobKey> jobKeyList = new ArrayList<>(jobKeySet);
            this.scheduler.deleteJobs(jobKeyList);
            // 要恢复组触发器，否则以后添加的任务会处于暂停状态，无法运行
            this.scheduler.resumeTriggers(triggerMatcher);
        } catch (Exception e) {
            log.error("删除任务组失败!", e);
            throw new RuntimeException(e);
        }
    }
}
