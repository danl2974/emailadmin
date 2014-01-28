package com.lambdus.emailengine.admin.data;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

import com.lambdus.emailengine.admin.model.Template;

@ApplicationScoped
public class TemplateRepository {

   @Inject
   private EntityManager em;

   public Template findById(int id) {
       return em.find(Template.class, id);
   }

   /*
   public Member findByEmail(String email) {
       CriteriaBuilder cb = em.getCriteriaBuilder();
       CriteriaQuery<Member> criteria = cb.createQuery(Member.class);
       Root<Member> member = criteria.from(Member.class);
       // Swap criteria statements if you would like to try out type-safe criteria queries, a new
       // feature in JPA 2.0
       // criteria.select(member).where(cb.equal(member.get(Member_.email), email));
       criteria.select(member).where(cb.equal(member.get("email"), email));
       return em.createQuery(criteria).getSingleResult();
   }

   public List<Member> findAllOrderedByName() {
       CriteriaBuilder cb = em.getCriteriaBuilder();
       CriteriaQuery<Member> criteria = cb.createQuery(Member.class);
       Root<Member> member = criteria.from(Member.class);
       // Swap criteria statements if you would like to try out type-safe criteria queries, a new
       // feature in JPA 2.0
       // criteria.select(member).orderBy(cb.asc(member.get(Member_.name)));
       criteria.select(member).orderBy(cb.asc(member.get("name")));
       return em.createQuery(criteria).getResultList();
   }
   */
   
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

