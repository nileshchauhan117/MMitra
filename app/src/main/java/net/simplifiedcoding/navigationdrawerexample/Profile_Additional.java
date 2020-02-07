package net.simplifiedcoding.navigationdrawerexample;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import net.simplifiedcoding.navigationdrawerexample.Helper.Constants;
import net.simplifiedcoding.navigationdrawerexample.Helper.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Profile_Additional extends AppCompatActivity
{
    ImageView back_btn;
    SessionManager sm;
    EditText txt_cname,
            txt_cregno,
    txt_panno;
        Spinner txt_pgroup,
    txt_ptype;
        Button btnlogin;
        ProgressBar progressBar_main;
    public static ArrayList<String> cid=new ArrayList<String>();
    public static ArrayList<String> cname=new ArrayList<String>();

    public static ArrayList<String> pid=new ArrayList<String>();
    public static ArrayList<String> pname=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_additional);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(Profile_Additional.this, R.color.maroon_dark));
        }
        progressBar_main=(ProgressBar)findViewById(R.id.progressBar_main);
        sm = new SessionManager(Profile_Additional.this);
        btnlogin=(Button)findViewById(R.id.btnlogin);
        txt_cname=(EditText)findViewById(R.id.txt_cname);
        txt_cregno=(EditText)findViewById(R.id.txt_cregno);
        txt_panno=(EditText)findViewById(R.id.txt_panno);
        txt_pgroup=(Spinner) findViewById(R.id.txt_pgroup);
        txt_ptype=(Spinner) findViewById(R.id.txt_ptype);
        try
        {
            JSONObject object1 = new JSONObject(sm.getjs());
            JSONObject object = object1.getJSONObject("AdditionalDetail");

            if(object.getString("CompanyName").equalsIgnoreCase("null")||object.getString("CompanyName").equalsIgnoreCase(""))
                Log.e("","dd");
            else
                txt_cname.setText(object.getString("CompanyName"));
            txt_cregno.setText(object.getString("CompanyRegID"));
            txt_panno.setText(object.getString("PANID"));
            //txt_email.setText(object.getString("MobileNumber"));
            //txt_add1.setText(object.getString("PathName"));
            //txt_add2.setText(object.getString("PathName"));
            //txt_zipcode.setText(object.getString("PathName"));

            /*{"UserID":"6","RoleID":"3","RoleName":"Promoters\/Builders","Name":"Anand....","LastName":"Joshi","DisplayName":"Anand.... Joshi","LoginID":"promoter","MobileNumber":"5432109876","MediaID":"35","PathName":"\/uploads\/profile\/5d5105f88d6cb-1565591032.jpg","PasswordAlert":"N","RelationID":"1","PromoterID":"1","PromoterType":"Company","PromoterGroupName":"Promoter Group #1","ContactDetail":{},"AdditionalDetail":{"TypeID":"1","PromoterGroupID":"2","PANID":"BWHQD674BD","CompanyRegID":"COMPANYREG002","CompanyName":"Anand Infra. Ltd...."}}*/




        }
        catch (Exception e)
        {

        }
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit();
            }
        });
        SpinAdapterTimeDelay adap_dri1;
        try
        {
            JSONObject jsonObject = new JSONObject(Constants.promotertype);
            if (jsonObject.getString("Code").equals("200"))
            {
                //JSONObject object = jsonObject.getJSONObject("DashboardData");

                //object.getString("CurrentProjects");

                JSONArray subArray = jsonObject.getJSONArray("Data");

                cid.add("0000");
                cname.add("Select Promotor Type");

                for(int i=0; i<subArray.length(); i++)
                {
                    cid.add(subArray.getJSONObject(i).getString("TypeID").toString());
                    cname.add(subArray.getJSONObject(i).getString("Name").toString());
                    adap_dri1=new SpinAdapterTimeDelay(Profile_Additional.this, cname);
                    txt_pgroup.setAdapter(adap_dri1);
                }

            }
            else
            {

            }
        }
        catch (Exception e)
        {

        }


        SpinAdapterTimeDelay adap_dri;
        try
        {
            JSONObject jsonObject = new JSONObject(Constants.userlist);
            if (jsonObject.getString("Code").equals("200"))
            {
                //JSONObject object = jsonObject.getJSONObject("DashboardData");

                //object.getString("CurrentProjects");

                JSONArray subArray = jsonObject.getJSONArray("Data");

                pid.add("0000");
                pname.add("Select Promoter Group");

                for(int i=0; i<subArray.length(); i++)
                {
                    pid.add(subArray.getJSONObject(i).getString("RoleID").toString());
                    pname.add(subArray.getJSONObject(i).getString("ContactName").toString());
                    adap_dri=new SpinAdapterTimeDelay(Profile_Additional.this, pname);
                    txt_ptype.setAdapter(adap_dri);
                }

            }
            else
            {

            }
        }
        catch (Exception e)
        {

        }
        back_btn=(ImageView)findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public class SpinAdapterTimeDelay extends BaseAdapter
    {
        ArrayList<String> list;
        Context a;
        private LayoutInflater mInflater;
        public SpinAdapterTimeDelay(Context cc,ArrayList<String> list) {
            this.a=cc;
            this.list=list;
            this.mInflater=(LayoutInflater)a.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View rowView = mInflater.inflate(R.layout.spinner, parent, false);


            float dip = 5f;
            Resources r = getResources();
            int px_bottom = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    dip,
                    r.getDisplayMetrics()
            );
            dip = 5f;
            int px_left = (int)TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    dip,
                    r.getDisplayMetrics()
            );
            TextView item = (TextView) rowView.findViewById(R.id.text);
            item.setPadding(px_left,10,px_left,10);
            // Toast.makeText(a, String.valueOf(list.get(position)),Toast.LENGTH_LONG).show();
            item.setText(list.get(position));
            return rowView;
        }

    }
    JSONObject object;
    JSONObject jsonObject;
    private void edit()
    {
        progressBar_main.setVisibility(View.VISIBLE);

        try
        {
            String jsond=sm.getjs();
            jsonObject = new JSONObject(jsond);
            object = jsonObject.getJSONObject("AdditionalDetail");


        }
        catch (Exception e)
        {

        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sighteat.com/imitra/api/update/EditPromoter/",
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
                                profile();
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
                try
                {

                    params.put("UserID",jsonObject.getString("UserID"));
                    params.put("Name",jsonObject.getString("UserID"));
                    params.put("MobileNumber",jsonObject.getString("UserID"));
                    params.put("Password","123");
                    params.put("Address",jsonObject.getString("UserID"));
                    params.put("Address2",jsonObject.getString("UserID"));
                    params.put("CountrySrNo","");
                    params.put("StateSrNo","");
                    params.put("CitySrNo","");
                    params.put("ZIPCode","");
                    params.put("EmailID","");
                    params.put("Remark","");
                    params.put("PromoterGroupID",object.getString("PromoterGroupID"));
                    params.put("PANID",txt_panno.getText().toString());
                    params.put("TypeID",object.getString("TypeID"));
                    params.put("CompanyRegID",txt_cregno.getText().toString());

                }
                catch (Exception e)
                {

                }

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
    private void profile()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://sighteat.com/imitra/api/selection/getUserProfile/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {


                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("Code").equals("200"))
                            {
                                JSONObject object = jsonObject.getJSONObject("ProfileData");
                                String UserID = object.getString("UserID");
                                String RoleID = object.getString("RoleID");
                                String RelationID = object.getString("RelationID");
                                String PathName= object.getString("PathName");
                                // String TokenID = object.getString("TokenID");
                                String LoginID = object.getString("LoginID");
                                String Name = object.getString("Name");
                                String LastName = object.getString("LastName");
                                String MobileNumber = object.getString("MobileNumber");

                                sm.setUserId(UserID, RoleID, RelationID, PathName,   sm.getTokenID(), object+"", MobileNumber, LastName, Name,LoginID);

                                //Toast.makeText(getApplicationContext(),MobileNumber,Toast.LENGTH_LONG).show();
                              //  mess(jsonObject.getString("Message"));



                            }
                            else
                            {

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
                        // progressBar_main.setVisibility(View.GONE);
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
