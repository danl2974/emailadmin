package com.lambdus.emailengine.admin.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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


import javax.naming.Context;
import javax.naming.InitialContext;

import org.jboss.logging.Logger;

@ManagedBean
@SessionScoped
public class BatchCampaignScheduler {
	
	private static final Logger log = Logger.getLogger(BatchCampaignScheduler.class.getName());
	
	//@EJB(mappedName = "BatchCampaignController")
	//private BatchCampaignController campaignController;
	
	private int templateId;
	
	private int targetId;
	
	private Date scheduledStart;
	
	private String status = "";
	
		
	public void schedule() {
		
		 //FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Campaign committed "));
		
		 log.info("schedule from bean called - template " + String.valueOf(this.templateId) + " target " + String.valueOf(this.targetId));
		 start();
		 
		 /*
		 this.status = "committed";
		 ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		 Map<String, String[]> paramValues = ec.getRequestParameterValuesMap();
		 
		 String committedTarget = paramValues.get("campaignform:selectedTarget_input")[0];
		 String committedTemplate = paramValues.get("campaignform:selectedTemplate_input")[0];
		 
		 this.targetId = Integer.valueOf(committedTarget);
		 this.templateId = Integer.valueOf(committedTemplate);
		 
		 log.info("commitcampaign " + committedTarget + " " + committedTemplate);
		 
		 start();
		 */
		 	 
	/*	 
		List<UIComponent> uic  = event.getComponent().getParent().getParent().getChildren(); 
		 for (UIComponent c : uic){
			 log.info("UI component id = " + c.getId() ); 
		 }
    */	
		 
	}
	
	private void start(){
		
		 log.info(String.format("Start called with template %s and target %s", this.templateId, this.targetId));
		 final Hashtable jndiProperties = new Hashtable();
	        jndiProperties.put(Context.URL_PKG_PREFIXES, "org.jboss.ejb.client.naming");
	        final Context context = new InitialContext(jndiProperties);
	        BatchCampaignController campaignController = (BatchCampaignController) context.lookup("java:global/emailengine/BatchCampaignController");
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



