package com.lambdus.emailengine.admin.data;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import java.util.List;

import com.lambdus.emailengine.admin.model.Template;

import org.jboss.logging.Logger;

@ManagedBean(name = "templateEditor")
@SessionScoped
public class TemplateEditor {
	
	private static final Logger log = Logger.getLogger(TemplateList.class.getName());

    @Inject
    private TemplateRepository templateRepository;
    
    @Inject
    private FacesContext facesContext;
    
    
    private String creative;

    
    @PostConstruct
    public void  getCreative() {
        HttpServletRequest req = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest();
        String tid = ( req.getParameter("t") == null ) ?  "0" : req.getParameter("t"); 
        creative = templateRepository.findById(Integer.valueOf(tid)).getcreative();
    }
 
    
    
    
}