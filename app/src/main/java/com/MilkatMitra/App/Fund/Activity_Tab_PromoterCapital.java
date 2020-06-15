package com.MilkatMitra.App.Fund;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity_Tab_PromoterCapital extends AppCompatActivity {

    SessionManager sm;
    String ProjectID="";
    LinearLayout container_plan;
    TextView txt_Promoter_Capital,
            txtsub_ProjectLoan,
            txtsub_Overdraft,
            txtsub_Other_Borrowings;

    TextView  txt_actual, txt_plan;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_promotercapital);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(Activity_Tab_PromoterCapital.this, R.color.maroon_dark));
        }

        ImageView back_btn;
        back_btn=(ImageView)findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        container_plan = (LinearLayout)findViewById(R.id.container_plan);
        Intent intent = getIntent();
        ProjectID = intent.getStringExtra("ProjectID");


        txt_Promoter_Capital=(TextView)findViewById(R.id.txt_Promoter_Capital) ;
        txtsub_ProjectLoan=(TextView)findViewById(R.id.txtsub_ProjectLoan) ;
        txtsub_Overdraft=(TextView)findViewById(R.id.txtsub_Overdraft) ;
        txtsub_Other_Borrowings=(TextView)findViewById(R.id.txtsub_Other_Borrowings) ;
        txt_actual=(TextView)findViewById(R.id.txt_actual) ;
        txt_plan=(TextView)findViewById(R.id.txt_plan) ;

        txt_Promoter_Capital.setBackgroundResource(R.color.maroon_tra);
        txtsub_ProjectLoan.setBackgroundResource(R.color.white);
        txtsub_Overdraft.setBackgroundResource(R.color.white);
        txtsub_Other_Borrowings.setBackgroundResource(R.color.white);
        txt_actual.setBackgroundResource(R.color.white);
        txt_plan.setBackgroundResource(R.color.maroon_tra);

        txt_actual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                Intent i=new Intent(getApplicationContext(), Activity_Actual_PromoterCapital.class);
                i.putExtra("ProjectID",ProjectID);
                i.putExtra("page","4");
                startActivity(i);
            }
        });

        txt_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
                Intent i=new Intent(getApplicationContext(), Activity_Tab_PromoterCapital.class);
                i.putExtra("ProjectID",ProjectID);
                i.putExtra("page","4");
                startActivity(i);
            }
        });


        txt_Promoter_Capital.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
                Intent i=new Intent(getApplicationContext(), Activity_Tab_PromoterCapital.class);
                i.putExtra("ProjectID",ProjectID);
                i.putExtra("page","4");
                startActivity(i);
            }
        });
        txtsub_ProjectLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i=new Intent(getApplicationContext(), Activity_Tab_ProjectLoan.class);
                i.putExtra("ProjectID",ProjectID);
                i.putExtra("page","4");
                startActivity(i);
            }
        });
        txtsub_Overdraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                Intent i=new Intent(getApplicationContext(), Activity_Tab_CreditOverdraft.class);
                i.putExtra("ProjectID",ProjectID);
                i.putExtra("page","4");
                startActivity(i);
            }
        });
        txtsub_Other_Borrowings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
                Intent i=new Intent(getApplicationContext(), Activity_Tab_OtherBorrowings.class);
                i.putExtra("ProjectID",ProjectID);
                i.putExtra("page","4");
                startActivity(i);
            }
        });



        sm = new SessionManager(Activity_Tab_PromoterCapital.this);
        loginUser();
    }
    private void loginUser()
    {
        String u=Constants.URL+"selection/getProjectList/{\"mode\":\"list\",\"ProjectID\":\""+ProjectID+"\",\"Screen\":\"PromoterCapital\"," +"\"RecordType\":\""+"P"+"\"}";
       Log.e("Url",u);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,u ,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {

                        // Toast.makeText(Activity_Authorized.this,response,Toast.LENGTH_LONG).show();
                        Log.e("-----------",response+"");
                        try
                        {

                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("Code").equals("200")) {
                                JSONArray array = new JSONArray();
                                array = jsonObject.getJSONArray("ProjectDetail");
                                int i = 0;
                                String ConstructionCost = array.getJSONObject(i).getString("PromoterCapital");

                                JSONObject ConstructionCost_jsonObject = new JSONObject(ConstructionCost);

                                String TotalAmount=ConstructionCost_jsonObject.getString("TotalAmount");
                                String QuarterTotal=ConstructionCost_jsonObject.getString("QuarterTotal");

                                String Status=ConstructionCost_jsonObject.getString("Status");

                                String Quarters = ConstructionCost_jsonObject.getString("QuarterList");
                                JSONArray arrayQuarters = new JSONArray(Quarters);

                                LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                final View addView = layoutInflater.inflate(R.layout.adapter_promotercapital, null);
                                EditText txt_OverallProject = (EditText) addView.findViewById(R.id.txt_OverallProject);
                                TextView txt_total_Receipt = (TextView)addView.findViewById(R.id.txt_total_Receipt);
                                txt_total_Receipt.setText("₹"+TotalAmount);
                                txt_OverallProject.setText(QuarterTotal);

                                final CheckBox checkBox_value_Receipt= (CheckBox) addView.findViewById(R.id.checkBox_value_Receipt);
                                final LinearLayout container_Receipt=(LinearLayout)addView.findViewById(R.id.container_Receipt);
                                final LinearLayout  layout_total_Receipt=(LinearLayout)addView.findViewById(R.id.layout_total_Receipt);

                                checkBox_value_Receipt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                                {
                                    @Override
                                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                                    {
                                        if ( isChecked )
                                        {
                                            checkBox_value_Receipt.setChecked(true);
                                            layout_total_Receipt.setVisibility(View.VISIBLE);
                                            container_Receipt.setVisibility(View.VISIBLE);


                                        }
                                        else
                                        {
                                            checkBox_value_Receipt.setChecked(false);
                                            layout_total_Receipt.setVisibility(View.GONE);
                                            container_Receipt.setVisibility(View.GONE);

                                        }

                                    }
                                });
                                if(Status.equalsIgnoreCase("Y"))
                                {
                                    checkBox_value_Receipt.setChecked(true);
                                    layout_total_Receipt.setVisibility(View.VISIBLE);
                                    container_Receipt.setVisibility(View.VISIBLE);
                                }
                                else
                                {
                                    checkBox_value_Receipt.setChecked(false);
                                    layout_total_Receipt.setVisibility(View.GONE);
                                    container_Receipt.setVisibility(View.GONE);
                                }

                                //layout_total_Receipt.setVisibility(View.GONE);

                                try
                                {
                                    for(int kk=0;kk<arrayQuarters.length();kk++)
                                    {
                                        LayoutInflater layoutInflater1 = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        final View addView1 = layoutInflater1.inflate(R.layout.adapter_three_part, null);
                                        TextView textOut = (TextView)addView1.findViewById(R.id.title);
                                        TextView textOut1 = (TextView)addView1.findViewById(R.id.title1);

                                        textOut.setText(arrayQuarters.getJSONObject(kk).getString("QuarterNumber").toString());
                                        textOut1.setText(arrayQuarters.getJSONObject(kk).getString("Amount").toString());
                                        container_Receipt.addView(addView1);
                                    }


                                }catch (Exception e) {

                                }
                                container_plan.addView(addView);


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
