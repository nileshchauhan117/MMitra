package com.MilkatMitra.App.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.MilkatMitra.App.Helper.Constants;
import com.MilkatMitra.App.Helper.SessionManager;
import com.MilkatMitra.App.R;
import com.MilkatMitra.App.webview;
import com.MilkatMitra.App.webview_pdf;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Activity_InventoryCost extends AppCompatActivity {

    ImageView back_btn;
    TextView t_one,
            t_two,
    t_three,
            t_four,
    t_five;
String TypeID,ProjectID="",page="";
LinearLayout layout_BlockandInventory;
    SessionManager sm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventorycost);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(Activity_InventoryCost.this, R.color.maroon_dark));
        }
        sm = new SessionManager(Activity_InventoryCost.this);
        Intent intent = getIntent();
        ProjectID = intent.getStringExtra("ProjectID");
        page = intent.getStringExtra("page");
        back_btn=(ImageView)findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        t_one=(TextView)findViewById(R.id.t_one) ;
                t_two=(TextView)findViewById(R.id.t_two) ;
        t_three=(TextView)findViewById(R.id.t_three) ;
                t_four=(TextView)findViewById(R.id.t_four) ;
        t_five=(TextView)findViewById(R.id.t_five) ;
        layout_BlockandInventory=(LinearLayout)findViewById(R.id.layout_BlockandInventory);
        loginUser();
        t_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


            }
        });
        t_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                Intent i=new Intent(getApplicationContext(), Activity_ConstructionShedule.class);
                i.putExtra("ProjectID",ProjectID);
                i.putExtra("page","4");
                startActivity(i);

            }
        });
        t_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();
                Intent i=new Intent(getApplicationContext(), Activity_ConstructionCost.class);
                i.putExtra("ProjectID",ProjectID);
                i.putExtra("page","4");
                startActivity(i);
            }
        });

        t_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                Intent i=new Intent(getApplicationContext(), Activity_landcost.class);
                i.putExtra("ProjectID",ProjectID);
                i.putExtra("page","4");
                startActivity(i);
            }
        });

        t_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                Intent i=new Intent(getApplicationContext(), Activity_othercost.class);
                i.putExtra("ProjectID",ProjectID);
                i.putExtra("page","4");
                startActivity(i);
            }
        });


    }
    private void loginUser()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL+"selection/getProjectList/{\"mode\":\"list\",\"ProjectID\":\""+ProjectID+"\",\"Screen\":\"ProjectConsultants\"}",
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {

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
                                TypeID= array.getJSONObject(i).getString("TypeID");



                                try
                                {
                                    String Blocks=array.getJSONObject(i).getString("Blocks");
                                    final JSONArray Blocksa =  new JSONArray(Blocks);

                                    for(int h=0; h<Blocksa.length(); h++)
                                    {

                                        LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        final View addView = layoutInflater.inflate(R.layout.list_block, null);

                                        TextView tv_desc = (TextView)addView.findViewById(R.id.tv_bname);
                                        ImageView img_block = (ImageView) addView.findViewById(R.id.img_block);
                                        CardView card_view= (CardView) addView.findViewById(R.id.card_view);

                                        final String BlockPlan=Blocksa.getJSONObject(h).getString("BlockPlan");
                                        JSONArray BlockPlana =  new JSONArray(BlockPlan);

                                        tv_desc.setText(Html.fromHtml(Blocksa.getJSONObject(h).getString("BlockName")));
                                        try
                                        {
                                            Picasso.with(getApplicationContext()).load(BlockPlana.getJSONObject(0).getString("FileURL")).into(img_block);

                                        }
                                        catch (Exception e)
                                        {

                                        }
                                        final int h1=h;
                                        card_view.setOnClickListener(new View.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(View v)
                                            {
                                                try
                                                {
                                                    CustomDialogClass cdd=new CustomDialogClass(Activity_InventoryCost.this,Blocksa.get(h1)+"");
                                                    cdd.show();

                                                }
                                                catch (JSONException e)
                                                {
                                                    e.printStackTrace();
                                                }

                                                // Toast.makeText(getApplicationContext(),"ssss",Toast.LENGTH_LONG).show();
                                            }
                                        });


                                        layout_BlockandInventory.addView(addView);
                                    }

                                }
                                catch (Exception e)
                                {

                                }



                              /*  tv_d_noofgarage.setText(Html.fromHtml("No Of Garage : <font color='black'>"+array.getJSONObject(i).getString("NoOfGarage")));
                                tv_d_agarage.setText(Html.fromHtml("Area of Garage (Sq Mtr) : <font color='black'>"+array.getJSONObject(i).getString("GarageArea")+" ₹"));
                                tv_d_noofoparking.setText(Html.fromHtml("No of Open Parking: <font color='black'>"+array.getJSONObject(i).getString("OpenParkings")));
                                tv_d_oparking.setText(Html.fromHtml("Area of Open Parking (Sq Mtr) : <font color='black'>"+array.getJSONObject(i).getString("OpenParkingArea")+" ₹"));
                                tv_d_noofparking.setText(Html.fromHtml("No of Covered Parking : <font color='black'>"+array.getJSONObject(i).getString("CoveredParkings")));
                                tv_d_aparking.setText(Html.fromHtml("Area of Covered Parking (Sq Mtr) : <font color='black'>"+array.getJSONObject(i).getString("CoveredParkingArea")+" ₹"));*/

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
    public class CustomDialogClass extends Dialog implements
            android.view.View.OnClickListener {

        public Activity c;
        public Dialog d;
        String value;
        TextView tv_b_name,
                tv_b_unit,
                tv_b_shop,
                tv_b_offices,
                tv_b_ploat,
                tv_b_other,
                tv_b_totalu;
        LinearLayout tv_b_totalu_layout,
        tv_b_other_layout,
                tv_b_ploat_layout,
        tv_b_offices_layout,
                tv_b_shop_layout,
        tv_b_unit_layout;
        ImageView image_view,
                image_view1;
        RelativeLayout rel_view_image;
        SubsamplingScaleImageView img_full;
        TextView btn_close;

        public CustomDialogClass(Activity a,String value)
        {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
            this.value = value;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.list_block_sub);
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            // Toast.makeText(getContext(),value+"",Toast.LENGTH_LONG).show();
            rel_view_image = (RelativeLayout) findViewById(R.id.rel_view_image);
            img_full = (SubsamplingScaleImageView) findViewById(R.id.img_full);
            tv_b_totalu_layout= (LinearLayout) findViewById(R.id.tv_b_totalu_layout);
                    tv_b_other_layout= (LinearLayout) findViewById(R.id.tv_b_other_layout);
            tv_b_ploat_layout= (LinearLayout) findViewById(R.id.tv_b_ploat_layout);
                    tv_b_offices_layout= (LinearLayout) findViewById(R.id.tv_b_offices_layout);
            tv_b_shop_layout= (LinearLayout) findViewById(R.id.tv_b_shop_layout);
                    tv_b_unit_layout= (LinearLayout) findViewById(R.id.tv_b_unit_layout);
            btn_close = (TextView) findViewById(R.id.btn_close);
            tv_b_name=(TextView)findViewById(R.id.tv_b_name) ;
            tv_b_unit=(TextView)findViewById(R.id.tv_b_unit) ;
            tv_b_shop=(TextView)findViewById(R.id.tv_b_shop) ;
            tv_b_offices=(TextView)findViewById(R.id.tv_b_offices) ;
            tv_b_ploat=(TextView)findViewById(R.id.tv_b_ploat) ;
            tv_b_other=(TextView)findViewById(R.id.tv_b_other) ;
            tv_b_totalu=(TextView)findViewById(R.id.tv_b_totalu) ;
            image_view=(ImageView) findViewById(R.id.image_view) ;
            image_view1=(ImageView) findViewById(R.id.image_view1) ;



         /*   image_view1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    try
                    {
                        rel_view_image.setVisibility(View.VISIBLE);
                        Bitmap bitmap = ((BitmapDrawable)image_view1.getDrawable()).getBitmap();
                        img_full.setImage(ImageSource.bitmap(bitmap));

                    }
                    catch (Exception e)
                    {

                    }
                    btn_close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rel_view_image.setVisibility(View.GONE);
                        }
                    });
                    rel_view_image.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            rel_view_image.setVisibility(View.GONE);
                        }
                    });

                }
            });*/

            try
            {
                JSONObject jsonObject = new JSONObject(value);


                final String BlockPlan=jsonObject.getString("BlockPlan");
                final JSONArray BlockPlana =  new JSONArray(BlockPlan);

                final String RajaChitthi=jsonObject.getString("RajaChitthi");
                final JSONArray RajaChitthia =  new JSONArray(RajaChitthi);
                for(int h=0; h<BlockPlana.length(); h++)
                {

                    if(BlockPlana.getJSONObject(h).getString("FileURL").contains(".pdf"))
                    {
                        image_view1.setBackgroundResource(R.drawable.pdf_link);
                    }
                    else
                        Picasso.with(getApplicationContext()).load(BlockPlana.getJSONObject(h).getString("FileURL")).placeholder(getResources().getDrawable(R.drawable.noi)).error(getResources().getDrawable(R.drawable.noi)).into(image_view1);

                }
                image_view1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        try
                        {
                            if(BlockPlana.getJSONObject(0).getString("FileURL").equalsIgnoreCase(""))
                            {
                                Toast.makeText(getApplicationContext(),"No Image found",Toast.LENGTH_LONG).show();
                            }
                            else if(BlockPlana.getJSONObject(0).getString("FileURL").contains(".pdf"))
                            {
                                Intent i=new Intent(getApplicationContext(), webview_pdf.class);
                                i.putExtra("url",BlockPlana.getJSONObject(0).getString("FileURL"));
                                startActivity(i);
                            }
                            else
                            {
                                Intent i=new Intent(getApplicationContext(), webview.class);
                                i.putExtra("url",BlockPlana.getJSONObject(0).getString("FileURL"));
                                startActivity(i);
                            }

                        } catch (JSONException e)
                        {
                            Toast.makeText(getApplicationContext(),"No Image found",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                });
                for(int h=0; h<RajaChitthia.length(); h++)
                {

                    if(RajaChitthia.getJSONObject(h).getString("FileURL").contains(".pdf"))
                    {
                        image_view.setBackgroundResource(R.drawable.pdf_link);
                    }
                    else
                        Picasso.with(getApplicationContext()).load(RajaChitthia.getJSONObject(h).getString("FileURL")).placeholder(getResources().getDrawable(R.drawable.noi)).error(getResources().getDrawable(R.drawable.noi)).into(image_view);


                }
                image_view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        try
                        {
                            if(RajaChitthia.getJSONObject(0).getString("FileURL").equalsIgnoreCase(""))
                            {
                                Toast.makeText(getApplicationContext(),"No Image found",Toast.LENGTH_LONG).show();
                            }
                            else if(RajaChitthia.getJSONObject(0).getString("FileURL").contains(".pdf"))
                            {
                                Intent i=new Intent(getApplicationContext(),webview_pdf.class);
                                i.putExtra("url",RajaChitthia.getJSONObject(0).getString("FileURL"));
                                startActivity(i);
                            }
                            else
                            {
                                Intent i=new Intent(getApplicationContext(),webview.class);
                                i.putExtra("url",RajaChitthia.getJSONObject(0).getString("FileURL"));
                                startActivity(i);
                            }

                        } catch (JSONException e) {

                            Toast.makeText(getApplicationContext(),"No Image found",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                });

                tv_b_name.setText("Block : "+jsonObject.getString("BlockName"));
                if(jsonObject.getString("UnitResidential").equalsIgnoreCase("null"))
                    tv_b_unit.setText(""+"-");
                else
                    tv_b_unit.setText(""+jsonObject.getString("UnitResidential"));

                if(jsonObject.getString("UnitShop").equalsIgnoreCase("null"))
                    tv_b_shop.setText(""+"-");
                else
                    tv_b_shop.setText(""+jsonObject.getString("UnitShop"));

                if(jsonObject.getString("UnitOffice").equalsIgnoreCase("null"))
                    tv_b_offices.setText(""+"-");
                else
                    tv_b_offices.setText(""+jsonObject.getString("UnitOffice"));

                if(jsonObject.getString("UnitPlot").equalsIgnoreCase("null"))
                    tv_b_ploat.setText(""+"-");
                else
                    tv_b_ploat.setText(""+jsonObject.getString("UnitPlot"));

                if(jsonObject.getString("UnitOther").equalsIgnoreCase("null"))
                    tv_b_other.setText(""+"-");
                else
                    tv_b_other.setText(""+jsonObject.getString("UnitOther"));

                if(jsonObject.getString("TotalUnits").equalsIgnoreCase("null"))
                    tv_b_totalu.setText(""+"-");
                else
                    tv_b_totalu.setText(""+jsonObject.getString("TotalUnits"));

                tv_b_unit.setVisibility(View.GONE);
                tv_b_shop.setVisibility(View.GONE);
                tv_b_offices.setVisibility(View.GONE);
                tv_b_ploat.setVisibility(View.GONE);
                tv_b_other.setVisibility(View.GONE);
                tv_b_totalu.setVisibility(View.GONE);

                tv_b_totalu_layout.setVisibility(View.GONE);
                        tv_b_other_layout.setVisibility(View.GONE);
                tv_b_ploat_layout.setVisibility(View.GONE);
                        tv_b_offices_layout.setVisibility(View.GONE);
                tv_b_shop_layout.setVisibility(View.GONE);
                        tv_b_unit_layout.setVisibility(View.GONE);

                if(TypeID.equalsIgnoreCase("2") ||TypeID.equalsIgnoreCase("3"))
                {
                    tv_b_unit.setVisibility(View.VISIBLE);
                    tv_b_totalu.setVisibility(View.VISIBLE);
                    tv_b_unit_layout.setVisibility(View.VISIBLE);
                    tv_b_totalu_layout.setVisibility(View.VISIBLE);

                }
                if(TypeID.equalsIgnoreCase("1") ||TypeID.equalsIgnoreCase("3"))
                {
                    tv_b_shop.setVisibility(View.VISIBLE);
                    tv_b_offices.setVisibility(View.VISIBLE);
                    tv_b_shop_layout.setVisibility(View.VISIBLE);
                    tv_b_offices_layout.setVisibility(View.VISIBLE);
                }
                if(TypeID.equalsIgnoreCase("4") ||TypeID.equalsIgnoreCase("3"))
                {
                    tv_b_ploat.setVisibility(View.VISIBLE);
                    tv_b_totalu.setVisibility(View.VISIBLE);
                    tv_b_ploat_layout.setVisibility(View.VISIBLE);
                    tv_b_totalu_layout.setVisibility(View.VISIBLE);
                }
                if(TypeID.equalsIgnoreCase("3") )
                {
                    tv_b_other.setVisibility(View.VISIBLE);
                    tv_b_other_layout.setVisibility(View.VISIBLE);

                }


            }
            catch (Exception e)
            {

            }


        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {

                default:
                    break;
            }
            dismiss();
        }

    }
}
