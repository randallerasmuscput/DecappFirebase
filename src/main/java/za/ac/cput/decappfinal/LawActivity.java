package za.ac.cput.decappfinal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import za.ac.cput.decappfinal.R;

/**
 * Created by User on 2016/06/14.
 */
// this activity displays the criminal procedure act for members to review
public class LawActivity extends AppCompatActivity{
    private WebView view;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_law);
        view = (WebView) this.findViewById(R.id.webView);
        view.getSettings().setJavaScriptEnabled(true);
        view.setWebViewClient(new MyBrowser());
        view.loadUrl("http://www.lawlibrary.co.za/resources/statutes_regulations/1977_51_criminal_procedure_ss19_36.htm"); //try js alert
        view.setWebChromeClient(new WebChromeClient()); // adding js alert support

    }
    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }






}
