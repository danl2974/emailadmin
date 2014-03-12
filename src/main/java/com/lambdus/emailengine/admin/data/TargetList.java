package com.lambdus.emailengine.admin.data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import com.lambdus.emailengine.admin.model.Target;


@ManagedBean(name = "targetList")
@RequestScoped
public class TargetList {
	
	private static final Logger log = Logger.getLogger(TargetList.class.getName());

    @Inject
    private TargetRepository targetRepository;

	private List<Target> targets;
	
	private Map<String,Integer> targetsmap; 
	
	private Target selectedTarget;  
	
    public List<Target> getTargets() {
        return targets;
    }
    
    public Target getSelectedTarget(){
    	return selectedTarget;
    }
	
    public void setSelectedTarget(Target selectedTarget){
    	this.selectedTarget = selectedTarget;
    	
    }
    
    @PostConstruct
    public void  retrieveAllTargets() {    	
    	targets = targetRepository.findAllOrderedById();
    	
    	targetsmap = new HashMap<String, Integer>();
    	for (Target t : this.targets){
    	   targetsmap.put(String.valueOf(t.getid()) + " | " + t.getname() + " | " + t.getassociation(), t.getid());
     	 }
    }
    
    
    public Map<String,Integer> getTargetsmap() {
        return targetsmap;
    }
    


}
