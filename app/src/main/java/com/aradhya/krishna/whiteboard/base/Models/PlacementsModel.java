package com.aradhya.krishna.whiteboard.base.Models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by krishna on 4/11/17.
 */

public class PlacementsModel {
    private String stream;
    private String info;
    private String company;
    private String imgUri;


    public  PlacementsModel(JSONObject obj) throws JSONException{
        stream = obj.getString("stream");
        info = obj.getString("info");
        company = obj.getString("company");

    }

    public String getStream() {
        return stream;
    }

    public String getInfo() {
        return info;
    }

    public String getCompany() {
        return company;
    }

    public String getImgUri() {
        return imgUri;
    }


    public void setStream(String stream){
        this.stream = stream;
    }



    public void setCompany(String company){
        this.company = company;
    }

    public void setImgUri(String uri){
        this.imgUri = uri;
    }


}