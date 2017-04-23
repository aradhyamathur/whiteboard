package com.aradhya.krishna.whiteboard.base.Models;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by krishna on 4/2/17.
 */

public class GrievanceModel {

    private String subject;
    private String info;
    private String date_info;
    private int id;
    private String grievanceType;



    public GrievanceModel(JSONObject obj) throws JSONException {

        this.id = obj.getInt("id");
        this.info = obj.getString("info");
        this.date_info = obj.getString("date_info");
        this.subject = obj.getString("subject");
        this.grievanceType = obj.getString("grievance_type");
    }
    public String getSubject(){
        return subject;

    }

    public String getInfo(){
        return info;
    }

    public String getDate_info(){
        return date_info;
    }

    public int getId() {
        return id;
    }

    public String getGrievanceType() {
        return grievanceType;
    }
}
