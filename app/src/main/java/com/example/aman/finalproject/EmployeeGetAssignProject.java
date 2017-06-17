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

import com.example.aman.finalproject.Adapter.GetAssignProjectAdapter;
import com.example.aman.finalproject.Adapter.ProjectListingAdapter;
import com.example.aman.finalproject.HttpRequestProcessor.HttpRequestProcessor;
import com.example.aman.finalproject.HttpRequestProcessor.Response;
import com.example.aman.finalproject.apiConfiguration.ApiConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Aman on 5/17/2017.
 */

public class EmployeeGetAssignProject extends Fragment {
    private ListView lv;
    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, getproject;
    private String jsonResponseString;
    private String message;
    private boolean success;
    private GetProjectSingle getProjectSingle;
    private ArrayList<GetProjectSingle> getProjectSingleArrayList;
    private GetAssignProjectAdapter getAssignProjectAdapter;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String member_id;
    private int projectid;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.get_assign_project, container, false);

        ListView getprojectlist = (ListView) v.findViewById(R.id.getprojectlist);


        //Initialization`33
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();
        sharedPreferences = getActivity().getSharedPreferences(MyPref.Pref_Name, Context.MODE_PRIVATE);
        // int mid = Integer.parseInt(sharedPreferences.getString(MyPref.UserIdentityKey, null));
        String mid = (sharedPreferences.getString(MyPref.MemberId, null));
        int applicationUserId = Integer.parseInt(mid);

        baseURL = apiConfiguration.getApi();
        getproject = baseURL + "ProjectMemberAssociationAPI/GetMyProjectList/"+applicationUserId;
        getProjectSingleArrayList = new ArrayList<>();
        getAssignProjectAdapter = new GetAssignProjectAdapter(getActivity(), getProjectSingleArrayList);
        getprojectlist.setAdapter(getAssignProjectAdapter);
        registerForContextMenu(getprojectlist);
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
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MyPref.Pref_Name, Context.MODE_PRIVATE);


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
                        String title = object.getString("ProjectName");
                        Log.d("Title", title);
                        String employee = object.getString("ProjectId");
                        Log.d("Description", employee);
                        getProjectSingle = new GetProjectSingle(title, employee);
                        getProjectSingleArrayList.add(getProjectSingle);

                    }
                    getAssignProjectAdapter.notifyDataSetChanged();
                }

                member_id = jsonObject.getString("MemberId");
                sharedPreferences = getActivity().getSharedPreferences(MyPref.Pref_Name, Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();
                editor.putString(MyPref.MemberId, member_id);
                editor.commit();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
