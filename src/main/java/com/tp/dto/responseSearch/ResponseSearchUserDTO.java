package com.tp.dto.responseSearch;

import java.io.Serializable;

import com.tp.dto.BaseDTO;

/**
 * Created by user on 21/08/2017.
 */
public class ResponseSearchUserDTO extends BaseDTO implements Serializable {
    private Long id;
    private String userName;
    private String fullName;
    private String email;
    private String permission;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }
}
