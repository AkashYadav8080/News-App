package com.example.newsapp.Activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.newsapp.R;

public class WebViewActivity extends AppCompatActivity {

    WebView webView;
//    ProgressBar progressBar;
    LinearLayout loaderLayout;
    TextView loaderText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_web_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String url = getIntent().getStringExtra("url").toString();

        webView = findViewById(R.id.webView);
//        progressBar = findViewById(R.id.progressBar);
        loaderLayout = findViewById(R.id.loaderLayout);
        loaderText = findViewById(R.id.loaderText);


        loaderLayout.setVisibility(View.VISIBLE); // Show loader initially
        webView.setVisibility(View.GONE); // Hide WebView initially

       // WebView Settings
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                loaderLayout.setVisibility(View.VISIBLE); // Show loader
                webView.setVisibility(View.GONE); // Hide WebView
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                loaderLayout.setVisibility(View.GONE); // Hide loader
                webView.setVisibility(View.VISIBLE); // Show WebView
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);

                loaderText.setText("Failed to load content. Please try again."); // Update loader text
                webView.setVisibility(View.GONE); // Keep WebView hidden
            }
        });

       // Load URL
        webView.loadUrl(url);

    }
}