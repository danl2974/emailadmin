package com.lambdus.emailengine.admin.data;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

import com.lambdus.emailengine.admin.model.Template;

import org.jboss.logging.Logger;

@ManagedBean(name = "templatesL")
@RequestScoped
public class TemplateList {
	
	private static final Logger log = Logger.getLogger(TemplateList.class.getName());

    @Inject
    private TemplateRepository templateRepository;
    
    @Inject
    private FacesContext facesContext;
    
    
    private List<Template> templates;

    
    @Produces
    @Named
    public List<Template> getTemplates() {
        return templates;
    }
    
  
    public void onMemberListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Template template) {
        retrieveAllTemplates();
        //retrieveOffset();
    }

    @PostConstruct
    public void  retrieveAllTemplates() {
        templates = templateRepository.findAllOrderedById();
    }
/*
    @PostConstruct
    public void  retrieveOffset() {
        HttpServletRequest req = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest();
        String offset = ( req.getParameter("off") == null ) ?  "0" : req.getParameter("off"); 
        log.info("Offset is " + offset);
        templates = templateRepository.getTemplatesTenSet(Integer.valueOf(offset));
        log.info("templatesoffset size is " + String.valueOf(this.templates.size()) );
        
    }
*/  
    
    
    
}