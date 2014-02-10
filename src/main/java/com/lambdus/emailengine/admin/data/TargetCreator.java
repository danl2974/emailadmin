package com.lambdus.emailengine.admin.data;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.lambdus.emailengine.admin.model.Target;
import com.lambdus.emailengine.admin.model.Template;
import com.lambdus.emailengine.admin.service.PersistenceService;
import com.lambdus.emailengine.admin.service.TemplateAddition;

@ManagedBean(name = "targetCreator")
@RequestScoped
public class TargetCreator implements Serializable {
	
	private static final Logger log = Logger.getLogger(TemplateCreator.class.getName());
	
    @Inject
    private PersistenceService persistenceService;
    
    private String name;
    
    private String association;
    
    private String queryText;
    
    
    public String getName(){
    	return name;
    	}
    
    public void setName(String name){
        this.name = name;
    	}    
 
    public String getAssociation(){
    	return association;
    	}
    
    public void setAssociation(String association){
        this.association = association;
    	}
    
    public String getQueryText(){
    	return queryText;
    	}
    
    public void setQueryText(String queryText){
        this.queryText = queryText;
    	}
     
    
    public void addNew(){
    	try{
    	Target t = new Target();
    	t.setname(this.name);
    	t.setassociation(this.association);
    	t.setqueryText(this.queryText);
    	persistenceService.doNew(t);
    	}
    	 catch (Exception e) {
    		 log.info(e.getMessage());
    	 }
    }
}

