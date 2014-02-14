package com.lambdus.emailengine.admin.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.NotEmpty;

@SuppressWarnings("serial")
@Entity
@XmlRootElement
@Table(name = "targets", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Target implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String queryText;

    @NotNull
    @NotEmpty
    private String association;
    
    @NotNull
    @NotEmpty
    private String dbhost;
    
    @NotNull
    @NotEmpty
    private String dbms;
    
    @NotNull
    @NotEmpty
    private String dbport;
    
    @NotNull
    @NotEmpty
    private String dbuser;
    
    @NotNull
    @NotEmpty
    private String dbpassword;
    

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getqueryText() {
        return queryText;
    }

    public void setqueryText(String queryText) {
        this.queryText = queryText;
    }

    public String getassociation() {
        return association;
    }

    public void setassociation(String association) {
        this.association = association;
    }
    
    

    public String getdbhost() {
        return dbhost;
    }

    public void setdbhost(String dbhost) {
        this.dbhost = dbhost;
    }
    

    public String getdbms() {
        return dbms;
    }

    public void setdbms(String dbms) {
        this.dbms = dbms;
    }
    

    public String getdbport() {
        return dbport;
    }

    public void setdbport(String dbport) {
        this.dbport = dbport;
    }
    

    public String getdbuser() {
        return dbuser;
    }

    public void setdbuser(String dbuser) {
        this.dbuser = dbuser;
    }
    
  
    public String getdbpassword() {
        return dbpassword;
    }

    public void setdbpassword(String dbpassword) {
        this.dbpassword = dbpassword;
    }
    
}

