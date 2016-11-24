package com.dustbincleaner.dustbincleaner.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.dustbincleaner.R;
import com.dustbincleaner.dustbincleaner.Interface.GetResult;
import com.dustbincleaner.dustbincleaner.Util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddCleaner extends AppCompatActivity implements View.OnClickListener {

    EditText name,username,password;
    Button addcleaner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cleaner);



        name = (EditText)findViewById(R.id.name);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);

        addcleaner = (Button)findViewById(R.id.add);
        addcleaner.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v == addcleaner){

            new Util().hideSoftKeyboard(this);
            if (new Util().check_connection(this)){
                String names = name.getText().toString();
                String usernames = username.getText().toString();
                String pass = password.getText().toString();
                if (new Util().emptyvalidatethree(names,usernames,pass)) {
                    addcleaners(names,usernames,pass);
                } else {
                    if (username.getText().toString().trim().equals("")) {
                        YoYo.with(Techniques.Pulse)
                                .duration(700)
                                .playOn(findViewById(R.id.username));
                    }
                    if (password.getText().toString().trim().equals("")) {
                        YoYo.with(Techniques.Pulse)
                                .duration(700)
                                .playOn(findViewById(R.id.password));
                    }
                    if (name.getText().toString().trim().equals("")) {
                        YoYo.with(Techniques.Pulse)
                                .duration(700)
                                .playOn(findViewById(R.id.password));
                    }
                }
            }else{
                new Util().checkinternet(this);
            }
        }
    }

    void addcleaners(String name,String usernames,String password){

        Map<String, String> params = new HashMap<String, String>();
        params.put("name", name);
        params.put("username", usernames);
        params.put("password", usernames);
        params.put("type","1");


        new Util().addcleaner(params, this, new GetResult() {
            @Override
            public void done(JSONObject jsonObject) {

                String status = null;
                try {
                    status = jsonObject.getString("status");
                    if(status.equals("success")){
                        Intent intent = new Intent(getApplicationContext(),Admin.class);
                        intent.putExtra("addcleanerstatus", "1");
                        startActivity(intent);

                    }else
                        new Util().showerrormessage(AddCleaner.this,"Sorry not added.....Try again!!!!");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

}
