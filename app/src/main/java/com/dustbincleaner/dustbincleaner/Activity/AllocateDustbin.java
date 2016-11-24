package com.dustbincleaner.dustbincleaner.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.dustbincleaner.R;
import com.dustbincleaner.dustbincleaner.Adapter.AllocateListAdapter;
import com.dustbincleaner.dustbincleaner.Interface.GetJsonArrayReesult;
import com.dustbincleaner.dustbincleaner.Interface.GetResult;
import com.dustbincleaner.dustbincleaner.Object.CleanerList;
import com.dustbincleaner.dustbincleaner.Util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AllocateDustbin extends AppCompatActivity {

    ArrayList<CleanerList> cleanerList = new ArrayList<>();
    ArrayList<String> dustbinlist = new ArrayList<String>();
    ListView li;
    AllocateListAdapter allocateListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allocate_dustbin);

        dustbinlist.add("bin1");
        dustbinlist.add("bin2");
        dustbinlist.add("bin3");
        dustbinlist.add("bin4");

        getcleanerlist();


    }


    void getcleanerlist(){

        new Util().getcleanerdata(this, new GetJsonArrayReesult() {
            @Override
            public void done(JSONArray jsonArray) {
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        JSONObject json = jsonArray.getJSONObject(i);
                        String id = json.getString("id");
                        String username  = json.getString("username");
                        String name  = json.getString("name");
                        cleanerList.add(new CleanerList(id,username,name));

                    } catch (JSONException e) {
                    }


                }

                li = (ListView) findViewById(R.id.dustbinlist);
                allocateListAdapter = new AllocateListAdapter(getApplicationContext(),dustbinlist,cleanerList);
                li.setAdapter(allocateListAdapter);


            }

        });


    }
}




