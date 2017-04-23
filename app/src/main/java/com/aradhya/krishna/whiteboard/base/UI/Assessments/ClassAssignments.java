package com.aradhya.krishna.whiteboard.base.UI.Assessments;

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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.aradhya.krishna.whiteboard.NetworkService.WhiteBoardSingleton;
import com.aradhya.krishna.whiteboard.R;
import com.aradhya.krishna.whiteboard.base.Config;
import com.aradhya.krishna.whiteboard.base.Models.AssignmentModel;
import com.aradhya.krishna.whiteboard.base.Models.GrievanceModel;
import com.aradhya.krishna.whiteboard.base.UI.ListAdapters.AssignmentAdapter;
import com.aradhya.krishna.whiteboard.base.UI.ListAdapters.GrievanceAdapter;
import com.aradhya.krishna.whiteboard.base.UI.VClickListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by krishna on 2/25/17.
 */

public class ClassAssignments  extends Fragment{
    private RecyclerView recyclerView;
    private AssignmentAdapter adapter;
    private static final String TAG = "ClassAssignmentFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.assisgnment_fragment_layout,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.class_assignments_recyclerview);
        List<AssignmentModel> models = new ArrayList<>();
        adapter = new AssignmentAdapter(models);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getAssignments();
    }

    public void getAssignments(){
        final List<AssignmentModel> assignmentModelList = new ArrayList<>();

        SharedPreferences preferences = getActivity().getSharedPreferences(Config.Myprefs, Context.MODE_PRIVATE);
        String email;

        JSONArray array = new JSONArray();
        JSONObject obj = new JSONObject();

        try {
            preferences = getActivity().getSharedPreferences(Config.Myprefs, Context.MODE_PRIVATE);
            email = preferences.getString(Config.email, "none");

            obj.put("email",email);
            array.put(obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ;


        JsonArrayRequest assignmentRequest = new JsonArrayRequest(Request.Method.POST,
                getActivity().getString(R.string.url) + "/whiteboard/assignments/",
                array,

                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG,response.toString());
                        JSONObject obj;

                        try {
                            for(int i=0; i<response.length();i++){

                                obj = (JSONObject) response.get(i);
                                AssignmentModel assignmentModel = new AssignmentModel(obj);
                                assignmentModelList.add(assignmentModel);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d(TAG, assignmentModelList.toString());
                        adapter = new AssignmentAdapter(assignmentModelList);
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
                        error.printStackTrace();
                    }
                }
        );
        WhiteBoardSingleton.getInstance(getActivity()).addToRequestQueue(assignmentRequest);


    }

    @Override
    public void onResume() {
        super.onResume();

    }
}


//    @Override
//    protected Map<String, String> getParams() throws AuthFailureError {
//        Map<String,String> params = new HashMap<>();
//        SharedPreferences preferences = getActivity().getSharedPreferences(Config.Myprefs, Context.MODE_PRIVATE);
//        String email = preferences.getString(Config.email,null);
//        if(email != null){
//            ObjectMapper objectMapper = new ObjectMapper();
//            ObjectNode objectNode = objectMapper.createObjectNode();
//            ArrayNode arrayNode = objectMapper.createArrayNode();
//            objectNode.put("email",email);
//            arrayNode.add(objectNode);
//            params.put("email",email);
//        }
//        return super.getParams();
//    }