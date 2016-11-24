package com.dustbincleaner.dustbincleaner.Adapter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dustbincleaner.R;
import com.dustbincleaner.dustbincleaner.Activity.AddCleaner;
import com.dustbincleaner.dustbincleaner.Activity.Admin;
import com.dustbincleaner.dustbincleaner.Interface.GetResult;
import com.dustbincleaner.dustbincleaner.Object.CleanerList;
import com.dustbincleaner.dustbincleaner.Util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AllocateListAdapter extends BaseAdapter {


    private ArrayList<CleanerList> cleanerList;
    private ArrayList<String> dustbinList;
    LayoutInflater inflater;
    private Context activity;


    public AllocateListAdapter(Context a, ArrayList<String> dustbinList,ArrayList<CleanerList> cleanerList) {
        activity = a;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.dustbinList = dustbinList;
        this.cleanerList = cleanerList;
    }

    @Override
    public int getCount() {
        return dustbinList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.allocatelist_custom, null);


        TextView dustbin=(TextView) vi.findViewById(R.id.bin);
        dustbin.setText(dustbinList.get(position));


        final Spinner dropdown = (Spinner)vi.findViewById(R.id.cleaner);


        ArrayList<String> stringArrayList = new ArrayList<String>();

        stringArrayList.add("Select Cleaner");

        for (int i=0; i<cleanerList.size(); i++) {

            stringArrayList.add(cleanerList.get(i).name);
        }

        String [] cleanerArray = stringArrayList.toArray(new String[stringArrayList.size()]);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, cleanerArray);
        dropdown.setAdapter(adapter);

        final String dus= dustbinList.get(position);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                int pos = dropdown.getSelectedItemPosition();

                if(pos != 0) {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("dustbin", dus);
                    params.put("cleanerid", cleanerList.get(pos-1).id);

                    new Util().allocatedustbin(params, activity, new GetResult() {
                        @Override
                        public void done(JSONObject jsonObject) {

                            String status = null;
                            try {
                                status = jsonObject.getString("status");
                                if (status.equals("success")) {
                                    //     new Util().showSuccessmessage(activity,"Alloted Successfully!!!!!!!");
                                    Toast.makeText(activity,"Alloted Successfully!!!!!!!",
                                            Toast.LENGTH_SHORT).show();

                                }else
                                    Toast.makeText(activity,"Sorry not allotted.....Try again!!!!",
                                            Toast.LENGTH_SHORT).show();
                                //    new Util().showerrormessage(activity,"Sorry not allotted.....Try again!!!!");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }

        });

        return vi;
    }


}
