package com.example.aman.finalproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.aman.finalproject.R;
import com.example.aman.finalproject.SpinnerListSingle;

import java.util.ArrayList;

/**
 * Created by Aman on 5/9/2017.
 */

public class SpinnerAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater inflater;
    private ArrayList<SpinnerListSingle> spinnerListSingleArrayList;

    public SpinnerAdapter(Context context, ArrayList<SpinnerListSingle> spinnerListSingleArrayList) {
        this.context = context;
        this.spinnerListSingleArrayList = spinnerListSingleArrayList;
    }

    @Override
    public int getCount() {
        return spinnerListSingleArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return spinnerListSingleArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.spinner_layout_items,parent,false);

        TextView txt1= (TextView) convertView.findViewById(R.id.txt_spinner);
        SpinnerListSingle spinnerListSingle=spinnerListSingleArrayList.get(position);
        String name=spinnerListSingle.getProjectname();
        txt1.setText(name);
        return convertView;
    }
}
