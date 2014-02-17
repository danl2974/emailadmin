package com.lambdus.emailengine.admin.data;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.servlet.http.HttpServletRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lambdus.emailengine.admin.model.Target;
import com.lambdus.emailengine.admin.model.Template;
import com.lambdus.emailengine.admin.service.PersistenceService;
import com.lambdus.emailengine.admin.service.TemplateAddition;

import org.jboss.logging.Logger;

@ManagedBean(name = "targetEditor")
@ViewScoped
public class TargetEditor {
	
	private static final Logger log = Logger.getLogger(TargetEditor.class.getName());

    @Inject
    private PersistenceRepository persistenceRepository;
    
    @Inject
    private FacesContext facesContext;
    
    @EJB
    private PersistenceService persistenceService;
    
    private String name;
    
    private String association;
    
    private String queryText;
    
    private Target target;
    
    private String targetString;
    
    private String dbhost;
    
    private String dbms;
    
    private String dbname;
    
    private String dbport;
    
    private String dbuser;
    

    @PostConstruct
    public void  fetchTargetById() {
        HttpServletRequest req = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest();
        String tid = ( req.getParameter("t") == null ) ?  "0" : req.getParameter("t"); 
        Target t = persistenceRepository.findTargetById(Integer.valueOf(tid));
        this.target = t;
        this.name = t.getname();
        this.association = t.getassociation();
        this.queryText = t.getqueryText();
        this.targetString = String.valueOf(this.target.getid());
        this.dbhost = t.getdbhost(); 
        this.dbms = t.getdbms();
        this.dbport = t.getdbport();
        this.dbuser = t.getdbuser();
        
    }
 
    
    public String getTargetString(){
    	return targetString;
    	}
    
    public void setTargetString(String targetString){
    	this.targetString = targetString;
    	}
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
        this.target.setname(name);
        persistenceService.doMerge(target);
    }
    
    public String getAssociation(){
    	return association;
    	}
    
    public void setAssociation(String association){
        this.association = association;
        this.target.setassociation(association);
        persistenceService.doMerge(target);
    	}
    
    public String getQueryText(){
    	return queryText;
    	}
    
    public void setQueryText(String queryText){
        this.queryText = queryText;
        this.target.setqueryText(queryText);
        persistenceService.doMerge(target);
    	}
    
    public String getDbhost() {
        return dbhost;
    }

    public void setDbhost(String dbhost) {
        this.dbhost = dbhost;
        this.target.setdbhost(dbhost);
        persistenceService.doMerge(target);
        
    }
    

    public String getDbms() {
        return dbms;
    }

    public void setDbms(String dbms) {
        this.dbms = dbms;
        this.target.setdbms(dbms);
        persistenceService.doMerge(target);
    }
 
    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
        this.target.setdbname(dbname);
        persistenceService.doMerge(target);
    }    

    public String getDbport() {
        return dbport;
    }

    public void setDbport(String dbport) {
        this.dbport = dbport;
        this.target.setdbport(dbport);
        persistenceService.doMerge(target);
    }
    

    public String getDbuser() {
        return dbuser;
    }

    public void setDbuser(String dbuser) {
        this.dbuser = dbuser;
        this.target.setdbuser(dbuser);
        persistenceService.doMerge(target);
    }
    
}
