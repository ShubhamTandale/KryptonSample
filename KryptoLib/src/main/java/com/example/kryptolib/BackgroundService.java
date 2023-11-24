package com.example.kryptolib;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Prashant on 7/28/2016.
 */
public class BackgroundService
{
    public static String serverUrl = null;

    private static String authToken = null;

    public static void setAuthToken(String token) { authToken = token; }

    public static String getServerUrl()
    {
        return serverUrl;
    }

    public static String serviceName = "BackgroundService";

    public static void setServerUrl(String url)
    {
        serverUrl = url;
    }

    private static final int SECONDS = 1000;
    private static final String TAG = "BackgroundService";

    public static String doInBackgroundService(String... params)
    {
//        System.out.println(serviceName+" serviceNameRequest: "+ params[0]);
        String jsonData = params[0];
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        try
        {
            System.setProperty("jsse.enableSNIExtension", "false");
            System.setProperty("https.protocols", "TLSv1.2");

            //It will fetch the url according to the property name
            String fetchUrl = getServerUrl();

            URL url = new URL(fetchUrl);
            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setConnectTimeout(30 * SECONDS);

            urlConnection.setRequestMethod("POST");
            //HttpsURLConnection.setDefaultSSLSocketFactory(new TLSSocketFactory(mContext));
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("AuthToken", authToken);

            Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(),"UTF-8"));
            writer.write(jsonData);

            writer.close();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null)
            {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String inputLine;
            while ((inputLine = reader.readLine()) != null)
                buffer.append(inputLine + "\n");
            if (buffer.length() == 0)
                return null;

//            System.out.println(serviceName+" Response: "+ buffer.toString());
            return buffer.toString();

        }
        catch (Exception e)
        {
            Log.e(TAG, e.getLocalizedMessage());
        }
        finally
        {
            if (urlConnection != null)
            {
                urlConnection.disconnect();
            }
            if (reader != null)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {
                    Log.e(TAG, e.getLocalizedMessage());
                }
            }
        }
        return null;
    }


}
