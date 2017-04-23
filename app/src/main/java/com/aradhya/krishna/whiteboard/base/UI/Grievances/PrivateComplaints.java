package com.aradhya.krishna.whiteboard.base.UI.Grievances;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.aradhya.krishna.whiteboard.NetworkService.WhiteBoardSingleton;
import com.aradhya.krishna.whiteboard.R;
import com.aradhya.krishna.whiteboard.base.Config;
import com.aradhya.krishna.whiteboard.base.Models.GrievanceModel;
import com.aradhya.krishna.whiteboard.base.UI.ListAdapters.GrievanceAdapter;
import com.aradhya.krishna.whiteboard.base.UI.VClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krishna on 2/25/17.
 */

public class PrivateComplaints extends Fragment {
    private RecyclerView recyclerView;
    private GrievanceAdapter grievanceAdapter;
    private static final String TAG="PrivateCompFragment";

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.private_complaints_fragment,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.private_complaint_recyclerview);
        List<GrievanceModel> models = new ArrayList<>();
        grievanceAdapter = new GrievanceAdapter(models);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(grievanceAdapter);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
            getPrivateGrievances();
    }


    public void getPrivateGrievances(){
        final List<GrievanceModel> grievanceModels = new ArrayList<>();
        SharedPreferences preferences = getActivity().getSharedPreferences(Config.Myprefs, Context.MODE_PRIVATE);
        String email;
        JSONArray array = new JSONArray();
        JSONObject obj = new JSONObject();

        try{
            email = preferences.getString(Config.email, "none");
            obj.put("email",email);
            array.put(obj);
        }catch (JSONException e){
            e.printStackTrace();
        }
        JsonArrayRequest privateComplaintRequest = new JsonArrayRequest(Request.Method.POST,
                getActivity().getString(R.string.url)+"/whiteboard/private_grievances/",
                array, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject obj ;
                Log.d(TAG,response.toString());
                try{
                    for (int i=0;i<response.length();i++){
                        obj = (JSONObject) response.get(i);
                        GrievanceModel model = new GrievanceModel(obj);
                        grievanceModels.add(model);
                    }
                }catch(JSONException e){

                }
                grievanceAdapter = new GrievanceAdapter(grievanceModels);
                grievanceAdapter.setOnItemClickListener(new VClickListener() {
                    @Override
                    public void onItemClick(int position, View v) {
//                        Toast.makeText(getActivity(),"Click",Toast.LENGTH_SHORT).show();
                    }
                });

                recyclerView.setAdapter(grievanceAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }

        );

        WhiteBoardSingleton.getInstance(getActivity()).addToRequestQueue(privateComplaintRequest);

    }
}
