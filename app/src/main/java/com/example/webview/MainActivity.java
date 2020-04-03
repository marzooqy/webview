package com.example.webview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    WebView webView;

    //set this to desired website
    static String websiteUrl = "https://www.example.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView = new WebView(this);
        setContentView(webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true); //enabling js
        webView.setWebViewClient(new CustomWebViewClient());
        webView.loadUrl(websiteUrl);
    }

    @Override
    public void onBackPressed() {
        //preventing the app from closing when it's not possible to go back anymore
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            Toast.makeText(getApplicationContext(), "You're in the main page", Toast.LENGTH_LONG).show();
        }
    }

    private class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            //customizing the app's behavior when opening links
            //if the link belongs to websiteUrl, then open the link
            //otherwise, if the user has an app on his phone that's capable of opening the link, then open the link in that app
            if (url.startsWith(websiteUrl)) {
                return false;
            } else {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } catch (ActivityNotFoundException error) {
                    Toast.makeText(getApplicationContext(), "There are not any apps in the device that can open this link", Toast.LENGTH_LONG).show();
                }
                return true;
            }
        }
    }
}