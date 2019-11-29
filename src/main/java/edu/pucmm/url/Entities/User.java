package edu.pucmm.url.Entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;

@Entity
public class User implements Serializable {
    @Id
    private String uid;
    private String username;
    private String name;
    private String password;
    private boolean admin;

    @OneToMany
    private Set<Url> myUrls;

    public User() {

    }

    public User(String uid, String username, String name, String password, boolean admin, Set<Url> myUrls) {
        this.uid = uid;
        this.username = username;
        this.name = name;
        this.password = password;
        this.admin = admin;
        this.myUrls = myUrls;
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

    public Set<Url> getMyUrls() {
        return myUrls;
    }

    public void setMyUrls(Set<Url> myUrls) {
        this.myUrls = myUrls;
    }
}
