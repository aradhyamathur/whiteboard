package com.aradhya.krishna.whiteboard.base.UI.Grievances;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aradhya.krishna.whiteboard.MainActivity;
import com.aradhya.krishna.whiteboard.NetworkService.WhiteBoardSingleton;
import com.aradhya.krishna.whiteboard.R;
import com.aradhya.krishna.whiteboard.base.Config;

import java.util.HashMap;
import java.util.Map;

public class PostGrievance extends AppCompatActivity {
    private Button submitButton;
    private EditText titleEditText;
    private EditText infoEditText;
    private static final String TAG="POSTGRIEVANCE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_grievance);
        submitButton = (Button) findViewById(R.id.complaint_submit_button);
        titleEditText = (EditText) findViewById(R.id.complaint_title_editText);
        infoEditText = (EditText) findViewById(R.id.complaint_info_editText2);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleEditText.getText().toString();
                String info = infoEditText.getText().toString();
                submitComplaint(title,info);
            }
        });

    }


    public void submitComplaint(final String title, final String info){
        Log.d(TAG,"Received title "+title);
        Log.d(TAG,"Received info "+info);
        SharedPreferences preferences = getSharedPreferences(Config.Myprefs, Context.MODE_PRIVATE);
        final String email = preferences.getString(Config.email,"none");

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getString(R.string.url)+"/whiteboard/post_grievance/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG,response);
                        Toast.makeText(getApplicationContext(),"Complaint Registered",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(PostGrievance.this, MainActivity.class);
                        startActivity(i);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        Toast.makeText(getApplicationContext(),"Check info or try again later",Toast.LENGTH_LONG).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("title",title);
                params.put("email",email);
                params.put("info", info);
                return params;
            }
        };
        WhiteBoardSingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
