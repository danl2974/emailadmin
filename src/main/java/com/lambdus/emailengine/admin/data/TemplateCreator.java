package com.lambdus.emailengine.admin.data;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.lambdus.emailengine.admin.model.Template;
import com.lambdus.emailengine.admin.service.TemplateAddition;

@ManagedBean(name = "templateCreator")
@RequestScoped
public class TemplateCreator implements Serializable {
	
	private static final Logger log = Logger.getLogger(TemplateCreator.class.getName());
	
    @Inject
    private TemplateAddition templateAddition;
    
    private String domain;
	
    private String creative;
    
    private String fromname;
    
    private String fromaddress;
    
    private String subjectline;
    /*
    private Template template;
    
    @PostConstruct
    public void initNewTemplate() {
    	this.template = new Template();
    }
    */
    
    public String getCreative(){
    	return creative;
    	}
    
    public void setCreative(String creative){
        this.creative = creative;
    	}    
 
    public String getFromname(){
    	return fromname;
    	}
    
    public void setFromname(String fromname){
        this.fromname = fromname;
    	}
    
    public String getFromaddress(){
    	return fromaddress;
    	}
    
    public void setFromaddress(String fromaddress){
        this.fromaddress = fromaddress;
    	}
    
    public String getSubjectline(){
    	return subjectline;
    	}
    
    public void setSubjectline(String subjectline){
        this.subjectline = subjectline;
    	} 
    
    public String getDomain(){
    	return domain;
    	}
    
    public void setDomain(String domain){
        this.domain = domain;
    	} 
    
    
    public void addNew(){
    	try{
    	log.info("addNew called from Template Creation");
    	Template t = new Template();
    	t.setcreative(this.creative);
    	t.setfromname(this.fromname);
    	t.setfromaddress(this.fromaddress);
    	t.setsubjectline(this.subjectline);
    	templateAddition.persistTemplate(t);
    	}
    	 catch (Exception e) {
    		 log.info(e.getMessage());
    	 }
    }
}
