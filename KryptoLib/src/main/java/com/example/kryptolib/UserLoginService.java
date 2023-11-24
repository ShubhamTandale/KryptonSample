package com.example.kryptolib;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

/**
 * Created by Prashant on 2/15/2017.
 */

public class UserLoginService extends AsyncTask<Object, Void, String> {
    public final String LOGIN_SERVICE_URL = "https://mob.paymentz.com/accountServices/AccountService/customerLogin";
//    private AppPreferences mPreferences;
    private UserLoginListener mUserLoginListener;
    private static final String TAG = "UserLoginService";

    public interface UserLoginListener {
        void onLoginSuccessful();
        void onLoginFailed();
        void onNoInternetConnection();
    }

    @Override
    protected String doInBackground(Object... params) {

        String result = null;
        Context context = (Context) params[1];
        try {
            mUserLoginListener = (UserLoginListener) context;
            BackgroundService.serviceName = getClass().getSimpleName();;
            BackgroundService.setServerUrl(LOGIN_SERVICE_URL);
            result = BackgroundService.doInBackgroundService((String) params[0]);
        } catch (Exception e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
        return result;
    }

    @Override
    protected void onPostExecute(String result) {

        super.onPostExecute(result);
        if (result != null) {
            try {
                System.out.println("Result of Login Service==== "+result);
                JSONObject jsonObject = new JSONObject(result);

                if (jsonObject.getString("loginStatus").equals("SUCCESS")) {

                    mUserLoginListener.onLoginSuccessful();

                } else {

                    mUserLoginListener.onLoginFailed();
                }
            } catch (Exception e) {

                Log.e(TAG, e.getLocalizedMessage());
                mUserLoginListener.onLoginFailed();
            }
        } else {
            mUserLoginListener.onNoInternetConnection();
        }
    }
}
