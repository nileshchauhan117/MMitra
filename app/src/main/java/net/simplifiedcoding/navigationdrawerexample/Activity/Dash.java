package net.simplifiedcoding.navigationdrawerexample.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.simplifiedcoding.navigationdrawerexample.Activity_Current_Project_details;
import net.simplifiedcoding.navigationdrawerexample.Activity_Current_Project_details_CA;
import net.simplifiedcoding.navigationdrawerexample.Activity_Form;
import net.simplifiedcoding.navigationdrawerexample.Activity_Form3;
import net.simplifiedcoding.navigationdrawerexample.Activity_Login;
import net.simplifiedcoding.navigationdrawerexample.Activity_contactus;
import net.simplifiedcoding.navigationdrawerexample.Activity_qpr;
import net.simplifiedcoding.navigationdrawerexample.Helper.SessionManager;
import net.simplifiedcoding.navigationdrawerexample.R;

public class Dash extends AppCompatActivity
{

    ImageView back_btn;
    TextView tv_usernames,tv_emails;
    SessionManager sm;

    RelativeLayout layout_basic,
            layout_qpr,
    layout_qprlist,
            layout_inventory;
String ProjectID,page,pname;
TextView title1;
String Confirmation="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(Dash.this, R.color.maroon_dark));
        }
        sm = new SessionManager(Dash.this);
        layout_basic=(RelativeLayout) findViewById(R.id.layout_basic);
                layout_qpr=(RelativeLayout) findViewById(R.id.layout_qpr);
        layout_qprlist=(RelativeLayout) findViewById(R.id.layout_qprlist);
                layout_inventory=(RelativeLayout) findViewById(R.id.layout_inventory);
        title1=(TextView)findViewById(R.id.title1);

        Intent intent = getIntent();
        ProjectID = intent.getStringExtra("ProjectID");
        page = intent.getStringExtra("page");
        pname = intent.getStringExtra("pname");


        title1.setText(pname);
        if(sm.getloginid().equalsIgnoreCase("promoter"))
        {

            layout_inventory.setVisibility(View.VISIBLE);
        }
        else
        {

            layout_inventory.setVisibility(View.INVISIBLE);
        }
        layout_basic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(sm.getloginid().equalsIgnoreCase("promoter"))
                {
                    Intent i=new Intent(getApplicationContext(), Activity_Current_Project_details.class);
                    i.putExtra("ProjectID",ProjectID);
                    i.putExtra("page","4");
                    startActivity(i);
                }
                else
                {
                    Intent i=new Intent(getApplicationContext(), Activity_Current_Project_details_CA.class);
                    i.putExtra("ProjectID",ProjectID);
                    i.putExtra("page","4");
                    startActivity(i);
                }



            }
        });
        layout_qpr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), Activity_qpr.class);
                i.putExtra("ProjectID",ProjectID);
                i.putExtra("page","4");
                startActivity(i);
            }
        });
        layout_qprlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(getApplicationContext(), Dash_QprtList.class);
                i.putExtra("ProjectID",ProjectID);
                i.putExtra("page","4");
                startActivity(i);
            }
        });
        layout_inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), Dash_Inventory.class);
                i.putExtra("ProjectID",ProjectID);
                startActivity(i);
            }
        });
        back_btn=(ImageView)findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        try
        {
            Confirmation = intent.getStringExtra("Confirmation");
            if(Confirmation.equalsIgnoreCase("Y")||Confirmation.equalsIgnoreCase("1"))
            {
                layout_qpr.setVisibility(View.VISIBLE);
               // layout_inventory.setVisibility(View.VISIBLE);
            }
            else
            {
                layout_qpr.setVisibility(View.INVISIBLE);
               // layout_inventory.setVisibility(View.INVISIBLE);
            }

        }
        catch (Exception e)
        {

        }
    }


}