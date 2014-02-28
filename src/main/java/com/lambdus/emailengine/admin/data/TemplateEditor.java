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

import com.lambdus.emailengine.admin.model.Template;
import com.lambdus.emailengine.admin.service.TemplateAddition;

import org.jboss.logging.Logger;

@ManagedBean(name = "templateEditor")
@ViewScoped
public class TemplateEditor {
	
	private static final Logger log = Logger.getLogger(TemplateEditor.class.getName());

    @Inject
    private TemplateRepository templateRepository;
    
    @Inject
    private FacesContext facesContext;
    
    @EJB
    private TemplateAddition templateAddition;
    
    //@PersistenceContext(type=PersistenceContextType.EXTENDED)
    //private EntityManager em;
    
    private String domain;
    
    private String creative;
    
    private String fromname;
    
    private String fromaddress;
    
    private String subjectline;
    
    private Template template;
    
    private String templateString;
     
    @Produces
    @Named
    public String getCreative() {
        return creative;
    }
    
    //@TransactionAttribute(TransactionAttributeType.REQUIRED)
    //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
    public void setCreative(String creative) {
        this.creative = creative;
        this.template.setcreative(creative);
        templateAddition.doMerge(template);
    }

    @PostConstruct
    public void  fetchCreativeById() {
        HttpServletRequest req = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest();
        String tid = ( req.getParameter("t") == null ) ?  "0" : req.getParameter("t"); 
        Template t = templateRepository.findById(Integer.valueOf(tid));
        log.info("fetchCreativeById called");
        this.template = t;
        this.creative = t.getcreative();
        this.fromname = t.getfromname();
        this.fromaddress = t.getfromaddress();
        this.subjectline = t.getsubjectline();
        this.domain = t.getdomain();
        this.templateString = String.valueOf(this.template.getid());
    }
 
    
    public String getTemplateString(){
    	return templateString;
    	}
    
    public void setTemplateString(String templateString){
    	this.templateString = templateString;
    	}
    
    public String getFromname(){
    	return fromname;
    	}
    
    public void setFromname(String fromname){
        this.fromname = fromname;
        this.template.setfromname(fromname);
        templateAddition.doMerge(template);
    	}
    
    public String getFromaddress(){
    	return fromaddress;
    	}
    
    public void setFromaddress(String fromaddress){
        this.fromaddress = fromaddress;
        this.template.setfromaddress(fromaddress);
        templateAddition.doMerge(template);
    	}
    
    public String getSubjectline(){
    	return subjectline;
    	}
    
    public void setSubjectline(String subjectline){
        this.subjectline = subjectline;
        this.template.setsubjectline(subjectline);
        templateAddition.doMerge(template);
    	}
    
    public String getDomain(){
    	return domain;
    	}
    
    public void setDomain(String domain){
        this.domain = domain;
        this.template.setdomain(domain);
        templateAddition.doMerge(template);
    	}
    
}