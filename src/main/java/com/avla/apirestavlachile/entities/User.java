package com.avla.apirestavlachile.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@javax.persistence.Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotEmpty
    private String name;
    
    @NotEmpty
    @Email
    private String email;
    
    @NotEmpty
    private String password;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<Phone> phones;

    // created
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;

    @PrePersist
    public void prePersist() {
        Date now = new Date();
        created = now;
        modified = now;
    }

    // modified
    @Temporal(TemporalType.TIMESTAMP)
    private Date modified;

    @PreUpdate
    public void preUpdate() {
        modified = new Date();
    }

    // Futuro token
    private String token = "token";

    // CONSTRUCTOR
    public User() {
        this.phones = new ArrayList<>();
    }

    // GETTERS Y SETTERS

    // Id
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    // Name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // Email
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    // Password
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    // Phones
    public List<Phone> getPhones() {
        return phones;
    }
    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    // created
    public Date getCreated() {
        return created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }

    // modified
    public Date getModified() {
        return modified;
    }
    public void setModified(Date modified) {
        this.modified = modified;
    }

    // token
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

}
