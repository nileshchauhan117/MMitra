package com.MilkatMitra.App;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.MilkatMitra.App.Helper.Constants;
import com.MilkatMitra.App.Helper.FilePath;
import com.MilkatMitra.App.Helper.RealPathUtil;
import com.MilkatMitra.App.Helper.SessionManager;
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
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Activity_consultant_add extends AppCompatActivity
{

    ImageView back_btn;
    TextView tv_usernames,tv_emails;
    SessionManager sm;
    TextView tv_display;
    ProgressBar progressBar_main;
    TextView title1;
    String type="Proprietorship",rolid="",qprid="";
    TextInputLayout layout_Firmno,layout_Firm,layout_Membership,layout_number,layout_regno,layout_mobile,layout_email,layout_lname,layout_fname;
    EditText txt_panno,txt_Firmno,txt_Firm,txt_first,txt_last,txt_email,txt_mobile,txt_regno,txt_number,txt_Membership;
    TextView txt_date;
    String page="",ProjectID="";
    LinearLayout layout_radio;
    Button btnlogin;
    LinearLayout layout_validupto;
    RadioGroup rg;
    int pos;
    int pos1;
    String RelationID="";
    ImageView image_Upload;

    File finalFile;
    static String image_path;
    public static File mypath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_consultant);
        progressBar_main = (ProgressBar) findViewById(R.id.progressBar_main);
        progressBar_main.setVisibility(View.GONE);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        sm = new SessionManager(Activity_consultant_add.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(Activity_consultant_add.this, R.color.maroon_dark));
        }
        btnlogin=(Button)findViewById(R.id.btnlogin);
        Intent intent = getIntent();
        ProjectID = intent.getStringExtra("ProjectID");
        page = intent.getStringExtra("page");

        image_Upload=(ImageView)findViewById(R.id.image_Upload);
        layout_radio=(LinearLayout)findViewById(R.id.layout_radio);
         layout_Membership=(TextInputLayout)findViewById(R.id.layout_Membership);
        layout_validupto=(LinearLayout)findViewById(R.id.layout_validupto);
        layout_number=(TextInputLayout)findViewById(R.id.layout_number);
        layout_regno=(TextInputLayout)findViewById(R.id.layout_regno);
        layout_mobile=(TextInputLayout)findViewById(R.id.layout_mobile);
        layout_email=(TextInputLayout)findViewById(R.id.layout_email);
        layout_lname=(TextInputLayout)findViewById(R.id.layout_lname);
        layout_fname=(TextInputLayout)findViewById(R.id.layout_fname);
        layout_Firm=(TextInputLayout)findViewById(R.id.layout_Firm);
        layout_Firmno=(TextInputLayout)findViewById(R.id.layout_Firmno);

        layout_Membership.setVisibility(View.GONE);
        layout_validupto.setVisibility(View.GONE);
        layout_number.setVisibility(View.GONE);
        layout_regno.setVisibility(View.GONE);
        layout_mobile.setVisibility(View.GONE);
        layout_email.setVisibility(View.GONE);
        layout_lname.setVisibility(View.GONE);
        layout_fname.setVisibility(View.GONE);
        layout_Firm.setVisibility(View.GONE);
        layout_Firmno.setVisibility(View.GONE);
        layout_radio.setVisibility(View.GONE);

        txt_panno=(EditText)findViewById(R.id.txt_panno) ;
                txt_first=(EditText)findViewById(R.id.txt_first) ;
        txt_last=(EditText)findViewById(R.id.txt_last) ;
                txt_email=(EditText)findViewById(R.id.txt_email) ;
        txt_mobile=(EditText)findViewById(R.id.txt_mobile) ;
                txt_regno=(EditText)findViewById(R.id.txt_regno) ;
        txt_number=(EditText)findViewById(R.id.txt_number) ;
                txt_date=(TextView) findViewById(R.id.txt_date) ;
        txt_Membership=(EditText)findViewById(R.id.txt_Membership) ;
        txt_Firm=(EditText)findViewById(R.id.txt_Firm) ;
                txt_Firmno=(EditText)findViewById(R.id.txt_Firmno) ;

                image_Upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Dexter.withActivity(Activity_consultant_add.this)
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


        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date5 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                int s=monthOfYear+1;
                txt_date.setText(dayOfMonth+"-"+s+"-"+year);




            }

        };
        txt_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                new DatePickerDialog(Activity_consultant_add.this, date5, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(btnlogin.getWindowToken(),
                        InputMethodManager.RESULT_UNCHANGED_SHOWN);
                vat();
            }
        });
        if(page.equalsIgnoreCase("1"))
        {
            rolid="9";
            layout_regno.setVisibility(View.VISIBLE);
            layout_validupto.setVisibility(View.VISIBLE);
            layout_mobile.setVisibility(View.VISIBLE);
            layout_email.setVisibility(View.VISIBLE);
            layout_lname.setVisibility(View.VISIBLE);
            layout_fname.setVisibility(View.VISIBLE);
        }
        else  if(page.equalsIgnoreCase("2"))
        {
            rolid="8";
            layout_number.setVisibility(View.VISIBLE);
            layout_validupto.setVisibility(View.VISIBLE);
            layout_mobile.setVisibility(View.VISIBLE);
            layout_email.setVisibility(View.VISIBLE);
            layout_lname.setVisibility(View.VISIBLE);
            layout_fname.setVisibility(View.VISIBLE);
        }
        else  if(page.equalsIgnoreCase("2a"))
        {
            rolid="21";
            layout_mobile.setVisibility(View.VISIBLE);
            layout_email.setVisibility(View.VISIBLE);
            layout_lname.setVisibility(View.VISIBLE);
            layout_fname.setVisibility(View.VISIBLE);
        }
        else if(page.equalsIgnoreCase("s"))
        {
            rolid="16";
            layout_mobile.setVisibility(View.VISIBLE);
            layout_email.setVisibility(View.VISIBLE);
            layout_lname.setVisibility(View.VISIBLE);
            layout_fname.setVisibility(View.VISIBLE);
        }
        else if(page.equalsIgnoreCase("3"))
        {
            rolid="6";
            layout_mobile.setVisibility(View.VISIBLE);
            layout_email.setVisibility(View.VISIBLE);
            layout_lname.setVisibility(View.VISIBLE);
            layout_fname.setVisibility(View.VISIBLE);
            layout_Membership.setVisibility(View.VISIBLE);
            layout_Firm.setVisibility(View.GONE);
            layout_Firmno.setVisibility(View.GONE);
            layout_radio.setVisibility(View.VISIBLE);
        }

        back_btn=(ImageView)findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rg = (RadioGroup) findViewById(R.id.radioGroup1);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub

                // Method 1 For Getting Index of RadioButton
                pos=rg.indexOfChild(findViewById(checkedId));



                //Method 2 For Getting Index of RadioButton
                pos1=rg.indexOfChild(findViewById(rg.getCheckedRadioButtonId()));



                switch (pos)
                {
                    case 0 :
                        layout_Firm.setVisibility(View.GONE);
                        layout_Firmno.setVisibility(View.GONE);
                        type="Proprietorship";
                        break;
                    case 1 :
                        layout_Firm.setVisibility(View.VISIBLE);
                        layout_Firmno.setVisibility(View.VISIBLE);
                        type="Partnership";
                        break;


                    default :
                        //The default selection is RadioButton 1
                        layout_Firm.setVisibility(View.GONE);
                        layout_Firmno.setVisibility(View.GONE);
                        type="Proprietorship";
                        break;
                }
            }
        });

    }
    public  void vat()
    {
        if(page.equalsIgnoreCase("1"))
        {
            if(txt_first.getText().toString().equalsIgnoreCase(""))
            {
                mess("Enter valid First Name");
            }
            else if(txt_last.getText().toString().equalsIgnoreCase(""))
            {
                mess("Enter valid Last Name");
            }
            else if(txt_email.getText().toString().equalsIgnoreCase(""))
            {
                mess("Enter valid EmailID");
            }
            else if(txt_mobile.getText().toString().equalsIgnoreCase(""))
            {
                mess("Enter valid Mobile Number");
            }
            else if(txt_regno.getText().toString().equalsIgnoreCase(""))
            {
                mess("Enter valid CoA Reg. Number");
            }
            else if(txt_date.getText().toString().equalsIgnoreCase(""))
            {
                mess("Enter valid Date");
            }
            else if(txt_panno.getText().toString().equalsIgnoreCase(""))
            {
                mess("Enter valid PAN Number");
            }
            else
            {
                loginUser();
            }

        }
        else  if(page.equalsIgnoreCase("2"))
        {
            if(txt_first.getText().toString().equalsIgnoreCase(""))
            {
                mess("Enter valid First Name");
            }
            else if(txt_last.getText().toString().equalsIgnoreCase(""))
            {
                mess("Enter valid Last Name");
            }
            else if(txt_email.getText().toString().equalsIgnoreCase(""))
            {
                mess("Enter valid EmailID");
            }
            else if(txt_mobile.getText().toString().equalsIgnoreCase(""))
            {
                mess("Enter valid Mobile Number");
            }
            else if(txt_number.getText().toString().equalsIgnoreCase(""))
            {
                mess("Enter valid License Number");
            }
            else if(txt_date.getText().toString().equalsIgnoreCase(""))
            {
                mess("Enter valid Date");
            }
            else if(txt_panno.getText().toString().equalsIgnoreCase(""))
            {
                mess("Enter valid PAN Number");
            }
            else
            {
                loginUser();
            }

        }
        else  if(page.equalsIgnoreCase("2a"))
        {
            if(txt_first.getText().toString().equalsIgnoreCase(""))
            {
                mess("Enter valid First Name");
            }
            else if(txt_last.getText().toString().equalsIgnoreCase(""))
            {
                mess("Enter valid Last Name");
            }
            else if(txt_email.getText().toString().equalsIgnoreCase(""))
            {
                mess("Enter valid EmailID");
            }
            else if(txt_mobile.getText().toString().equalsIgnoreCase(""))
            {
                mess("Enter valid Mobile Number");
            }
            else if(txt_panno.getText().toString().equalsIgnoreCase(""))
            {
                mess("Enter valid PAN Number");
            }
            else
            {
                loginUser();
            }

        }
        else  if(page.equalsIgnoreCase("s"))
        {

            if(txt_first.getText().toString().equalsIgnoreCase(""))
            {
                mess("Enter valid First Name");
            }
            else if(txt_last.getText().toString().equalsIgnoreCase(""))
            {
                mess("Enter valid Last Name");
            }
            else if(txt_email.getText().toString().equalsIgnoreCase(""))
            {
                mess("Enter valid EmailID");
            }
            else if(txt_mobile.getText().toString().equalsIgnoreCase(""))
            {
                mess("Enter valid Mobile Number");
            }
            else if(txt_panno.getText().toString().equalsIgnoreCase(""))
            {
                mess("Enter valid PAN Number");
            }
            else
            {
                loginUser();
            }
        }
        else  if(page.equalsIgnoreCase("3"))
        {

            if(txt_first.getText().toString().equalsIgnoreCase(""))
            {
                mess("Enter valid First Name");
            }
            else if(txt_last.getText().toString().equalsIgnoreCase(""))
            {
                mess("Enter valid Last Name");
            }
            else if(txt_email.getText().toString().equalsIgnoreCase(""))
            {
                mess("Enter valid EmailID");
            }
            else if(txt_mobile.getText().toString().equalsIgnoreCase(""))
            {
                mess("Enter valid Mobile Number");
            }
            else if(txt_Membership.getText().toString().equalsIgnoreCase(""))
            {
                mess("Enter valid Membership Number");
            }
            else if(txt_panno.getText().toString().equalsIgnoreCase(""))
            {
                mess("Enter valid PAN Number");
            }
            else
            {
                loginUser();
            }
        }

    }



    public void mess(String mess) {
        Snackbar snackbar = Snackbar.make(back_btn, mess, Snackbar.LENGTH_LONG).setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.parseColor("#FA8072"));
        snackbar.show();
    }
    private void loginUser()
    {



        progressBar_main.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL+"update/RegisterProjectConsultant/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        progressBar_main.setVisibility(View.GONE);

                        //Toast.makeText(Activity_Login.this,response,Toast.LENGTH_LONG).show();
                        parseData(response);

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
                Map<String,String> params = new HashMap<String, String>();

                params.put("ProjectID",ProjectID);
                params.put("QPRID",qprid);
                params.put("RoleID",rolid);
                params.put("Type","QPR");
                params.put("FirstName",txt_last.getText().toString());
                params.put("LastName",txt_last.getText().toString());
                params.put("EmailID",txt_email.getText().toString());
                params.put("MobileNumber",txt_mobile.getText().toString());
                params.put("PAN",txt_panno.getText().toString());
                if(page.equalsIgnoreCase("1"))
                {
                    params.put("LicenceNumber",txt_regno.getText().toString());
                }
                else  if(page.equalsIgnoreCase("2"))
                {
                    params.put("LicenceNumber",txt_number.getText().toString());
                }
                else  if(page.equalsIgnoreCase("2a"))
                {

                }
                else  if(page.equalsIgnoreCase("s"))
                {

                }
                else  if(page.equalsIgnoreCase("3"))
                {
                    params.put("LicenceNumber",txt_Membership.getText().toString());

                }


                params.put("LicenceValidityDate",formatedate(txt_date.getText().toString()));
                params.put("ConsultantType",type);
                params.put("FirmName",txt_Firm.getText().toString());
                params.put("FirmRegNo",txt_Firmno.getText().toString());


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


    public void parseData(String response) {

        try
        {


            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("Code").equals("200"))
            {
                // JSONObject object = jsonObject.getJSONObject("Profile");

                Toast.makeText(getApplicationContext(),jsonObject.getString("Message")+"",Toast.LENGTH_LONG).show();

                finish();
                // mess(jsonObject.getString("Message"));

               /* Intent intent = new Intent(Activity_Login.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();*/

            }
            else
            {
                mess(jsonObject.getString("Message"));
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

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
            str="";
        }
        return str;
    }

    String uriSavedImage = "";
    private void imageUpload()
    {
        final CharSequence[] items = {"Take Photo", "Choose from Sdcard", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_consultant_add.this);
        builder.setTitle("Update Document");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {

                    // get prompts.xml view
                    LayoutInflater li = LayoutInflater.from(Activity_consultant_add.this);
                    View promptsView = li.inflate(R.layout.dialog_image_name, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            Activity_consultant_add.this);

                    // set prompts.xml to alertdialog builder
                    alertDialogBuilder.setView(promptsView);

                    final EditText userInput = (EditText) promptsView
                            .findViewById(R.id.editTextDialogUserInput);


                    // edit text
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

                } else if (items[item].equals("Cancel")) {
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
                        image_Upload.setImageBitmap(getResizedBitmap(myBitmap,200));

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
    private Uri picUri;
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
    Uri uri_SavedImage = null;
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
    String responsestr="";
    private class UploadData extends AsyncTask<String, String, String> {
        //  ProgressDialog pd;


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            progressBar_main.setVisibility(View.VISIBLE);
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
                // HttpPost httppost = new HttpPost("http://sighteat.com/imitra/admin/common-pages/file-upload.php?do=KYC&mediaid="+MediaID);
                HttpPost httppost = new HttpPost(Constants.IMG_URL+"do=kyc&DocumentID="+sm.getRegId());

                org.apache.http.entity.mime.MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

                // for(int i=0; i<photos.size(); i++)
                //{
                if (finalFile != null)
                    entity.addPart("myfile", new FileBody(finalFile));
                entity.addPart("RoleID", new StringBody(sm.getRoleID()));
                entity.addPart("UserID", new StringBody(sm.getUserId()));
                httppost.setEntity(entity);
                //httppost.setHeader("Content-Type", "application/json");
                // server call
                HttpResponse hResponse = httpClient.execute(httppost);
                HttpEntity hEntity = hResponse.getEntity();
                responsestr = EntityUtils.toString(hEntity);
                Log.e("Upload_Pics_res->", responsestr);

            } catch (ClientProtocolException e)
            {
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
                progressBar_main.setVisibility(View.GONE);
               // btn_submit.setVisibility(View.VISIBLE);
                //Toast.makeText(getApplicationContext(),responsestr, Toast.LENGTH_SHORT).show();
                JSONObject jsonObject = new JSONObject(responsestr);
                String mess=jsonObject.getString("Message");

                if (jsonObject.getString("Code").equals("200"))
                {
                    String id= jsonObject.getString("MediaID");
                    mess(mess);

                    // sm.setUserId(sm.getUserID(), sm.getRoleID(), sm.getRelationID(),jsonObject.getString("PathName") ,sm.getto(), sm.getjs()+"", sm.getmobileno(), sm.getlastname(), sm.getname());

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

}