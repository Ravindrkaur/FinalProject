package com.example.aman.finalproject.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.aman.finalproject.EmployeeListSpinner;
import com.example.aman.finalproject.GetProjectSingle;
import com.example.aman.finalproject.ProjectListvalue;
import com.example.aman.finalproject.R;

import java.util.ArrayList;

/**
 * Created by Aman on 5/17/2017.
 */

public class GetAssignProjectAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<GetProjectSingle> getProjectSingleArrayList;
    private LayoutInflater inflater;

    public GetAssignProjectAdapter(FragmentActivity activity, ArrayList<GetProjectSingle> getProjectSingleArrayList) {
    }

    @Override
    public int getCount() {
        return getProjectSingleArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return getProjectSingleArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.get_project_items,parent,false);

        TextView txt1= (TextView) convertView.findViewById(R.id.txt_employee_name);
        TextView txt2= (TextView) convertView.findViewById(R.id.txt_project_name);


        GetProjectSingle layout=getProjectSingleArrayList.get(position);

        String title1=layout.getEmployeeName();
        String descr=layout.getProjectName();

        txt1.setText(title1);
        txt2.setText(descr);

        return convertView;

    }
}
