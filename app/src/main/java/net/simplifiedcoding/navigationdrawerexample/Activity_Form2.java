package net.simplifiedcoding.navigationdrawerexample;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
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
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;


import net.simplifiedcoding.navigationdrawerexample.Helper.ConnectionDetector;
import net.simplifiedcoding.navigationdrawerexample.Helper.Constants;
import net.simplifiedcoding.navigationdrawerexample.Helper.FilePath;
import net.simplifiedcoding.navigationdrawerexample.Helper.RealPathUtil;
import net.simplifiedcoding.navigationdrawerexample.Helper.SessionManager;

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
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
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
import java.util.Locale;
import java.util.Map;



import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static java.awt.font.TextAttribute.FONT;

/**
 * Created by Admin on 04/13/2018.
 */

public class Activity_Form2 extends Activity {

    private static final int REQUEST_EXTERNAL_STORAGE = 0;

    TextView tv_username, tv_from_date, tv_to_date;
    EditText tv_user_name, et_search;
    int lblock=0;
    CardView card_view;
    private File pdfFile;
    Button   btn_select_user;
    RelativeLayout layout_f1;
    private BaseFont bfBold;
    SessionManager sm;
    LinearLayout layout_date;
    String[] arr_headers_all = {"CustName", "SizeTypeDesc", "totaltruckRequired","SourceAdd", "DestinationAdd"};
    String[] arr_key = {"CustName", "SizeTypeDesc", "totaltruckRequired","SourceAdd", "DestinationAdd"};
    String[]  arr_headers_all_pdf ={"CustName", "SizeTypeDesc", "totaltruckRequired","SourceAdd", "DestinationAdd"};
    String[]   arr_key2 = new String[]{"BlockName", "EstimatedCost", "IncurredCost","IncurredPercentage", "Balance","Other"};
    String[] arr_headers_all2 = new String[]{"","EstimatedCost", "IncurredCost", "IncurredPercentage","Balance", "Other"};


    String[]   arr_key3 = new String[]{"BlockName", "EstimatedCost", "IncurredCost","IncurredPercentage", "Balance","Other"};
    String[] arr_headers_all3 = new String[]{"","EstimatedCost", "IncurredCost", "IncurredPercentage","Balance", "Other"};

    TableLayout tbl_header, tbl_data;
    ArrayList<HashMap<String, String>> arr_data = new ArrayList<>();
    ArrayList<HashMap<String, String>> arr_data2 = new ArrayList<>();
    ArrayList<HashMap<String, String>> arr_data3 = new ArrayList<>();
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
    TextView t2_disd,t1_disd;
    boolean report_open = false;
    boolean filter_open = true;
    String services;
    Calendar myCalendar = Calendar.getInstance();
    Calendar myCalendar1 = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date1, date2;

