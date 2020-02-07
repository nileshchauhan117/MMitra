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

public class MyPromoter extends AppCompatActivity {

    SessionManager sm;
    FloatingActionButton btn_add;
    ArrayList<Driver_model> actorsList;
    AllDriversAdapter adapter;
    ListView order_listview;
    private ShimmerFrameLayout mShimmerViewContainer;
    SwipeRefreshLayout swipeRefreshLayout;
    LinearLayout norecord, ll_filter, ll_refresh;
    ImageView back_btn;
    ImageView img_norecord;
    LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promoter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(MyPromoter.this, R.color.maroon_dark));
        }
        container = (LinearLayout)findViewById(R.id.container3);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        sm = new SessionManager(MyPromoter.this);
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
        profile();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.simpleSwipeRefreshLayout);
        btn_add = (FloatingActionButton) findViewById(R.id.btn_add);
        order_listview = (ListView) findViewById(R.id.listView_orderd);

        //swipeRefreshLayout = (SwipeRefreshLayout)rootView. findViewById(R.id.swipe_refresh_layout);
        order_listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        norecord = (LinearLayout) findViewById(R.id.norecord);

        actorsList = new ArrayList<Driver_model>();


    }
    private void profile()
    {



        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://sighteat.com/imitra/api/selection/getUserProfile/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {

                        mShimmerViewContainer.stopShimmerAnimation();
                        mShimmerViewContainer.setVisibility(View.GONE);

                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("Code").equals("200"))
                            {
                                mShimmerViewContainer.stopShimmerAnimation();
                                mShimmerViewContainer.setVisibility(View.GONE);

                                JSONObject object = jsonObject.getJSONObject("ProfileData");
                                String UserID = object.getString("UserID");
                                String RoleID = object.getString("RoleID");
                                String RelationID = "";
                                img_norecord.setVisibility(View.GONE);
                                String Promoters = object.getString("Promoters");

                                JSONArray j=new JSONArray(Promoters);

                                if(j.length()==0)
                                {
                                    img_norecord.setVisibility(View.VISIBLE);
                                }

                                for(int i=0;i<=j.length();i++)
                                {
                                    LayoutInflater layoutInflater =
                                            (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    final View addView = layoutInflater.inflate(R.layout.list_promoter, null);
                                    TextView textOut = (TextView)addView.findViewById(R.id.tv_driver_name);
                                    textOut.setText(j.getJSONObject(i).getString("PromoterName"));
                                    LinearLayout layout_main=(LinearLayout) addView.findViewById(R.id.layout_main);
                                    layout_main.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            Intent i1 = new Intent(getApplicationContext(), MainActivity_Ca.class);
                                            i1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                            startActivity(i1);
                                            finish();

                                        }
                                    });
                                    container.addView(addView);
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

                        // Toast.makeText(Activity_Login.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                        mShimmerViewContainer.stopShimmerAnimation();
                        mShimmerViewContainer.setVisibility(View.GONE);
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
            super(context, R.layout.list_promoter, objects);
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

                rowView = vi.inflate(R.layout.list_promoter, null);
                setViewHolder(rowView);
            } else {
                rowView = convertView;

            }
            vh = (ViewHolder) rowView.getTag();


            vh.tv_driver_name.setText(Html.fromHtml(actorList.get(position).gettruck_subcategory()));
            vh.tv_type.setText(Html.fromHtml(actorList.get(position).gettruck_subcategory()));
            vh.tv_mobile_no.setText(Html.fromHtml(actorList.get(position).gettruck_subcategory()));
            vh.tv_emaild.setText(Html.fromHtml(actorList.get(position).gettruck_subcategory()));

            return rowView;

        }

        class ViewHolder {



            public TextView tv_driver_name,
            tv_type,
                    tv_mobile_no,
            tv_emaild;
            //    txt_status,

           // LinearLayout card_view;


        }

        private void setViewHolder(View rowView) {
            ViewHolder vh = new ViewHolder();

            vh.tv_driver_name = (TextView) rowView.findViewById(R.id.tv_driver_name);
            vh.tv_emaild = (TextView) rowView.findViewById(R.id.tv_emaild);
            vh.tv_type = (TextView) rowView.findViewById(R.id.tv_type);
            vh.tv_mobile_no= (TextView) rowView.findViewById(R.id.tv_mobile_no);

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