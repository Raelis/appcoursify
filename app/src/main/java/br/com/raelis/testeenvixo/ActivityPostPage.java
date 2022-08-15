package br.com.raelis.testeenvixo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;

import java.util.ArrayList;

public class ActivityPostPage extends AppCompatActivity {

    String ShowOrHideWebViewInitialUse = "show";
    private WebView webview ;
    //private ProgressBar spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CoursifyPost post = AppData.post;

        View inflater = getLayoutInflater().inflate(R.layout.post_page, null, true);

        AppCompatTextView textViewTitle = inflater.findViewById(R.id.textView6);
        textViewTitle.setText(post.getTitleRendered());

        //AppCompatTextView textViewDescription = inflater.findViewById(R.id.textView7);
        //textViewDescription.setText(Html.fromHtml(post.getContentRendered()));

        webview = inflater.findViewById(R.id.webView2);
        //spinner = (ProgressBar)findViewById(R.id.progressBar1);
        webview.setWebViewClient(new CustomWebViewClient());

        //webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDefaultFontSize(32);
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webview.getSettings().setAppCacheEnabled(false);
        webview.getSettings().setLoadsImagesAutomatically(true);
        webview.getSettings().setGeolocationEnabled(false);
        webview.getSettings().setNeedInitialFocus(false);
        webview.getSettings().setSaveFormData(false);

        webview.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        webview.loadData(fixHtml(post.getContentRendered()), "text/html; charset=UTF-8", null);

        //Log.d("source",post.getContentRendered());

        setContentView(inflater);
    }

    // This allows for a splash screen
    // (and hide elements once the page loads)
    private class CustomWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView webview, String url, Bitmap favicon) {

            // only make it invisible the FIRST time the app is run
            if (ShowOrHideWebViewInitialUse.equals("show")) {
                webview.setVisibility(webview.INVISIBLE);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {

            ShowOrHideWebViewInitialUse = "hide";
            //spinner.setVisibility(View.GONE);

            view.setVisibility(webview.VISIBLE);
            super.onPageFinished(view, url);

        }
    }

    public String fixHtml(String html) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        html = html.replace("<p>&nbsp;</p>","");
        html = html.replace("&","");
        System.out.println(width);
        System.out.println(html);
        return html;
    }

}

