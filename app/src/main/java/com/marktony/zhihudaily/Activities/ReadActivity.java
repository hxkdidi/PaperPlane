package com.marktony.zhihudaily.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.marktony.zhihudaily.R;
import com.marktony.zhihudaily.Utils.Api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReadActivity extends AppCompatActivity {

    private WebView webViewRead;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private ImageView ivFirstImg;

    private RequestQueue queue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        initViews();

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");

        queue = Volley.newRequestQueue(getApplicationContext());

        //能够和js交互
        webViewRead.getSettings().setJavaScriptEnabled(true);
        //缩放
        //webViewRead.getSettings().setBuiltInZoomControls(true);
        //缓存
        webViewRead.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //开启DOM strong API功能

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, Api.NEWS + id, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    if (!jsonObject.getString("body").isEmpty()){
                        Glide.with(ReadActivity.this).load(jsonObject.getString("image")).centerCrop().into(ivFirstImg);
                        String css = null;
                        if (jsonObject.getJSONArray("css").length() != 0){
                            for (int i = 0;i < jsonObject.getJSONArray("css").length();i++){
                                css = "<link type=\"text/css\" href=\"" +
                                        jsonObject.getJSONArray("css").getString(i) +
                                        "\" " +
                                        "rel=\"stylesheet\" />\n";
                            }
                        }
                        String html = "<!DOCTYPE html>\n" +
                                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n" +
                                "<head>\n" +
                                "\t<meta charset=\"utf-8\" />\n</head>\n" +
                                "<body>\n"  +
                                css +
                                jsonObject.getString("body").replace("<div class=\"img-place-holder\">", "") + "\n<body>";
                        webViewRead.loadDataWithBaseURL("x-data://base",html,"text/html","utf-8",null);
                    } else {
                        ivFirstImg.setImageResource(R.drawable.no_img);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        queue.add(request);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    private void initViews() {

        webViewRead = (WebView) findViewById(R.id.wb_read);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ivFirstImg = (ImageView) findViewById(R.id.head_img);

    }
}
