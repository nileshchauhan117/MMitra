package com.MilkatMitra.App.Activity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;


import com.MilkatMitra.App.Form3_BankLenders;
import com.MilkatMitra.App.Form3_Centificate;
import com.MilkatMitra.App.Form3_Parking;
import com.MilkatMitra.App.Helper.ConnectionDetector;
import com.MilkatMitra.App.Helper.Constants;
import com.MilkatMitra.App.Helper.FilePath;
import com.MilkatMitra.App.Helper.RealPathUtil;
import com.MilkatMitra.App.Helper.SessionManager;
import com.MilkatMitra.App.R;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * Created by Admin on 04/13/2018.
 */

public class Activity_othercost extends Activity {

    private static final int REQUEST_EXTERNAL_STORAGE = 0;
    File finalFile;
    static String image_path;
    public static File mypath;

    TextView tv_username, tv_from_date, tv_to_date;
    EditText tv_user_name, et_search;
    String DocumentID_d;
    CardView card_view;
    private File pdfFile;
    Button   btn_select_user;
    RelativeLayout layout_f1;
    private BaseFont bfBold;
    SessionManager sm;
    TextView txt_text2,txt_text1,txt_text3;
    String[] arr_headers_all = {"CustName", "SizeTypeDesc", "totaltruckRequired","SourceAdd", "DestinationAdd"};
    String[] arr_key = {"CustName", "SizeTypeDesc", "totaltruckRequired","SourceAdd", "DestinationAdd"};
    String[]  arr_headers_all_pdf ={"CustName", "SizeTypeDesc", "totaltruckRequired","SourceAdd", "DestinationAdd"};

    TableLayout tbl_header, tbl_data;
    ArrayList<HashMap<String, String>> arr_data = new ArrayList<>();
    ArrayList<HashMap<String, String>> arr_data_Final = new ArrayList<>();
    ArrayList<HashMap<String, String>> arr_data_Final_pdf = new ArrayList<>();
    Double total_amt = 0.0;
    Double total_remaining_amt = 0.0;
    Double total_total_amt = 0.0;
    Double total_m1 = 0.0, total_m2 = 0.0, total_m3 = 0.0, total_m4 = 0.0, total_m5 = 0.0, total_m6 = 0.0;

    RelativeLayout rel_report_table;
    RelativeLayout rel_filter;
    LinearLayout ll_date, ll_select_user, rel_filter_view;
    ImageView report_arrow, filter_arrow;

    CheckBox check_group_by;

    Button btn_generate_report;

    boolean report_open = false;
    boolean filter_open = true;

    Calendar myCalendar = Calendar.getInstance();
    Calendar myCalendar1 = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date1, date2;

    int sig=0;
    RadioButton rb_invoice_date, rb_due_date;


    @Override
    public void setFinishOnTouchOutside(boolean finish) {
        super.setFinishOnTouchOutside(finish);
    }

    JSONObject prms;
    //UserFunctions UF;
    ConnectionDetector cd;
    String json_save = "", user_name_selected = "", groupby="", date_type="INV";
    ProgressBar progressdisplay_layout;
    LinearLayout layout_f2_title;
    TextView title_f1;
    TextView tv_form3,tv_form2;
    LinearLayout container;
    String ProjectID="";
    ImageView back;
    ImageView img_pdf;

    RadioButton radio0_q1,radio1_q1,radio0_q2, radio1_q2;
    LinearLayout layout_image;
    RadioGroup radioGroup_q1,radioGroup_q2;
    int pos;
    int pos1;
    ImageView image_Upload;
    Button btn_upload,btnsubmit;
    Uri uri_SavedImage = null;
    String uriSavedImage = "";
    TextView t_one,
            t_two,
            t_three,
            t_four,
            t_five;

    private Uri picUri;
    CheckBox radio_turms;
    LinearLayout layout_questions;
    Spinner spi_mainin;
    String title="";
    String url_m="";
    LinearLayout layout3;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_othercost);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setStatusBarColor(ContextCompat.getColor(Activity_othercost.this, R.color.maroon));
        }
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent intent = getIntent();
        ProjectID = intent.getStringExtra("ProjectID");
        TextView title1;
        layout_questions=(LinearLayout)findViewById(R.id.layout_questions);
        layout3=(LinearLayout)findViewById(R.id.layout3);
        //layout3.setVisibility(View.GONE);
        txt_text1=(TextView)findViewById(R.id.txt_text1) ;
        txt_text2=(TextView)findViewById(R.id.txt_text2) ;
        txt_text3=(TextView)findViewById(R.id.txt_text3) ;
        t_one=(TextView)findViewById(R.id.t_one) ;
        t_two=(TextView)findViewById(R.id.t_two) ;
        t_three=(TextView)findViewById(R.id.t_three) ;
        t_four=(TextView)findViewById(R.id.t_four) ;
        t_five=(TextView)findViewById(R.id.t_five) ;
        layout_questions.setVisibility(View.GONE);
        t_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
                Intent i=new Intent(getApplicationContext(), Activity_InventoryCost.class);
                i.putExtra("ProjectID",ProjectID);
                i.putExtra("page","4");
                startActivity(i);

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
        try
        {
            title1=(TextView)findViewById(R.id.title1);
            title = intent.getStringExtra("title");
            if(title.equalsIgnoreCase(""))
            {
                title1.setText("Form 3");
                url_m=Constants.URL+"selection/getProjectList/{\"mode\":\"list\",\"ProjectID\":\""+ProjectID+"\",\"Screen\":\"OtherCost\"}";

            }
            else
            {
                layout_questions.setVisibility(View.GONE);
                title1.setText(title+" Form 3");
                String qpr_n=title.replace("Quater#","");
                url_m=Constants.URL+"selection/getProjectList/{\"mode\":\"list\",\"ProjectID\":\""+ProjectID+",\"QPRID\":\""+qpr_n+"\",\"Screen\":\"OtherCost\"}";

            }

        }
        catch (Exception e)
        {
            url_m=Constants.URL+"selection/getProjectList/{\"mode\":\"list\",\"ProjectID\":\""+ProjectID+"\",\"Screen\":\"OtherCost\"}";

        }

        spi_mainin=(Spinner)findViewById(R.id.spi_mainin);

        container = (LinearLayout)findViewById(R.id.container);
        progressdisplay_layout=(ProgressBar) findViewById(R.id.progressBar2);
        progressdisplay_layout.setVisibility(View.GONE);
        img_pdf=(ImageView)findViewById(R.id.img_pdf);
        img_pdf.setVisibility(View.GONE);
        img_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.URL+"pdf/form1.php?project=MQ==&qpr=MQ=="));
                startActivity(browserIntent);
            }
        });
        btnsubmit=(Button)findViewById(R.id.btnsubmit);
        radio_turms= (CheckBox) findViewById(R.id.radio_turms);
        radio_turms.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    // perform logic
                    btnsubmit.setVisibility(View.VISIBLE);
                }
                else
                {
                    btnsubmit.setVisibility(View.GONE);
                }

            }
        });
        hideKeyboard(Activity_othercost.this);
        layout_f2_title=(LinearLayout)findViewById(R.id.layout_f2_title);
        layout_f2_title.setVisibility(View.GONE);
        cd = new ConnectionDetector(Activity_othercost.this);
        sm = new SessionManager(Activity_othercost.this);
        card_view = (CardView) findViewById(R.id.card_view);
        title_f1=(TextView)findViewById(R.id.title_f1);
        title_f1.setText("Form 3 (TABLE A)");
        rel_filter = (RelativeLayout) findViewById(R.id.rel_filter);
        tv_form2=(TextView)findViewById(R.id.tv_form2);
        tv_form3=(TextView)findViewById(R.id.tv_form3);
        layout_f1= (RelativeLayout) findViewById(R.id.layout_f1);
        layout_f1.setVisibility(View.GONE);
        rel_report_table = (RelativeLayout) findViewById(R.id.rel_report_table);
        rel_filter_view = (LinearLayout) findViewById(R.id.rel_filter_view);
        ll_select_user = (LinearLayout) findViewById(R.id.ll_select_user);
        ll_date = (LinearLayout) findViewById(R.id.ll_date);

              // t_three=(TextView)findViewById(R.id.t_three);
        // t_four=(TextView)findViewById(R.id.t_four);


        rb_invoice_date = (RadioButton) findViewById(R.id.rb_invoice_date);
        rb_due_date = (RadioButton) findViewById(R.id.rb_due_date);

        check_group_by = (CheckBox) findViewById(R.id.check_group_by);

        report_arrow = (ImageView) findViewById(R.id.report_arrow);
        filter_arrow = (ImageView) findViewById(R.id.filter_arrow);

        tv_user_name = (EditText) findViewById(R.id.tv_user_name);
        et_search = (EditText) findViewById(R.id.et_search);

        btn_generate_report = (Button) findViewById(R.id.btn_generate_report);

        btn_select_user = (Button) findViewById(R.id.btn_select_user);

        tv_username = (TextView) findViewById(R.id.tv_username);
        tv_from_date = (TextView) findViewById(R.id.tv_from_date);
        tv_to_date = (TextView) findViewById(R.id.tv_to_date);

        tbl_header = (TableLayout) findViewById(R.id.tbl_header);
        tbl_data = (TableLayout) findViewById(R.id.tbl_data);

        //tv_username.setText("Hi, "+ Constants.userName);

        rel_report_table.setVisibility(View.VISIBLE);

        back=(ImageView)findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //////////////////////
        radio0_q1=(RadioButton) findViewById(R.id.radio0_q1);
        radio1_q1=(RadioButton) findViewById(R.id.radio1_q1);
        radio0_q2=(RadioButton) findViewById(R.id.radio0_q2);
        radio1_q2=(RadioButton) findViewById(R.id.radio1_q2);
        radioGroup_q1 = (RadioGroup) findViewById(R.id.radioGroup_q1);
        radioGroup_q2 = (RadioGroup) findViewById(R.id.radioGroup_q2);
        layout_image= (LinearLayout) findViewById(R.id.layout_image);

        btn_upload=(Button)findViewById(R.id.btn_upload);
        image_Upload=(ImageView)findViewById(R.id.image_Upload);
        layout_image.setVisibility(View.GONE);
        img_pdf=(ImageView)findViewById(R.id.img_pdf);

        img_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.URL+"pdf/form1.php?project=MQ==&qpr=MQ=="));
                startActivity(browserIntent);
            }
        });
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(Activity_othercost.this)
                        .withPermissions(

                                Manifest.permission.CAMERA,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                // check if all permissions are granted
                                if (report.areAllPermissionsGranted())
                                {
                                    sig=1;
                                    imageUpload();
                                }

                                // check for permanent denial of any permission
                                if (report.isAnyPermissionPermanentlyDenied()) {
                                    // show alert dialog navigating to Settings
                                    //openSettingsDialog();
                                }
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                            }



                        }).
                        withErrorListener(new PermissionRequestErrorListener() {
                            @Override
                            public void onError(DexterError error) {
                                //Toast.makeText(getContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .onSameThread()
                        .check();
            }
        });
        radioGroup_q1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                pos=radioGroup_q1.indexOfChild(findViewById(checkedId));


                //Method 2 For Getting Index of RadioButton
                pos1=radioGroup_q1.indexOfChild(findViewById(radioGroup_q1.getCheckedRadioButtonId()));



                switch (pos)
                {
                    case 0 :
                        layout_image.setVisibility(View.VISIBLE);
                        break;
                    case 1 :
                        layout_image.setVisibility(View.GONE);
                        break;

                    default :
                        //The default selection is RadioButton 1
                        layout_image.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });


        radioGroup_q2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                pos=radioGroup_q2.indexOfChild(findViewById(checkedId));


                //Method 2 For Getting Index of RadioButton
                pos1=radioGroup_q2.indexOfChild(findViewById(radioGroup_q2.getCheckedRadioButtonId()));



                switch (pos)
                {
                    case 0 :
                        user_question();
                        // Toast.makeText(getBaseContext(), "You have Clicked RadioButton 1",Toast.LENGTH_SHORT).show();
                        break;
                    case 1 :
                        user_question();
                        //  Toast.makeText(getBaseContext(), "You have Clicked RadioButton 2",Toast.LENGTH_SHORT).show();
                        break;

                    default :
                        //The default selection is RadioButton 1
                        // Toast.makeText(getBaseContext(),"You have Clicked RadioButton 1" ,/ Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        btnsubmit.setOnClickListener(new View.OnClickListener()
        {


            @Override
            public void onClick(View v) {
                open(v);

            }
        });
        ////////////////////////

        check_group_by.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    user_name_selected = "";
                    tv_user_name.setText("");
                    groupby="name";
                    date_type = "INV";
                    ll_select_user.setVisibility(View.GONE);
                    ll_date.setVisibility(View.VISIBLE);
                } else {
                    rb_invoice_date.setChecked(true);
                    groupby="";
                    date_type = "INV";
                    ll_select_user.setVisibility(View.VISIBLE);
                    ll_date.setVisibility(View.GONE);
                }
            }
        });

        // INV DUE name



        rb_invoice_date.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    date_type = "INV";
                }
            }
        });

        rb_due_date.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    date_type = "DUE";
                }
            }
        });





          /*  report_open = true;
            report_arrow.setImageResource(R.drawable.down_icon);
            rel_report.setVisibility(View.VISIBLE);

            filter_open = false;
            filter_arrow.setImageResource(R.drawable.up_icon);
            card_view.setVisibility(View.GONE);*/



        if (cd.isConnectingToInternet())
        {
            loginUser();
            //new GetJsonBookOrder().execute();
        } else {
            // UF.msg("Internet connection not available.");
        }




        btn_generate_report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {


            }
        });

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                arr_data = new ArrayList<>();
                // {"Invoice No","Company","Name","Invoice Date","Due Date","Amount","Remaining Amount"};
                if(et_search.getText().toString().equals("") || et_search.getText().toString().length() == 0){
                    arr_data.addAll(arr_data_Final);
                } else {
                    for (HashMap<String, String> map : arr_data_Final) {
                        try {
                            for(int ii=0; ii<arr_headers_all.length; ii++){
                                if (map.get(arr_headers_all[ii]).toLowerCase().contains(et_search.getText().toString().toLowerCase())) {
                                    arr_data.add(map);
                                    break;
                                }
                            }
                            /*if (map.get("Invoice No").toLowerCase().contains(et_search.getText().toString().toLowerCase())) {
                                arr_data.add(map);
                            } else if (map.get("Company").toLowerCase().contains(et_search.getText().toString().toLowerCase())) {
                                arr_data.add(map);
                            } *//*else if (map.get("Name").toLowerCase().contains(et_search.getText().toString().toLowerCase())) {
                                arr_data.add(map);
                            } *//*else if (map.get("Invoice Date").toLowerCase().contains(et_search.getText().toString().toLowerCase())) {
                                arr_data.add(map);
                            } else if (map.get("Due Date").toLowerCase().contains(et_search.getText().toString().toLowerCase())) {
                                arr_data.add(map);
                            } else if (map.get("Amount (AED)").toLowerCase().contains(et_search.getText().toString().toLowerCase())) {
                                arr_data.add(map);
                            } else if (map.get("Remaining Amount (AED)").toLowerCase().contains(et_search.getText().toString().toLowerCase())) {
                                arr_data.add(map);
                            }*/
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }
                //  CreateHeader(false);
                // AddedTableData(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
        });

        final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        myCalendar.setTime(date);
