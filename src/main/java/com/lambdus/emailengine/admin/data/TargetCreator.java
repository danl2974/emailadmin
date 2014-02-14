package com.lambdus.emailengine.admin.data;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.logging.Logger;

import com.lambdus.emailengine.admin.model.Target;
import com.lambdus.emailengine.admin.model.Template;
import com.lambdus.emailengine.admin.service.PersistenceService;
import com.lambdus.emailengine.admin.service.TemplateAddition;

import com.lambdus.emailengine.CredentialSecurityDES;

@ManagedBean(name = "targetCreator")
@RequestScoped
public class TargetCreator implements Serializable {
	
	private static final Logger log = Logger.getLogger(TemplateCreator.class.getName());
	
    @Inject
    private PersistenceService persistenceService;
    
    private String name;
    
    private String association;
    
    private String queryText;
    
    private String dbhost;
    
    private String dbms;
    
    private String dbport;
    
    private String dbuser;
    
    private String dbpassword;
    
    
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
    
    
    public String getDbhost() {
        return dbhost;
    }

    public void setDbhost(String dbhost) {
        this.dbhost = dbhost;
    }
    

    public String getDbms() {
        return dbms;
    }

    public void setDbms(String dbms) {
        this.dbms = dbms;
    }
    

    public String getDbport() {
        return dbport;
    }

    public void setDbport(String dbport) {
        this.dbport = dbport;
    }
    

    public String getDbuser() {
        return dbuser;
    }

    public void setDbuser(String dbuser) {
        this.dbuser = dbuser;
    }
    
  
    public String getDbpassword() {
        return dbpassword;
    }

    public void setDbpassword(String dbpassword) {
        this.dbpassword = dbpassword;
    }
    
    
     
    
    public void addNew(){
    	try{
    	Target t = new Target();
    	t.setname(this.name);
    	t.setassociation(this.association);
    	t.setqueryText(this.queryText);
    	t.setdbhost(this.dbhost);
    	t.setdbms(this.dbms);
    	t.setdbport(this.dbport);
    	t.setdbuser(this.dbuser);
    	
    	CredentialSecurityDES csDes = new CredentialSecurityDES();
    	t.setdbpassword(csDes.encrypt(this.dbpassword));
    	
    	persistenceService.doNew(t);
    	}
    	 catch (Exception e) {
    		 log.info(e.getMessage());
    	 }
    }
}

