package edu.pucmm.url.Entities;

import edu.pucmm.url.Soap.XmlDateFormat;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class Url implements Serializable {
    @Id
    @Column(unique = true)
    private String shortVersion;
    private String originalVersion;
    private String qrVersion;

    @CreationTimestamp
    @XmlJavaTypeAdapter(XmlDateFormat.class)
    private Timestamp createdAt;

    private String anonymousUser;

    @ManyToOne
    @XmlTransient
    private User user;

    @OneToMany(mappedBy = "url", cascade = CascadeType.REMOVE)
    @XmlTransient
    private List<Info> myInfos;

    private String imageBase;

    public Url() {

    }

    public Url(String shortVersion, String originalVersion, String qrVersion, User user, String anonymousUser) {
        this.shortVersion = shortVersion;
        this.originalVersion = originalVersion;
        this.qrVersion = qrVersion;
        this.user = user;
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.anonymousUser = anonymousUser;
    }

    public String getAnonymousUser() {
        return anonymousUser;
    }

    public void setAnonymousUser(String anonymousUser) {
        this.anonymousUser = anonymousUser;
    }

    public String getShortVersion() {
        return shortVersion;
    }

    public void setShortVersion(String shortVersion) {
        this.shortVersion = shortVersion;
    }

    public String getOriginalVersion() {
        return originalVersion;
    }

    public void setOriginalVersion(String originalVersion) {
        this.originalVersion = originalVersion;
    }

    public String getQrVersion() {
        return qrVersion;
    }

    public void setQrVersion(String qrVersion) {
        this.qrVersion = qrVersion;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public List<Info> getMyInfos() {
        return myInfos;
    }

    public void setMyInfos(List<Info> myInfos) {
        this.myInfos = myInfos;
    }

    public String getImageBase() {
        return imageBase;
    }

    public void setImageBase(String imageBase) {
        this.imageBase = imageBase;
    }
}
