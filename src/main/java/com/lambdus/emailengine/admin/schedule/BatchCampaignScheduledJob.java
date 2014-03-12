package com.lambdus.emailengine.admin.schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.jboss.logging.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import com.lambdus.emailengine.IBatchCampaignController;


public class BatchCampaignScheduledJob implements Job {
	
	private static final Logger log = Logger.getLogger(BatchCampaignScheduledJob.class.getName());
	
	private int targetId = 0;
	private int templateId = 0;
	
	public void execute(JobExecutionContext context) throws JobExecutionException {
	    JobKey jobKey = context.getJobDetail().getKey();
	    JobDataMap jdm = context.getJobDetail().getJobDataMap();
	    
	    //log.info("Job: " + jobKey + " executing at " + new Date() + "Job Data " + jdm.getInt("targetId") + " " + jdm.getInt("templateId") );
	    
	    final Hashtable jndiProperties = new Hashtable();
        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
        try{
            final Context ejbContext = new InitialContext(jndiProperties);
            String jndi = "ejb:mailingservice/admin//BatchCampaignController!com.lambdus.emailengine.IBatchCampaignController?stateful";
            IBatchCampaignController campaignController = (IBatchCampaignController) ejbContext.lookup(jndi);
        
            //campaignController.setTargetId(jdm.getInt("targetId"));
            //campaignController.setTargetIds((List<Integer>) jdm.get("targetIds"));
	        campaignController.setTemplateId(jdm.getInt("templateId"));
	        
	        Set<String> keys = jdm.keySet();
	        ArrayList<Integer> targetlist = new ArrayList<Integer>();
	        for (String k : keys){
	        	if (k.indexOf("targetId") != -1){
	        		targetlist.add(Integer.valueOf(jdm.getString(k)));
	        	}
	        }
	        
	        
	        campaignController.setTargetIds((List<Integer>) targetlist);
	        
	        campaignController.startCampaign();
	        
	        context.getScheduler().shutdown();
	        
        }
        catch(Exception e){
        	log.error(e.getMessage());
        }
	    
	}
	
	public void setTargetId(int targetId){
		this.targetId = targetId;
	}
	
	public void setTemplateId(int templateId){
		this.templateId = templateId;
	}
	
	public int getTargetId(){
		return this.targetId;
	}
	
	public int getTemplateId(){
		return this.templateId;
	}	

}

