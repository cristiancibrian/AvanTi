/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.avanti.CorreosSiract.clases;

import org.quartz.*;

import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author Jesus Pio
 */
@WebListener

public class QuartzListener extends QuartzInitializerListener {

    private static final Logger LOG = LoggerFactory.getLogger(QuartzListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        super.contextInitialized(sce);
        ServletContext ctx = sce.getServletContext();
        StdSchedulerFactory factory = (StdSchedulerFactory) ctx.getAttribute(QUARTZ_FACTORY_KEY);
        try {
            /*    Scheduler scheduler = factory.getScheduler();
            JobDetail job = JobBuilder.newJob(EnvioAlertaThread.class).build();
            Trigger trigger = TriggerBuilder
                    .newTrigger()
                    .withIdentity("simple")
                    //                    .withSchedule(CronScheduleBuilder.cronSchedule("0/5 * * * * ?")).startNow().build();
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).repeatForever()).build();
            scheduler.scheduleJob(job, trigger);
            scheduler.start();
             */
            JobDetail j = JobBuilder.newJob(EnvioAlertaThread.class).build();

            Trigger t = TriggerBuilder.newTrigger().withIdentity("CroneTrigger")
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10)
                            .repeatForever()).build();

            Scheduler s = StdSchedulerFactory.getDefaultScheduler();

            s.start();
            s.scheduleJob(j, t);
        } catch (Exception e) {
            LOG.error("Ocurri\u00f3 un error al calendarizar el trabajo", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        super.contextDestroyed(sce);
    }
}
