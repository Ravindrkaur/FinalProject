package com.example.aman.finalproject;

/**
 * Created by Aman on 4/29/2017.
 */

public class MemberList {
    String name;
    String email;
    public boolean checked;


    public MemberList(String name, String email) {
        this.name = name;
        this.email = email;

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


    public void setChecked(boolean b) {
    }


}
