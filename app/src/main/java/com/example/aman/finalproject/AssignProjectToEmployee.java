package com.example.aman.finalproject;

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

import com.example.aman.finalproject.Adapter.EmployeeDetailAdapter;
import com.example.aman.finalproject.Adapter.SpinnerAdapter;
import com.example.aman.finalproject.HttpRequestProcessor.HttpRequestProcessor;
import com.example.aman.finalproject.HttpRequestProcessor.Response;
import com.example.aman.finalproject.apiConfiguration.ApiConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Aman on 5/15/2017.
 */

public class AssignProjectToEmployee extends Fragment {
    private Spinner spinner;
    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, getproject, getEmployee;
    private String jsonResponseString;
    private String message;
    private boolean success;
    private SpinnerListSingle spinnerListSingle;
    private ArrayList<SpinnerListSingle> spinnerListSingleArrayList;
    private SpinnerAdapter spinnerAdapter;
    private ListView lvEmployee;
    private EmployeeListSpinner employeeListSpinner;
    private ArrayList<EmployeeListSpinner> memberListArrayList;
    private EmployeeDetailAdapter employeeDetailAdapter;
    private Button assign_project;
    private String url3;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.spinner_list_layout, container, false);

        spinner = (Spinner) v.findViewById(R.id.spinner);
        lvEmployee = (ListView) v.findViewById(R.id.lv_spinner);
        assign_project=(Button)v.findViewById(R.id.assign_project);

        //Initialization`33
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();


        baseURL = apiConfiguration.getApi();
        getproject = baseURL + "ProjectAPI/GetProjectListing";
        getEmployee = baseURL + "MemberAPI/GetApplicationMemberList";
        url3 = baseURL + "ProjectMemberAssociationAPI/AddNewAssociation";

        spinnerListSingleArrayList = new ArrayList<>();
        memberListArrayList = new ArrayList<>();
        new SpinnerTask().execute();
        new MyEmployeeListTask().execute();
        //new AssignProjects.SpinnerTask().execute();
        spinnerAdapter = new SpinnerAdapter(getActivity(), spinnerListSingleArrayList);
        employeeDetailAdapter = new EmployeeDetailAdapter(getActivity(), memberListArrayList);
        spinner.setAdapter(spinnerAdapter);
        lvEmployee.setAdapter(employeeDetailAdapter);


        //Button Click
        assign_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                new AssignTask().execute();
            }

        });
        return v;

    }

    public class MyEmployeeListTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
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
                        employeeListSpinner = new EmployeeListSpinner(name, email, memberID);
                        memberListArrayList.add(employeeListSpinner);
                    }
                    employeeDetailAdapter.notifyDataSetChanged();

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void execute() {
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
                        spinnerListSingle = new SpinnerListSingle(title, projectID);

                        spinnerListSingleArrayList.add(spinnerListSingle);

                    }
                    spinnerAdapter.notifyDataSetChanged();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        public void execute() {
        }
    }

    private class AssignTask extends AsyncTask<String, String, String> {
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





                    }
                    spinnerAdapter.notifyDataSetChanged();
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
