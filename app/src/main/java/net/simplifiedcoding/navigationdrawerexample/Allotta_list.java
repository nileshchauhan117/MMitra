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
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Allotta_list extends AppCompatActivity {

    SessionManager sm;
    TextView text1,text2,text3,text4,text5,text6,text7,text8,text9,text10,text11;
    Button btn_name3,btn_name2,btn_name1;
    ImageView image_Upload3,image_Upload2,image_Upload1;
TextView title_de;
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
    String ProjectID,page,pname;
    TextView title1;
    SessionManager session;
    ImageView back_btn;
    TextView txt_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allotta_list);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(Allotta_list.this, R.color.maroon_dark));
        }
        txt_link=(TextView)findViewById(R.id.txt_link);
        title1=(TextView)findViewById(R.id.title1);
        back_btn=(ImageView)findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        title_de=(TextView)findViewById(R.id.title_de);
        Intent intent = getIntent();
        ProjectID = intent.getStringExtra("ProjectID");
        page = intent.getStringExtra("page");
        pname = intent.getStringExtra("pname");
       // title1.setText(pname);
        session = new SessionManager(Allotta_list.this);
        sm = new SessionManager(Allotta_list.this);
        text1=(TextView)findViewById(R.id.text1);
        text2=(TextView)findViewById(R.id.text2);
        text3=(TextView)findViewById(R.id.text3);
        text4=(TextView)findViewById(R.id.text4);
        text5=(TextView)findViewById(R.id.text5);
        text6=(TextView)findViewById(R.id.text6);
        text7=(TextView)findViewById(R.id.text7);
        text8=(TextView)findViewById(R.id.text8);
        text9=(TextView)findViewById(R.id.text9);
        text10=(TextView)findViewById(R.id.text10);
        text11=(TextView)findViewById(R.id.text11);
        image_Upload3=(ImageView) findViewById(R.id.image_Upload3);
        image_Upload2=(ImageView) findViewById(R.id.image_Upload2);
        image_Upload1=(ImageView) findViewById(R.id.image_Upload1);
        btn_name3=(Button) findViewById(R.id.btn_name3);
        btn_name2=(Button) findViewById(R.id.btn_name2);
        btn_name1=(Button) findViewById(R.id.btn_name1);

        image_Upload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



            }
        });

        image_Upload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        image_Upload3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btn_name1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                counter=1;
                /////
                Dexter.withActivity(Allotta_list.this)
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
                Dexter.withActivity(Allotta_list.this)
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
        btn_name3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                counter=3;
                /////
                Dexter.withActivity(Allotta_list.this)
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
        profile();

    }
    public static void hideKeyboardFrom(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    private void imageUpload()
    {
        hideKeyboardFrom(getApplicationContext(),btn_name1);
        final CharSequence[] items = {"Take Photo", "Choose from Sdcard", "Remove Photo","Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Allotta_list.this);
        builder.setTitle("Update Document");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {

                    // get prompts.xml view
                    LayoutInflater li = LayoutInflater.from(Allotta_list.this);
                    View promptsView = li.inflate(R.layout.dialog_image_name, null);

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            Allotta_list.this);

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
                        image_Upload1.setImageBitmap(getResizedBitmap(myBitmap,200));
                    }
                    else   if(counter==2)
                    {
                        image_Upload2.setImageBitmap(getResizedBitmap(myBitmap,200));
                    }
                    else   if(counter==3)
                    {
                        image_Upload3.setImageBitmap(getResizedBitmap(myBitmap,200));
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
                    image_Upload1.setImageBitmap(getResizedBitmap(myBitmap,200));
                }
                else   if(counter==2)
                {
                    image_Upload2.setImageBitmap(getResizedBitmap(myBitmap,200));
                }
                else   if(counter==3)
                {
                    image_Upload3.setImageBitmap(getResizedBitmap(myBitmap,200));
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
                    image_Upload1.setImageBitmap(getResizedBitmap(myBitmap,200));
                }
                else   if(counter==2)
                {
                    image_Upload2.setImageBitmap(getResizedBitmap(myBitmap,200));
                }
                else   if(counter==3)
                {
                    image_Upload3.setImageBitmap(getResizedBitmap(myBitmap,200));
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

                String url="";
                if(counter==1)
                    url="http://sighteat.com/imitra/admin/common-pages/file-upload.php?do=allottee-kyc&DocumentID=6";
                else if(counter==2)
                    url="http://sighteat.com/imitra/admin/common-pages/file-upload.php?do=allottee-afs&DocumentID=49";
                else if(counter==3)
                    url="http://sighteat.com/imitra/admin/common-pages/file-upload.php?do=allottee-photo&DocumentID=6";


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
        Snackbar snackbar = Snackbar.make(btn_name1, mess, Snackbar.LENGTH_LONG).setAction("Action", null);
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.parseColor("#ff3cb371"));
        snackbar.show();
    }
    private void profile()
    {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://sighteat.com/imitra/api/selection/getAllotteeUnitList/{\"UnitID\":"+ProjectID+"}",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {

                        try
                        {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("Code").equals("200"))
                            {

                                JSONArray subArray = jsonObject.getJSONArray("Units");
                               // Toast.makeText(getApplicationContext(),jsonObject+"",Toast.LENGTH_LONG).show();
                                String s=subArray.getJSONObject(0).getString("Detail").toString();
                                final JSONObject jsonObject1 = new JSONObject(s);
                                title_de.setText("Unit Name : "+jsonObject1.getString("BlockName")+"/"+jsonObject1.getString("UnitName")+"("+pname+")");
                                text1.setText(jsonObject1.getString("UsageType"));
                                text2.setText(jsonObject1.getString("AllotteeName"));
                                text3.setText(jsonObject1.getString("MobileNumber"));
                                text4.setText(jsonObject1.getString("KYCType"));
                                text5.setText(jsonObject1.getString("KYCNumber"));
                                text6.setText(jsonObject1.getString("AFSDate"));
                                text7.setText("₹ "+jsonObject1.getString("UnitAmount"));
                                text8.setText("₹ "+jsonObject1.getString("ReceivedAmount"));
                                text9.setText("₹ "+jsonObject1.getString("Balance"));
                                text10.setText(jsonObject1.getString("CarpetArea")+" sq. mtr.");
                                text11.setText(jsonObject1.getString("ExclusiveBalconyArea")+" sq. mtr.");

                                final String url_S=jsonObject1.getString("AFSFile");
                                String FileURL="",FileURL1="",FileURL2="";
                                FileURL=jsonObject1.getString("KYCFile");
                                FileURL1=jsonObject1.getString("AFSFile");
                                FileURL2=jsonObject1.getString("AllotteePhoto");


                                if(FileURL.equalsIgnoreCase("null")||FileURL.equalsIgnoreCase(""))
                                    Log.e("ddd","");
                                else
                                    Picasso.with(getApplicationContext()).load(FileURL).into(image_Upload1);

                                if(FileURL1.equalsIgnoreCase("null")||FileURL1.equalsIgnoreCase(""))
                                    Log.e("ddd","");
                                else
                                    Picasso.with(getApplicationContext()).load(FileURL1).into(image_Upload2);

                                if(FileURL2.equalsIgnoreCase("null")||FileURL2.equalsIgnoreCase(""))
                                    Log.e("ddd","");
                                else
                                    Picasso.with(getApplicationContext()).load(FileURL2).into(image_Upload3);


                                txt_link.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url_S));
                                        startActivity(browserIntent);
                                    }
                                });

                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),jsonObject.getString("Message"),Toast.LENGTH_LONG).show();

                            }
                        }
                        catch (Exception e)
                        {

                        }

                        // Toast.makeText(Activity_Login.this,response,Toast.LENGTH_LONG).show();
                        //parseData(response);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        //progressBar_main.setVisibility(View.GONE);
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
                headers.put("Tokenid", "" + session.getto());
                headers.put("Userid", "" + session.getUserID());
                headers.put("Roleid", "" + session.getRoleID());
                // headers.put("Relationid", "" + RelationID);
                return headers;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}

