package com.example.aman.finalproject;

import android.content.Context;
import android.content.SharedPreferences;
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
 * Created by Aman on 5/15/2017.
 */

public class OneEmployeeeeDetail  extends Fragment {
    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, getproject;
    private String jsonResponseString;
    private String message;
    private boolean success;
    private EmployeeArrayList myInformationSingle;
    private ArrayList<EmployeeArrayList> myInformationSingleArrayList;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int memberid;
    long mobile1;
    TextView memid, firstName, lastName, email, mobile, gender, dob, address, fatherName, motherName, designation;
    private String fname, laname, emailid, mob, gen, dobb, addr, fatname, mathname, design;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.employeee_list_item, container, false);
        memid = (TextView) v.findViewById(R.id.member_Id);
        firstName = (TextView) v.findViewById(R.id.txtName);
        lastName = (TextView) v.findViewById(R.id.lstName);
        email = (TextView) v.findViewById(R.id.txtEmailAddress);
        mobile = (TextView) v.findViewById(R.id.txtMobileNumber);
        gender = (TextView) v.findViewById(R.id.gen);
        dob = (TextView) v.findViewById(R.id.txtdateOfBirth);
        address = (TextView) v.findViewById(R.id.address);
        fatherName = (TextView) v.findViewById(R.id.fathern);
        motherName = (TextView) v.findViewById(R.id.mathern);
        designation = (TextView) v.findViewById(R.id.designation);
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();

        sharedPreferences = getActivity().getSharedPreferences(MyPref.Pref_Name, Context.MODE_PRIVATE);
        // int mid = Integer.parseInt(sharedPreferences.getString(MyPref.UserIdentityKey, null));
        String mid = (sharedPreferences.getString(MyPref.UserTypeId, null));
        memberid = Integer.parseInt(mid);


        fname = sharedPreferences.getString(MyPref.FName, null);
        laname = sharedPreferences.getString(MyPref.LName, null);
        emailid = sharedPreferences.getString(MyPref.EmailId, null);
        mob = sharedPreferences.getString(MyPref.MobileNo, null);
        //mobile1 = Long.parseLong(mob);
        gen = sharedPreferences.getString(MyPref.Gender, null);
        dobb = sharedPreferences.getString(MyPref.DateOfBirth, null);
        addr = sharedPreferences.getString(MyPref.Address, null);
        fatname = sharedPreferences.getString(MyPref.FatherName, null);
        mathname = sharedPreferences.getString(MyPref.MotherName, null);
        design = sharedPreferences.getString(MyPref.Designation, null);
        baseURL = apiConfiguration.getApi();
        getproject = baseURL + "MemberAPI/GetMemberDetail/4";
        myInformationSingleArrayList = new ArrayList<>();
        // adapter = new ProjectListingAdapter(getActivity(), layoutArrayList);
        //  lv2.setAdapter(adapter);
        // registerForContextMenu(lv2);
        memid.setText(String.valueOf(memberid));
        firstName.setText(fname);
        lastName.setText(laname);
        email.setText(emailid);
        //
        mobile.setText(mob);
        gender.setText(gen);
        dob.setText(dobb);
        address.setText(addr);
        fatherName.setText(fatname);
        motherName.setText(mathname);
        designation.setText(design);

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
                        String mobile = object.getString("MobileNo");
                        Log.d("MobileNo", mobile);
                        String gender = object.getString("Gender");
                        Log.d("Gender", gender);
                        String dob = object.getString("DateOfBirth");
                        Log.d("DateOfBirth", dob);
                        String addr = object.getString("Address");
                        Log.d("Address", addr);
                        String ftname = object.getString("FatherName");
                        Log.d("FatherName", ftname);
                        String mtname = object.getString("MotherName");
                        Log.d("MotherName", mtname);
                        String des = object.getString("Designation");
                        Log.d("Designation", des);
                        myInformationSingle = new EmployeeArrayList(memberid, fname);
                        myInformationSingleArrayList.add(myInformationSingle);


                    }
                    //   adapter.notifyDataSetChanged();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("My Information");
    }

}
