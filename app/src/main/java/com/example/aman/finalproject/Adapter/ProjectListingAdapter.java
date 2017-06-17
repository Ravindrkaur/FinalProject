package com.example.aman.finalproject.Adapter;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.aman.finalproject.ProjectListvalue;
import com.example.aman.finalproject.R;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by Aman on 4/27/2017.
 */

public class ProjectListingAdapter extends BaseAdapter {

private Context context;
    private ArrayList<ProjectListvalue> projectListvalueArrayList;
    private LayoutInflater inflater;

    public ProjectListingAdapter(Context context, ArrayList<ProjectListvalue> projectListvalueArrayList) {
        this.context = context;
        this.projectListvalueArrayList = projectListvalueArrayList;
    }

    @Override
    public int getCount() {
        return projectListvalueArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return projectListvalueArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.layout_activity,parent,false);

        TextView txt1= (TextView) convertView.findViewById(R.id.txt_name);
        TextView txt2= (TextView) convertView.findViewById(R.id.txt_desc);


        ProjectListvalue layout=projectListvalueArrayList.get(position);

        String title1=layout.getTitle();
        String descr=layout.getDescription();

        txt1.setText(title1);
        txt2.setText(descr);

        return convertView;


    }
}
