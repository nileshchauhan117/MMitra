package net.simplifiedcoding.navigationdrawerexample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.simplifiedcoding.navigationdrawerexample.Helper.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity_Login extends AppCompatActivity {
    ProgressBar progressBar_main;
    Button btnlogin;
    EditText txt_mnos,txt_passs;
    SessionManager sm;
    String TokenID;
    String UserID ;
    String RoleID ;
    String RelationID ;
    TextView txt_regi,txt_fpass;
    LinearLayout layout1,layout2,layout3;
    CheckBox radio_turms;
    String type="O";
    RadioGroup rg;
    int pos;
    int pos1;
    TextView txt_pp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setStatusBarColor(ContextCompat.getColor(Activity_Login.this, R.color.maroon_dark));
        }
        sm = new SessionManager(Activity_Login.this);
        progressBar_main = (ProgressBar) findViewById(R.id.progressBar_main);
        btnlogin = (Button) findViewById(R.id.btnlogin);
        txt_pp= (TextView) findViewById(R.id.txt_pp);
        txt_regi= (TextView) findViewById(R.id.txt_regi);
        txt_mnos = (EditText) findViewById(R.id.txt_mnos);
        radio_turms= (CheckBox) findViewById(R.id.radio_turms);
        txt_passs = (EditText) findViewById(R.id.txt_passs);
        txt_fpass=(TextView)findViewById(R.id.txt_fpass);
        layout1=(LinearLayout)findViewById(R.id.layout1);
        layout2=(LinearLayout)findViewById(R.id.layout2);
        layout3=(LinearLayout)findViewById(R.id.layout3);
        rg = (RadioGroup) findViewById(R.id.radioGroup1);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                pos=rg.indexOfChild(findViewById(checkedId));



                //Method 2 For Getting Index of RadioButton
                pos1=rg.indexOfChild(findViewById(rg.getCheckedRadioButtonId()));

                switch (pos)
                {
                    case 0 :
                        type="O";
                        break;
                    case 1 :
                        type="A";
                        break;
                    default :
                        type="O";
                        break;
                }
            }
        });
        layout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),Activity_services.class);
                startActivity(i);
            }
        });
        txt_pp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Activity_PP.class);
                startActivity(i);
            }
        });
        layout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),Activity_aboutus.class);
                startActivity(i);
            }
        });

        layout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),Activity_contactus.class);
                startActivity(i);
            }
        });

        txt_regi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),Activity_Regi.class);
                startActivity(i);
            }
        });
        txt_fpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),Activity_FPass.class);
                startActivity(i);
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(txt_mnos.getText().toString().equalsIgnoreCase(""))
                {
                    mess("Enter Valid Username");
                }
                else if(txt_passs.getText().toString().equalsIgnoreCase(""))
                {
                    mess("Enter Valid Password");
                }
                else
                {
                    // if(radio_turms.isChecked())
                    // {
                         loginUser();

                    // }
                  //  else
                  //  {
                  //      mess("Check terms and conditions");
                   // }

                }
                //Intent i = new Intent(getApplicationContext(), MainActivity.class);
               // startActivity(i);
            }
        });
    }


    public void mess(String mess) {
        Snackbar snackbar = Snackbar.make(btnlogin, mess, Snackbar.LENGTH_LONG).setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.parseColor("#FA8072"));
        snackbar.show();
    }
    private void loginUser()
    {

        progressBar_main.setVisibility(View.VISIBLE);
        final String username = txt_mnos.getText().toString();
        final String password = txt_passs.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sighteat.com/imitra/api/authenticate/login/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        progressBar_main.setVisibility(View.GONE);
                        //Toast.makeText(Activity_Login.this,response,Toast.LENGTH_LONG).show();
                        parseData(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        progressBar_main.setVisibility(View.GONE);
                        mess(error.toString());
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("LoginID",username);
                params.put("Password",password);
                params.put("Type",type);

                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {


                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Host", "sighteat.com");
                headers.put("Language", "" + "en");
                headers.put("Ipaddress", "" + "185.255.554.20");
                headers.put("Authorization", "" + "NWNiNTZiZGZmMWZiZTU1MGE1ODUxY2RlODE1MzIxNzRiZjIyMTVkMmI2Mzli");
                headers.put("Devicetype", "" + "Web");
                headers.put("Timestamp", "" + "15672401123");
                headers.put("Deviceid", "" + "Android");
                headers.put("Clientcode", "" + "5cb56bfb61a6f");
                headers.put("Tokenid", "" + "8ba100109997531537277777");
                headers.put("Userid", "" + "1");
                headers.put("Roleid", "" + "1");
                headers.put("Relationid", "" + "1");


                return headers;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void profile()
    {

        progressBar_main.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://sighteat.com/imitra/api/selection/getUserProfile/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        progressBar_main.setVisibility(View.GONE);

                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("Code").equals("200"))
                            {
                                JSONObject object = jsonObject.getJSONObject("ProfileData");
                                String UserID = object.getString("UserID");
                                String RoleID = object.getString("RoleID");
                                String RelationID="";
                                try
                                {
                                     RelationID = object.getString("RelationID");
                                }
                                catch (Exception e)
                                {

                                }

                                String PathName= object.getString("ProfilePicture");
                                String LoginID= object.getString("RoleName");
                               // String TokenID = object.getString("TokenID");
                                Log.e("----------",TokenID);
                                Log.e("----------",sm.getto());
                                String MediaID= object.getString("MediaID");
                                String KYCAlert = object.getString("KYCAlert");
                                String PasswordAlert= object.getString("PasswordAlert");


                                String Name = object.getString("Name");
                                String LastName = object.getString("LastName");
                                String MobileNumber = object.getString("MobileNumber");


                                sm.setUserId(UserID, RoleID, RelationID, PathName,   TokenID, object+"", MobileNumber, LastName, Name,LoginID);

                                //Toast.makeText(getApplicationContext(),MobileNumber,Toast.LENGTH_LONG).show();
                                //mess(jsonObject.getString("Message"));

                                if(PasswordAlert.equalsIgnoreCase("N")&&KYCAlert.equalsIgnoreCase("N"))
                                {
                                    if(LoginID.equalsIgnoreCase("promoter"))
                                    {
                                        Intent intent = new Intent(Activity_Login.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        startActivity(intent);
                                        overridePendingTransition(0, 0);
                                        finish();
                                    }

                                   else
                                    {
                                        Intent intent = new Intent(Activity_Login.this, MainActivity_Ca.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        startActivity(intent);
                                        overridePendingTransition(0, 0);
                                        finish();
                                    }
                                }
                                else
                                {
                                    Intent intent = new Intent(Activity_Login.this, Activity_Verification.class);
                                    intent.putExtra("PasswordAlert",PasswordAlert);
                                    intent.putExtra("KYCAlert",KYCAlert);
                                    intent.putExtra("RoleID",RoleID);
                                    intent.putExtra("UserID",UserID);
                                    intent.putExtra("MediaID",MediaID);

                                    startActivity(intent);
                                    overridePendingTransition(0, 0);
                                    finish();
                                }


                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),jsonObject.getString("Message"),Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Activity_Login.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intent);
                                overridePendingTransition(0, 0);
                                finish();
                            }
                        }
                        catch (Exception e)
                        {

                        }

                       // Toast.makeText(Activity_Login.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        progressBar_main.setVisibility(View.GONE);
                        mess(error.toString());
                    }
                }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {


                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Host", "sighteat.com");
                headers.put("Language", "" + "en");
                headers.put("Ipaddress", "" + "185.255.554.20");
                headers.put("Authorization", "" + "NWNiNTZiZGZmMWZiZTU1MGE1ODUxY2RlODE1MzIxNzRiZjIyMTVkMmI2Mzli");
                headers.put("Devicetype", "" + "Web");
                headers.put("Timestamp", "" + "15672401123");
                headers.put("Deviceid", "" + "Android");
                headers.put("Clientcode", "" + "5cb56bfb61a6f");
                headers.put("Tokenid", "" + TokenID);
                headers.put("Userid", "" + UserID);
                headers.put("Roleid", "" + RoleID);
                headers.put("Relationid", "" + RelationID);

                Log.e("Header",headers+"");
                return headers;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void parseData(String response) {

        try
        {

            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("Code").equals("200"))
            {
                JSONObject object = jsonObject.getJSONObject("Profile");
                 UserID = object.getString("UserID");
                 RoleID = object.getString("RoleID");
                 try
                 {
                     RelationID = object.getString("RelationID");
                 }
                 catch (Exception e)
                 {

                 }

                 TokenID = object.getString("TokenID");

                String Name = object.getString("Name");
                String LoginID = object.getString("Name");
                String LastName = object.getString("LastName");
                String MobileNumber = object.getString("MobileNumber");

                sm.createLoginSession(UserID);
                sm.setUserId(UserID, RoleID, RelationID, TokenID, TokenID, MobileNumber, MobileNumber, LastName, Name,LoginID);
                profile();
                //Toast.makeText(getApplicationContext(),MobileNumber,Toast.LENGTH_LONG).show();
               // mess(jsonObject.getString("Message"));

               /* Intent intent = new Intent(Activity_Login.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();*/

            }
            else
            {
                mess(jsonObject.getString("Message"));
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

    }
}
