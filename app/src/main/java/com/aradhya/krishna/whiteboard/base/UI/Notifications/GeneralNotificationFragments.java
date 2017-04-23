package com.aradhya.krishna.whiteboard.base.UI.Notifications;

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

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.aradhya.krishna.whiteboard.NetworkService.WhiteBoardSingleton;
import com.aradhya.krishna.whiteboard.R;
import com.aradhya.krishna.whiteboard.base.Models.NewsModel;
import com.aradhya.krishna.whiteboard.base.UI.ListAdapters.NewsAdapter;
import com.aradhya.krishna.whiteboard.base.UI.VClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krishna on 2/25/17.
 */

public class GeneralNotificationFragments extends Fragment {
    private List<NewsModel> notificationsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private static final String TAG="GeneNotificationFrag";
    private NewsAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.general_notification_fragment,container,false);
        recyclerView = (RecyclerView) v.findViewById(R.id.notifications_recyclerview);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getNotifications();
    }

    public void getNotifications(){
        JsonArrayRequest noticeRequest = new JsonArrayRequest(
                getActivity().getString(R.string.url) + "/whiteboard/notifications/",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        JSONObject jsonObject;
                        try{
                            for (int i=0; i<response.length(); i++){

                                jsonObject = (JSONObject) response.get(i);
                                if (jsonObject != null) {
                                    NewsModel model = new NewsModel(jsonObject);
                                    notificationsList.add(model);
                                }
                            }


                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                        adapter = new NewsAdapter(notificationsList);
                        adapter.setOnItemClickListener(new VClickListener() {
                            @Override
                            public void onItemClick(int position, View v) {
                                Toast.makeText(getActivity(),"Click",Toast.LENGTH_SHORT).show();
                            }
                        });
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        recyclerView.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,error.toString());
            }

        }
        );

        WhiteBoardSingleton.getInstance(getActivity()).addToRequestQueue(noticeRequest);

    }
}
