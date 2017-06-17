package com.example.aman.finalproject.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.aman.finalproject.AddProjectMember;
import com.example.aman.finalproject.CheckBoxSingle;
import com.example.aman.finalproject.MemberList;
import com.example.aman.finalproject.ProjectListvalue;
import com.example.aman.finalproject.R;

import java.util.ArrayList;

/**
 * Created by Aman on 4/29/2017.
 */

public class MemberAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<MemberList> memberArrayList;
    private LayoutInflater inflater;

    public MemberAdapter(Context context, ArrayList<MemberList> memberArrayList) {
        this.context = context;
        this.memberArrayList = memberArrayList;
    }

    @Override
    public int getCount() {
        return memberArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return memberArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.member_show_activity, parent, false);

        TextView txt1 = (TextView) convertView.findViewById(R.id.txt_name);
        TextView txt2 = (TextView) convertView.findViewById(R.id.txt_email);


        MemberList memberList = memberArrayList.get(position);

        String name = memberList.getName();
        String emailid = memberList.getEmail();


        txt1.setText(name);
        txt2.setText(emailid);

        return convertView;


    }
}
