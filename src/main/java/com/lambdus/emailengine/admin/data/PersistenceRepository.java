package com.lambdus.emailengine.admin.data;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import com.lambdus.emailengine.admin.model.Target;
import com.lambdus.emailengine.admin.model.Template;

@ApplicationScoped
public class PersistenceRepository {

   @Inject
   private EntityManager em;

   public Template findById(int id) {
       return em.find(Template.class, id);
   }
   
   public Target findTargetById(int id) {
       return em.find(Target.class, id);
   }
   
   public List<Template> findAllOrderedById(){
       CriteriaBuilder cb = em.getCriteriaBuilder();
       CriteriaQuery<Template> criteria = cb.createQuery(Template.class);
       Root<Template> template = criteria.from(Template.class);
       criteria.select(template).orderBy(cb.desc(template.get("id")));
       return em.createQuery(criteria).getResultList();
	   
   }
   
   
   public List<Template> getTemplatesTenSet(int offset){
	   CriteriaBuilder cb = em.getCriteriaBuilder();
       CriteriaQuery<Template> criteria = cb.createQuery(Template.class);  
       Root<Template> template = criteria.from(Template.class);
       criteria.select(template).orderBy(cb.desc(template.get("id")));
       return em.createQuery(criteria).setFirstResult(offset).setMaxResults(10).getResultList();
	   
   }
   
}


