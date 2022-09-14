package com.fevertime.coinvillage.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@Lazy(false)
@Component
@EnableScheduling
public class SpringDynamicCornTask implements SchedulingConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(SpringDynamicCornTask.class);

    private static final String DEFAULT_CRON = "0 0 0 1 * *";
    private String cron = DEFAULT_CRON;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(() -> {
            logger.info("         。。。");
        }, triggerContext -> {
            CronTrigger trigger = new CronTrigger(cron);
            return trigger.nextExecutionTime(triggerContext);
        });
    }
    public void setCron(String cron) {
        System.out.println("   cron："+this.cron+"    cron："+cron);
        this.cron = cron;
    }
    public String getCron() {
        return this.cron;
    }
}
