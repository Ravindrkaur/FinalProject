package com.example.aman.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.aman.finalproject.HttpRequestProcessor.HttpRequestProcessor;
import com.example.aman.finalproject.HttpRequestProcessor.Response;
import com.example.aman.finalproject.apiConfiguration.ApiConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.R.id.message;

/**
 * Created by Aman on 4/24/2017.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edt_username, edt_password;
    private Button login_admin, login_employee;
    private ImageView img;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String name, passwd;
    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, urlLogin, jsonStringToPost, jsonResponseString;
    private boolean success;
    private int userID;
    private String ErrorMessage;
    private int UserTypeId;
    private String applicationuserid, useridentitykey, usertypeid, usertype, fname, lname, membercode, email, mobile, gender, dob, address, ftname, mtname, designation, uname;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        //Find by view
        img = (ImageView) findViewById(R.id.img);
        edt_username = (EditText) findViewById(R.id.edt_username);
        edt_password = (EditText) findViewById(R.id.edt_password);
        login_employee = (Button) findViewById(R.id.login_employee);

        //Registered
        login_employee.setOnClickListener(this);


        //Initialization
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();

        //Getting base url
        baseURL = apiConfiguration.getApi();
        urlLogin = baseURL + "AccountAPI/GetLoginUser";


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.login_employee:

                //Getting name and password
                name = edt_username.getText().toString();
                passwd = edt_password.getText().toString();

                new LoginTask().execute(name, passwd);
                break;
        }
    }

    public class LoginTask extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            name = params[0];
            passwd = params[1];

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("UserName", name);
                jsonObject.put("Password", passwd);

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
                ErrorMessage = jsonObject.getString("ErrorMessage");

                //  Log.d("message", message);

                if (ErrorMessage.equals("User Authenticated!!")) {
                    //Shared preferences

                    Toast.makeText(LoginActivity.this, "success", Toast.LENGTH_LONG).show();
                    UserTypeId = jsonObject.getInt("UserTypeId");
                    //  startActivity(new Intent(LoginActivity.this,Main2Activity.class));*/
                    if (UserTypeId == 1) {
                        startActivity(new Intent(LoginActivity.this, Main2Activity.class));
                        Toast.makeText(LoginActivity.this, "admin login", Toast.LENGTH_LONG).show();
                    } else if (UserTypeId == 4) {


                        startActivity(new Intent(LoginActivity.this, EmployeeActivity.class));

                        Toast.makeText(LoginActivity.this, "employee login", Toast.LENGTH_LONG).show();
                    } else if (ErrorMessage.equals("Invalid username!! ")) {
                        Toast.makeText(LoginActivity.this, "User name is invalid.Please Try Again...", Toast.LENGTH_LONG).show();
                    } else if (ErrorMessage.equals("Invalid password!! ")) {
                        Toast.makeText(LoginActivity.this, "password is invalid.Please Try Again...", Toast.LENGTH_LONG).show();
                    }
                }

                    applicationuserid = jsonObject.getString("ApplicationUserId");
                    useridentitykey = jsonObject.getString("UserIdentityKey");
                    usertypeid = jsonObject.getString("UserTypeId");
                    usertype = jsonObject.getString("UserType");
                    fname = jsonObject.getString("FName");
                    lname = jsonObject.getString("LName");
                    membercode = jsonObject.getString("MemberCode");
                    email = jsonObject.getString("EmailId");
                    mobile = jsonObject.getString("MobileNo");
                    gender = jsonObject.getString("Gender");
                    dob = jsonObject.getString("DateOfBirth");
                    address = jsonObject.getString("Address");
                    ftname = jsonObject.getString("FatherName");
                    mtname = jsonObject.getString("MotherName");
                    designation = jsonObject.getString("Designation");
                    uname = jsonObject.getString("UserName");
                    sharedPreferences = getSharedPreferences(MyPref.Pref_Name, Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString(MyPref.ApplicationUserId, applicationuserid);
                    editor.putString(MyPref.UserIdentityKey, useridentitykey);
                    editor.putString(MyPref.UserTypeId, usertypeid);
                    editor.putString(MyPref.UserType, usertype);
                    editor.putString(MyPref.FName, fname);
                    editor.putString(MyPref.LName, lname);
                    editor.putString(MyPref.EmailId, email);
                    editor.putString(MyPref.MobileNo, mobile);
                    editor.putString(MyPref.Gender, gender);
                    editor.putString(MyPref.DateOfBirth, dob);
                    editor.putString(MyPref.Address, address);
                    editor.putString(MyPref.FatherName, ftname);
                    editor.putString(MyPref.MotherName, mtname);
                    editor.putString(MyPref.Designation, designation);
                    editor.putString(MyPref.UserName, uname);

                    editor.commit();



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}

