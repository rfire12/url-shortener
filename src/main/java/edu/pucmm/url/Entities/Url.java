package edu.pucmm.url.Entities;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

public class Url implements Serializable {
    @Id
    @Column(unique = true)
    private String shortVersion;
    private String originalVersion;
    private String qrVersion;
    private String latitude;
    private String longitude;

    @ManyToOne
    private User user;

    public Url() {

    }

    public Url(String shortVersion, String originalVersion, String previewVersion, String qrVersion, String latitude, String longitude, User user) {
        this.shortVersion = shortVersion;
        this.originalVersion = originalVersion;
        this.qrVersion = qrVersion;
        this.latitude = latitude;
        this.longitude = longitude;
        this.user = user;
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

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}