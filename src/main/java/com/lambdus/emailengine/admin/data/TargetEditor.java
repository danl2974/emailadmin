package com.lambdus.emailengine.admin.data;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import javax.servlet.http.HttpServletRequest;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lambdus.emailengine.admin.model.Target;
import com.lambdus.emailengine.admin.model.Template;
import com.lambdus.emailengine.admin.service.PersistenceService;
import com.lambdus.emailengine.admin.service.TemplateAddition;

import com.lambdus.emailengine.CredentialSecurityDES;

import org.jboss.logging.Logger;

@ManagedBean(name = "targetEditor")
@ViewScoped
public class TargetEditor implements Serializable {
	
	private static final Logger log = Logger.getLogger(TargetEditor.class.getName());

    @Inject
    private PersistenceRepository persistenceRepository;
    
    @Inject
    private FacesContext facesContext;
    
    //@EJB
    @Inject
    private PersistenceService persistenceService;
    
    private String name;
    
    private String association;
    
    private String queryText;
    
    private Target target;
    
    private String targetString;
    
    private String dbhost;
    
    private String dbms;
    
    private String dbname;
    
    private String dbport;
    
    private String dbuser;
    
    private String dbpassword;
    
    private List<Map<String, Object>> rows;
    
    private List<String> columns;
    

    @PostConstruct
    public void  fetchTargetById() {
        HttpServletRequest req = (HttpServletRequest) facesContext.getCurrentInstance().getExternalContext().getRequest();
        String tid = ( req.getParameter("t") == null ) ?  "0" : req.getParameter("t"); 
        Target t = persistenceRepository.findTargetById(Integer.valueOf(tid));
        this.target = t;
        this.name = t.getname();
        this.association = t.getassociation();
        this.queryText = t.getqueryText();
        this.targetString = String.valueOf(this.target.getid());
        this.dbhost = t.getdbhost(); 
        this.dbms = t.getdbms();
        this.dbname = t.getdbname();
        this.dbport = t.getdbport();
        this.dbuser = t.getdbuser();
        this.dbpassword = "";
        log.info("db pw: " + this.dbpassword);
        
    }
 
    
    public String getTargetString(){
    	return targetString;
    	}
    
    public void setTargetString(String targetString){
    	this.targetString = targetString;
    	}
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
        this.target.setname(name);
        persistenceService.doMerge(target);
    }
    
    public String getAssociation(){
    	return association;
    	}
    
    public void setAssociation(String association){
        this.association = association;
        this.target.setassociation(association);
        persistenceService.doMerge(target);
    	}
    
    public String getQueryText(){
    	return queryText;
    	}
    
    public void setQueryText(String queryText){
        this.queryText = queryText;
        this.target.setqueryText(queryText);
        persistenceService.doMerge(target);
    	}
    
    public String getDbhost() {
        return dbhost;
    }

    public void setDbhost(String dbhost) {
        this.dbhost = dbhost;
        this.target.setdbhost(dbhost);
        persistenceService.doMerge(target);
        
    }
    

    public String getDbms() {
        return dbms;
    }

    public void setDbms(String dbms) {
        this.dbms = dbms;
        this.target.setdbms(dbms);
        persistenceService.doMerge(target);
    }
 
    public String getDbname() {
        return dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
        this.target.setdbname(dbname);
        persistenceService.doMerge(target);
    }    

    public String getDbport() {
        return dbport;
    }

    public void setDbport(String dbport) {
        this.dbport = dbport;
        this.target.setdbport(dbport);
        persistenceService.doMerge(target);
    }
    

    public String getDbuser() {
        return dbuser;
    }

    public void setDbuser(String dbuser) {
        this.dbuser = dbuser;
        this.target.setdbuser(dbuser);
        persistenceService.doMerge(target);
    }
    
    public String getDbpassword() {
        return dbpassword;
    }

    public void setDbpassword(String dbpassword) {
        this.dbpassword = dbpassword;
        this.target.setdbpassword(encryptPw(dbpassword));
        persistenceService.doMerge(target);
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
    
    
    public enum JdbcDriver {
    	sqlserver, mysql, postgresql
    }
    
    
    public void fetchpreview(){

  	    String jdbcFormat;
  	    Connection con = null;
        /*
  	    CredentialSecurityDES csDes = null;
		try {
			csDes = new CredentialSecurityDES();
			log.info("called fetchPreview in TargetEditor decryptpw: " + csDes.decrypt(this.dbpassword));
		} catch (Exception e) {
			e.getMessage();
		}
		*/
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
    	
    	ArrayList<String> columnsList = new ArrayList<String>();
    	ArrayList<Map<String, Object>> rowsList = new ArrayList<Map<String, Object>>();
    	try{
    	Statement stmt = con.createStatement();
    	ResultSet rs = stmt.executeQuery(this.queryText);
    	
    	ResultSetMetaData rsmd = rs.getMetaData();
    	
    	for(int j = 1; j <= rsmd.getColumnCount(); j++ ){
    		columnsList.add(rsmd.getColumnName(j));
    	}

    	int counter = 0;
    	
    	while (rs.next() && counter <= 20) {

    		 HashMap<String, Object> recordMap = new HashMap<String, Object>();
    		 for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
    		 {
    			recordMap.put(rs.getMetaData().getColumnName(i), new String(rs.getBytes(i)));
    		 }
    		 rowsList.add(recordMap);
    		 counter ++;
    	}
    	}catch(Exception e){ log.info(e.getMessage());}	 
    	 
    	this.columns = (List<String>) columnsList;
    	this.rows = (List<Map<String, Object>>) rowsList;
    	
    }    
    
    
    private String encryptPw(String pw){
    	
    	CredentialSecurityDES csDes = null;
    	String encPw = null;
        try {
			csDes = new CredentialSecurityDES();
			encPw = csDes.encrypt(pw);
		} catch (Exception e) {
			log.error(e.getMessage());
			return encPw;
		}
        return encPw;
        		
    }
    
}
