package com.lambdus.emailengine.admin.controller;

import java.io.Serializable;
import java.util.List;


import javax.inject.Inject;

import org.jboss.logging.Logger;


import com.lambdus.emailengine.admin.data.TemplateRepository;
import com.lambdus.emailengine.admin.model.Template;
import org.primefaces.model.LazyDataModel;


public class TemplatesPaginator implements Serializable {

	private static final long serialVersionUID = 8969653828349899774L;

	private static final Logger log = Logger.getLogger(TemplatesPaginator.class.getName());

    @Inject
    private TemplateRepository templateRepository;
    
	
	private List<Template> templates;
	
    public List<Template> getTemplates() {
        return templates;
    }
	
	public TemplatesPaginator(){
		log.info("call TemplatesPaginator constructor");
		templates = templateRepository.findAllOrderedById();
	} 
	

}
                    
