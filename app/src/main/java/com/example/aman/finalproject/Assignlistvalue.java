package com.example.aman.finalproject;

/**
 * Created by Aman on 05-05-2017.
 */

public class Assignlistvalue {


    String title;
    int projectID;

    public Assignlistvalue(String title, int projectID) {
        this.title = title;
        this.projectID = projectID;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    @Override
    public String toString() {
        return title;
    }
}
