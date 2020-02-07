package net.simplifiedcoding.navigationdrawerexample;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
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
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Profile_Kyc extends AppCompatActivity
{
    ImageView back_btn;
    Button btn_creg;
    Button btn_pan;
    ImageView img_panno;
    ImageView img_creg;
    File finalFile;
    static String image_path;
    public static File mypath;
    int counter_image=0;
    String MediaID="";
    SessionManager sm;
    Button btn_submit;
    LinearLayout layout_regno;
    ProgressBar progressBar_main;
    ImageView img_aadhar,
    img_sign;
    EditText txt_panno,txt_liceno;
    TextView txt_expdate;
    LinearLayout layout_lic,layout_pan,layout_txt_expdate;
    TextView title_lic;
    TextInputLayout name_display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_kyc);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        sm = new SessionManager(Profile_Kyc.this);
        btn_submit=(Button)findViewById(R.id.btn_submit);
        //btn_submit.setVisibility(View.GONE);
        progressBar_main=(ProgressBar)findViewById(R.id.progressBar_main);
        layout_regno=(LinearLayout)findViewById(R.id.layout_regno);
        layout_lic=(LinearLayout)findViewById(R.id.layout_lic);
        txt_liceno=(EditText)findViewById(R.id.txt_aadharno);
        name_display=(TextInputLayout)findViewById(R.id.name_display);
        layout_pan=(LinearLayout)findViewById(R.id.layout_pan);
        layout_txt_expdate=(LinearLayout)findViewById(R.id.layout_txt_expdate);
        txt_panno=(EditText)findViewById(R.id.txt_panno);
        title_lic=(TextView) findViewById(R.id.title_lic);
        txt_expdate=(TextView) findViewById(R.id.txt_expdate);

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date5 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                txt_expdate.setText(dayOfMonth+"-"+monthOfYear+1+"-"+year);



            }

        };
        txt_expdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                new DatePickerDialog(Profile_Kyc.this, date5, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setStatusBarColor(ContextCompat.getColor(Profile_Kyc.this, R.color.maroon_dark));
        }

        back_btn=(ImageView)findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_creg=(Button) findViewById(R.id.btn_creg);
        btn_pan=(Button) findViewById(R.id.btn_pan);
        img_panno=(ImageView)findViewById(R.id.img_panno);
        img_aadhar=(ImageView)findViewById(R.id.img_aadhar);
        img_sign=(ImageView)findViewById(R.id.img_sign);
        img_creg=(ImageView)findViewById(R.id.img_creg);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if(sm.getloginid().equalsIgnoreCase("promoter"))
                {
                    Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
                    Matcher matcher = pattern.matcher(txt_panno.getText().toString());
                    if (matcher.matches())
                    {
                        submitUser();
                    }
                    else
                    {
                        mess("Enter valid PAN Number");
                    }

                }
                else
                {
                    if (txt_liceno.getText().toString().equalsIgnoreCase(""))
                    {
                        mess("Enter valid Number");
                        Log.i("Matching","Yes");

                    }
                    else
                    {
                        submitUser();
                    }
                }


            }
        });
        try
        {
           //JSONObject jsonObject = new JSONObject(sm.getjs());
           // MediaID = jsonObject.getString("MediaID");
            profile();


        }
        catch (Exception e)
        {

        }
        img_panno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                counter_image=1;
                Dexter.withActivity(Profile_Kyc.this)
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
                            public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {

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
        img_creg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                counter_image=2;
                Dexter.withActivity(Profile_Kyc.this)
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
                            public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {

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

        img_aadhar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                counter_image=3;
                Dexter.withActivity(Profile_Kyc.this)
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
                            public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {

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
        img_sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                counter_image=4;
                Dexter.withActivity(Profile_Kyc.this)
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
                            public void onPermissionRationaleShouldBeShown(List<com.karumi.dexter.listener.PermissionRequest> permissions, PermissionToken token) {

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

        if(sm.getloginid().equalsIgnoreCase("promoter"))
        {

            layout_txt_expdate.setVisibility(View.GONE);
            txt_liceno.setVisibility(View.GONE);
            txt_expdate.setVisibility(View.GONE);
            layout_regno.setVisibility(View.GONE);
            layout_lic.setVisibility(View.GONE);
            txt_panno.setVisibility(View.VISIBLE);
            layout_pan.setVisibility(View.VISIBLE);

        }
       else if(sm.getloginid().equalsIgnoreCase("Chartered Accountant"))
        {
            txt_liceno.setVisibility(View.VISIBLE);
            txt_expdate.setVisibility(View.GONE);
            layout_txt_expdate.setVisibility(View.GONE);
            layout_lic.setVisibility(View.VISIBLE);

            name_display.setHint("CA Membership Number");
            txt_liceno.setHint("CA Membership Number");
            txt_expdate.setHint("Membership Expiration Date");
            title_lic.setText("Upload Membership Photo");
            txt_panno.setVisibility(View.GONE);
            layout_pan.setVisibility(View.GONE);
        }
        else if(sm.getloginid().equalsIgnoreCase("engineer"))
        {
            txt_liceno.setVisibility(View.VISIBLE);
            txt_expdate.setVisibility(View.VISIBLE);
            layout_lic.setVisibility(View.VISIBLE);
            name_display.setHint("Firm Licence Number");
            txt_liceno.setHint("Firm Licence Number");
            txt_expdate.setHint("Licence Expiration Date");
            title_lic.setText("Upload Licence Photo");
            txt_panno.setVisibility(View.GONE);
            layout_pan.setVisibility(View.GONE);

        }
        else if(sm.getloginid().equalsIgnoreCase("architects"))
        {
            txt_liceno.setVisibility(View.VISIBLE);
            txt_expdate.setVisibility(View.VISIBLE);
            layout_lic.setVisibility(View.VISIBLE);

            name_display.setHint("COA Reg.No");
            txt_liceno.setHint("COA Reg.No");
            txt_expdate.setHint("Reg.No Expiration Date");
            title_lic.setText("Upload COA Photo");
            txt_panno.setVisibility(View.GONE);
            layout_pan.setVisibility(View.GONE);
        }
        else
        {


        }


    }
    String uriSavedImage = "";
    private void imageUpload()
    {
        final CharSequence[] items = {"Take Photo", "Choose from Sdcard", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Profile_Kyc.this);
        builder.setTitle("Update Document");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo"))
                {

                    // get prompts.xml view
                    LayoutInflater li = LayoutInflater.from(Profile_Kyc.this);
                    View promptsView = li.inflate(R.layout.dialog_image_name, null);

                   // AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Profile_Kyc.this);

                    // set prompts.xml to alertdialog builder
                   // alertDialogBuilder.setView(promptsView);

                    final EditText userInput = (EditText) promptsView
                            .findViewById(R.id.editTextDialogUserInput);

                    // set dialog message
                 //  alertDialogBuilder.setCancelable(false);
                  //  alertDialogBuilder.setPositiveButton("Done",
                          //  new DialogInterface.OnClickListener() {
                            //    public void onClick(DialogInterface dialog, int id) {
                                    // get user input and set it to result
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
                               // }
                        // });

                    // create alert dialog
                   // AlertDialog alertDialog = alertDialogBuilder.create();
                    // show it
                  //  alertDialog.show();

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
                    if(counter_image==1)
                    {
                        Bitmap myBitmap = BitmapFactory.decodeFile(finalFile.getAbsolutePath());
                        img_panno.setImageBitmap(getResizedBitmap(myBitmap,200));
                    }
                   else  if(counter_image==2)
                    {
                        Bitmap myBitmap = BitmapFactory.decodeFile(finalFile.getAbsolutePath());
                        img_creg.setImageBitmap(getResizedBitmap(myBitmap,200));

                    }
                    else  if(counter_image==3)
                    {
                        Bitmap myBitmap = BitmapFactory.decodeFile(finalFile.getAbsolutePath());
                        img_aadhar.setImageBitmap(getResizedBitmap(myBitmap,200));

                    }
                    else  if(counter_image==4)
                    {
                        Bitmap myBitmap = BitmapFactory.decodeFile(finalFile.getAbsolutePath());
                        img_sign.setImageBitmap(getResizedBitmap(myBitmap,200));

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
                if(counter_image==1)
                {
                    Bitmap myBitmap = BitmapFactory.decodeFile(finalFile.getAbsolutePath());
                    img_panno.setImageBitmap(getResizedBitmap(myBitmap,200));
                }
                else  if(counter_image==2)
                {
                    Bitmap myBitmap = BitmapFactory.decodeFile(finalFile.getAbsolutePath());
                    img_creg.setImageBitmap(getResizedBitmap(myBitmap,200));

                }
                else  if(counter_image==3)
                {
                    Bitmap myBitmap = BitmapFactory.decodeFile(finalFile.getAbsolutePath());
                    img_aadhar.setImageBitmap(getResizedBitmap(myBitmap,200));

                }
                else  if(counter_image==4)
                {
                    Bitmap myBitmap = BitmapFactory.decodeFile(finalFile.getAbsolutePath());
                    img_sign.setImageBitmap(getResizedBitmap(myBitmap,200));

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

                if(counter_image==1)
                {
                    Bitmap myBitmap = BitmapFactory.decodeFile(finalFile.getAbsolutePath());
                    img_panno.setImageBitmap(getResizedBitmap(myBitmap,200));
                }
                else  if(counter_image==2)
                {
                    Bitmap myBitmap = BitmapFactory.decodeFile(finalFile.getAbsolutePath());
                    img_creg.setImageBitmap(getResizedBitmap(myBitmap,200));

                }
                else  if(counter_image==3)
                {
                    Bitmap myBitmap = BitmapFactory.decodeFile(finalFile.getAbsolutePath());
                    img_aadhar.setImageBitmap(getResizedBitmap(myBitmap,200));

                }
                else  if(counter_image==4)
                {
                    Bitmap myBitmap = BitmapFactory.decodeFile(finalFile.getAbsolutePath());
                    img_sign.setImageBitmap(getResizedBitmap(myBitmap,200));

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

                String u="";
                if(sm.getloginid().equalsIgnoreCase("promoter"))
                {
                    u="http://sighteat.com/imitra/admin/common-pages/file-upload.php?do=kyc&DocumentID=2" ;
                }
                else
                {
                    u="http://sighteat.com/imitra/admin/common-pages/file-upload.php?do=kyc&DocumentID=42";

                }

                HttpPost httppost = new HttpPost(u);

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
                    mess_sucess(mess);
                    btn_submit.setVisibility(View.VISIBLE);
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
        Snackbar snackbar = Snackbar.make(back_btn, mess, Snackbar.LENGTH_LONG).setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.parseColor("#FA8072"));
        snackbar.show();
    }
    public void mess_sucess(String mess)
    {
        Snackbar snackbar = Snackbar.make(back_btn, mess, Snackbar.LENGTH_LONG).setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.parseColor("#ff3cb371"));
        snackbar.show();
    }
    private void profile()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://sighteat.com/imitra/api/selection/getUserProfile/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("Code").equals("200"))
                            {
                                JSONObject object = jsonObject.getJSONObject("ProfileData");
                                String UserID = object.getString("UserID");
                                String RoleID = object.getString("RoleID");
                               // String RelationID = object.getString("RelationID");
                                String PathName= object.getString("ProfilePicture");
                                String LoginID = object.getString("LoginID");
                                // String TokenID = object.getString("TokenID");
                                //String PromoterType=object.getString("PromoterType");
                              /*  if(PromoterType.equalsIgnoreCase("Company"))
                                {
                                    layout_regno.setVisibility(View.VISIBLE);
                                }
                                else
                                {
                                    layout_regno.setVisibility(View.GONE);
                                }*/


                              try
                              {
                                  String ContactDetail=object.getString("ContactDetail");
                                  JSONObject j=new JSONObject(ContactDetail);

                                  txt_liceno.setText(j.getString("LicenceNumber"));

                                  if(j.getString("LicenceValidityDate").equalsIgnoreCase("0000-00-00"))
                                    Log.e("ddd","ddd");
                                  else
                                  txt_expdate.setText(formatedate_server(j.getString("LicenceValidityDate")));

                                 /* if(j.getString("ConsultantType").equalsIgnoreCase("Partnership"))
                                  {
                                      txt_expdate.setVisibility(View.VISIBLE);

                                  }
                                  else
                                  {
                                      txt_expdate.setVisibility(View.GONE);
                                      layout_txt_expdate.setVisibility(View.GONE);
                                  }*/
                              }
                              catch (Exception e)
                              {

                              }
                                String Name = object.getString("Name");
                                String LastName = object.getString("LastName");
                                String MobileNumber = object.getString("MobileNumber");

                             //   sm.setUserId(UserID, RoleID, RelationID, PathName,   sm.getto(), object+"", MobileNumber, LastName, Name,LoginID);

                                MediaID = object.getString("MediaID");

                                try
                                {
                                    String res=object.getString("KYCDetail");
                                    JSONObject KYCDetail = new JSONObject(res);

                                    JSONObject PAN= new JSONObject(KYCDetail.getString("COMPANYREG"));
                                    String PANimage=PAN.getString("FileNumber");
                                    String FileURL=PAN.getString("FileURL");
                                    if(PANimage.equalsIgnoreCase("null")||PANimage.equalsIgnoreCase(""))
                                        Log.e("ddd","");
                                    else
                                        txt_panno.setText(PANimage);

                                    if(FileURL.equalsIgnoreCase("null")||FileURL.equalsIgnoreCase(""))
                                        Log.e("ddd","");
                                    else

                                    Picasso.with(getApplicationContext()).load(FileURL).placeholder(getResources().getDrawable(R.drawable.noi)).error(getResources().getDrawable(R.drawable.noi)).into(img_sign);




                                }
                                catch (Exception e)
                                {

                                }


                                try
                                {
                                    String res=object.getString("KYCDetail");
                                    JSONObject KYCDetail = new JSONObject(res);

                                    JSONObject PAN= new JSONObject(KYCDetail.getString("PAN"));
                                    String PANimage=PAN.getString("FileNumber");
                                    String FileURL=PAN.getString("FileURL");
                                    if(PANimage.equalsIgnoreCase("null")||PANimage.equalsIgnoreCase(""))
                                        Log.e("ddd","");
                                    else
                                    txt_panno.setText(PANimage);

                                    if(FileURL.equalsIgnoreCase("null")||FileURL.equalsIgnoreCase(""))
                                        Log.e("ddd","");
                                    else
                                        Picasso.with(getApplicationContext()).load(FileURL).placeholder(getResources().getDrawable(R.drawable.noi)).error(getResources().getDrawable(R.drawable.noi)).into(img_panno);





                                }
                                catch (Exception e)
                                {

                                }

                                try
                                {
                                    String res=object.getString("KYCDetail");
                                    JSONObject KYCDetail = new JSONObject(res);

                                    JSONObject PAN= new JSONObject(KYCDetail.getString("AADHAR"));
                                    String PANimage=PAN.getString("FileNumber");
                                    String FileURL=PAN.getString("FileURL");
                                    if(PANimage.equalsIgnoreCase("null")||PANimage.equalsIgnoreCase(""))
                                        Log.e("ddd","");
                                    else
                                        txt_liceno.setText(PANimage);

                                    if(FileURL.equalsIgnoreCase("null")||FileURL.equalsIgnoreCase(""))
                                        Log.e("ddd","");
                                    else
                                        Picasso.with(getApplicationContext()).load(FileURL).placeholder(getResources().getDrawable(R.drawable.noi)).error(getResources().getDrawable(R.drawable.noi)).into(img_aadhar);

                                    //Picasso.with(getApplicationContext()).load(FileURL).into(img_aadhar);

                                }
                                catch (Exception e)
                                {

                                }
                                try
                                {
                                    String res=object.getString("KYCDetail");
                                    JSONObject KYCDetail = new JSONObject(res);

                                    JSONObject PAN= new JSONObject(KYCDetail.getString("SIGN"));

                                    String FileURL=PAN.getString("FileURL");


                                    if(FileURL.equalsIgnoreCase("null")||FileURL.equalsIgnoreCase(""))
                                        Log.e("ddd","");
                                    else
                                        Picasso.with(getApplicationContext()).load(FileURL).placeholder(getResources().getDrawable(R.drawable.noi)).error(getResources().getDrawable(R.drawable.noi)).into(img_creg);

                                  //  Picasso.with(getApplicationContext()).load(FileURL).into(img_creg);

                                }
                                catch (Exception e)
                                {

                                }

                                //Toast.makeText(getApplicationContext(),MobileNumber,Toast.LENGTH_LONG).show();
                                // mess(jsonObject.getString("Message"));



                            }
                            else
                            {

                            }
                        }
                        catch (Exception e)
                        {

                            Log.e("dddd",e.toString());
                        }

                        // Toast.makeText(Activity_Login.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        // progressBar_main.setVisibility(View.GONE);
                        mess(error.toString());
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
    private void submitUser()
    {

        progressBar_main.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sighteat.com/imitra/api/update/SaveUserKYC/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            progressBar_main.setVisibility(View.GONE);
                            JSONObject jsonObject = new JSONObject(response);
                            String mess=jsonObject.getString("Message");
                            if (jsonObject.getString("Code").equals("200"))
                            {
                                mess_sucess(mess);


                                finish();
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
                        // Toast.makeText(Activity_Verification.this,response,Toast.LENGTH_LONG).show();


                        // parseData(response);

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
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();

             //   params.put("UserID",sm.getUserID());
              //  params.put("RoleID",sm.getRoleID());
              //  params.put("AADHARNumber",txt_aadharno.getText().toString());
                params.put("PANNumber",txt_panno.getText().toString());
                params.put("LicenceValidityDate",formatedate(txt_expdate.getText().toString()));
                params.put("LicenceNumber",txt_liceno.getText().toString());
              //  params.put("COMPANYREGNumber","");

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
                headers.put("Relationid", "" + "1");
                headers.put("Tokenid", "" + sm.getto());
                headers.put("Userid", "" + sm.getUserID());
                headers.put("Roleid", "" +sm.getRoleID());
                headers.put("CompanyRegFileID", "1");
                headers.put("PANFileID", "1");

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
