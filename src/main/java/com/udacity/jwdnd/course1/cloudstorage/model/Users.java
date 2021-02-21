package com.udacity.jwdnd.course1.cloudstorage.model;

public class Users {

    private Integer userId;
    private String username;
    private String password;
    private String salt;
    private String firstname;
    private String lastname;

    public Users() {
    }

    public Users(String username, String password, String salt, String firstname, String lastname) {
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}