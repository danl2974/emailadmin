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
@Table(name = "templates", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class Template implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @NotNull
    @NotEmpty
    private String fromname;

    @NotNull
    @NotEmpty
    private String fromaddress;

    @NotNull
    @NotEmpty
    private String subjectline;
    
    @NotNull
    @NotEmpty
    private String creative;

    public int getid() {
        return id;
    }

    public void setid(int id) {
        this.id = id;
    }

    public String getfromname() {
        return fromname;
    }

    public void setfromname(String name) {
        this.fromname = name;
    }

    public String getfromaddress() {
        return fromaddress;
    }

    public void setfromaddress(String email) {
        this.fromaddress = email;
    }

    public String getsubjectline() {
        return subjectline;
    }

    public void setsubjectline(String subjectline) {
        this.subjectline = subjectline;
    }
    public String getcreative() {
        return creative;
    }

    public void setcreative(String creative) {
        this.creative = creative;
    }    
    
    
}

