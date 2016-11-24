package com.dustbincleaner.dustbincleaner.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.dustbincleaner.R;
import java.util.ArrayList;

public class DisplayDustbinAdapter extends BaseAdapter {


    private ArrayList<String> dustbinList;
    LayoutInflater inflater;
    private Context activity;


    public DisplayDustbinAdapter(Context a, ArrayList<String> dustbinList) {
        activity = a;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.dustbinList = dustbinList;
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
            vi = inflater.inflate(R.layout.dustbin_data_custom, null);


        TextView tv = (TextView) vi.findViewById(R.id.tv);
        ProgressBar pb = (ProgressBar) vi.findViewById(R.id.pb);

        int data = Integer.parseInt(dustbinList.get(position)) * 10;

        pb.setProgress(data);
        tv.setText(dustbinList.get(position) + "0% Dustbin filled");



        return vi;
    }


}

