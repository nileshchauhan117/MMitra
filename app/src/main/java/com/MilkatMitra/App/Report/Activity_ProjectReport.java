package com.MilkatMitra.App.Report;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.MilkatMitra.App.Activity_Agent;
import com.MilkatMitra.App.Helper.Constants;
import com.MilkatMitra.App.Helper.SessionManager;
import com.MilkatMitra.App.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Activity_ProjectReport extends AppCompatActivity {

    SessionManager sm;
    ProgressBar progressdisplay_layout;
    LinearLayout container_dis;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projectreport);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(Activity_ProjectReport.this, R.color.maroon_dark));
        }
        progressdisplay_layout=(ProgressBar) findViewById(R.id.progressBar2);
        container_dis=(LinearLayout)findViewById(R.id.container_dis);
        sm = new SessionManager(Activity_ProjectReport.this);
        back=(ImageView)findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        loginUser();
    }
    private void loginUser()
    {
        progressdisplay_layout.setVisibility(View.VISIBLE);
      String  url =Constants.URL+"selection/getProjectReport/{\"Status\":\"" + "Ongoing" + "\"}";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {


                        progressdisplay_layout.setVisibility(View.GONE);
                        container_dis.removeAllViews();
                        // Toast.makeText(Activity_Authorized.this,response,Toast.LENGTH_LONG).show();
                        Log.e("-----------",response+"");
                        try
                        {

                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("Code").equals("200"))
                            {
                                // layout_f1.setVisibility(View.VISIBLE);
                                JSONArray array1 = new JSONArray();

                                array1 = jsonObject.getJSONArray("Projects");

                                // JSONObject B = Form1.getJSONObject("B");
                                //JSONArray Pointsb = B.getJSONArray("Points");
                                //JSONArray Blocksb = B.getJSONArray("Blocks");

                                // arr_data = new ArrayList<>();
                                //arr_data_Final = new ArrayList<>();
                                //arr_data_Final_pdf = new ArrayList<>();

                                String[]   arr_headers_all = new String[]{"ProjectName", "TotalInventory", "BookedInventory","ReceivedAmount","QuarterNumber","QPRDueDate"};
                                String[]    arr_key = new String[]{"ProjectName", "TotalInventory", "BookedInventory","ReceivedAmount","QuarterNumber","QPRDueDate"};



                                for (int i = 0; i < array1.length(); i++)
                                {


                                    ///////////////////////
                                    LayoutInflater layoutInflater =
                                            (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    final View addView = layoutInflater.inflate(R.layout.list_projectreport, null);
                                    TextView tv1 = (TextView)addView.findViewById(R.id.tv1);
                                    TextView tv2 = (TextView)addView.findViewById(R.id.tv2);
                                    TextView tv3 = (TextView)addView.findViewById(R.id.tv3);
                                    TextView tv4 = (TextView)addView.findViewById(R.id.tv4);
                                    TextView tv5 = (TextView)addView.findViewById(R.id.tv5);
                                    TextView tv6 = (TextView)addView.findViewById(R.id.tv6);
                                    CardView item_driver_row=(CardView)addView.findViewById(R.id.item_driver_row);


                                    tv1.setText(": "+array1.getJSONObject(i).getString(arr_key[0]).toString());
                                    tv2.setText(": "+array1.getJSONObject(i).getString(arr_key[1]).toString());
                                    tv3.setText(": "+array1.getJSONObject(i).getString(arr_key[2]).toString());
                                    tv4.setText(": ₹"+array1.getJSONObject(i).getString(arr_key[3]).toString());
                                    tv5.setText(": "+array1.getJSONObject(i).getString(arr_key[4]).toString());
                                    tv6.setText(": "+array1.getJSONObject(i).getString(arr_key[5]).toString());

                                    final String aid=array1.getJSONObject(i).getString("ProjectID").toString();
                                    final String name=array1.getJSONObject(i).getString(arr_key[0]).toString();
                                    item_driver_row.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v)
                                        {
                                            Intent i6=new Intent(getApplicationContext(), Activity_SummaryProjects.class);
                                            i6.putExtra("ProjectID",aid);
                                            i6.putExtra("ProjectName",name);
                                            startActivity(i6);
                                            //Toast.makeText(getApplicationContext(),aid,Toast.LENGTH_LONG).show();
                                        }
                                    });

                                    container_dis.addView(addView);
                                    ////////////////////////



                                    // Points.getJSONObject(h).getString("Name");
                                   /* HashMap<String, String> hmap = new HashMap<>();
                                    HashMap<String, String> hmap1 = new HashMap<>();

                                    for (int j = 0; j < arr_headers_all.length; j++)
                                    {

                                        if(array1.getJSONObject(i).has(arr_key[j]))
                                        {

                                            hmap.put(arr_headers_all[j], array1.getJSONObject(i).getString(arr_key[j]).toString());
                                        }
                                        else
                                        {


                                        }

                                    }
                                    // arr_data_Final_pdf.add(hmap1);
                                    arr_data.add(hmap);*/
                                    // arr_data_Final.add(hmap);
                                }
                                // if(arr_data.size() > 0)
                                // {
                                //     rel_report_table.setVisibility(View.VISIBLE);
                                //  }
                                // CreateHeader(false);
                                //AddedTableData(false);



                            }
                            else
                            {
                                mess("No Data Found");
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
                        // mess(error.toString());
                    }
                }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {


                HashMap<String, String> headers = new HashMap<String, String>();

                headers.put("Host", Constants.Host);
                headers.put("Language", "" + Constants.Language);
                headers.put("Ipaddress", "" + Constants.Ipaddress);
                headers.put("Authorization", "" + Constants.Authorization);
                headers.put("Devicetype", "" + Constants.Devicetype);
                headers.put("Timestamp", "" + Constants.Timestamp);
                headers.put("Deviceid", "" + Constants.Deviceid);
                headers.put("Clientcode", "" + Constants.Clientcode);
                headers.put("Relationid",sm.getRelationID());
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
        Snackbar snackbar = Snackbar.make(back, mess, Snackbar.LENGTH_LONG).setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.parseColor("#FA8072"));
        snackbar.show();
    }
}