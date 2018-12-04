package com.example.openai.translate;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import java.util.Locale;

public class Mean extends AppCompatActivity {

    WebView webView;
    Button save,notsave;
    FloatingActionButton speech;
    TextToSpeech t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mean);

        webView = (WebView) findViewById(R.id.webview1);
        save = (Button) findViewById(R.id.save);
        notsave = (Button) findViewById(R.id.notsave);
        notsave.setVisibility(View.INVISIBLE);
        speech = (FloatingActionButton) findViewById(R.id.speech);

        Intent call = getIntent();
        Bundle laydulieu = call.getBundleExtra("maintomean");

        final String temp;
        temp = laydulieu.getString("word");

        DbHelpter myDbHelper = new DbHelpter(Mean.this);
        Toast.makeText(Mean.this, "Successfully Imported", Toast.LENGTH_SHORT).show();
        String mean = myDbHelper.getmean(temp);
        WebSettings ws = webView.getSettings();
        ws.setJavaScriptEnabled(true);
        String staticContent="<!DOCTYPE html>\n" +
                "\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                "<body>\n" +
                ""+mean+"\n" +
                "</body>\n" +
                "</html>";
        webView.loadData(staticContent, "text/html", "UTF-8");

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelpter db = new DbHelpter(Mean.this);
                save.setVisibility(View.INVISIBLE);
                notsave.setVisibility(View.VISIBLE);
                db.addfav(temp);
                Toast.makeText(Mean.this, "Đã thêm \""+temp+"\" vào Từ của bạn", Toast.LENGTH_SHORT).show();

            }
        });
        notsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelpter db = new DbHelpter(Mean.this);
                notsave.setVisibility(View.INVISIBLE);
                save.setVisibility(View.VISIBLE);
                db.delfav(temp);
                Toast.makeText(Mean.this, "Đã xóa \""+temp+"\" khỏi Từ của bạn", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
