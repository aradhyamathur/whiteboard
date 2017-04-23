package com.aradhya.krishna.whiteboard.base.UI.Home;

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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.aradhya.krishna.whiteboard.NetworkService.WhiteBoardSingleton;
import com.aradhya.krishna.whiteboard.R;
import com.aradhya.krishna.whiteboard.base.Config;
import com.aradhya.krishna.whiteboard.base.Models.NewsModel;
import com.aradhya.krishna.whiteboard.base.UI.ListAdapters.NewsAdapter;
import com.aradhya.krishna.whiteboard.base.UI.VClickListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by krishna on 4/1/17.
 */

public class HomeFragment extends Fragment {
    private static final String TAG = "HomeFragment";
    private RecyclerView newsRecyclerView ;
    private NewsAdapter adapter;
    private SharedPreferences prefs ;
    private TextView nameTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_view_fragment,container,false);
        newsRecyclerView = (RecyclerView) v.findViewById(R.id.news_recyclerview);
         nameTextView = (TextView) v.findViewById(R.id.username_textView8);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        prefs = getActivity().getSharedPreferences(Config.Myprefs,getActivity().getApplicationContext().MODE_PRIVATE);
        String name = prefs.getString(Config.name,null);
        if (name != null)
            nameTextView.setText(name);
        getNews();


    }

    public void getNews(){
       final List<NewsModel> newsModelList = new ArrayList<>();

       JsonArrayRequest newsRequest = new JsonArrayRequest(
               getActivity().getResources().getString(R.string.url)+"/whiteboard/newsfeed/",
               new Response.Listener<JSONArray>(){
                
                   @Override
                   public void onResponse(JSONArray response) {
                        Log.d(TAG,response.toString());
                       JSONObject jsonObject;
                       try {
                           for (int i = 0; i < response.length(); i++) {

                               jsonObject = (JSONObject) response.get(i);
                                Log.d(TAG,jsonObject.toString());
                                NewsModel news = new NewsModel(jsonObject);
                                newsModelList.add(news);
                           }
                       }catch (JSONException e){
                           Log.e(TAG,e.getMessage());
                       }
                        adapter = new NewsAdapter(newsModelList);
                        adapter.setOnItemClickListener(new VClickListener() {
                           @Override
                           public void onItemClick(int position, View v) {
                               Toast.makeText(getActivity(),"Click",Toast.LENGTH_SHORT).show();
                           }
                       });
                        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                        newsRecyclerView.setAdapter(adapter);
                   }
               },new Response.ErrorListener(){
           @Override
           public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"error");
               Log.e(TAG, error.toString());
           }
       });
        WhiteBoardSingleton.getInstance(getActivity()).addToRequestQueue(newsRequest);

    }

    @Override
    public void onResume() {
        super.onResume();


    }
}
