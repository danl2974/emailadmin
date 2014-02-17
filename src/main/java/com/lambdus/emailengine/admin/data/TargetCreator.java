package com.lambdus.emailengine.admin.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.logging.Logger;

import com.lambdus.emailengine.admin.model.Target;
import com.lambdus.emailengine.admin.model.Template;
import com.lambdus.emailengine.admin.service.PersistenceService;
import com.lambdus.emailengine.admin.service.TemplateAddition;

import com.lambdus.emailengine.CredentialSecurityDES;

@ManagedBean(name = "targetCreator")
@RequestScoped
public class TargetCreator implements Serializable {
	
	private static final Logger log = Logger.getLogger(TemplateCreator.class.getName());
	
    @Inject
    private PersistenceService persistenceService;
    
    private String name;
    
    private String association;
    
    private String queryText;
    
    private String dbhost;
    
    private String dbms;
    
    private String dbname;
    
    private String dbport;
    
    private String dbuser;
    
    private String dbpassword;
    
    private List<String> previewdata;
    
    private List<Map<String, Object>> rows;
    
    private List<String> columns;
    
    
    public String getName(){
    	return name;
    	}
    
    public void setName(String name){
        this.name = name;
    	}    
 
    public String getAssociation(){
    	return association;
    	}
    
    public void setAssociation(String association){
        this.association = association;
    	}
    
    public String getQueryText(){
    	return queryText;
    	}
    
    public void setQueryText(String queryText){
        this.queryText = queryText;
    	}
    
    
    public String getDbhost() {
        return dbhost;
    }

    public void setDbhost(String dbhost) {
        this.dbhost = dbhost;
    }
    

    public String getDbms() {
        return dbms;
    }

    public void setDbms(String dbms) {
        this.dbms = dbms;
    }
    
    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }    
    

    public String getDbport() {
        return dbport;
    }

    public void setDbport(String dbport) {
        this.dbport = dbport;
    }
    

    public String getDbuser() {
        return dbuser;
    }

    public void setDbuser(String dbuser) {
        this.dbuser = dbuser;
    }
    
  
    public String getDbpassword() {
        return dbpassword;
    }

    public void setDbpassword(String dbpassword) {
        this.dbpassword = dbpassword;
    }
    
    
    public List<String> getPreviewdata() {
        return previewdata;
    }

    public void setPreviewdata(List<String> previewdata) {
        this.previewdata =  previewdata;
    }   
    
    public List<Map<String, Object>> getRows() {
        return rows;
    }

    public void setRows(List<Map<String, Object>> rows) {
        this.rows =  rows;
    }  
    
    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns =  columns;
    } 
     
    
    public void addNew(){
    	try{
    	Target t = new Target();
    	t.setname(this.name);
    	t.setassociation(this.association);
    	t.setqueryText(this.queryText);
    	t.setdbhost(this.dbhost);
    	t.setdbms(this.dbms);
    	t.setdbport(this.dbport);
    	t.setdbname(this.dbname);
    	t.setdbuser(this.dbuser);
    	
    	CredentialSecurityDES csDes = new CredentialSecurityDES();
    	t.setdbpassword(csDes.encrypt(this.dbpassword));
    	
    	persistenceService.doNew(t);
    	}
    	 catch (Exception e) {
    		 log.info(e.getMessage());
    	 }
    }
    
    
    public enum JdbcDriver {
    	sqlserver, mysql, postgresql
    }
    
    public void fetchPreviewData(){
    	/*
    	Enumeration<Driver> drivers1 = DriverManager.getDrivers();
    	while(drivers1.hasMoreElements()){
    		log.info("First driver check " + (Driver) drivers1.nextElement() );
    		}
    	*/	
  	    String jdbcFormat;
  	    Connection con = null;
    	try{
    	  JdbcDriver driver = JdbcDriver.valueOf(this.dbms);
    	  switch(driver)
    	   {
    	   case sqlserver: 
    		   Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
    	       jdbcFormat = String.format("jdbc:%s://%s:%s;database=%s;user=%s;password=%s;", this.dbms, this.dbhost, this.dbport, this.dbname, this.dbuser, this.dbpassword);
    	       con = DriverManager.getConnection(jdbcFormat);
    	   break;
    	   case mysql: 
    		   Class.forName("com.mysql.jdbc.Driver");
    	       jdbcFormat = String.format("jdbc:%s://%s:%s/%s", this.dbms, this.dbhost, this.dbport, this.dbname);
    	       con = DriverManager.getConnection(jdbcFormat, this.dbuser, this.dbpassword);
    	   break;
    	   case postgresql:
    		   Class.forName("org.postgresql.Driver");
    	       jdbcFormat = String.format("jdbc:%s://%s:%s/%s", this.dbms, this.dbhost, this.dbport, this.dbname);
    	       con = DriverManager.getConnection(jdbcFormat, this.dbuser, this.dbpassword);
    	    break;
    	   }
    	}
    	catch(ClassNotFoundException cnfe){ log.error(cnfe.getMessage());}
    	catch(SQLException sqle){ log.error(sqle.getMessage());}
    	
    	ArrayList<String> previewList = new ArrayList<String>();
    	ArrayList<String> columnsList = new ArrayList<String>();
    	ArrayList<Map<String, Object>> rowsList = new ArrayList<Map<String, Object>>();
    	try{
    	/*
        	Enumeration<Driver> drivers2 = DriverManager.getDrivers();
        	while(drivers2.hasMoreElements()){
        		log.info("Second driver check " +  (Driver) drivers2.nextElement() );
        		}   		
    	*/	
    	Statement stmt = con.createStatement();
    	ResultSet rs = stmt.executeQuery(this.queryText);
    	
    	ResultSetMetaData rsmd = rs.getMetaData();
    	
    	for(int j = 1; j <= rsmd.getColumnCount(); j++ ){
    		columnsList.add(rsmd.getColumnName(j));
    	}

    	int counter = 0;
    	
    	while (rs.next() && counter <= 20) {
    		 String record = "";
    		 HashMap<String, Object> recordMap = new HashMap<String, Object>();
    		 for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
    		 {
    			recordMap.put(rs.getMetaData().getColumnName(i), new String(rs.getBytes(i)));
    	        record += (new String(rs.getBytes(i)) + "  ");
    		 }
    		 previewList.add(record);
    		 rowsList.add(recordMap);
    		 counter ++;
    	}
    	}catch(Exception e){ log.info(e.getMessage());}	 
    	 
    	this.previewdata = (List<String>) previewList;
    	this.columns = (List<String>) columnsList;
    	this.rows = (List<Map<String, Object>>) rowsList;
    	log.info("MAP " + this.rows.size() + "  " + this.previewdata.size());
    }
    
}

