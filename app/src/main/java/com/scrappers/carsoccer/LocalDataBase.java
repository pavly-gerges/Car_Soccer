package com.scrappers.carsoccer;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LocalDataBase {

    private final AppCompatActivity context;
    private final String file;

    /**
     * Instantiate a new JSON database local file
     * @param context the context of the activity
     * @param file the local file to save data to & fetch it from
     */
    public LocalDataBase(@NonNull AppCompatActivity context, @NonNull String file){
        this.context=context;
        this.file=file;
    }

    /**
     * Instantiate a new JSON database local file
     * @param context the context of the activity
     * @param file the local file to save data to & fetch it from
     */
    public LocalDataBase(@NonNull Activity context, @NonNull String file){
        this.context= (AppCompatActivity) context;
        this.file=file;
    }
    /**
     * Writes data in the form of JSONArray under the object name
     * @param name the JSONObject that holds that JSONArray that have the local data in place
     * @param isRememberMe true if the user signed in - false otherwise
     */
    public void writeData(String name, String myPassword, boolean isRememberMe)  {
        JSONObject jsonObject=new JSONObject();
        JSONArray jsonArray=new JSONArray();
        try {
            jsonObject.put(name, jsonArray);
            jsonArray.put(new JSONObject().put("name",name));
            jsonArray.put(new JSONObject().put("myPassword",myPassword));
            jsonArray.put(new JSONObject().put("isRememberMe",isRememberMe));
            try(BufferedWriter fos=new BufferedWriter(new FileWriter(new File(context.getFilesDir()+file)))){
                fos.write(jsonObject.getString(name));
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }catch (JSONException ex){
            System.err.println(ex.getMessage());
        }
    }

    /**
     * reads the data fetched from the local file & parse it to a JSONArray
     * @param jsonObjectIndex the index of the JsonObject inside the JsonArray that you wanna access
     * @param key @{@link NonNull} the key to access the data(Value) in jsonObject
     * @return data in the form of JSONArray
     */
    public Object readData(int jsonObjectIndex,@NonNull String key){
        try(BufferedReader fis=new BufferedReader(new FileReader(new File(context.getFilesDir()+file)))){
            return new JSONArray(fis.readLine()).getJSONObject(jsonObjectIndex).get(key);
        }catch (IOException | JSONException | NullPointerException ex){
            ex.printStackTrace();
            return null;
        }
    }

    public void removeLocalFiles(){
        System.out.println(new File(context.getFilesDir()+"/user/user.json").delete());
        System.out.println(new File(context.getFilesDir()+"/user/profileImage.png").delete());
        System.out.println(new File(context.getFilesDir()+"/user").delete());
    }

}
