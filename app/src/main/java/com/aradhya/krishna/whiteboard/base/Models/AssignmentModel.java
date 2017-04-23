package com.aradhya.krishna.whiteboard.base.Models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by krishna on 4/16/17.
 */

public class AssignmentModel {
    private String subject;
    private String submissionDate;
    private String info;

    public AssignmentModel(JSONObject obj) throws JSONException {


        this.info = obj.getString("info");
        this.submissionDate= obj.getString("submission_date");
        if (this.submissionDate == "null")this.submissionDate="To be informed";
        this.subject = obj.getString("subject");

    }

    public String getSubject() {
        return subject;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public String getInfo() {
        return info;
    }
}
