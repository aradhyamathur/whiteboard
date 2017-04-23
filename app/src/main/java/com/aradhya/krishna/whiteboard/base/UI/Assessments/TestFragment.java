package com.aradhya.krishna.whiteboard.base.UI.Assessments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.aradhya.krishna.whiteboard.base.Models.TestModel;
import com.aradhya.krishna.whiteboard.base.UI.ListAdapters.TestAdapter;
import com.aradhya.krishna.whiteboard.base.UI.VClickListener;

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

public class TestFragment extends Fragment {
    private RecyclerView recyclerView;
    private TestAdapter adapter;
    private static final String TAG = "TestFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.test_fragment,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.test_recyclerview);
        List<TestModel> models = new ArrayList<>();
        adapter = new TestAdapter(models,getActivity().getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getTests();
    }

    public void getTests(){
        final List<TestModel> testModels = new ArrayList<>();

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

        JsonArrayRequest testRequest = new JsonArrayRequest(Request.Method.POST,
                getActivity().getString(R.string.url) + "/whiteboard/tests/",
                array,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject obj;
                        try{
                            for (int i=0; i<response.length(); i++){
                                obj = (JSONObject) response.get(i);
                                TestModel model = new TestModel(obj);
                                testModels.add(model);
                            }
                        }catch(JSONException e){

                        }
//ab dekho
                        adapter = new TestAdapter(testModels,getActivity().getApplicationContext());
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

                    }
                }
        );

        WhiteBoardSingleton.getInstance(getActivity()).addToRequestQueue(testRequest);
    }
}
