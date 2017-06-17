package com.example.aman.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class MemberListFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView lv;
    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, urlRegister,getproject;
    private String jsonPostString, jsonResponseString;
    private String message;

    private boolean success;
    private EmployeeArrayList employeeDetail;
    private ArrayList<EmployeeArrayList> employeeDetailArrayList;
    private MemberList memberList;
    private ArrayList<MemberList> memberListArrayList;
    private MemberAdapter memberAdapter;
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private int UserTypeId;
    private String applicationuserid, useridentitykey, usertypeid, usertype, fname, lname, membercode, email, mobile, gender, dob, address, ftname, mtname, designation, uname;
    private int memberid;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.member_list_fragment, container, false);

        ListView lv3 = (ListView) v.findViewById(R.id.lv3);
        //Initialization
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();


        baseURL = apiConfiguration.getApi();
        urlRegister = baseURL + "MemberAPI/GetApplicationMemberList";
        memberListArrayList = new ArrayList<>();
        memberAdapter = new MemberAdapter(getActivity(), memberListArrayList);
        lv3.setAdapter(memberAdapter);
        lv3.setOnItemClickListener(this);
        registerForContextMenu(lv3);
        new RegistrationTask().execute();
        return v;
    }
   @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater= getActivity().getMenuInflater();
        inflater.inflate(R.menu.main2, menu);

    }

   @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.delete:
                Toast.makeText(MemberListFragment.this.getActivity(), "Delete selected", Toast.LENGTH_SHORT).show();
                httpRequestProcessor = new HttpRequestProcessor();
                response = new Response();
                apiConfiguration = new ApiConfiguration();

                sharedPreferences = getActivity().getSharedPreferences(MyPref.Pref_Name, Context.MODE_PRIVATE);
                // int mid = Integer.parseInt(sharedPreferences.getString(MyPref.UserIdentityKey, null));
                String mid = (sharedPreferences.getString(MyPref.ApplicationUserId, null));
                int applicationUserId = Integer.parseInt(mid);
                baseURL = apiConfiguration.getApi();
                getproject = baseURL + "ProjectMemberAssociationAPI/DeleteProjectAssociation/" + applicationUserId;
                memberListArrayList = new ArrayList<>();
                new DeleteTask().execute();
                return true;

        }
        return super.onContextItemSelected(item);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(MemberListFragment.this.getActivity(), MyInformation3.class);
        startActivity(intent);

    }

    public class RegistrationTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {


            jsonResponseString = httpRequestProcessor.gETRequestProcessor(urlRegister);

            return jsonResponseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Response String", s);
            sharedPreferences = getActivity().getSharedPreferences(MyPref.Pref_Name, Context.MODE_PRIVATE);
            String lID = sharedPreferences.getString(MyPref.UserIdentityKey, String.valueOf(memberid));
            int loggedInUserID = Integer.parseInt(lID);

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
                        String email = object.getString("EmailId");
                        Log.d("EmailId", email);

                        //         if (loggedInUserID==memberId){
                        memberList = new MemberList(fname, email);
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

    public class GetProfileTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            jsonResponseString = httpRequestProcessor.gETRequestProcessor(urlRegister);

            return jsonResponseString;
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("Response String", s);
            sharedPreferences = getActivity().getSharedPreferences(MyPref.Pref_Name, Context.MODE_PRIVATE);
            String lID = sharedPreferences.getString(MyPref.UserIdentityKey, String.valueOf(memberid));
            int loggedInUserID = Integer.parseInt(lID);
            String fname = sharedPreferences.getString(MyPref.FName, null);

            try {
                JSONObject jsonObject = new JSONObject(s);
                success = jsonObject.getBoolean("success");
                Log.d("Success", String.valueOf(success));
                message = jsonObject.getString("message");
                if (success) {
                    JSONArray responseData = jsonObject.getJSONArray("responseData");
                    for (int i = 0; i < responseData.length(); i++) {
                        JSONObject object = (JSONObject) responseData.get(i);

                         fname= object.getString("FName");
                        Log.d("FName", fname);
                        String email = object.getString("EmailId");
                         Log.d("EmailId", email);


                        //         if (loggedInUserID==memberId){
                        employeeDetail = new EmployeeArrayList(fname, email);
                        memberListArrayList.add(memberList);
                        //   }
                        String memid=jsonObject.getString("MemberId");
                      applicationuserid=jsonObject.getString("ApplicationUserId");

                        usertypeid = jsonObject.getString("UserTypeId");

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
                        sharedPreferences = getActivity().getSharedPreferences(MyPref1.Pref_Name, Context.MODE_PRIVATE);
                        editor = sharedPreferences.edit();
                        editor.putString(MyPref1.MemberId,memid);
                        editor.putString(MyPref1.ApplicationUserId,applicationuserid);
                        editor.putString(MyPref1.UserTypeId, usertypeid);
                        editor.putString(MyPref1.FName, fname);
                        editor.putString(MyPref1.LName, lname);
                        editor.putString(MyPref1.EmailId, email);
                        editor.putString(MyPref1.MobileNo, mobile);
                        editor.putString(MyPref1.Gender, gender);
                        editor.putString(MyPref1.DateOfBirth, dob);
                        editor.putString(MyPref1.Address, address);
                        editor.putString(MyPref1.FatherName, ftname);
                        editor.putString(MyPref1.MotherName, mtname);
                        editor.putString(MyPref1.Designation, designation);
                        editor.commit();

                    }
                    memberAdapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            super.onPostExecute(s);
        }
    }
    public class DeleteTask extends AsyncTask<String, String, String> {
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
//                message = jsonObject.getString("message");
//                Log.d("mesage", message);
                int responseData = jsonObject.getInt("responseData");
                if (responseData == 1) {
                    Toast.makeText(MemberListFragment.this.getActivity(), "Record deleted successfully", Toast.LENGTH_LONG).show();
                } else if(responseData == 2) {
                    Toast.makeText(MemberListFragment.this.getActivity(), "Record deleted failed.Please Try Again...", Toast.LENGTH_LONG).show();
                }


                //   adapter.notifyDataSetChanged();


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Our Employee");
    }
}
