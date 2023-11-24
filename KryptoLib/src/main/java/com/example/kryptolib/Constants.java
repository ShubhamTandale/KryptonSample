package com.example.kryptolib;

import android.content.Context;
import android.provider.Settings;

public class Constants {

    public static int ANDROID_PLATFORM_ID = 1;
    public static String WALLET_OWNER_ID = "";
    public static String TOKEN = "";


    public static String getUniqueDeviceId(Context context) {

        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

}
