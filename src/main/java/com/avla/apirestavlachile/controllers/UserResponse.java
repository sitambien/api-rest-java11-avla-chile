package com.avla.apirestavlachile.controllers;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Datos del usuario actualizado junto con su token de autenticación")
public class UserResponse {

    private Long id;
    private Date created;
    private Date modified;
    private String token;
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Date getCreated() {
        return created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }
    public Date getModified() {
        return modified;
    }
    public void setModified(Date modified) {
        this.modified = modified;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

}
