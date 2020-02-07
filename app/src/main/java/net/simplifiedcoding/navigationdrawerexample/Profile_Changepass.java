package net.simplifiedcoding.navigationdrawerexample;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Profile_Changepass extends AppCompatActivity
{
    ImageView back_btn;
    Button btnlogin;
    EditText txt_cpass,txt_pass,txt_npass;
    ProgressBar progressBar_main;
    SessionManager sm;
    TextView txt_messotp,txt_messotp1;
    Button btn_otp;
    EditText txt_otp;
    private static CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_changepass);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(Profile_Changepass.this, R.color.maroon_dark));
        }
        sm = new SessionManager(Profile_Changepass.this);
        txt_messotp=(TextView)findViewById(R.id.txt_messotp) ;
        txt_messotp1=(TextView)findViewById(R.id.txt_messotp1) ;
        txt_messotp.setVisibility(View.GONE);
        txt_messotp1.setVisibility(View.GONE);
        btn_otp =(Button)findViewById(R.id.btn_otp);
        txt_otp=(EditText)findViewById(R.id.txt_otp);
        back_btn=(ImageView)findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressBar_main=(ProgressBar)findViewById(R.id.progressBar_main);
        btnlogin=(Button)findViewById(R.id.btnlogin);
        txt_cpass=(EditText)findViewById(R.id.txt_cpass);
        txt_pass=(EditText)findViewById(R.id.txt_pass);
        txt_npass=(EditText)findViewById(R.id.txt_npass);
        btn_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                loginUser_otp();
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(txt_cpass.getText().toString().equalsIgnoreCase(""))
                {
                    mess("Enter Current password");
                }
                else if(txt_pass.getText().toString().equalsIgnoreCase(""))
                {
                    mess("Enter New password");
                }
                else if(txt_npass.getText().toString().equalsIgnoreCase(""))
                {
                    mess("Enter Retype New password");
                }
                else if(txt_otp.getText().toString().equalsIgnoreCase(""))
                {
                    mess("Enter Valid OTP");
                }

                else
                {
                     if(txt_pass.getText().toString().equalsIgnoreCase(txt_npass.getText().toString()))
                     {
                         change();

                     }
                     else
                     {
                         mess("New password & Retype new password not match");
                     }

                }




            }
        });



    }
    private void change()
    {

        progressBar_main.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sighteat.com/imitra/api/update/ChangePassword/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        progressBar_main.setVisibility(View.GONE);
                        //Toast.makeText(Activity_Login.this,response,Toast.LENGTH_LONG).show();
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("Code").equals("200"))
                            {
                                mess_sucess(jsonObject.getString("Message"));
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
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("currentPassword",txt_cpass.getText().toString());
                params.put("Password",txt_pass.getText().toString());
                params.put("newPassword",txt_npass.getText().toString());
                params.put("OTP",txt_otp.getText().toString());

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
        Snackbar snackbar = Snackbar.make(btnlogin, mess, Snackbar.LENGTH_LONG).setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.parseColor("#FA8072"));
        snackbar.show();
    }
    public void mess_sucess(String mess)
    {
        Snackbar snackbar = Snackbar.make(btnlogin, mess, Snackbar.LENGTH_LONG).setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.parseColor("#ff3cb371"));
        snackbar.show();
    }
    private void loginUser_otp()
    {

        progressBar_main.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sighteat.com/imitra/api/authenticate/GenerateOTP/",
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
                                txt_messotp1.setText("OTP Sent! It is valid for 15 minutes.");
                                txt_messotp.setVisibility(View.VISIBLE);
                                txt_messotp1.setVisibility(View.VISIBLE);
                                Toast.makeText(getApplicationContext(),jsonObject.getString("Message")+"",Toast.LENGTH_LONG).show();
                                if (countDownTimer == null)
                                {
                                    String getMinutes = "1";
                                    //Check validation over edittext
                                    if (!getMinutes.equals("") && getMinutes.length() > 0) {
                                        int noOfMinutes = Integer.parseInt(getMinutes) * 60 * 1000;//Convert minutes into milliseconds

                                        startTimer(noOfMinutes);//start countdown
                                        // startTimer.setText(getString(R.string.stop_timer));//Change Text

                                    } else
                                        Toast.makeText(Profile_Changepass.this, "Please enter no. of Minutes.", Toast.LENGTH_SHORT).show();//Display toast if edittext is empty
                                } else {
                                    //Else stop timer and change text
                                    stopCountdown();
                                    // startTimer.setText(getString(R.string.start_timer));
                                }

                            }
                            else
                            {
                                mess(jsonObject.getString("Message"));
                            }
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
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
                Map<String,String> params = new HashMap<String, String>();

                params.put("MobileNumber",sm.getmobileno());
                params.put("Type","PC");


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
    private void startTimer(int noOfMinutes)
    {
        countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%02d",  TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                btn_otp.setText("Please Wait.."+hms);//set text
                txt_messotp.setVisibility(View.GONE);
                //txt_mobile.setEnabled(false);
                btn_otp.setEnabled(false);

            }

            public void onFinish() {

               // txt_mobile.setEnabled(true);
                txt_messotp.setVisibility(View.GONE);
                btn_otp.setEnabled(true);
                btn_otp.setText("Get OTP");
                txt_messotp.setText("TIME'S UP!!"); //On finish change timer text
                countDownTimer = null;//set CountDownTimer to null
                //txt_messotp.setText(getString(R.string.start_timer));//Change button text
            }
        }.start();

    }
    private void stopCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }
}
