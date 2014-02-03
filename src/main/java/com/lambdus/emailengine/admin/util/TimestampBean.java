package com.lambdus.emailengine.admin.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
 
@ManagedBean
@RequestScoped
public class TimestampBean {
	
	private String timestamp = "";
 
    public String getTimestamp() {
        return this.timestamp;
    }
    
    public void setTimestamp(String timestamp){
    	this.timestamp = timestamp;
    }
    
    public void current(){
    	Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss aaa");
        sdf.setTimeZone(TimeZone.getTimeZone("EST"));
        this.timestamp = "Template saved at " + sdf.format(date);
    	
    }
}
