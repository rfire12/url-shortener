package edu.pucmm.url.Entities;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

@Entity
@XmlType
@XmlAccessorType(XmlAccessType.FIELD)
public class User implements Serializable {
    @Id
    private String uid;
    @Column(unique = true)
    private String username;
    private String name;
    private String password;
    private boolean admin;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Url> myUrls;

    public User() {

    }

    public User(String uid, String username, String name, String password, boolean admin) {
        this.uid = uid;
        this.username = username;
        this.name = name;
        this.password = password;
        this.admin = admin;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public List<Url> getMyUrls() {
        return myUrls;
    }

    public void setMyUrls(List<Url> myUrls) {
        this.myUrls = myUrls;
    }
}
