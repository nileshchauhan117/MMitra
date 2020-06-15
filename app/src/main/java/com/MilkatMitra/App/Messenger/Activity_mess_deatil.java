package com.MilkatMitra.App.Messenger;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.MilkatMitra.App.Helper.Constants;
import com.MilkatMitra.App.Helper.SessionManager;
import com.MilkatMitra.App.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Activity_mess_deatil extends AppCompatActivity
{

    ImageView back_btn;
    TextView tv_usernames,tv_emails;
    SessionManager sm;
    String Name="";
    EditText edit_mess;
       ImageView btn_ok;
    TextView txt_title;
     LinearLayout container;
     String RoleID="",UserID="";
     ScrollView layout_scroll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messdeatil);

        back_btn=(ImageView)findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        layout_scroll=(ScrollView)findViewById(R.id.layout_scroll);
        layout_scroll.fullScroll(View.FOCUS_DOWN);
        sm = new SessionManager(Activity_mess_deatil.this);
        //   progressBar_main = (ProgressBar) findViewById(R.id.progressBar_main);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        Intent intent = getIntent();
        RoleID = intent.getStringExtra("RoleID");
        UserID = intent.getStringExtra("UserID");
        Name= intent.getStringExtra("Name");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(Activity_mess_deatil.this, R.color.maroon_dark));
        }

        edit_mess=(EditText)findViewById(R.id.edit_mess);
        btn_ok=(ImageView) findViewById(R.id.btn_ok);
        txt_title=(TextView) findViewById(R.id.txt_title);
        txt_title.setText(Name);
        container=(LinearLayout)findViewById(R.id.container);

        loginUser();

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                add_data();

            }
        });

    }
    private void loginUser()
    {


        String u= Constants.URL+"selection/getUserConversation/{\"ReceiverID\":\""+UserID+"\",\"ReceiverRole\":\""+RoleID+"\"}";

        // String u= Constants.URL+"selection/getUserConversation/";
        Log.e("Url",u);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,u ,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        //{"Code":400,"Message":"Please submit user id"}
                        container.removeAllViews();
                        // Toast.makeText(Activity_Authorized.this,response,Toast.LENGTH_LONG).show();
                        Log.e("-----------",response+"");
                        try
                        {

                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("Code").equals("200"))
                            {

                                String  Quarters= jsonObject.getString("Messages");
                                JSONArray Messages = new JSONArray(Quarters);

                                for(int k=0;k<Messages.length();k++)
                                {
                                    String ReceiverID=Messages.getJSONObject(k).getString("ReceiverID").toString();
                                    LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    final View addView;
                                    if(ReceiverID.equalsIgnoreCase(UserID))
                                    {
                                        addView = layoutInflater.inflate(R.layout.adapter_left, null);
                                    }
                                    else
                                    {
                                        addView = layoutInflater.inflate(R.layout.adapter_rigt, null);
                                    }

                                    TextView txt_mess = (TextView)addView.findViewById(R.id.txt_mess);
                                    TextView txt_datettime = (TextView)addView.findViewById(R.id.txt_datettime);
                                    ImageView img_person=(ImageView)addView.findViewById(R.id.img_person);

                                    txt_mess.setText(Messages.getJSONObject(k).getString("Message").toString());
                                    txt_datettime.setText(Html.fromHtml("<font color='black'>"+Messages.getJSONObject(k).getString("SenderName").toString()+"</font> at "+Messages.getJSONObject(k).getString("DateTime").toString()));
                                  //  Picasso.with(getApplicationContext()).load(Messages.getJSONObject(k).getString("SenderName").toString()).into(img_person);

                                    container.addView(addView);
                                }




                            }
                        }
                        catch (Exception e)
                        {

                        }
                        // parseData(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        // progressBar_main.setVisibility(View.GONE);
                        Log.e("",error.toString());
                    }
                }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {


                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Host", Constants.Host);
                headers.put("Language", "" + Constants.Language);
                headers.put("Ipaddress", "" + Constants.Ipaddress);
                headers.put("Authorization", "" + Constants.Authorization);
                headers.put("Devicetype", "" + Constants.Devicetype);
                headers.put("Timestamp", "" + Constants.Timestamp);
                headers.put("Deviceid", "" + Constants.Deviceid);
                headers.put("Clientcode", "" + Constants.Clientcode);
                headers.put("Relationid",sm.getRelationID());
                headers.put("Tokenid", "" + sm.getto());
                headers.put("Userid", "" + sm.getUserID());
                headers.put("Roleid", "" + sm.getRoleID());

                return headers;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void add_data()
    {
      //  progressBar_main.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL+"insert/SendUserMessage/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                           // progressBar_main.setVisibility(View.GONE);
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("Code").equals("200"))
                            {
                                //mess(jsonObject.getString("Message"));
                                //Toast.makeText(getApplicationContext(),jsonObject.getString("Message"),Toast.LENGTH_LONG).show();
                                loginUser();

                            }
                            else
                            {
                              //  mess(jsonObject.getString("Message"));
                            }

                        }
                        catch (Exception e)
                        {

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                       // progressBar_main.setVisibility(View.GONE);
                       // mess(error.toString());
                    }
                }){


            @Override
            protected Map<String,String> getParams()
            {

                Date currentTime = Calendar.getInstance().getTime();
                Map<String,String> params = new HashMap<String, String>();
                params.put("ReceiverID",UserID);
                params.put("ReceiverRole",RoleID);
                params.put("Message",edit_mess.getText().toString());
                params.put("Timestamp",currentTime+"");


                Log.e("==========",params+"");
                edit_mess.setText("");
                return params;
            }
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {


                HashMap<String, String> headers = new HashMap<String, String>();


                headers.put("Host", Constants.Host);
                headers.put("Language", "" + Constants.Language);
                headers.put("Ipaddress", "" + Constants.Ipaddress);
                headers.put("Authorization", "" + Constants.Authorization);
                headers.put("Devicetype", "" + Constants.Devicetype);
                headers.put("Timestamp", "" + Constants.Timestamp);
                headers.put("Deviceid", "" + Constants.Deviceid);
                headers.put("Clientcode", "" + Constants.Clientcode);
                headers.put("Relationid",sm.getRelationID());
                headers.put("Tokenid", "" + sm.getto());
                headers.put("Userid", "" + sm.getUserID());
                headers.put("Roleid", "" + sm.getRoleID());



                return headers;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
