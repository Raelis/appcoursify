package br.com.raelis.testeenvixo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Recupera dados do singleton
        CoursifyPost post = AppData.post;

        // Infla o layout
        View inflater = getLayoutInflater().inflate(R.layout.post_page, null, true);

        // Add o destino do botão no footer
        Button footerButton = inflater.findViewById(R.id.button);
        footerButton.setOnClickListener(view -> {
            Uri uri = Uri.parse("https://coursify.me");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });

        // Add o título no post
        AppCompatTextView textViewTitle = inflater.findViewById(R.id.textView6);
        textViewTitle.setText(post.getTitleRendered());

        // Add a webview para o conteúdo do post
        webview = inflater.findViewById(R.id.webView2);
        webview.setWebViewClient(new CustomWebViewClient());
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

        // Seta o layout
        setContentView(inflater);
    }

    private class CustomWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView webview, String url, Bitmap favicon) {
            // apenas deixa invisível na PRIMEIRA vez que o aplicativo é executado
            if (ShowOrHideWebViewInitialUse.equals("show")) {
                webview.setVisibility(webview.INVISIBLE);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            ShowOrHideWebViewInitialUse = "hide";
            view.setVisibility(webview.VISIBLE);
            super.onPageFinished(view, url);
        }
    }

    // Método que retira "&" do código HTML
    public String fixHtml(String html) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        html = html.replace("<p>&nbsp;</p>","");
        html = html.replace("&","");
        //System.out.println(width);
        //System.out.println(html);
        return html;
    }

}

