package com.example.aman.finalproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.aman.finalproject.EmployeeListSpinner;
import com.example.aman.finalproject.MemberList;
import com.example.aman.finalproject.R;
import com.example.aman.finalproject.SpinnerListSingle;

import java.util.ArrayList;

/**
 * Created by Aman on 5/15/2017.
 */

public class




EmployeeDetailAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<EmployeeListSpinner> employeeListSpinnerArrayList;
    private LayoutInflater inflater;

    public EmployeeDetailAdapter(Context context, ArrayList<EmployeeListSpinner> employeeListSpinnerArrayList) {
        this.context = context;
        this.employeeListSpinnerArrayList = employeeListSpinnerArrayList;
    }

    @Override
    public int getCount() {
        return employeeListSpinnerArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return employeeListSpinnerArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.employee_layout_list_items,parent,false);

        TextView txt1= (TextView) convertView.findViewById(R.id.txt_employee);
        TextView txt2= (TextView) convertView.findViewById(R.id.chk_box);
        EmployeeListSpinner spinnerListSingle=employeeListSpinnerArrayList.get(position);
        String name=spinnerListSingle.getEmployee_name();
        String chk=spinnerListSingle.getCheckbox();

        txt1.setText(name);
        txt2.setText(chk);
        return convertView;
    }
}
