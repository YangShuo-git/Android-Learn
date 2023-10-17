package com.example.clientapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.content.ServiceConnection;

import com.example.twoapplication.ITwoAppInterface;

public class ClientAppActivity extends AppCompatActivity implements View.OnClickListener{
    private String TAG = "ClientAppActivity";
    private int mCount = 0;
    private ITwoAppInterface mBinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_app);
        Log.i(TAG, "onCreate: ClientAppActivity");

        findViewById(R.id.btnBindServiceApp).setOnClickListener(this);
        findViewById(R.id.btnUnBindServiceApp).setOnClickListener(this);
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = ITwoAppInterface.Stub.asInterface(service);
            mCount++;
            Log.i(TAG, "onServiceConnected:  count = " + mCount);

            try {
                String strData = "第" + mCount + "次连接Service成功！";
                mBinder.setStringData(strData);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG, "onServiceDisconnected()");
        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBindServiceApp: {
                Log.v(TAG, "onClick()：btnBindMainAppService");

                Intent intent = new Intent();
                ComponentName cpName = new ComponentName("com.example.twoapplication", "com.example.twoapplication.ServiceApp");
                intent.setComponent(cpName);

                boolean isBind = bindService(intent, connection, Context.BIND_AUTO_CREATE);
                Log.i(TAG, "bindService: isBind = " + isBind);
            }
            break;

            case R.id.btnUnBindServiceApp: {
                Log.v(TAG, "onClick()：btnUnBindMainAppService");
                unbindService(connection);
                mBinder = null;
            }
            break;
        }
    }
}