package com.example.findrestaurant;

public class User {
    String res,email,pass,add,res_ID;

    public String getEmail() {
        return email;
    }

    public String getRes_ID() {
        return res_ID;
    }

    public void setRes_ID(String res_ID) {
        this.res_ID = res_ID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User() {
    }

    public User(String res, String email, String pass, String add) {
        this.res = res;
        this.email = email;
        this.pass = pass;
        this.add = add;
    }

    public User(String res, String add) {
        this.res = res;
        this.add = add;
    }

    public String getRes() {
        return res;
    }

    public void setRes(String res) {
        this.res = res;
    }



    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }
}
