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
import com.dustbincleaner.dustbincleaner.Util.Util;
import com.dustbincleaner.dustbincleaner.Interface.GetResult;
import com.dustbincleaner.dustbincleaner.SharedPrefrence.UserLocalStore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity implements View.OnClickListener {

    String type,status,dustbin;
    EditText username,password;
    Button login;
    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        type = getIntent().getStringExtra("type");
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        login = (Button)findViewById(R.id.login);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == login){
            new Util().hideSoftKeyboard(this);
            if (new Util().check_connection(this)){
                String name = username.getText().toString();
                String pass = password.getText().toString();
                if (new Util().emptyvalidate(name,pass)) {
                    validatecredentials(name, pass);
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
                }
            }else{
                new Util().checkinternet(Login.this);
            }
        }
    }

    void validatecredentials(final String username, String password){

        Map<String, String> params = new HashMap<String, String>();
        params.put("username",username);
        params.put("password",password);

        if(type.equals("admin"))
            params.put("type","1");
        else
            params.put("type","2");


        new Util().loginvalidate(params, this, new GetResult() {


            @Override
            public void done(JSONObject jsonObject) {
                if (jsonObject != null) {
                    try {
                        status = jsonObject.getString("status");

                        if(status.equals("success")){
                            if(type.equals("cleaner")) {
                                dustbin = jsonObject.getString("dustbin");
                                if (!dustbin.equals("none")) {
                                    userLocalStore = new UserLocalStore(getApplicationContext());
                                    userLocalStore.userData("cleaner",username);
                                    userLocalStore.setUserloggedIn(true);
                                    userLocalStore.updatedustbin(dustbin);
                                    Intent in = new Intent(Login.this, Cleaner.class);
                                    startActivity(in);

                                } else {
                                    new Util().showerrormessage(Login.this,"You can't proceed furthuer as no dustbin alloted.");
                                }
                            }else{
                                userLocalStore = new UserLocalStore(getApplicationContext());
                                userLocalStore.userData("admin","admin");
                                userLocalStore.setUserloggedIn(true);
                                Intent in = new Intent(Login.this, Admin.class);
                                startActivity(in);
                            }
                        }else{

                            new Util().showerrormessage(Login.this,"Either wrong ID or password !!!!");
                        }



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
    }


    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        super.onBackPressed();

    }


    @Override
    protected void onResume() {

        UserLocalStore userLocalStore;
        userLocalStore = new UserLocalStore(this);
        super.onResume();
        if (userLocalStore.getuserloggedIn())
        {
            if(userLocalStore.getStatus().equals("admin")){
                startActivity(new Intent(this,Admin.class));
            }else if (userLocalStore.getStatus().equals("cleaner")){
                startActivity(new Intent(this,Cleaner.class));
            }
        }

    }

}
