package com.example.symph.symphtest.helper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Helper {

    public static String getJsonResponse(String urlString){
        StringBuilder result=new StringBuilder();
        try {

            URL url = new URL(urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            urlConnection.disconnect();
        }catch( Exception e) {
            e.printStackTrace();
        }

        Log.e("Response",result.toString());

        return result.toString();
    }

    public static Bitmap getAvatar(String src) throws IOException {
        URL url = new URL(src);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.connect();
        InputStream input = connection.getInputStream();
        return BitmapFactory.decodeStream(input);
    }

    public static void viewOnBrowser(Context context,String url){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse(url));
        context.startActivity(browserIntent);
    }

    public static byte[] toByteArray(Bitmap bitmap){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        return bos.toByteArray();
    }

    public static Bitmap decodeImage(byte[]bytearray){
        return BitmapFactory.decodeByteArray(bytearray, 0, bytearray.length);
    }

    public static String getUserLink(String username){
        return "https://api.github.com/users/"+username;
    }

    public static void displayMessage(String message,View view){
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public static JSONObject getJSON(String username) throws JSONException {
        return new JSONObject(getJsonResponse(getUserLink(username)));
    }

}
