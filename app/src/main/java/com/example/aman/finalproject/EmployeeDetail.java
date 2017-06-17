package com.example.aman.finalproject;

/**
 * Created by Aman on 5/4/2017.
 */

public class EmployeeDetail {
    String fname,lname,email,mobileno,gender,dob,address,fatherNmae,motherName,degination;
    String memberid;

    public EmployeeDetail(String memberid, String fname) {
        this.memberid = memberid;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.mobileno = mobileno;
        this.gender = gender;
        this.dob = dob;
        this.address = address;
        this.fatherNmae = fatherNmae;
        this.motherName = motherName;
        this.degination=degination;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFatherNmae() {
        return fatherNmae;
    }

    public void setFatherNmae(String fatherNmae) {
        this.fatherNmae = fatherNmae;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getDegination() {
        return degination;
    }

    public void setDegination(String degination) {
        this.degination = degination;
    }
}
