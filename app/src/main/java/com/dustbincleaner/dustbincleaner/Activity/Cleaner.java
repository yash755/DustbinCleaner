package com.dustbincleaner.dustbincleaner.Activity;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.dustbincleaner.R;
import com.dustbincleaner.dustbincleaner.Adapter.DisplayDustbinAdapter;
import com.dustbincleaner.dustbincleaner.Interface.GetJsonArrayReesult;
import com.dustbincleaner.dustbincleaner.Interface.GetResult;
import com.dustbincleaner.dustbincleaner.Util.Util;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Cleaner extends AppCompatActivity {

    ArrayList<String> dustbindata = new ArrayList<String>();
    ArrayList<String> binlist = new ArrayList<String>();
    ArrayList<String> dustbinname = new ArrayList<String>();
    ListView li;
    DisplayDustbinAdapter displayDustbinAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cleaner);


        getcleanerid();
    }

    void getcleanerid(){


        new Util().getcleanerid(this, new GetResult() {
            @Override
            public void done(JSONObject jsonObject) {
                try {
                    String id = jsonObject.getString("id");
                    getallocateddustbin(id);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    void getallocateddustbin(final String id){

        new Util().getallocateddustbin(this, new GetJsonArrayReesult() {

                @Override
                public void done(JSONArray jsonArray) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        try {
                            JSONObject json = jsonArray.getJSONObject(i);
                            String ids = json.getString("cleanerid");
                            String dustbin  = json.getString("dustbin");

                            if(ids.equals(id)) {
                                binlist.add(dustbin);
                                FirebaseMessaging.getInstance().subscribeToTopic(dustbin);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    getcleanerdustbindata();
                }

            });


    }

    void getcleanerdustbindata(){

        new Util().getdustbindata(this, new GetResult() {
            @Override
            public void done(JSONObject jsonObject) {

                for (int i=0; i<binlist.size(); i++) {
                    try {
                        String data = jsonObject.getString(binlist.get(i));
                        dustbindata.add(data);
                        dustbinname.add(binlist.get(i));
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                li = (ListView) findViewById(R.id.cleanerdata);
                displayDustbinAdapter = new DisplayDustbinAdapter(getApplicationContext(),dustbindata,dustbinname);
                li.setAdapter(displayDustbinAdapter);

            }
        });
    }


}
