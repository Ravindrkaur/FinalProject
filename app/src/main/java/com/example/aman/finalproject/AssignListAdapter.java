package com.example.aman.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



import java.util.ArrayList;

/**
 * Created by katyal on 05-05-2017.
 */

public class AssignListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Assignlistvalue> assignlistvalueArrayList;
    private LayoutInflater inflater;

    public AssignListAdapter(Context context, ArrayList<Assignlistvalue> assignlistvalueArrayList) {
        this.context = context;
        this.assignlistvalueArrayList = assignlistvalueArrayList;
    }



    @Override
    public int getCount() {
        return assignlistvalueArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return assignlistvalueArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.spinner_layout_items,parent,false);
        // convertView=inflater.inflate(R.layout.layout_activity,parent,false);

        TextView txt1= (TextView) convertView.findViewById(R.id.txt_spinner);

        Assignlistvalue layout=assignlistvalueArrayList.get(position);

        String title1=layout.getTitle();


        txt1.setText(title1);

        return convertView;


    }
}
