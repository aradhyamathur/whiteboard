package com.aradhya.krishna.whiteboard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.aradhya.krishna.whiteboard.base.Config;
import com.aradhya.krishna.whiteboard.base.LoginActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;

public class SettingsActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private  Button signOutButton;
    private GoogleApiClient googleApiClient;
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;
    private static final String TAG = "SettingsActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        prefs = getSharedPreferences(Config.Myprefs, Context.MODE_PRIVATE);
        editor = prefs.edit();
        setContentView(R.layout.activity_settings_actitivity);
        signOutButton = (Button) findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"Click");
                try{
                    Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
                    signOut();
                    Intent i = new Intent(SettingsActivity.this, LoginActivity.class);
                    startActivity(i);
                }catch(Exception e ){
                    Log.e(TAG,e.getMessage());
                    e.printStackTrace();
                }
            }
        });
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
        Log.d(TAG,"Connection failed");
        Toast.makeText(getApplicationContext(),"Connection Failed", Toast.LENGTH_LONG).show();
    }
}
