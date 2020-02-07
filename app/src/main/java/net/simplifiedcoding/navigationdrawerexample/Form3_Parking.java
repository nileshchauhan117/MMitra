package net.simplifiedcoding.navigationdrawerexample;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Form3_Parking extends Activity

{
    LinearLayout container;
    ProgressBar progressdisplay_layout;
    SessionManager sm;
    TextView tv_form3,tv_form2;
    String ProjectID="";
    TextView t_one, t_two,t_three,t_four;
    EditText tv_Garages,tv_oParkings,tv_cParkings;
    EditText tv_GaragesBooked,tv_oParkingsBooked,tv_cParkingsBooked;
    Map<String,String> params = new HashMap<String, String>();
    Button btn_save;
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
    File finalFile;
    static String image_path;
    public static File mypath;
    private Uri picUri;
    CheckBox radio_turms;
    LinearLayout layout_questions;
    String title="";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form3_parking);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setStatusBarColor(ContextCompat.getColor(Form3_Parking.this, R.color.maroon));
        }
        Intent intent = getIntent();
        ProjectID = intent.getStringExtra("ProjectID");
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
        back=(ImageView)findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sm = new SessionManager(Form3_Parking.this);
        tv_form2=(TextView)findViewById(R.id.tv_form2);
        btn_save=(Button)findViewById(R.id.btn_save);
        tv_form3=(TextView)findViewById(R.id.tv_form3);
        progressdisplay_layout= (ProgressBar)findViewById(R.id.progressBar2);
        container = (LinearLayout)findViewById(R.id.container);

        tv_Garages=(EditText)findViewById(R.id.tv_Garages);
        tv_oParkings=(EditText)findViewById(R.id.tv_oParkings);
        tv_cParkings=(EditText)findViewById(R.id.tv_cParkings);

        tv_GaragesBooked=(EditText)findViewById(R.id.tv_GaragesBooked);
                tv_oParkingsBooked=(EditText)findViewById(R.id.tv_oParkingsBooked);
        tv_cParkingsBooked=(EditText)findViewById(R.id.tv_cParkingsBooked);

        t_one=(TextView)findViewById(R.id.t_one);
        t_two=(TextView)findViewById(R.id.t_two);
        t_three=(TextView)findViewById(R.id.t_three);
        t_four=(TextView)findViewById(R.id.t_four);
        t_four.setBackgroundResource(R.color.white);

        loginUser();
        //////////////////////
        radio_turms= (CheckBox) findViewById(R.id.radio_turms);
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
                Dexter.withActivity(Form3_Parking.this)
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
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hideKeyboardFrom(getApplicationContext(),btn_save);
                callservices();

            }
        });
        img_pdf=(ImageView)findViewById(R.id.img_pdf);
        img_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://sighteat.com/imitra/api/pdf/form1.php?project=MQ==&qpr=MQ=="));
                startActivity(browserIntent);
            }
        });

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
        t_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                Intent i=new Intent(getApplicationContext(),Form3_BankLenders.class);
                i.putExtra("ProjectID",ProjectID);
                i.putExtra("title",title);
                startActivity(i);
            }
        });

    }
    private void loginUser()
    {
        progressdisplay_layout.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://sighteat.com/imitra/api/selection/getProjectList/{\"mode\":\"list\",\"ProjectID\":\""+ProjectID+"\",\"Screen\":\"Development\"}",
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

                                String Development=array1.getJSONObject(0).getString("Development");
                                JSONObject Developmenta =  new JSONObject(Development);

                                String OpenParkings=Developmenta.getString("OpenParkings");
                                String CoveredParkings=Developmenta.getString("CoveredParkings");
                                String NoOfGarage=Developmenta.getString("NoOfGarage");

                                tv_Garages.setText(""+NoOfGarage);
                                tv_oParkings.setText(""+OpenParkings);
                                tv_cParkings.setText(""+CoveredParkings);
                                tv_GaragesBooked.setText(""+Developmenta.getString("BookedGarage"));
                                tv_oParkingsBooked.setText(""+Developmenta.getString("BookedOpenParkings"));
                                tv_cParkingsBooked.setText(""+Developmenta.getString("BookedCoveredParkings"));

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
    private void callservices()
    {

        progressdisplay_layout.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sighteat.com/imitra/api/update/ParkingDetailEntry/",
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
                               // finish();
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

                params.put("ProjectID",ProjectID);
                params.put("NoOfGarage",tv_Garages.getText().toString());
                params.put("OpenParkings",tv_oParkings.getText().toString());
                params.put("CoveredParkings",tv_cParkings.getText().toString());
                params.put("BookedGarage",tv_GaragesBooked.getText().toString());
                params.put("BookedOpenParkings",tv_oParkingsBooked.getText().toString());
                params.put("BookedCoveredParkings",tv_cParkingsBooked.getText().toString());

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
    public void mess(String mess) {
        Snackbar snackbar = Snackbar.make(btn_save, mess, Snackbar.LENGTH_LONG).setAction("Action", null);
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
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Form3_Parking.this);
        builder.setTitle("Update Document");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {

                    // get prompts.xml view
                    LayoutInflater li = LayoutInflater.from(Form3_Parking.this);
                    View promptsView = li.inflate(R.layout.dialog_image_name, null);

                    android.app.AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            Form3_Parking.this);

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
                HttpPost httppost = new HttpPost("http://sighteat.com/imitra/admin/common-pages/file-upload.php?do=certificate&DocumentID=15&ProjectID="+ProjectID+"&QPRID=67");

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
}
