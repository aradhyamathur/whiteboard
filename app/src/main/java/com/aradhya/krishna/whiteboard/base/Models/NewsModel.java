package com.aradhya.krishna.whiteboard.base.Models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by krishna on 4/2/17.
 */

public class NewsModel {

    private String title;
    private String info;


    private String date;
    private int id;
    public NewsModel(JSONObject obj) throws JSONException {

        this.title = obj.getString("news_title");
        this.id = obj.getInt("id");
        this.date = obj.getString("news_date");
        this.info = obj.getString("news_info");
    }


    public String getTitle(){
        return title;
    }

    public String getInfo(){
        return info;
    }

    public String getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

}
