package com.MilkatMitra.App.Messenger;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.MilkatMitra.App.Activity_aboutus;
import com.MilkatMitra.App.Fund.Activity_Tab_CreditOverdraft;
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

import java.util.HashMap;
import java.util.Map;

public class Activity_messlist extends AppCompatActivity
{

    ImageView back_btn;
    TextView tv_usernames,tv_emails;
    SessionManager sm;
    TextView tv_display;
    ProgressBar progressBar_main;
    WebView webView;
    TextView title1;
    LinearLayout
            container_SubUsers,
    container_Architect,
            container_Engineers,
    container_CA;

    TextView txt_SubUsers,
    txt_Architect,
            txt_Engineers,
    txt_CA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messlist);

        sm = new SessionManager(Activity_messlist.this);
     //   progressBar_main = (ProgressBar) findViewById(R.id.progressBar_main);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(Activity_messlist.this, R.color.maroon_dark));
        }
        container_SubUsers=(LinearLayout)findViewById(R.id.container_SubUsers) ;
                container_Architect=(LinearLayout)findViewById(R.id.container_Architect) ;
        container_Engineers=(LinearLayout)findViewById(R.id.container_Engineers) ;
                container_CA=(LinearLayout)findViewById(R.id.container_CA) ;

        txt_SubUsers=(TextView)findViewById(R.id.txt_SubUsers) ;
        txt_Architect=(TextView)findViewById(R.id.txt_Architect) ;
        txt_Engineers=(TextView)findViewById(R.id.txt_Engineers) ;
        txt_CA=(TextView)findViewById(R.id.txt_CA) ;


        txt_SubUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                container_SubUsers.setVisibility(View.VISIBLE);
                container_Architect.setVisibility(View.GONE);
                container_Engineers.setVisibility(View.GONE);
                container_CA.setVisibility(View.GONE);

                txt_SubUsers.setBackgroundResource(R.color.maroon_tra);
                txt_Architect.setBackgroundResource(R.color.White);
                txt_Engineers.setBackgroundResource(R.color.White);
                txt_CA.setBackgroundResource(R.color.White);

            }
        });
        txt_Architect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                container_SubUsers.setVisibility(View.GONE);
                container_Architect.setVisibility(View.VISIBLE);
                container_Engineers.setVisibility(View.GONE);
                container_CA.setVisibility(View.GONE);

                txt_SubUsers.setBackgroundResource(R.color.white);
                txt_Architect.setBackgroundResource(R.color.maroon_tra);
                txt_Engineers.setBackgroundResource(R.color.White);
                txt_CA.setBackgroundResource(R.color.White);
            }
        });
        txt_Engineers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                container_SubUsers.setVisibility(View.GONE);
                container_Architect.setVisibility(View.GONE);
                container_Engineers.setVisibility(View.VISIBLE);
                container_CA.setVisibility(View.GONE);

                txt_SubUsers.setBackgroundResource(R.color.white);
                txt_Architect.setBackgroundResource(R.color.white);
                txt_Engineers.setBackgroundResource(R.color.maroon_tra);
                txt_CA.setBackgroundResource(R.color.White);
            }
        });
        txt_CA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                container_SubUsers.setVisibility(View.GONE);
                container_Architect.setVisibility(View.GONE);
                container_Engineers.setVisibility(View.GONE);
                container_CA.setVisibility(View.VISIBLE);

                txt_SubUsers.setBackgroundResource(R.color.white);
                txt_Architect.setBackgroundResource(R.color.white);
                txt_Engineers.setBackgroundResource(R.color.White);
                txt_CA.setBackgroundResource(R.color.maroon_tra);
            }
        });

        container_SubUsers.setVisibility(View.GONE);
        container_Architect.setVisibility(View.GONE);
        container_Engineers.setVisibility(View.GONE);
        container_CA.setVisibility(View.VISIBLE);

        back_btn = (ImageView) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
       // loginUser();



    }


    @Override
    protected void onResume()
    {
        super.onResume();

        loginUser();
    }
    private void loginUser()
    {
        String u=Constants.URL+"selection/getMessageContacts/";

       // String u= Constants.URL+"selection/getUserConversation/";
        Log.e("Url",u);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,u ,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        //{"Code":400,"Message":"Please submit user id"}
                        // Toast.makeText(Activity_Authorized.this,response,Toast.LENGTH_LONG).show();

                        container_SubUsers.removeAllViews();
                        container_Architect.removeAllViews();
                        container_Engineers.removeAllViews();
                        container_CA.removeAllViews();

                        Log.e("-----------",response+"");
                        try
                        {

                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("Code").equals("200"))
                            {


                                String  Users= jsonObject.getString("Users");
                                JSONObject ConstructionCost_jsonObject = new JSONObject(Users);

                                String  Quarters= ConstructionCost_jsonObject.getString("CA");
                                JSONArray CA = new JSONArray(Quarters);

                                for(int k=0;k<CA.length();k++)
                                {
                                    LayoutInflater layoutInflater =
                                            (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    final View addView = layoutInflater.inflate(R.layout.adapter_mess, null);
                                    TextView txt_name = (TextView)addView.findViewById(R.id.txt_name);

                                    ImageView img_person=(ImageView)addView.findViewById(R.id.img_person);
                                    TextView txt_lastmess= (TextView)addView.findViewById(R.id.txt_lastmess);
                                    TextView txt_datetime= (TextView)addView.findViewById(R.id.txt_datetime);
                                    LinearLayout layout_person=(LinearLayout)addView.findViewById(R.id.layout_person);
                                    txt_name.setText(CA.getJSONObject(k).getString("ContactName").toString()+"  ●");
                                    txt_lastmess.setText(CA.getJSONObject(k).getString("MessageText").toString());
                                    txt_datetime.setText(CA.getJSONObject(k).getString("MessageTime").toString());
                                    Picasso.with(getApplicationContext()).load(CA.getJSONObject(k).getString("ProfilePicture").toString()).into(img_person);
                                    final String RoleID=CA.getJSONObject(k).getString("RoleID").toString();
                                    final String UserID=CA.getJSONObject(k).getString("UserID").toString();
                                    final String name=CA.getJSONObject(k).getString("ContactName").toString();
                                    layout_person.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            Intent i=new Intent(getApplicationContext(),Activity_mess_deatil.class);
                                            i.putExtra("RoleID",RoleID);
                                            i.putExtra("UserID",UserID);
                                            i.putExtra("Name",name);
                                            startActivity(i);

                                        }
                                    });

                                    container_CA.addView(addView);
                                }

                                String  Engineer= ConstructionCost_jsonObject.getString("Engineer");
                                JSONArray Engineer_a = new JSONArray(Engineer);

                                for(int k=0;k<Engineer_a.length();k++)
                                {
                                    LayoutInflater layoutInflater =
                                            (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    final View addView = layoutInflater.inflate(R.layout.adapter_mess, null);
                                    TextView txt_name = (TextView)addView.findViewById(R.id.txt_name);
                                    ImageView img_person=(ImageView)addView.findViewById(R.id.img_person);
                                    TextView txt_lastmess= (TextView)addView.findViewById(R.id.txt_lastmess);
                                    TextView txt_datetime= (TextView)addView.findViewById(R.id.txt_datetime);
                                    LinearLayout layout_person=(LinearLayout)addView.findViewById(R.id.layout_person);
                                    txt_name.setText(Engineer_a.getJSONObject(k).getString("ContactName").toString()+"  ●");
                                    txt_lastmess.setText(Engineer_a.getJSONObject(k).getString("MessageText").toString());
                                    txt_datetime.setText(Engineer_a.getJSONObject(k).getString("MessageTime").toString());
                                    Picasso.with(getApplicationContext()).load(Engineer_a.getJSONObject(k).getString("ProfilePicture").toString()).into(img_person);
                                    final String RoleID=Engineer_a.getJSONObject(k).getString("RoleID").toString();
                                    final String UserID=Engineer_a.getJSONObject(k).getString("UserID").toString();
                                    final String name=Engineer_a.getJSONObject(k).getString("ContactName").toString();
                                    layout_person.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            Intent i=new Intent(getApplicationContext(),Activity_mess_deatil.class);
                                            i.putExtra("RoleID",RoleID);
                                            i.putExtra("UserID",UserID);
                                            i.putExtra("Name",name);
                                            startActivity(i);

                                        }
                                    });

                                    container_Engineers.addView(addView);
                                }

                                String  Architect= ConstructionCost_jsonObject.getString("Architect");
                                JSONArray Architect_a = new JSONArray(Architect);

                                for(int k=0;k<Architect_a.length();k++)
                                {
                                    LayoutInflater layoutInflater =
                                            (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    final View addView = layoutInflater.inflate(R.layout.adapter_mess, null);
                                    TextView txt_name = (TextView)addView.findViewById(R.id.txt_name);
                                    ImageView img_person=(ImageView)addView.findViewById(R.id.img_person);
                                    TextView txt_lastmess= (TextView)addView.findViewById(R.id.txt_lastmess);
                                    TextView txt_datetime= (TextView)addView.findViewById(R.id.txt_datetime);
                                    LinearLayout layout_person=(LinearLayout)addView.findViewById(R.id.layout_person);
                                    txt_name.setText(Architect_a.getJSONObject(k).getString("ContactName").toString()+"  ●");
                                    txt_lastmess.setText(Architect_a.getJSONObject(k).getString("MessageText").toString());
                                    txt_datetime.setText(Architect_a.getJSONObject(k).getString("MessageTime").toString());
                                    final String RoleID=Architect_a.getJSONObject(k).getString("RoleID").toString();
                                    final String UserID=Architect_a.getJSONObject(k).getString("UserID").toString();
                                    final String name=Architect_a.getJSONObject(k).getString("ContactName").toString();
                                    Picasso.with(getApplicationContext()).load(Architect_a.getJSONObject(k).getString("ProfilePicture").toString()).into(img_person);
                                    layout_person.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            Intent i=new Intent(getApplicationContext(),Activity_mess_deatil.class);
                                            i.putExtra("RoleID",RoleID);
                                            i.putExtra("UserID",UserID);
                                            i.putExtra("Name",name);
                                            startActivity(i);

                                        }
                                    });

                                    container_Architect.addView(addView);
                                }

                                String  SubUser= ConstructionCost_jsonObject.getString("SubUser");
                                JSONArray SubUser_a = new JSONArray(SubUser);

                                for(int k=0;k<SubUser_a.length();k++)
                                {
                                    LayoutInflater layoutInflater =
                                            (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    final View addView = layoutInflater.inflate(R.layout.adapter_mess, null);
                                    final TextView txt_name = (TextView)addView.findViewById(R.id.txt_name);
                                    ImageView img_person=(ImageView)addView.findViewById(R.id.img_person);
                                    TextView txt_lastmess= (TextView)addView.findViewById(R.id.txt_lastmess);
                                    TextView txt_datetime= (TextView)addView.findViewById(R.id.txt_datetime);
                                    LinearLayout layout_person=(LinearLayout)addView.findViewById(R.id.layout_person);
                                    txt_name.setText(SubUser_a.getJSONObject(k).getString("ContactName").toString()+"  ●");
                                    txt_lastmess.setText(SubUser_a.getJSONObject(k).getString("MessageText").toString());
                                    txt_datetime.setText(SubUser_a.getJSONObject(k).getString("MessageTime").toString());
                                    final String RoleID=SubUser_a.getJSONObject(k).getString("RoleID").toString();
                                    final String UserID=SubUser_a.getJSONObject(k).getString("UserID").toString();
                                    Picasso.with(getApplicationContext()).load(SubUser_a.getJSONObject(k).getString("ProfilePicture").toString()).into(img_person);
                                  final String name=SubUser_a.getJSONObject(k).getString("ContactName").toString();
                                    layout_person.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            Intent i=new Intent(getApplicationContext(),Activity_mess_deatil.class);
                                            i.putExtra("Name",name);
                                            i.putExtra("RoleID",RoleID);
                                            i.putExtra("UserID",UserID);
                                            startActivity(i);

                                        }
                                    });

                                    container_SubUsers.addView(addView);
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
}
