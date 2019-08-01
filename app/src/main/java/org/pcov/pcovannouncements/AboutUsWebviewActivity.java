package org.pcov.pcovannouncements;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class AboutUsWebviewActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus_webview);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        webView = findViewById(R.id.about_us_webview);
        webView.setWebViewClient(new WebViewClient());

        //Configure webView settings to eliminate WIX Site Problem
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("WebLinks")
                .document("pcov_about")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                ProgressBar progressBar = findViewById(R.id.webview_progressbar);
                progressBar.setVisibility(View.GONE);

                int position = getIntent().getIntExtra("position", 0);
                if (task.getResult() != null && getSupportActionBar() != null) {
                    if (position == 0) {
                        webView.loadUrl(task.getResult().getString("about_us"));
                        getSupportActionBar().setTitle(getString(R.string.about_us));
                    } else if (position == 1) {
                        webView.loadUrl(task.getResult().getString("for_newcommers"));
                        getSupportActionBar().setTitle(getString(R.string.for_newcomers));
                    } else if (position == 2) {
                        webView.loadUrl(task.getResult().getString("worship_services"));
                        getSupportActionBar().setTitle(getString(R.string.worship_services));
                    } else if (position == 3) {
                        webView.loadUrl(task.getResult().getString("church_history"));
                        getSupportActionBar().setTitle(getString(R.string.church_history));
                    } else {
                        webView.loadUrl(task.getResult().getString("visit_us"));
                        getSupportActionBar().setTitle(getString(R.string.visit_us));
                    }

                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // this takes the user 'back', as if they pressed the back button on the main android toolbar.
                super.onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
