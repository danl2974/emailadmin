package com.lambdus.emailengine.admin.data;

import javax.annotation.PostConstruct;
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

import org.jboss.logging.Logger;

@ManagedBean(name = "templateEditor")
@SessionScoped
//@Stateful
//@Stateless
//@TransactionManagement(TransactionManagementType.CONTAINER)
//@TransactionManagement(TransactionManagementType.BEAN)
public class TemplateEditor {
	
	private static final Logger log = Logger.getLogger(TemplateEditor.class.getName());

    @Inject
    private TemplateRepository templateRepository;
    
    @Inject
    private FacesContext facesContext;
    
    @PersistenceContext(type=PersistenceContextType.EXTENDED)
    private EntityManager em;
    
    private String creative;
    
    private Template template;
    
    @Produces
    @Named
    public String getCreative() {
        return creative;
    }
    
    //@TransactionAttribute(TransactionAttributeType.REQUIRED)
    
    public void setCreative(String creative) {
    	log.info(em);
    	em.joinTransaction();
        this.creative = creative;
        this.template.setcreative(creative);
        em.merge(template);
        //return template;
    }

    @PostConstruct
    public void  fetchCreativeById() {
        HttpServletRequest req = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest();
        String tid = ( req.getParameter("t") == null ) ?  "0" : req.getParameter("t"); 
        Template t = templateRepository.findById(Integer.valueOf(tid));
        this.template = t;
        this.creative = t.getcreative();
    }
 
    
    
    
}