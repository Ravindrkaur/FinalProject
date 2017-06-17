package com.example.aman.finalproject.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.aman.finalproject.MemberListt;
import com.example.aman.finalproject.R;

import java.util.ArrayList;

/**
 * Created by katyal on 05-05-2017.
 */

public class Employee_List_Assign_Project_Adapter extends BaseAdapter {
    private Context context;
    private ArrayList<MemberListt> memberListArrayList;
    private LayoutInflater inflater;
    private ArrayList<MemberListt> chkBoxArrayList;
    private MemberListt s;

    public Employee_List_Assign_Project_Adapter(Context context, ArrayList<MemberListt> memberListArrayList) {
        this.context = context;
        this.memberListArrayList = memberListArrayList;

    }


    @Override
    public int getCount() {
        return memberListArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return memberListArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.employee_layout_list_items, viewGroup, false);

        TextView txtEmployeeName = (TextView) view.findViewById(R.id.txt_employee);
        final CheckBox checkBox = (CheckBox) view.findViewById(R.id.chk_box);


        final MemberListt memberList = memberListArrayList.get(i);


        txtEmployeeName.setText(memberList.getName());


        checkBox.setOnCheckedChangeListener(myChecked);
        checkBox.setTag(i);
        checkBox.setChecked(memberList.checked);


        chkBoxArrayList = new ArrayList<>();

        return view;
    }

    MemberListt getmemberlist(int pos) {
        return ((MemberListt) getItem(pos));
    }

    public ArrayList<MemberListt> getChkBoxArrayList() {
        return chkBoxArrayList;
    }

    CompoundButton.OnCheckedChangeListener myChecked = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


            if (b) {
                int pos = (int) compoundButton.getTag();
                s = memberListArrayList.get(pos);
                s.setChecked(b);
                chkBoxArrayList.add(s);
            } else {
                int pos = (int) compoundButton.getTag();
                s = memberListArrayList.get(pos);

                if (chkBoxArrayList.contains(s)) {
                    chkBoxArrayList.remove(s);
                }
            }
        }
    };
}


      /*  MemberList memberList = memberListArrayList.get(i);
        String name = memberList.getName();

        txtName.setText(name);



        return view;
    }
}*/