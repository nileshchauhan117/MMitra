package net.simplifiedcoding.navigationdrawerexample;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.ImageViewTarget;

import net.simplifiedcoding.navigationdrawerexample.Helper.Constants;
import net.simplifiedcoding.navigationdrawerexample.Helper.SessionManager;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Splash extends AppCompatActivity
{
    protected boolean _active = true;
    protected int _splashTime = 2000;
    TextView txt_version;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(Splash.this, R.color.maroon_dark));
        }
        try
        {
            PackageManager manager = getPackageManager();
            PackageInfo info = null;
            info = manager.getPackageInfo(getPackageName(), 0);
            String version = info.versionCode+"";
            Constants.versionCode_app=version;
            txt_version=(TextView)findViewById(R.id.txt_version);
            txt_version.setText("Version 1."+Constants.versionCode_app);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        //ImageView iView = (ImageView) findViewById(R.id.imageView2);
        // if (iView != null) Glide.with(this).load(R.drawable.trukker_logo).into(new GifDrawableImageViewTarget(iView, 1));

        ImageView iView1 = (ImageView) findViewById(R.id.imageView2);
        if (iView1 != null) Glide.with(this).load(R.drawable.tenor).into(new GifDrawableImageViewTarget(iView1, 1));

        session = new SessionManager(Splash.this);
        Thread splashThread = new Thread()
        {
            @Override
            public void run()
            {
                try {
                    int waited = 0;
                    while (_active && (waited < _splashTime))
                    {
                        sleep(100);

                        if (_active) {
                            waited += 100;
                        }
                    }

                } catch (InterruptedException e) {

                }
                finally
                {
                    startNavigationViewActivity(true);



                }
            }
        };
        splashThread.start();
    }

    /*  Start Navigation View activity  */
    private void startNavigationViewActivity(boolean value)
    {
        // finish();
        if (session.isLoggedIn())
        {
            try
            {

                profile();



            }
            catch (Exception e)
            {

            }


        }
        else
        {
            Intent intent = new Intent(Splash.this, Activity_Login.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent);
            overridePendingTransition(0, 0);
            finish();
        }

    }

    public class GifDrawableImageViewTarget extends ImageViewTarget<Drawable> {

        private int mLoopCount = GifDrawable.LOOP_FOREVER;

        public GifDrawableImageViewTarget(ImageView view, int loopCount) {
            super(view);
            mLoopCount = loopCount;
        }

        public GifDrawableImageViewTarget(ImageView view, int loopCount, boolean waitForLayout) {
            super(view);
            mLoopCount = loopCount;
        }

        @Override
        protected void setResource(@Nullable Drawable resource)
        {
            if (resource instanceof GifDrawable)
            {
                ((GifDrawable) resource).setLoopCount(mLoopCount);
            }
            view.setImageDrawable(resource);
        }
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
                                String RelationID="";
                                try
                                {
                                     RelationID = object.getString("RelationID");
                                }
                                catch (Exception e)
                                {

                                }

                                String PathName= object.getString("ProfilePicture");
                                // String TokenID = object.getString("TokenID");

                                String MediaID= object.getString("MediaID");
                                String KYCAlert = object.getString("KYCAlert");
                                String PasswordAlert= object.getString("PasswordAlert");


                                String Name = object.getString("Name");
                                String LastName = object.getString("LastName");
                                String MobileNumber = object.getString("MobileNumber");
                                String LoginID = object.getString("RoleName");

                                session.setUserId(UserID, RoleID, RelationID, PathName,   session.getto(), object+"", MobileNumber, LastName, Name,LoginID);

                                //Toast.makeText(getApplicationContext(),MobileNumber,Toast.LENGTH_LONG).show();
                                //mess(jsonObject.getString("Message"));

                                if(PasswordAlert.equalsIgnoreCase("N")&&KYCAlert.equalsIgnoreCase("N"))
                                {
                                    if(LoginID.equalsIgnoreCase("promoter"))
                                    {
                                        Intent intent = new Intent(Splash.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        startActivity(intent);
                                        overridePendingTransition(0, 0);
                                        finish();
                                    }

                                    else
                                    {
                                        Intent intent = new Intent(Splash.this, MainActivity_Ca.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                        startActivity(intent);
                                        overridePendingTransition(0, 0);
                                        finish();
                                    }


                                }
                                else
                                {
                                    Intent intent = new Intent(Splash.this, Activity_Verification.class);
                                    intent.putExtra("PasswordAlert",PasswordAlert);
                                    intent.putExtra("KYCAlert",KYCAlert);
                                    intent.putExtra("RoleID",RoleID);
                                    intent.putExtra("UserID",UserID);
                                    intent.putExtra("MediaID",MediaID);

                                    startActivity(intent);
                                    overridePendingTransition(0, 0);
                                    finish();
                                }


                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),jsonObject.getString("Message"),Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(Splash.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intent);
                                overridePendingTransition(0, 0);
                                finish();
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

                Log.e("Header",headers+"");
                return headers;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
