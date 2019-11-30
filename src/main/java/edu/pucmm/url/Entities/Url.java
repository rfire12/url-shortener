package edu.pucmm.url.Entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Url implements Serializable {
    @Id
    @Column(unique = true)
    private String shortVersion;
    private String originalVersion;
    private String qrVersion;
    @CreationTimestamp
    private LocalDateTime createdAt;
    private String anonymousUser;
    @ManyToOne
    private User user;

    public Url() {

    }

    public Url(String shortVersion, String originalVersion, String qrVersion, User user, String anonymousUser) {
        this.shortVersion = shortVersion;
        this.originalVersion = originalVersion;
        this.qrVersion = qrVersion;
        this.user = user;
        this.createdAt = LocalDateTime.now();
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
