package com.example.aman.finalproject;

/**
 * Created by Aman on 5/9/2017.
 */

public class MyInformationSingle2 {
    String memberid,mobil;
    String fname,lname,emailid,gen,dob,addr,ftname,mtname,des;

    public MyInformationSingle2(String memberid, String fname, String lname, String emailid, String mobil, String gen, String dob, String addr, String ftname, String mtname, String des) {
        this.memberid = memberid;
        this.fname = fname;
        this.lname = lname;
        this.emailid = emailid;
        this.mobil = mobil;
        this.gen = gen;
        this.dob = dob;
        this.addr = addr;
        this.ftname = ftname;
        this.mtname = mtname;
        this.des = des;
    }

    public String getMemberid() {
        return memberid;
    }

    public void setMemberid(String memberid) {
        this.memberid = memberid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getMobil() {
        return mobil;
    }

    public void setMobil(String mobil) {
        this.mobil = mobil;
    }

    public String getGen() {
        return gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getFtname() {
        return ftname;
    }

    public void setFtname(String ftname) {
        this.ftname = ftname;
    }

    public String getMtname() {
        return mtname;
    }

    public void setMtname(String mtname) {
        this.mtname = mtname;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }
}
