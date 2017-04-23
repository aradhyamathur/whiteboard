package com.aradhya.krishna.whiteboard.base.Models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by krishna on 4/16/17.
 */

public class TestModel {
    private String subject;
    private String stream;
    private String test_date;
    private String title;
    private String syllabus;

    public TestModel(JSONObject obj) throws JSONException{
        this.subject = obj.getString("subject");
        this.stream = obj.getString("stream");
        this.test_date = obj.getString("test_date");
        if(this.test_date == "null") this.test_date = "To be confirmed";
        this.title = obj.getString("title");
        this.syllabus = obj.getString("syllabus");


    }

    public String getSubject() {
        return subject;
    }

    public String getStream() {
        return stream;
    }

    public String getTest_date() {
        return test_date;
    }

    public String getTitle() {
        return title;
    }

    public String getSyllabus() {
        return syllabus;
    }
}
