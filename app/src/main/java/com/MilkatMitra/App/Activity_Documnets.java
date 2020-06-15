package com.MilkatMitra.App;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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

import java.util.HashMap;
import java.util.Map;

public class Activity_Documnets extends AppCompatActivity
{

    ImageView back_btn;
    SessionManager sm;
    LinearLayout container;
    ProgressBar  progressBar_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        progressBar_main = (ProgressBar) findViewById(R.id.progressBar_main);
        sm = new SessionManager(Activity_Documnets.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(Activity_Documnets.this, R.color.maroon_dark));
        }
        container = (LinearLayout) findViewById(R.id.container);
        back_btn=(ImageView)findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        loginUser();


    }
    private void loginUser()
    {
        progressBar_main.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL+"selection/getRegulatoryCategoryList/",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        progressBar_main.setVisibility(View.GONE);
                        Log.e("-----------",response+"");
                        try
                        {

                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("Code").equals("200"))
                            {

                                final JSONArray a=jsonObject.getJSONArray("CategoryData");

                                for(int i=0;i<a.length();i++)
                                {
                                    LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    final View addView = layoutInflater.inflate(R.layout.list_documnet, null);
                                    TextView tx_title = (TextView) addView.findViewById(R.id.tx_title);
                                    tx_title.setText(a.getJSONObject(i).getString("Category").toString());
                                    LinearLayout layout_main = (LinearLayout) addView.findViewById(R.id.layout_main);
                                    final String CategoryID=a.getJSONObject(i).getString("CategoryID").toString();
                                    final String IsActive=a.getJSONObject(i).getString("IsActive").toString();
                                    final String Status=a.getJSONObject(i).getString("Status").toString();
                                    layout_main.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v)
                                        {
                                            Intent i1=new Intent(getApplicationContext(),Activity_SubDocumnets.class);
                                            i1.putExtra("CategoryID",CategoryID);
                                            i1.putExtra("IsActive",IsActive);
                                            i1.putExtra("Status",Status);
                                            startActivity(i1);
                                        }
                                    });
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
                        // mess(error.toString());
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
