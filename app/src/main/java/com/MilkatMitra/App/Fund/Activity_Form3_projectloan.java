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

public class Activity_Form3_projectloan extends AppCompatActivity {

    SessionManager sm;
    String ProjectID="";
    LinearLayout container_plan;
    TextView txt_Promoter_Capital,
            txtsub_ProjectLoan,
            txtsub_Overdraft,
            txtsub_Other_Borrowings,txtsub_RERABankAccount;

    TextView  txt_actual, txt_plan;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form3_projectloan);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(Activity_Form3_projectloan.this, R.color.maroon_dark));
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
        sm = new SessionManager(Activity_Form3_projectloan.this);

        txt_Promoter_Capital=(TextView)findViewById(R.id.txt_Promoter_Capital) ;
        txtsub_ProjectLoan=(TextView)findViewById(R.id.txtsub_ProjectLoan) ;
        txtsub_Overdraft=(TextView)findViewById(R.id.txtsub_Overdraft) ;
        txtsub_Other_Borrowings=(TextView)findViewById(R.id.txtsub_Other_Borrowings) ;
        txtsub_RERABankAccount=(TextView)findViewById(R.id.txtsub_RERABankAccount) ;


        txt_actual=(TextView)findViewById(R.id.txt_actual) ;
        txt_plan=(TextView)findViewById(R.id.txt_plan) ;

        txtsub_RERABankAccount.setBackgroundResource(R.color.white);
        txt_Promoter_Capital.setBackgroundResource(R.color.white);
        txtsub_ProjectLoan.setBackgroundResource(R.color.maroon_tra);
        txtsub_Overdraft.setBackgroundResource(R.color.white);
        txtsub_Other_Borrowings.setBackgroundResource(R.color.white);
        txt_actual.setBackgroundResource(R.color.maroon_tra);
        txt_plan.setBackgroundResource(R.color.white);


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
        txtsub_RERABankAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i=new Intent(getApplicationContext(), Activity_Actual_BankLenders.class);
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
                Intent i=new Intent(getApplicationContext(), Activity_Actual_PromoterCapital.class);
                i.putExtra("ProjectID",ProjectID);
                i.putExtra("page","4");
                startActivity(i);
            }
        });
        txtsub_ProjectLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent i=new Intent(getApplicationContext(), Activity_Actual_ProjectLoan.class);
                i.putExtra("ProjectID",ProjectID);
                i.putExtra("page","4");
                startActivity(i);
            }
        });
        txtsub_Overdraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                Intent i=new Intent(getApplicationContext(), Activity_Actual_CreditOverdraft.class);
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
                Intent i=new Intent(getApplicationContext(), Activity_Actual_OtherBorrowings.class);
                i.putExtra("ProjectID",ProjectID);
                i.putExtra("page","4");
                startActivity(i);
            }
        });




        loginUser();
    }
    private void loginUser()
    {
        String u=Constants.URL+"selection/getProjectList/{\"mode\":\"list\",\"ProjectID\":\""+ProjectID+"\",\"Screen\":\"ProjectLenders\"," +"\"RecordType\":\""+"A"+"\"}";
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
                            if(jsonObject.getString("Code").equals("200"))
                            {
                                JSONArray array = new JSONArray();
                                array = jsonObject.getJSONArray("ProjectDetail");
                                int i=0;
                                String  ConstructionCost= array.getJSONObject(i).getString("LendersList");

                                JSONObject ConstructionCost_jsonObject = new JSONObject(ConstructionCost);

                                String  Quarters= ConstructionCost_jsonObject.getString("Data");
                                JSONArray arrayQuarters = new JSONArray(Quarters);
                                for(int k=0;k<arrayQuarters.length();k++)
                                {

                                    LayoutInflater layoutInflater =
                                            (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    final View addView = layoutInflater.inflate(R.layout.fund_sub, null);

                                    TextView txt_name = (TextView)addView.findViewById(R.id.txt_name);
                                    final LinearLayout  layout_list=(LinearLayout)addView.findViewById(R.id.layout_list);
                                    layout_list.setVisibility(View.GONE);
                                    txt_name.setText(arrayQuarters.getJSONObject(k).getString("LenderName").toString());
                                    txt_name.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            if (layout_list.getVisibility() == View.VISIBLE) {
                                                // Its visible
                                                layout_list.setVisibility(View.GONE);
                                            } else {
                                                // Either gone or invisible
                                                layout_list.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    });

                                    final LinearLayout l_FundInformation= (LinearLayout) addView.findViewById(R.id.l_FundInformation);
                                    final LinearLayout l_Receipt= (LinearLayout) addView.findViewById(R.id.l_Receipt);
                                    final LinearLayout l_Repayment= (LinearLayout) addView.findViewById(R.id.l_Repayment);

                                    l_FundInformation.setVisibility(View.VISIBLE);
                                    l_Receipt.setVisibility(View.GONE);
                                    l_Repayment.setVisibility(View.GONE);

                                    final TextView txt_FundInformation= (TextView)addView.findViewById(R.id.txt_FundInformation);
                                    final TextView txt_Receipt= (TextView)addView.findViewById(R.id.txt_Receipt);
                                    final TextView txt_Repayment= (TextView)addView.findViewById(R.id.txt_Repayment);


                                    /////////////////////////////

                                    final CheckBox checkBox_value_Receipt= (CheckBox) addView.findViewById(R.id.checkBox_value_Receipt);
                                    checkBox_value_Receipt.setVisibility(View.GONE);
                                    final LinearLayout container_Receipt=(LinearLayout)addView.findViewById(R.id.container_Receipt);
                                    final LinearLayout  layout_total_Receipt=(LinearLayout)addView.findViewById(R.id.layout_total_Receipt);
                                    TextView txt_total_Receipt=(TextView)addView.findViewById(R.id.txt_total_Receipt);
                                    //layout_total_Receipt.setVisibility(View.GONE);

                                    String Receipts=arrayQuarters.getJSONObject(k).getString("Receipts");
                                    JSONObject QuarterList = new JSONObject(Receipts);
                                    String Status=QuarterList.getString("Status");
                                    txt_total_Receipt.setText("₹"+QuarterList.getString("QuarterTotal"));


                                 /*   checkBox_value_Receipt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
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
                                    }*/
                                    checkBox_value_Receipt.setChecked(true);
                                    layout_total_Receipt.setVisibility(View.VISIBLE);
                                    container_Receipt.setVisibility(View.VISIBLE);

                                    String  QuarterList_s= QuarterList.getString("QuarterList");
                                    JSONArray QuarterList_array = new JSONArray(QuarterList_s);
                                    try
                                    {
                                        for(int kk=0;kk<QuarterList_array.length();kk++)
                                        {
                                            LayoutInflater layoutInflater1 = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                            final View addView1 = layoutInflater1.inflate(R.layout.adapter_three_part, null);
                                            TextView textOut = (TextView)addView1.findViewById(R.id.title);
                                            TextView textOut1 = (TextView)addView1.findViewById(R.id.title1);

                                            textOut.setText(QuarterList_array.getJSONObject(kk).getString("QuarterNumber").toString());
                                            textOut1.setText(QuarterList_array.getJSONObject(kk).getString("Principal").toString());
                                            container_Receipt.addView(addView1);
                                        }


                                    }catch (Exception e)
                                    {

                                    }
////////////////////////////////////////////////////////////////////////////////////
                                    String Repayments=arrayQuarters.getJSONObject(k).getString("Repayments");
                                    JSONObject QuarterList_Repayments = new JSONObject(Repayments);
                                    String  QuarterList_s_Repayments= QuarterList_Repayments.getString("QuarterList");
                                    JSONArray QuarterList_array_Repayments = new JSONArray(QuarterList_s_Repayments);

                                    final CheckBox checkBox_value_Repayment= (CheckBox) addView.findViewById(R.id.checkBox_value_Repayment);
                                    checkBox_value_Repayment.setVisibility(View.GONE);
                                    final LinearLayout container_Repayment=(LinearLayout)addView.findViewById(R.id.container_Repayment);
                                    final LinearLayout  layout_total_Repayment=(LinearLayout)addView.findViewById(R.id.layout_total_Repayment);

                                    //  layout_total_Receipt.setVisibility(View.GONE);

                                    TextView total_Principal=(TextView)addView.findViewById(R.id.total_Principal);
                                    TextView  total_Interest=(TextView)addView.findViewById(R.id.total_Interest);
                                    TextView txt_Repayment_Principal=(TextView)addView.findViewById(R.id.txt_Repayment_Principal);
                                    TextView txt_Repayment_Interest=(TextView)addView.findViewById(R.id.txt_Repayment_Interest);

                                    total_Principal.setText("₹"+QuarterList_Repayments.getString("QuarterTotal"));
                                    total_Interest.setText("₹"+QuarterList_Repayments.getString("QuarterInterest"));
                                    txt_Repayment_Principal.setText(QuarterList_Repayments.getString("TotalPrincipal"));
                                    txt_Repayment_Interest.setText(QuarterList_Repayments.getString("TotalInterest"));




                                    String Status_re=QuarterList_Repayments.getString("Status");

                                    container_Repayment.setVisibility(View.VISIBLE);
                                    layout_total_Repayment.setVisibility(View.VISIBLE);
                                    checkBox_value_Repayment.setChecked(true);
                                  /*  if(Status_re.equalsIgnoreCase("Y"))
                                    {
                                        container_Repayment.setVisibility(View.VISIBLE);
                                        layout_total_Repayment.setVisibility(View.VISIBLE);
                                        checkBox_value_Repayment.setChecked(true);
                                    }
                                    else
                                    {
                                        container_Repayment.setVisibility(View.GONE);
                                        layout_total_Repayment.setVisibility(View.GONE);
                                        checkBox_value_Repayment.setChecked(false);
                                    }

                                    checkBox_value_Repayment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                                    {
                                        @Override
                                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                                        {
                                            if ( isChecked )
                                            {
                                                container_Repayment.setVisibility(View.VISIBLE);
                                                layout_total_Repayment.setVisibility(View.VISIBLE);
                                                checkBox_value_Repayment.setChecked(true);
                                            }
                                            else
                                            {
                                                container_Repayment.setVisibility(View.GONE);
                                                layout_total_Repayment.setVisibility(View.GONE);
                                                checkBox_value_Repayment.setChecked(false);

                                            }

                                        }
                                    });*/

                                    try
                                    {
                                        for(int kk=0;kk<QuarterList_array_Repayments.length();kk++)
                                        {
                                            LayoutInflater layoutInflater1 = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                            final View addView1 = layoutInflater1.inflate(R.layout.adapter_two_part, null);
                                            TextView textOut = (TextView)addView1.findViewById(R.id.title);
                                            TextView textOut1 = (TextView)addView1.findViewById(R.id.title1);
                                            TextView textOut2 = (TextView)addView1.findViewById(R.id.title2);

                                            textOut.setText(QuarterList_array_Repayments.getJSONObject(kk).getString("QuarterNumber").toString());
                                            textOut1.setText(QuarterList_array_Repayments.getJSONObject(kk).getString("Principal").toString());
                                            textOut2.setText(QuarterList_array_Repayments.getJSONObject(kk).getString("Interest").toString());
                                            container_Repayment.addView(addView1);
                                        }


                                    }catch (Exception e)
                                    {

                                    }



                                    ///////////////////////
                                    TextView tv_LenderName = (TextView)addView.findViewById(R.id.tv_LenderName);
                                    TextView tv_Loan_Amount = (TextView)addView.findViewById(R.id.tv_Loan_Amount);
                                    TextView tv_Loan_Date = (TextView)addView.findViewById(R.id.tv_Loan_Date);
                                    TextView tv_Units_Mortgaged = (TextView)addView.findViewById(R.id.tv_Units_Mortgaged);
                                    ImageView image_LoanAgreement= (ImageView)addView.findViewById(R.id.image_LoanAgreement);
                                    ImageView  image_MortgageDeed= (ImageView)addView.findViewById(R.id.image_MortgageDeed);
                                    ImageView image_LoanStatement= (ImageView)addView.findViewById(R.id.image_LoanStatement);

                                    tv_LenderName.setText(arrayQuarters.getJSONObject(k).getString("LenderName").toString());
                                    tv_Loan_Amount.setText(arrayQuarters.getJSONObject(k).getString("RequestAmount").toString());
                                    tv_Loan_Date.setText(arrayQuarters.getJSONObject(k).getString("TransactionDate").toString());
                                    tv_Units_Mortgaged.setText(arrayQuarters.getJSONObject(k).getString("UnitsMortgaged").toString());
                                    /////////////////////////
                                    Picasso.with(getApplicationContext()).load(arrayQuarters.getJSONObject(k).getString("FileAgrementURL").toString()).placeholder(getResources().getDrawable(R.drawable.noi)).error(getResources().getDrawable(R.drawable.noi)).into(image_LoanAgreement);
                                    Picasso.with(getApplicationContext()).load(arrayQuarters.getJSONObject(k).getString("FileMortgageURL").toString()).placeholder(getResources().getDrawable(R.drawable.noi)).error(getResources().getDrawable(R.drawable.noi)).into(image_MortgageDeed);
                                    Picasso.with(getApplicationContext()).load(arrayQuarters.getJSONObject(k).getString("FileOtherURL").toString()).placeholder(getResources().getDrawable(R.drawable.noi)).error(getResources().getDrawable(R.drawable.noi)).into(image_LoanStatement);

                                    if(arrayQuarters.getJSONObject(k).getString("FileAgrementURL").toString().contains(".pdf"))
                                        image_LoanAgreement.setBackgroundResource(R.drawable.pdf_link);

                                    if(arrayQuarters.getJSONObject(k).getString("FileMortgageURL").toString().contains(".pdf"))
                                        image_MortgageDeed.setBackgroundResource(R.drawable.pdf_link);

                                    if(arrayQuarters.getJSONObject(k).getString("FileOtherURL").toString().contains(".pdf"))
                                        image_LoanStatement.setBackgroundResource(R.drawable.pdf_link);


                                    txt_FundInformation.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            l_FundInformation.setVisibility(View.VISIBLE);
                                            l_Receipt.setVisibility(View.GONE);
                                            l_Repayment.setVisibility(View.GONE);


                                            txt_FundInformation.setBackgroundResource(R.color.viewcolor);
                                            txt_Receipt.setBackgroundResource(R.color.white);
                                            txt_Repayment.setBackgroundResource(R.color.white);
                                        }
                                    });

                                    txt_Receipt.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            l_FundInformation.setVisibility(View.GONE);
                                            l_Receipt.setVisibility(View.VISIBLE);
                                            l_Repayment.setVisibility(View.GONE);

                                            txt_FundInformation.setBackgroundResource(R.color.white);
                                            txt_Receipt.setBackgroundResource(R.color.viewcolor);
                                            txt_Repayment.setBackgroundResource(R.color.white);
                                        }
                                    });

                                    txt_Repayment.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            l_FundInformation.setVisibility(View.GONE);
                                            l_Receipt.setVisibility(View.GONE);
                                            l_Repayment.setVisibility(View.VISIBLE);

                                            txt_FundInformation.setBackgroundResource(R.color.white);
                                            txt_Receipt.setBackgroundResource(R.color.white);
                                            txt_Repayment.setBackgroundResource(R.color.viewcolor);
                                        }
                                    });
                                    //////////////////////////////////////////////////////////////



                                    container_plan.addView(addView);

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

