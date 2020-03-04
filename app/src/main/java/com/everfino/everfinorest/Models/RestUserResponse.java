package com.everfino.everfinorest.Models;

public class RestUserResponse {
    public int userid;
    public String name;
    public String password;
    public String email;
    public String mobileno;
    public String role;

    public RestUserResponse(int userid, String name, String password, String email, String mobileno, String role) {
        this.userid = userid;
        this.name = name;
        this.password = password;
        this.email = email;
        this.mobileno = mobileno;
        this.role = role;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
