package com.MilkatMitra.App;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.squareup.picasso.Picasso;

import com.MilkatMitra.App.Helper.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Activity_consultants_main extends AppCompatActivity {

        SessionManager sm;
        TextView tv_mno,
                tv_email,
                tv_type,
                tv_add,
                tv_pan,
                tv_group;
        String TypeID="";
        ImageView img_pro;
        TextView
                tv_rdate,tv_wreg,
                tv_p_name,
                tv_p_deatils,
                tv_p_des,
                tv_p_type,
                tv_p_status,
                tv_p_sdate,
                tv_p_cdate,
                tv_p_tland,
                tv_p_carea,
                tv_p_tarea,
                tv_p_const,
                tv_p_land,
                tv_p_cost,
                tv_p_add1,
                tv_p_add2,
                tv_p_state,
                tv_p_city,
                tv_p_zip,
                tv_p_aurhority,
                tv_p_office,
                tv_p_wreg,
                tv_p_reg;
        TextView title1;

        TextView  tv_e_desc,
                tv_i_RoadSystem,
                tv_i_WaterSupply,
                tv_i_Sewage,
                tv_i_Electricity,
                tv_i_Solid;

        TextView
                tv_d_noofgarage,
                tv_d_agarage,
                tv_d_noofoparking,
                tv_d_oparking,
                tv_d_noofparking,
                tv_d_aparking;

        LinearLayout layout_authorized,layout_projecthead,
                layout_StructuralEngineers,
                layout_ProjectArchitect,
                layout_ProjectCA,layout_rera,
                layout_ProjectContractor,
                layout_ProjectAgent,layout_DevelopmentDetail,layout_ExternalDevelopmentWork,layout_BlockandInventory,layout_formA;

        LinearLayout
                one_deatils,two_deatils,
                three_deatils,
                four_deatils,
                five_deatils,
                six_deatils,
                one,
                two,
                three,
                four,
                five,
                six;

        ImageView six_arrow,five_arrow,four_arrow,three_arrow,two_arrow,one_arrow;
        LinearLayout layout_fone,layout_ftwo,layout_fthree,layout_fC;

        String ProjectID="";
        ImageView back_btn;

        TextView
                tv_westLongitude,
                tv_WestLatitude,
                tv_EastLongitude,
                tv_EastLatitude,
                tv_SouthLongitude,
                tv_SouthLatitude,
                tv_NorthLongitude,
                tv_NorthLatitude;

        LinearLayout layout_MEPConsultant,
                layout_ClerkofWorks,
                layout_SiteSupervisor,
                layout_QuantitySurveyor,
                layout_StructuralConsultant;

        JSONObject jsonObject_c;
        TextView tv_plotnumber;
        TextView tv_group_title;
        RelativeLayout layout_basic;
        String page="1";
        LinearLayout rera_la;
        TextView tv_assign,tv_appdate;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_consultants_main);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        getWindow().setStatusBarColor(ContextCompat.getColor(Activity_consultants_main.this, R.color.maroon_dark));
                }
                Intent intent = getIntent();
                ProjectID = intent.getStringExtra("ProjectID");
                page = intent.getStringExtra("page");


                sm = new SessionManager(Activity_consultants_main.this);
                back_btn=(ImageView) findViewById(R.id.back_btn);
                back_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                finish();
                        }
                });
                rera_la=(LinearLayout)findViewById(R.id.rera_la);
                layout_basic=(RelativeLayout)findViewById(R.id.layout_basic);
                tv_wreg=(TextView) findViewById(R.id.tv_wreg);
                tv_assign=(TextView)findViewById(R.id.tv_assign);
                tv_appdate=(TextView)findViewById(R.id.tv_appdate);
                tv_rdate=(TextView) findViewById(R.id.tv_rdate);
                tv_group_title=(TextView) findViewById(R.id.tv_group_title);
                tv_plotnumber=(TextView) findViewById(R.id.tv_plotnumber);
                layout_MEPConsultant=(LinearLayout)findViewById(R.id.layout_MEPConsultant);
                layout_ClerkofWorks=(LinearLayout)findViewById(R.id.layout_ClerkofWorks);
                layout_SiteSupervisor=(LinearLayout)findViewById(R.id.layout_SiteSupervisor);
                layout_QuantitySurveyor=(LinearLayout)findViewById(R.id.layout_QuantitySurveyor);
                layout_StructuralConsultant=(LinearLayout)findViewById(R.id.layout_StructuralConsultant);
                layout_fone=(LinearLayout)findViewById(R.id.layout_fone);
                layout_fC=(LinearLayout)findViewById(R.id.layout_fC);
                layout_rera=(LinearLayout)findViewById(R.id.layout_rera);
                tv_westLongitude=(TextView) findViewById(R.id.tv_westLongitude);
                tv_WestLatitude=(TextView) findViewById(R.id.tv_WestLatitude);
                tv_EastLongitude=(TextView) findViewById(R.id.tv_EastLongitude);
                tv_EastLatitude=(TextView) findViewById(R.id.tv_EastLatitude);
                tv_SouthLongitude=(TextView) findViewById(R.id.tv_SouthLongitude);
                tv_SouthLatitude=(TextView) findViewById(R.id.tv_SouthLatitude);
                tv_NorthLongitude=(TextView) findViewById(R.id.tv_NorthLongitude);
                tv_NorthLatitude=(TextView) findViewById(R.id.tv_NorthLatitude);

                layout_ftwo =(LinearLayout)findViewById(R.id.layout_ftwo);
                layout_fthree=(LinearLayout)findViewById(R.id.layout_fthree);
                one_deatils=(LinearLayout)findViewById(R.id.one_deatils);
                two_deatils=(LinearLayout)findViewById(R.id.two_deatils);
                three_deatils=(LinearLayout)findViewById(R.id.three_deatils);
                four_deatils=(LinearLayout)findViewById(R.id.four_deatils);
                five_deatils=(LinearLayout)findViewById(R.id.five_deatils);
                six_deatils=(LinearLayout)findViewById(R.id.six_deatils);
                one=(LinearLayout)findViewById(R.id.one);
                two=(LinearLayout)findViewById(R.id.two);
                three=(LinearLayout)findViewById(R.id.three);
                four=(LinearLayout)findViewById(R.id.four);
                five=(LinearLayout)findViewById(R.id.five);
                six=(LinearLayout)findViewById(R.id.six);

                six_arrow=(ImageView) findViewById(R.id.six_arrow);
                five_arrow=(ImageView) findViewById(R.id.five_arrow);
                four_arrow=(ImageView) findViewById(R.id.four_arrow);
                three_arrow=(ImageView) findViewById(R.id.three_arrow);
                two_arrow=(ImageView) findViewById(R.id.two_arrow);
                one_arrow=(ImageView) findViewById(R.id.one_arrow);

                //one_deatils.setVisibility(View.GONE);
                one.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                                two_deatils.setVisibility(View.GONE);
                                three_deatils.setVisibility(View.GONE);
                                four_deatils.setVisibility(View.GONE);
                                five_deatils.setVisibility(View.GONE);
                                six_deatils.setVisibility(View.GONE);


                                if (one_deatils.getVisibility() == View.VISIBLE)
                                {
                                        one_deatils.setVisibility(View.GONE);
                                        one_arrow.setImageDrawable(getResources().getDrawable(R.drawable.down_a));

                                }
                                else
                                {
                                        one_deatils.setVisibility(View.VISIBLE);
                                        one_arrow.setImageDrawable(getResources().getDrawable(R.drawable.up_a));
                                }
                        }
                });
                layout_fthree.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                                Intent i=new Intent(getApplicationContext(),Activity_Form3.class);
                                i.putExtra("ProjectID",ProjectID);
                                startActivity(i);
                        }
                });
                layout_ftwo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                                Intent i=new Intent(getApplicationContext(),Activity_Form2.class);
                                i.putExtra("ProjectID",ProjectID);
                                startActivity(i);

                        }
                });
                layout_fone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                                Intent i=new Intent(getApplicationContext(),Activity_Form.class);
                                i.putExtra("ProjectID",ProjectID);
                                startActivity(i);
                        }
                });
                layout_fC.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                                Intent i=new Intent(getApplicationContext(),Activity_consultants.class);
                                i.putExtra("ProjectID",ProjectID);
                                startActivity(i);
                        }
                });

                //   two_deatils.setVisibility(View.GONE);
                two.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                                one_deatils.setVisibility(View.GONE);
                                three_deatils.setVisibility(View.GONE);
                                four_deatils.setVisibility(View.GONE);
                                five_deatils.setVisibility(View.GONE);
                                six_deatils.setVisibility(View.GONE);
                                if (two_deatils.getVisibility() == View.VISIBLE)
                                {
                                        two_deatils.setVisibility(View.GONE);
                                        two_arrow.setImageDrawable(getResources().getDrawable(R.drawable.down_a));

                                }
                                else
                                {
                                        two_deatils.setVisibility(View.VISIBLE);
                                        two_arrow.setImageDrawable(getResources().getDrawable(R.drawable.up_a));
                                }
                        }
                });
                //   three_deatils.setVisibility(View.GONE);
                three.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                                two_deatils.setVisibility(View.GONE);
                                one_deatils.setVisibility(View.GONE);
                                four_deatils.setVisibility(View.GONE);
                                five_deatils.setVisibility(View.GONE);
                                six_deatils.setVisibility(View.GONE);
                                if (three_deatils.getVisibility() == View.VISIBLE)
                                {
                                        three_deatils.setVisibility(View.GONE);
                                        three_arrow.setImageDrawable(getResources().getDrawable(R.drawable.down_a));

                                }
                                else
                                {
                                        three_deatils.setVisibility(View.VISIBLE);
                                        three_arrow.setImageDrawable(getResources().getDrawable(R.drawable.up_a));
                                }
                        }
                });

                // four_deatils.setVisibility(View.GONE);
                four.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                                two_deatils.setVisibility(View.GONE);
                                three_deatils.setVisibility(View.GONE);
                                one_deatils.setVisibility(View.GONE);
                                five_deatils.setVisibility(View.GONE);
                                six_deatils.setVisibility(View.GONE);
                                if (four_deatils.getVisibility() == View.VISIBLE)
                                {
                                        four_deatils.setVisibility(View.GONE);
                                        four_arrow.setImageDrawable(getResources().getDrawable(R.drawable.down_a));

                                }
                                else
                                {

                                        four_deatils.setVisibility(View.VISIBLE);
                                        four_arrow.setImageDrawable(getResources().getDrawable(R.drawable.up_a));
                                }
                        }
                });

                //  five_deatils.setVisibility(View.GONE);
                five.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                                two_deatils.setVisibility(View.GONE);
                                three_deatils.setVisibility(View.GONE);
                                four_deatils.setVisibility(View.GONE);
                                one_deatils.setVisibility(View.GONE);
                                six_deatils.setVisibility(View.GONE);
                                if (five_deatils.getVisibility() == View.VISIBLE)
                                {
                                        five_deatils.setVisibility(View.GONE);
                                        five_arrow.setImageDrawable(getResources().getDrawable(R.drawable.down_a));

                                }
                                else
                                {
                                        five_deatils.setVisibility(View.VISIBLE);
                                        five_arrow.setImageDrawable(getResources().getDrawable(R.drawable.up_a));
                                }
                        }
                });

                // six_deatils.setVisibility(View.GONE);
                six.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v)
                        {
                                two_deatils.setVisibility(View.GONE);
                                three_deatils.setVisibility(View.GONE);
                                four_deatils.setVisibility(View.GONE);
                                five_deatils.setVisibility(View.GONE);
                                one_deatils.setVisibility(View.GONE);

                                if (six_deatils.getVisibility() == View.VISIBLE)
                                {
                                        six_deatils.setVisibility(View.GONE);
                                        six_arrow.setImageDrawable(getResources().getDrawable(R.drawable.down_a));

                                }
                                else
                                {
                                        six_deatils.setVisibility(View.VISIBLE);
                                        six_arrow.setImageDrawable(getResources().getDrawable(R.drawable.up_a));
                                }
                        }
                });

                one_deatils.setVisibility(View.GONE);
                two_deatils.setVisibility(View.GONE);
                three_deatils.setVisibility(View.VISIBLE);
                four_deatils.setVisibility(View.GONE);
                five_deatils.setVisibility(View.GONE);
                six_deatils.setVisibility(View.GONE);


      /*  if(page.equalsIgnoreCase("1"))
        {
            layout_basic.setVisibility(View.VISIBLE);
            one.setVisibility(View.VISIBLE);
            one_deatils.setVisibility(View.VISIBLE);

          //  two.setVisibility(View.VISIBLE);
           // two_deatils.setVisibility(View.VISIBLE);

           // three.setVisibility(View.GONE);
            three_deatils.setVisibility(View.GONE);
          //  four.setVisibility(View.GONE);
            four_deatils.setVisibility(View.GONE);
           // five.setVisibility(View.GONE);
            five_deatils.setVisibility(View.GONE);
           // six.setVisibility(View.GONE);
            six_deatils.setVisibility(View.GONE);

        }
       else  if(page.equalsIgnoreCase("2"))
        {
          //  layout_basic.setVisibility(View.GONE);


          //  one.setVisibility(View.GONE);
            one_deatils.setVisibility(View.GONE);
           // two.setVisibility(View.GONE);
            two_deatils.setVisibility(View.GONE);
           // three.setVisibility(View.VISIBLE);
            three_deatils.setVisibility(View.VISIBLE);
          //  four.setVisibility(View.GONE);
            four_deatils.setVisibility(View.GONE);
          //  five.setVisibility(View.GONE);
            five_deatils.setVisibility(View.GONE);
          //  six.setVisibility(View.GONE);
            six_deatils.setVisibility(View.GONE);

        }
        else  if(page.equalsIgnoreCase("3"))
        {
          //  layout_basic.setVisibility(View.GONE);


          //  one.setVisibility(View.GONE);
            one_deatils.setVisibility(View.GONE);
         //   two.setVisibility(View.GONE);
            two_deatils.setVisibility(View.GONE);
         //   three.setVisibility(View.GONE);
            three_deatils.setVisibility(View.GONE);
         //   four.setVisibility(View.VISIBLE);
            four_deatils.setVisibility(View.VISIBLE);
         //   five.setVisibility(View.VISIBLE);
            five_deatils.setVisibility(View.VISIBLE);
         //   six.setVisibility(View.GONE);
            six_deatils.setVisibility(View.GONE);
        }
        else  if(page.equalsIgnoreCase("4"))
        {
         //   layout_basic.setVisibility(View.GONE);


        //    one.setVisibility(View.GONE);
            one_deatils.setVisibility(View.GONE);
       //     two.setVisibility(View.GONE);
            two_deatils.setVisibility(View.GONE);
        //    three.setVisibility(View.GONE);
            three_deatils.setVisibility(View.GONE);
        //    four.setVisibility(View.GONE);
            four_deatils.setVisibility(View.GONE);
          //  five.setVisibility(View.GONE);
            five_deatils.setVisibility(View.GONE);
         //   six.setVisibility(View.VISIBLE);
            six_deatils.setVisibility(View.VISIBLE);
        }*/

                img_pro=(ImageView)findViewById(R.id.img_pro);
                layout_authorized=(LinearLayout)findViewById(R.id.layout_authorized);
                layout_projecthead=(LinearLayout)findViewById(R.id.layout_projecthead);

                layout_StructuralEngineers=(LinearLayout)findViewById(R.id.layout_StructuralEngineers);
                layout_ProjectArchitect=(LinearLayout)findViewById(R.id.layout_ProjectArchitect);
                layout_ProjectCA=(LinearLayout)findViewById(R.id.layout_ProjectCA);
                layout_ProjectContractor=(LinearLayout)findViewById(R.id.layout_ProjectContractor);
                layout_ProjectAgent=(LinearLayout)findViewById(R.id.layout_ProjectAgent);
                layout_DevelopmentDetail=(LinearLayout)findViewById(R.id.layout_DevelopmentDetail);
                layout_BlockandInventory=(LinearLayout)findViewById(R.id.layout_BlockandInventory);
                layout_ExternalDevelopmentWork=(LinearLayout)findViewById(R.id.layout_ExternalDevelopmentWork);
                // layout_formA=(LinearLayout)findViewById(R.id.layout_formA);

                tv_mno=(TextView)findViewById(R.id.tv_mno) ;
                // tv_e_desc=(TextView)findViewById(R.id.tv_e_desc);
                tv_i_RoadSystem=(TextView)findViewById(R.id.tv_i_RoadSystem);
                tv_i_WaterSupply=(TextView)findViewById(R.id.tv_i_WaterSupply);
                tv_i_Sewage=(TextView)findViewById(R.id.tv_i_Sewage);
                tv_i_Electricity=(TextView)findViewById(R.id.tv_i_Electricity);
                tv_i_Solid=(TextView)findViewById(R.id.tv_i_Solid);


                title1=(TextView)findViewById(R.id.title1);
                tv_p_name=(TextView)findViewById(R.id.tv_p_name) ;
                tv_email=(TextView)findViewById(R.id.tv_email) ;
                tv_type=(TextView)findViewById(R.id.tv_type) ;
                tv_add=(TextView)findViewById(R.id.tv_add) ;
                tv_pan=(TextView)findViewById(R.id.tv_pan) ;
                tv_group=(TextView)findViewById(R.id.tv_group) ;

                tv_p_deatils=(TextView)findViewById(R.id.tv_p_deatils) ;
                tv_p_des=(TextView)findViewById(R.id.tv_p_des) ;
                tv_p_type=(TextView)findViewById(R.id.tv_p_type) ;
                tv_p_status=(TextView)findViewById(R.id.tv_p_status) ;
                tv_p_sdate=(TextView)findViewById(R.id.tv_p_sdate) ;
                tv_p_cdate=(TextView)findViewById(R.id.tv_p_cdate) ;
                tv_p_tland=(TextView)findViewById(R.id.tv_p_tland) ;
                tv_p_carea=(TextView)findViewById(R.id.tv_p_carea) ;
                tv_p_tarea=(TextView)findViewById(R.id.tv_p_tarea) ;
                tv_p_const=(TextView)findViewById(R.id.tv_p_const) ;
                tv_p_land=(TextView)findViewById(R.id.tv_p_land) ;
                tv_p_cost=(TextView)findViewById(R.id.tv_p_cost) ;
                tv_p_add1=(TextView)findViewById(R.id.tv_p_add1) ;
                tv_p_add2=(TextView)findViewById(R.id.tv_p_add2) ;
                tv_p_state=(TextView)findViewById(R.id.tv_p_state) ;
                tv_p_city=(TextView)findViewById(R.id.tv_p_city) ;
                tv_p_zip=(TextView)findViewById(R.id.tv_p_zip) ;
                tv_p_aurhority=(TextView)findViewById(R.id.tv_p_aurhority) ;
                tv_p_office=(TextView)findViewById(R.id.tv_p_office) ;
                tv_p_wreg =(TextView)findViewById(R.id.tv_p_wreg) ;
                tv_p_reg=(TextView)findViewById(R.id.tv_p_reg) ;

                // tv_d_typei=(TextView)findViewById(R.id.tv_d_typei) ;
                // tv_d_noofi=(TextView)findViewById(R.id.tv_d_noofi) ;
                // tv_d_ca=(TextView)findViewById(R.id.tv_d_ca) ;
                // tv_d_exclusiveb=(TextView)findViewById(R.id.tv_d_exclusiveb) ;
                // tv_d_exclusiveo=(TextView)findViewById(R.id.tv_d_exclusiveo) ;
                tv_d_noofgarage=(TextView)findViewById(R.id.tv_d_noofgarage) ;
                tv_d_agarage=(TextView)findViewById(R.id.tv_d_agarage) ;
                tv_d_noofoparking=(TextView)findViewById(R.id.tv_d_noofoparking) ;
                tv_d_oparking=(TextView)findViewById(R.id.tv_d_oparking) ;
                tv_d_noofparking=(TextView)findViewById(R.id.tv_d_noofparking) ;
                tv_d_aparking=(TextView)findViewById(R.id.tv_d_aparking) ;

                try
                {
                        jsonObject_c = new JSONObject(sm.getjs());
                        // String PathName = jsonObject.getString("DisplayName");
                     //   title1.setText(": "+jsonObject_c.getString("DisplayName"));
                        tv_mno.setText(": "+jsonObject_c.getString("MobileNumber"));
                        String PathName = jsonObject_c.getString("ProfilePicture");

                        JSONObject object = jsonObject_c.getJSONObject("ContactDetail");
                        tv_email.setText(": "+object.getString("EmailID"));
                        tv_type.setText(": "+jsonObject_c.getString("PromoterType"));
                        JSONObject AdditionalDetail = jsonObject_c.getJSONObject("AdditionalDetail");

                        // tv_add.setText(object.getString("Address")+" "+object.getString("Address2")+" ,"+object.getString("CityName")+", "+object.getString("StateName")+", "+object.getString("ZIPCode"));

                        //tv_add.setText(object.getString("Address"));


                        // tv_group.setText(AdditionalDetail.getString("PANID"));



                }
                catch (Exception e)
                {

                }
                loginUser();
                blocks();
                Development();
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
                                                        String idt=array.getJSONObject(i).getString("TypeID");

                                                        TextView tv_ctitle=(TextView)findViewById(R.id.tv_ctitle);
                                                        tv_ctitle.setText("Firm Name");

                                                        String Promoter=array.getJSONObject(i).getString("Promoter");
                                                        JSONObject Promotera =  new JSONObject(Promoter);
                                                        tv_pan.setText(": "+Promotera.getString("FirmName"));
                                                        Picasso.with(getApplicationContext()).load(""+Promotera.getString("ProfileImage")).placeholder(getResources().getDrawable(R.drawable.no_img)).error(getResources().getDrawable(R.drawable.no_img)).into(img_pro);


                                                        String Address=array.getJSONObject(i).getString("Address");
                                                        JSONObject Addressa =  new JSONObject(Address);
                                                        tv_add.setText(": "+Promotera.getString("AddressText"));

                               /* try
                                {
                                    JSONObject AdditionalDetails = jsonObject_c.getJSONObject("AdditionalDetail");

                                    //TextView tv_ctitle=(TextView)findViewById(R.id.tv_ctitle);
                                    if(idt.equalsIgnoreCase("1"))
                                    {
                                        tv_pan.setText(jsonObject_c.getString("CompanyName"));
                                        tv_group.setText(AdditionalDetails.getString("CompanyRegID"));
                                    }
                                    else if(idt.equalsIgnoreCase("2"))
                                    {
                                        tv_pan.setText(jsonObject_c.getString("DisplayName"));
                                        tv_group.setText(AdditionalDetails.getString("PANID"));
                                        tv_group_title.setText("PAN No. : ");
                                    }
                                    else if(idt.equalsIgnoreCase("3"))
                                    {
                                        tv_group_title.setText("PAN No. : ");
                                        tv_pan.setText(jsonObject_c.getString("DisplayName"));
                                        tv_group.setText(AdditionalDetails.getString("PANID"));
                                    }
                                }
                                catch (Exception e)
                                {

                                }*/

                                                        tv_assign.setText(Html.fromHtml("<font color='black'>: "+array.getJSONObject(i).getString("AssigneeName")));
                                                        tv_appdate.setText(Html.fromHtml("<font color='black'>: "+array.getJSONObject(i).getString("RADate")));

                                                        tv_plotnumber.setText(Html.fromHtml("<font color='black'>: "+array.getJSONObject(i).getString("PlotNumber")));
                                                        tv_p_name.setText(Html.fromHtml("<font color='black'>: "+array.getJSONObject(i).getString("ProjectName")));
                                                        tv_p_des.setText(Html.fromHtml("<font color='black'>: "+array.getJSONObject(i).getString("Description")));
                                                        tv_p_des.setText(Html.fromHtml("<font color='black'>: "+array.getJSONObject(i).getString("Description")));
                                                        tv_p_type.setText(Html.fromHtml("<font color='black'>: "+array.getJSONObject(i).getString("ProjectType")));
                                                        tv_p_status.setText(Html.fromHtml("<font color='black'>:"+array.getJSONObject(i).getString("ProjectStatus")));
                                                        tv_p_sdate.setText(Html.fromHtml("<font color='black'>: "+formatedate_server(array.getJSONObject(i).getString("StartDate"))));
                                                        tv_p_cdate.setText(Html.fromHtml("<font color='black'>: "+formatedate_server(array.getJSONObject(i).getString("EndDate"))));
                                                        tv_p_tland.setText(Html.fromHtml("<font color='black'>: "+" "+array.getJSONObject(i).getString("AreaLand")));
                                                        tv_p_carea.setText(Html.fromHtml("<font color='black'>: "+""+array.getJSONObject(i).getString("AreaCovered")));
                                                        tv_p_tarea.setText(Html.fromHtml("<font color='black'>: "+""+array.getJSONObject(i).getString("AreaOpen")));
                                                        // tv_p_const.setText(Html.fromHtml("Est Cost of Costruction : <font color='black'>"+array.getJSONObject(i).getString("EstConstructionCost")));
                                                        //tv_p_land.setText(Html.fromHtml("Cost of Land (INR): <font color='black'>"+array.getJSONObject(i).getString("LandCost")));
                                                        //tv_p_cost.setText(Html.fromHtml("Total Project Cost (INR): <font color='black'>"+array.getJSONObject(i).getString("ProjectCost")));

                                                        // tv_westLongitude.setText(Html.fromHtml("west Longitude: <font color='black'>"+array.getJSONObject(i).getString("WestLng")));
                                                        if(array.getJSONObject(i).getString("WestLocation").equalsIgnoreCase("null"))
                                                                tv_WestLatitude.setText(Html.fromHtml("West : <font color='black'>"+"0.00"));
                                                        else
                                                                tv_WestLatitude.setText(Html.fromHtml("West : <font color='black'>"+array.getJSONObject(i).getString("WestLocation")));
                                                        // tv_EastLongitude.setText(Html.fromHtml("East Longitude: <font color='black'>"+array.getJSONObject(i).getString("EastLng")));

                                                        if(array.getJSONObject(i).getString("EastLocation").equalsIgnoreCase("null"))
                                                                tv_EastLatitude.setText(Html.fromHtml("East : <font color='black'>"+"0.00"));
                                                        else
                                                                tv_EastLatitude.setText(Html.fromHtml("East : <font color='black'>"+array.getJSONObject(i).getString("EastLocation")));
                                                        //// tv_SouthLongitude.setText(Html.fromHtml("South Longitude: <font color='black'>"+array.getJSONObject(i).getString("SouthLng")));

                                                        if(array.getJSONObject(i).getString("SouthLocation").equalsIgnoreCase("null"))
                                                                tv_SouthLatitude.setText(Html.fromHtml("South : <font color='black'>"+"0.00"));
                                                        else
                                                                tv_SouthLatitude.setText(Html.fromHtml("South : <font color='black'>"+array.getJSONObject(i).getString("SouthLocation")));
                                                        //tv_NorthLongitude.setText(Html.fromHtml("North Longitude: <font color='black'>"+array.getJSONObject(i).getString("NorthLng")));
                                                        if(array.getJSONObject(i).getString("NorthLocation").equalsIgnoreCase("null"))
                                                                tv_NorthLatitude.setText(Html.fromHtml("North : <font color='black'>"+"0.00"));
                                                        else
                                                                tv_NorthLatitude.setText(Html.fromHtml("North : <font color='black'>"+array.getJSONObject(i).getString("NorthLocation")));


                                                        String s=array.getJSONObject(i).getString("Address");
                                                        JSONObject jsonObject1 = new JSONObject(s);

                                                        tv_p_add1.setText(Html.fromHtml("<font color='black'>: "+jsonObject1.getString("Address")+", "+jsonObject1.getString("Address2")+", "+jsonObject1.getString("CityName")+", "+jsonObject1.getString("StateName")+", "+jsonObject1.getString("ZIPCode")));
                                                        tv_p_add2.setText(Html.fromHtml("Address Line 2: <font color='black'>"+jsonObject1.getString("Address2")));
                                                        tv_p_state.setText(Html.fromHtml("State: <font color='black'>"+jsonObject1.getString("StateName")));
                                                        tv_p_city.setText(Html.fromHtml("City: <font color='black'>"+jsonObject1.getString("CityName")));
                                                        tv_p_zip.setText(Html.fromHtml("PIN code: <font color='black'>"+jsonObject1.getString("ZIPCode")));
                                                        tv_p_aurhority.setText(Html.fromHtml("<font color='black'>: "+array.getJSONObject(i).getString("PlanApprovingAuthorityName")));
                                                        // tv_p_office.setText(Html.fromHtml("Hard Copy Submission Office: <font color='black'>"+array.getJSONObject(i).getString("HardCopySubmissionName")));
                                                        if(array.getJSONObject(i).getString("RegNoStatus").equalsIgnoreCase("null")||array.getJSONObject(i).getString("RegNoStatus").equalsIgnoreCase(""))
                                                                tv_p_wreg.setText(Html.fromHtml("<font color='black'>: "+"No"));
                                                        else if(array.getJSONObject(i).getString("RegNoStatus").equalsIgnoreCase("Y"))
                                                                tv_p_wreg.setText(Html.fromHtml("<font color='black'>: "+"Yes"));
                                                        else if(array.getJSONObject(i).getString("RegNoStatus").equalsIgnoreCase("N"))
                                                                tv_p_wreg.setText(Html.fromHtml("<font color='black'>: "+"No"));
                                                        tv_p_reg.setText(Html.fromHtml("<font color='black'>: "+array.getJSONObject(i).getString("RegistrationNumber")));
                                                        tv_rdate.setText(Html.fromHtml("Registration Date: <font color='black'>"+array.getJSONObject(i).getString("RADate")));
                                                        if(array.getJSONObject(i).getString("RegApplication").equalsIgnoreCase("N"))
                                                                tv_wreg.setText(Html.fromHtml("<font color='black'>: "+"No"));
                                                        else
                                                                tv_wreg.setText(Html.fromHtml("<font color='black'>: "+"Yes"));
                             /*   String OtherProjects=array.getJSONObject(i).getString("OtherProjects");
                                if(OtherProjects.equalsIgnoreCase("N"))
                                {
                                    rera_la.setVisibility(View.GONE);
                                }
                                else
                                {
                                    rera_la.setVisibility(View.GONE);
                                }*/
                                                        try
                                                        {
                                                                String RERAReg=array.getJSONObject(i).getString("RERAReg");
                                                                JSONArray RERARega =  new JSONArray(RERAReg);
                                                                for(int h=0; h<RERARega.length(); h++)
                                                                {

                                                                        LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                                        final View addView = layoutInflater.inflate(R.layout.list_rera, null);

                                                                        TextView tv_RERARegistrationNo = (TextView)addView.findViewById(R.id.tv_RERARegistrationNo);
                                                                        TextView tv_State = (TextView)addView.findViewById(R.id.tv_State);
                                                                        TextView tv_revoked = (TextView)addView.findViewById(R.id.tv_revoked);
                                                                        TextView tv_revokedr = (TextView)addView.findViewById(R.id.tv_revokedr);

                                                                        tv_RERARegistrationNo.setText(Html.fromHtml("RERA Registration No. : <font color='black'>"+RERARega.getJSONObject(h).getString("RegNumber")));
                                                                        tv_State.setText(Html.fromHtml("State : <font color='black'>"+RERARega.getJSONObject(h).getString("StateName")));

                                                                        if(RERARega.getJSONObject(h).getString("RevokeStatus").equalsIgnoreCase("Y"))
                                                                                tv_revoked.setText(Html.fromHtml("Have you said registration been revoked? : <font color='black'>"+"Yes"));
                                                                        else
                                                                                tv_revoked.setText(Html.fromHtml("Have you said registration been revoked? : <font color='black'>"+"No"));

                                                                        if(RERARega.getJSONObject(h).getString("RevokeStatus").equalsIgnoreCase("Y"))
                                                                                tv_revokedr.setVisibility(View.VISIBLE);
                                                                        else
                                                                                tv_revokedr.setVisibility(View.GONE);
                                                                        tv_revokedr.setText(Html.fromHtml("Revoke Reason : <font color='black'>"+RERARega.getJSONObject(h).getString("RevokeReason")));


                                                                        layout_rera.addView(addView);
                                                                }

                                                        }
                                                        catch (Exception e)
                                                        {

                                                        }
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
                                                                                                CustomDialogClass cdd=new CustomDialogClass(Activity_consultants_main.this,Blocksa.get(h1)+"");
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
                                                        JSONArray Engineer=null,Architect=null,CA=null,Structural = null;
                                                        String Consultants=array.getJSONObject(i).getString("Consultants");
                                                        JSONObject Consultantsj = new JSONObject(Consultants);
                                                        JSONArray Consultantsa = Consultantsj.getJSONArray("AuthorizedSignatory");
                                                        //  JSONArray Engineer = Consultantsj.getJSONArray("Engineer");
                                                        // JSONArray Architect = Consultantsj.getJSONArray("Architect");
                                                        //JSONArray CA = Consultantsj.getJSONArray("CA");
                                                        // JSONArray Structural = Consultantsj.getJSONArray("Structural");


                                                        JSONArray Contractor = Consultantsj.getJSONArray("Contractor");
                                                        JSONArray Agent = Consultantsj.getJSONArray("Agent");
                                                        JSONArray QuantitySurveyor = Consultantsj.getJSONArray("QuantitySurveyor");
                                                        JSONArray SiteSupervisor = Consultantsj.getJSONArray("SiteSupervisor");
                                                        JSONArray COW = Consultantsj.getJSONArray("COW");
                                                        JSONArray MEP = Consultantsj.getJSONArray("MEP");

                                                        JSONArray ProjectHead = Consultantsj.getJSONArray("ProjectHead");


                                                        for(int h=0; h<Contractor  .length(); h++)
                                                        {

                                                                LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                                final View addView = layoutInflater.inflate(R.layout.list_a_authorized, null);
                                                                TextView tv_a_fname = (TextView)addView.findViewById(R.id.tv_a_fname);
                                                                TextView tv_a_email = (TextView)addView.findViewById(R.id.tv_a_email);
                                                                TextView tv_a_mno = (TextView)addView.findViewById(R.id.tv_a_mno);
                                                                tv_a_email.setText(Html.fromHtml("Email ID : "+Contractor.getJSONObject(h).getString("EmailID").toString()));
                                                                tv_a_fname.setText(Html.fromHtml("Contact Name : "+Contractor .getJSONObject(h).getString("ContactName").toString()));
                                                                tv_a_mno.setText(Html.fromHtml("Mobile : "+Contractor .getJSONObject(h).getString("MobileNumber").toString()));

                                                                TextView tv_8 = (TextView)addView.findViewById(R.id.tv_8);
                                                                ImageView image_Upload=(ImageView)addView.findViewById(R.id.image_Upload);
                                                                tv_8.setVisibility(View.VISIBLE);
                                                                Picasso.with(getApplicationContext()).load(Contractor.getJSONObject(h).getString("ProfilePicture").toString()).into(image_Upload);
                                                                tv_8.setText("PAN No. : "+Contractor.getJSONObject(i).getString("PANNumber").toString());
                                                                layout_ProjectContractor.addView(addView);
                                                        }
                                                        for(int h=0; h<Agent.length(); h++)
                                                        {

                                                                LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                                final View addView = layoutInflater.inflate(R.layout.list_a_authorized, null);
                                                                TextView tv_a_fname = (TextView)addView.findViewById(R.id.tv_a_fname);
                                                                TextView tv_a_email = (TextView)addView.findViewById(R.id.tv_a_email);
                                                                TextView tv_a_mno = (TextView)addView.findViewById(R.id.tv_a_mno);
                                                                tv_a_email.setText(Html.fromHtml("Email ID : "+Agent.getJSONObject(h).getString("EmailID").toString()));
                                                                tv_a_fname.setText(Html.fromHtml("Contact Name : "+Agent .getJSONObject(h).getString("ContactName").toString()));
                                                                tv_a_mno.setText(Html.fromHtml("Mobile : "+Agent .getJSONObject(h).getString("MobileNumber").toString()));
                                                                TextView tv_8 = (TextView)addView.findViewById(R.id.tv_8);
                                                                ImageView image_Upload=(ImageView)addView.findViewById(R.id.image_Upload);
                                                                tv_8.setVisibility(View.VISIBLE);
                                                                Picasso.with(getApplicationContext()).load(Agent.getJSONObject(h).getString("ProfilePicture").toString()).into(image_Upload);
                                                                tv_8.setText("PAN No. : "+Agent.getJSONObject(i).getString("PANNumber").toString());
                                                                layout_ProjectAgent.addView(addView);
                                                        }

                                                        try
                                                        {
                                                                for(int h=0; h<QuantitySurveyor.length(); h++)
                                                                {

                                                                        LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                                        final View addView = layoutInflater.inflate(R.layout.list_a_authorized, null);
                                                                        TextView tv_a_fname = (TextView)addView.findViewById(R.id.tv_a_fname);
                                                                        TextView tv_a_mno = (TextView)addView.findViewById(R.id.tv_a_mno);
                                                                        TextView tv_a_email = (TextView)addView.findViewById(R.id.tv_a_email);
                                                                        tv_a_fname.setText(Html.fromHtml("Contact Name : "+QuantitySurveyor.getJSONObject(i).getString("ContactName").toString()));
                                                                        tv_a_mno.setText(Html.fromHtml("Mobile : "+QuantitySurveyor.getJSONObject(i).getString("MobileNumber").toString()));
                                                                        tv_a_email.setText(Html.fromHtml("Email ID : "+QuantitySurveyor.getJSONObject(i).getString("EmailID").toString()));
                                                                        TextView tv_8 = (TextView)addView.findViewById(R.id.tv_8);
                                                                        ImageView image_Upload=(ImageView)addView.findViewById(R.id.image_Upload);
                                                                        tv_8.setVisibility(View.VISIBLE);
                                                                        Picasso.with(getApplicationContext()).load(QuantitySurveyor.getJSONObject(h).getString("ProfilePicture").toString()).into(image_Upload);
                                                                        tv_8.setText("PAN No. : "+QuantitySurveyor.getJSONObject(i).getString("PANNumber").toString());
                                                                        layout_QuantitySurveyor.addView(addView);
                                                                }

                                                        }
                                                        catch (Exception e)
                                                        {

                                                        }
                                                        try
                                                        {
                                                                for(int h=0; h<SiteSupervisor.length(); h++)
                                                                {

                                                                        LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                                        final View addView = layoutInflater.inflate(R.layout.list_a_authorized, null);
                                                                        TextView tv_a_fname = (TextView)addView.findViewById(R.id.tv_a_fname);
                                                                        TextView tv_a_mno = (TextView)addView.findViewById(R.id.tv_a_mno);
                                                                        TextView tv_a_email = (TextView)addView.findViewById(R.id.tv_a_email);
                                                                        tv_a_fname.setText(Html.fromHtml("Contact Name : "+SiteSupervisor.getJSONObject(i).getString("ContactName").toString()));
                                                                        tv_a_mno.setText(Html.fromHtml("Mobile : "+SiteSupervisor.getJSONObject(i).getString("MobileNumber").toString()));
                                                                        tv_a_email.setText(Html.fromHtml("Email ID : "+SiteSupervisor.getJSONObject(i).getString("EmailID").toString()));
                                                                        TextView tv_8 = (TextView)addView.findViewById(R.id.tv_8);
                                                                        ImageView image_Upload=(ImageView)addView.findViewById(R.id.image_Upload);
                                                                        tv_8.setVisibility(View.VISIBLE);
                                                                        Picasso.with(getApplicationContext()).load(SiteSupervisor.getJSONObject(h).getString("ProfilePicture").toString()).into(image_Upload);
                                                                        tv_8.setText("PAN No. : "+SiteSupervisor.getJSONObject(i).getString("PANNumber").toString());
                                                                        layout_SiteSupervisor.addView(addView);
                                                                }

                                                        }
                                                        catch (Exception e)
                                                        {

                                                        }
                                                        try
                                                        {
                                                                for(int h=0; h<COW.length(); h++)
                                                                {

                                                                        LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                                        final View addView = layoutInflater.inflate(R.layout.list_a_authorized, null);
                                                                        TextView tv_a_fname = (TextView)addView.findViewById(R.id.tv_a_fname);
                                                                        TextView tv_a_mno = (TextView)addView.findViewById(R.id.tv_a_mno);
                                                                        TextView tv_a_email = (TextView)addView.findViewById(R.id.tv_a_email);
                                                                        tv_a_fname.setText(Html.fromHtml("Contact Name : "+COW.getJSONObject(i).getString("ContactName").toString()));
                                                                        tv_a_mno.setText(Html.fromHtml("Mobile : "+COW.getJSONObject(i).getString("MobileNumber").toString()));
                                                                        tv_a_email.setText(Html.fromHtml("Email ID : "+COW.getJSONObject(i).getString("EmailID").toString()));
                                                                        TextView tv_8 = (TextView)addView.findViewById(R.id.tv_8);
                                                                        ImageView image_Upload=(ImageView)addView.findViewById(R.id.image_Upload);
                                                                        tv_8.setVisibility(View.VISIBLE);
                                                                        Picasso.with(getApplicationContext()).load(COW.getJSONObject(h).getString("ProfilePicture").toString()).into(image_Upload);
                                                                        tv_8.setText("PAN No. : "+COW.getJSONObject(i).getString("PANNumber").toString());
                                                                        layout_ClerkofWorks.addView(addView);
                                                                }

                                                        }
                                                        catch (Exception e)
                                                        {

                                                        }
                                                        try
                                                        {
                                                                for(int h=0; h<MEP.length(); h++)
                                                                {

                                                                        LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                                        final View addView = layoutInflater.inflate(R.layout.list_a_authorized, null);
                                                                        TextView tv_a_fname = (TextView)addView.findViewById(R.id.tv_a_fname);
                                                                        TextView tv_a_mno = (TextView)addView.findViewById(R.id.tv_a_mno);
                                                                        TextView tv_a_email = (TextView)addView.findViewById(R.id.tv_a_email);
                                                                        tv_a_fname.setText(Html.fromHtml("Contact Name : "+MEP.getJSONObject(i).getString("ContactName").toString()));
                                                                        tv_a_mno.setText(Html.fromHtml("Mobile : "+MEP.getJSONObject(i).getString("MobileNumber").toString()));
                                                                        tv_a_email.setText(Html.fromHtml("Email ID : "+MEP.getJSONObject(i).getString("EmailID").toString()));
                                                                        TextView tv_8 = (TextView)addView.findViewById(R.id.tv_8);
                                                                        ImageView image_Upload=(ImageView)addView.findViewById(R.id.image_Upload);
                                                                        tv_8.setVisibility(View.VISIBLE);
                                                                        Picasso.with(getApplicationContext()).load(MEP.getJSONObject(h).getString("ProfilePicture").toString()).into(image_Upload);
                                                                        tv_8.setText("PAN No. : "+MEP.getJSONObject(i).getString("PANNumber").toString());
                                                                        layout_MEPConsultant.addView(addView);
                                                                }

                                                        }
                                                        catch (Exception e)
                                                        {

                                                        }
                                                        try
                                                        {
                                                                for(int h=0; h<Structural.length(); h++)
                                                                {

                                                                        LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                                        final View addView = layoutInflater.inflate(R.layout.list_a_authorized, null);
                                                                        TextView tv_a_fname = (TextView)addView.findViewById(R.id.tv_a_fname);
                                                                        TextView tv_a_mno = (TextView)addView.findViewById(R.id.tv_a_mno);
                                                                        TextView tv_a_email = (TextView)addView.findViewById(R.id.tv_a_email);
                                                                        tv_a_fname.setText(Html.fromHtml("Contact Name : "+Structural.getJSONObject(i).getString("ContactName").toString()));
                                                                        tv_a_mno.setText(Html.fromHtml("Mobile : "+Structural.getJSONObject(i).getString("MobileNumber").toString()));
                                                                        tv_a_email.setText(Html.fromHtml("Email ID : "+Structural.getJSONObject(i).getString("EmailID").toString()));
                                                                        layout_StructuralConsultant.addView(addView);
                                                                }

                                                        }
                                                        catch (Exception e)
                                                        {

                                                        }

                                                        try
                                                        {
                                                                for(int h=0; h<Consultantsa.length(); h++)
                                                                {

                                                                        LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                                        final View addView = layoutInflater.inflate(R.layout.list_a_authorized, null);
                                                                        TextView tv_a_fname = (TextView)addView.findViewById(R.id.tv_a_fname);
                                                                        TextView tv_a_mno = (TextView)addView.findViewById(R.id.tv_a_mno);
                                                                        TextView tv_a_email = (TextView)addView.findViewById(R.id.tv_a_email);
                                                                        tv_a_fname.setText(Html.fromHtml("Contact Name : "+Consultantsa.getJSONObject(i).getString("ContactName").toString()));
                                                                        tv_a_mno.setText(Html.fromHtml("Mobile : "+Consultantsa.getJSONObject(i).getString("MobileNumber").toString()));
                                                                        tv_a_email.setText(Html.fromHtml("Email ID : "+Consultantsa.getJSONObject(i).getString("EmailID").toString()));
                                                                        layout_authorized.addView(addView);
                                                                }

                                                        }
                                                        catch (Exception e)
                                                        {

                                                        }


                                                        for(int h=0; h<ProjectHead.length(); h++)
                                                        {

                                                                LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                                final View addView = layoutInflater.inflate(R.layout.list_a_authorized, null);
                                                                TextView tv_a_fname = (TextView)addView.findViewById(R.id.tv_a_fname);
                                                                TextView tv_a_email = (TextView)addView.findViewById(R.id.tv_a_email);
                                                                TextView tv_a_mno = (TextView)addView.findViewById(R.id.tv_a_mno);
                                                                tv_a_fname.setText(Html.fromHtml("Contact Name : "+ProjectHead.getJSONObject(i).getString("ContactName").toString()));
                                                                tv_a_mno.setText(Html.fromHtml("Mobile : "+ProjectHead.getJSONObject(i).getString("MobileNumber").toString()));
                                                                tv_a_email.setText(Html.fromHtml("Email ID : "+ProjectHead.getJSONObject(i).getString("EmailID").toString()));
                                                                layout_projecthead.addView(addView);
                                                        }


                                                        //////////////////////////////
                                                        for(int h=0; h<Engineer.length(); h++)
                                                        {

                                                                LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                                final View addView = layoutInflater.inflate(R.layout.list_a_authorized, null);
                                                                TextView tv_a_fname = (TextView)addView.findViewById(R.id.tv_a_fname);
                                                                TextView tv_a_email = (TextView)addView.findViewById(R.id.tv_a_email);
                                                                TextView tv_a_mno = (TextView)addView.findViewById(R.id.tv_a_mno);
                                                                TextView tv_4 = (TextView)addView.findViewById(R.id.tv_4);
                                                                TextView tv_5 = (TextView)addView.findViewById(R.id.tv_5);
                                                                tv_4.setVisibility(View.VISIBLE);
                                                                tv_5.setVisibility(View.VISIBLE);
                                                                tv_a_fname.setText(Html.fromHtml("Contact Name : "+Engineer.getJSONObject(i).getString("ContactName").toString()));
                                                                tv_a_email.setText(Html.fromHtml("Email ID : "+Engineer.getJSONObject(i).getString("EmailID").toString()));
                                                                tv_a_mno.setText(Html.fromHtml("Mobile : "+Engineer.getJSONObject(i).getString("MobileNumber").toString()));
                                                                tv_4.setText(Html.fromHtml("Licence Number : "+Engineer.getJSONObject(i).getString("LicenceNumber").toString()));
                                                                tv_5.setText(Html.fromHtml("Licence Valid Upto : "+Engineer.getJSONObject(i).getString("LicenceValidityDate").toString()));
                                                                layout_StructuralEngineers.addView(addView);
                                                        }
                                                        for(int h=0; h<Architect.length(); h++)
                                                        {

                                                                LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                                final View addView = layoutInflater.inflate(R.layout.list_a_authorized, null);
                                                                TextView tv_a_fname = (TextView)addView.findViewById(R.id.tv_a_fname);
                                                                TextView tv_a_email = (TextView)addView.findViewById(R.id.tv_a_email);
                                                                TextView tv_a_mno = (TextView)addView.findViewById(R.id.tv_a_mno);

                                                                TextView tv_4 = (TextView)addView.findViewById(R.id.tv_4);
                                                                TextView tv_5 = (TextView)addView.findViewById(R.id.tv_5);
                                                                tv_4.setVisibility(View.VISIBLE);
                                                                tv_5.setVisibility(View.VISIBLE);
                                                                tv_a_fname.setText(Html.fromHtml("Contact Name : "+Architect.getJSONObject(i).getString("ContactName").toString()));
                                                                tv_a_email.setText(Html.fromHtml("Email ID : "+Architect.getJSONObject(i).getString("EmailID").toString()));
                                                                tv_a_mno.setText(Html.fromHtml("Mobile : "+Architect.getJSONObject(i).getString("MobileNumber").toString()));

                                                                tv_4.setText(Html.fromHtml("COA Reg. Number : "+Architect.getJSONObject(i).getString("LicenceNumber").toString()));
                                                                tv_5.setText(Html.fromHtml("Number Valid Upto : "+Architect.getJSONObject(i).getString("LicenceValidityDate").toString()));



                                                                layout_ProjectArchitect.addView(addView);
                                                        }
                                                        for(int h=0; h<CA .length(); h++)
                                                        {

                                                                LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                                final View addView = layoutInflater.inflate(R.layout.list_a_authorized, null);
                                                                TextView tv_a_fname = (TextView)addView.findViewById(R.id.tv_a_fname);
                                                                TextView tv_a_email = (TextView)addView.findViewById(R.id.tv_a_email);
                                                                TextView tv_a_mno = (TextView)addView.findViewById(R.id.tv_a_mno);

                                                                TextView tv_4 = (TextView)addView.findViewById(R.id.tv_4);
                                                                TextView tv_5 = (TextView)addView.findViewById(R.id.tv_5);
                                                                TextView tv_6 = (TextView)addView.findViewById(R.id.tv_6);
                                                                TextView tv_7 = (TextView)addView.findViewById(R.id.tv_7);
                                                                tv_4.setVisibility(View.VISIBLE);
                                                                tv_5.setVisibility(View.VISIBLE);



                                                                tv_a_email.setText(Html.fromHtml("Email ID : "+CA.getJSONObject(i).getString("EmailID").toString()));
                                                                tv_a_fname.setText(Html.fromHtml("Contact Name : "+CA .getJSONObject(i).getString("ContactName").toString()));
                                                                tv_a_mno.setText(Html.fromHtml("Mobile : "+CA .getJSONObject(i).getString("MobileNumber").toString()));

                                                                tv_4.setText(Html.fromHtml("Type : "+CA.getJSONObject(i).getString("ConsultantType").toString()));


                                                                if(CA.getJSONObject(i).getString("ConsultantType").toString().equalsIgnoreCase("Partnership"))
                                                                {
                                                                        tv_6.setVisibility(View.VISIBLE);
                                                                        tv_7.setVisibility(View.VISIBLE);
                                                                        tv_6.setText(Html.fromHtml("Firm Name : "+CA.getJSONObject(i).getString("FirmName").toString()));
                                                                        tv_7.setText(Html.fromHtml("Firm Reg. Number : "+CA.getJSONObject(i).getString("FirmRegNo").toString()));
                                                                }

                                                                tv_5.setText(Html.fromHtml("Membership Number : "+CA.getJSONObject(i).getString("LicenceNumber").toString()));



                                                                layout_ProjectCA.addView(addView);
                                                        }

                                                        try
                                                        {
                                                                String Development=array.getJSONObject(i).getString("Development");
                                                                JSONArray Developmenta =  new JSONArray(Development);

                                                                for(int h=0; h<Developmenta .length(); h++)
                                                                {

                                                                        LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                                        final View addView = layoutInflater.inflate(R.layout.list_development, null);

                                                                        TextView tv_d_typei = (TextView)addView.findViewById(R.id.tv_d_typei);
                                                                        TextView tv_d_noofi = (TextView)addView.findViewById(R.id.tv_d_noofi);
                                                                        TextView tv_d_ca = (TextView)addView.findViewById(R.id.tv_d_ca);
                                                                        TextView tv_d_exclusiveb = (TextView)addView.findViewById(R.id.tv_d_exclusiveb);
                                                                        TextView tv_d_exclusiveo = (TextView)addView.findViewById(R.id.tv_d_exclusiveo);

                                                                        tv_d_typei.setText(Html.fromHtml("Type Of Inventory : <font color='black'>"+Developmenta.getJSONObject(h).getString("InventoryType")));
                                                                        tv_d_noofi.setText(Html.fromHtml("No of Inventory : <font color='black'>"+Developmenta.getJSONObject(h).getString("InventoryCount")));
                                                                        tv_d_ca.setText(Html.fromHtml("Carpet Area (Sq Mtr) : <font color='black'>"+Developmenta.getJSONObject(h).getString("CarpetArea")));
                                                                        tv_d_exclusiveb.setText(Html.fromHtml("Area of exclusive balcony/varandah (Sq.Mtr) : <font color='black'>"+Developmenta.getJSONObject(h).getString("ExBV")));
                                                                        tv_d_exclusiveo.setText(Html.fromHtml("Area  of exclusive open terrace If any(Sq.Mtr) : <font color='black'>"+Developmenta.getJSONObject(h).getString("ExOT")));

                                                                        layout_DevelopmentDetail.addView(addView);
                                                                }

                                                        }
                                                        catch (Exception e)
                                                        {

                                                        }

                                                        try
                                                        {
                                                                String Form1A=array.getJSONObject(i).getString("QPR");
                                                                final JSONArray Form1Aa =  new JSONArray(Form1A);

                                                                for(int h=0; h<Form1Aa.length(); h++)
                                                                {

                                                                        LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                                        final View addView = layoutInflater.inflate(R.layout.list_forma, null);

                                                                        CardView card_view= (CardView) addView.findViewById(R.id.card_view);
                                                                        TextView txt_enddate = (TextView)addView.findViewById(R.id.txt_enddate);
                                                                        TextView txt_startdate = (TextView)addView.findViewById(R.id.txt_startdate);
                                                                        TextView txt_status = (TextView)addView.findViewById(R.id.txt_status);
                                                                        TextView txt_no = (TextView)addView.findViewById(R.id.txt_no);

                                                                        txt_enddate.setText(Html.fromHtml(Form1Aa.getJSONObject(h).getString("EndDate")));
                                                                        txt_startdate.setText(Html.fromHtml(Form1Aa.getJSONObject(h).getString("StartDate")));
                                                                        txt_no.setText(Html.fromHtml("SR.No : <font color='black'>"+Form1Aa.getJSONObject(h).getString("QPRID")));
                                                                        txt_status.setText(Html.fromHtml(Form1Aa.getJSONObject(h).getString("Status")));

                                                                        final String id= Form1Aa.getJSONObject(h).getString("QPRID");
                                                                        card_view.setOnClickListener(new View.OnClickListener()
                                                                        {
                                                                                @Override
                                                                                public void onClick(View v)
                                                                                {
                                                                                        // Toast.makeText(getApplicationContext(),id,Toast.LENGTH_LONG).show();
                                                                                        Intent i=new Intent(getApplicationContext(),Activity_Form.class);
                                                                                        i.putExtra("QPRID",id);
                                                                                        startActivity(i);
                                                                                }
                                                                        });

                                                                        // layout_formA.addView(addView);
                                                                }

                                                        }
                                                        catch (Exception e)
                                                        {

                                                        }


                                                        try
                                                        {
                                                                String ExternalDevelopment=array.getJSONObject(i).getString("ExternalDevelopment");
                                                                JSONArray ExternalDevelopmenta =  new JSONArray(ExternalDevelopment);

                                                                for(int h=0; h<ExternalDevelopmenta.length(); h++)
                                                                {

                                                                        LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                                        final View addView = layoutInflater.inflate(R.layout.list_desc, null);

                                                                        TextView tv_desc = (TextView)addView.findViewById(R.id.tv_desc);

                                                                        tv_desc.setText(Html.fromHtml("Description : <font color='black'>"+ExternalDevelopmenta.getJSONObject(h).getString("Description")));

                                                                        layout_ExternalDevelopmentWork.addView(addView);
                                                                }
                                                        }
                                                        catch (Exception e)
                                                        {

                                                        }


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
                                                                                                CustomDialogClass cdd=new CustomDialogClass(Activity_consultants_main.this,Blocksa.get(h1)+"");
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




                                                        // String InternalDevelopment1=array.getJSONObject(i).getString("InternalDevelopment");
                                                        // JSONObject InternalDevelopment = new JSONObject(InternalDevelopment1);

                                                        //tv_i_RoadSystem.setText(Html.fromHtml("Road System : <font color='black'>"+InternalDevelopment.getString("RoadSystemName")));
                                                        //tv_i_WaterSupply.setText(Html.fromHtml("Water Supply :  <font color='black'>"+InternalDevelopment.getString("WaterSupplyName")));
                                                        // tv_i_Sewage.setText(Html.fromHtml("Sewage & Drainage System  <font color='black'>"+InternalDevelopment.getString("SewageDrainageName")));
                                                        // tv_i_Electricity.setText(Html.fromHtml("Electricity Supply System : <font color='black'>"+InternalDevelopment.getString("ElectricitySupplyName")));
                                                        //tv_i_Solid.setText(Html.fromHtml("Solid Waste Management & Disposal : <font color='black'>"+InternalDevelopment.getString("SolidWasteManagementName")));

                                                        // tv_d_typei=(TextView)findViewById(R.id.tv_d_typei) ;
                                                        // tv_d_noofi=(TextView)findViewById(R.id.tv_d_noofi) ;
                                                        // tv_d_ca=(TextView)findViewById(R.id.tv_d_ca) ;
                                                        // tv_d_exclusiveb=(TextView)findViewById(R.id.tv_d_exclusiveb) ;
                                                        // tv_d_exclusiveo=(TextView)findViewById(R.id.tv_d_exclusiveo) ;

                                                        try
                                                        {


                                                                String Development=array.getJSONObject(i).getString("Development");
                                                                JSONObject Developmenta =  new JSONObject(Development);

                                                                tv_d_noofgarage.setText(Html.fromHtml("Carpet Area : <font color='black'>"+" "+Developmenta.getString("CarpetArea")));
                                                                tv_d_agarage.setText(Html.fromHtml("Exclusive Balcony/Varandah Area : <font color='black'>"+""+Developmenta.getString("ExBV")));
                                                                tv_d_noofoparking.setText(Html.fromHtml("Exclusive Open Terrace Area (If any) <font color='black'>"+""+Developmenta.getString("ExOT")));
                                                                tv_d_oparking.setText(Html.fromHtml("Garage Area (Sq Mtr) : <font color='black'>"+""+Developmenta.getString("GarageArea")));
                                                                tv_d_noofparking.setText(Html.fromHtml("Open Parking Area (Sq Mtr) : <font color='black'>"+""+Developmenta.getString("OpenParkingArea")));
                                                                tv_d_aparking.setText(Html.fromHtml("Covered Parking Area (Sq Mtr) : <font color='black'>"+" "+Developmenta.getString("CoveredParkingArea")));

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

        private void blocks()
        {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL+"selection/getProjectList/{\"mode\":\"list\",\"ProjectID\":\""+ProjectID+"\",\"Screen\":\"Blocks\"}",
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
                                                        String idt=array.getJSONObject(i).getString("TypeID");
                                                        JSONObject AdditionalDetails = jsonObject_c.getJSONObject("AdditionalDetail");


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
                                                                                                CustomDialogClass cdd=new CustomDialogClass(Activity_consultants_main.this,Blocksa.get(h1)+"");
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
        public String formatedate_server(String time)
        {
                String outputPattern="";
                String inputPattern = "yyyy-MM-dd";

                outputPattern = "dd-MM-yyyy";
                SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
                SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

                Date date = null;
                String str = null;

                try
                {
                        date = inputFormat.parse(time);
                        str = outputFormat.format(date);
                } catch (ParseException e) {
                        e.printStackTrace();
                        str="-";
                }
                return str;
        }
        private void Development()
        {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, Constants.URL+"selection/getProjectList/{\"mode\":\"list\",\"ProjectID\":\""+ProjectID+"\",\"Screen\":\"Development\"}",
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


                                                                String Development=array.getJSONObject(i).getString("Development");
                                                                JSONObject Developmenta =  new JSONObject(Development);

                                                                tv_d_noofgarage.setText(Html.fromHtml("Carpet Area : <font color='black'>"+" "+Developmenta.getString("CarpetArea")));
                                                                tv_d_agarage.setText(Html.fromHtml("Exclusive Balcony/Varandah Area : <font color='black'>"+""+Developmenta.getString("ExBV")));
                                                                tv_d_noofoparking.setText(Html.fromHtml("Exclusive Open Terrace Area (If any) <font color='black'>"+""+Developmenta.getString("ExOT")));
                                                                tv_d_oparking.setText(Html.fromHtml("Garage Area (Sq Mtr) : <font color='black'>"+""+Developmenta.getString("GarageArea")));
                                                                tv_d_noofparking.setText(Html.fromHtml("Open Parking Area (Sq Mtr) : <font color='black'>"+""+Developmenta.getString("OpenParkingArea")));
                                                                tv_d_aparking.setText(Html.fromHtml("Covered Parking Area (Sq Mtr) : <font color='black'>"+" "+Developmenta.getString("CoveredParkingArea")));

                                                        }
                                                        catch (Exception e)
                                                        {

                                                        }


                                                        try
                                                        {
                                                                String ExternalDevelopment=array.getJSONObject(i).getString("ExternalDevelopment");
                                                                JSONArray ExternalDevelopmenta =  new JSONArray(ExternalDevelopment);

                                                                for(int h=0; h<ExternalDevelopmenta.length(); h++)
                                                                {

                                                                        LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                                        final View addView = layoutInflater.inflate(R.layout.list_desc, null);

                                                                        TextView tv_desc = (TextView)addView.findViewById(R.id.tv_desc);

                                                                        tv_desc.setText(Html.fromHtml("Description : <font color='black'>"+ExternalDevelopmenta.getJSONObject(h).getString("Description")));

                                                                        layout_ExternalDevelopmentWork.addView(addView);
                                                                }
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
                                                                Intent i=new Intent(getApplicationContext(),webview_pdf.class);
                                                                i.putExtra("url",BlockPlana.getJSONObject(0).getString("FileURL"));
                                                                startActivity(i);
                                                        }
                                                        else
                                                        {
                                                                Intent i=new Intent(getApplicationContext(),webview.class);
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
                                        tv_b_unit.setText(""+"0");
                                else
                                        tv_b_unit.setText(""+jsonObject.getString("UnitResidential"));

                                if(jsonObject.getString("UnitShop").equalsIgnoreCase("null"))
                                        tv_b_shop.setText(""+"0");
                                else
                                        tv_b_shop.setText(""+jsonObject.getString("UnitShop"));

                                if(jsonObject.getString("UnitOffice").equalsIgnoreCase("null"))
                                        tv_b_offices.setText(""+"0");
                                else
                                        tv_b_offices.setText(""+jsonObject.getString("UnitOffice"));

                                if(jsonObject.getString("UnitPlot").equalsIgnoreCase("null"))
                                        tv_b_ploat.setText(""+"0");
                                else
                                        tv_b_ploat.setText(""+jsonObject.getString("UnitPlot"));

                                if(jsonObject.getString("UnitOther").equalsIgnoreCase("null"))
                                        tv_b_other.setText(""+"0");
                                else
                                        tv_b_other.setText(""+jsonObject.getString("UnitOther"));

                                if(jsonObject.getString("TotalUnits").equalsIgnoreCase("null"))
                                        tv_b_totalu.setText(""+"0");
                                else
                                        tv_b_totalu.setText(""+jsonObject.getString("TotalUnits"));

                                tv_b_unit.setVisibility(View.GONE);
                                tv_b_shop.setVisibility(View.GONE);
                                tv_b_offices.setVisibility(View.GONE);
                                tv_b_ploat.setVisibility(View.GONE);
                                tv_b_other.setVisibility(View.GONE);
                                tv_b_totalu.setVisibility(View.GONE);

                                if(TypeID.equalsIgnoreCase("2") ||TypeID.equalsIgnoreCase("3"))
                                {
                                        tv_b_unit.setVisibility(View.VISIBLE);
                                        tv_b_totalu.setVisibility(View.VISIBLE);
                                }
                                if(TypeID.equalsIgnoreCase("1") ||TypeID.equalsIgnoreCase("3"))
                                {
                                        tv_b_shop.setVisibility(View.VISIBLE);
                                        tv_b_offices.setVisibility(View.VISIBLE);
                                }
                                if(TypeID.equalsIgnoreCase("4") ||TypeID.equalsIgnoreCase("3"))
                                {
                                        tv_b_ploat.setVisibility(View.VISIBLE);
                                        tv_b_totalu.setVisibility(View.VISIBLE);
                                }
                                if(TypeID.equalsIgnoreCase("3") )
                                {
                                        tv_b_other.setVisibility(View.VISIBLE);

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
