package com.example.aman.finalproject;

/**
 * Created by Aman on 5/9/2017.
 */

public class EmployeeListSpinner {
    String checkbox,employee_name;

    public EmployeeListSpinner(String checkbox, String employee_name, int memberID) {
        this.checkbox = checkbox;
        this.employee_name = employee_name;

    }

    public String getCheckbox() {
        return checkbox;
    }

    public void setCheckbox(String checkbox) {
        this.checkbox = checkbox;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }
}
