package com.dustbincleaner.dustbincleaner.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.dustbincleaner.R;
import com.dustbincleaner.dustbincleaner.SharedPrefrence.UserLocalStore;
import com.dustbincleaner.dustbincleaner.Util.Util;
import com.google.firebase.messaging.FirebaseMessaging;

public class Admin extends AppCompatActivity implements View.OnClickListener {

    LinearLayout addcleaner,allocatedustbin,dustbindta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.admin_action_bar);
        View view =getSupportActionBar().getCustomView();

        ImageButton imageButton= (ImageButton)view.findViewById(R.id.logout);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserLocalStore userLocalStore = new UserLocalStore(getApplicationContext());
                userLocalStore.clearUserdata();
                Intent in = new Intent(getApplicationContext(), Splash.class);
                startActivity(in);
            }
        });


        if(getIntent().hasExtra("addcleanerstatus")){
            new Util().showSuccessmessage(this,"Add Successfully");
        }

        addcleaner = (LinearLayout)findViewById(R.id.addcleaner);
        allocatedustbin = (LinearLayout)findViewById(R.id.allocatedustbin);
        dustbindta = (LinearLayout)findViewById(R.id.dustbinstatus);

        addcleaner.setOnClickListener(this);
        allocatedustbin.setOnClickListener(this);
        dustbindta.setOnClickListener(this);

        FirebaseMessaging.getInstance().subscribeToTopic("bin1");
        FirebaseMessaging.getInstance().subscribeToTopic("bin2");
        FirebaseMessaging.getInstance().subscribeToTopic("bin3");
        FirebaseMessaging.getInstance().subscribeToTopic("bin4");

    }

    @Override
    protected void onResume() {
        UserLocalStore userLocalStore;
        userLocalStore = new UserLocalStore(this);
        super.onResume();
        if (!userLocalStore.getuserloggedIn())
            startActivity(new Intent(this,Splash.class));
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
    public void onClick(View v) {

        if(v == addcleaner){
            startActivity(new Intent(this,AddCleaner.class));
        }else if (v == allocatedustbin){
            startActivity(new Intent(this,AllocateDustbin.class));
        }else if(v == dustbindta){
            startActivity(new Intent(this,DustbinData.class));
        }
    }
}
