package com.aradhya.krishna.whiteboard.base.UI.Notifications;

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
import com.aradhya.krishna.whiteboard.base.Models.PlacementsModel;
import com.aradhya.krishna.whiteboard.base.UI.ListAdapters.PlacementAdapter;
import com.aradhya.krishna.whiteboard.base.UI.ListAdapters.TestAdapter;
import com.aradhya.krishna.whiteboard.base.UI.VClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krishna on 2/25/17.
 */

public class PlacementsFragment extends Fragment {
    private RecyclerView recyclerView;
    private PlacementAdapter adapter;
    private static final String TAG = "PlacementsFragment";
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.placements_fragment,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.placements_recyclerview);
        List<PlacementsModel> models = new ArrayList<>();
        adapter = new PlacementAdapter(models);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPlacementInfo();
    }

    public void getPlacementInfo(){
        final List<PlacementsModel> placementsModels = new ArrayList<>();

//        SharedPreferences preferences = getActivity().getSharedPreferences(Config.Myprefs, Context.MODE_PRIVATE);
//        String email;
//        JSONArray array = new JSONArray();
//        JSONObject obj = new JSONObject();
//
//        try{
//            email = preferences.getString(Config.email, "none");
//            obj.put("email",email);
//            array.put(obj);
//        }catch (JSONException e){
//            e.printStackTrace();
//        }

        JsonArrayRequest placementRequest = new JsonArrayRequest(
                getActivity().getString(R.string.url)+"/whiteboard/placement/",
//                array,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject obj;
                        Log.d(TAG,response.toString());
                        try{
                            for(int i=0;i<response.length();i++){
                                obj = (JSONObject) response.get(i);
                                PlacementsModel model = new PlacementsModel(obj);
                                placementsModels.add(model);
                            }
                        }
                        catch(JSONException e){
                            e.printStackTrace();
                        }

                        adapter = new PlacementAdapter(placementsModels);
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

        WhiteBoardSingleton.getInstance(getActivity()).addToRequestQueue(placementRequest);
    }
}
