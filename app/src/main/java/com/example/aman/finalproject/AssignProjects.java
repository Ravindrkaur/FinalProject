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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aman.finalproject.Adapter.Employee_List_Assign_Project_Adapter;
import com.example.aman.finalproject.HttpRequestProcessor.HttpRequestProcessor;
import com.example.aman.finalproject.HttpRequestProcessor.Response;
import com.example.aman.finalproject.apiConfiguration.ApiConfiguration;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Aman on 05-05-2017.
 */

public class AssignProjects extends Fragment {
    private Spinner spinner;
    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, getproject, getEmployee;
    private String jsonResponseString;
    private String message;
    private boolean success;
    private Assignlistvalue layout2;
    private ArrayList<Assignlistvalue> layoutArrayList;
    private AssignListAdapter adapter;
    private ListView lvEmployee;
    private MemberListt memberList;
    private ArrayList<MemberListt> memberListArrayList;
    private Employee_List_Assign_Project_Adapter empAdapter;
    private Button assign_project;
    private String urlAssignProject;
    private int pID;
    String mID;
    int responseData;
    String projectID;
    String selectedMemberID;
    JSONArray memberArray;
    String jsonStringToPost;
    JSONObject jsonObject;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.spinner_list_layout, container, false);

        spinner = (Spinner) v.findViewById(R.id.spinner);
        lvEmployee = (ListView) v.findViewById(R.id.lv_spinner);
        assign_project = (Button) v.findViewById(R.id.assign_project);

        //Initialization`33
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();


        baseURL = apiConfiguration.getApi();
        getproject = baseURL + "ProjectAPI/GetProjectListing";
        getEmployee = baseURL + "MemberAPI/GetApplicationMemberList";
        urlAssignProject = baseURL + "ProjectMemberAssociationAPI/AddNewAssociation";

        layoutArrayList = new ArrayList<>();
        memberListArrayList = new ArrayList<>();
        new SpinnerTask().execute();
        new MyEmployeeListTask().execute();
        adapter = new AssignListAdapter(getActivity(), layoutArrayList);
        empAdapter = new Employee_List_Assign_Project_Adapter(getActivity(), memberListArrayList);
        spinner.setAdapter(adapter);
        lvEmployee.setAdapter(empAdapter);


        //Button Click
        assign_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer buffer = new StringBuffer();
                Assignlistvalue assignlistvalue = (Assignlistvalue) spinner.getSelectedItem();
                pID = assignlistvalue.getProjectID();
                projectID = String.valueOf(pID);
                //Toast.makeText(AssignProjects.this,pID,Toast.LENGTH_LONG).show();
                Log.d("test", String.valueOf(pID));

                //Getting checked members from the listview
                ArrayList<MemberListt> checkedMemberList = empAdapter.getChkBoxArrayList();
                for (MemberListt m : checkedMemberList) {
                    String name = m.getName();
                   int id = m.getId();
                   mID = String.valueOf(id);
                    buffer.append(mID + "/");
                }
                Log.d("test", buffer.toString());
                selectedMemberID = buffer.toString();

                new AssignTask().execute(projectID, selectedMemberID);
            }

        });
        return v;

    }

    public class MyEmployeeListTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            jsonResponseString = httpRequestProcessor.gETRequestProcessor(getEmployee);
            return jsonResponseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                success = jsonObject.getBoolean("success");
                Log.d("Success", String.valueOf(success));
                message = jsonObject.getString("message");
                if (success) {
                    JSONArray responseData = jsonObject.getJSONArray("responseData");
                    for (int i = 0; i < responseData.length(); i++) {
                        JSONObject object = (JSONObject) responseData.get(i);
                        String fname = object.getString("FName");
                        Log.d("FName", fname);
                        String lName = object.getString("LName");
                        String name = fname + " " + lName;
                        String email = object.getString("EmailId");
                        Log.d("EmailId", email);
                        int memberID = object.getInt("MemberId");
                        Log.d("MemberId", String.valueOf(memberID));
                        memberList = new MemberListt(name, email,memberID);
                        memberListArrayList.add(memberList);
                    }
                    empAdapter.notifyDataSetChanged();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    private class SpinnerTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            //JSONObject jsonObject = new JSONObject();


            jsonResponseString = httpRequestProcessor.gETRequestProcessor(getproject);
            return jsonResponseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Response String", s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                success = jsonObject.getBoolean("success");
                Log.d("Success", String.valueOf(success));
                message = jsonObject.getString("message");
                Log.d("mesage", message);


                if (success) {
                    JSONArray responseData = jsonObject.getJSONArray("responseData");
                    for (int i = 0; i < responseData.length(); i++) {
                        JSONObject object = (JSONObject) responseData.get(i);
                        String title = object.getString("Title");
                        int projectID = object.getInt("ProjectId");
                        Log.d("Title", title);
                        layout2 = new Assignlistvalue(title, projectID);

                        layoutArrayList.add(layout2);

                    }
                    adapter.notifyDataSetChanged();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    private class AssignTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            //JSONObject jsonObject = new JSONObject();
            projectID = params[0];
            selectedMemberID = params[1];
            String[] mID = selectedMemberID.split("/");
            int length = mID.length;

            jsonObject = new JSONObject();

            memberArray = new JSONArray();
            for (int i = 0; i < mID.length; i++) {
                JSONObject obj = new JSONObject();
                try {
                    obj.put("ProjectMemberId", mID[i]);
                    memberArray.put(obj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            try {
                jsonObject.put("ProjectId", projectID);
                jsonObject.put("MemberId", "");
                jsonObject.put("StartDate", "");
                jsonObject.put("EndDate", "");
                jsonObject.put("Description", "");
                jsonObject.put("Status", "");
                jsonObject.put("ProjectMemberList", memberArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            jsonStringToPost = jsonObject.toString();
            response = httpRequestProcessor.pOSTRequestProcessor(jsonStringToPost, urlAssignProject);
            jsonResponseString = response.getJsonResponseString();
            return jsonResponseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Response String", s);

           try {
                JSONObject jsonObject = new JSONObject(s);
                success = jsonObject.getBoolean("success");
                Log.d("Success", String.valueOf(success));
               // message = jsonObject.getString("message");
               // Log.d("mesage", message);
               responseData = jsonObject.getInt("responseData");


               if (responseData == 1) {
                   Toast.makeText(AssignProjects.this.getActivity(), "Data loaded successfully", Toast.LENGTH_LONG).show();
               } else if (responseData == 2) {
                   Toast.makeText(AssignProjects.this.getActivity(), "Data not loaded.Please Try Again...", Toast.LENGTH_LONG).show();
               }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
        getActivity().setTitle("Assign Projects");
    }

}

