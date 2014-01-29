
package com.lambdus.emailengine.admin.controller;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.lambdus.emailengine.admin.model.Template;
import com.lambdus.emailengine.admin.service.TemplateAddition;
import com.lambdus.emailengine.admin.data.TemplateRepository;


@Model
public class TemplateController {

    @Inject
    private FacesContext facesContext;

    @Inject
    private TemplateAddition templateAddition;
    
    /*
    @Inject
    private TemplateRepository templateRepository;
    */

    @Produces
    @Named
    private Template newTemplate;

    @PostConstruct
    public void initNewTemplate() {
    	newTemplate = new Template();
    }
    

    public void addNew() throws Exception {
        try {
        	templateAddition.persistTemplate(newTemplate);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registered!", "Registration successful");
            facesContext.addMessage(null, m);
            initNewTemplate();
        } catch (Exception e) {
            String errorMessage = getRootErrorMessage(e);
            FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Registration unsuccessful");
            facesContext.addMessage(null, m);
        }
    }

    private String getRootErrorMessage(Exception e) {
        // Default to general error message that registration failed.
        String errorMessage = "Registration failed. See server log for more information";
        if (e == null) {
            // This shouldn't happen, but return the default messages
            return errorMessage;
        }

        // Start with the exception and recurse to find the root cause
        Throwable t = e;
        while (t != null) {
            // Get the message from the Throwable class instance
            errorMessage = t.getLocalizedMessage();
            t = t.getCause();
        }
        // This is the root cause message
        return errorMessage;
    }

}
