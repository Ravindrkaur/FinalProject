package com.example.aman.finalproject;

/**
 * Created by Aman on 4/27/2017.
 */

public class ProjectListvalue  {
    String title,description,pID;

    public ProjectListvalue(String title, String description, String pID) {
        this.title = title;
        this.description = description;
        this.pID = pID;
    }

    public String getpID() {
        return pID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
