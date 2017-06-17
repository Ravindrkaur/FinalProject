package com.example.aman.finalproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.aman.finalproject.HttpRequestProcessor.HttpRequestProcessor;
import com.example.aman.finalproject.HttpRequestProcessor.Response;
import com.example.aman.finalproject.apiConfiguration.ApiConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Aman on 5/4/2017.
 */

public class Employee_detail extends Fragment {
    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, getproject;
    private String jsonResponseString;
    private String message;
    private boolean success;
   private EmployeeDetail employeeDetail;
    //private EmployeeDetailAdapter employeeDetailAdapter;
    private ArrayList<EmployeeDetail> employeeDetails;

   @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.employee_detail_items, container, false);


//FrameLayout fm= (FrameLayout) v.findViewById(R.id.frame);

      TextView memberid= (TextView) v.findViewById(R.id.edt_memberid);
       TextView fname= (TextView)v.findViewById(R.id.edt_first_name);
       TextView lname= (TextView) v.findViewById(R.id.edt_lname);
       TextView email= (TextView) v.findViewById(R.id.edt_email);
       TextView mobileno= (TextView) v.findViewById(R.id.edt_mobileno);
       TextView gender= (TextView) v.findViewById(R.id.edt_gender);
       TextView dob= (TextView) v.findViewById(R.id.edt_dob);
       TextView addr= (TextView) v.findViewById(R.id.edt_address);
       TextView ftname= (TextView) v.findViewById(R.id.edt_fatherName);
       TextView mtname= (TextView)v.findViewById(R.id.edt_motherName);
       TextView des= (TextView) v.findViewById(R.id.edt_designation);

       String memberID=memberid.getText().toString();
       String finame=fname.getText().toString();
       //Initialization`33
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();


        baseURL = apiConfiguration.getApi();
        getproject = baseURL + "MemberAPI/GetMemberDetail/4";
         // EmployeeDetail employeeDetail1=new EmployeeDetail();

        new ProjectTask().execute();
          return v;
        }



    public class ProjectTask extends AsyncTask<String, String, String> {
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
                        String memberid = object.getString("MemberId");
                        Log.d("MemberId", memberid);
                        String fname = object.getString("FName");
                        Log.d("FName", fname);
                        String lname = object.getString("LName");
                        Log.d("LName", lname);
                        String email = object.getString("EmailId");
                        Log.d("EmailId", email);
                        String mobileno = object.getString("MobileNo");
                        Log.d("MobileNo", mobileno);
                        String gender= object.getString("Gender");
                        Log.d("Gender", gender);
                        String dob = object.getString("DateOfBirth");
                        Log.d("DateOfBirth", dob);
                        String address = object.getString("Address");
                        Log.d("Address", address);
                        String ftname = object.getString("FatherName");
                        Log.d("FatherName", ftname);
                        String mtname = object.getString("MotherName");
                        Log.d("MotherName", mtname);
                        String des = object.getString("Designation");
                        Log.d("Designation", des);

                          employeeDetail=new EmployeeDetail(memberid,fname);
                       employeeDetails.add(employeeDetail);

                    }
                   // employeeDetailAdapter.notifyDataSetChanged();


                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
