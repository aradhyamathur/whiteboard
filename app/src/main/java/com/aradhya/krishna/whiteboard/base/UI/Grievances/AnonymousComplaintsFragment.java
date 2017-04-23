package com.aradhya.krishna.whiteboard.base.UI.Grievances;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.aradhya.krishna.whiteboard.NetworkService.WhiteBoardSingleton;
import com.aradhya.krishna.whiteboard.R;
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

public class AnonymousComplaintsFragment extends Fragment {
    private RecyclerView recyclerView;
    private GrievanceAdapter adapter;

    private static final String TAG = "AnonymousComplaintsFrag";
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.anonymous_complain_fragment,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.anonymous_complaint_recyclerview);
        List<GrievanceModel> models = new ArrayList<>();
        adapter = new GrievanceAdapter(models);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getGrievances();


    }

    public void getGrievances(){
        final List<GrievanceModel> grievanceModelsList = new ArrayList<>();
        JsonArrayRequest grievanceRequest = new JsonArrayRequest(
                getActivity().getString(R.string.url) + "/whiteboard/grievance/",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG,response.toString());
                        JSONObject obj;

                        try {
                            for(int i=0; i<response.length();i++){

                                     obj = (JSONObject) response.get(i);
                                    GrievanceModel grievance = new GrievanceModel(obj);
                                    grievanceModelsList.add(grievance);
                                }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG, grievanceModelsList.toString());
                        adapter = new GrievanceAdapter(grievanceModelsList);
                        adapter.setOnItemClickListener(new VClickListener() {
                            @Override
                            public void onItemClick(int position, View v) {
                                Toast.makeText(getActivity(),"Click",Toast.LENGTH_SHORT).show();
                            }
                        });

                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG,error.toString());
                    }
                }
        );
        WhiteBoardSingleton.getInstance(getActivity()).addToRequestQueue(grievanceRequest);


    }

    @Override
    public void onResume() {
        super.onResume();
        getGrievances();
    }
}

