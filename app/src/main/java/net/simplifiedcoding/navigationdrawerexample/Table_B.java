package net.simplifiedcoding.navigationdrawerexample;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
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
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import com.tooltip.Tooltip;

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

public class Table_B  extends AppCompatActivity {

    File finalFile;
    static String image_path;
    public static File mypath;

    LinearLayout container;
    Button btnsubmit;
    int count=0;
    JSONObject jsonObj = new JSONObject();
    String data="",classs="";
    int pos;
    ImageView image_Upload2;
    int pos1;
    ProgressBar progressBar_main;
    SessionManager sm;
    Map<String,String> params = new HashMap<String, String>();
    String ProjectID ="";
    String BlockID = "";
    String MediaID;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_b);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(Table_B.this, R.color.maroon_dark));
        }
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        back=(ImageView)findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressBar_main = (ProgressBar) findViewById(R.id.progressBar_main);
        sm = new SessionManager(Table_B.this);
        container = (LinearLayout)findViewById(R.id.container);
        btnsubmit=(Button)findViewById(R.id.btnsubmit);
        Intent intent = getIntent();
        data = intent.getStringExtra("data");
        classs = intent.getStringExtra("class");

        try
        {
            final JSONObject QPR = new JSONObject(data);
            // JSONObject Form1 = QPR.getJSONObject("Form1");
            JSONObject A = QPR.getJSONObject("B");
            //JSONArray array = A.getJSONArray("Points");
            final JSONArray  Evaluation = A.getJSONArray("Evaluation");
            for (int i = 0; i <= Evaluation.length(); i++)
            {

                    final RadioGroup rg;
                final RadioButton radio0,radio1;

                    LayoutInflater layoutInflater =(LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.form1_b_view, null);
                    final Spinner textOut = (Spinner) addView.findViewById(R.id.tv_header);

                List age = new ArrayList<Integer>();
                for (int k = 0;k <= 100; k++) {
                    age.add(Integer.toString(k));
                }
                ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, age);
                spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );
                textOut.setAdapter(spinnerArrayAdapter);
                textOut.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    @Override
                    public void onItemSelected(AdapterView adapter, View v, int i, long lng) {

                        hideKeyboardFrom(getApplicationContext(),btnsubmit);
                        //or this can be also right: selecteditem = level[i];
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parentView)
                    {
                        hideKeyboardFrom(getApplicationContext(),btnsubmit);
                    }
                });

                //textOut.setFilters(new InputFilter[]{ new MinMaxFilter("0", "100")});
                    final EditText txt_Quantity= (EditText) addView.findViewById(R.id.txt_Quantity);
                   final TextView tkk= (TextView) addView.findViewById(R.id.tkk);
                    TextView title= (TextView) addView.findViewById(R.id.titled);
                    final TextView title_r= (TextView) addView.findViewById(R.id.title);

                LinearLayout layout_name=(LinearLayout)addView.findViewById(R.id.layout_name);
                LinearLayout layout_name1=(LinearLayout)addView.findViewById(R.id.layout_name1);
                LinearLayout layout_image=(LinearLayout)addView.findViewById(R.id.layout_image);
                TextView img_name= (TextView) addView.findViewById(R.id.img_name);
                ImageView image_Upload= (ImageView) addView.findViewById(R.id.image_Upload);
                Button btn_name= (Button) addView.findViewById(R.id.btn_name);
                image_Upload2=image_Upload;

                    try
                    {

                        tkk.setText(Evaluation.getJSONObject(i).getString("Name"));

                        title.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Tooltip.Builder builder = new Tooltip.Builder(v, R.style.Tooltip2)
                                        .setCancelable(true)
                                        .setDismissOnClick(false)
                                        .setCornerRadius(20f)
                                        .setGravity(Gravity.BOTTOM)
                                        .setText(tkk.getText().toString());
                                builder.show();
                            }
                        });
                    }
                    catch (Exception e)
                    {

                    }
                radio0=(RadioButton) addView.findViewById(R.id.radio0_t);
                radio1=(RadioButton) addView.findViewById(R.id.radio1_t);
                    rg = (RadioGroup)addView.findViewById(R.id.radioGroup1);
                    layout_image.setVisibility(View.GONE);
                     if(i==(Evaluation.length()+1))
                     {
                         layout_name.setVisibility(View.GONE);
                         layout_name1.setVisibility(View.GONE);
                         layout_image.setVisibility(View.VISIBLE);
                     }


                     try
                     {
                         if(Evaluation.getJSONObject(i).getString("Proposal").equalsIgnoreCase("Y"))
                         {
                             txt_Quantity.setEnabled(true);
                             textOut.setEnabled(true);
                             title_r.setText("Y");
                             radio0.setChecked(true);
                             radio1.setChecked(false);
                         }
                         else if(Evaluation.getJSONObject(i).getString("Proposal").equalsIgnoreCase("N"))
                         {
                             txt_Quantity.setVisibility(View.GONE);
                             textOut.setVisibility(View.GONE);
                             title_r.setText("N");
                              radio0.setChecked(false);
                              radio1.setChecked(true);
                         }
                         else if(Evaluation.getJSONObject(i).getString("Proposal").equalsIgnoreCase("null"))
                         {
                             txt_Quantity.setVisibility(View.GONE);
                             textOut.setVisibility(View.GONE);
                             title_r.setText("N");
                            radio0.setChecked(false);
                           radio1.setChecked(true);
                         }


                         title.setText(i+1+". "+Evaluation.getJSONObject(i).getString("Name"));

                         textOut.setSelection(Integer.parseInt(Evaluation.getJSONObject(i).getString("WorkDone")));
                         //textOut.setText(Evaluation.getJSONObject(i).getString("WorkDone"));
                         if(Evaluation.getJSONObject(i).getString("Remark").equalsIgnoreCase("null"))
                             txt_Quantity.setText("");
                         else
                         txt_Quantity.setText(Evaluation.getJSONObject(i).getString("Remark"));

                     }
                     catch (Exception e)
                     {
                         layout_name.setVisibility(View.GONE);
                         layout_image.setVisibility(View.VISIBLE);
                     }

                    rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        // TODO Auto-generated method stub

                        hideKeyboardFrom(getApplicationContext(),btnsubmit);
                        // Method 1 For Getting Index of RadioButton
                        pos=rg.indexOfChild(addView.findViewById(checkedId));

                       // Toast.makeText(getBaseContext(), "Method 1 ID = "+String.valueOf(pos),Toast.LENGTH_SHORT).show();

                        //Method 2 For Getting Index of RadioButton
                        pos1=rg.indexOfChild(addView.findViewById(rg.getCheckedRadioButtonId()));

                       // Toast.makeText(getBaseContext(), "Method 2 ID = "+String.valueOf(pos1),Toast.LENGTH_SHORT).show();

                        switch (pos)
                        {
                            case 0 :
                                txt_Quantity.setVisibility(View.VISIBLE);
                                textOut.setVisibility(View.VISIBLE);
                                title_r.setText("Y");
                                break;
                            case 1 :

                                txt_Quantity.setVisibility(View.GONE);
                                textOut.setVisibility(View.GONE);
                                title_r.setText("N");
                                break;


                            default :
                                //The default selection is RadioButton 1
                                txt_Quantity.setVisibility(View.VISIBLE);
                                textOut.setVisibility(View.VISIBLE);
                                title_r.setText("Y");
                                break;
                        }
                    }
                });



                btn_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v)
                    {


                        Dexter.withActivity(Table_B.this)
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
                    container.addView(addView);



            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }



        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                hideKeyboardFrom(getApplicationContext(),btnsubmit);
                count = container.getChildCount();

                if(count>0)
                {
                    // JSONArray jsonArr = new JSONArray();

                    for(int i1=0;i1<=count;i1++)
                    {

                        //  JSONObject prmsLogin = new JSONObject();

                        try
                        {
                            View row = container.getChildAt(i1);
                            // current_count=current_count+1;
                            EditText textOut = (EditText) row.findViewById(R.id.tv_header);
                            EditText txt_Quantity = (EditText) row.findViewById(R.id.txt_Quantity);
                             TextView title_r= (TextView) row.findViewById(R.id.title);
                            params.put("Work"+(i1+1),textOut.getText().toString());

                            params.put("Remark"+(i1+1),txt_Quantity.getText().toString());
                            params.put("Proposal"+(i1+1),title_r.getText().toString());


                            // prmsLogin.put("transaction_id",textOut.getText().toString());
                            // jsonArr.put(prmsLogin);

                        }
                        catch (Exception e)
                        {

                        }
                    }

                    try
                    {
                        params.put("NOC",MediaID+"");
                        jsonObj.put("param", params);

                        loginUser();
                    }
                    catch (Exception e)
                    {

                    }


                }
            }
        });



    }
    private void loginUser()
    {

        progressBar_main.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sighteat.com/imitra/api/update/SaveForm1BDetail/",
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
                                Intent i = new Intent();
                                setResult(RESULT_OK, i);
                                finish();
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

                params.put("ProjectID",classs);
                params.put("NOC","");

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
        Snackbar snackbar = Snackbar.make(btnsubmit, mess, Snackbar.LENGTH_LONG).setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.parseColor("#FA8072"));
        snackbar.show();
    }
    String uriSavedImage = "";
    private void imageUpload()
    {
        hideKeyboardFrom(getApplicationContext(),btnsubmit);
        final CharSequence[] items = {"Take Photo", "Choose from Sdcard", "Remove Photo","Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Table_B.this);
        builder.setTitle("Update Document");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {

                    // get prompts.xml view
                    LayoutInflater li = LayoutInflater.from(Table_B.this);
                    View promptsView = li.inflate(R.layout.dialog_image_name, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            Table_B.this);

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
                    image_Upload2.setImageBitmap(getResizedBitmap(myBitmap,200));
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
               image_Upload2.setImageBitmap(getResizedBitmap(myBitmap,200));

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
                image_Upload2.setImageBitmap(getResizedBitmap(myBitmap,200));

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
                HttpPost httppost = new HttpPost("http://sighteat.com/imitra/admin/common-pages/file-upload.php?do=noc-document&DocumentID=12");

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
                     MediaID=jsonObject.getString("MediaID");
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
    private void  requestMultiplePermissions()
    {
        Dexter.withActivity(this)
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
        Snackbar snackbar = Snackbar.make(progressBar_main, mess, Snackbar.LENGTH_LONG).setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.parseColor("#ff3cb371"));
        snackbar.show();
    }
    public class MinMaxFilter implements InputFilter {

        private int mIntMin, mIntMax;

        public MinMaxFilter(int minValue, int maxValue) {
            this.mIntMin = minValue;
            this.mIntMax = maxValue;
        }

        public MinMaxFilter(String minValue, String maxValue) {
            this.mIntMin = Integer.parseInt(minValue);
            this.mIntMax = Integer.parseInt(maxValue);
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (isInRange(mIntMin, mIntMax, input))
                    return null;
            } catch (NumberFormatException nfe) { }
            return "";
        }

        private boolean isInRange(int a, int b, int c) {
            return b > a ? c >= a && c <= b : c >= b && c <= a;
        }
    }
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}