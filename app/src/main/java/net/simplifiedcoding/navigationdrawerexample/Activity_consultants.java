package net.simplifiedcoding.navigationdrawerexample;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.simplifiedcoding.navigationdrawerexample.Helper.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class Activity_consultants extends AppCompatActivity
{
    String ProjectID;
    ImageView back_btn;
    SessionManager sm;
    LinearLayout

            layout_ProjectArchitect,
    layout_StructuralEngineers,
            layout_StructuralConsultant,
    layout_ProjectCA;
    ProgressBar progressdisplay_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultants);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(Activity_consultants.this, R.color.maroon_dark));
        }
        Intent intent = getIntent();
        ProjectID = intent.getStringExtra("ProjectID");
        sm = new SessionManager(Activity_consultants.this);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressdisplay_layout=(ProgressBar)findViewById(R.id.progressBar2);

        layout_ProjectArchitect=(LinearLayout)findViewById(R.id.layout_ProjectArchitect) ;
        layout_StructuralEngineers=(LinearLayout)findViewById(R.id.layout_StructuralEngineers) ;
        layout_StructuralConsultant=(LinearLayout)findViewById(R.id.layout_StructuralConsultant) ;
        layout_ProjectCA=(LinearLayout)findViewById(R.id.layout_ProjectCA) ;

        loginUser();
    }
    private void loginUser()
    {
        progressdisplay_layout.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://sighteat.com/imitra/api/selection/getProjectList/{\"mode\":\"list\",\"ProjectID\":\""+ProjectID+"\",\"Screen\":\"QPRConsultants\"}",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        progressdisplay_layout.setVisibility(View.GONE);
                        // Toast.makeText(Activity_Authorized.this,response,Toast.LENGTH_LONG).show();
                        Log.e("-----------",response+"");
                        try
                        {

                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("Code").equals("200"))
                            {
                                JSONArray array = new JSONArray();
                                array = jsonObject.getJSONArray("ProjectDetail");

                                int i=0;
                                // JSONObject Form1 = QPR.getJSONObject("Form1");
                                String Consultants=array.getJSONObject(i).getString("Quarter");
                                JSONObject Consultantsj = new JSONObject(Consultants);

                                JSONArray Engineer = Consultantsj.getJSONArray("Engineer");
                                JSONArray Architect = Consultantsj.getJSONArray("Architect");
                                JSONArray CA = Consultantsj.getJSONArray("CA");
                                JSONArray Contractor = Consultantsj.getJSONArray("Structural");

                                try
                                {
                                    for(int h=0; h<Architect.length(); h++)
                                    {

                                        LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        final View addView = layoutInflater.inflate(R.layout.list_a_authorized, null);
                                        TextView tv_a_fname = (TextView)addView.findViewById(R.id.tv_a_fname);
                                        TextView tv_a_mno = (TextView)addView.findViewById(R.id.tv_a_mno);
                                        TextView tv_a_email = (TextView)addView.findViewById(R.id.tv_a_email);
                                        TextView tv_4 = (TextView)addView.findViewById(R.id.tv_4);
                                        TextView tv_5 = (TextView)addView.findViewById(R.id.tv_5);
                                        tv_4.setVisibility(View.VISIBLE);
                                        tv_5.setVisibility(View.VISIBLE);
                                        tv_a_fname.setText(Html.fromHtml("Contact Name : "+Architect.getJSONObject(h).getString("ContactName").toString()));
                                        tv_a_mno.setText(Html.fromHtml("Mobile : "+Architect.getJSONObject(h).getString("MobileNumber").toString()));
                                        tv_a_email.setText(Html.fromHtml("Email ID : "+Architect.getJSONObject(h).getString("EmailID").toString()));
                                        tv_4.setText(Html.fromHtml("CoA Reg. Number : "+Architect.getJSONObject(h).getString("LicenceNumber").toString()));
                                        tv_5.setText(Html.fromHtml("Licence Valid Upto : "+Architect.getJSONObject(h).getString("LicenceValidityDate").toString()));

                                        layout_ProjectArchitect.addView(addView);
                                    }

                                }
                                catch (Exception e)
                                {

                                }

                                try
                                {
                                    for(int h=0; h<Engineer.length(); h++)
                                    {

                                        LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        final View addView = layoutInflater.inflate(R.layout.list_a_authorized, null);
                                        TextView tv_a_fname = (TextView)addView.findViewById(R.id.tv_a_fname);
                                        TextView tv_a_mno = (TextView)addView.findViewById(R.id.tv_a_mno);
                                        TextView tv_a_email = (TextView)addView.findViewById(R.id.tv_a_email);
                                        TextView tv_4 = (TextView)addView.findViewById(R.id.tv_4);
                                        TextView tv_5 = (TextView)addView.findViewById(R.id.tv_5);
                                        tv_4.setVisibility(View.VISIBLE);
                                        tv_5.setVisibility(View.VISIBLE);
                                        tv_a_fname.setText(Html.fromHtml("Contact Name : "+Engineer.getJSONObject(h).getString("ContactName").toString()));
                                        tv_a_mno.setText(Html.fromHtml("Mobile : "+Engineer.getJSONObject(h).getString("MobileNumber").toString()));
                                        tv_a_email.setText(Html.fromHtml("Email ID : "+Engineer.getJSONObject(h).getString("EmailID").toString()));
                                        tv_4.setText(Html.fromHtml("Licence Number : "+Engineer.getJSONObject(h).getString("LicenceNumber").toString()));
                                        tv_5.setText(Html.fromHtml("Licence Valid Upto : "+Engineer.getJSONObject(h).getString("LicenceValidityDate").toString()));

                                        layout_StructuralEngineers.addView(addView);
                                    }

                                }
                                catch (Exception e)
                                {

                                }

                                try
                                {
                                    for(int h=0; h<Contractor.length(); h++)
                                    {

                                        LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        final View addView = layoutInflater.inflate(R.layout.list_a_authorized, null);
                                        TextView tv_a_fname = (TextView)addView.findViewById(R.id.tv_a_fname);
                                        TextView tv_a_mno = (TextView)addView.findViewById(R.id.tv_a_mno);
                                        TextView tv_a_email = (TextView)addView.findViewById(R.id.tv_a_email);




                                        tv_a_fname.setText(Html.fromHtml("Contact Name : "+Contractor.getJSONObject(i).getString("ContactName").toString()));
                                        tv_a_mno.setText(Html.fromHtml("Mobile : "+Contractor.getJSONObject(i).getString("MobileNumber").toString()));
                                        tv_a_email.setText(Html.fromHtml("Email ID : "+Contractor.getJSONObject(i).getString("EmailID").toString()));

                                        layout_StructuralConsultant.addView(addView);
                                    }

                                }
                                catch (Exception e)
                                {

                                }


                                try
                                {
                                    for(int h=0; h<CA.length(); h++)
                                    {

                                        LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        final View addView = layoutInflater.inflate(R.layout.list_a_authorized, null);
                                        TextView tv_a_fname = (TextView)addView.findViewById(R.id.tv_a_fname);
                                        TextView tv_a_mno = (TextView)addView.findViewById(R.id.tv_a_mno);
                                        TextView tv_a_email = (TextView)addView.findViewById(R.id.tv_a_email);

                                        TextView tv_4 = (TextView)addView.findViewById(R.id.tv_4);
                                        TextView tv_5 = (TextView)addView.findViewById(R.id.tv_5);
                                        TextView tv_6 = (TextView)addView.findViewById(R.id.tv_6);
                                        TextView tv_7 = (TextView)addView.findViewById(R.id.tv_7);
                                        tv_4.setVisibility(View.VISIBLE);
                                        tv_5.setVisibility(View.VISIBLE);


                                        tv_a_fname.setText(Html.fromHtml("Contact Name : "+CA.getJSONObject(i).getString("ContactName").toString()));
                                        tv_a_mno.setText(Html.fromHtml("Mobile : "+CA.getJSONObject(i).getString("MobileNumber").toString()));
                                        tv_a_email.setText(Html.fromHtml("Email ID : "+CA.getJSONObject(i).getString("EmailID").toString()));

                                        tv_4.setText(Html.fromHtml("Type : "+CA.getJSONObject(i).getString("ConsultantType").toString()));
                                        tv_5.setText(Html.fromHtml("Membership Number: "+CA.getJSONObject(i).getString("LicenceNumber").toString()));


                                        if(CA.getJSONObject(i).getString("ConsultantType").toString().equalsIgnoreCase("Partnership"))
                                        {
                                            tv_6.setVisibility(View.VISIBLE);
                                            tv_7.setVisibility(View.VISIBLE);
                                            tv_6.setText(Html.fromHtml("Firm Name : "+CA.getJSONObject(i).getString("FirmName").toString()));
                                            tv_7.setText(Html.fromHtml("Firm Reg. Number : "+CA.getJSONObject(i).getString("FirmRegNo").toString()));

                                        }

                                        layout_ProjectCA.addView(addView);
                                    }

                                }
                                catch (Exception e)
                                {

                                }


                                //  JSONArray subArray = subObject.getJSONArray("Evaluation");



                                // JSONObject B = Form1.getJSONObject("B");
                                //JSONArray Pointsb = B.getJSONArray("Points");
                                //JSONArray Blocksb = B.getJSONArray("Blocks");

                                //  else
                                //{
                                //  UF.msg("No Data Found.");
                                //   rel_filter.performClick();
                                //   rel_report_table.setVisibility(View.GONE);
                                //   arr_data.clear();
                                // }

                                ////////////////

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
                headers.put("Host", "sighteat.com");
                headers.put("Language", "" + "en");
                headers.put("Ipaddress", "" + "185.255.554.20");
                headers.put("Authorization", "" + "NWNiNTZiZGZmMWZiZTU1MGE1ODUxY2RlODE1MzIxNzRiZjIyMTVkMmI2Mzli");
                headers.put("Devicetype", "" + "Web");
                headers.put("Timestamp", "" + "15672401123");
                headers.put("Deviceid", "" + "Android");
                headers.put("Clientcode", "" + "5cb56bfb61a6f");
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
