package net.simplifiedcoding.navigationdrawerexample;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

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
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Form3_BankLenders extends Activity

{
    LinearLayout container,container1;
    ProgressBar progressdisplay_layout;
    SessionManager sm;
    TextView tv_form3,tv_form2;
    String ProjectID="";
    TextView t_one, t_two,t_three,t_four;
    ImageView back;
    ImageView img_pdf;
    Map<String,String> params = new HashMap<String, String>();
    Map<String,String> params1 = new HashMap<String, String>();

    RadioButton radio0_q1,radio1_q1,radio0_q2, radio1_q2;
    LinearLayout layout_image;
    RadioGroup radioGroup_q1,radioGroup_q2;
    int pos;
    int pos1;
    ImageView image_Upload,image_Uploadd;
    Button btn_upload,btnsubmit;
    Uri uri_SavedImage = null;
    String uriSavedImage = "";
    File finalFile;
    static String image_path;
    public static File mypath;
    private Uri picUri;
    CheckBox radio_turms;
    LinearLayout layout_questions;
    String title="";
    int counter=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form3_bank);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setStatusBarColor(ContextCompat.getColor(Form3_BankLenders.this, R.color.maroon));
        }
        Intent intent = getIntent();
        back=(ImageView)findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ProjectID = intent.getStringExtra("ProjectID");
        sm = new SessionManager(Form3_BankLenders.this);
        tv_form2=(TextView)findViewById(R.id.tv_form2);
        tv_form3=(TextView)findViewById(R.id.tv_form3);
        progressdisplay_layout= (ProgressBar)findViewById(R.id.progressBar2);
        container = (LinearLayout)findViewById(R.id.container);
        container1 = (LinearLayout)findViewById(R.id.container1);
        TextView title1;
        layout_questions=(LinearLayout)findViewById(R.id.layout_questions);
        layout_questions.setVisibility(View.GONE);

        try
        {
            title1=(TextView)findViewById(R.id.title1);
            title = intent.getStringExtra("title");
            if(title.equalsIgnoreCase(""))
                title1.setText("Form 3");
            else
            {
                layout_questions.setVisibility(View.GONE);
                title1.setText(title+" Form 3");
            }

        }
        catch (Exception e)
        {

        }
        t_one=(TextView)findViewById(R.id.t_one);
        t_two=(TextView)findViewById(R.id.t_two);
        t_three=(TextView)findViewById(R.id.t_three);
        t_four=(TextView)findViewById(R.id.t_four);
        t_two.setBackgroundResource(R.color.white);

        loginUser();
        img_pdf=(ImageView)findViewById(R.id.img_pdf);


        radio0_q1=(RadioButton) findViewById(R.id.radio0_q1);
        radio1_q1=(RadioButton) findViewById(R.id.radio1_q1);
        radio0_q2=(RadioButton) findViewById(R.id.radio0_q2);
        radio1_q2=(RadioButton) findViewById(R.id.radio1_q2);
        radioGroup_q1 = (RadioGroup) findViewById(R.id.radioGroup_q1);
        radioGroup_q2 = (RadioGroup) findViewById(R.id.radioGroup_q2);
        layout_image= (LinearLayout) findViewById(R.id.layout_image);
        btnsubmit=(Button)findViewById(R.id.btnsubmit);
        btn_upload=(Button)findViewById(R.id.btn_upload);
     //   image_Upload=(ImageView)findViewById(R.id.image_Upload);
        layout_image.setVisibility(View.GONE);
        img_pdf=(ImageView)findViewById(R.id.img_pdf);
        radio_turms= (CheckBox) findViewById(R.id.radio_turms);
        img_pdf.setVisibility(View.GONE);
        img_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://sighteat.com/imitra/api/pdf/form1.php?project=MQ==&qpr=MQ=="));
                startActivity(browserIntent);
            }
        });
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
                Dexter.withActivity(Form3_BankLenders.this)
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

        t_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(),Activity_Form3.class);
                i.putExtra("ProjectID",ProjectID);
                i.putExtra("title",title);
                startActivity(i);
                finish();

            }
        });
        t_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i=new Intent(getApplicationContext(),Form3_Centificate.class);
                i.putExtra("ProjectID",ProjectID);
                i.putExtra("title",title);
                startActivity(i);
                finish();
            }
        });
        t_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                Intent i=new Intent(getApplicationContext(),Form3_Parking.class);
                i.putExtra("ProjectID",ProjectID);
                i.putExtra("title",title);
                startActivity(i);
            }
        });

    }
    private void loginUser()
    {
        progressdisplay_layout.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://sighteat.com/imitra/api/selection/getProjectList/{\"mode\":\"list\",\"ProjectID\":\""+ProjectID+"\",\"Screen\":\"Form3B\"}",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {


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
                                final JSONObject QPR =  new JSONObject(res);
                                // JSONObject Form1 = QPR.getJSONObject("Form1");


                                try
                                {
                                    JSONObject BankDetail = QPR.getJSONObject("BankDetail");

                                    for(int i=0;i<1;i++)
                                    {

                                        LayoutInflater layoutInflater =
                                                (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        final View addView = layoutInflater.inflate(R.layout.list_form3, null);

                                        final EditText tv_BankName= (EditText)addView.findViewById(R.id.tv_BankName);
                                        final EditText tv_BranchName= (EditText)addView.findViewById(R.id.tv_BranchName);
                                        final EditText tv_AccountNumber= (EditText)addView.findViewById(R.id.tv_AccountNumber);
                                        final EditText tv_AccountName= (EditText)addView.findViewById(R.id.tv_AccountName);
                                        final EditText tv_IFSCCode= (EditText)addView.findViewById(R.id.tv_IFSCCode);
                                        final EditText  tv_OpeningBalance= (EditText)addView.findViewById(R.id.tv_OpeningBalance);
                                        final TextView tv_OpeningBalanceDate= (TextView)addView.findViewById(R.id.tv_OpeningBalanceDate);
                                        final EditText tv_Deposit= (EditText)addView.findViewById(R.id.tv_Deposit);
                                        final EditText tv_Withdrawal= (EditText)addView.findViewById(R.id.tv_Withdrawal);
                                        final EditText   tv_ClosingBalance= (EditText)addView.findViewById(R.id.tv_ClosingBalance);
                                        final TextView tv_ClosingBalanceDate= (TextView)addView.findViewById(R.id.tv_ClosingBalanceDate);

                                        final Button btn_save=(Button)addView.findViewById(R.id.btn_save);


                                        tv_BankName.setText(                  Html.fromHtml  ("<font color='black'>"+BankDetail.getString("BankName")));
                                        tv_BranchName.setText(                Html.fromHtml("<font color='black'>"+BankDetail.getString("Branch")));
                                        tv_AccountNumber.setText(     Html.fromHtml("<font color='black'>"+BankDetail.getString("AccountNumber")));
                                        tv_AccountName.setText(               Html.fromHtml("<font color='black'>"+BankDetail.getString("AccountName")));
                                        tv_IFSCCode.setText(          Html.fromHtml("<font color='black'>"+BankDetail.getString("IFSC")));
                                        tv_OpeningBalance.setText(            Html.fromHtml("<font color='black'>"+BankDetail.getString("OpeningBalance")));
                                        tv_OpeningBalanceDate.setText(Html.fromHtml("<font color='black'>"+formatedate_server(BankDetail.getString("OBDate"))));
                                        tv_Deposit.setText(                   Html.fromHtml("<font color='black'>"+BankDetail.getString("Deposits")));
                                        tv_Withdrawal.setText(        Html.fromHtml("<font color='black'>"+BankDetail.getString("Withdrawal")));
                                        if(BankDetail.getString("ClosingBalance").equalsIgnoreCase("null"))
                                            tv_ClosingBalance.setText(Html.fromHtml("<font color='black'>"+"-"));
                                        else
                                            tv_ClosingBalance.setText(            Html.fromHtml("<font color='black'>"+BankDetail.getString("ClosingBalance")));
                                        tv_ClosingBalanceDate.setText(Html.fromHtml("<font color='black'>"+formatedate_server(BankDetail.getString("CBDate"))));

                                        btn_save.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                hideKeyboardFrom(getApplicationContext(),btn_save);
                                                params.clear();
                                                params.put("BankName",tv_BankName.getText().toString());
                                                params.put("Branch",tv_BranchName.getText().toString());
                                                params.put("AccountNumber",tv_AccountNumber.getText().toString());
                                                params.put("AccountName",tv_AccountName.getText().toString());
                                                params.put("IFSC",tv_IFSCCode.getText().toString());
                                                params.put("OpeningBalance",tv_OpeningBalance.getText().toString());
                                                params.put("OBDate",formatedate(tv_OpeningBalanceDate.getText().toString()));
                                                params.put("Deposits",tv_Deposit.getText().toString());
                                                params.put("Withdrawal",tv_Withdrawal.getText().toString());
                                                params.put("ClosingBalance",tv_ClosingBalance.getText().toString());
                                                params.put("CBDate",formatedate(tv_ClosingBalanceDate.getText().toString()));
                                                params.put("ProjectID",ProjectID);
                                                bankdetail();
                                            }
                                        });
                                        final Calendar myCalendar = Calendar.getInstance();
                                        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                                            @Override
                                            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                                  int dayOfMonth) {
                                                // TODO Auto-generated method stub
                                                myCalendar.set(Calendar.YEAR, year);
                                                myCalendar.set(Calendar.MONTH, monthOfYear);
                                                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                                tv_OpeningBalanceDate.setText(formatedate_server(year+"-"+monthOfYear+1+"-"+dayOfMonth));

                                            }

                                        };

                                        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

                                            @Override
                                            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                                  int dayOfMonth) {
                                                // TODO Auto-generated method stub
                                                myCalendar.set(Calendar.YEAR, year);
                                                myCalendar.set(Calendar.MONTH, monthOfYear);
                                                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                                tv_ClosingBalanceDate.setText(formatedate_server(year+"-"+monthOfYear+1+"-"+dayOfMonth));

                                            }

                                        };


                                        tv_OpeningBalanceDate.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v)
                                            {

                                                new DatePickerDialog(Form3_BankLenders.this, date, myCalendar
                                                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                                            }
                                        });

                                        tv_ClosingBalanceDate.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v)
                                            {
                                                new DatePickerDialog(Form3_BankLenders.this, date1, myCalendar
                                                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

                                            }
                                        });

                                        container.addView(addView);
                                    }
                                }
                                catch (Exception e)
                                {

                                }


                                try
                                {
                                    final JSONArray LendersList = QPR.getJSONArray("LendersList");
                                    for(int i=0;i<LendersList.length();i++) {

                                        LayoutInflater layoutInflater =
                                                (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        final View addView = layoutInflater.inflate(R.layout.list_projectloan, null);

                                        final Button btn_save=(Button)addView.findViewById(R.id.btn_save);
                                        final EditText tv_LenderName = (EditText) addView.findViewById(R.id.tv_LenderName);
                                        final EditText tv_LoanAmount = (EditText) addView.findViewById(R.id.tv_LoanAmount);
                                        final EditText tv_UnitsMortgaged = (EditText) addView.findViewById(R.id.tv_UnitsMortgaged);
                                        final EditText tv_LoanDisbursalReceived = (EditText) addView.findViewById(R.id.tv_LoanDisbursalReceived);
                                        final EditText tv_LoanRepaid = (EditText) addView.findViewById(R.id.tv_LoanRepaid);
                                        final EditText tv_LoanBalance = (EditText) addView.findViewById(R.id.tv_LoanBalance);
                                        final EditText tv_LoanAgreement = (EditText) addView.findViewById(R.id.tv_LoanAgreement);
                                        final EditText tv_MortgageDeed = (EditText) addView.findViewById(R.id.tv_MortgageDeed);
                                        ImageView image_Upload1=(ImageView)addView.findViewById(R.id.image_Upload1);
                                        final Button  btn_name1=(Button)addView.findViewById(R.id.btn_name1) ;
                                        ImageView image_Upload2=(ImageView)addView.findViewById(R.id.image_Upload2);
                                        final Button  btn_name2=(Button)addView.findViewById(R.id.btn_name2) ;

                                        tv_LenderName.setText(Html.fromHtml("<font color='black'>" + LendersList.getJSONObject(i).getString("LenderName")));
                                        tv_LoanAmount.setText(Html.fromHtml("<font color='black'>" + LendersList.getJSONObject(i).getString("LoanAmount")));
                                        tv_UnitsMortgaged.setText(Html.fromHtml("<font color='black'>" + LendersList.getJSONObject(i).getString("UnitsMortgaged")));
                                        tv_LoanDisbursalReceived.setText(Html.fromHtml("<font color='black'>" + LendersList.getJSONObject(i).getString("LoanDisbursalReceived")));
                                        tv_LoanRepaid.setText(Html.fromHtml("<font color='black'>" + LendersList.getJSONObject(i).getString("LoanRepaid")));
                                        tv_LoanBalance.setText(Html.fromHtml("<font color='black'>" + "" + " ₹ " + LendersList.getJSONObject(i).getString("LoanBalance")));
                                        tv_LoanAgreement.setText(Html.fromHtml("<font color='black'>" + LendersList.getJSONObject(i).getString("LoanAgreement")));
                                        tv_MortgageDeed.setText(Html.fromHtml("<font color='black'>" + " " + LendersList.getJSONObject(i).getString("MortgageDeed")));
                                        final String sr=LendersList.getJSONObject(i).getString("SrNo");
                                        try
                                        {

                                            String FileURL = "",FileURL1="";
                                            FileURL=LendersList.getJSONObject(i).getString("LoanAgrementFile");
                                            FileURL1=LendersList.getJSONObject(i).getString("MortgageDeedFile");
                                            if(FileURL.equalsIgnoreCase("null")||FileURL.equalsIgnoreCase(""))
                                                Log.e("ddd","");
                                            else
                                            {
                                                if(FileURL.contains(".pdf"))
                                                {
                                                    image_Upload1.setBackgroundResource(R.drawable.pdf_link);
                                                    final String finalFileURL = FileURL;
                                                    image_Upload1.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v)
                                                        {

                                                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalFileURL));
                                                            startActivity(browserIntent);
                                                        }
                                                    });
                                                }
                                                // Log.e("ddd","");

                                                else
                                                    Picasso.with(getApplicationContext()).load(FileURL).into(image_Upload1);
                                            }


                                            if(FileURL1.equalsIgnoreCase("null")||FileURL1.equalsIgnoreCase(""))
                                                Log.e("ddd","");
                                            else
                                            {
                                                if(FileURL1.contains(".pdf"))
                                                {
                                                    image_Upload2.setBackgroundResource(R.drawable.pdf_link);
                                                    final String finalFileURL1 = FileURL1;
                                                    image_Upload2.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(finalFileURL1));
                                                            startActivity(browserIntent);
                                                        }
                                                    });

                                                }
                                                //Log.e("ddd","");

                                                else
                                                    Picasso.with(getApplicationContext()).load(FileURL).into(image_Upload2);
                                            }
                                        }
                                        catch (Exception e)
                                        {

                                        }

                                        image_Upload=image_Upload1;
                                        image_Uploadd=image_Upload2;
                                        btn_name1.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v)
                                            {

                                                counter=1;
                                                /////
                                                Dexter.withActivity(Form3_BankLenders.this)
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
                                        btn_name2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v)
                                            {

                                                counter=2;
                                                /////
                                                Dexter.withActivity(Form3_BankLenders.this)
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
                                        btn_save.setOnClickListener(new View.OnClickListener()
                                        {
                                            @Override
                                            public void onClick(View v)
                                            {
                                                hideKeyboardFrom(getApplicationContext(),btn_save);
                                                params1.clear();
                                                params1.put("SrNo",sr);
                                                params1.put("LenderName",tv_LenderName.getText().toString());
                                                params1.put("LoanAmount",tv_LoanAmount.getText().toString());
                                                params1.put("LoanAgreement",tv_LoanAgreement.getText().toString());
                                                params1.put("UnitsMortgaged",tv_UnitsMortgaged.getText().toString());
                                                params1.put("MortgageDeed",tv_MortgageDeed.getText().toString());
                                                params1.put("LoanDisbursalReceived",tv_LoanDisbursalReceived.getText().toString());
                                                params1.put("LoanRepaid",tv_LoanRepaid.getText().toString());
                                                params1.put("LoanBalance",tv_LoanBalance.getText().toString());

                                                lenderdetail();
                                            }
                                        });
                                        container1.addView(addView);
                                    }
                                }
                                catch (Exception e)
                                {

                                }
                                // JSONObject B = Form1.getJSONObject("B");
                                //JSONArray Pointsb = B.getJSONArray("Points");
                                //JSONArray Blocksb = B.getJSONArray("Blocks");


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
    private void bankdetail()
    {

        progressdisplay_layout.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sighteat.com/imitra/api/update/SaveBankAccountDetail/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            progressdisplay_layout.setVisibility(View.GONE);
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("Code").equals("200"))
                            {
                                //mess(jsonObject.getString("Message"));
                                Toast.makeText(getApplicationContext(),jsonObject.getString("Message"),Toast.LENGTH_LONG).show();
                                //finish();
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
                        progressdisplay_layout.setVisibility(View.GONE);
                        mess(error.toString());
                    }
                }){
            @Override
            protected Map<String,String> getParams()
            {




                Log.e("==========",params+"");

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
    private void lenderdetail()
    {

        progressdisplay_layout.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sighteat.com/imitra/api/update/SaveProjectLendersDetail/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            progressdisplay_layout.setVisibility(View.GONE);
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("Code").equals("200"))
                            {
                                //mess(jsonObject.getString("Message"));
                                Toast.makeText(getApplicationContext(),jsonObject.getString("Message"),Toast.LENGTH_LONG).show();
                                //finish();
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
                        progressdisplay_layout.setVisibility(View.GONE);
                        mess(error.toString());
                    }
                }){
            @Override
            protected Map<String,String> getParams()
            {

                params1.put("ProjectID",ProjectID);


                Log.e("==========",params1+"");

                return params1;
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
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
                params.put("FormID","3");


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
    private void imageUpload()
    {
        hideKeyboardFrom(getApplicationContext(),back);
        final CharSequence[] items = {"Take Photo", "Choose from Sdcard", "Remove Photo","Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Form3_BankLenders.this);
        builder.setTitle("Update Document");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {

                    // get prompts.xml view
                    LayoutInflater li = LayoutInflater.from(Form3_BankLenders.this);
                    View promptsView = li.inflate(R.layout.dialog_image_name, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            Form3_BankLenders.this);

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

                    if(counter==1)
                    {
                        image_Upload.setImageBitmap(getResizedBitmap(myBitmap,200));
                    }
                    else
                    {
                        image_Uploadd.setImageBitmap(getResizedBitmap(myBitmap,200));
                    }


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

                if(counter==1)
                {
                    image_Upload.setImageBitmap(getResizedBitmap(myBitmap,200));
                }
                else
                {
                    image_Uploadd.setImageBitmap(getResizedBitmap(myBitmap,200));
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

                if(counter==1)
                {
                    image_Upload.setImageBitmap(getResizedBitmap(myBitmap,200));
                }
                else
                {
                    image_Uploadd.setImageBitmap(getResizedBitmap(myBitmap,200));
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
