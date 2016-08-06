package com.example.symph.symphtest.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import com.example.symph.symphtest.object.User;

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

/**
 * Created by Kenneth on 8/5/2016.
 */
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

    public static Bitmap getImage(String src){
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static User getUser(String userName) throws JSONException {
        String response=getJsonResponse(getUserLink(userName));
        JSONObject json=new JSONObject(response);
        User user=null;
        if(json!=null){
            user=new User(json);
            Bitmap bitmap = Helper.getImage(json.getString("avatar_url"));
            user.setByteArray(Helper.toByteArray(bitmap));
        }
        return user;
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
}
