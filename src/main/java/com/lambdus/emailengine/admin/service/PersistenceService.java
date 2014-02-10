package com.lambdus.emailengine.admin.service;

import com.lambdus.emailengine.admin.data.TemplateEditor;
import com.lambdus.emailengine.admin.model.Template;
import com.lambdus.emailengine.admin.model.Target;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import org.jboss.logging.Logger;

@Stateless
public class PersistenceService {
	
	private static final Logger log = Logger.getLogger(PersistenceService.class.getName());

    @Inject
    private EntityManager em;

    public void doNew(Object entity) throws Exception {
        em.persist(entity);
    }
    
    public Object doMerge(Object entity) {   
 	   return em.merge(entity);
    }
	
}
