package com.test.recipes.domain.entities;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Entity Persistent_logins
 * Used to manage remember-me functionality.
 */
@Entity
public class Persistent_logins {

    @Id
    @Column(name="series", length=64)
    private String series;

    @Column(name="username", nullable=false, length=64)
    private String username;
    
    @Column(name="token", nullable=false, length=64)
    private String token;
    
    @Column(name="last_used", nullable=false)
    private Timestamp lastUsed;
    
    public Persistent_logins() {}

    public Persistent_logins(String series, String username, String token, Timestamp lastUsed) {
        this.series = series;
        this.username = username;
        this.token = token;
        this.lastUsed = lastUsed;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Timestamp lastUsed) {
        this.lastUsed = lastUsed;
    }
    
}
