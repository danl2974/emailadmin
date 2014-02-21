package com.lambdus.emailengine.admin.data;

import java.io.Serializable;
import java.sql.CallableStatement;
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

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.jboss.logging.Logger;



@ManagedBean(name = "batchCampaignReporting")
@RequestScoped
public class BatchCampaignReporting implements Serializable {
	
	
	private static final Logger log = Logger.getLogger(BatchCampaignReporting.class.getName());
	
    private List<Map<String, Object>> rows;
    
    private List<String> columns;
	
    @PostConstruct
	public void fetchReportingData(){
		
	    String jdbcHandle = "jdbc:mysql://localhost:3306/email_engine";
		String dbusername = "dan";
		String dbpassword = "lambdus2200";
		
    	ArrayList<String> columnsList = new ArrayList<String>();
    	ArrayList<Map<String, Object>> rowsList = new ArrayList<Map<String, Object>>();

  	    Connection con = null;
  	    CallableStatement callableStatement = null;
  	    ResultSet rs = null;
  	    
  	    try{
		Class.forName("com.mysql.jdbc.Driver");
	    con = DriverManager.getConnection(jdbcHandle, dbusername, dbpassword);
	    String sproc = "{call getBatchCampaignReporting}";
	    callableStatement = con.prepareCall(sproc);
	    
	    boolean ex = callableStatement.execute();
	    if (ex){
	    	 rs = callableStatement.getResultSet();
	     }
    	
    	ResultSetMetaData rsmd = rs.getMetaData();
    	
    	for(int j = 1; j <= rsmd.getColumnCount(); j++ ){
    		columnsList.add(rsmd.getColumnName(j));
    	}
    	
    	while (rs.next()) {
    		 HashMap<String, Object> recordMap = new HashMap<String, Object>();
    		 for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
    		 {
    			 try{
    			recordMap.put(rs.getMetaData().getColumnName(i), new String(rs.getBytes(i)));
    			log.info("recordMap: " +  rs.getMetaData().getColumnName(i) + "  " + new String(rs.getBytes(i)) );
    			 }catch(Exception e){}
    		 }
    		 rowsList.add(recordMap);
    	  }
    	}
	    catch(Exception e){ log.error(e.getMessage());}	 
    	 
    	this.columns = (List<String>) columnsList;
    	this.rows = (List<Map<String, Object>>) rowsList;				
		
		
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
    

}
