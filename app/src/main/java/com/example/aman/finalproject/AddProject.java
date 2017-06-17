package com.example.aman.finalproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.aman.finalproject.HttpRequestProcessor.HttpRequestProcessor;
import com.example.aman.finalproject.HttpRequestProcessor.Response;
import com.example.aman.finalproject.apiConfiguration.ApiConfiguration;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Aman on 4/29/2017.
 */

public class AddProject extends Fragment {

    private EditText edt_title, edt_desc,edt_allias_name,edt_project_type,edt_start_date,edt_end_date;
    private Button add_project;
    private String name, passwd;
    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, urlLogin, jsonStringToPost, jsonResponseString;
    private boolean success;
    String title, descr,alliasName,projectType,startDate,endDate;
    int responseData;
    String message;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_new_project, container, false);
        edt_title = (EditText) v.findViewById(R.id.edt_title);
        edt_desc = (EditText) v.findViewById(R.id.edt_desc);
        edt_allias_name= (EditText) v.findViewById(R.id.edt_allias_name);
        edt_project_type= (EditText) v.findViewById(R.id.edt_project_type);
        edt_start_date= (EditText) v.findViewById(R.id.edt_start_date);
        edt_end_date= (EditText) v.findViewById(R.id.edt_end_date);
        add_project = (Button) v.findViewById(R.id.add_project);
        //Initialization
        httpRequestProcessor = new HttpRequestProcessor();

        response = new Response();
        apiConfiguration = new ApiConfiguration();
        //Getting base url
        baseURL = apiConfiguration.getApi();
        urlLogin = baseURL + "ProjectAPI/AddNewProject";

        add_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = edt_title.getText().toString();
                descr = edt_desc.getText().toString();
                alliasName=edt_allias_name.getText().toString();
                projectType=edt_project_type.getText().toString();
                startDate=edt_start_date.getText().toString();
                endDate=edt_end_date.getText().toString();
                boolean invalid=false;
                if(title.equals("")){
                    invalid=true;
                    Toast.makeText(AddProject.this.getActivity(),"Enter Title",Toast.LENGTH_LONG).show();
                }else if (descr.equals("")) {
                    invalid = true;
                    Toast.makeText(AddProject.this.getActivity(), "Enter Description", Toast.LENGTH_LONG).show();
                }else if (alliasName.equals("")) {
                    invalid = true;
                    Toast.makeText(AddProject.this.getActivity(), "Enter Allias Name", Toast.LENGTH_LONG).show();
                }else if (projectType.equals("")) {
                    invalid = true;
                    Toast.makeText(AddProject.this.getActivity(), "Enter Project Type", Toast.LENGTH_LONG).show();
                }else if (startDate.equals("")) {
                    invalid = true;
                    Toast.makeText(AddProject.this.getActivity(), "Enter Start Date", Toast.LENGTH_LONG).show();
                }else if (endDate.equals("")) {
                    invalid = true;
                    Toast.makeText(AddProject.this.getActivity(), "Enter End Date", Toast.LENGTH_LONG).show();
                }
                new ProjectTask().execute(title, descr,alliasName,projectType,startDate,endDate);
            }
        });
        return v;

    }

    private class ProjectTask extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            title = params[0];
            descr = params[1];
            alliasName=params[2];
            projectType=params[3];
            startDate=params[4];
            endDate=params[5];

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("Title", title);
                jsonObject.put("Description", descr);
                jsonObject.put("AlliasName", alliasName);
                jsonObject.put("ProjectType", projectType);
                jsonObject.put("StartDate",startDate);
                jsonObject.put("EndDate",endDate);

                jsonStringToPost = jsonObject.toString();
                response = httpRequestProcessor.pOSTRequestProcessor(jsonStringToPost, urlLogin);
                jsonResponseString = response.getJsonResponseString();


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonResponseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Log.d("Response String", s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                success = jsonObject.getBoolean("success");
                Log.d("Success", String.valueOf(success));
                message = jsonObject.getString("message");
                responseData = jsonObject.getInt("responseData");
                if (responseData == 1) {
                    Toast.makeText(AddProject.this.getActivity(), "Record saved successfully!!", Toast.LENGTH_LONG).show();
                    //startActivity(new Intent(AddProject.this.getActivity(), ProjectFragment.class));
                } else if (responseData == 2) {
                    Toast.makeText(AddProject.this.getActivity(), "Record Unsuccessfully.Please Try Again...", Toast.LENGTH_LONG).show();
                }

                //  Log.d("message", message);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}

