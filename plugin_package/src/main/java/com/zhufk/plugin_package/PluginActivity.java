package com.zhufk.plugin_package;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PluginActivity extends BaseActivity {

    private static final String ACTION = "com.zhufk.plugin_package.Action";

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plugin);
        button1 = (Button) findViewById(R.id.test_btn);
        button2 = (Button) findViewById(R.id.test_start_service);
        button3 = (Button) findViewById(R.id.test_start_receive);
        button4 = (Button) findViewById(R.id.test_send_receive);
        String appName = getIntent().getStringExtra("appName");
        Toast.makeText(appActivity, appName, Toast.LENGTH_SHORT).show();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(appActivity, TestActivity.class));
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(ACTION);
                registerReceiver(new PluginReceiver(), intentFilter);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(ACTION);
                sendBroadcast(intent);
            }
        });
    }
}
