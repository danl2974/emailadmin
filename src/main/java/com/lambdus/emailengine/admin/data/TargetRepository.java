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

@ApplicationScoped
public class TargetRepository {

   @Inject
   private EntityManager em;

   public Target findById(int id) {
       return em.find(Target.class, id);
   }
   
   
   public List<Target> findAllOrderedById(){
       CriteriaBuilder cb = em.getCriteriaBuilder();
       CriteriaQuery<Target> criteria = cb.createQuery(Target.class);
       Root<Target> template = criteria.from(Target.class);
       criteria.select(template).orderBy(cb.desc(template.get("id")));
       return em.createQuery(criteria).getResultList();
	   
   }
   
   
}


