package net.maximuma.player.maximumplayer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

public class PlayerActivity extends AppCompatActivity {

    protected static final String ADDRESS = "http://android-tv.maximuma.net";

    protected WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_player);
        load();
    }

    protected void load() {
        JavaScriptInterface JSInterface;
        setContentView(R.layout.activity_player);
        myWebView = (WebView) findViewById(R.id.webView);
        myWebView.loadUrl(ADDRESS);
        myWebView.getSettings().setJavaScriptEnabled(true);
        // register class containing methods to be exposed to JavaScript
        JSInterface = new JavaScriptInterface(this);
        myWebView.addJavascriptInterface(JSInterface, "JSInterface");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @Override
    protected void onPause() {
        myWebView.destroy();
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        load();
    }

    @Override
    protected void onResume() {
        super.onResume();
        load();
    }

    @Override
    protected void onStop() {
        myWebView.destroy();
        super.onStop();
    }

    public class JavaScriptInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        JavaScriptInterface(Context c) {
            mContext = c;
        }

        @JavascriptInterface
        public void changeActivity() {
            Intent i = new Intent(PlayerActivity.this, PlayerActivity.class);
            startActivity(i);
            finish();
        }
    }
}
