package net.simplifiedcoding.navigationdrawerexample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.squareup.picasso.Picasso;

import net.simplifiedcoding.navigationdrawerexample.Helper.Constants;
import net.simplifiedcoding.navigationdrawerexample.Helper.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class Profile_Personal extends AppCompatActivity
{
    ImageView back_btn;
    TextView txt_firstname,txt_middlename,
            txt_lastname,
    txt_mobile,
            txt_email,
    txt_add1,
            txt_add2,

    txt_zipcode;
    Spinner  txt_state,
    txt_city;
    Button btnlogin;
    SessionManager sm;
    ProgressBar progressBar_main;
    String sname_get="";
    String sid_get="";
    String cname_get="";    String cid_get="";
    String d_city_name="",d_city_code="";
    String d_state_name="",d_state_code="";

    TextInputLayout layout_txt_middlename;
    public static ArrayList<String> cid=new ArrayList<String>();
    public static ArrayList<String> cname=new ArrayList<String>();
    //Partnership
    //String[] s_per = {"Company","Individual","partnership Firm"};
    String[] s_per = {"Partnership","Proprietorship"};
    String[] s_per_promoter = {"Company","Partnership","Proprietorship"};

    public static ArrayList<String> sid=new ArrayList<String>();
    public static ArrayList<String> sname=new ArrayList<String>();

    ImageView img_aadhar,img_sign;
    EditText txt_panno,txt_aadharno;
    Spinner txt_peri_main;
    ArrayAdapter<String> spinnerArrayAdapter_peri;
    EditText txt_comname,txt_partnershipFirm,txt_desi,txt_partnershipFirmno;
    LinearLayout layout_comname,layout_partnershipFirmno;
    TextInputLayout layout_partnershipFirm;
    LinearLayout layout_type;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_personal);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(Profile_Personal.this, R.color.maroon_dark));
        }
        txt_partnershipFirmno=(EditText)findViewById(R.id.txt_partnershipFirmno);
        layout_type=(LinearLayout)findViewById(R.id.layout_type);
        txt_desi=(EditText)findViewById(R.id.txt_desi);
        layout_txt_middlename=(TextInputLayout)findViewById(R.id.layout_txt_middlename);
        progressBar_main=(ProgressBar)findViewById(R.id.progressBar_main);
        sm = new SessionManager(Profile_Personal.this);
        back_btn=(ImageView)findViewById(R.id.back_btn);
        layout_partnershipFirm=(TextInputLayout) findViewById(R.id.layout_partnershipFirm);
        layout_partnershipFirmno=(LinearLayout)findViewById(R.id.layout_partnershipFirmno);
        layout_comname=(LinearLayout)findViewById(R.id.layout_comname);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        txt_peri_main=(Spinner)findViewById(R.id.txt_type);
        img_aadhar=(ImageView)findViewById(R.id.img_aadhar) ;
        img_sign=(ImageView)findViewById(R.id.img_sign);
        txt_comname=(EditText)findViewById(R.id.txt_comname);
        txt_partnershipFirm=(EditText)findViewById(R.id.txt_partnershipFirm);
        try
        {
            if(sm.getloginid().equalsIgnoreCase("promoter"))
            {
                spinnerArrayAdapter_peri = new ArrayAdapter<String>(Profile_Personal.this,R.layout.spinner,s_per_promoter);
            }
            else
            {
                spinnerArrayAdapter_peri = new ArrayAdapter<String>(Profile_Personal.this,R.layout.spinner,s_per);

            }

            spinnerArrayAdapter_peri.setDropDownViewResource(R.layout.spinner);
            txt_peri_main.setAdapter(spinnerArrayAdapter_peri);
        }
        catch (Exception e)
        {

        }
        txt_peri_main.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub

                // Toast.makeText(getBaseContext(), sp.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
                if(txt_peri_main.getSelectedItem().toString().equalsIgnoreCase("Proprietorship"))
                {
                    layout_partnershipFirm.setVisibility(View.GONE);
                    layout_partnershipFirmno.setVisibility(View.GONE);
                    layout_comname.setVisibility(View.GONE);
                    txt_desi.setText("Proprietor");
                }
                else if(txt_peri_main.getSelectedItem().toString().equalsIgnoreCase("Partnership"))
                {
                    layout_partnershipFirm.setVisibility(View.VISIBLE);
                    layout_partnershipFirmno.setVisibility(View.VISIBLE);
                    layout_partnershipFirm.setHint("Firm Name");
                    layout_comname.setVisibility(View.GONE);
                    txt_desi.setText("Partner");
                }
                else if(txt_peri_main.getSelectedItem().toString().equalsIgnoreCase("Company"))
                {
                    layout_partnershipFirm.setVisibility(View.VISIBLE);
                    layout_partnershipFirmno.setVisibility(View.VISIBLE);
                    layout_partnershipFirm.setHint("Company Name");
                    layout_comname.setVisibility(View.GONE);
                    txt_desi.setText("");
                }

                else
                {
                    layout_partnershipFirm.setVisibility(View.GONE);
                    layout_partnershipFirmno.setVisibility(View.GONE);
                    layout_comname.setVisibility(View.GONE);

                }
            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });

        txt_panno=(EditText)findViewById(R.id.txt_panno);
        txt_aadharno=(EditText)findViewById(R.id.txt_aadharno);
        txt_firstname=(TextView)findViewById(R.id.txt_firstname);
        txt_middlename=(TextView)findViewById(R.id.txt_middlename);
        txt_lastname=(TextView)findViewById(R.id.txt_lastname);
        txt_mobile=(TextView)findViewById(R.id.txt_mobile);
        txt_email=(TextView)findViewById(R.id.txt_email);
        txt_add1=(TextView)findViewById(R.id.txt_add1);
        txt_add2=(TextView)findViewById(R.id.txt_add2);
        txt_zipcode=(TextView)findViewById(R.id.txt_zipcode);

        txt_state=(Spinner) findViewById(R.id.txt_state);
        txt_city=(Spinner) findViewById(R.id.txt_city);
        btnlogin=(Button)findViewById(R.id.btnlogin);



        try
        {
            profile();
        }
        catch (Exception e)
        {

        }



        SpinAdapterTimeDelay adap_dri1;
        try
        {
            JSONObject jsonObject = new JSONObject(Constants.city);
            if (jsonObject.getString("Code").equals("200"))
            {
                //JSONObject object = jsonObject.getJSONObject("DashboardData");

                //object.getString("CurrentProjects");

                JSONArray subArray = jsonObject.getJSONArray("CityData");

                cid.add("0000");
                cname.add("Select City");

                for(int i=0; i<subArray.length(); i++)
                {
                    cid.add(subArray.getJSONObject(i).getString("CitySrNo").toString());
                    cname.add(subArray.getJSONObject(i).getString("CityName").toString());
                    adap_dri1=new SpinAdapterTimeDelay(Profile_Personal.this, cname);
                    txt_city.setAdapter(adap_dri1);
                }


               // txt_city.setSelection(Integer.parseInt(d_city_code));

                txt_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3)
                    {
                        //submit_btn.setBackgroundColor(getResources().getColor(R.color.green_bg));
                        //Toast.makeText(getApplicationContext(), name.get(arg2), Toast.LENGTH_LONG).show();
                        cname_get=cname.get(arg2);
                        cid_get=cid.get(arg2);
                        //gettrucktype_sub gis = new gettrucktype_sub();
                        //AsyncTaskExecutor.executeConcurrently(gis);

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0)
                    {
                    }
                });

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
            JSONObject jsonObject = new JSONObject(Constants.state);
            if (jsonObject.getString("Code").equals("200"))
            {
                //JSONObject object = jsonObject.getJSONObject("DashboardData");

                //object.getString("CurrentProjects");
                sid.add("0000");
                sname.add("Select State");

                JSONArray subArray = jsonObject.getJSONArray("StateData");
                for(int i=0; i<subArray.length(); i++)
                {
                    sid.add(subArray.getJSONObject(i).getString("StateSrNo").toString());
                    sname.add(subArray.getJSONObject(i).getString("StateName").toString());
                }

                adap_dri=new SpinAdapterTimeDelay(Profile_Personal.this, sname);
                txt_state.setAdapter(adap_dri);



                txt_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3)
                    {
                        //submit_btn.setBackgroundColor(getResources().getColor(R.color.green_bg));
                        //Toast.makeText(getApplicationContext(), name.get(arg2), Toast.LENGTH_LONG).show();
                        sname_get=sname.get(arg2);
                        sid_get=sid.get(arg2);
                        //gettrucktype_sub gis = new gettrucktype_sub();
                        //AsyncTaskExecutor.executeConcurrently(gis);

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0)
                    {
                    }
                });
              //  int spinnerPosition = adap_dri.
               // txt_state.setSelection(Integer.parseInt(d_state_code));

            }
            else
            {

            }
        }
        catch (Exception e)
        {

        }

        btnlogin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                edit();

            }
        });

        LinearLayout layout_state, layout_country;
        layout_state=(LinearLayout)findViewById(R.id.layout_state);
        layout_country=(LinearLayout)findViewById(R.id.layout_country);

        if(sm.getloginid().equalsIgnoreCase("Allottee"))
        {
            txt_partnershipFirm.setVisibility(View.GONE);
            txt_email.setVisibility(View.GONE);
            layout_type.setVisibility(View.GONE);
            txt_desi.setVisibility(View.GONE);
            txt_add1.setVisibility(View.GONE);
            txt_add2.setVisibility(View.GONE);
            txt_city.setVisibility(View.GONE);
            txt_state.setVisibility(View.GONE);
            txt_zipcode.setVisibility(View.GONE);
            txt_comname.setVisibility(View.GONE);
            layout_state.setVisibility(View.GONE);
            layout_country.setVisibility(View.GONE);

            /////

        }

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


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sighteat.com/imitra/api/update/EditUserProfile/",
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
                    params.put("RoleID",sm.getRoleID());
                    params.put("Name",txt_firstname.getText().toString());
                    params.put("MiddleName",txt_middlename.getText().toString());
                    params.put("LastName",txt_lastname.getText().toString());
                    params.put("CompanyName",txt_comname.getText().toString());
                    params.put("EmailID",txt_email.getText().toString());

                    params.put("MobileNumber",txt_mobile.getText().toString());
                    params.put("Password","123");
                    params.put("Address",txt_add1.getText().toString());
                    params.put("Address2",txt_add1.getText().toString());
                    params.put("CountrySrNo","");
                    params.put("StateSrNo",sid_get);
                    params.put("CitySrNo",cid_get);
                    params.put("ZIPCode",txt_zipcode.getText().toString());
                    params.put("ConsultantType",txt_desi.getText().toString());
                    params.put("FirmName",txt_partnershipFirm.getText().toString());
                    params.put("FirmRegNo",txt_partnershipFirmno.getText().toString());

                   /* params.put("Remark","");
                    params.put("PromoterGroupID",object.getString("PromoterGroupID"));
                    params.put("PANID",object.getString("PANID"));
                    params.put("TypeID",object.getString("TypeID"));
                    params.put("CompanyRegID",object.getString("CompanyRegID"));*/

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

                                try
                                {
                                    JSONObject object = new JSONObject(jsonObject.getJSONObject("ProfileData")+"");

                                    String UserID = object.getString("UserID");
                                    String RoleID = object.getString("RoleID");
                                    String PathName= object.getString("ProfilePicture");
                                    String Name = object.getString("Name");
                                    String LastName = object.getString("LastName");
                                    String MobileNumber = object.getString("MobileNumber");
                                    txt_comname.setText(object.getString("CompanyName"));
                                    txt_middlename.setText(object.getString("MiddleName"));
                                    try
                                    {
                                        String PromoterType=object.getString("PromoterType");
                                        if(PromoterType.equalsIgnoreCase("Individual"))
                                        {

                                            //layout_txt_middlename.setVisibility(View.VISIBLE);
                                        }
                                        else
                                        {
                                            //layout_txt_middlename.setVisibility(View.GONE);
                                        }
                                    }
                                    catch (Exception e)
                                    {

                                    }

                                    txt_firstname.setText(Name);
                                    txt_lastname.setText(LastName);
                                    txt_mobile.setText(object.getString("MobileNumber"));
                                    String ContactDetail=object.getString("ContactDetail");
                                    JSONObject j=new JSONObject(ContactDetail);
                                    try
                                    {
                                        txt_email.setText(j.getString("EmailID"));

                                        if(j.getString("Address").equalsIgnoreCase("null"))
                                            Log.e("ddd","dd");
                                        else
                                            txt_add1.setText(j.getString("Address"));

                                        if(j.getString("Address2").equalsIgnoreCase("null"))
                                            Log.e("ddd","dd");
                                        else
                                            txt_add2.setText(j.getString("Address2"));

                                        if(j.getString("ZIPCode").equalsIgnoreCase("null"))
                                            txt_zipcode.setText("");
                                        else
                                            txt_zipcode.setText(j.getString("ZIPCode"));

                                        d_city_name=j.getString("CityName");
                                        d_city_code=j.getString("CitySrNo");
                                        d_state_name=j.getString("StateName");
                                        d_state_code=j.getString("StateCode");
                                    }
                                    catch (Exception e)
                                    {

                                    }

                                    try
                                    {
                                        if(sm.getloginid().equalsIgnoreCase("promoter"))
                                        {
                                            txt_partnershipFirm.setText(object.getString("CompanyName"));
                                        }
                                        else
                                        {
                                            txt_partnershipFirm.setText(j.getString("FirmName"));
                                        }

                                        if(j.getString("FirmRegNo").equalsIgnoreCase("null"))
                                            Log.e("ddd","");
                                        else
                                        txt_partnershipFirmno.setText(j.getString("FirmRegNo"));

                                        String ConsultantType= j.getString("ConsultantType");

                                        if(sm.getloginid().equalsIgnoreCase("promoter"))
                                        {
                                            if(ConsultantType.equalsIgnoreCase("Proprietorship"))
                                            {
                                                txt_desi.setText("Proprietor");
                                                txt_peri_main.setSelection(2);
                                            }
                                            else if(ConsultantType.equalsIgnoreCase("Partnership"))
                                            {
                                                txt_desi.setText("Partner");
                                                txt_peri_main.setSelection(1);

                                            }
                                            else
                                            {
                                                txt_desi.setText("Partner");
                                                txt_peri_main.setSelection(0);
                                            }
                                        }
                                        else
                                        {
                                            if(ConsultantType.equalsIgnoreCase("Proprietorship"))
                                            {
                                                txt_desi.setText("Proprietor");
                                                txt_peri_main.setSelection(1);
                                            }
                                            else if(ConsultantType.equalsIgnoreCase("Partnership"))
                                            {
                                                txt_desi.setText("Partner");
                                                txt_peri_main.setSelection(0);
                                            }
                                            else
                                            {
                                                txt_desi.setText("Partner");
                                                txt_peri_main.setSelection(0);
                                            }
                                        }

                                    }
                                    catch (Exception e)
                                    {

                                    }




                                }
                                catch (Exception e)
                                {

                                }

                            }
                            else
                            {

                            }
                        }
                        catch (Exception e)
                        {

                            Log.e("dddd",e.toString());
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
