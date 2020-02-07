package net.simplifiedcoding.navigationdrawerexample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tooltip.Tooltip;

import net.simplifiedcoding.navigationdrawerexample.Helper.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class list_ceri_landlost  extends AppCompatActivity {

    LinearLayout container;
    Button btnsubmit;
    int count=0;
    JSONObject jsonObj = new JSONObject();
    String data="",classs="";
    ProgressBar progressBar_main;
    SessionManager sm;
    Map<String,String> params = new HashMap<String, String>();
    String ProjectID ="";
    String BlockID = "";
    ImageView back;
    int counter=3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_certi_lans);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(list_ceri_landlost.this, R.color.maroon_dark));
        }

        back=(ImageView)findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressBar_main = (ProgressBar) findViewById(R.id.progressBar_main);
        sm = new SessionManager(list_ceri_landlost.this);
        container = (LinearLayout)findViewById(R.id.container);
        btnsubmit=(Button)findViewById(R.id.btnsubmit);
        Intent intent = getIntent();
        data = intent.getStringExtra("data");
        ProjectID = intent.getStringExtra("ProjectID");
      di();


        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                params.clear();
                hideKeyboardFrom(getApplicationContext(),btnsubmit);
                count = container.getChildCount();

                if(count>0)
                {
                    // JSONArray jsonArr = new JSONArray();

                    for(int i1=0;i1<=count;i1++)
                    {

                        //  JSONObject prmsLogin = new JSONObject();

                        try
                        {
                            View row = container.getChildAt(i1);
                            // current_count=current_count+1;
                            EditText textOut = (EditText) row.findViewById(R.id.t1);
                            EditText txt_Quantity = (EditText) row.findViewById(R.id.t2);

                            if(i1==10)
                            {

                            }
                           else if(i1==5)
                            {

                                params.put("f3a_ic_"+(i1+1),"Y");
                            }
                            else
                            {
                                params.put("f3a_ec_"+(i1+1),textOut.getText().toString());
                                params.put("f3a_ic_"+(i1+1),txt_Quantity.getText().toString());
                            }


                            // prmsLogin.put("transaction_id",textOut.getText().toString());
                            // jsonArr.put(prmsLogin);

                        }
                        catch (Exception e)
                        {

                        }
                    }

                    try
                    {
                        jsonObj.put("param", params);

                        loginUser();
                    }
                    catch (Exception e)
                    {

                    }


                }
            }
        });



    }
    private void loginUser()
    {

        progressBar_main.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sighteat.com/imitra/api/update/SaveForm3CLandCost/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            progressBar_main.setVisibility(View.GONE);
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("Code").equals("200"))
                            {
                                //mess(jsonObject.getString("Message"));
                                Toast.makeText(getApplicationContext(),jsonObject.getString("Message"),Toast.LENGTH_LONG).show();
                                Intent i = new Intent();
                                setResult(RESULT_OK, i);
                                finish();
                            }
                            else
                            {
                                mess(jsonObject.getString("Message"));
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
                        progressBar_main.setVisibility(View.GONE);
                        mess(error.toString());
                    }
                }){
            @Override
            protected Map<String,String> getParams()
            {

                params.put("ProjectID",ProjectID);

                Log.e("==========",params+"");

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
                headers.put("Tokenid", "" + sm.getto());
                headers.put("Userid", "" + sm.getUserID());
                headers.put("Roleid", "" + sm.getRoleID());



                return headers;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void mess(String mess) {
        Snackbar snackbar = Snackbar.make(btnsubmit, mess, Snackbar.LENGTH_LONG).setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.parseColor("#FA8072"));
        snackbar.show();
    }
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public  void di()
    {
        try
        {
            JSONArray Blocks = new JSONArray(data);

            for (int i = 0; i < Blocks.length(); i++)
            {
                JSONObject json_data = Blocks.getJSONObject(i);
                LayoutInflater layoutInflater =
                        (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.list_fom3_certi, null);
                EditText textOut = (EditText) addView.findViewById(R.id.t1);
                EditText textOut1 = (EditText) addView.findViewById(R.id.t2);
                final CheckBox checkBox_ap=(CheckBox)addView.findViewById(R.id.checkBox_ap);
                if(counter==3||counter==1)
                checkBox_ap.setChecked(true);
                else
                    checkBox_ap.setChecked(false);
                TextView title= (TextView) addView.findViewById(R.id.title);
                final TextView tkk= (TextView) addView.findViewById(R.id.tkk);
                title.setText(i+1+". "+json_data.getString("Name"));
                checkBox_ap.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                    {
                        if ( isChecked )
                        {
                            counter=1;
                            container.removeAllViews();
                            di();

                        }
                        else
                        {
                            counter=0;
                            container.removeAllViews();
                            di();
                        }

                    }
                });
                try
                {
                    tkk.setText(json_data.getString("Name"));
                    title.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Tooltip.Builder builder = new Tooltip.Builder(v, R.style.Tooltip2)
                                    .setCancelable(true)
                                    .setDismissOnClick(false)
                                    .setCornerRadius(20f)
                                    .setGravity(Gravity.BOTTOM)
                                    .setText(tkk.getText().toString());
                            builder.show();
                        }
                    });
                }
                catch (Exception e)
                {

                }

                if(i==5)
                {
                    textOut.setEnabled(false);
                    textOut1.setEnabled(false);
                    textOut.setText("Applicable");
                    textOut1.setText("Checked");
                    textOut1.setVisibility(View.GONE);
                    checkBox_ap.setVisibility(View.VISIBLE);
                    textOut.setBackgroundResource(R.color.transparent);
                    textOut1.setBackgroundResource(R.color.transparent);

                }
                else if( i== (Blocks.length()-1))
                {
                    textOut.setEnabled(false);
                    textOut1.setEnabled(false);
                    textOut.setBackgroundResource(R.color.transparent);
                    textOut1.setBackgroundResource(R.color.transparent);
                    textOut.setText(json_data.getString("EstimatedCost"));
                    textOut1.setText(json_data.getString("IncurredCost"));


                }else {
                    textOut.setText(json_data.getString("EstimatedCost"));
                    textOut1.setText(json_data.getString("IncurredCost"));
                }

                if(counter==0)
                {
                    if(i==6||i==7||i==8||i==9)
                    {

                    }
                    else
                    {
                        container.addView(addView);
                    }
                }
                else
                {
                    container.addView(addView);
                }







            }
        }
        catch (Exception e)
        {

        }

    }

}