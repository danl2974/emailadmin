package com.lambdus.emailengine.admin.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.lambdus.emailengine.BatchCampaignController;

import javax.naming.InitialContext;

import org.jboss.logging.Logger;




@ManagedBean(name = "batchCampaignClient")
@SessionScoped
public class BatchCampaignClient {
	
	private static final Logger log = Logger.getLogger(BatchCampaignClient.class.getName());
	
	private int templateId;
	
	private int targetId;
	
	public void start(){
		
		 InitialContext ctx = new InitialContext();
		 BatchCampaignController campaignController = (BatchCampaignController) ctx.lookup("java:global/emailengine/BatchCampaignController");
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

}
