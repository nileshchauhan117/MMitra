package net.simplifiedcoding.navigationdrawerexample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;

import net.simplifiedcoding.navigationdrawerexample.Activity.Dash;
import net.simplifiedcoding.navigationdrawerexample.Helper.ConnectionDetector;
import net.simplifiedcoding.navigationdrawerexample.Helper.SessionManager;
import net.simplifiedcoding.navigationdrawerexample.model.Driver_model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Activity_Current_Project extends AppCompatActivity {

    SessionManager sm;
    FloatingActionButton btn_add;
    ArrayList<Driver_model> actorsList;
    AllDriversAdapter adapter;
    ListView order_listview;
    private ShimmerFrameLayout mShimmerViewContainer;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout norecord, ll_filter, ll_refresh;
    ImageView back_btn,img_norecord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorized);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(Activity_Current_Project.this, R.color.maroon_dark));
        }
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        sm = new SessionManager(Activity_Current_Project.this);
        back_btn=(ImageView)findViewById(R.id.back_btn);
        img_norecord=(ImageView)findViewById(R.id.img_norecord);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_add=(FloatingActionButton)findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //Intent i=new Intent(getApplicationContext(),Activity_Add_Athorized.class);
                //startActivity(i);
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


        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://sighteat.com/imitra/api/selection/getDashboardInfo/",
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
                                img_norecord.setVisibility(View.GONE);
                                String json=jsonObject.getString("DashboardData");
                                JSONObject jsonObject1 = new JSONObject(json);
                                JSONArray array = new JSONArray();
                                array = jsonObject1.getJSONArray("Projects");

                                if(array.length()==0)
                                {
                                    img_norecord.setVisibility(View.VISIBLE);
                                    order_listview.setVisibility(View.GONE);
                                }


                                for (int i = 0; i < array.length(); i++) {

                                    actorsList.add(new Driver_model(
                                            array.getJSONObject(i).getString("ProjectName"),
                                            array.getJSONObject(i).getString("ProjectType"),
                                            array.getJSONObject(i).getString("ProjectID"),
                                            array.getJSONObject(i).getString("ProjectID"),
                                            array.getJSONObject(i).getString("ProjectID"),
                                            array.getJSONObject(i).getString("ProjectID"),
                                            array.getJSONObject(i).getString("ProjectID"),
                                            array.getJSONObject(i).getString("ProjectID"),
                                            array.getJSONObject(i).getString("ProjectID"),//reg_date
                                            array.getJSONObject(i).getString("ProjectID"),
                                            array.getJSONObject(i).getString("ProjectID"),
                                            array.getJSONObject(i).getString("ProjectID"),
                                            array.getJSONObject(i).getString("ProjectID"),
                                            array.getJSONObject(i).getString("ProjectID"),
                                            array.getJSONObject(i).getString("ProjectID"),
                                            array.getJSONObject(i).getString("ProjectID"),//active_flag
                                            array.getJSONObject(i).getString("ProjectID"),
                                            array.getJSONObject(i).getString("ProjectID"),
                                            array.getJSONObject(i).getString("ProjectID"),//created_by_name
                                            // array.getJSONObject(i).getString("dob"),
                                            array.getJSONObject(i).getString("ProjectID"),
                                            array.getJSONObject(i).getString("ProjectID"),
                                            array.getJSONObject(i).getString("Confirmation")));

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
                            else
                            {
                                img_norecord.setVisibility(View.VISIBLE);
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
                headers.put("Tokenid", "" + sm.getto());
                headers.put("Userid", "" + sm.getUserID());
                headers.put("Roleid", "" + sm.getRoleID());
                headers.put("Relationid","1");

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
            super(context, R.layout.list_project_dashboard, objects);
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

                rowView = vi.inflate(R.layout.list_project_dashboard, null);
                setViewHolder(rowView);
            } else {
                rowView = convertView;

            }
            vh = (ViewHolder) rowView.getTag();


            vh.tv_proname.setText(Html.fromHtml(actorList.get(position).gettruck_subcategory()));
            vh.tv_proname1 .setText(Html.fromHtml("("+actorList.get(position).gettruck_body_desc()+")"));
            //  vh.tv_proname2.setText(Html.fromHtml(actorList.get(position).gettruck_));

            vh.card_view.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {

                    if(sm.getloginid().equalsIgnoreCase("Allottee"))
                    {
                        Intent i=new Intent(getContext(), Allotta_list.class);
                        i.putExtra("ProjectID",actorList.get(position).getgps());
                        i.putExtra("page","1");
                        i.putExtra("pname",actorList.get(position).gettruck_subcategory());
                        i.putExtra("Confirmation",actorList.get(position).getdob());
                        startActivity(i);
                    }
                    else{
                        Intent i=new Intent(getContext(), Dash.class);
                        i.putExtra("ProjectID",actorList.get(position).getgps());
                        i.putExtra("page","4");
                        i.putExtra("pname",actorList.get(position).gettruck_subcategory());
                        i.putExtra("Confirmation",actorList.get(position).getdob());
                        startActivity(i);
                    }



                }
            });

            return rowView;

        }

        class ViewHolder {



            public TextView tv_proname,tv_proname1;
            //    txt_status,

            LinearLayout card_view;


        }

        private void setViewHolder(View rowView) {
            ViewHolder vh = new ViewHolder();

            vh.tv_proname = (TextView) rowView.findViewById(R.id.tv_proname);

            vh.card_view=(LinearLayout)rowView.findViewById(R.id.card_view);


            vh.tv_proname1= (TextView) rowView.findViewById(R.id.tv_proname1);

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