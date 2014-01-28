package com.lambdus.emailengine.admin.data;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

import com.lambdus.emailengine.admin.model.Template;

@RequestScoped
public class TemplateList {

    @Inject
    private TemplateRepository templateRepository;
    
    @Inject
    private FacesContext facesContext;

    private List<Template> templates;
    
    private List<Template> templatesoffset;

    @Produces
    @Named
    public List<Template> getTemplates() {
        return templates;
    }

    public void onMemberListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Template template) {
        retrieveAllTemplates();
    }

    @PostConstruct
    public void  retrieveAllTemplates() {
        templates = templateRepository.findAllOrderedById();
    }
    
    @PostConstruct
    public void  retrieveOffset() {
        HttpServletRequest req = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest();
        String offset = ( req.getParameter("off").equals(null) ) ? req.getParameter("off") : "0"; 
        templatesoffset = templateRepository.getTemplatesTenSet(Integer.valueOf(offset));
    }
    
    
    
    
}