//        myCalendar.set(Calendar.YEAR, date.getYear());
//        myCalendar.set(Calendar.MONTH, date.getMonth());
        myCalendar.set(Calendar.DAY_OF_MONTH, 1);
        myCalendar1.setTime(date);
//        myCalendar1.set(Calendar.YEAR, date.getYear());
//        myCalendar1.set(Calendar.MONTH, date.getMonth());
        //myCalendar1.set(Calendar.DAY_OF_MONTH, myCalendar1.getActualMaximum(Calendar.DAY_OF_MONTH));
//        tv_from_date.setText(formatter.format(myCalendar.getTime()));
//        tv_to_date.setText(formatter.format(myCalendar1.getTime()));
        myCalendar1.set(Calendar.DAY_OF_MONTH, myCalendar1.getActualMaximum(Calendar.DAY_OF_MONTH));
        tv_from_date.setText(formatter.format(myCalendar.getTime()));
        tv_to_date.setText(formatter.format(myCalendar1.getTime()));
        date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                tv_from_date.setText(formatter.format(myCalendar.getTime()));
            }

        };

        date2 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                myCalendar1.set(Calendar.DAY_OF_MONTH, myCalendar1.getActualMaximum(Calendar.DAY_OF_MONTH));

                tv_to_date.setText(formatter.format(myCalendar1.getTime()));
            }

        };

        tv_from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(Activity_othercost.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        tv_to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(Activity_othercost.this, date2, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_search.getWindowToken(),
                InputMethodManager.RESULT_UNCHANGED_SHOWN);
//        CreateHeader(false);
//        AddedTableData(false);
    }

    @Override
    protected void onResume()
    {
        super.onResume();


        if (cd.isConnectingToInternet())
        {
            loginUser();
            //new GetJsonBookOrder().execute();
        } else {
            // UF.msg("Internet connection not available.");
        }


    }


    public void CreateHeader(boolean group_by,TableLayout  tbl_header,TableLayout  tbl_data)
    {

        tbl_header.removeAllViews();
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));


        for(int i=0; i<arr_headers_all.length; i++)
        {
            LayoutInflater inflater = LayoutInflater.from(Activity_othercost.this);
            View inflatedLayout = null;
            if(arr_headers_all[i].equalsIgnoreCase("Edit")||arr_headers_all[i].equalsIgnoreCase("Sr.No.")||arr_headers_all[i].equalsIgnoreCase("No.Of Truck"))
                inflatedLayout= inflater.inflate(R.layout.table_header_tv_dashbord1_viewd_s, null, false);
            else if(arr_headers_all[i].equalsIgnoreCase("Particulars"))
                inflatedLayout = inflater.inflate(R.layout.table_header_tv_dashbord, null, false);

            else
                inflatedLayout= inflater.inflate(R.layout.table_header_tv_dashbord1_viewd, null, false);
            TextView tv = (TextView) inflatedLayout.findViewById(R.id.tv_header);

            tv.setText(arr_headers_all[i]);
            tr.addView(inflatedLayout);

        }

        tbl_header.addView(tr);

    }

    public void AddedTableData(boolean group_by , TableLayout  tbl_header, TableLayout  tbl_data, final Spinner tablea_spi)
    {
        total_amt = 0.0;
        total_remaining_amt = 0.0;
        total_total_amt = 0.0;
        total_m1 = 0.0;
        total_m2 = 0.0;
        total_m3 = 0.0;
        total_m4 = 0.0;
        total_m5 = 0.0;
        total_m6 = 0.0;

        tbl_data.removeAllViews();
        int size = 0;
        if(arr_data.size() > 1000){
            size = 1000;
        } else {
            size = arr_data.size();
        }
        final List unitname = new ArrayList<String>();
        final List unitname_str = new ArrayList<String>();
        unitname.add(" Unit Name ");
        unitname_str.add(" Unit Name ");
        for(int j=0; j<size; j++) {

            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

            for (int i = 0; i < arr_headers_all.length; i++)
            {
                LayoutInflater inflater = LayoutInflater.from(Activity_othercost.this);

                View inflatedLayout = null;
                if(arr_headers_all[i].equalsIgnoreCase("Edit")||arr_headers_all[i].equalsIgnoreCase("Sr.No.")||arr_headers_all[i].equalsIgnoreCase("No.Of Truck"))
                    inflatedLayout = inflater.inflate(R.layout.table_data_tv_dashbord1_viewd_s, null, false);

                else if(arr_headers_all[i].equalsIgnoreCase("Particulars"))
                    inflatedLayout = inflater.inflate(R.layout.table_data_tv_dashbord, null, false);

                else
                    inflatedLayout = inflater.inflate(R.layout.table_data_tv_dashbord1_viewd, null, false);

                TextView tv = (TextView) inflatedLayout.findViewById(R.id.tv_data);
                ImageView tv_image= (ImageView) inflatedLayout.findViewById(R.id.tv_image);


                //
                try {
                    if (arr_headers_all[i].equals("Balance"))
                    {
                        try {
                            total_amt = total_amt + Double.parseDouble(arr_data.get(j).get(arr_headers_all[i]));
                        }catch (NumberFormatException nfe){
                            nfe.printStackTrace();
                        }
                        tv.setText(" ₹"+arr_data.get(j).get(arr_headers_all[i]));
                        tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                    }else if(arr_headers_all[i].equalsIgnoreCase("Particulars")||arr_headers_all[i].equalsIgnoreCase("Truck Type")||arr_headers_all[i].equalsIgnoreCase("From")||arr_headers_all[i].equalsIgnoreCase("To"))
                    {

                        tv.setText(arr_data.get(j).get(arr_headers_all[i]));
                        tv.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
                    }
                    else if(arr_headers_all[i].equalsIgnoreCase("Total Cost")||arr_headers_all[i].equalsIgnoreCase("Quarter")||arr_headers_all[i].equalsIgnoreCase("From")||arr_headers_all[i].equalsIgnoreCase("To"))
                    {

                        tv.setText(arr_data.get(j).get(arr_headers_all[i]));
                        tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                    }
                    else if(arr_headers_all[i].equalsIgnoreCase("Unit Name"))
                    {
                        tv.setText(arr_data.get(j).get(arr_headers_all[i]));
                        tv.setGravity(Gravity.CENTER);
                        if(arr_data.get(j).get(arr_headers_all[i]).equalsIgnoreCase(""))
                            Log.e("ddd","ddd");
                        else
                            unitname.add(arr_data.get(j).get(arr_headers_all[i]));


                    }
                    else if(arr_headers_all[i].equalsIgnoreCase("Exclusive Balcony Area"))
                    {

                        tv.setText(arr_data.get(j).get(arr_headers_all[i]));
                        tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                    }

                    else if(arr_headers_all[i].equalsIgnoreCase("Carpet Area"))
                    {

                        tv.setText(arr_data.get(j).get(arr_headers_all[i]));
                        tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                    }
                    else if(arr_headers_all[i].equalsIgnoreCase("Received Amount"))
                    {

                        tv.setText("₹"+arr_data.get(j).get(arr_headers_all[i]));
                        tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                    }

                    else if(arr_headers_all[i].equalsIgnoreCase("Balance Amount"))
                    {

                        tv.setText("₹"+arr_data.get(j).get(arr_headers_all[i]));
                        tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                    }
                    else if(arr_headers_all[i].equalsIgnoreCase("Unit Amount"))
                    {

                        tv.setText("₹"+arr_data.get(j).get(arr_headers_all[i]));
                        tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                    }
                    else if(arr_headers_all[i].equalsIgnoreCase("Status"))
                    {

                        if(arr_data.get(j).get(arr_headers_all[i]).equalsIgnoreCase("U"))
                            tv.setText("Not Booked");
                        else
                            tv.setText("Booked");
                        tv.setGravity(Gravity.CENTER|Gravity.CENTER );
                    }
                    else if(arr_headers_all[i].equalsIgnoreCase("Usage Type"))
                    {

                        if(arr_data.get(j).get(arr_headers_all[i]).equalsIgnoreCase("Residential"))
                            tv.setText("R");
                        else if(arr_data.get(j).get(arr_headers_all[i]).equalsIgnoreCase("Shop"))
                            tv.setText("S");
                        else if(arr_data.get(j).get(arr_headers_all[i]).equalsIgnoreCase("Office"))
                            tv.setText("O");
                        else if(arr_data.get(j).get(arr_headers_all[i]).equalsIgnoreCase("Plot"))
                            tv.setText("P");
                        else
                            tv.setText("R");
                        tv.setGravity(Gravity.CENTER|Gravity.CENTER );
                    }
                    else if(arr_headers_all[i].equalsIgnoreCase("Edit"))
                    {

                        tv_image.setVisibility(View.VISIBLE);
                        tv_image.setImageDrawable(getResources().getDrawable(R.drawable.view_detail1));
                        tv.setVisibility(View.GONE);
                        tv.setText("Edit");
                        tv.setTextColor(this.getResources().getColor(R.color.dark_skyblue));
                        tv.setGravity(Gravity.CENTER|Gravity.CENTER);
                        final String s=arr_data.get(j).get(arr_headers_all[i]);
                        unitname_str.add(s);
                        tv_image.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v)
                            {
                                try
                                {
                                    CustomDialogClass cdd=new CustomDialogClass(Activity_othercost.this,s);

                                    cdd.show();

                                }
                                catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                                //Toast.makeText(getApplicationContext(),"dddd",Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                    else if (arr_headers_all[i].equals("Remaining Amount (AED)")) {
                        try {
                            total_remaining_amt = total_remaining_amt + Double.parseDouble(arr_data.get(j).get(arr_headers_all[i]));
                        }catch (NumberFormatException nfe){
                            nfe.printStackTrace();
                        }
                        tv.setText(arr_data.get(j).get(arr_headers_all[i]));
                        tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                    } else if (arr_headers_all[i].equals("Pending Days")) {
                        tv.setText(arr_data.get(j).get(arr_headers_all[i]));
                        tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                    } else if (arr_headers_all[i].equals("0-30")) {
                        try {
                            total_m1 = total_m1 + Double.parseDouble(arr_data.get(j).get(arr_headers_all[i]));
                        }catch (NumberFormatException nfe){
                            nfe.printStackTrace();
                        }
                        tv.setText(arr_data.get(j).get(arr_headers_all[i]));
                        tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                    } else if (arr_headers_all[i].equals("30-60")) {
                        try {
                            total_m2 = total_m2 + Double.parseDouble(arr_data.get(j).get(arr_headers_all[i]));
                        }catch (NumberFormatException nfe){
                            nfe.printStackTrace();
                        }
                        tv.setText(arr_data.get(j).get(arr_headers_all[i]));
                        tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                    } else if (arr_headers_all[i].equals("60-90")) {
                        try {
                            total_m3 = total_m3 + Double.parseDouble(arr_data.get(j).get(arr_headers_all[i]));
                        }catch (NumberFormatException nfe){
                            nfe.printStackTrace();
                        }
                        tv.setText(arr_data.get(j).get(arr_headers_all[i]));
                        tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                    } else if (arr_headers_all[i].equals("90-180")) {
                        try {
                            total_m4 = total_m4 + Double.parseDouble(arr_data.get(j).get(arr_headers_all[i]));
                        }catch (NumberFormatException nfe){
                            nfe.printStackTrace();
                        }
                        tv.setText(arr_data.get(j).get(arr_headers_all[i]));
                        tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                    } else if (arr_headers_all[i].equals("180-365")) {
                        try {
                            total_m5 = total_m5 + Double.parseDouble(arr_data.get(j).get(arr_headers_all[i]));
                        }catch (NumberFormatException nfe){
                            nfe.printStackTrace();
                        }
                        tv.setText(arr_data.get(j).get(arr_headers_all[i]));
                        tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                    } else if (arr_headers_all[i].equals(">365")) {
                        try {
                            total_m6 = total_m6 + Double.parseDouble(arr_data.get(j).get(arr_headers_all[i]));
                        }catch (NumberFormatException nfe){
                            nfe.printStackTrace();
                        }
                        tv.setText(arr_data.get(j).get(arr_headers_all[i]));
                        tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                    } else if (arr_headers_all[i].equals("Total (AED)")) {
                        try {
                            total_total_amt = total_total_amt + Double.parseDouble(arr_data.get(j).get(arr_headers_all[i]));
                        }catch (NumberFormatException nfe){
                            nfe.printStackTrace();
                        }
                        tv.setText(arr_data.get(j).get(arr_headers_all[i]));
                        tv.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
                    } else if (arr_headers_all[i].equals("Invoice Date") || arr_headers_all[i].equals("Due Date")) {
                        try {
                            String[] arr = arr_data.get(j).get(arr_headers_all[i]).split(" ");
                            String[] date = arr[0].split("\\/");
                            if(date[0].length() == 1){
                                date[0] = "0"+date[0];
                            }
                            if(date[1].length() == 1){
                                date[1] = "0"+date[1];
                            }
                            tv.setText(date[1]+"-"+date[0]+"-"+date[2]);
                        }catch (IndexOutOfBoundsException iobe){
                            iobe.printStackTrace();
                            tv.setText(arr_data.get(j).get(arr_headers_all[i]));
                        }catch (Exception nfe){
                            nfe.printStackTrace();
                            tv.setText(arr_data.get(j).get(arr_headers_all[i]));
                        }
                        tv.setGravity(Gravity.CENTER);
                    } else {
                        tv.setText(arr_data.get(j).get(arr_headers_all[i]));
                        tv.setGravity(Gravity.CENTER);
                    }

                    tr.addView(inflatedLayout);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }



            tbl_data.addView(tr);
        }
        ArrayAdapter<String> adp2 = new ArrayAdapter<String>
                (this, android.R.layout.simple_dropdown_item_1line, unitname);
        tablea_spi.setAdapter(adp2);


        tablea_spi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3)
            {
                // TODO Auto-generated method stub
                String s=unitname_str.get(arg2).toString();
                //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                if(s.equalsIgnoreCase(" Unit Name "))
                {

                }
                else
                {
                    // tablea_spi.setEnabled(true);
                    CustomDialogClass cdd=new CustomDialogClass(Activity_othercost.this,s);
                    cdd.show();
                }

                // CustomDialogClass cdd=new CustomDialogClass(Activity_Form3.this,s);
                // cdd.show();



            }

            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });



    }

    private boolean mayRequestStorage() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        /*if (shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE)) {
            Snackbar.make(btn_excel, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
                        }
                    });
        } */else {
            if (checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
            else
                requestPermissions(new String[]{READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // btn_excel.performClick();
            }
        }
    }




    private void generatePDF(String personName){

        //create a new document
        Document document = new Document();

        try {

            PdfWriter docWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();


            PdfContentByte cb = docWriter.getDirectContent();
            //initialize fonts for text printing
            initializeFonts();

            //the company logo is stored in the assets which is read only
            //get the logo and print on the document
            InputStream inputStream = getAssets().open("olympic_logo.png");
            Bitmap bmp = BitmapFactory.decodeStream(inputStream);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image companyLogo = Image.getInstance(stream.toByteArray());
            companyLogo.setAbsolutePosition(25,700);
            companyLogo.scalePercent(25);
            document.add(companyLogo);

            //creating a sample invoice with some customer data
            createHeadings(cb,400,780,"Company Name");
            createHeadings(cb,400,765,"Address Line 1");
            createHeadings(cb,400,750,"Address Line 2");
            createHeadings(cb,400,735,"City, State - ZipCode");
            createHeadings(cb,400,720,"Country");

            //list all the products sold to the customer
            float[] columnWidths = {1.5f, 2f, 5f, 2f,2f};
            //create PDF table with the given widths
            PdfPTable table = new PdfPTable(columnWidths);
            // set table width a percentage of the page width
            table.setTotalWidth(500f);

            PdfPCell cell = new PdfPCell(new Phrase("Qty"));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Item Number"));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Item Description"));
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Price"));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Ext Price"));
            cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
            table.addCell(cell);
            table.setHeaderRows(1);

            DecimalFormat df = new DecimalFormat("0.00");
            for(int i=0; i < 15; i++ ){
                double price = Double.valueOf(df.format(Math.random() * 10));
                double extPrice = price * (i+1) ;
                table.addCell(String.valueOf(i+1));
                table.addCell("ITEM" + String.valueOf(i+1));
                table.addCell("Product Description - SIZE " + String.valueOf(i+1));
                table.addCell(df.format(price));
                table.addCell(df.format(extPrice));
            }

            //absolute location to print the PDF table from
            table.writeSelectedRows(0, -1, document.leftMargin(), 650, docWriter.getDirectContent());

            //print the signature image along with the persons name
            inputStream = getAssets().open("signature.png");
            bmp = BitmapFactory.decodeStream(inputStream);
            stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image signature = Image.getInstance(stream.toByteArray());
            signature.setAbsolutePosition(400f, 150f);
            signature.scalePercent(25f);
            document.add(signature);

            createHeadings(cb,450,135,personName);

            document.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }

        //PDF file is now ready to be sent to the bluetooth printer using PrintShare
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setPackage("com.dynamixsoftware.printershare");
        i.setDataAndType(Uri.fromFile(pdfFile),"application/pdf");
        startActivity(i);

    }
    private void createHeadings(PdfContentByte cb, float x, float y, String text){

        cb.beginText();
        cb.setFontAndSize(bfBold, 8);
        cb.setTextMatrix(x,y);
        cb.showText(text.trim());
        cb.endText();

    }
    private void initializeFonts(){

        try {
            bfBold = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addTitlePage(Document document) throws DocumentException, IOException, ParseException {
        // Font Style for Document
        Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 22, Font.BOLD
                | Font.UNDERLINE, BaseColor.GRAY);
        Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
        Font normal = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD);
        Font normal3 = new Font(Font.FontFamily.TIMES_ROMAN, 12);

        Font normal1 = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD,BaseColor.ORANGE);
        Font normal2 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD,BaseColor.GRAY);
        // Start New Paragraph
        //Paragraph prHead = new Paragraph();
        //prHead.setFont(titleFont);
        //prHead.add("RESUME – Name\n");

        PdfPTable myTable = new PdfPTable(1);
        myTable.setWidthPercentage(100.0f);

        // Create New Cell into Table
        PdfPCell myCell = new PdfPCell(new Paragraph(""));
        myCell.setBorder(Rectangle.BOTTOM);
        myTable.addCell(myCell);

        //prHead.setFont(catFont);
        //prHead.add("\nName1 Name2\n");
        //prHead.setAlignment(Element.ALIGN_CENTER);

        // Add all above details into Document
        //document.add(prHead);
        document.add(myTable);

        document.add(myTable);


        InputStream inputStream = getAssets().open("applogo.png");
        Bitmap bmp = BitmapFactory.decodeStream(inputStream);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        Image companyLogo = Image.getInstance(stream.toByteArray());
        companyLogo.setAbsolutePosition(500,740);
        companyLogo.scalePercent(35);
        companyLogo.setAlignment(Element.ALIGN_RIGHT);
        //document.add(companyLogo);
        //  Chunk glue = new Chunk(new VerticalPositionMark());

        Paragraph prPersinalInfo1 = new Paragraph();
        prPersinalInfo1.setFont(normal1);
        prPersinalInfo1.add("TRUKKER TECHNOLOGIES\n");


        prPersinalInfo1.setAlignment(Element.ALIGN_LEFT);

        Paragraph prPersinalInfo = new Paragraph();
        prPersinalInfo.setFont(normal3);
        prPersinalInfo.add("O?ce : 204, 1 Lake Plaza, Cluster T,Jumeirah Lake Towers, Dubai.\n");
        prPersinalInfo.add("Tel : +971 600-524642, Email : contact@trukker.ae WEB : www.TRUKKER.ae\n");
        prPersinalInfo.add("\n");


        prPersinalInfo.setAlignment(Element.ALIGN_LEFT);
        document.add(prPersinalInfo1);
        document.add(companyLogo);
        document.add(prPersinalInfo);
        document.add(myTable);

        document.add(myTable);
        document.add(myTable);
        Paragraph prPersinalInfo2 = new Paragraph();
        prPersinalInfo2.setFont(normal);
        prPersinalInfo2.add("Statement of Account\n\n");
        prPersinalInfo2.setAlignment(Element.ALIGN_CENTER);
        prPersinalInfo2.setFont(normal);
        Paragraph prPersinalInfo3 = new Paragraph();
        prPersinalInfo3.setFont(normal);
        prPersinalInfo3.add("Customer Name :"+tv_user_name.getText().toString()+"\n\n");
        prPersinalInfo3.setAlignment(Element.ALIGN_CENTER);


        document.add(prPersinalInfo2);
        document.add(myTable);
        document.add(myTable);
        document.add(prPersinalInfo3);
        document.add(myTable);

        Paragraph prPersinalInfo4 = new Paragraph();
        PdfPTable t = new PdfPTable(6);
        t.setSpacingBefore(5);
        t.setSpacingAfter(5);

        PdfPCell c1 = new PdfPCell(new Phrase("INV DATE"));
        t.addCell(c1);


        PdfPCell c2 = new PdfPCell(new Phrase("INV NO"));
        t.addCell(c2);

        PdfPCell c3 = new PdfPCell(new Phrase("SCOPE"));
        t.addCell(c3);

        PdfPCell c4 = new PdfPCell(new Phrase("Invoice Amount\n" +"(AED)"));
        t.addCell(c4);

        PdfPCell c5 = new PdfPCell(new Phrase("Pending Balance\n"+"(AED)"));
        t.addCell(c5);

        PdfPCell c6 = new PdfPCell(new Phrase("Invoice Pending Days"));
        t.addCell(c6);

        double amount=0.0;
        double re_amount=0.0;


        for(int i=0;i<arr_data_Final_pdf.size();i++)
        {
            for(int j=0;j<arr_headers_all_pdf.length;j++)
            {
                if(arr_headers_all_pdf[j].toString().equalsIgnoreCase("net_total_amt"))
                {
                    amount=amount+Double.parseDouble(arr_data_Final_pdf.get(i).get(arr_headers_all_pdf[j]));
                    t.addCell(arr_data_Final_pdf.get(i).get(arr_headers_all_pdf[j]));
                }
                else if(arr_headers_all_pdf[j].toString().equalsIgnoreCase("rem_amt_to_receive"))
                {
                    re_amount=amount+Double.parseDouble(arr_data_Final_pdf.get(i).get(arr_headers_all_pdf[j]));
                    t.addCell(arr_data_Final_pdf.get(i).get(arr_headers_all_pdf[j]));
                }
                else if(arr_headers_all_pdf[j].toString().equalsIgnoreCase("doc_date"))
                {
                    t.addCell(getdate(arr_data_Final_pdf.get(i).get(arr_headers_all_pdf[j])));
                }
                else if(arr_headers_all_pdf[j].toString().equalsIgnoreCase("order_type"))
                {
                    String order_type = arr_data_Final_pdf.get(i).get(arr_headers_all_pdf[j]);
                    if(order_type.equalsIgnoreCase("H"))
                        t.addCell("Home Moving");
                    else if(order_type.equalsIgnoreCase("GL"))
                        t.addCell("Goods Moving");
                    else if(order_type.equalsIgnoreCase("HT"))
                        t.addCell("Hire Truck");
                    else if(order_type.equalsIgnoreCase("GD"))
                        t.addCell("Item Delivery");
                    else if(order_type.equalsIgnoreCase("CB"))
                        t.addCell("Cross Border");


                }
                else
                {
                    t.addCell(arr_data_Final_pdf.get(i).get(arr_headers_all_pdf[j]));
                }



            }
        }

        t.addCell("");
        t.addCell("");
        t.addCell("");
        t.addCell(roundTwoDecimals(amount)+"");
        t.addCell(roundTwoDecimals(re_amount)+"");
        t.addCell("");

        prPersinalInfo4.add(t);

        /////////
        //////

        document.add(myTable);
        document.add(prPersinalInfo4);
        document.add(myTable);

        Paragraph prProfile = new Paragraph();
        Paragraph prProfile1 = new Paragraph();
        prProfile.setFont(smallBold);
        prProfile.add("\n \n Kindly request to Contact Trukker Office or Send a Email to Accounts@trukker.ae for Chq Collection \n ");
        prProfile1.add("\n** The Above Statement will be deemed valid and accepted unless any clarification is received within 48 hours of receipt");
        prProfile.setFont(normal3);
        document.add(prProfile);
        document.add(prProfile1);
        // Create new Page in PDF
        document.newPage();
    }
    public String getdate(String dated) throws ParseException {

        DateFormat srcDf = new SimpleDateFormat("MM/dd/yyyy hh:mm:s a");
        Date date = srcDf.parse(dated);
        DateFormat destDf = new SimpleDateFormat("dd-MM-yyyy");
        String fdate= destDf.format(date);
        return fdate;
    }
    double roundTwoDecimals(double d)
    {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }
    public static boolean isDateAfter(String startDate,String endDate)
    {
        try
        {
            String myFormatString = "dd-MM-yyyy"; // for example
            SimpleDateFormat df = new SimpleDateFormat(myFormatString);
            Date date1 = df.parse(endDate);
            Date startingDate = df.parse(startDate);

            if (date1.after(startingDate))
                return true;
            else
                return false;
        }
        catch (Exception e)
        {

            return false;
        }
    }

    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    String CompletionStatus="";
    private void loginUser()
    {
        progressdisplay_layout.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url_m,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {

                        layout_f1.setVisibility(View.VISIBLE);
                        progressdisplay_layout.setVisibility(View.GONE);
                        // Toast.makeText(Activity_Authorized.this,response,Toast.LENGTH_LONG).show();
                        Log.e("-----------",response+"");
                        try
                        {

                            container.removeAllViews();
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("Code").equals("200"))
                            {
                                JSONArray array1 = new JSONArray();

                                array1 = jsonObject.getJSONArray("ProjectDetail");
                                try {

                                    CompletionStatus = array1.getJSONObject(0).getString("F3QPRCompletionStatus");

                                }
                                catch (Exception e)
                                {
                                }

                                String res=array1.getJSONObject(0).getString("OtherCost");

                                JSONObject Quarter = new JSONObject(res);
                              //  txt_text1.setText(Html.fromHtml("Current date : <font color='black'>"+Quarter.getString("TodayDate").toString()));
                                final JSONArray array = Quarter.getJSONArray("Data");
                                final List unitname_main = new ArrayList<String>();
                                unitname_main.clear();
                                for(int k=0;k<array.length();k++)
                                {

                                    //String Costing=array.getJSONObject(k).getString("Costing").toString();
                                    // JSONArray Costinga = new JSONArray(res);

                                    unitname_main.add(array.getJSONObject(k).getString("QuarterNumber").toString());
                                }
                                ArrayAdapter<String> adp2 = new ArrayAdapter<String>
                                        (getApplicationContext(), android.R.layout.simple_dropdown_item_1line, unitname_main);
                                spi_mainin.setAdapter(adp2);

                                try
                                {
                                    for(int k=0;k<array.length();k++)
                                    {
                                        LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        final View addView = layoutInflater.inflate(R.layout.list_inventory, null);
                                       // final TextView title_f1 = (TextView)addView.findViewById(R.id.title_f1);
                                       // title_f1.setText(array.getJSONObject(k).getString("BlockName").toString());


                                        Spinner tablea_spi=(Spinner)addView.findViewById(R.id.tablea_spi);
                                        tablea_spi.setVisibility(View.GONE);
                                        try
                                        {
                                            TableLayout tbl_header, tbl_data;
                                            tbl_header = (TableLayout)addView. findViewById(R.id.tbl_header);
                                            tbl_data = (TableLayout)addView. findViewById(R.id.tbl_data);
                                            final RelativeLayout layout_de=(RelativeLayout)addView.findViewById(R.id.layout_de);
                                            LinearLayout layout_title=(LinearLayout)addView.findViewById(R.id.layout_title);


                                            spi_mainin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                                public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3)
                                                {
                                                    // TODO Auto-generated method stub
                                                    container.removeAllViews();
                                                    String s=unitname_main.get(arg2).toString();
                                                    //   Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();


                                                    try
                                                    {
                                                        for(int k=0;k<array.length();k++)
                                                        {
                                                            LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                                            final View addView = layoutInflater.inflate(R.layout.list_inventory, null);
                                                            final TextView title_f1 = (TextView) addView.findViewById(R.id.title_f1);
                                                            title_f1.setText(array.getJSONObject(k).getString("QuarterNumber").toString());

                                                            Spinner tablea_spi = (Spinner) addView.findViewById(R.id.tablea_spi);
                                                            try
                                                            {

                                                                TableLayout tbl_header, tbl_data;
                                                                tbl_header = (TableLayout) addView.findViewById(R.id.tbl_header);
                                                                tbl_data = (TableLayout) addView.findViewById(R.id.tbl_data);
                                                                final RelativeLayout layout_de = (RelativeLayout) addView.findViewById(R.id.layout_de);
                                                                LinearLayout layout_title = (LinearLayout) addView.findViewById(R.id.layout_title);


                                                                if(array.getJSONObject(k).getString("QuarterNumber").toString().equalsIgnoreCase(s))
                                                                {
                                                                    layout_title.setVisibility(View.GONE);
                                                                    layout_de.setVisibility(View.VISIBLE);
                                                                }
                                                                else
                                                                {
                                                                    layout_title.setVisibility(View.GONE);
                                                                    layout_de.setVisibility(View.GONE);
                                                                }

                                                                //Heenali


                                                                String re=array.getJSONObject(k).getString("Costing");
                                                                JSONArray Units = new JSONArray(re);


                                                                arr_data = new ArrayList<>();
                                                                // arr_headers_all = new String[]{"Sr.No.","Name","CompletionDate","Period","ReceivableCost" };
                                                                //arr_key = new String[]{"Sr.No.","Name", "CompletionDate","QuarterNumber","ReceivableCost"};

                                                                arr_headers_all = new String[]{"Sr.No.","Particulars","Total Cost","Quarter" };
                                                                arr_key = new String[]{"Sr.No.","Name","TotalCost","Cost" };


                                                                for (int i = 0; i < Units.length(); i++)
                                                                {
                                                                    HashMap<String, String> hmap = new HashMap<>();
                                                                    HashMap<String, String> hmap1 = new HashMap<>();

                                                                    for (int j = 0; j < arr_headers_all.length; j++)
                                                                    {

                                                                        if(Units.getJSONObject(i).has(arr_key[j]))
                                                                        {
                                                                            if(arr_headers_all[j].equalsIgnoreCase("% Receivable Limit"))
                                                                                hmap.put(arr_headers_all[j], Units.getJSONObject(i).getString(arr_key[j]).toString()+"%");
                                                                            else if(arr_headers_all[j].equalsIgnoreCase("Total Cost")||arr_headers_all[j].equalsIgnoreCase("Quarter"))
                                                                                hmap.put(arr_headers_all[j], "₹"+Units.getJSONObject(i).getString(arr_key[j]).toString());
                                                                            else
                                                                                hmap.put(arr_headers_all[j], Units.getJSONObject(i).getString(arr_key[j]).toString());

                                                                        }

                                                                        else
                                                                        {
                                                                            if(arr_headers_all[j].equalsIgnoreCase("Edit"))
                                                                            {
                                                                                hmap.put(arr_headers_all[j], Units.getJSONObject(i)+"");
                                                                            }
                                                                            else if(arr_headers_all[j].equalsIgnoreCase("Sr.No."))
                                                                            {
                                                                                hmap.put(arr_headers_all[j], i+1+"");
                                                                            }
                                                                            else
                                                                            {
                                                                                hmap.put(arr_headers_all[j],"-");
                                                                            }


                                                                        }
                                                                    }

                                                                    arr_data.add(hmap);

                                                                }

                                                                CreateHeader(false,tbl_header,tbl_data);
                                                                AddedTableData(false,tbl_header,tbl_data,tablea_spi);
                                                            }
                                                            catch (Exception e)
                                                            {

                                                            }
                                                            container.addView(addView);

                                                        }
                                                    }
                                                    catch (Exception e)
                                                    {

                                                    }



                                                }

                                                public void onNothingSelected(AdapterView<?> arg0) {
                                                    // TODO Auto-generated method stub

                                                }
                                            });


                                            if(k==0)
                                            {
                                                layout_title.setVisibility(View.GONE);
                                                layout_de.setVisibility(View.VISIBLE);
                                            }
                                            else
                                            {
                                                layout_title.setVisibility(View.GONE);
                                                layout_de.setVisibility(View.GONE);
                                            }

                                            layout_title.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v)
                                                {
                                                    if (layout_de.getVisibility() == View.VISIBLE) {
                                                        // Its visible
                                                        layout_de.setVisibility(View.GONE);
                                                    } else {
                                                        layout_de.setVisibility(View.VISIBLE);
                                                        // Either gone or invisible
                                                    }


                                                }
                                            });



                                        }
                                        catch (Exception e)
                                        {

                                        }

                                        container.addView(addView);
                                    }




                                }
                                catch (Exception e)
                                {

                                }



                              /*  arr_data = new ArrayList<>();
                                arr_data_Final = new ArrayList<>();
                                arr_data_Final_pdf = new ArrayList<>();



                                arr_headers_all = new String[]{"Block Name", "Estimated Cost", "Incurred Cost","Incurred Percentage", "Balance Cost","Other"};
                                arr_key = new String[]{"BlockName", "EstimatedCost", "IncurredCost","IncurredPercentage", "Balance","Other"};

                                for (int i = 0; i < array.length(); i++)
                                {

                                    // Points.getJSONObject(h).getString("Name");
                                    HashMap<String, String> hmap = new HashMap<>();
                                    HashMap<String, String> hmap1 = new HashMap<>();

                                    for (int j = 0; j < arr_headers_all.length; j++)
                                    {
                                        if(array.getJSONObject(i).has(arr_key[j]))
                                        {
                                            hmap.put(arr_headers_all[j], array.getJSONObject(i).getString(arr_key[j]).toString());
                                        }
                                        else if(arr_key[j].toString().equalsIgnoreCase("EstimatedCost"))
                                        {
                                            String Costing=array.getJSONObject(0).getString("Costing").toString();
                                            JSONObject Costingj = new JSONObject(Costing);
                                            hmap.put(arr_headers_all[j], Costingj.getString(arr_key[j]).toString());
                                        }
                                        else
                                        {

                                            String Costing=array.getJSONObject(0).getString("Costing").toString();
                                            JSONObject Costingj = new JSONObject(Costing);
                                            hmap.put(arr_headers_all[j], Costingj.getString(arr_key[j]).toString());
                                        }


                                    }
                                    arr_data_Final_pdf.add(hmap1);
                                    arr_data.add(hmap);
                                    arr_data_Final.add(hmap);
                                }
                                if(arr_data.size() > 0) {
                                    rel_report_table.setVisibility(View.VISIBLE);
                                }
                                CreateHeader(false);
                                AddedTableData(false);

                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(et_search.getWindowToken(),InputMethodManager.RESULT_UNCHANGED_SHOWN);
                                //  else
                                //{
                                //  UF.msg("No Data Found.");
                                //   rel_filter.performClick();
                                //   rel_report_table.setVisibility(View.GONE);
                                //   arr_data.clear();
                                // }

                                ////////////////*/


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
    String KYCID_d,
            AFSID_d,
            PhotoID;
    int counter=1;
    ImageView image_KYCUpload,
            img_AFSUpload,
            image_PhotoUpload;
    public class CustomDialogClass extends Dialog implements
            View.OnClickListener {

        public Activity c;
        public Dialog d;
        String value;
        TextView txt_afsdate;
        TextView btn_close;
        Spinner tv_KYCDocument;
        EditText

                tv_KYCNumber,
                tv_MobileNumber,
                tv_AllotteeName,

        tv_ramount,
                tv_uamount,
                tv_uname,

        tv_area,
                tv_balcony
                        ;

        TextView tv_Redevelopment,tv_status,
                tv_EncumbranceStatusr,
                tv_balance,
                tv_type;
        int pos;
        int pos1;
        RadioGroup radioGroup1_tv_Redevelopment,
                radioGroup1_tv_EncumbranceStatusr,radioGroup1_tv_type;

        LinearLayout layout_moredetails,view_moredetail;

        String Redevelopment="",EncumbranceStatusr="N",typed="";
        RadioButton
                tv_type1,
                tv_type2,

        tv_Redevelopment_y,
                tv_Redevelopment_n,

        tv_EncumbranceStatusr1,
                tv_EncumbranceStatusr2,
                tv_EncumbranceStatusr3;
        ProgressBar progressBar_main;
        SessionManager sm;
        Map<String,String> params = new HashMap<String, String>();
        Button btn_save;
        LinearLayout layout_aname,layout_ramount,
                layout_mno,layout_afsdate;
        TextView kyc_title;
        ImageView back_btn;
        String UnitID;
        String Status_d;
        TextView txt_date;
        String
                AFSDate,
                AFSStatus,
                BlockID,
                RowID;

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
            setContentView(R.layout.list_inventory_destail);
            txt_afsdate=(TextView)findViewById(R.id.txt_afsdate);
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            btn_save=(Button)findViewById(R.id.btn_save);
            back_btn=(ImageView) findViewById(R.id.back_btn);
            back_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            tv_type1=(RadioButton)findViewById(R.id.tv_type1);
            layout_afsdate=(LinearLayout)findViewById(R.id.layout_afsdate);
            txt_date=(TextView) findViewById(R.id.txt_date);
            tv_type2=(RadioButton)findViewById(R.id.tv_type2);
            progressBar_main = (ProgressBar) findViewById(R.id.progressBar_main);
            sm = new SessionManager(Activity_othercost.this);
            tv_Redevelopment_y=(RadioButton)findViewById(R.id.tv_Redevelopment_y);
            tv_Redevelopment_n =(RadioButton)findViewById(R.id.tv_Redevelopment_n);
            layout_aname=(LinearLayout)findViewById(R.id.layout_aname);
            layout_ramount=(LinearLayout)findViewById(R.id.layout_ramount);
            layout_mno=(LinearLayout)findViewById(R.id.layout_mno);
            tv_EncumbranceStatusr1=(RadioButton)findViewById(R.id.tv_EncumbranceStatusr1);
            tv_EncumbranceStatusr2=(RadioButton)findViewById(R.id.tv_EncumbranceStatusr2);
            tv_EncumbranceStatusr3=(RadioButton)findViewById(R.id.tv_EncumbranceStatusr3);
            tv_MobileNumber=(EditText) findViewById(R.id.tv_MobileNumber) ;
            tv_AllotteeName=(EditText) findViewById(R.id.tv_AllotteeName) ;
            tv_balance=(TextView)findViewById(R.id.tv_balance) ;
            tv_ramount=(EditText) findViewById(R.id.tv_ramount) ;
            tv_uamount=(EditText) findViewById(R.id.tv_uamount) ;
            tv_Redevelopment=(TextView)findViewById(R.id.tv_Redevelopment) ;
            tv_EncumbranceStatusr=(TextView)findViewById(R.id.tv_EncumbranceStatusr) ;

            tv_KYCDocument=(Spinner) findViewById(R.id.tv_KYCDocument) ;
            tv_KYCNumber=(EditText) findViewById(R.id.tv_KYCNumber) ;
            radioGroup1_tv_EncumbranceStatusr=(RadioGroup) findViewById(R.id.radioGroup1_tv_EncumbranceStatusr);
            radioGroup1_tv_Redevelopment=(RadioGroup) findViewById(R.id.radioGroup1_tv_Redevelopment);
            radioGroup1_tv_type=(RadioGroup) findViewById(R.id.radioGroup1_tv_type);
            kyc_title=(TextView)findViewById(R.id.kyc_title);
            view_moredetail=(LinearLayout) findViewById(R.id.view_moredetail) ;
            layout_moredetails=(LinearLayout) findViewById(R.id.layout_moredetails) ;
            image_KYCUpload=(ImageView) findViewById(R.id.image_KYCUpload) ;
            img_AFSUpload=(ImageView) findViewById(R.id.img_AFSUpload) ;
            image_PhotoUpload=(ImageView) findViewById(R.id.image_PhotoUpload) ;
            final Calendar myCalendar = Calendar.getInstance();
            final DatePickerDialog.OnDateSetListener date5 = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    txt_date.setText(dayOfMonth+"-"+monthOfYear+1+"-"+year);
                    //siteinspection();


                }

            };
            txt_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    new DatePickerDialog(Activity_othercost.this, date5, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                }
            });




            image_KYCUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    hideKeyboardFrom(getApplicationContext(),image_KYCUpload);
                    Dexter.withActivity(Activity_othercost.this)
                            .withPermissions(

                                    Manifest.permission.CAMERA,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE)
                            .withListener(new MultiplePermissionsListener() {
                                @Override
                                public void onPermissionsChecked(MultiplePermissionsReport report) {
                                    // check if all permissions are granted
                                    if (report.areAllPermissionsGranted())
                                    {
                                        counter=1;
                                        imageUpload();
                                    }

                                    // check for permanent denial of any permission
                                    if (report.isAnyPermissionPermanentlyDenied()) {
                                        // show alert dialog navigating to Settings
                                        //openSettingsDialog();
                                    }
                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                                }



                            }).
                            withErrorListener(new PermissionRequestErrorListener() {
                                @Override
                                public void onError(DexterError error) {
                                    //Toast.makeText(getContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .onSameThread()
                            .check();

                }
            });
            image_PhotoUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    hideKeyboardFrom(getApplicationContext(),image_KYCUpload);
                    Dexter.withActivity(Activity_othercost.this)
                            .withPermissions(

                                    Manifest.permission.CAMERA,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE)
                            .withListener(new MultiplePermissionsListener() {
                                @Override
                                public void onPermissionsChecked(MultiplePermissionsReport report) {
                                    // check if all permissions are granted
                                    if (report.areAllPermissionsGranted())
                                    {
                                        counter=3;
                                        imageUpload();
                                    }

                                    // check for permanent denial of any permission
                                    if (report.isAnyPermissionPermanentlyDenied()) {
                                        // show alert dialog navigating to Settings
                                        //openSettingsDialog();
                                    }
                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                                }



                            }).
                            withErrorListener(new PermissionRequestErrorListener() {
                                @Override
                                public void onError(DexterError error) {
                                    //Toast.makeText(getContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .onSameThread()
                            .check();
                }
            });

            img_AFSUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    hideKeyboardFrom(getApplicationContext(),image_KYCUpload);
                    Dexter.withActivity(Activity_othercost.this)
                            .withPermissions(

                                    Manifest.permission.CAMERA,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.READ_EXTERNAL_STORAGE)
                            .withListener(new MultiplePermissionsListener() {
                                @Override
                                public void onPermissionsChecked(MultiplePermissionsReport report) {
                                    // check if all permissions are granted
                                    if (report.areAllPermissionsGranted())
                                    {
                                        counter=2;
                                        imageUpload();
                                    }

                                    // check for permanent denial of any permission
                                    if (report.isAnyPermissionPermanentlyDenied()) {
                                        // show alert dialog navigating to Settings
                                        //openSettingsDialog();
                                    }
                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

                                }



                            }).
                            withErrorListener(new PermissionRequestErrorListener() {
                                @Override
                                public void onError(DexterError error) {
                                    //Toast.makeText(getContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .onSameThread()
                            .check();

                }
            });



            tv_KYCDocument.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                public void onItemSelected(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3)
                {
                    // TODO Auto-generated method stub
                    kyc_title.setText( tv_KYCDocument.getSelectedItem().toString());
                    hideKeyboardFrom(getApplicationContext(),image_KYCUpload);

                }

                public void onNothingSelected(AdapterView<?> arg0) {
                    // TODO Auto-generated method stub

                }
            });




            radioGroup1_tv_Redevelopment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // TODO Auto-generated method stub
                    hideKeyboardFrom(getApplicationContext(),image_KYCUpload);
                    // Method 1 For Getting Index of RadioButton
                    pos=radioGroup1_tv_Redevelopment.indexOfChild(findViewById(checkedId));

                    //Toast.makeText(getBaseContext(), "Method 1 ID = "+String.valueOf(pos),Toast.LENGTH_SHORT).show();

                    //Method 2 For Getting Index of RadioButton
                    pos1=radioGroup1_tv_Redevelopment.indexOfChild(findViewById(radioGroup1_tv_Redevelopment.getCheckedRadioButtonId()));

                    // Toast.makeText(getBaseContext(), "Method 2 ID = "+String.valueOf(pos1),Toast.LENGTH_SHORT).show();

                    switch (pos)
                    {
                        case 0 :
                            // Toast.makeText(getBaseContext(), "Yes",Toast.LENGTH_SHORT).show();
                            Redevelopment="Y";
                            break;
                        case 1 :
                            // Toast.makeText(getBaseContext(), "no", Toast.LENGTH_SHORT).show();
                            Redevelopment="N";
                            break;

                        default :
                            Redevelopment="Y";
                            //The default selection is RadioButton 1
                            // Toast.makeText(getBaseContext(),"You have Clicked RadioButton 1" ,Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });

            radioGroup1_tv_type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // TODO Auto-generated method stub
                    hideKeyboardFrom(getApplicationContext(),image_KYCUpload);
                    // Method 1 For Getting Index of RadioButton
                    pos=radioGroup1_tv_type.indexOfChild(findViewById(checkedId));

                    //  Toast.makeText(getBaseContext(), "Method 1 ID = "+String.valueOf(pos), Toast.LENGTH_SHORT).show();

                    //Method 2 For Getting Index of RadioButton
                    pos1=radioGroup1_tv_type.indexOfChild(findViewById(radioGroup1_tv_type.getCheckedRadioButtonId()));

                    // Toast.makeText(getBaseContext(), "Method 2 ID = "+String.valueOf(pos1),/Toast.LENGTH_SHORT).show();

                    switch (pos)
                    {
                        case 0 :
                            // Toast.makeText(getBaseContext(), "Booked",Toast.LENGTH_SHORT).show();
                            typed="Booked";
                            layout_moredetails.setVisibility(View.VISIBLE);
                            tv_AllotteeName.setVisibility(View.VISIBLE);
                            tv_MobileNumber.setVisibility(View.VISIBLE);
                            view_moredetail.setVisibility(View.VISIBLE);
                            layout_aname.setVisibility(View.VISIBLE);
                            layout_ramount.setVisibility(View.VISIBLE);
                            layout_mno.setVisibility(View.VISIBLE);
                            layout_afsdate.setVisibility(View.VISIBLE);
                            break;
                        case 1 :
                            // Toast.makeText(getBaseContext(), "not Booked", Toast.LENGTH_SHORT).show();
                            typed="not Booked";
                            layout_moredetails.setVisibility(View.GONE);
                            tv_AllotteeName.setVisibility(View.GONE);
                            tv_MobileNumber.setVisibility(View.GONE);
                            view_moredetail.setVisibility(View.GONE);
                            layout_aname.setVisibility(View.GONE);
                            layout_ramount.setVisibility(View.GONE);
                            layout_mno.setVisibility(View.GONE);
                            layout_afsdate.setVisibility(View.GONE);
                            break;

                        default :
                            typed="Booked";
                            layout_moredetails.setVisibility(View.VISIBLE);
                            tv_AllotteeName.setVisibility(View.VISIBLE);
                            tv_MobileNumber.setVisibility(View.VISIBLE);
                            view_moredetail.setVisibility(View.VISIBLE);
                            layout_aname.setVisibility(View.VISIBLE);
                            layout_mno.setVisibility(View.VISIBLE);
                            layout_afsdate.setVisibility(View.VISIBLE);
                            //The default selection is RadioButton 1
                            // Toast.makeText(getBaseContext(),"You have Clicked RadioButton 1" ,Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            });

            radioGroup1_tv_EncumbranceStatusr.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
            {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // TODO Auto-generated method stub
                    hideKeyboardFrom(getApplicationContext(),image_KYCUpload);
                    // Method 1 For Getting Index of RadioButton
                    pos=radioGroup1_tv_EncumbranceStatusr.indexOfChild(findViewById(checkedId));

                    // Toast.makeText(getBaseContext(), "Method 1 ID = "+String.valueOf(pos),Toast.LENGTH_SHORT).show();

                    //Method 2 For Getting Index of RadioButton
                    pos1=radioGroup1_tv_EncumbranceStatusr.indexOfChild(findViewById(radioGroup1_tv_EncumbranceStatusr.getCheckedRadioButtonId()));

                    // Toast.makeText(getBaseContext(), "Method 2 ID = "+String.valueOf(pos1),/Toast.LENGTH_SHORT).show();

                    switch (pos)
                    {
                        case 0 :
                            //no
                            //Toast.makeText(getBaseContext(), "no", Toast.LENGTH_SHORT).show();
                            EncumbranceStatusr="N";
                            break;
                        case 1 :
                            //created
                            // Toast.makeText(getBaseContext(), "created", Toast.LENGTH_SHORT).show();
                            EncumbranceStatusr="C";
                            break;
                        case 2 :
                            //release
                            //Toast.makeText(getBaseContext(), "release",Toast.LENGTH_SHORT).show();
                            EncumbranceStatusr="R";
                            break;
                        default :
                            //The default selection is RadioButton 1
                            EncumbranceStatusr="N";
                            break;
                    }
                }
            });

            tv_uname=(EditText) findViewById(R.id.tv_uname) ;
            tv_type=(TextView)findViewById(R.id.tv_type) ;
            tv_area=(EditText) findViewById(R.id.tv_area) ;
            tv_balcony=(EditText) findViewById(R.id.tv_balcony) ;
            tv_status=(TextView) findViewById(R.id.tv_status) ;

            try
            {
                JSONObject obj=new JSONObject(value);
                UnitID=obj.getString("UnitID");
                Status_d=obj.getString("Status");
                DocumentID_d=obj.getString("DocumentID");
                KYCID_d=obj.getString("KYCID");
                AFSID_d=obj.getString("AFSID");
                PhotoID=obj.getString("PhotoID");

                AFSDate=obj.getString("AFSDate");
                txt_date.setText(AFSDate);
                AFSStatus="N";
                BlockID=obj.getString("BlockID");
                RowID="0";
                txt_afsdate.setText("AFS Date :"+obj.getString("AFSDate")+" *AFS is required if the amount received is more than 10% of the inventory value. Default can result in scrutiny by GujRERA");
                txt_afsdate.setText("*AFS is required if the amount received is more than 10% of the inventory value. Default can result in scrutiny by GujRERA");

                tv_uname.setText(Html.fromHtml("<font color='black'>"+obj.getString("UnitName")));
                tv_type.setText(Html.fromHtml("<font color='black'>"+obj.getString("UsageType")));
                if(obj.getString("BookingStatus").equalsIgnoreCase("Booked"))
                {
                    txt_afsdate.setVisibility(View.VISIBLE);
                    tv_type1.setChecked(true);
                    tv_type2.setChecked(false);
                    layout_moredetails.setVisibility(View.VISIBLE);
                    tv_AllotteeName.setVisibility(View.VISIBLE);
                    tv_MobileNumber.setVisibility(View.VISIBLE);
                    view_moredetail.setVisibility(View.VISIBLE);
                    layout_aname.setVisibility(View.VISIBLE);
                    layout_ramount.setVisibility(View.VISIBLE);
                    layout_mno.setVisibility(View.VISIBLE);
                    typed="Booked";
                }

                else if(obj.getString("BookingStatus").equalsIgnoreCase("Not Booked"))
                {
                    txt_afsdate.setVisibility(View.GONE);
                    tv_type1.setChecked(false);
                    tv_type2.setChecked(true);

                    layout_moredetails.setVisibility(View.GONE);
                    tv_AllotteeName.setVisibility(View.GONE);
                    tv_MobileNumber.setVisibility(View.GONE);
                    view_moredetail.setVisibility(View.GONE);
                    layout_ramount.setVisibility(View.GONE);
                    layout_aname.setVisibility(View.GONE);
                    layout_mno.setVisibility(View.GONE);
                    typed="not Booked";
                }
                else
                {
                    txt_afsdate.setVisibility(View.VISIBLE);
                    tv_type1.setChecked(true);
                    tv_type2.setChecked(false);
                    layout_moredetails.setVisibility(View.VISIBLE);
                    tv_AllotteeName.setVisibility(View.VISIBLE);
                    tv_MobileNumber.setVisibility(View.VISIBLE);
                    view_moredetail.setVisibility(View.VISIBLE);
                    layout_aname.setVisibility(View.VISIBLE);
                    layout_ramount.setVisibility(View.VISIBLE);
                    layout_mno.setVisibility(View.VISIBLE);
                    typed="Booked";
                }


                tv_area.setText(Html.fromHtml("<font color='black'>"+obj.getString("CarpetArea")));
                tv_balcony.setText(Html.fromHtml("<font color='black'>"+obj.getString("ExclusiveBalconyArea")));
                tv_status.setText(Html.fromHtml("<font color='black'>"+obj.getString("UsageType")));

                tv_MobileNumber.setText(Html.fromHtml("<font color='black'>"+obj.getString("MobileNumber")));
                tv_AllotteeName.setText(Html.fromHtml("<font color='black'>"+obj.getString("AllotteeName")));
                tv_balance.setText(Html.fromHtml("<font color='black'>"+" ₹ "+obj.getString("BalanceAmount")));
                tv_ramount.setText(Html.fromHtml("<font color='black'>"+""+obj.getString("ReceivedAmount")));
                tv_uamount.setText(Html.fromHtml("<font color='black'>"+""+obj.getString("UnitAmount")));



                if(obj.getString("Redevelopment").equalsIgnoreCase("N"))
                {
                    tv_Redevelopment.setText(Html.fromHtml("<font color='black'>"+"No"));

                    tv_Redevelopment_n.setChecked(true);
                    tv_Redevelopment_y.setChecked(false);
                }

                else
                {
                    tv_Redevelopment.setText(Html.fromHtml("<font color='black'>"+"Yes"));

                    tv_Redevelopment_n.setChecked(false);
                    tv_Redevelopment_y.setChecked(true);
                }


                tv_EncumbranceStatusr.setText(Html.fromHtml("<font color='black'>"+obj.getString("EncumbranceStatus")));

                ////////////////////
                if(obj.getString("EncumbranceStatus").equalsIgnoreCase("No Encumbrance"))
                {
                    tv_EncumbranceStatusr1.setChecked(true);
                    tv_EncumbranceStatusr2.setChecked(false);
                    tv_EncumbranceStatusr3.setChecked(false);
                }

                else if(obj.getString("EncumbranceStatus").equalsIgnoreCase("Created"))
                {
                    tv_EncumbranceStatusr1.setChecked(false);
                    tv_EncumbranceStatusr2.setChecked(true);
                    tv_EncumbranceStatusr3.setChecked(false);
                }
                else if(obj.getString("EncumbranceStatus").equalsIgnoreCase("Released"))
                {
                    tv_EncumbranceStatusr1.setChecked(false);
                    tv_EncumbranceStatusr2.setChecked(false);
                    tv_EncumbranceStatusr3.setChecked(true);
                }
                ///////////////////////////////


                // tv_KYCDocument.setText(Html.fromHtml("<font color='black'>"+obj.getString("DocumentName")));
                tv_KYCNumber.setText(Html.fromHtml("<font color='black'>"+obj.getString("KYCNumber")));

                if(obj.getString("KYCFile").contains(".pdf"))
                {
                    image_KYCUpload.setBackgroundResource(R.drawable.pdf_link);
                }
                else
                {
                    Picasso.with(getApplicationContext()).load(obj.getString("KYCFile")).placeholder(getResources().getDrawable(R.drawable.noi)).error(getResources().getDrawable(R.drawable.noi)).into(image_KYCUpload);

                }

                if(obj.getString("AFSFile").contains(".pdf"))
                {
                    img_AFSUpload.setBackgroundResource(R.drawable.pdf_link);
                }
                else
                {
                    Picasso.with(getApplicationContext()).load(obj.getString("AFSFile")).placeholder(getResources().getDrawable(R.drawable.noi)).error(getResources().getDrawable(R.drawable.noi)).into(img_AFSUpload);

                }

                if(obj.getString("AllotteePhoto").contains(".pdf"))
                {
                    image_PhotoUpload.setBackgroundResource(R.drawable.pdf_link);
                }
                else
                {
                    Picasso.with(getApplicationContext()).load(obj.getString("AllotteePhoto")).placeholder(getResources().getDrawable(R.drawable.noi)).error(getResources().getDrawable(R.drawable.noi)).into(image_PhotoUpload);

                }


            }
            catch (Exception e)
            {

            }

            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    hideKeyboardFrom(getApplicationContext(),image_KYCUpload);


                    if(tv_uname.getText().toString().equalsIgnoreCase(""))
                    {
                        mess("Enter valid Name");
                    }
                    else  if(tv_area.getText().toString().equalsIgnoreCase("0.00"))
                    {
                        mess("Enter valid Carpet Area");
                    }
                    else  if(tv_balcony.getText().toString().equalsIgnoreCase("0.00"))
                    {
                        mess("Enter valid Ex.Balcony Area");
                    }
                    else  if(tv_uamount.getText().toString().equalsIgnoreCase("0.00"))
                    {
                        mess("Enter valid Unit Amount");
                    }
                    else  if(tv_ramount.getText().toString().equalsIgnoreCase("0.00") && typed.equalsIgnoreCase("Booked"))
                    {
                        mess("Enter valid Received Amount");
                    }
                    else  if(tv_AllotteeName.getText().toString().equalsIgnoreCase("")&& typed.equalsIgnoreCase("Booked"))
                    {
                        mess("Enter valid Allottee Name ");
                    }
                    else  if(tv_MobileNumber.getText().toString().equalsIgnoreCase("")&&typed.equalsIgnoreCase("Booked"))
                    {
                        mess("Enter valid Mobile Number");
                    }
                    else  if(typed.equalsIgnoreCase("Booked"))
                    {

                        if(tv_KYCDocument.getSelectedItem().toString().equalsIgnoreCase("PAN"))
                        {
                            Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
                            Matcher matcher = pattern.matcher(tv_KYCNumber.getText().toString());
                            if (matcher.matches())
                            {
                                Log.i("Matching","Yes");
                                loginUser();
                            }
                            else
                            {
                                mess("Enter valid PAN Number");
                            }
                        }
                        else if(tv_KYCDocument.getSelectedItem().toString().equalsIgnoreCase("Adhaar Card"))
                        {

                            if (tv_KYCNumber.getText().toString().length()==12)
                            {
                                Log.i("Matching","Yes");
                                loginUser();
                            }
                            else
                            {
                                mess("Enter valid Adhaar Card Number");
                            }
                        }
                        else if(tv_KYCDocument.getSelectedItem().toString().equalsIgnoreCase("Driving License"))
                        {
                            Pattern pattern = Pattern.compile("^[A-Z]{2}[ -][0-9]{1,2}(?: [A-Z])?(?: [A-Z]*)? [0-9]{4}$");
                            Matcher matcher = pattern.matcher(tv_KYCNumber.getText().toString());
                            if (matcher.matches())
                            {
                                Log.i("Matching","Yes");
                                loginUser();
                            }
                            else
                            {
                                mess("Enter valid Driving License Number");
                            }
                        }
                        else if(tv_KYCDocument.getSelectedItem().toString().equalsIgnoreCase("Passport"))
                        {
                            Pattern pattern = Pattern.compile("^(?!^0+$)[a-zA-Z0-9]{3,20}$");
                            Matcher matcher = pattern.matcher(tv_KYCNumber.getText().toString());
                            if (matcher.matches())
                            {
                                Log.i("Matching","Yes");
                                loginUser();
                            }
                            else
                            {
                                mess("Enter valid Passport Number");
                            }


                        }

                    }
                    else
                    {
                        loginUser();
                    }

                }
            });
            if (CompletionStatus.equalsIgnoreCase("N"))
            {

            }
            else
            {
                if(sm.getloginid().equalsIgnoreCase("promoter"))
                {

                }
                else
                {
                    image_KYCUpload.setEnabled(false);
                    img_AFSUpload.setEnabled(false);
                    image_PhotoUpload.setEnabled(false);
                    btn_save.setVisibility(View.GONE);



                    tv_uname.setEnabled(false);
                    tv_uamount.setEnabled(false);
                    tv_ramount.setEnabled(false);
                    tv_area.setEnabled(false);
                    tv_balcony.setEnabled(false);
                    tv_AllotteeName.setEnabled(false);
                    tv_KYCNumber.setEnabled(false);
                    tv_MobileNumber.setEnabled(false);
                    tv_type1.setEnabled(false);
                    tv_type2.setEnabled(false);
                    tv_Redevelopment_y.setEnabled(false);
                    tv_Redevelopment_n.setEnabled(false);
                    tv_EncumbranceStatusr1.setEnabled(false);
                    tv_EncumbranceStatusr2.setEnabled(false);
                    tv_EncumbranceStatusr3.setEnabled(false);
                    tv_KYCDocument.setEnabled(false);
                }

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
        private void loginUser()
        {

            hideKeyboardFrom(getApplicationContext(),image_KYCUpload);
            progressBar_main.setVisibility(View.VISIBLE);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL+"update/SaveUnitInventory/",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response)
                        {
                            try
                            {
                                progressBar_main.setVisibility(View.GONE);
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getString("Code").equals("200"))
                                {
                                    //mess(jsonObject.getString("Message"));
                                    Toast.makeText(getApplicationContext(),jsonObject.getString("Message"),Toast.LENGTH_LONG).show();
                                    dismiss();
                                }
                                else
                                {
                                    mess(jsonObject.getString("Message"));
                                }

                            }
                            catch (Exception e)
                            {

                            }


                            //Toast.makeText(Activity_Login.this,response,Toast.LENGTH_LONG).show();
                            //parseData(response);

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
                    String Residential="";
                    if(tv_type.getText().toString().equalsIgnoreCase("Residential"))
                    {
                        Residential="1";
                    }
                    else  if(tv_type.getText().toString().equalsIgnoreCase("Shop"))
                    {
                        Residential="2";
                    }
                    else  if(tv_type.getText().toString().equalsIgnoreCase("Office"))
                    {
                        Residential="3";
                    }
                    else  if(tv_type.getText().toString().equalsIgnoreCase("Plot"))
                    {
                        Residential="4";
                    }
                    else  if(tv_type.getText().toString().equalsIgnoreCase("Other"))
                    {
                        Residential="5";
                    }

                    String bk="";
                    if(typed.equalsIgnoreCase("Booked"))
                    {
                        bk="B";

                    }
                    else
                    {
                        bk="U";
                    }

                    params.put("ProjectID",ProjectID);
                    params.put("UnitID",UnitID);
                    params.put("UnitName",tv_uname.getText().toString());
                    params.put("UsageType",tv_status.getText().toString());
                    params.put("UnitAmount",tv_uamount.getText().toString());
                    params.put("ReceivedAmount",tv_ramount.getText().toString());
                    params.put("CarpetArea",tv_area.getText().toString());
                    params.put("ExclusiveBalconyArea",tv_balcony.getText().toString());
                    params.put("Status", Status_d);
                    params.put("Redevelopment",Redevelopment);
                    params.put("Encumbrance",EncumbranceStatusr);
                    params.put("AllotteeName",tv_AllotteeName.getText().toString());
                    params.put("DocumentID",DocumentID_d);

                    params.put("KYCID",KYCID_d);
                    params.put("AFSID",AFSID_d);
                    params.put("PhotoID",PhotoID);

                    params.put("KYCNumber",tv_KYCNumber.getText().toString());
                    params.put("MobileNumber",tv_MobileNumber.getText().toString());
                    params.put("BookingStatus",bk);
                    params.put("QPRID", Constants.qprid);
                    params.put("AFSDate", formatedate(txt_date.getText().toString()));
                    params.put("BlockID", BlockID);
                    params.put("AFSStatus", AFSStatus);
                    params.put("RowID", RowID);
                    params.put("BalanceAmount", tv_balance.getText().toString());




                    Log.e("==========",params+"");

                    return params;
                }
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

            RequestQueue requestQueue = Volley.newRequestQueue(this.getContext());
            requestQueue.add(stringRequest);
        }
        public void mess(String mess) {
            Snackbar snackbar = Snackbar.make(tv_uname, mess, Snackbar.LENGTH_LONG).setAction("Action", null);
            View sbView = snackbar.getView();
            sbView.setBackgroundColor(Color.parseColor("#FA8072"));
            snackbar.show();
        }
        public String formatedate(String time)
        {
            String outputPattern="";
            String inputPattern = "dd-MM-yyyy";

            outputPattern = "yyyy-MM-dd";
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
    }


    private void imageUpload()
    {

        final CharSequence[] items = {"Take Photo", "Choose from Sdcard", "Remove Photo","Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_othercost.this);
        builder.setTitle("Update Document");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {

                    // get prompts.xml view
                    LayoutInflater li = LayoutInflater.from(Activity_othercost.this);
                    View promptsView = li.inflate(R.layout.dialog_image_name, null);



                    final EditText userInput = (EditText) promptsView
                            .findViewById(R.id.editTextDialogUserInput);

                    // set dialog message

                    if (userInput.getText().toString().trim().equals("") || userInput.getText().toString().length() == 0) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        uriSavedImage = timeStamp;
                        File imagesFolder = new File(Environment.getExternalStorageDirectory(), "TrukkerCache");
                        imagesFolder.mkdirs();

                        File image = new File(imagesFolder, uriSavedImage + ".jpg");
                        if (image.exists ()) image.delete ();
//                                        uriSavedImage = Uri.fromFile(image);

                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(image));
                        startActivityForResult(intent, 0);
                    } else {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        uriSavedImage = userInput.getText().toString().trim().replace(" ","_");
                        File imagesFolder = new File(Environment.getExternalStorageDirectory(), "TrukkerCache");
                        imagesFolder.mkdirs();

                        File image = new File(imagesFolder, uriSavedImage + ".jpg");
                        if (image.exists ()) image.delete ();
//                                        uriSavedImage = Uri.fromFile(image);

                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(image));
                        startActivityForResult(intent, 0);
                    }


                }
                else if (items[item].equals("Choose from Sdcard"))
                {
                    uriSavedImage="";
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("*/*");
                    startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1);

                    //Intent li_history = new Intent(Intent.ACTION_GET_CONTENT);
                    // li_history.setType("image/*");
                    // startActivityForResult(Intent.createChooser(li_history, "Complete action using"), 1);

                }
                else if (items[item].equals("Remove Photo"))
                {
                    dialog.dismiss();

                }else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2

        if (requestCode == 2)
        {
            if (resultCode == RESULT_OK) {
                try {
                    // String root = Environment.getExternalStorageDirectory().toString();
                    //File myDir = new File(root + "/TrukkerCache");
                    //myDir.mkdirs();
                    // String fname = uriSavedImage + ".jpg";
                    //finalFile = new File(myDir, fname);
                    Uri pictureUri = data.getData();
                    SaveImage(pictureUri.toString());
                    finalFile = new File(uri_SavedImage.toString().replace("file://",""));    // CALL THIS METHOD TO GET THE ACTUAL PATH
                    image_path = finalFile.getAbsolutePath();
                    mypath = new File(image_path);
                    Log.e("Image Path....", finalFile + "");
                    Bitmap myBitmap = BitmapFactory.decodeFile(finalFile.getAbsolutePath());
                    if(sig==1)
                    {
                        image_Upload.setImageBitmap(getResizedBitmap(myBitmap,200));

                    }
                    else {
                        if(counter==1)
                        {
                            image_KYCUpload.setImageBitmap(getResizedBitmap(myBitmap,200));

                        }
                        else if(counter==2)
                        {
                            img_AFSUpload.setImageBitmap(getResizedBitmap(myBitmap,200));
                        }
                        else if(counter==3)
                        {
                            image_PhotoUpload.setImageBitmap(getResizedBitmap(myBitmap,200));
                        }
                    }


                    // profile_img.setImageBitmap(getResizedBitmap(myBitmap,200));
//            new UploadData().execute();
                    try
                    {

                        // String s=convertimagetipdf(photos.get(0)+"");


                        new UploadData().execute();

                        // UploadData ud = new UploadData();
                        //AsyncTaskExecutor.executeConcurrently(ud);
                    }
                    catch (Exception e)
                    {

                    }


                   /* LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.list_orderimage, null);
                    final ImageView img = (ImageView) addView.findViewById(R.id.images_oned);
                    final ImageView btn_delete = (ImageView) addView.findViewById(R.id.btn_delete);
                    final TextView path = (TextView) addView.findViewById(R.id.path);
                    Bitmap myBitmap = BitmapFactory.decodeFile(finalFile.getAbsolutePath());
                    img.setImageBitmap(myBitmap);
                    path.setText(pos_del + "");
                    pos_del++;
                   layout_imageslider.addView(addView);

                    btn_delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            photos.remove(photos_temp.get(Integer.parseInt(path.getText().toString())));
                            layout_imageslider.removeView(addView);
                            //pos_del--;
                        }
                    });*/
             /*   try
                {
                    cdd.callService();
                }catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),"some error occurred during uploading image",Toast.LENGTH_SHORT).show();
                }*/
                } catch (Exception e) {

                }
//                Bitmap photo = (Bitmap) data.getExtras().get("data");  // uriSavedImage.toString().replace("file://","")

            }
        }
        else if (requestCode == 4)
        {
            if (resultCode == RESULT_OK) {
                try {


                    if (cd.isConnectingToInternet())
                    {
                        loginUser();
                        //new GetJsonBookOrder().execute();
                    } else {
                        // UF.msg("Internet connection not available.");
                    }
                }
                catch (Exception e)
                {

                }
            }
        }
        else if (requestCode == 1 && resultCode == RESULT_OK) {
            try
            {
                String realPath;
                // SDK < API11
                if (Build.VERSION.SDK_INT < 11)
                    realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, data.getData());

                    // SDK >= 11 && SDK < 19
                else if (Build.VERSION.SDK_INT < 19)
                    realPath = RealPathUtil.getRealPathFromURI_API11to18(this, data.getData());

                    // SDK > 19 (Android 4.4)
                else
                    realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());
                // Uri selectedImageUri = Uri.parse(String.valueOf(data.getData().getPath()));

                image_path =  realPath;

                // Uri selectedImageUri = data.getData();
                // image_path = getPath(selectedImageUri);
                mypath = new File(image_path);
                finalFile = new File(image_path);
                Log.e("Image Path....", finalFile + "");
                Bitmap myBitmap = BitmapFactory.decodeFile(finalFile.getAbsolutePath());
                //profile_img.setImageBitmap(getResizedBitmap(myBitmap,200));

                if(sig==1)
                {
                    image_Upload.setImageBitmap(getResizedBitmap(myBitmap,200));

                }
                else
                {
                    if(counter==1)
                    {
                        image_KYCUpload.setImageBitmap(getResizedBitmap(myBitmap,200));

                    }
                    else if(counter==2)
                    {
                        img_AFSUpload.setImageBitmap(getResizedBitmap(myBitmap,200));
                    }
                    else if(counter==3)
                    {
                        image_PhotoUpload.setImageBitmap(getResizedBitmap(myBitmap,200));
                    }
                }

                try
                {

                    new UploadData().execute();


                    // UploadData ud = new UploadData();
                    // AsyncTaskExecutor.executeConcurrently(ud);
                }
                catch (Exception e)
                {

                }

              /*  LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View addView = layoutInflater.inflate(R.layout.list_orderimage, null);
                final ImageView img = (ImageView) addView.findViewById(R.id.images_oned);
                final ImageView btn_delete = (ImageView) addView.findViewById(R.id.btn_delete);
                final TextView path = (TextView) addView.findViewById(R.id.path);
                Bitmap myBitmap = BitmapFactory.decodeFile(finalFile.getAbsolutePath());
                img.setImageBitmap(myBitmap);
                path.setText(pos_del + "");
                pos_del++;
                layout_imageslider.addView(addView);

                btn_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        photos.remove(photos_temp.get(Integer.parseInt(path.getText().toString())));
                       layout_imageslider.removeView(addView);
                        //pos_del--;
                    }
                });*/
            }
            catch (Exception e)
            {
                Uri selectedImageUri = data.getData();
                String path = FilePath.getPath(this, selectedImageUri);
                mypath = new File(path);
                finalFile = new File(path);
                Log.e("Image Path....", finalFile + "");
                Bitmap myBitmap = BitmapFactory.decodeFile(finalFile.getAbsolutePath());
                //profile_img.setImageBitmap(getResizedBitmap(myBitmap,200));

                if(sig==1)
                {
                    image_Upload.setImageBitmap(getResizedBitmap(myBitmap,200));

                }
                else
                {
                    if(counter==1)
                    {
                        image_KYCUpload.setImageBitmap(getResizedBitmap(myBitmap,200));

                    }
                    else if(counter==2)
                    {
                        img_AFSUpload.setImageBitmap(getResizedBitmap(myBitmap,200));
                    }
                    else if(counter==3)
                    {
                        image_PhotoUpload.setImageBitmap(getResizedBitmap(myBitmap,200));
                    }
                }
                // photos.add(finalFile);
                // photos_temp.add(finalFile);

                try
                {
                    new UploadData().execute();

                    // UploadData ud = new UploadData();
                    // AsyncTaskExecutor.executeConcurrently(ud);
                }
                catch (Exception e1)
                {

                }
                //e.printStackTrace();
            }


            /*try
            {
                cdd.callService();

            }catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),"some error occurred during uploading image",Toast.LENGTH_SHORT).show();
            }*/
        } else if(requestCode == 0){
            if (resultCode == RESULT_OK) {
                try {
                    //get the Uri for the captured image
                    String root = Environment.getExternalStorageDirectory().toString();
                    File myDir1 = new File(root + "/TrukkerCache");
                    String fname = uriSavedImage +".jpg";
                    File file = new File (myDir1, fname);
                    picUri = Uri.fromFile(file);



                    //carry out the crop operation
                    //  performCrop();
                    performCrop(Uri.fromFile(file));
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    private void performCrop(Uri img) {
        //take care of exceptions
        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(img, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            // cropIntent.putExtra("aspectX", 1);
            //cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
            // cropIntent.putExtra("outputX", 256);
            // cropIntent.putExtra("outputY", 256);
            //retrieve data on return

            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, 2);
        }
        //respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void performCrop() {
        //take care of exceptions
        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
//            cropIntent.putExtra("aspectX", 1);
//            cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
//            cropIntent.putExtra("outputX", 256);
//            cropIntent.putExtra("outputY", 256);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning li_history onActivityResult
            startActivityForResult(cropIntent, 2);
        }
        //respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            //display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    public String getPath(Uri uri) {
        String[] projection = {"_data"};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow("_data");
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void SaveImage(String ss) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/TrukkerImages");
        File myDir1 = new File(root + "/TrukkerCache");
        myDir.mkdirs();

        String sd=root + "/TrukkerCache/";
        String s=ss.replace(sd,"");
        s=s.replace("file://","");
        //Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
        String fname = s;
        File file = new File (myDir, fname);
        File file1 = new File (myDir1, fname);
        uri_SavedImage = Uri.fromFile(file);
        if (file.exists ())
            file.delete ();
        try {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap finalBitmap = BitmapFactory.decodeFile(file1.getAbsolutePath(),bmOptions);
            finalBitmap = Bitmap.createScaledBitmap(finalBitmap,finalBitmap.getWidth(),finalBitmap.getHeight(),true);
            FileOutputStream out = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(out);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 20, bos);
            Log.e("Compressed size",finalBitmap.getHeight()+" "+finalBitmap.getWidth());
            bos.flush();
            bos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void SaveImage() {

//        Log.e("Origional size",finalBitmap.getHeight()+" "+finalBitmap.getWidth());
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/TrukkerImages");
        File myDir1 = new File(root + "/TrukkerCache");
        myDir.mkdirs();
        String fname = uriSavedImage +".jpg";
        File file = new File (myDir, fname);
        File file1 = new File (myDir1, fname);
        uri_SavedImage = Uri.fromFile(file);
        if (file.exists ()) file.delete ();
        try {
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap finalBitmap = BitmapFactory.decodeFile(file1.getAbsolutePath(),bmOptions);
            finalBitmap = Bitmap.createScaledBitmap(finalBitmap,finalBitmap.getWidth(),finalBitmap.getHeight(),true);
            FileOutputStream out = new FileOutputStream(file);
            BufferedOutputStream bos = new BufferedOutputStream(out);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos);
            Log.e("Compressed size",finalBitmap.getHeight()+" "+finalBitmap.getWidth());
            bos.flush();
            bos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    String responsestr="";
    private class UploadData extends AsyncTask<String, String, String> {
        //  ProgressDialog pd;
        // KProgressHUD hud;
        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            /*pd = new ProgressDialog(Activity_Documents.this);
            pd.setTitle("");
            pd.setMessage("Please Wait...");
            pd.setCancelable(false);
            pd.show();*/
            // swipeRefreshLayout.setRefreshing(true);
            // nodata.setVisibility(View.GONE);
          /* hud = KProgressHUD.create(Activity_Documents.this)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel("Please wait")
                    .setWindowColor(getResources().getColor(R.color.app_orange_tra))
                    .setCancellable(false)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f)
                    .show();*/
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            Thread th = new Thread(new Runnable() {

                @Override
                public void run() {
                    // TODO Auto-generated method stub
                    upload();
                }
            });
            th.start();
            try {
                th.join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @SuppressWarnings("deprecation")
        private String upload()
        {
            try
            {
                // sslc_gallery();
                responsestr = "";
//                    int TIMEOUT_MILLISEC = 60000;  // = 60 seconds
//                    HttpParams httpParams = new BasicHttpParams();
//                    HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_MILLISEC);
//                    HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_MILLISEC);
                HttpClient httpClient = new DefaultHttpClient();
                String url="";

                if(counter==1)
                {
                    url= Constants.IMG_URL+"do=allottee-kyc&DocumentID="+DocumentID_d;


                }
                else if(counter==2)
                {
                    url=Constants.IMG_URL+"do=allottee-afs&DocumentID="+DocumentID_d;

                }
                else if(counter==3)
                {
                    url=Constants.IMG_URL+"do=allottee-photo&DocumentID="+DocumentID_d;

                }


                HttpPost httppost = new HttpPost(url);

                MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);



                // for(int i=0; i<photos.size(); i++)
                //{
                if (finalFile != null)
                    entity.addPart("myfile", new FileBody(finalFile));
                entity.addPart("RoleID", new StringBody(sm.getRoleID()));
                entity.addPart("UserID", new StringBody(sm.getUserID()));

                // entity.addPart("deletedfilesIds", new StringBody(deleted_trnsaction_id));
                httppost.setEntity(entity);
                //httppost.setHeader("Content-Type", "application/json");
                // server call
                HttpResponse hResponse = httpClient.execute(httppost);
                HttpEntity hEntity = hResponse.getEntity();
                responsestr = EntityUtils.toString(hEntity);
                Log.e("Upload_Pics_res->", responsestr);
                sig=0;

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (Exception e){
                e.printStackTrace();
            }
            return responsestr;
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            try
            {

                //Toast.makeText(getApplicationContext(),responsestr, Toast.LENGTH_SHORT).show();
                JSONObject jsonObject = new JSONObject(responsestr);
                String mess=jsonObject.getString("Message");
                if (jsonObject.getString("Code").equals("200"))
                {

                    if(counter==1)
                    {
                        KYCID_d=jsonObject.getString("MediaID");
                    }
                    else if(counter==2)
                    {
                        AFSID_d=jsonObject.getString("MediaID");

                    }
                    else if(counter==3)
                    {
                        PhotoID=jsonObject.getString("MediaID");

                    }


                    Toast.makeText(getApplicationContext(),mess,Toast.LENGTH_LONG).show();
                    mess_sucess(mess);
                    // profile();
                    //sm.setUserId(sm.getUserID(), sm.getRoleID(), sm.getRelationID(),jsonObject.getString("PathName") ,sm.getto(), sm.getjs()+"", sm.getmobileno(), sm.getlastname(), sm.getname());

                }
                else
                {
                    mess(mess);
                }
            }
            catch (Exception e)
            {
            }

           /* if (hud.isShowing())
            {
                hud.dismiss();
            }*/
        }
    }
    public Bitmap getResizedBitmap(Bitmap image, int maxSize)
    {
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image, width, height, true);
    }
    public void mess(String mess) {
        Snackbar snackbar = Snackbar.make(back, mess, Snackbar.LENGTH_LONG).setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.parseColor("#FA8072"));
        snackbar.show();
    }
    private void  requestMultiplePermissions()
    {
        Dexter.withActivity(this)
                .withPermissions(

                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted())
                        {

                            //
                            // Toast.makeText(getApplicationContext(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Without granted permission you can not access Trukker..", Toast.LENGTH_SHORT).show();
                            //requestMultiplePermissions();

                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied())
                        {
                            // finish();
                            //Toast.makeText(getApplicationContext(), "sss", Toast.LENGTH_SHORT).show();

                            // show alert dialog navigating to Settings
                            //openSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }


                }).
                withErrorListener(new PermissionRequestErrorListener()
                {
                    @Override
                    public void onError(DexterError error)
                    {
                        //Toast.makeText(getContext(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    public void mess_sucess(String mess)
    {
        Snackbar snackbar = Snackbar.make(back, mess, Snackbar.LENGTH_LONG).setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.parseColor("#ff3cb371"));
        snackbar.show();

    }
    private void user_question()
    {

        progressdisplay_layout.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL+"update/CompleteQPRForm/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        progressdisplay_layout.setVisibility(View.GONE);
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("Code").equals("200"))
                            {
                                Toast.makeText(getApplicationContext(),jsonObject.getString("Message")+"",Toast.LENGTH_LONG).show();


                            }
                            else
                            {
                                mess(jsonObject.getString("Message"));
                            }
                        }
                        catch (Exception e)
                        {

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        progressdisplay_layout.setVisibility(View.GONE);
                        mess(error.toString());
                    }
                }){
            @Override
            protected Map<String,String> getParams()
            {
                Map<String,String> params = new HashMap<String, String>();


                params.put("QPRID", Constants.qprid);
                params.put("FormID","3");


                return params;
            }
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
    public void open(View view)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure, You wanted to Complete Form");
        alertDialogBuilder.setPositiveButton("yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        user_question();
                    }
                });

        alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
            @ Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}


