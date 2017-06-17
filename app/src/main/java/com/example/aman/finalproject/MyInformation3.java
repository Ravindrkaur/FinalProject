package com.example.aman.finalproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.aman.finalproject.HttpRequestProcessor.HttpRequestProcessor;
import com.example.aman.finalproject.HttpRequestProcessor.Response;
import com.example.aman.finalproject.apiConfiguration.ApiConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by TOSHIBA on 5/19/2017.
 */

public class MyInformation3 extends AppCompatActivity {
    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, getproject;
    private String jsonResponseString;
    private String message;
    private boolean success;
    private MyInformationSingle myInformationSingle;
    private ArrayList<MyInformationSingle> myInformationSingleArrayList;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int memberid;
    long mobile1;
    TextView memid, firstName, lastName, email, mobile, gender, dob, address, fatherName, motherName, designation;
    private String fname, laname, emailid, mob, gen, dobb, addr, fatname, mathname, design;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_information3);
        memid = (TextView) findViewById(R.id.member_Id);
        firstName = (TextView) findViewById(R.id.txtName);
        lastName = (TextView) findViewById(R.id.lstName);
        email = (TextView) findViewById(R.id.txtEmailAddress);
        mobile = (TextView) findViewById(R.id.txtMobileNumber);
        gender = (TextView) findViewById(R.id.gen);
        dob = (TextView) findViewById(R.id.txtdateOfBirth);
        address = (TextView) findViewById(R.id.address);
        fatherName = (TextView) findViewById(R.id.fathern);
        motherName = (TextView) findViewById(R.id.mathern);
        designation = (TextView) findViewById(R.id.designation);
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();

        sharedPreferences = getSharedPreferences(MyPref1.Pref_Name, Context.MODE_PRIVATE);
        // int mid = Integer.parseInt(sharedPreferences.getString(MyPref.UserIdentityKey, null));
        String mid = (sharedPreferences.getString(MyPref1.MemberId, null));
        memberid = Integer.parseInt(mid);


        fname = sharedPreferences.getString(MyPref1.FName, null);
        laname = sharedPreferences.getString(MyPref1.LName, null);
        emailid = sharedPreferences.getString(MyPref1.EmailId, null);
        mob = sharedPreferences.getString(MyPref1.MobileNo, null);
        //mobile1 = Long.parseLong(mob);
        gen = sharedPreferences.getString(MyPref1.Gender, null);
        dobb = sharedPreferences.getString(MyPref1.DateOfBirth, null);
        addr = sharedPreferences.getString(MyPref1.Address, null);
        fatname = sharedPreferences.getString(MyPref1.FatherName, null);
        mathname = sharedPreferences.getString(MyPref1.MotherName, null);
        design = sharedPreferences.getString(MyPref1.Designation, null);
        baseURL = apiConfiguration.getApi();
        getproject = baseURL + "MemberAPI/GetMemberProfile/" + memberid;
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
                        myInformationSingle = new MyInformationSingle(memberid, fname, lname, email, mobile, gender, dob, addr, ftname, mtname, des);
                        myInformationSingleArrayList.add(myInformationSingle);


                    }
                    //   adapter.notifyDataSetChanged();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
