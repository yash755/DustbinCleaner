package com.dustbincleaner.dustbincleaner.Activity;

import android.graphics.Color;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.dustbincleaner.R;
import com.dustbincleaner.dustbincleaner.Adapter.AllocateListAdapter;
import com.dustbincleaner.dustbincleaner.Adapter.DisplayDustbinAdapter;
import com.dustbincleaner.dustbincleaner.Interface.GetResult;
import com.dustbincleaner.dustbincleaner.Util.Util;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DustbinData extends AppCompatActivity {


    ArrayList<String> dustbindata = new ArrayList<String>();
    ListView li;
    DisplayDustbinAdapter displayDustbinAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dustbin_data);

        getdustbindata();
    }

    void getdustbindata(){

        new Util().getdustbindata(this, new GetResult() {
            @Override
            public void done(JSONObject jsonObject) {

                int j = 1;
                for (int i=0; i<jsonObject.length(); i++) {
                    try {
                        String data = jsonObject.getString("bin" + j);
                        dustbindata.add(data);
                        j++;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                li = (ListView) findViewById(R.id.dusdata);
                displayDustbinAdapter = new DisplayDustbinAdapter(getApplicationContext(),dustbindata);
                li.setAdapter(displayDustbinAdapter);

                }
        });
    }


}
