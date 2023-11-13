package com.example.assignmentwebbrowser;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.net.URL;

public class WebPageFragment extends Fragment {
    private String url;
    private WebView webView;
    public WebPageFragment() {
    }

    public static WebPageFragment newInstance(String url){
        WebPageFragment fragment=new WebPageFragment();
        Bundle args= new Bundle();
        args.putString("url",url);
        fragment.setArguments(args);
        return fragment;
    }


    @SuppressLint({"MissingInflatedId", "SetJavaScriptEnabled"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_page, container, false);

        TextView tabTitle = view.findViewById(R.id.tabTitle);
        webView = view.findViewById(R.id.myWebView);

        if (getArguments() != null) {
            url = getArguments().getString("url", "");
            tabTitle.setText(getHostName());

            // Initialize the WebView
            if (webView != null) {
                // Enable JavaScript and other settings
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
                webSettings.setDomStorageEnabled(true);
                webSettings.setLoadWithOverviewMode(true);
                webSettings.setUseWideViewPort(true);

                // Set a WebViewClient to handle URL loading internally
                webView.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView view, String url) {
                        view.loadUrl(url);
                        return true;
                    }
                });

                webView.loadUrl(url);
            } else {
                Log.e("WebPageFragment", "WebView is null. Cannot initialize.");
            }
        }

        return view;
    }

    String getHostName() {
        try {
            URL parsedUrl = new URL(this.url);
            String host = parsedUrl.getHost();
            if (host != null && host.startsWith("www.")) {
                return host.substring(4);
            } else if (host != null) {
                return host;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.url;
    }
    public void loadUrl(String url) {
        if (webView != null) {
            Log.d("WebPageFragment", "Loading URL in WebView: " + url);
            webView.loadUrl(url);
        } else {
            Log.e("WebPageFragment", "WebView is null. Cannot load URL: " + url);
        }
    }
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            webView.restoreState(savedInstanceState);
        }
    }
    public String getUrl() {
        return url;
    }

}
