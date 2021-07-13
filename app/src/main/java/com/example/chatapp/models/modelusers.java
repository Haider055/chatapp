package com.example.chatapp.models;

public class modelusers {
    String uid,name,email,password,status,profilepic;

    public modelusers(String uid, String name, String email, String password, String status, String profilepic) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.password = password;
        this.status = status;
        this.profilepic = profilepic;
    }

    public modelusers() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }
}
