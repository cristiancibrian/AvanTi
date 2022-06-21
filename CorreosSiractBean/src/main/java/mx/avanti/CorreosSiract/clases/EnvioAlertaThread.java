package mx.avanti.CorreosSiract.clases;

import java.util.logging.Level;
import javax.servlet.*;
import java.util.logging.Logger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class EnvioAlertaThread implements Job, ServletContextListener{

     /** The servlet context with which we are associated. */
    private ServletContext context = null;

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        log("Context destroyed");
        this.context = null;
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        this.context = event.getServletContext();
        log("Context initialized");
    }

    private void log(String message) {
        if (context != null) {
            context.log("MyServletContextListener: " + message);
        } else {
            System.out.println("MyServletContextListener: " + message);
        }
    }
    
    public static void main(String[] args) throws SchedulerException {
        JobDetail j = JobBuilder.newJob(EnvioAlertaThread.class).build();
       //Tiempo de espera para los hilos        
        Trigger t = TriggerBuilder.newTrigger().withIdentity("CroneTrigger")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(100000)
                .repeatForever()).build();
        
        Scheduler s = StdSchedulerFactory.getDefaultScheduler();
        
        s.start();
        s.scheduleJob(j, t);
        
    }

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        Thread t = new Thread(new Envio());
        t.start();
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    static class Envio implements Runnable {
        @Override
        public void run() {
            EnvioAlertas alertas = new EnvioAlertas();
            while (true) {
                try {
                    System.out.println("Iniciando");
                    alertas.enviarCorreo();
                    System.out.println("Terminando");
                    //Thread.sleep(6000000);
                    Thread.sleep(86400000);
                } catch (Exception ex) {
                    Logger.getLogger(EnvioAlertaThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
