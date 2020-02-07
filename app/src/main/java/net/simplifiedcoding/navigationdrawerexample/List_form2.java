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
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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

import net.simplifiedcoding.navigationdrawerexample.Helper.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class List_form2 extends AppCompatActivity {

    LinearLayout container;
    Button btnsubmit;
    int count = 0;
    JSONObject jsonObj = new JSONObject();
    String data = "", classs = "";
    TextView t1,t2,t3,t4,t5;
    EditText a1,a2,a3,a4,a5;
    ProgressBar progressBar_main;
    SessionManager sm;
    String BlockID="",ProjectID="";
    Map<String,String> params = new HashMap<String, String>();
    int p=0;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_form2);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(List_form2.this, R.color.maroon_dark));
        }
        progressBar_main = (ProgressBar) findViewById(R.id.progressBar_main);
        sm = new SessionManager(List_form2.this);
        btnsubmit = (Button) findViewById(R.id.btnsubmit);
        Intent intent = getIntent();
        ProjectID = intent.getStringExtra("ProjectID");
        data = intent.getStringExtra("data");
        classs = intent.getStringExtra("class");
        back=(ImageView)findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        t1=(TextView)findViewById(R.id.t1);
        t2=(TextView)findViewById(R.id.t2);
        t3=(TextView)findViewById(R.id.t3);
        t4=(TextView)findViewById(R.id.t4);
        t5=(TextView)findViewById(R.id.t5);

        a1=(EditText) findViewById(R.id.a1);
        a2=(EditText) findViewById(R.id.a2);
        a3=(EditText) findViewById(R.id.a3);
        a4=(EditText) findViewById(R.id.a4);
        a5=(EditText) findViewById(R.id.a5);

        t1.setText("Estimated Cost : ");
        t2.setText("Incurred Cost : ");
        t3.setText("Incurred Percentage : ");
        t4.setText("Balance : ");
        t5.setText("Other : ");

        a1.setHint("Estimated Cost ");
        a2.setHint("Incurred Cost ");
       // a3.setHint("Incurred Percentage ");
        a4.setHint("Balance  ");
        a5.setHint("Other  ");

        if(classs.equalsIgnoreCase("Common Area"))
        {
            p=1;
            try
            {
                final JSONObject QPR = new JSONObject(data);
                JSONObject A = QPR.getJSONObject("B");

                String s1=A.getString("Costing");
                JSONObject jsonObject = new JSONObject(s1);
                a1.setText(jsonObject.getString("EstimatedCost"));
                a2.setText(jsonObject.getString("IncurredCost"));
                a3.setText(jsonObject.getString("IncurredPercentage"));
                a4.setText(jsonObject.getString("Balance"));
                a5.setText(jsonObject.getString("Other"));

            }
            catch ( Exception e)
            {

            }
        }
        else
        {
            p=0;
            try
            {
                final JSONObject QPR = new JSONObject(data);
                JSONObject A = QPR.getJSONObject("A");

                JSONArray  Blocks = A.getJSONArray("Blocks");

                for(int k=0;k<Blocks.length();k++)
                {
                    try
                    {

                        JSONObject json_data = Blocks.getJSONObject(k);
                        String s=json_data.getString("BlockName");
                        if(s.equalsIgnoreCase(classs))
                        {
                             BlockID=json_data.getString("BlockID");
                            String s1=json_data.getString("Costing");
                            JSONObject jsonObject = new JSONObject(s1);
                            a1.setText(jsonObject.getString("EstimatedCost"));
                            a2.setText(jsonObject.getString("IncurredCost"));
                            a3.setText(jsonObject.getString("IncurredPercentage"));
                            a4.setText(jsonObject.getString("Balance"));
                            a5.setText(jsonObject.getString("Other"));

                        }



                    }
                    catch (Exception e)
                    {

                    }

                }


            } catch (Exception e) {

            }

        }


        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                hideKeyboardFrom(getApplicationContext(),btnsubmit);
                if(p==1)
                {
                    loginUser1();
                }
                else
                {
                    loginUser();
                }

            }
        });
    }
    private void loginUser()
    {

        progressBar_main.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sighteat.com/imitra/api/update/SaveForm2Detail/",
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
                params.put("BlockID",BlockID);
                params.put("EstimatedCost",a1.getText().toString());
                params.put("IncurredCost",a2.getText().toString());
                params.put("OtherCost",a5.getText().toString());

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
    private void loginUser1()
    {

        progressBar_main.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sighteat.com/imitra/api/update/SaveForm2Detail/",
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
                params.put("EstimatedCost",a1.getText().toString());
                params.put("IncurredCost",a2.getText().toString());
                params.put("OtherCost",a5.getText().toString());

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
}
