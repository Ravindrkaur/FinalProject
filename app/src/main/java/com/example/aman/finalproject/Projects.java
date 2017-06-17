package com.example.aman.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by Aman on 4/27/2017.
 */

public class Projects extends Fragment {
    private Button btn1,btn2,btn3,btn4;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v= inflater.inflate(R.layout.projects, container, false);


        btn1=(Button)v.findViewById(R.id.button1);
        btn2=(Button)v.findViewById(R.id.button2);
        btn3=(Button)v.findViewById(R.id.button3);
        btn4=(Button)v.findViewById(R.id.button4);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment= new ProjectFragment();
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransation=fragmentManager.beginTransaction();
                fragmentTransation.replace(R.id.content_fragment,fragment);
                fragmentTransation.addToBackStack(null);
                fragmentTransation.commit();

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Fragment fragment= new AddProject();
              FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
               FragmentTransaction fragmentTransation=fragmentManager.beginTransaction();
                fragmentTransation.replace(R.id.content_fragment,fragment);
                fragmentTransation.addToBackStack(null);
                fragmentTransation.commit();

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment= new AssignProjects();
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransation=fragmentManager.beginTransaction();
                fragmentTransation.replace(R.id.content_fragment,fragment);
                fragmentTransation.addToBackStack(null);
                fragmentTransation.commit();

            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=new EmployeeGetAssignProject();
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransation=fragmentManager.beginTransaction();
                fragmentTransation.replace(R.id.content_fragment,fragment);
                fragmentTransation.addToBackStack(null);
                fragmentTransation.commit();

            }
        });

        return v;
    }





    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Projects");
    }

}
