
package com.lambdus.emailengine.admin.service;

import com.lambdus.emailengine.admin.model.Template;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Logger;

@Stateless
public class TemplateAddition {

    @Inject
    private Logger log;

    @Inject
    private EntityManager em;

    @Inject
    private Event<Template> templateEventSrc;

    public void persistTemplate(Template template) throws Exception {
        log.info("Registering " + template.getsubjectline() + " -- ID:" + template.getfromname());
        em.persist(template);
        templateEventSrc.fire(template);
    }
    
    public Template doMerge(Template template) {   
 	   return em.merge(template);
    }
}

