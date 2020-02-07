package net.simplifiedcoding.navigationdrawerexample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import com.facebook.shimmer.ShimmerFrameLayout;

import net.simplifiedcoding.navigationdrawerexample.Helper.ConnectionDetector;
import net.simplifiedcoding.navigationdrawerexample.Helper.SessionManager;
import net.simplifiedcoding.navigationdrawerexample.model.Driver_model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Activity_Authorized extends AppCompatActivity {

    SessionManager sm;
    FloatingActionButton btn_add;
    ArrayList<Driver_model> actorsList;
    AllDriversAdapter adapter;
    ListView order_listview;
    private ShimmerFrameLayout mShimmerViewContainer;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout norecord, ll_filter, ll_refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorized);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(Activity_Authorized.this, R.color.maroon_dark));
        }
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        sm = new SessionManager(Activity_Authorized.this);
        btn_add=(FloatingActionButton)findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(getApplicationContext(),Activity_Add_Athorized.class);
                startActivity(i);
            }
        });
        loginUser();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.simpleSwipeRefreshLayout);
        btn_add = (FloatingActionButton) findViewById(R.id.btn_add);
        order_listview = (ListView) findViewById(R.id.listView_orderd);

        //swipeRefreshLayout = (SwipeRefreshLayout)rootView. findViewById(R.id.swipe_refresh_layout);
        order_listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        norecord = (LinearLayout) findViewById(R.id.norecord);

        actorsList = new ArrayList<Driver_model>();


    }
    private void loginUser()
    {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://sighteat.com/imitra/api/selection/getAuthSignatoriesUserList/{\"UserID\":\"0\",\"PromoterID\":\"0\",\"AuthUserID\":\"0\"}",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {

                       // Toast.makeText(Activity_Authorized.this,response,Toast.LENGTH_LONG).show();
                        Log.e("-----------",response+"");
                        try
                        {
                            mShimmerViewContainer.stopShimmerAnimation();
                            mShimmerViewContainer.setVisibility(View.GONE);

                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("Code").equals("200"))
                            {
                                JSONArray array = new JSONArray();
                                array = jsonObject.getJSONArray("AuthSignatories");


                                for (int i = 0; i < array.length(); i++) {

                                    actorsList.add(new Driver_model(
                                            array.getJSONObject(i).getString("ContactName"),
                                            array.getJSONObject(i).getString("MobileNumber"),
                                            array.getJSONObject(i).getString("EmailID"),
                                            array.getJSONObject(i).getString("ContactName"),
                                            array.getJSONObject(i).getString("ContactName"),
                                            array.getJSONObject(i).getString("ContactName"),
                                            array.getJSONObject(i).getString("ContactName"),
                                            array.getJSONObject(i).getString("ContactName"),
                                            array.getJSONObject(i).getString("ContactName"),//reg_date
                                            array.getJSONObject(i).getString("ContactName"),
                                            array.getJSONObject(i).getString("ContactName"),
                                            array.getJSONObject(i).getString("ContactName"),
                                            array.getJSONObject(i).getString("ContactName"),
                                            array.getJSONObject(i).getString("ContactName"),
                                            array.getJSONObject(i).getString("ContactName"),
                                            array.getJSONObject(i).getString("ContactName"),//active_flag
                                            array.getJSONObject(i).getString("ContactName"),
                                            array.getJSONObject(i).getString("ContactName"),
                                            array.getJSONObject(i).getString("ContactName"),//created_by_name
                                            // array.getJSONObject(i).getString("dob"),
                                            array.getJSONObject(i).getString("ContactName"),
                                            array.getJSONObject(i).getString("ContactName"),
                                            array.getJSONObject(i).getString("ContactName")));

                                }
                                if (getApplication() != null) {
                                    if (adapter == null) {
                                        adapter = new AllDriversAdapter(getApplication(), actorsList);
                                        order_listview.setAdapter(adapter);
                                    } else {
                                        adapter.notifyDataSetChanged();
                                    }
                                }

                                if (actorsList.size() == 0) {
                                    norecord.setVisibility(View.VISIBLE);
                                    adapter = new AllDriversAdapter(getApplication(), actorsList);
                                    order_listview.setAdapter(adapter);

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
                headers.put("Tokenid", "" + sm.getto());
                headers.put("Userid", "" + sm.getUserID());
                headers.put("Roleid", "" + sm.getRoleID());

                return headers;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void mess(String mess)
    {
       // Snackbar snackbar = Snackbar.make(rootView, mess, Snackbar.LENGTH_LONG).setAction("Action", null);
      //  View sbView = snackbar.getView();
       // sbView.setBackgroundColor(Color.parseColor("#FA8072"));
        //snackbar.show();
    }
    public class AllDriversAdapter extends ArrayAdapter<Driver_model> {
        ArrayList<Driver_model> actorList;
        LayoutInflater vi;
        Context context;
        Activity a;
        SessionManager sm;
        ConnectionDetector cd;
        String json_save = "", load_inquiry_no = "", IsCancel = "N", status = "45", created_by = "Trukker", created_host = "", device_id = "", device_type = "M";
        int pos = 0;
        String driver_id;
        String tid = "", did = "";

        public AllDriversAdapter(Context context, ArrayList<Driver_model> objects) {
            super(context, R.layout.list_authorized, objects);
            this.context = context;
            this.vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.actorList = objects;
            sm = new SessionManager(context);
            cd = new ConnectionDetector(context);

        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            View rowView;
            final ViewHolder vh;
            if (convertView == null) {

                rowView = vi.inflate(R.layout.list_authorized, null);
                setViewHolder(rowView);
            } else {
                rowView = convertView;

            }
            vh = (ViewHolder) rowView.getTag();

            vh.btn_edit .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            vh.btn_delete .setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            vh.txt_mobileno.setText(Html.fromHtml("Mobile Number: <font color='black'>"+actorList.get(position).gettruck_body_desc()+"</font>"));
            vh.txt_emailid .setText(Html.fromHtml("Email Id: <font color='black'>"+actorList.get(position).gettruck_body_id()+"</font>"));
            vh.txt_name .setText(actorList.get(position).gettruck_subcategory());
           /* vh.btn_gps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    tid=actorList.get(position).gettruckid();
                    did=actorList.get(position).getdriver_id();
                    CustomDialogClass_gps cdd = new CustomDialogClass_gps(context);
                    cdd.setCancelable(false);
                    cdd.show();
                }
            });*/
            return rowView;

        }

        class ViewHolder {


            public TextView btn_edit,
                    btn_delete,
            txt_mobileno,
                    txt_emailid,
            txt_name;


        }

        private void setViewHolder(View rowView) {
            ViewHolder vh = new ViewHolder();

            vh.btn_edit = (TextView) rowView.findViewById(R.id.btn_edit);
            vh.btn_delete = (TextView) rowView.findViewById(R.id.btn_delete);
            vh.txt_mobileno = (TextView) rowView.findViewById(R.id.txt_mobileno);
            vh.txt_emailid = (TextView) rowView.findViewById(R.id.txt_emailid);
            vh.txt_name = (TextView) rowView.findViewById(R.id.txt_name);


            rowView.setTag(vh);

        }


    }
    @Override
    public void onResume()
    {
        super.onResume();
        mShimmerViewContainer.startShimmerAnimation();

    }
}
