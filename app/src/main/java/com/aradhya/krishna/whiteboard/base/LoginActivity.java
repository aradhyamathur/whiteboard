package com.aradhya.krishna.whiteboard.base;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ConfigurationInfo;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.aradhya.krishna.whiteboard.MainActivity;
import com.aradhya.krishna.whiteboard.NetworkService.WhiteBoardSingleton;
import com.aradhya.krishna.whiteboard.R;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.ObjectArrayDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.auth.api.*;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.aradhya.krishna.whiteboard.base.Config;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
    private Button loginButton;
    private static final String MyPrefs = "Prefs";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private SignInButton signInButton;
    private GoogleApiClient googleApiClient;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        signInButton = (SignInButton) findViewById(R.id.signin_button);
        preferences = getSharedPreferences(MyPrefs, Context.MODE_PRIVATE);

        preferences = getSharedPreferences(MyPrefs,Context.MODE_PRIVATE);
        editor = preferences.edit();
        GoogleSignInOptions signInOptions = new GoogleSignInOptions
                                                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                                .requestEmail()
                                                .requestId()
                                                .requestScopes(new Scope(Scopes.PLUS_ME))
                                                .requestIdToken(Config.ID)
                                                .requestProfile().build();
         googleApiClient = new  GoogleApiClient.Builder(this)
                                                .enableAutoManage(this,this)
                                                .addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions).build();

        signInButton.setScopes(signInOptions.getScopeArray());
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    signIn();
                    String email = preferences.getString(Config.email,null);
                    String token = preferences.getString(Config.token,null);
                    String name = preferences.getString(Config.name,null);
                    String uid = preferences.getString(Config.uid,null);
                    if (email!=null && name!=null && token!=null && uid != null ) {

                        validateUser(name,email,token,uid);

                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

    }


    private void validateUser(String name, String email, String token, String uid){




    }
    private void signIn(){
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }


    private void handleSignInResult(GoogleSignInResult result){
        Log.d(TAG, "Sign-In result: "+result.toString());
        if (result.isSuccess()){
            final GoogleSignInAccount googleSignInAccount = result.getSignInAccount();
            Log.d(TAG, "Signin Success");
            Log.d(TAG, googleSignInAccount.getDisplayName()+" "+googleSignInAccount.getEmail()+" "
                    +googleSignInAccount.getIdToken()+" "+googleSignInAccount.getId());
            ObjectMapper objectMapper = new ObjectMapper();
            final ArrayNode arrayNode = objectMapper.createArrayNode();
            String url = getString(R.string.url)+"/whiteboard/login/";

            ObjectNode objectNode = objectMapper.createObjectNode();
            objectNode.put(Config.name,googleSignInAccount.getDisplayName());
            objectNode.put(Config.email,googleSignInAccount.getEmail());
            objectNode.put(Config.uid,googleSignInAccount.getId());
            objectNode.put(Config.token,googleSignInAccount.getIdToken());
            arrayNode.add(objectNode);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i(TAG,response);
                    if(!response.equals("no data received")) {
                        if (response.equals("Valid")){
                            editor = preferences.edit();
                            editor.putBoolean(Config.loginstat, true);
                            editor.putString(Config.uid, googleSignInAccount.getId());
                            editor.putString(Config.name, googleSignInAccount.getDisplayName());
                            editor.putString(Config.email, googleSignInAccount.getEmail());
                            editor.putString(Config.token, googleSignInAccount.getIdToken());
                            editor.apply();
                            editor.commit();
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                        }else{
                            signOut();
                            Toast.makeText(getApplicationContext(),"Contact college admin for access",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        signOut();
                        Toast.makeText(getApplicationContext(),"Try again later",Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    signOut();
                }
            }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    Log.d(TAG,"Sent data: "+arrayNode.toString());
                    params.put("data", arrayNode.toString());
                    return params;

                }
            };
            WhiteBoardSingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);




        }else{

        }

    }


    private void signOut(){
        Log.d(TAG,"Logout called");

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                Log.d(TAG, "signout status: "+status);
                editor.clear();
                editor.commit();

            }
        });
    }



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()){
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        }else{
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if( requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
}
