package net.simplifiedcoding.navigationdrawerexample;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Activity_Terms  extends AppCompatActivity
{

    ImageView back_btn;
    TextView tv_usernames,tv_emails;
    SessionManager sm;
    TextView tv_display;
    ProgressBar progressBar_main;
    WebView webView;
    TextView title1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview_con);

        title1=(TextView)findViewById(R.id.title1);
        progressBar_main = (ProgressBar) findViewById(R.id.progressBar_main);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(Activity_Terms.this, R.color.maroon_dark));
        }

        back_btn=(ImageView)findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        webView = (WebView) findViewById(R.id.webView);
        callapi();


    }
    private void callapi()
    {

        progressBar_main.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://sighteat.com/imitra/api/selection/getContent//{\"Category\":\"Terms\"}",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        progressBar_main.setVisibility(View.GONE);

                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("Code").equals("200"))
                            {
                                JSONObject object = jsonObject.getJSONObject("Content");
                                String Description = object.getString("Description");
                                webView.loadDataWithBaseURL(null, Description, "text/html", "utf-8", null);
                                title1.setText(object.getString("Title"));


                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),jsonObject.getString("Message"),Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Activity_Terms.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intent);
                                overridePendingTransition(0, 0);
                                finish();
                            }
                        }
                        catch (Exception e)
                        {

                        }

                        // Toast.makeText(Activity_Login.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);

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
                headers.put("Tokenid", "" + "8ba100109997531537277777");
                headers.put("Userid", "" + "1");
                headers.put("Roleid", "" + "1");
                headers.put("Relationid", "" + "1");
                return headers;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void mess(String mess) {
        Snackbar snackbar = Snackbar.make(back_btn, mess, Snackbar.LENGTH_LONG).setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.parseColor("#FA8072"));
        snackbar.show();
    }
}