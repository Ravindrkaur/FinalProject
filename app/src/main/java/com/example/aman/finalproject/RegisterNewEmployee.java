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
 * Created by Aman on 5/1/2017.
 */

public class RegisterNewEmployee  extends Fragment{

    private EditText fst_name, lst_name, designation,address, email_id, mobile_no, gender, date_of_birth, father_name, mother_name, user_name, password, created_by, modified_by;
    private Button register;
    private String fname, lname,dgi,addr, email, mob, gen, dob, ftname, mtname, username, passw, create, modify;

    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, urlRegister;
    private String jsonPostString, jsonResponseString;
    private String message;
    private boolean success;
    int responseData;
    private int userID;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.activity_register_activity ,container, false);

        fst_name = (EditText)v.findViewById(R.id.fst_name);
        lst_name = (EditText)v.findViewById(R.id.lst_name);
         designation= (EditText) v.findViewById(R.id.designation);
        address = (EditText)v.findViewById(R.id.address);
        email_id = (EditText)v.findViewById(R.id.email_id);
        gender = (EditText)v.findViewById(R.id.gender);
        mobile_no = (EditText)v.findViewById(R.id.mobile_no);
        date_of_birth = (EditText)v.findViewById(R.id.date_of_birth);
        father_name = (EditText)v.findViewById(R.id.father_name);
        mother_name = (EditText)v.findViewById(R.id.mother_name);
        user_name = (EditText)v.findViewById(R.id.user_name);
        password = (EditText)v.findViewById(R.id.password);

        register = (Button)v.findViewById(R.id.register);

        //Initialization
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();

        //Getting BaseURL
        baseURL = apiConfiguration.getApi();
        urlRegister = baseURL + "AccountAPI/SaveApplicationUser";

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Getting values
                Toast.makeText(RegisterNewEmployee.this.getActivity(), "cliked ", Toast.LENGTH_LONG).show();
                fname = fst_name.getText().toString();
                lname = lst_name.getText().toString();
                dgi=designation.getText().toString();
                addr = address.getText().toString();
                email = email_id.getText().toString();
                mob = mobile_no.getText().toString();
                gen = gender.getText().toString();
                dob = date_of_birth.getText().toString();
                ftname = father_name.getText().toString();
                mtname = mother_name.getText().toString();
                username = user_name.getText().toString();
                passw = password.getText().toString();
                boolean invalid=false;
                if(fname.equals("")){
                    invalid=true;
                    Toast.makeText(RegisterNewEmployee.this.getActivity(),"Enter First Name",Toast.LENGTH_LONG).show();
                }else if (lname.equals("")){
                    invalid=true;
                    Toast.makeText(RegisterNewEmployee.this.getActivity(),"Enter Last Name",Toast.LENGTH_LONG).show();
                } else if (designation.equals("")) {
                    invalid = true;
                    Toast.makeText(RegisterNewEmployee.this.getActivity(), "Enter Designation", Toast.LENGTH_LONG).show();
                }  else if (addr.equals("")){
                    invalid=true;
                    Toast.makeText(RegisterNewEmployee.this.getActivity(),"Enter Address",Toast.LENGTH_LONG).show();
                }else if (email.equals("")){
                    invalid=true;
                    Toast.makeText(RegisterNewEmployee.this.getActivity(),"Enter Your Email",Toast.LENGTH_LONG).show();
                }else if (mob.equals("")){
                    invalid=true;
                    Toast.makeText(RegisterNewEmployee.this.getActivity(),"Enter Mobile no",Toast.LENGTH_LONG).show();
                }else if (gen.equals("")){
                    invalid=true;
                    Toast.makeText(RegisterNewEmployee.this.getActivity(),"Enter Gender",Toast.LENGTH_LONG).show();
                }else if (dob.equals("")){
                    invalid=true;
                    Toast.makeText(RegisterNewEmployee.this.getActivity(),"Enter Date Of Birth Name",Toast.LENGTH_LONG).show();
                }else if (fname.equals("")){
                    invalid=true;
                    Toast.makeText(RegisterNewEmployee.this.getActivity(),"Enter Father Name",Toast.LENGTH_LONG).show();
                }else if (mtname.equals("")){
                    invalid=true;
                    Toast.makeText(RegisterNewEmployee.this.getActivity(),"Enter Mother Name",Toast.LENGTH_LONG).show();
                }else if (user_name.equals("")){
                    invalid=true;
                    Toast.makeText(RegisterNewEmployee.this.getActivity(),"Enter User Name",Toast.LENGTH_LONG).show();
                }else if (passw.equals("")){
                    invalid=true;
                    Toast.makeText(RegisterNewEmployee.this.getActivity(),"Enter Password",Toast.LENGTH_LONG).show();
                }

                new RegistrationTask().execute(fname, lname,dgi,addr, email, mob, gen, dob,ftname,mtname, username, passw);

            }
        });

        return v;
    }
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Registeration");
    }
    private class RegistrationTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            fname = params[0];
            Log.e("First Name", fname);
            lname = params[1];
            Log.e("Last Name", lname);
            dgi=params[2];
            addr = params[3];
            email = params[4];
            mob = params[5];
            gen = params[6];
            dob = params[7];
            ftname = params[8];
            mtname = params[9];
            username = params[10];
            passw = params[11];


            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("FName", fname);
                jsonObject.put("LName", lname);
                jsonObject.put("MemberCode", "");
                jsonObject.put("UserTypeId", 4);
                jsonObject.put("Designation",dgi);
                jsonObject.put("Address", addr);
                jsonObject.put("EmailId", email);
                jsonObject.put("MobileNo", mob);
                jsonObject.put("Gender", gen);
                jsonObject.put("DateOfBirth", dob);
                jsonObject.put("FatherName", ftname);
                jsonObject.put("MotherName", mtname);
                jsonObject.put("UserName", username);
                jsonObject.put("Password", passw);


                jsonPostString = jsonObject.toString();
                response = httpRequestProcessor.pOSTRequestProcessor(jsonPostString, urlRegister);
                jsonResponseString = response.getJsonResponseString();

            } catch (JSONException e) {
                e.printStackTrace();
            }
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
                responseData = jsonObject.getInt("responseData");
                //Log.d("message", message);

                if (responseData == 1) {
                    Toast.makeText(RegisterNewEmployee.this.getActivity(), "User registered successfully", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterNewEmployee.this.getActivity(), LoginActivity.class));
                } else if (responseData == 2) {
                    Toast.makeText(RegisterNewEmployee.this.getActivity(), "User already exists.Please Try Again...", Toast.LENGTH_LONG).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
