package com.example.activitydemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.activitydemo.activity.ServiceAidlActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener  {
    public static final String TAG = "MainActivity";
    private Button serviceBasicBtn;

    private Button serviceExampleBtn;

    private Button serviceAidlIpcBtn;

    private Button serviceMessagerIpcBtn;

    private Button contentProviderBasicBtn;
    private Button demoProviderBtn;

    private Button broadcastBtn;

    private Button broadcastPermissionBtn;

    private Button intentBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        serviceBasicBtn = findViewById(R.id.ac_main_servicebasic_btn);
        serviceExampleBtn = findViewById(R.id.ac_main_serviceexample_btn);
        serviceAidlIpcBtn = findViewById(R.id.ac_main_service_aidl_ipc_btn);
        serviceMessagerIpcBtn = findViewById(R.id.ac_main_service_messager_ipc_btn);
        contentProviderBasicBtn  = findViewById(R.id.ac_main_contentproviderbasic_btn);
        demoProviderBtn = findViewById(R.id.ac_main_contentprovidercustom_btn);
        broadcastBtn = findViewById(R.id.ac_main_broadcast_btn);
        broadcastPermissionBtn = findViewById(R.id.ac_main_broadcastpermission_btn);
        intentBtn  = findViewById(R.id.ac_main_intent_btn);

        serviceBasicBtn.setOnClickListener(this);
        serviceExampleBtn.setOnClickListener(this);
        serviceAidlIpcBtn.setOnClickListener(this);
        serviceMessagerIpcBtn.setOnClickListener(this);
        contentProviderBasicBtn.setOnClickListener(this);
        demoProviderBtn.setOnClickListener(this);
        broadcastBtn.setOnClickListener(this);
        broadcastPermissionBtn.setOnClickListener(this);
        intentBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ac_main_servicebasic_btn:
                Log.d(TAG, "onClick: TODO");
                break;
            case R.id.ac_main_serviceexample_btn:
                Log.d(TAG, "onClick: TODO");
                break;
            case R.id.ac_main_service_aidl_ipc_btn:
                goServiceAidlActivity();
                break;
            case R.id.ac_main_service_messager_ipc_btn:
                Log.d(TAG, "onClick: TODO");
                break;
            case R.id.ac_main_contentproviderbasic_btn:
                Log.d(TAG, "onClick: TODO");
                break;
            case R.id.ac_main_contentprovidercustom_btn:
                Log.d(TAG, "onClick: TODO");
                break;
            case R.id.ac_main_broadcast_btn:
                Log.d(TAG, "onClick: TODO");
                break;
            case R.id.ac_main_broadcastpermission_btn:
                Log.d(TAG, "onClick: TODO");
                break;
            case R.id.ac_main_intent_btn:
                Log.d(TAG, "onClick: TODO");
                break;
        }
    }
    private void goServiceAidlActivity(){
        Intent intent = new Intent(this, ServiceAidlActivity.class);
        startActivity(intent);
    }
}