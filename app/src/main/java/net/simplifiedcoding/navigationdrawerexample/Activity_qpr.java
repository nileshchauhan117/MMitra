package net.simplifiedcoding.navigationdrawerexample;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.GridView;
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

import net.simplifiedcoding.navigationdrawerexample.Activity.Dash;
import net.simplifiedcoding.navigationdrawerexample.Activity.Dash_QprtList;
import net.simplifiedcoding.navigationdrawerexample.Helper.Constants;
import net.simplifiedcoding.navigationdrawerexample.Helper.SessionManager;
import net.simplifiedcoding.navigationdrawerexample.model.CustomAdapter;
import net.simplifiedcoding.navigationdrawerexample.model.ItemObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity_qpr extends AppCompatActivity
{

    ImageView back_btn;
    TextView tv_usernames,tv_emails;
    SessionManager sm;
    LinearLayout layout_Quarter;
    String ProjectID="",page="";
    ProgressBar progressBar_main;
    GridView gridview;
     List<ItemObject> allItems;
     ImageView img_norecord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qpr);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(Activity_qpr.this, R.color.maroon_dark));
        }
        progressBar_main=(ProgressBar) findViewById(R.id.progressBar2);
        sm = new SessionManager(Activity_qpr.this);
         gridview = (GridView) findViewById(R.id.gridview);
        img_norecord= (ImageView) findViewById(R.id.img_norecord);
        img_norecord.setVisibility(View.GONE);
        Intent intent = getIntent();
        ProjectID = intent.getStringExtra("ProjectID");
        page = intent.getStringExtra("page");
        layout_Quarter=(LinearLayout)findViewById(R.id.layout_Quarter);
        back_btn=(ImageView)findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Development();
    }
    private void Development()
    {
        progressBar_main.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://sighteat.com/imitra/api/selection/getProjectList/{\"mode\":\"list\",\"ProjectID\":\""+ProjectID+"\",\"Screen\":\"QPR\"}",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {

                        progressBar_main.setVisibility(View.GONE);
                        // Toast.makeText(Activity_Authorized.this,response,Toast.LENGTH_LONG).show();
                        Log.e("-----------",response+"");
                        try
                        {

                            JSONObject jsonObject = new JSONObject(response);
                            if(jsonObject.getString("Code").equals("200"))
                            {
                                JSONArray array = new JSONArray();
                                array = jsonObject.getJSONArray("ProjectDetail");
                                int i=0;
                               // TypeID= array.getJSONObject(i).getString("TypeID");

                                try
                                {
                                    String ExternalDevelopment=array.getJSONObject(i).getString("Quarter");
                                    JSONObject jj=new JSONObject(ExternalDevelopment);
                                    String List=jj.getString("List");



                                    final JSONArray ExternalDevelopmenta =  new JSONArray(List);
                                    final List<ItemObject> items = new ArrayList<>();
                                    if(ExternalDevelopmenta.length()==0)
                                    {
                                        img_norecord.setVisibility(View.VISIBLE);
                                        gridview.setVisibility(View.GONE);
                                    }

                                    for(int h=0; h<ExternalDevelopmenta.length(); h++)
                                    {

                                        LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        final View addView = layoutInflater.inflate(R.layout.list_quater, null);
                                        LinearLayout card_view = (LinearLayout)addView.findViewById(R.id.card_view);
                                        TextView txt_startdate = (TextView)addView.findViewById(R.id.txt_startdate);
                                      final  TextView txt_name = (TextView)addView.findViewById(R.id.txt_name);
                                        final TextView txt_n= (TextView)addView.findViewById(R.id.txt_n);
                                        TextView txt_enddate = (TextView)addView.findViewById(R.id.txt_enddate);
                                        LinearLayout layout_color= (LinearLayout) addView.findViewById(R.id.layout_color);
                                        txt_n.setText(ExternalDevelopmenta.getJSONObject(h).getString("QPRID"));
                                        if(ExternalDevelopmenta.getJSONObject(h).getString("IsActive").equalsIgnoreCase("Y"))
                                        {
                                            layout_color.setBackgroundResource(R.color.blue_back);
                                            txt_startdate.setTextColor(getResources().getColor(R.color.blue));
                                            txt_name.setTextColor(getResources().getColor(R.color.blue));
                                            txt_enddate.setTextColor(getResources().getColor(R.color.blue));
                                        }
                                        else if(ExternalDevelopmenta.getJSONObject(h).getString("IsActive").equalsIgnoreCase("P"))
                                        {
                                            txt_startdate.setTextColor(getResources().getColor(R.color.deeppink));
                                            txt_enddate.setTextColor(getResources().getColor(R.color.deeppink));
                                            txt_name.setTextColor(getResources().getColor(R.color.deeppink));
                                            layout_color.setBackgroundResource(R.color.maroon_tra);


                                        }
                                        else
                                        {
                                            layout_color.setBackgroundResource(R.color.blue_t);
                                            txt_startdate.setTextColor(getResources().getColor(R.color.cadetblue));
                                            txt_enddate.setTextColor(getResources().getColor(R.color.cadetblue));
                                            txt_name.setTextColor(getResources().getColor(R.color.cadetblue));



                                        }

                                        txt_startdate.setText(Html.fromHtml("From "+ExternalDevelopmenta.getJSONObject(h).getString("StartDate")));
                                        txt_enddate.setText(Html.fromHtml(" - To "+ExternalDevelopmenta.getJSONObject(h).getString("EndDate")));
                                        //txt_name.setText(Html.fromHtml(ExternalDevelopmenta.getJSONObject(h).getString("QPRID")));
                                        txt_name.setText(Html.fromHtml("Quater#"+ExternalDevelopmenta.getJSONObject(h).getString("QPRID")));

                                        String dt="From "+ExternalDevelopmenta.getJSONObject(h).getString("StartDate")+" - To "+ExternalDevelopmenta.getJSONObject(h).getString("EndDate");
                                        String f=ExternalDevelopmenta.getJSONObject(h).getString("IsActive");

                                        items.add(new ItemObject(dt, "Quater#"+ExternalDevelopmenta.getJSONObject(h).getString("QPRID"),f,txt_n.getText().toString(),ExternalDevelopmenta.getJSONObject(h).getString("Editable")));

                                        layout_Quarter.addView(addView);
                                    }



                                    CustomAdapter customAdapter = new CustomAdapter(Activity_qpr.this, items);

                                    gridview.setAdapter(customAdapter);

                                    gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
                                        {
                                            String name=items.get(position).getImageResource();
                                            String f=items.get(position).getf();
                                            String e=items.get(position).gete();
                                            Constants.qprid=items.get(position).getg();

                                            if(sm.getloginid().equalsIgnoreCase("promoter"))
                                            {
                                                Intent i=new Intent(getApplicationContext(), Dash_QprtList.class);
                                                i.putExtra("title",name);
                                                i.putExtra("page","4");
                                                i.putExtra("ProjectID",ProjectID);
                                                startActivity(i);
                                            }
                                            else
                                            {

                                                if(f.equalsIgnoreCase("P") || f.equalsIgnoreCase("A"))
                                                {

                                                    if(e.equalsIgnoreCase("Y"))
                                                    {
                                                        if(sm.getloginid().equalsIgnoreCase("architects"))
                                                        {
                                                            Intent i=new Intent(getApplicationContext(),Activity_Form.class);
                                                            i.putExtra("title",name);
                                                            i.putExtra("page","4");
                                                            i.putExtra("ProjectID",ProjectID);
                                                            startActivity(i);
                                                        }
                                                        else if(sm.getloginid().equalsIgnoreCase("engineer"))
                                                        {
                                                            Intent i=new Intent(getApplicationContext(),Activity_Form2.class);
                                                            i.putExtra("title",name);
                                                            i.putExtra("page","4");
                                                            i.putExtra("ProjectID",ProjectID);
                                                            startActivity(i);
                                                        }
                                                        else if(sm.getloginid().equalsIgnoreCase("Chartered Accountant"))
                                                        {
                                                            Intent i=new Intent(getApplicationContext(),Activity_Form3.class);
                                                            i.putExtra("title",name);
                                                            i.putExtra("page","4");
                                                            i.putExtra("ProjectID",ProjectID);
                                                            startActivity(i);
                                                        }
                                                        else
                                                        {

                                                            Intent i=new Intent(getApplicationContext(), Dash_QprtList.class);
                                                            i.putExtra("ProjectID",ProjectID);
                                                            i.putExtra("page","4");
                                                            i.putExtra("title",name);
                                                            startActivity(i);
                                                        }


                                                    }
                                                    else
                                                    {
                                                       String path="http://sighteat.com/imitra/api/pdf/form1.php?project=MQ==&qpr=MQ==";
                                                      /*  Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                                                        pdfIntent.setDataAndType(Uri.parse(path), "application/pdf");
                                                        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                                        try
                                                        {
                                                            startActivity(pdfIntent);
                                                        }
                                                        catch(ActivityNotFoundException e1)
                                                        {
                                                            Toast.makeText(Activity_qpr.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
                                                        }*/

                                                          Intent i=new Intent(getApplicationContext(), pdffile.class);
                                                          i.putExtra("url","http://sighteat.com/imitra/api/pdf/form1.php?project=MQ==&qpr=MQ==");
                                                          startActivity(i);

                                                       // Intent i=new Intent(getApplicationContext(), web.class);
                                                        //i.putExtra("url","http://sighteat.com/imitra/api/pdf/form1.php?project=MQ==&qpr=MQ==");
                                                        //startActivity(i);
                                                       // Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://sighteat.com//imitra//api//pdf//form2.php?project=MQ==&qpr=NA=="));
                                                       // startActivity(browserIntent);
                                                        //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://sighteat.com/imitra/api/pdf/form1.php?project=MQ==&qpr=MQ=="));
                                                        //startActivity(browserIntent);


                                                    }
                                                }
                                                else
                                                {

                                                }


                                            }



                                            //Toast.makeText(Activity_qpr.this, name, Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                                catch (Exception e)
                                {

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
                        // mess(error.toString());
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