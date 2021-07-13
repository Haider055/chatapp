package com.example.chatapp.models;

public class modelforprofile {
    String name,email,password,profilepic,status,uid;


    public modelforprofile(String name, String email, String password, String profilepic, String status,String uid) {
        this.name = name;
        this.uid = uid;
        this.email = email;
        this.password = password;
        this.profilepic = profilepic;
        this.status = status;
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

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
