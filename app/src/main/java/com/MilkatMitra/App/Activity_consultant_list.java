package com.MilkatMitra.App;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.MilkatMitra.App.Helper.Constants;
import com.MilkatMitra.App.Helper.SessionManager;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Activity_consultant_list extends AppCompatActivity
{

    ImageView back_btn;
    SessionManager sm;
    ProgressBar progressdisplay_layout;
    String ProjectID,page;
    LinearLayout container;
    String QPRID="",RoleID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultant_list);

        back_btn=(ImageView)findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressdisplay_layout = (ProgressBar) findViewById(R.id.progressBar2);
        progressdisplay_layout.setVisibility(View.GONE);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        sm = new SessionManager(Activity_consultant_list.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(Activity_consultant_list.this, R.color.maroon_dark));
        }
        container = (LinearLayout)findViewById(R.id.container);

        Intent intent = getIntent();
        ProjectID = intent.getStringExtra("ProjectID");
        page = intent.getStringExtra("page");
        RoleID = intent.getStringExtra("RoleID");
        QPRID = intent.getStringExtra("QPRID");


        loginUser();
    }
    private void loginUser() {
        progressdisplay_layout.setVisibility(View.VISIBLE);
       String url= Constants.URL+"selection/getProjectConsultants/{\"ProjectID\":\"" + ProjectID + "\",\"QPRID\":\"" + QPRID + "\",\"Type\":\""+"QPR"+"\",\"RoleID\":\""+RoleID+"\"}";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        progressdisplay_layout.setVisibility(View.GONE);
                        // Toast.makeText(Activity_Authorized.this,response,Toast.LENGTH_LONG).show();
                        Log.e("-----------", response + "");
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("Code").equals("200"))
                            {
                                JSONArray array1 = new JSONArray();
                                array1 = jsonObject.getJSONArray("Available");

                                for(int h=0; h<array1.length(); h++)
                                {
                                    LayoutInflater layoutInflater =
                                            (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    final View addView = layoutInflater.inflate(R.layout.list_consultanttype, null);
                                    TextView tv1 = (TextView)addView.findViewById(R.id.tv1);
                                    TextView tv2 = (TextView)addView.findViewById(R.id.tv2);
                                    LinearLayout card_view=(LinearLayout)addView.findViewById(R.id.card_view);
                                    tv1.setText(array1.getJSONObject(h).getString("ContactName").toString());
                                    tv2.setText(array1.getJSONObject(h).getString("MobileNumber").toString());

                                    final String UserID=array1.getJSONObject(h).getString("UserID").toString();
                                    card_view.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {


                                            add(UserID);
                                           // Toast.makeText(getApplicationContext(),UserID,Toast.LENGTH_LONG).show();
                                        }
                                    });

                                    container.addView(addView);

                                }


                            }
                        } catch (Exception e) {

                            e.printStackTrace();
                        }
                        // parseData(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // progressBar_main.setVisibility(View.GONE);
                        // mess(error.toString());
                    }
                }) {

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
    private void add(final String userid)
    {

        progressdisplay_layout.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL+"update/LinkConsultant/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        progressdisplay_layout.setVisibility(View.GONE);

                        //Toast.makeText(Activity_Login.this,response,Toast.LENGTH_LONG).show();
                        parseData(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        progressdisplay_layout.setVisibility(View.GONE);
                        mess(error.toString());
                    }
                }){
            @Override
            protected Map<String,String> getParams()
            {
                Map<String,String> params = new HashMap<String, String>();

                params.put("ProjectID",ProjectID);
                params.put("QPRID",QPRID);
                params.put("RoleID",RoleID);
                params.put("Type","QPR");
                params.put("UserID",userid);


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
    public void parseData(String response) {

        try
        {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("Code").equals("200"))
            {
                // JSONObject object = jsonObject.getJSONObject("Profile");

                //Toast.makeText(getApplicationContext(),jsonObject.getString("Message")+"",Toast.LENGTH_LONG).show();

                finish();
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
    public void mess(String mess) {
        Snackbar snackbar = Snackbar.make(back_btn, mess, Snackbar.LENGTH_LONG).setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.parseColor("#FA8072"));
        snackbar.show();
    }

}
