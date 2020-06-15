package com.MilkatMitra.App;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.MilkatMitra.App.Activity.Activity_ConstructionShedule;
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
import com.tooltip.Tooltip;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Activity_Partner extends Activity
{
    String ProjectID="";
    LinearLayout container;
    SessionManager sm;
    ImageView back_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(Activity_Partner.this, R.color.maroon));
        }
        sm = new SessionManager(Activity_Partner.this);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent intent = getIntent();
        ProjectID = intent.getStringExtra("ProjectID");
        container = (LinearLayout)findViewById(R.id.container);
        back_btn=(ImageView)findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        profile();




    }
    private void profile()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Constants.URL+"selection/getUserProfile/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("Code").equals("200"))
                            {
                                JSONObject object = jsonObject.getJSONObject("ProfileData");
                                JSONArray array = new JSONArray();
                                array = object.getJSONArray("Partners");
                                for(int h=0; h<array.length(); h++)
                                {
                                    LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    final View addView = layoutInflater.inflate(R.layout.list_partnerdeatils, null);
                                    TextView tv_text1 = (TextView)addView.findViewById(R.id.tv_text1);
                                    TextView tv_text2 = (TextView)addView.findViewById(R.id.tv_text2);
                                    TextView tv_text3 = (TextView)addView.findViewById(R.id.tv_text3);
                                    TextView tv_text4 = (TextView)addView.findViewById(R.id.tv_text4);
                                    TextView tv_text5 = (TextView)addView.findViewById(R.id.tv_text5);
                                    TextView tv_text6 = (TextView)addView.findViewById(R.id.tv_text6);
                                    ImageView profile_img=(ImageView)addView.findViewById(R.id.profile_img);

                                    tv_text1.setText("First Name     :"+array.getJSONObject(h).getString("Name"));
                                    tv_text2.setText("Middle Name    :"+array.getJSONObject(h).getString("MiddleName"));
                                    tv_text3.setText("Last Name      :"+array.getJSONObject(h).getString("LastName"));
                                    tv_text4.setText("Mobile Number  :"+array.getJSONObject(h).getString("MobileNumber"));
                                    tv_text5.setText("Email ID       :"+array.getJSONObject(h).getString("EmailID"));
                                    tv_text6.setText("PAN No.        :"+array.getJSONObject(h).getString("EmailID"));

                                    String ProfilePicture=array.getJSONObject(h).getString("ProfilePicture");
                                    Picasso.with(getApplicationContext()).load(ProfilePicture).into(profile_img);

                                    container.addView(addView);

                                }



                            }
                            else
                            {

                            }
                        }
                        catch (Exception e)
                        {

                            Log.e("dddd",e.toString());
                        }

                        // Toast.makeText(Activity_Login.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        // progressBar_main.setVisibility(View.GONE);
                        mess(error.toString());
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
    public void mess(String mess)
    {
        Snackbar snackbar = Snackbar.make(container, mess, Snackbar.LENGTH_LONG).setAction("Action", null);
          View sbView = snackbar.getView();
         sbView.setBackgroundColor(Color.parseColor("#FA8072"));
        snackbar.show();
    }
}
