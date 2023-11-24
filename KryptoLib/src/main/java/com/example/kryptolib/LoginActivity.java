package com.example.kryptolib;


import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class LoginActivity extends Activity implements UserLoginService.UserLoginListener {


    private EditText passwordView, userName;

    private String freememory;
    private String density;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        userName = (EditText) findViewById(R.id.mTextMobile);
        passwordView = (EditText) findViewById(R.id.inputPassword);


        DecimalFormat dec = new DecimalFormat("0.00");

        StatFs stat = new StatFs(Environment.getExternalStorageDirectory().getPath());
        long bytesAvailable = (long) stat.getBlockSize() * (long) stat.getBlockCount();
        long megAvailable = bytesAvailable / 1048576;
        if (megAvailable > 1) {
            freememory = dec.format(megAvailable).concat(" GB");
        }


        DisplayMetrics dm = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(dm);
        int dpiClassification = dm.densityDpi;
        density = String.valueOf(dpiClassification);


    }

    public void userLogin() {



            String countryCode = "91";
            countryCode = countryCode.replace("+", "");
            try {

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("telnocc", countryCode);
                jsonObject.put("userName",userName.getText().toString().trim());
                jsonObject.put("password", passwordView.getText().toString().trim());
                jsonObject.put("cust_device_id", Constants.getUniqueDeviceId(this));
                jsonObject.put("platformId", Constants.ANDROID_PLATFORM_ID);
                jsonObject.put("walletOwnerId", Constants.WALLET_OWNER_ID);
                jsonObject.put("token", Constants.TOKEN);

                JSONObject fingerPrintDetails = new JSONObject();
                fingerPrintDetails.put("Device_UUID", Constants.getUniqueDeviceId(this));
                fingerPrintDetails.put("OS", "Android");
                fingerPrintDetails.put("OS Version", Build.VERSION.RELEASE);
                fingerPrintDetails.put("Device", android.os.Build.MODEL);
                fingerPrintDetails.put("Free Memory", freememory);
                fingerPrintDetails.put("Density", density + "dpi");
                JSONObject deviceDetails = new JSONObject();
                deviceDetails.put("deviceFingerprint",fingerPrintDetails.toString());
                deviceDetails.put("ip","IP");
                deviceDetails.put("location",("latitude:"+0.0000 +","+"longitude:" +0.0000));
                jsonObject.put("deviceDetails",deviceDetails);
                new UserLoginService().execute(String.valueOf(jsonObject), this);

            } catch (JSONException e) {
                Log.e("LoginActivity", e.getLocalizedMessage());
            }
        }


    @Override
    public void onLoginSuccessful() {
        Intent intent = new Intent(LoginActivity.this, MainDashboardActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginFailed() {

    }

    @Override
    public void onNoInternetConnection() {

    }
}