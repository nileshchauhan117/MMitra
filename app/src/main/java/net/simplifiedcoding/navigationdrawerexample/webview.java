package net.simplifiedcoding.navigationdrawerexample;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class webview extends Activity {

    String url="";
    //private Button button;
    private WebView webView;
    ProgressBar progressBar_main;
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            getWindow().setStatusBarColor(ContextCompat.getColor(webview.this, R.color.maroon_dark));
        }
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
       // url = "https://i.stack.imgur.com/FXudQ.png";
        //Get webview
        progressBar_main = (ProgressBar) findViewById(R.id.progressBar_main);
        webView = (WebView) findViewById(R.id.webView);

        startWebView(url);

    }

    private void startWebView(String url) {

        //Create new webview Client to show progress dialog
        //When opening a url or click on link

        webView.setWebViewClient(new WebViewClient() {


            //If you will not use this method url links are opeen in new brower not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
               // view.loadUrl(url);
                view.loadDataWithBaseURL(null, "<html><head></head><body><table style=\"width:100%; height:100%;\"><tr><td style=\"vertical-align:middle;\"><img src=\"" + url + "\"></td></tr></table></body></html>", "html/css", "utf-8", null);
                return true;
            }

            //Show loader on url load
            public void onLoadResource (WebView view, String url) {

                    // in standard case YourActivity.this
                 //   progressBar_main.setVisibility(View.VISIBLE);

            }
            public void onPageFinished(WebView view, String url) {
                try{
                  //  progressBar_main.setVisibility(View.GONE);
                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }

        });

        // Javascript inabled on webview
        webView.getSettings().setJavaScriptEnabled(true);

        // Other webview options
        /*
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setBuiltInZoomControls(true);
        */

        /*
         String summary = "<html><body>You scored <b>192</b> points.</body></html>";
         webview.loadData(summary, "text/html", null);
         */

        //Load url in webview
        webView.loadUrl(url);


    }

    // Open previous opened link from history on webview when back button pressed

    @Override
    // Detect when the back button is pressed
    public void onBackPressed() {
        if(webView.canGoBack()) {
            webView.goBack();
        } else {
            // Let the system handle the back button
            super.onBackPressed();
        }
    }

}