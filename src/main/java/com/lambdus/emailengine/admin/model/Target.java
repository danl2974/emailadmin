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
     
    
}

