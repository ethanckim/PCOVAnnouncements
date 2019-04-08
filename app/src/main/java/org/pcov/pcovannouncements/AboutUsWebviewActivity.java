package org.pcov.pcovannouncements;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AboutUsWebviewActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus_webview);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView = findViewById(R.id.about_us_webview);
        webView.setWebViewClient(new WebViewClient());

        //Configure webView settings to eliminate WIX Site Problem
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        int position = getIntent().getIntExtra("position", 0);
        if (position == 0) {
            webView.loadUrl("https://vancouverphiladelp.wixsite.com/pcov/about_us");
            getSupportActionBar().setTitle(getString(R.string.about_us));
        } else if (position == 1) {
            webView.loadUrl("https://vancouverphiladelp.wixsite.com/pcov/blank");
            getSupportActionBar().setTitle(getString(R.string.for_newcomers));
        } else if (position == 2) {
            webView.loadUrl("https://vancouverphiladelp.wixsite.com/pcov/services");
            getSupportActionBar().setTitle(getString(R.string.worship_services));
        } else if (position == 3) {
            webView.loadUrl("https://vancouverphiladelp.wixsite.com/pcov/blank-1");
            getSupportActionBar().setTitle(getString(R.string.church_history));
        } else {
            webView.loadUrl("https://vancouverphiladelp.wixsite.com/pcov/visit_us");
            getSupportActionBar().setTitle(getString(R.string.visit_us));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
