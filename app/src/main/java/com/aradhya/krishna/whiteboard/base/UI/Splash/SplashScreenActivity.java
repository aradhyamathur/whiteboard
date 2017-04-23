package com.aradhya.krishna.whiteboard.base.UI.Splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;

import com.aradhya.krishna.whiteboard.MainActivity;
import com.aradhya.krishna.whiteboard.R;
import com.aradhya.krishna.whiteboard.base.Config;
import com.aradhya.krishna.whiteboard.base.LoginActivity;

import java.io.StringBufferInputStream;

public class SplashScreenActivity extends Activity {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private static final String TAG = "SplashScreenActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        preferences = getSharedPreferences(Config.Myprefs, Context.MODE_PRIVATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent();
                boolean status = preferences.getBoolean(Config.loginstat,false);
                Log.d(TAG,String.valueOf(status));
                if(!status) {
                    i.setClass(SplashScreenActivity.this,LoginActivity.class);
                }else{
                    i.setClass(SplashScreenActivity.this, MainActivity.class);
                }
                SplashScreenActivity.this.startActivity(i);
                SplashScreenActivity.this.finish();
            }
        },3000);
    }


}
