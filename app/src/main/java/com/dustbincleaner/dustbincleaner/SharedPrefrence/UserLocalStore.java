package com.dustbincleaner.dustbincleaner.SharedPrefrence;


import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class UserLocalStore {

    public static final String SP_Name = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context)
    {
        userLocalDatabase = context.getSharedPreferences(SP_Name, 0);
    }

    public void userData(String token,String username)
    {
        SharedPreferences.Editor speditor = userLocalDatabase.edit();
        speditor.putString("status",token);
        speditor.putString("username",username);
        speditor.commit();
    }

    public String getUsername(){
        String name = userLocalDatabase.getString("username", "");
        System.out.println("status"+ name);
        return name;
    }

    public String getStatus(){
        String name = userLocalDatabase.getString("status", "");
        System.out.println("status"+ name);
        return name;
    }



    public void updatedustbin(String dustbin)
    {
        SharedPreferences.Editor speditor = userLocalDatabase.edit();
        speditor.putString("dustbin",dustbin);
        speditor.apply();
    }

    public String getdustbin(){

        String name = userLocalDatabase.getString("dustbin", "");
        System.out.println("Dustbin"+ name);
        return name;
    }



    public void setUserloggedIn(boolean loggedIn){
        SharedPreferences.Editor speditor = userLocalDatabase.edit();
        speditor.putBoolean("loggedIn",loggedIn);
        speditor.commit();

    }



    public boolean getuserloggedIn(){

        if(userLocalDatabase.getBoolean("loggedIn",false) == true)
            return true;
        else
            return false;
    }



    public void clearUserdata(){

        SharedPreferences.Editor speditor = userLocalDatabase.edit();
        speditor.clear();
        speditor.commit();

    }
}
