package net.simplifiedcoding.navigationdrawerexample;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import net.simplifiedcoding.navigationdrawerexample.Helper.SessionManager;

public class Activity_Add_Athorized extends AppCompatActivity {

    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_authorized);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(Activity_Add_Athorized.this, R.color.maroon_dark));
        }
        sm = new SessionManager(Activity_Add_Athorized.this);
    }
}


