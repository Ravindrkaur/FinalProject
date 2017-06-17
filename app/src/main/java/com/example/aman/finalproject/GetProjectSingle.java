package com.example.aman.finalproject;

/**
 * Created by Aman on 5/17/2017.
 */

public class GetProjectSingle {
    String projectName,EmployeeName;

    public GetProjectSingle(String projectName, String employeeName) {
        this.projectName = projectName;
        EmployeeName = employeeName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }
}
