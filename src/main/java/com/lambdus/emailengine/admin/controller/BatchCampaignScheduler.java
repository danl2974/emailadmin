package com.lambdus.emailengine.admin.controller;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.sql.Timestamp;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.inject.Inject;


import javax.naming.Context;
import javax.naming.InitialContext;

import org.jboss.logging.Logger;
import org.quartz.CronExpression;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.lambdus.emailengine.IBatchCampaignController;
import com.lambdus.emailengine.admin.schedule.BatchCampaignScheduledJob;


@ManagedBean
@SessionScoped
public class BatchCampaignScheduler {
	
	private static final Logger log = Logger.getLogger(BatchCampaignScheduler.class.getName());
	
	//@EJB(mappedName = "BatchCampaignController")
	//private BatchCampaignController campaignController;
	
	//@Inject
	//private IBatchCampaignController campaignController;
	
	private int templateId;
	
	private int targetId;
	
	private Date scheduledStart;
	
	private String status = "";
	
		
	public void schedule() {

		 Date date = new Date();
		 Timestamp ts = new Timestamp(date.getTime());
		 Calendar cal = Calendar.getInstance();
		 cal.setTime(this.scheduledStart);
		 cal.add(Calendar.HOUR_OF_DAY, 5);
		 Date utcScheduleStart = cal.getTime();
		 
		 log.info("Timestamp " + ts);
		 try{
		 SchedulerFactory sf = new StdSchedulerFactory();
		 Scheduler scheduler = sf.getScheduler();
		 scheduler.start();
		 
		 JobDetail job = JobBuilder.newJob(BatchCampaignScheduledJob.class)
				    .withIdentity("job" + ts, "admin")
				    .build();
		 
		 JobDataMap jmd = job.getJobDataMap();
		 jmd.put("templateId", templateId);
		 jmd.put("targetId", targetId);

	     SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
				    .withIdentity("trigger" + ts, "admin")
				    .startAt(utcScheduleStart)
				    .build();

	     scheduler.scheduleJob(job, trigger);
	     
	     log.info("trigger start time " + trigger.getStartTime());
	     log.info(scheduler.getMetaData().getSummary());
	     
		 }
		 catch (SchedulerException se){
			 log.info(se.getMessage());
		 }
		 catch (Exception e){
			 log.info(e.getMessage());
		 }
		 
		 //start();
		 
	}
	
	private void start(){
		
		    Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		    String datestr = formatter.format(this.scheduledStart);	
		    log.info(String.format("Start called with template %s and target %s and date %s", this.templateId, this.targetId, datestr));
		 
		    final Hashtable jndiProperties = new Hashtable();
	        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
	        try{
	        final Context context = new InitialContext(jndiProperties);
	        String jndi = "ejb:mailingservice/admin//BatchCampaignController!com.lambdus.emailengine.IBatchCampaignController?stateful";
	        //String jndi = "java:global/mailingservice/admin/BatchCampaignController!com.lambdus.emailengine.IBatchCampaignController";
	        IBatchCampaignController campaignController = (IBatchCampaignController) context.lookup(jndi);
	        
	        log.info("Call after JNDI remote bean lookup");
		 
		 /*
		 Properties env = new Properties();  
		    env.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");  
		    env.put(Context.PROVIDER_URL, "remote://localhost:4447");  
		    env.put(Context.SECURITY_PRINCIPAL, "jboss");  
		    env.put(Context.SECURITY_CREDENTIALS, "lambdus2200");  
		    env.put("jboss.naming.client.ejb.context", true); 
		 */
		 
		 //InitialContext ctx = new InitialContext(env);
		 //BatchCampaignController campaignController = (BatchCampaignController) ctx.lookup("java:global/emailengine/BatchCampaignController");
		 campaignController.setTargetId(this.targetId);
		 campaignController.setTemplateId(this.templateId);
		 campaignController.startCampaign();
		 
	     }
	        catch(Exception e){
	        	log.info("exception with jndi lookup");
	        	log.error(e.getMessage());
	        }
		 
	}
	
	
    public void setTargetId(int targetId)
    {
            this.targetId = targetId;
    }
    
    public void setTemplateId(int templateId)
    {
            this.templateId = templateId;
    }
    
    public int getTargetId()
    {
            return this.targetId;
    }
    
    public int getTemplateId()
    {
            return this.templateId;
    }
    
    public String getStatus()
    {
            return this.status;
    }
    
    public void setStatus (String status)
    {
            this.status = status;
    }
    
    public Date getScheduledStart()
    {
            return this.scheduledStart;
    }
    
    public void setScheduledStart(Date date)
    {
    	this.scheduledStart = date;
    }

}