    RadioButton rb_invoice_date, rb_due_date;
    ImageView back;
    JSONObject prms;
    //UserFunctions UF;
    ConnectionDetector cd;
    String json_save = "", user_name_selected = "", groupby="", date_type="INV";
    ProgressBar progressdisplay_layout;
    LinearLayout layout_f2_title;
    TextView title_f1;
    TextView tv_form3,tv_form2;
    String QPRID="",ProjectID="";
    JSONArray array;
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
    File finalFile;
    static String image_path;
    public static File mypath;
    private Uri picUri;
    CheckBox radio_turms;
    LinearLayout layout_questions;
    TextView txt_cdate;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form2);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setStatusBarColor(ContextCompat.getColor(Activity_Form2.this, R.color.maroon));
        }
        //Intent intent = getIntent();
       // QPRID = intent.getStringExtra("QPRID");
        layout_date=(LinearLayout)findViewById(R.id.layout_date);
        layout_questions=(LinearLayout)findViewById(R.id.layout_questions);
        txt_cdate=(TextView)findViewById(R.id.txt_cdate);
        layout_questions.setVisibility(View.VISIBLE);
        Intent intent = getIntent();
        ProjectID = intent.getStringExtra("ProjectID");

        String title="";
        TextView title1;

        try
        {
            title1=(TextView)findViewById(R.id.title1);
            title = intent.getStringExtra("title");
            if(title.equalsIgnoreCase(""))
                title1.setText("Form 2");
            else
            {
                layout_questions.setVisibility(View.VISIBLE);
                title1.setText(title+" Form 2");
            }

        }
        catch (Exception e)
        {

        }
        t2_disd=(TextView)findViewById(R.id.t2_disd);
        t1_disd=(TextView)findViewById(R.id.t1_disd);
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date5 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                t1_disd.setText(dayOfMonth+"-"+monthOfYear+1+"-"+year);
                siteinspection();


            }

        };

        final DatePickerDialog.OnDateSetListener date6 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                try
                {
                    Date date1;
                    Date date2;
                    SimpleDateFormat dates = new SimpleDateFormat("dd-MM-yyyy");
                    date1 = dates.parse(t1_disd.getText().toString());
                    date2 = dates.parse(dayOfMonth+"-"+monthOfYear+1+"-"+year);

                    if (date1.compareTo(date2) > 0)
                    {
                        t2_disd.setText(dayOfMonth+"-"+monthOfYear+1+"-"+year);
                        certdate();
                        System.out.println("Date1 is before Date2");

                    }
                    else if (date1.compareTo(date2) < 0)
                    {
                        mess("Certificate Date should not be less than book of account date");

                        System.out.println("Date1 is before Date2");
                    }
                    else if (date1.compareTo(date2) == 0)
                    {
                        t2_disd.setText(dayOfMonth+"-"+monthOfYear+1+"-"+year);
                        certdate();
                        System.out.println("Date1 is before Date2");
                    }




                }
                catch (Exception e)
                {

                }


            }

        };
        t2_disd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                new DatePickerDialog(Activity_Form2.this, date6, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        t1_disd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                new DatePickerDialog(Activity_Form2.this, date5, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        progressdisplay_layout=(ProgressBar) findViewById(R.id.progressBar2);
        progressdisplay_layout.setVisibility(View.GONE);
        hideKeyboard(Activity_Form2.this);
        layout_f2_title=(LinearLayout)findViewById(R.id.layout_f2_title);
        layout_f2_title.setVisibility(View.GONE);
        cd = new ConnectionDetector(Activity_Form2.this);
        sm = new SessionManager(Activity_Form2.this);
        card_view = (CardView) findViewById(R.id.card_view);
        radio_turms= (CheckBox) findViewById(R.id.radio_turms);
        title_f1=(TextView)findViewById(R.id.title_f1);
        title_f1.setText("Form 2 (TABLE A)");
        rel_filter = (RelativeLayout) findViewById(R.id.rel_filter);
        tv_form2=(TextView)findViewById(R.id.tv_form2);
        tv_form3=(TextView)findViewById(R.id.tv_form3);
        layout_f1= (RelativeLayout) findViewById(R.id.layout_f1);
        layout_f1.setVisibility(View.GONE);
        rel_report_table = (RelativeLayout) findViewById(R.id.rel_report_table);
        rel_filter_view = (LinearLayout) findViewById(R.id.rel_filter_view);
        ll_select_user = (LinearLayout) findViewById(R.id.ll_select_user);
        ll_date = (LinearLayout) findViewById(R.id.ll_date);

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

        //////////////////////
        radio0_q1=(RadioButton) findViewById(R.id.radio0_q1);
        radio1_q1=(RadioButton) findViewById(R.id.radio1_q1);
        radio0_q2=(RadioButton) findViewById(R.id.radio0_q2);
        radio1_q2=(RadioButton) findViewById(R.id.radio1_q2);
        radioGroup_q1 = (RadioGroup) findViewById(R.id.radioGroup_q1);
        radioGroup_q2 = (RadioGroup) findViewById(R.id.radioGroup_q2);
        layout_image= (LinearLayout) findViewById(R.id.layout_image);
        btnsubmit=(Button)findViewById(R.id.btnsubmit);
        btn_upload=(Button)findViewById(R.id.btn_upload);
        image_Upload=(ImageView)findViewById(R.id.image_Upload);
        layout_image.setVisibility(View.GONE);
        img_pdf=(ImageView)findViewById(R.id.img_pdf);


        btnsubmit.setVisibility(View.GONE);
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
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(Activity_Form2.this)
                        .withPermissions(

                                android.Manifest.permission.CAMERA,
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new MultiplePermissionsListener() {
                            @Override
                            public void onPermissionsChecked(MultiplePermissionsReport report) {
                                // check if all permissions are granted
                                if (report.areAllPermissionsGranted())
                                {

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

                //user_question();
                open(v);
            }
        });
        ////////////////////////




        rel_report_table.setVisibility(View.VISIBLE);
        img_pdf=(ImageView)findViewById(R.id.img_pdf);


        back=(ImageView)findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
        tv_form2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),Activity_Form.class);
                i.putExtra("ProjectID",ProjectID);
                startActivity(i);
                finish();

            }
        });
        tv_form3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i=new Intent(getApplicationContext(),Activity_Form3.class);
                i.putExtra("ProjectID",ProjectID);
                startActivity(i);
                finish();
            }
        });

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
                if(et_search.getText().toString().equals("") || et_search.getText().toString().length() == 0)
                {
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
                CreateHeader(false);
                AddedTableData(false);
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
                DatePickerDialog dialog = new DatePickerDialog(Activity_Form2.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });

        tv_to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(Activity_Form2.this, date2, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH));
                dialog.show();
            }
        });
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(et_search.getWindowToken(),InputMethodManager.RESULT_UNCHANGED_SHOWN);
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

    public void CreateHeader(boolean group_by)
    {

        tbl_header.removeAllViews();
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        for(int i=0; i<arr_headers_all.length; i++)
        {
            LayoutInflater inflater = LayoutInflater.from(Activity_Form2.this);
            View inflatedLayout = null;
            if(arr_headers_all[i].equalsIgnoreCase("Cust Name")||arr_headers_all[i].equalsIgnoreCase("Truck Type")||arr_headers_all[i].equalsIgnoreCase("No.Of Truck"))
                inflatedLayout= inflater.inflate(R.layout.table_header_tv_dashbord, null, false);
            else
                inflatedLayout= inflater.inflate(R.layout.table_header_tv_dashbord1, null, false);
            TextView tv = (TextView) inflatedLayout.findViewById(R.id.tv_header);

            tv.setText(arr_headers_all[i]);
            tr.addView(inflatedLayout);

        }

        tbl_header.addView(tr);

        /*TableRow tr1 = new TableRow(this);
        tr1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        LayoutInflater inflater = LayoutInflater.from(Activity_Form2.this);
        View inflatedLayout = null;
        inflatedLayout= inflater.inflate(R.layout.d, null, false);
        TextView tv = (TextView) inflatedLayout.findViewById(R.id.tv_header);
        tv.setText("Table A");
        tr1.addView(inflatedLayout);
        tbl_header.addView(tr1);*/


    }

    public void AddedTableData(boolean group_by)
    {
      //  CreateHeader(false);
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


        TableRow tr4 = new TableRow(this);
        tr4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        int l1=arr_headers_all2.length;
        for (int i = 0; i <=(l1); i++)
        {
            LayoutInflater inflater = LayoutInflater.from(Activity_Form2.this);
            View inflatedLayout = null;

                inflatedLayout = inflater.inflate(R.layout.list_center, null, false);
            TextView tv = (TextView) inflatedLayout.findViewById(R.id.tv_data);
            View layout_line= (View) inflatedLayout.findViewById(R.id.layout_line);
            tv.setTypeface(null, Typeface.BOLD);
            if(arr_headers_all[i].equalsIgnoreCase("Particulars")||arr_headers_all[i].equalsIgnoreCase("Total"))

            {
                layout_line.setVisibility(View.VISIBLE);
                tv.setText("-");
                tv.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
            }

            if(i==1)
            {
                layout_line.setVisibility(View.VISIBLE);
                tv.setText("Table A");
            }

            else if(arr_headers_all[i].equalsIgnoreCase("Common Area"))
            {
                layout_line.setVisibility(View.VISIBLE);
                tv.setText("Table B");
               // tv.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL);
            }
            else
            {
               // layout_line.setVisibility(View.GONE);
            }


            tr4.addView(inflatedLayout);
        }
        tbl_data.addView(tr4);

        ////////
/////
        TableRow tr5 = new TableRow(this);
        tr5.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        for(int i=0; i<arr_headers_all.length; i++)
        {
            LayoutInflater inflater = LayoutInflater.from(Activity_Form2.this);
            View inflatedLayout = null;
            if(arr_headers_all[i].equalsIgnoreCase("Cust Name")||arr_headers_all[i].equalsIgnoreCase("Truck Type")||arr_headers_all[i].equalsIgnoreCase("No.Of Truck"))
                inflatedLayout= inflater.inflate(R.layout.table_header_tv_dashbord, null, false);
            else
                inflatedLayout= inflater.inflate(R.layout.table_header_tv_dashbord2, null, false);
            TextView tv = (TextView) inflatedLayout.findViewById(R.id.tv_header);

            tv.setText(arr_headers_all[i]);
            tr5.addView(inflatedLayout);

        }

        tbl_data.addView(tr5);
        //////



        for (int i = 0; i < arr_headers_all2.length; i++)
        {
            TableRow tr2 = new TableRow(this);
            tr2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            List<String> Values = new ArrayList<>();
            Values.clear();

            for(int j=0;j<lblock+3;j++)
            {

                for(int k=0;k<array.length();k++)
                {
                    try
                    {

                        JSONObject json_data = array.getJSONObject(k);
                        String s=json_data.getString("Costing");
                        JSONObject jsonObject = new JSONObject(s);
                        Values.add(jsonObject.getString(arr_headers_all3[i]));


                    }
                    catch (Exception e)
                    {

                    }

                }

                LayoutInflater inflater1 = LayoutInflater.from(Activity_Form2.this);
                View inflatedLayout1 = null;
                inflatedLayout1 = inflater1.inflate(R.layout.table_data_tv_dashbord2, null, false);
                TextView tv1 = (TextView) inflatedLayout1.findViewById(R.id.tv_data);
                Log.e("------------",arr_data2.get(0).get(arr_headers_all2[i])+"");
                if(j==(lblock+1))
                {
                    if(i==2)
                        tv1.setText(arr_data2.get(0).get(arr_headers_all2[i])+"%");
                    else
                        tv1.setText(" ₹"+arr_data2.get(0).get(arr_headers_all2[i]));
                    tv1.setGravity(Gravity.RIGHT| Gravity.CENTER_VERTICAL);
                }
                else if(j==(lblock+2))
                {
                    //if(i==2)
                      //  tv1.setText(arr_data3.get(0).get(arr_headers_all3[i]+"%"));
                    //else
                    if((i)==2)
                        tv1.setText(arr_data3.get(0).get(arr_headers_all3[i])+"%");
                    else
                        tv1.setText(" ₹"+arr_data3.get(0).get(arr_headers_all3[i]));
                    tv1.setGravity(Gravity.RIGHT| Gravity.CENTER_VERTICAL);
                }
                else if(j==0)
                {
                    tv1.setText(arr_headers_all3[i]);
                    tv1.setGravity(Gravity.LEFT| Gravity.CENTER_VERTICAL);
                }
                else
                {
                    if((i)==2)
                        tv1.setText(Values.get(j-1)+"%");
                    else
                        tv1.setText(" ₹"+Values.get(j-1));
                    tv1.setGravity(Gravity.RIGHT| Gravity.CENTER_VERTICAL);
                }



                tr2.addView(inflatedLayout1);

            }


            tbl_data.addView(tr2);

        }


        int l=arr_headers_all2.length;
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        for (int i = 0; i <=(l); i++) {
            LayoutInflater inflater = LayoutInflater.from(Activity_Form2.this);
            View inflatedLayout = null;
            if(arr_headers_all[i].equalsIgnoreCase("Particulars")||arr_headers_all[i].equalsIgnoreCase("Total"))
                inflatedLayout = inflater.inflate(R.layout.table_data_tv_dashbord2, null, false);
            else

                inflatedLayout = inflater.inflate(R.layout.list_button2, null, false);
            TextView tv = (TextView) inflatedLayout.findViewById(R.id.tv_data);
            tv.setTypeface(null, Typeface.BOLD);
            if(arr_headers_all[i].equalsIgnoreCase("Particulars")||arr_headers_all[i].equalsIgnoreCase("Total"))
                tv.setText("");
            final int finalI = i;
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    Intent i1=new Intent(getApplicationContext(),List_form2.class);
                    i1.putExtra("data",services);
                    i1.putExtra("ProjectID",ProjectID);

                    i1.putExtra("class",arr_headers_all[finalI].toString());
                    startActivity(i1);
                   // Toast.makeText(getApplicationContext(), arr_headers_all[finalI].toString(), Toast.LENGTH_LONG).show();
                }
            });

            tr.addView(inflatedLayout);
        }

        tbl_data.addView(tr);


        //////////////////////////


       /* TableRow tr3 = new TableRow(this);
        tr3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));

        for (int i = 0; i < arr_headers_all3.length; i++)
        {
            LayoutInflater inflater1 = LayoutInflater.from(Activity_Form2.this);
            View inflatedLayout1 = null;
            inflatedLayout1 = inflater1.inflate(R.layout.table_data_tv_dashbord1, null, false);
            TextView tv1 = (TextView) inflatedLayout1.findViewById(R.id.tv_data);
            Log.e("------------",arr_data2.get(0).get(arr_headers_all3[i])+"");
            tv1.setText("Total : "+arr_data3.get(0).get(arr_headers_all3[i]));
            tv1.setGravity(Gravity.CENTER);
            tr3.addView(inflatedLayout1);

        }
        tbl_data.addView(tr3);*/


    }

    private boolean mayRequestStorage()
    {
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
        if (view == null)
        {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    private void loginUser()
    {
        progressdisplay_layout.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://sighteat.com/imitra/api/selection/getProjectList/{\"mode\":\"list\",\"ProjectID\":\""+ProjectID+"\",\"Screen\":\"Form2\"}",
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

                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("Code").equals("200"))
                            {
                                JSONArray array1 = new JSONArray();

                                array1 = jsonObject.getJSONArray("ProjectDetail");

                                String res=array1.getJSONObject(0).getString("Quarter");
                                Constants.qprid = array1.getJSONObject(0).getString("QPRID");

                                services=res;
                                final JSONObject QPR =  new JSONObject(res);

                                try
                                {
                                    String CompletionStatus=QPR.getString("CompletionStatus");
                                    if(CompletionStatus.equalsIgnoreCase("N"))
                                    {
                                        //radioGroup_q1.setEnabled(false);
                                    }
                                    else
                                    {
                                        radio0_q1.setChecked(true);
                                        radioGroup_q1.setEnabled(false);
                                        radio0_q1.setEnabled(false);
                                        radio1_q1.setEnabled(false);
                                        t1_disd.setTextColor(R.color.grey);
                                        t2_disd.setTextColor(R.color.grey);
                                        layout_image.setVisibility(View.GONE);
                                        layout_image.setEnabled(false);
                                        layout_date.setEnabled(false);
                                        t1_disd.setEnabled(false);
                                        t2_disd.setEnabled(false);
                                        txt_cdate.setText("Form completion confirmed on "+QPR.getString("CompletionTime"));
                                    }

                                    t1_disd.setText(QPR.getString("VisitDate"));
                                    t2_disd.setText(QPR.getString("CertificateDate"));
                                    JSONObject Certificate = QPR.getJSONObject("Certificate");
                                    final String   FileURL= Certificate.getString("FileURL").toString();

                                    if(FileURL.equalsIgnoreCase("null")||FileURL.equalsIgnoreCase(""))
                                        Log.e("ddd","");
                                    else

                                        Picasso.with(getApplicationContext()).load(FileURL).placeholder(getResources().getDrawable(R.drawable.noi)).error(getResources().getDrawable(R.drawable.noi)).into(image_Upload);

                                    image_Upload.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            Intent i=new Intent(getApplicationContext(),webview.class);
                                            i.putExtra("url",FileURL+"");
                                            startActivity(i);
                                        }
                                    });
                                }
                                catch (Exception e)
                                {

                                }

                                //JSONObject Form1 = QPR.getJSONObject("Form2");
                                JSONObject A = QPR.getJSONObject("A");
                                final String FormURL=QPR.getString("FormURL");
                                img_pdf.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v)
                                    {

                                        Intent i=new Intent(getApplicationContext(), pdffile.class);
                                        i.putExtra("url",FormURL);
                                        startActivity(i);

                                       /* Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                                        pdfIntent.setDataAndType(Uri.parse(FormURL), "application/pdf");
                                        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                        try{
                                            startActivity(pdfIntent);
                                        }catch(ActivityNotFoundException e1){
                                            Toast.makeText(Activity_Form2.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
                                        }*/

                                    }
                                });
                                array = A.getJSONArray("Blocks");

                                lblock=array.length();
                                Log.e("ddd",array.length()+"");

                                JSONObject B = QPR.getJSONObject("B");
                                JSONObject Costingjs = B.getJSONObject("Costing");
                                JSONObject Total = QPR.getJSONObject("Total");
                                //JSONArray Blocksb = B.getJSONArray("Blocks");

                                arr_headers_all = new String[array.length()+3];
                                arr_key = new String[array.length()+3];

                                arr_data = new ArrayList<>();
                                arr_data2 = new ArrayList<>();
                                arr_data3 = new ArrayList<>();
                                // arr_data_Final = new ArrayList<>();
                                // arr_data_Final_pdf = new ArrayList<>();

                                arr_headers_all[0]="Particulars";
                                arr_key[0]="Name";

                                for(int i=0;i<array.length();i++)
                                {
                                    arr_headers_all[i+1]=array.getJSONObject(i).getString("BlockName");
                                    arr_key[i+1]="WorkDone";

                                }

                                arr_headers_all[array.length()+1]="Common Area";
                                arr_key[array.length()+1]="WorkDone";

                                arr_headers_all[array.length()+2]="Total";
                                arr_key[array.length()+2]="WorkDone";

                                // arr_headers_all = new String[]{"Block Name", "Estimated Cost", "Incurred Cost","Incurred Percentage", "Balance Cost","Other"};
                                arr_key2 = new String[]{"EstimatedCost", "IncurredCost", "IncurredPercentage","Balance", "Other"};
                                arr_headers_all2 = new String[]{"EstimatedCost", "IncurredCost", "IncurredPercentage","Balance", "Other"};


                                arr_key3 = new String[]{"EstimatedCost", "IncurredCost", "IncurredPercentage","Balance", "Other"};
                                arr_headers_all3 = new String[]{"EstimatedCost", "IncurredCost", "IncurredPercentage","Balance", "Other"};



                               /* for (int i = 0; i < array.length(); i++)
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
                                      //  arr_data_Final_pdf.add(hmap1);
                                       arr_data.add(hmap);
                                        //arr_data_Final.add(hmap);
                                    }*/

                                HashMap<String, String> hmap = new HashMap<>();

                                for (int j = 0; j < arr_headers_all2.length; j++)
                                {
                                    if(Costingjs.has(arr_key2[j]))
                                    {
                                        hmap.put(arr_headers_all2[j], Costingjs.getString(arr_key2[j]).toString());
                                    }

                                    else
                                    {
                                        hmap.put(arr_headers_all2[j], "-");
                                    }
                                }
                                //  arr_data_Final_pdf.add(hmap1);
                                arr_data2.add(hmap);


                                HashMap<String, String> hmap1 = new HashMap<>();

                                for (int j = 0; j < arr_headers_all3.length; j++)
                                {
                                    if(Costingjs.has(arr_key3[j]))
                                    {
                                        hmap1.put(arr_headers_all3[j], Total.getString(arr_key3[j]).toString());
                                    }

                                    else
                                    {
                                        hmap1.put(arr_headers_all3[j], "-");
                                    }
                                }
                                //  arr_data_Final_pdf.add(hmap1);
                                arr_data3.add(hmap1);


                                // if(arr_data.size() > 0)
                                // {
                                //      rel_report_table.setVisibility(View.VISIBLE);
                                // }
                                // CreateHeader(false);
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

                                ////////////////


                            }
                        }
                        catch (Exception e)
                        {

                            Log.e("dddddd",e+"");
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
    private void user_question()
    {

        progressdisplay_layout.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sighteat.com/imitra/api/update/CompleteQPRForm/",
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
                params.put("FormID","2");


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
        Snackbar snackbar = Snackbar.make(back, mess, Snackbar.LENGTH_LONG).setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.parseColor("#FA8072"));
        snackbar.show();
    }
    private void imageUpload()
    {
        hideKeyboardFrom(getApplicationContext(),back);
        final CharSequence[] items = {"Take Photo", "Choose from Sdcard", "Remove Photo","Cancel"};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Activity_Form2.this);
        builder.setTitle("Update Document");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {

                    // get prompts.xml view
                    LayoutInflater li = LayoutInflater.from(Activity_Form2.this);
                    View promptsView = li.inflate(R.layout.dialog_image_name, null);

                    android.app.AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            Activity_Form2.this);

                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);

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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // TODO Auto-generated method stub


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
                    image_Upload.setImageBitmap(getResizedBitmap(myBitmap,200));
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
                image_Upload.setImageBitmap(getResizedBitmap(myBitmap,200));

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
                image_Upload.setImageBitmap(getResizedBitmap(myBitmap,200));

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
                HttpPost httppost = new HttpPost("http://sighteat.com/imitra/admin/common-pages/file-upload.php?do=certificate&DocumentID=15&ProjectID="+ProjectID+"&QPRID="+Constants.qprid);

                org.apache.http.entity.mime.MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);



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
                    String  MediaID=jsonObject.getString("MediaID");
                    mess_sucess(mess);
                    // profile();
                    //sm.setUserId(sm.getUserID(), sm.getRoleID(), sm.getRelationID(),jsonObject.getString("PathName") ,sm.getto(), sm.getjs()+"", sm.getmobileno(), sm.getlastname(), sm.getname());

                }
                else
                {
                    mess_sucess(mess);
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
    public void mess_sucess(String mess)
    {
        Snackbar snackbar = Snackbar.make(back, mess, Snackbar.LENGTH_LONG).setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.parseColor("#ff3cb371"));
        snackbar.show();
    }
    private void siteinspection()
    {

        progressdisplay_layout.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sighteat.com/imitra/api/update/SaveSiteInspectionDate/",
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
                params.put("FormID", "2");
                params.put("VisitDate",formatedate(t1_disd.getText().toString()));


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
    private void certdate()
    {

        progressdisplay_layout.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sighteat.com/imitra/api/update/SaveCertificateDate/",
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
                params.put("FormID", "2");
                params.put("CertificateDate",formatedate(t2_disd.getText().toString()));


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

