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

public class list_ceri_devcost2 extends AppCompatActivity {

    LinearLayout container;
    Button btnsubmit;
    int count = 0;
    ProgressBar progressBar_main;
    SessionManager sm;
    Map<String,String> params = new HashMap<String, String>();
    JSONObject jsonObj = new JSONObject();
    String data = "", classs = "",ProjectID="";
    EditText t2;
    TextView displaytext;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_form3_certi3);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(list_ceri_devcost2.this, R.color.maroon_dark));
        }
        back=(ImageView)findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressBar_main = (ProgressBar) findViewById(R.id.progressBar_main);
        sm = new SessionManager(list_ceri_devcost2.this);
        container = (LinearLayout) findViewById(R.id.container);
        btnsubmit = (Button) findViewById(R.id.btn_save);
        Intent intent = getIntent();

        displaytext=(TextView)findViewById(R.id.displaytext);
        displaytext.setText("Amount withdrawn till date of this certificate as per the Books of Accounts and Bank Statement");

        t2=(EditText)findViewById(R.id.t2);
        data = intent.getStringExtra("data");
        classs = intent.getStringExtra("class");
        ProjectID = intent.getStringExtra("ProjectID");

        try {
            JSONArray jArray = new JSONArray(data);
            for(int i=0;i<jArray.length();i++)
            {
                JSONObject json_data = jArray.getJSONObject(i);
                if(i==(jArray.length()-2))
                {
                    String s=json_data.getString("IncurredCost");
                    t2.setText(s);
                    displaytext.setText(json_data.getString("Name"));
                }


            }

            // JSONObject Form1 = QPR.getJSONObject("Form1");

        } catch (Exception e) {

        }
        btnsubmit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                hideKeyboardFrom(getApplicationContext(),btnsubmit);
                loginUser();
            }
        });
    }
    private void loginUser()
    {

        progressBar_main.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sighteat.com/imitra/api/update/SaveForm3Withdrawal/",
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
                params.put("WithdrawalAmount",t2.getText().toString());

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
