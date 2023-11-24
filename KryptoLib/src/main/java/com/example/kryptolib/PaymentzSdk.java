package com.example.kryptolib;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

public class PaymentzSdk {


    public static String login(Context context, String walletOwnerId,String token){

        if (!walletOwnerId.isEmpty() || !token.isEmpty()){
            Constants.WALLET_OWNER_ID = walletOwnerId;
            Constants.TOKEN = token;

            Intent loginActivity = new Intent(context, LoginActivity.class);
            context.startActivity(loginActivity);

            return "Success";
        }else {
            return "Failed";
        }

    }
}
