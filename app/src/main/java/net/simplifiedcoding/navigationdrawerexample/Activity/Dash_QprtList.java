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

import net.simplifiedcoding.navigationdrawerexample.Activity_Form;
import net.simplifiedcoding.navigationdrawerexample.Activity_Form2;
import net.simplifiedcoding.navigationdrawerexample.Activity_Form3;
import net.simplifiedcoding.navigationdrawerexample.Activity_consultants;
import net.simplifiedcoding.navigationdrawerexample.Form2a;
import net.simplifiedcoding.navigationdrawerexample.Activity_qpr;
import net.simplifiedcoding.navigationdrawerexample.Helper.SessionManager;
import net.simplifiedcoding.navigationdrawerexample.R;

public class Dash_QprtList extends AppCompatActivity
{

    ImageView back_btn;
    TextView tv_usernames,tv_emails;
    SessionManager sm;
    LinearLayout layout_fC,
    layout_fone,
            layout_ftwo,layout_ftwoa,
    layout_fthree;
    String ProjectID,page;
    String title="";
    TextView title1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_qprlist);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(Dash_QprtList.this, R.color.maroon_dark));
        }
        layout_fC=(LinearLayout) findViewById(R.id.layout_fC);
        layout_fone=(LinearLayout) findViewById(R.id.layout_fone);
        layout_ftwoa=(LinearLayout) findViewById(R.id.layout_ftwoa);
        layout_ftwo=(LinearLayout) findViewById(R.id.layout_ftwo);
        layout_fthree=(LinearLayout) findViewById(R.id.layout_fthree);
       layout_ftwoa.setVisibility(View.GONE);
        sm = new SessionManager(Dash_QprtList.this);
        Intent intent = getIntent();
        ProjectID = intent.getStringExtra("ProjectID");
        page = intent.getStringExtra("page");

        try
        {
            title1=(TextView)findViewById(R.id.title1);
            title = intent.getStringExtra("title");
            if(title.equalsIgnoreCase(""))
                title1.setText("Registration Forms");
            else
                title1.setText(title+" Registration Forms");
        }
        catch (Exception e)
        {

        }



        if(sm.getloginid().equalsIgnoreCase("architects"))
        {
            layout_fC.setVisibility(View.GONE);
            layout_fone.setVisibility(View.VISIBLE);

            layout_fthree.setVisibility(View.GONE);
            layout_ftwo.setVisibility(View.GONE);

            Intent i=new Intent(getApplicationContext(), Activity_Form.class);
            i.putExtra("ProjectID",ProjectID);
            i.putExtra("page","4");
            i.putExtra("title",title);
            startActivity(i);
            finish();

        }
        else if(sm.getloginid().equalsIgnoreCase("engineer"))
        {
            layout_ftwo.setVisibility(View.VISIBLE);
            layout_fC.setVisibility(View.GONE);
            layout_fone.setVisibility(View.GONE);
            layout_fthree.setVisibility(View.GONE);
            layout_ftwoa.setVisibility(View.VISIBLE);



          //  Intent i=new Intent(getApplicationContext(), Activity_Form2.class);
           // i.putExtra("ProjectID",ProjectID);
           // i.putExtra("page","4");
           // i.putExtra("title",title);
           // startActivity(i);
            //finish();
        }
        else if(sm.getloginid().equalsIgnoreCase("Chartered Accountant"))
        {
            layout_fthree.setVisibility(View.VISIBLE);
            layout_fone.setVisibility(View.GONE);
            layout_ftwo.setVisibility(View.GONE);
            layout_fC.setVisibility(View.GONE);
            Intent i=new Intent(getApplicationContext(), Activity_Form3.class);
            i.putExtra("ProjectID",ProjectID);
            i.putExtra("title",title);
            startActivity(i);
            finish();
        }
        else
        {
            layout_fC.setVisibility(View.VISIBLE);
            layout_fone.setVisibility(View.VISIBLE);
            layout_fthree.setVisibility(View.VISIBLE);
            layout_ftwo.setVisibility(View.VISIBLE);

        }

        if(sm.getloginid().equalsIgnoreCase("architects"))
        {
            layout_fC.setVisibility(View.GONE);
        }
        else if(sm.getloginid().equalsIgnoreCase("engineer"))
        {
            layout_fC.setVisibility(View.GONE);
        }
        else if(sm.getloginid().equalsIgnoreCase("Chartered Accountant"))
        {
            layout_fC.setVisibility(View.GONE);
        }
        else
        {
            layout_fC.setVisibility(View.VISIBLE);

        }

        layout_fC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                        Intent i=new Intent(getApplicationContext(), Activity_consultants.class);
                        i.putExtra("ProjectID",ProjectID);
                        i.putExtra("page","4");
                i.putExtra("title",title);
                        startActivity(i);


            }
        });
        layout_fone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), Activity_Form.class);
                i.putExtra("ProjectID",ProjectID);
                i.putExtra("page","4");
                i.putExtra("title",title);
                startActivity(i);
            }
        });
        layout_ftwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(getApplicationContext(), Activity_Form2.class);
                i.putExtra("ProjectID",ProjectID);
                i.putExtra("page","4");
                i.putExtra("title",title);
                startActivity(i);
            }
        });
        layout_ftwoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(getApplicationContext(), Form2a.class);
                i.putExtra("ProjectID",ProjectID);
                i.putExtra("page","4");
                i.putExtra("title",title);
                startActivity(i);
            }
        });
        layout_fthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(), Activity_Form3.class);
                i.putExtra("ProjectID",ProjectID);
                i.putExtra("title",title);
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
    }
}