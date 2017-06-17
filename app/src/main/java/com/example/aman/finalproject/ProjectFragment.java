package com.example.aman.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aman.finalproject.Adapter.ProjectListingAdapter;
import com.example.aman.finalproject.HttpRequestProcessor.HttpRequestProcessor;
import com.example.aman.finalproject.HttpRequestProcessor.Response;
import com.example.aman.finalproject.apiConfiguration.ApiConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Aman on 4/25/2017.
 */

public class ProjectFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView lv;
    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, getproject;
    private String jsonResponseString;


     private String message;
    private boolean success;
    private ProjectListvalue layout;
    private ArrayList<ProjectListvalue> layoutArrayList;
    private ProjectListingAdapter adapter;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String project_id;
    private int projectid;
    ListView lv2;
    int position;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.projects_activity, container, false);

        lv2 = (ListView) v.findViewById(R.id.lv2);


        //Initialization`33
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();


        baseURL = apiConfiguration.getApi();
        getproject = baseURL + "ProjectAPI/GetProjectListing";
        layoutArrayList = new ArrayList<>();
        adapter = new ProjectListingAdapter(getActivity(), layoutArrayList);
        lv2.setAdapter(adapter);
        registerForContextMenu(lv2);
        new ProjectTask().execute();
        return v;


    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menuproject, menu);

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        position = info.position;
        switch (item.getItemId()) {
            case R.id.view:
                Toast.makeText(ProjectFragment.this.getActivity(), "View selected", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.delete:
                Toast.makeText(ProjectFragment.this.getActivity(), "Delete selected", Toast.LENGTH_SHORT).show();

                httpRequestProcessor = new HttpRequestProcessor();
                response = new Response();
                apiConfiguration = new ApiConfiguration();
                ProjectListvalue projectListvalue = (ProjectListvalue) adapter.getItem(position);
                project_id = projectListvalue.getpID();

                projectid = Integer.parseInt(project_id);
                baseURL = apiConfiguration.getApi();
                getproject = baseURL + "ProjectAPI/DeleteProject/" + projectid;
                new DeleteTask().execute();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


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
                        String title = object.getString("Title");
                        Log.d("Title", title);
                        String desc = object.getString("Description");
                        Log.d("Description", desc);
                        int pId = object.getInt("ProjectId");
                        project_id = String.valueOf(pId);
                        layout = new ProjectListvalue(title, desc, project_id);
                        layoutArrayList.add(layout);

                    }
                    adapter.notifyDataSetChanged();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

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
                message = jsonObject.getString("message");
                Log.d("mesage", message);
                int responseData = jsonObject.getInt("responseData");
                if (responseData == 1) {
                    Toast.makeText(ProjectFragment.this.getActivity(), "Project deleted successfully", Toast.LENGTH_LONG).show();
                    //startActivity(new Intent(ProjectFragment.this.getActivity(), LoginActivity.class));
                } else if (responseData == 2) {
                    Toast.makeText(ProjectFragment.this.getActivity(), "project  deleted failed.Please Try Again...", Toast.LENGTH_LONG).show();
                }


                adapter.notifyDataSetChanged();


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


    }
}






