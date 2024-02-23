package com.akapps.paani.Model;

public class User {
    private String name;
    private String profile;
    private String email;
    private String uid;
    private String password;
    public User(){

    }
    public User(String name, String profile, String email, String uid, String password) {
        this.name = name;
        this.profile = profile;
        this.email = email;
        this.uid = uid;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String phone) {
        this.email = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
