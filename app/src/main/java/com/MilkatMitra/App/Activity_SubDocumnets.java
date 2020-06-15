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

public class Activity_SubDocumnets extends AppCompatActivity
{

    ImageView back_btn;
    SessionManager sm;
    LinearLayout container;
    ProgressBar  progressBar_main;
    String CategoryID,IsActive,Status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent intent = getIntent();
        CategoryID = intent.getStringExtra("CategoryID");
        IsActive = intent.getStringExtra("IsActive");
        Status = intent.getStringExtra("Status");

        progressBar_main = (ProgressBar) findViewById(R.id.progressBar_main);
        sm = new SessionManager(Activity_SubDocumnets.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(Activity_SubDocumnets.this, R.color.maroon_dark));
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
      String  url= Constants.URL+"selection/getRegulatoryCategoryList/{\"DocumentFlag\":\""+IsActive+"\",\"CategoryID\":\""+CategoryID+"\",\"Status\":\""+"S"+"\"}";

        progressBar_main.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
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

                                JSONArray a=jsonObject.getJSONArray("CategoryData");

                                for(int i=0;i<a.length();i++)
                                {
                                    LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    final View addView = layoutInflater.inflate(R.layout.list_documnet, null);
                                    TextView tx_title = (TextView) addView.findViewById(R.id.tx_title);

                                    LinearLayout layout_main = (LinearLayout) addView.findViewById(R.id.layout_main);
                                    final LinearLayout container1= (LinearLayout) addView.findViewById(R.id.container1);
                                    LinearLayout layout_name=(LinearLayout) addView.findViewById(R.id.layout_name);
                                    layout_name.setVisibility(View.VISIBLE);
                                    ////////////////
                                    String s=a.getJSONObject(i) .getString("Documents").toString();
                                    JSONArray subArray =new JSONArray(s);
                                    for(int j=0;j<subArray.length();j++)
                                    {
                                        LayoutInflater layoutInflater1 =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        final View addView1 = layoutInflater1.inflate(R.layout.list_subdocument, null);
                                        TextView tx_title1 = (TextView) addView1.findViewById(R.id.tv_title);
                                        TextView txt_srno = (TextView) addView1.findViewById(R.id.txt_srno);
                                        ImageView btn_pdf2=(ImageView)addView1.findViewById(R.id.btn_pdf2);
                                        tx_title.setText(subArray.getJSONObject(j).getString("Title").toString());
                                        ImageView btn_pdf=(ImageView)addView1.findViewById(R.id.btn_pdf);
                                       final String File_EN_URL=subArray.getJSONObject(j) .getString("File_EN_URL").toString();
                                        if(File_EN_URL.equalsIgnoreCase(""))
                                        {
                                            btn_pdf.setVisibility(View.GONE);
                                        }
                                        else
                                        {
                                            btn_pdf.setVisibility(View.VISIBLE);
                                        }

                                        final String File_GJ_URL=subArray.getJSONObject(j) .getString("File_GJ_URL").toString();
                                        if(File_GJ_URL.equalsIgnoreCase(""))
                                        {
                                            btn_pdf2.setVisibility(View.GONE);
                                        }
                                        else
                                        {
                                            btn_pdf2.setVisibility(View.VISIBLE);
                                        }
                                      // final String File_GJ_URL=subArray.getJSONObject(j) .getString("File_GJ_URL").toString();
                                        btn_pdf2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent i=new Intent(getApplicationContext(),webview_pdf.class);
                                                i.putExtra("url",File_GJ_URL);
                                                startActivity(i);
                                            }
                                        });

                                        btn_pdf.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent i=new Intent(getApplicationContext(),webview_pdf.class);
                                                i.putExtra("url",File_EN_URL);
                                                startActivity(i);
                                            }
                                        });


                                        txt_srno.setText((j+1)+"");
                                        tx_title1.setText(subArray.getJSONObject(j) .getString("Title").toString());
                                        container1.setVisibility(View.GONE);
                                        container1.addView(addView1);

                                    }
                                    ////////////////

                                    layout_main.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v)
                                        {
                                            if (container1.getVisibility() == View.VISIBLE) {
                                                container1.setVisibility(View.GONE);
                                            } else {
                                                container1.setVisibility(View.VISIBLE);
                                            }

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
