package edu.pucmm.url.Entities;

import edu.pucmm.url.Soap.XmlDateFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.sql.Date;

@Entity
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class Info implements Serializable {
    @Id
    private String uid;
    private Date date;

    @ManyToOne
    private Url url;

    private String browser;
    private String os;
    private String country;
    private String ip;

    public Info() {

    }

    public Info(String uid, Date date, Url url, String browser, String os, String country, String ip) {
        this.uid = uid;
        this.date = date;
        this.url = url;
        this.browser = browser;
        this.os = os;
        this.country = country;
        this.ip = ip;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Url getUrl() {
        return url;
    }

    public void setUrl(Url url) {
        this.url = url;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
