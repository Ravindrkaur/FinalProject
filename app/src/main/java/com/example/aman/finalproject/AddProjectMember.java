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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.example.aman.finalproject.Adapter.MemberAdapter;
import com.example.aman.finalproject.HttpRequestProcessor.HttpRequestProcessor;
import com.example.aman.finalproject.HttpRequestProcessor.Response;
import com.example.aman.finalproject.apiConfiguration.ApiConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Aman on 4/29/2017.
 */

public class AddProjectMember extends Fragment {

    private EditText edt_project_id, edt_start_date,edt_end_date,edt_desc,edt_member_id,edt_status;
    private Button add_member;
    private String projectId,startDate,endDate,Desc,memberId,status;
    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, urlLogin, jsonStringToPost, jsonResponseString,urlRegister;
    private boolean success;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    int responseData;
    String message;
    private CheckBoxSingle memberList;
    private ArrayList<CheckBoxSingle> memberListArrayList;
  private int memberid;

    private MemberAdapter memberAdapter;

    public AddProjectMember() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.add_new_project_member, container, false);
        edt_project_id= (EditText) v.findViewById(R.id.edt_project_id);
       edt_start_date= (EditText) v.findViewById(R.id.edt_satrt_date);
        edt_end_date= (EditText) v.findViewById(R.id.edt_end_date);
        edt_desc= (EditText) v.findViewById(R.id.edt_descc);
        add_member= (Button) v.findViewById(R.id.add_member);
        edt_member_id= (EditText) v.findViewById(R.id.edt_member_id);
        edt_status= (EditText) v.findViewById(R.id.edt_status);
        ListView lv8= (ListView) v.findViewById(R.id.lv8);
        //Initialization
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();
        //Getting base url
        baseURL = apiConfiguration.getApi();
        urlLogin = baseURL + "ProjectMemberAssociationAPI/AddNewAssociation";
        urlRegister = baseURL + "MemberAPI/GetApplicationMemberList";
        memberListArrayList=new ArrayList<>();
       // memberAdapter=new MemberAdapter(this,memberListArrayList);
        lv8.setAdapter(memberAdapter);

        //  registerForContextMenu(lv3);
         new RegistrationTask().execute();
        add_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             projectId=edt_project_id.getText().toString();
            startDate=edt_start_date.getText().toString();
                endDate=edt_end_date.getText().toString();
                Desc=edt_desc.getText().toString();
                memberId=edt_member_id.getText().toString();
                status=edt_status.getText().toString();

                boolean invalid=false;
                 if (projectId.equals("")) {
                    invalid = true;
                    Toast.makeText(AddProjectMember.this.getActivity(), "Enter Project Id", Toast.LENGTH_LONG).show();
                }else if (startDate.equals("")) {
                     invalid = true;
                     Toast.makeText(AddProjectMember.this.getActivity(), "Enter Start Date", Toast.LENGTH_LONG).show();
                 }else if (endDate.equals("")) {
                     invalid = true;
                     Toast.makeText(AddProjectMember.this.getActivity(), "Enter End Date", Toast.LENGTH_LONG).show();
                 }else if (Desc.equals("")) {
                     invalid = true;
                     Toast.makeText(AddProjectMember.this.getActivity(), "Enter Description", Toast.LENGTH_LONG).show();
                 }else if (memberId.equals("")) {
                     invalid = true;
                     Toast.makeText(AddProjectMember.this.getActivity(), "Enter Member Id", Toast.LENGTH_LONG).show();
                 }
                new addProjectMemberTask().execute(projectId,startDate,endDate,Desc,memberId,status);
            }
        });
        return v;
    }
    public class addProjectMemberTask extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            projectId=params[0];
            startDate = params[1];
            endDate = params[2];
            Desc=params[3];
            memberId=params[4];
            status=params[5];
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("ProjectId","");
                jsonObject.put("StartDate", startDate);
                jsonObject.put("EndDate", endDate);
                jsonObject.put("Description", Desc);
                jsonObject.put("CreatedBy","");
                jsonObject.put("ModifiedBy", "");
                jsonObject.put("MemberId","");
                jsonObject.put("Status","");

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
                    Toast.makeText(AddProjectMember.this.getActivity(), "Records saved successfully !!", Toast.LENGTH_LONG).show();
                    //3startActivity(new Intent(AddProjectMember.this.getActivity(), ProjectFragment.class));
                } else if (responseData == 2) {
                    Toast.makeText(AddProjectMember.this.getActivity(), "Record saved Unsuccessfully.Please Try Again...", Toast.LENGTH_LONG).show();
                }

                //  Log.d("message", message);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
    public class RegistrationTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {



            jsonResponseString = httpRequestProcessor.gETRequestProcessor( urlRegister);

            return jsonResponseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Response String", s);
            sharedPreferences=getActivity().getSharedPreferences(MyPref.Pref_Name, Context.MODE_PRIVATE);
            String lID=sharedPreferences.getString(MyPref.UserIdentityKey, String.valueOf(memberid));
            int  loggedInUserID=Integer.parseInt(lID);

            try {
                JSONObject jsonObject = new JSONObject(s);
                success = jsonObject.getBoolean("success");
                Log.d("Success", String.valueOf(success));
                message = jsonObject.getString("message");
                if(success){
                    JSONArray responseData=jsonObject.getJSONArray("responseData");
                    for (int i=0;i<responseData.length();i++) {
                        JSONObject object = (JSONObject) responseData.get(i);
                        String fname= object.getString("FName");
                        Log.d("FName", fname);


                        //         if (loggedInUserID==memberId){
                        memberList = new CheckBoxSingle(fname);
                        memberListArrayList.add(memberList);
                        //   }

                    }
                    memberAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
//    public class GetProfileTask extends AsyncTask<String,String,String>
//    {
//
//        @Override
//        protected String doInBackground(String... params) {
//            jsonResponseString = httpRequestProcessor.gETRequestProcessor( urlRegister);
//
//            return jsonResponseString;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            Log.d("Response String", s);
//            sharedPreferences=getActivity().getSharedPreferences(MyPref.Pref_Name, Context.MODE_PRIVATE);
//            String lID=sharedPreferences.getString(MyPref.UserIdentityKey, String.valueOf(memberid));
//            int  loggedInUserID=Integer.parseInt(lID);
//            String fname=sharedPreferences.getString(MyPref.FName,null);
//
//            try {
//                JSONObject jsonObject = new JSONObject(s);
//                success = jsonObject.getBoolean("success");
//                Log.d("Success", String.valueOf(success));
//                message = jsonObject.getString("message");
//                if(success){
//                    JSONArray responseData=jsonObject.getJSONArray("responseData");
//                    for (int i=0;i<responseData.length();i++) {
//                        JSONObject object = (JSONObject) responseData.get(i);
//                        //     String fname= object.getString("FName");
//                        Log.d("FName", fname);
//
//
//                        //         if (loggedInUserID==memberId){
//                      memberList = new CheckBoxSingle(fname);
//                        memberListArrayList.add(memberList);
//                        //   }
//
//                    }
//                    memberAdapter.notifyDataSetChanged();
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            super.onPostExecute(s);
//        }
//    }
}
