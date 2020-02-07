package net.simplifiedcoding.navigationdrawerexample;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
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

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Activity_Regi extends AppCompatActivity
{

    ImageView back_btn;
    TextView tv_usernames,tv_emails;
    SessionManager sm;
    ProgressBar progressBar_main;
    private RadioButton radio1, radio2, radio3,radio4;
    EditText txt_mobile,txt_email,txt_last,txt_first,txt_comy;
    Button btnlogin;
    String RoleID="3";
    EditText txt_partnershipFirm;
    LinearLayout layout_type;
    String[] s_per = {"Company","Individual","partnership Firm"};
    Spinner txt_peri_main;
    TextInputLayout layout_lname,layout_fname,layout_partnershipFirm,layout_comy;
    ArrayAdapter<String> spinnerArrayAdapter_peri;
    CheckBox radio_turms;
    TextView txt_tc;
   TextView txt_messotp,txt_messotp1;
   Button btn_otp;
    private static CountDownTimer countDownTimer;
   EditText txt_otp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regi);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        back_btn=(ImageView)findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txt_tc=(TextView)findViewById(R.id.txt_tc) ;
        radio_turms= (CheckBox) findViewById(R.id.radio_turms);
        txt_tc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               // Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.antennahouse.com/XSLsample/pdf/sample-link_1.pdf"));
               // startActivity(browserIntent);

                Intent i=new Intent(getApplicationContext(),Activity_Terms.class);
                startActivity(i);
            }
        });
        progressBar_main = (ProgressBar) findViewById(R.id.progressBar_main);
        txt_peri_main=(Spinner)findViewById(R.id.txt_type);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(Activity_Regi.this, R.color.maroon_dark));
        }

        try
        {
            spinnerArrayAdapter_peri = new ArrayAdapter<String>(Activity_Regi.this,R.layout.spinner,s_per);
            spinnerArrayAdapter_peri.setDropDownViewResource(R.layout.spinner);
            txt_peri_main.setAdapter(spinnerArrayAdapter_peri);
        }
        catch (Exception e)
        {

        }

        txt_messotp=(TextView)findViewById(R.id.txt_messotp) ;
        txt_messotp1=(TextView)findViewById(R.id.txt_messotp1) ;
        txt_messotp.setVisibility(View.GONE);
        txt_messotp1.setVisibility(View.GONE);
        btn_otp =(Button)findViewById(R.id.btn_otp);
        txt_otp=(EditText)findViewById(R.id.txt_otp);


        layout_partnershipFirm=(TextInputLayout) findViewById(R.id.layout_partnershipFirm);
        layout_lname=(TextInputLayout)findViewById(R.id.layout_lname);
        layout_fname=(TextInputLayout)findViewById(R.id.layout_fname);
        layout_comy=(TextInputLayout)findViewById(R.id.layout_comy);
        layout_type=(LinearLayout)findViewById(R.id.layout_type);
        radio1=(RadioButton)findViewById(R.id.radio1);
        radio2=(RadioButton)findViewById(R.id.radio2);
        radio3=(RadioButton)findViewById(R.id.radio3);
        radio4=(RadioButton)findViewById(R.id.radio4);
        txt_comy=(EditText)findViewById(R.id.txt_comy);
        txt_mobile=(EditText)findViewById(R.id.txt_mobile);
        txt_email=(EditText)findViewById(R.id.txt_email);
        txt_last=(EditText)findViewById(R.id.txt_last);
        txt_partnershipFirm=(EditText)findViewById(R.id.txt_partnershipFirm);
        txt_first=(EditText)findViewById(R.id.txt_first);
        btnlogin=(Button)findViewById(R.id.btnlogin);

        layout_lname.setVisibility(View.GONE);
        layout_fname.setVisibility(View.GONE);
        layout_partnershipFirm.setVisibility(View.GONE);
        radio1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(radio1.isChecked())
                {
                    radio1.setChecked(true);
                    radio2.setChecked(false);
                    radio3.setChecked(false);
                    radio4.setChecked(false);
                    //3-Promoter/6-CA/8-Engineer/9-Architect
                    RoleID="3";

                    txt_peri_main.setSelection(0);
                    layout_lname.setVisibility(View.GONE);
                    layout_fname.setVisibility(View.GONE);
                    layout_type.setVisibility(View.VISIBLE);
                    txt_comy.setVisibility(View.VISIBLE);
                    layout_comy.setVisibility(View.VISIBLE);
                    txt_comy.requestFocus();

                }
            }

        });

        radio2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(radio2.isChecked())
                {

                    radio2.setChecked(true);
                    radio1.setChecked(false);
                    radio4.setChecked(false);
                    radio3.setChecked(false);
                    //3-Promoter/6-CA/8-Engineer/9-Architect
                    RoleID="6";

                    layout_type.setVisibility(View.GONE);
                    txt_comy.setVisibility(View.GONE);
                    layout_comy.setVisibility(View.GONE);
                    layout_lname.setVisibility(View.VISIBLE);
                    layout_fname.setVisibility(View.VISIBLE);
                    txt_partnershipFirm.setVisibility(View.GONE);
                    txt_first.requestFocus();

                }
            }

        });
        radio3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(radio3.isChecked())
                {

                    radio3.setChecked(true);
                    radio1.setChecked(false);
                    radio2.setChecked(false);
                    radio4.setChecked(false);
                    //3-Promoter/6-CA/8-Engineer/9-Architect
                    RoleID="8";
                    layout_type.setVisibility(View.GONE);
                    txt_comy.setVisibility(View.GONE);
                    layout_comy.setVisibility(View.GONE);
                    layout_lname.setVisibility(View.VISIBLE);
                    layout_fname.setVisibility(View.VISIBLE);
                    txt_partnershipFirm.setVisibility(View.GONE);
                    txt_first.requestFocus();

                }
            }

        });

        radio4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if(radio4.isChecked())
                {
                    radio4.setChecked(true);
                    radio1.setChecked(false);
                    radio2.setChecked(false);
                    radio3.setChecked(false);
                    //3-Promoter/6-CA/8-Engineer/9-Architect
                    RoleID="9";
                    layout_type.setVisibility(View.GONE);
                    txt_comy.setVisibility(View.GONE);
                    layout_comy.setVisibility(View.GONE);
                    layout_lname.setVisibility(View.VISIBLE);
                    layout_fname.setVisibility(View.VISIBLE);
                    txt_partnershipFirm.setVisibility(View.GONE);
                    txt_first.requestFocus();

                }
            }

        });

        btn_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
             if(txt_mobile.getText().toString().equalsIgnoreCase(""))
            {
                mess("Enter Valid Mobile Number");
            }
             else
                loginUser_otp();
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(RoleID.equalsIgnoreCase("3")&&txt_peri_main.getSelectedItem().toString().equalsIgnoreCase("Company"))
                {
                    if(txt_comy.getText().toString().equalsIgnoreCase(""))
                    {
                        mess("Enter Valid Company Name");
                    }
                    else if(!txt_email.getText().toString().matches("[a-zA-Z0-9.+_-]+@[a-z]+.[a-z]+"))
                    {
                        mess("Enter Valid Email Id");
                    }
                    else if(txt_mobile.getText().toString().equalsIgnoreCase(""))
                    {
                        mess("Enter Valid Mobile Number");
                    }
                    else if(txt_otp.getText().toString().equalsIgnoreCase(""))
                    {
                        mess("Enter Valid OTP");
                    }
                    else
                    {
                        if(radio_turms.isChecked())
                        {
                            loginUser();
                        }

                        else
                        {
                            mess("Accept terms and conditions");
                        }



                    }
                }
               else if(RoleID.equalsIgnoreCase("3")&&txt_peri_main.getSelectedItem().toString().equalsIgnoreCase("Individual"))
                {
                    if(txt_first.getText().toString().equalsIgnoreCase(""))
                    {
                        mess("Enter Valid First Name");
                    }
                    else if(txt_last.getText().toString().equalsIgnoreCase(""))
                    {
                        mess("Enter Valid Last Name");
                    }
                    else if(!txt_email.getText().toString().matches("[a-zA-Z0-9.+_-]+@[a-z]+.[a-z]+"))
                    {
                        mess("Enter Valid Email Id");
                    }
                    else if(txt_mobile.getText().toString().equalsIgnoreCase(""))
                    {
                        mess("Enter Valid Mobile Number");
                    }
                    else if(txt_otp.getText().toString().equalsIgnoreCase(""))
                    {
                        mess("Enter Valid OTP");
                    }
                    else
                    {
                        if(radio_turms.isChecked())
                        {
                            loginUser();
                        }

                        else
                        {
                            mess("Accept terms and conditions");
                        }



                    }
                }
                else if(RoleID.equalsIgnoreCase("3")&&txt_peri_main.getSelectedItem().toString().equalsIgnoreCase("partnership Firm"))
                {
                    if(txt_partnershipFirm.getText().toString().equalsIgnoreCase(""))
                    {
                        mess("Enter Valid Firm Name");
                    }
                    else if(!txt_email.getText().toString().matches("[a-zA-Z0-9.+_-]+@[a-z]+.[a-z]+"))
                    {
                        mess("Enter Valid Email Id");
                    }
                    else if(txt_mobile.getText().toString().equalsIgnoreCase(""))
                    {
                        mess("Enter Valid Mobile Number");
                    }
                    else if(txt_otp.getText().toString().equalsIgnoreCase(""))
                    {
                        mess("Enter Valid OTP");
                    }
                    else
                    {
                        if(radio_turms.isChecked())
                        {
                            loginUser();
                        }

                        else
                        {
                            mess("Accept terms and conditions");
                        }



                    }
                }
                else if(RoleID.equalsIgnoreCase("6"))
                {
                    if(txt_first.getText().toString().equalsIgnoreCase(""))
                    {
                        mess("Enter Valid First Name");
                    }
                    else if(txt_last.getText().toString().equalsIgnoreCase(""))
                    {
                        mess("Enter Valid Last Name");
                    }

                    else if(!txt_email.getText().toString().matches("[a-zA-Z0-9.+_-]+@[a-z]+.[a-z]+"))
                    {
                        mess("Enter Valid Email Id");
                    }
                    else if(txt_mobile.getText().toString().equalsIgnoreCase(""))
                    {
                        mess("Enter Valid Mobile Number");
                    }
                    else if(txt_otp.getText().toString().equalsIgnoreCase(""))
                    {
                        mess("Enter Valid OTP");
                    }
                    else
                    {
                        if(radio_turms.isChecked())
                        {
                            loginUser();
                        }

                        else
                        {
                            mess("Accept terms and conditions");
                        }



                    }
                }
                else if(RoleID.equalsIgnoreCase("8"))
                {
                    if(txt_first.getText().toString().equalsIgnoreCase(""))
                    {
                        mess("Enter Valid First Name");
                    }
                    else if(txt_last.getText().toString().equalsIgnoreCase(""))
                    {
                        mess("Enter Valid Last Name");
                    }
                    else if(!txt_email.getText().toString().matches("[a-zA-Z0-9.+_-]+@[a-z]+.[a-z]+"))
                    {
                        mess("Enter Valid Email Id");
                    }
                    else if(txt_mobile.getText().toString().equalsIgnoreCase(""))
                    {
                        mess("Enter Valid Mobile Number");
                    }
                    else if(txt_otp.getText().toString().equalsIgnoreCase(""))
                    {
                        mess("Enter Valid OTP");
                    }
                    else
                    {
                        if(radio_turms.isChecked())
                        {
                            loginUser();
                        }

                        else
                        {
                            mess("Accept terms and conditions");
                        }



                    }
                }
                else if(RoleID.equalsIgnoreCase("9"))
                {
                    if(txt_first.getText().toString().equalsIgnoreCase(""))
                    {
                        mess("Enter Valid First Name");
                    }
                    else if(txt_last.getText().toString().equalsIgnoreCase(""))
                    {
                        mess("Enter Valid Last Name");
                    }

                    else if(!txt_email.getText().toString().matches("[a-zA-Z0-9.+_-]+@[a-z]+.[a-z]+"))
                    {
                        mess("Enter Valid Email Id");
                    }
                    else if(txt_mobile.getText().toString().equalsIgnoreCase(""))
                    {
                        mess("Enter Valid Mobile Number");
                    }
                    else if(txt_otp.getText().toString().equalsIgnoreCase(""))
                    {
                        mess("Enter Valid OTP");
                    }
                    else
                    {
                        if(radio_turms.isChecked())
                        {
                            loginUser();
                        }

                        else
                        {
                            mess("Accept terms and conditions");
                        }



                    }
                }


                //Intent i = new Intent(getApplicationContext(), MainActivity.class);
                // startActivity(i);
            }
        });
        txt_peri_main.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub

                // Toast.makeText(getBaseContext(), sp.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
                if(txt_peri_main.getSelectedItem().toString().equalsIgnoreCase("partnership Firm"))
                {
                    layout_lname.setVisibility(View.GONE);
                    layout_fname.setVisibility(View.GONE);
                    layout_type.setVisibility(View.VISIBLE);
                    txt_comy.setVisibility(View.GONE);
                    layout_comy.setVisibility(View.GONE);
                    layout_partnershipFirm.setVisibility(View.VISIBLE);
                    txt_partnershipFirm.setVisibility(View.VISIBLE);
                    txt_partnershipFirm.requestFocus();

                }
               else if(txt_peri_main.getSelectedItem().toString().equalsIgnoreCase("Individual"))
                {
                    layout_lname.setVisibility(View.VISIBLE);
                    layout_fname.setVisibility(View.VISIBLE);
                    layout_type.setVisibility(View.VISIBLE);
                    txt_comy.setVisibility(View.GONE);
                    layout_comy.setVisibility(View.GONE);
                    layout_partnershipFirm.setVisibility(View.GONE);
                    txt_partnershipFirm.requestFocus();

                }
                else
                {

                    layout_lname.setVisibility(View.GONE);
                    layout_fname.setVisibility(View.GONE);
                    layout_type.setVisibility(View.VISIBLE);
                    txt_comy.setVisibility(View.VISIBLE);
                    layout_comy.setVisibility(View.VISIBLE);
                    layout_partnershipFirm.setVisibility(View.GONE);
                    txt_comy.requestFocus();

                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

    }
    private void loginUser()
    {



        progressBar_main.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sighteat.com/imitra/api/insert/UserRegistration/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        progressBar_main.setVisibility(View.GONE);
                        //Toast.makeText(Activity_Login.this,response,Toast.LENGTH_LONG).show();
                        parseData(response);

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

                if(RoleID.equalsIgnoreCase("3")&& txt_peri_main.getSelectedItem().toString().equalsIgnoreCase("partnership Firm"))
                {
                    params.put("Name",txt_partnershipFirm.getText().toString());
                }
                else if(RoleID.equalsIgnoreCase("3") && txt_peri_main.getSelectedItem().toString().equalsIgnoreCase("Company"))
                {
                    params.put("Name",txt_comy.getText().toString());
                }
                else
                {
                    params.put("Name",txt_first.getText().toString());
                }

                params.put("TypeID",txt_peri_main.getSelectedItem().toString());
                params.put("LastName",txt_last.getText().toString());
                params.put("EmailID",txt_email.getText().toString());
                params.put("MobileNumber",txt_mobile.getText().toString());
                params.put("RoleID",RoleID);


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

                            txt_mobile.setEnabled(false);
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
                                        Toast.makeText(Activity_Regi.this, "Please enter no. of Minutes.", Toast.LENGTH_SHORT).show();//Display toast if edittext is empty
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

                params.put("MobileNumber",txt_mobile.getText().toString());
                params.put("Type","RG");


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
    public void mess(String mess) {
        Snackbar snackbar = Snackbar.make(btnlogin, mess, Snackbar.LENGTH_LONG).setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.parseColor("#FA8072"));
        snackbar.show();
    }
    public void parseData(String response) {

        try
        {

            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("Code").equals("200"))
            {
               // JSONObject object = jsonObject.getJSONObject("Profile");

               Toast.makeText(getApplicationContext(),jsonObject.getString("Message")+"",Toast.LENGTH_LONG).show();

              finish();
              // mess(jsonObject.getString("Message"));

               /* Intent intent = new Intent(Activity_Login.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();*/

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

    private void startTimer(int noOfMinutes)
    {
        countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%02d",  TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                btn_otp.setText("Please Wait.."+hms);//set text
                txt_mobile.setEnabled(false);
                txt_messotp.setVisibility(View.GONE);
                btn_otp.setEnabled(false);

            }

            public void onFinish() {

               // txt_mobile.setEnabled(true);
                txt_messotp.setVisibility(View.GONE);
                btn_otp.setEnabled(true);
                btn_otp.setText("Get OTP"); //On finish change timer text
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
