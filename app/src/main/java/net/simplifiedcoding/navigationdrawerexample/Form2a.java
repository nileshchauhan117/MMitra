package net.simplifiedcoding.navigationdrawerexample;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;
import com.squareup.picasso.Picasso;

import net.simplifiedcoding.navigationdrawerexample.Helper.Constants;
import net.simplifiedcoding.navigationdrawerexample.Helper.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Form2a extends Activity   {

    String url = "";
    //private Button button;
    private WebView webView;
    ProgressBar progressBar_main;
    private static final String TAG = MainActivity.class.getSimpleName();

    PDFView pdfView;
    Integer pageNumber = 0;
    String pdfFileName;
    ImageView img_pdf;
    ProgressBar progressdisplay_layout;
    String ProjectID="";
    SessionManager sm;
    ImageView back;
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form2a);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(ContextCompat.getColor(Form2a.this, R.color.maroon_dark));
        }
        sm = new SessionManager(Form2a.this);
        progressdisplay_layout=(ProgressBar) findViewById(R.id.progressBar_main);
        progressdisplay_layout.setVisibility(View.GONE);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        ProjectID = intent.getStringExtra("ProjectID");

        back=(ImageView)findViewById(R.id.back_btn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        webView = (WebView) findViewById(R.id.webView);
        img_pdf=(ImageView)findViewById(R.id.img_pdf);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptEnabled(true);

        loginUser();


    }
    private void loginUser()
    {
        progressdisplay_layout.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://sighteat.com/imitra/api/selection/getProjectList/{\"mode\":\"list\",\"ProjectID\":\""+ProjectID+"\",\"Screen\":\"Form2A\"}",
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

                                String res=array1.getJSONObject(0).getString("Form2AURL");
                             //   String url = "https://www.adobe.com/devnet/acrobat/pdfs/pdf_open_parameters.pdf";
                                // webView.loadUrl("https://docs.google.com/gview?embedded=true&url="+url);

                                webView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + res);
                                progressdisplay_layout.setVisibility(View.VISIBLE);
                                webView.setWebViewClient(new WebViewClient()
                                {
                                    public void onPageFinished(WebView view, String url)
                                    {
                                        progressdisplay_layout.setVisibility(View.GONE);
                                    }
                                });
                                final JSONObject QPR =  new JSONObject(res);
                                final String FormURL=QPR.getString("FormURL");
                                img_pdf.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        Intent pdfIntent = new Intent(Intent.ACTION_VIEW);
                                        pdfIntent.setDataAndType(Uri.parse(FormURL), "application/pdf");
                                        pdfIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                                        try{
                                            startActivity(pdfIntent);
                                        }catch(ActivityNotFoundException e1){
                                            Toast.makeText(Form2a.this, "No Application available to view PDF", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
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

}
