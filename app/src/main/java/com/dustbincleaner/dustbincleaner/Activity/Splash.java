package com.dustbincleaner.dustbincleaner.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dustbincleaner.R;
import com.dustbincleaner.dustbincleaner.SharedPrefrence.UserLocalStore;

public class Splash extends AppCompatActivity implements View.OnClickListener {

    Button admin,cleaner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        admin = (Button)findViewById(R.id.admin);
        cleaner=(Button)findViewById(R.id.cleaner);

        admin.setOnClickListener(this);
        cleaner.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if(v == admin){
            Intent intent = new Intent(Splash.this,Login.class);
            intent.putExtra("type", "admin");
            startActivity(intent);

        }else if (v == cleaner){
            Intent intent = new Intent(Splash.this,Login.class);
            intent.putExtra("type", "cleaner");
            startActivity(intent);

        }
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
