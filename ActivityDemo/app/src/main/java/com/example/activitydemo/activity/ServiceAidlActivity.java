package com.example.activitydemo.activity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.activitydemo.R;
import com.example.binderdemo.ICallback;
import com.example.binderdemo.IPersonInterface;
import com.example.binderdemo.Person;

import java.util.List;


public class ServiceAidlActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "ServiceAidlActivity";
    private IPersonInterface iPersonManager;

    private ServiceConnection conn = new ServiceConnection() {
        // Activity与Service连接成功时回调该方法
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i(TAG,"onServiceConnected");
            iPersonManager = IPersonInterface.Stub.asInterface(service);
            try {
                iPersonManager.addCallback(callback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        // Activity与Service断开连接时回调该方法
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.i(TAG,"onServiceDisconnected");
        }
    };

    private ICallback callback = new ICallback.Stub() {
        @Override
        public void onResponse(String tag) throws RemoteException {
            Log.i(TAG,"客户端的方法, tag = " + tag);
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_aidl_ipc);

        Button bindRemoteService = findViewById(R.id.ac_ipcservice_bind);
        bindRemoteService.setOnClickListener(this);

        Button setRemoteService = findViewById(R.id.ac_ipcservice_set_in);
        setRemoteService.setOnClickListener(this);

        Button setRemoteServiceOut = findViewById(R.id.ac_ipcservice_set_out);
        setRemoteServiceOut.setOnClickListener(this);

        Button setRemoteServiceInOut = findViewById(R.id.ac_ipcservice_set_inout);
        setRemoteServiceInOut.setOnClickListener(this);

        Button getRemoteService = findViewById(R.id.ac_ipcservice_get);
        getRemoteService.setOnClickListener(this);

        Button unbindRemoteService = findViewById(R.id.ac_ipcservice_unbind);
        unbindRemoteService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ac_ipcservice_bind:
                bindRemoteService();
                break;
            case R.id.ac_ipcservice_set_in:
                setPerson();
                break;
            case R.id.ac_ipcservice_set_out:
                setPersonOut();
                break;
            case R.id.ac_ipcservice_set_inout:
                setPersonInOut();
                break;
            case R.id.ac_ipcservice_get:
                getPerson();
                break;
            case R.id.ac_ipcservice_unbind:
                if (iPersonManager != null){
                    unbindService(conn);
                    iPersonManager = null;
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        Log.i(TAG,"onDestroy");
        if (iPersonManager != null){
            unbindService(conn);
        }
        super.onDestroy();
    }

    private void bindRemoteService(){
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.example.binderdemo",
                "com.example.binderdemo.PersonAIDLService"));
        boolean isBind = bindService(intent, conn, Service.BIND_AUTO_CREATE);
        Log.i(TAG,"bindRemoteService isBind = " + isBind);
    }

    private void setPerson(){
        if (iPersonManager == null){
            return;
        }
        try {
            Person person = new Person(24, "in");
            Log.d(TAG, " setPerson-1" + person.toString());
            iPersonManager.addPerson(person);
            Log.d(TAG, " setPerson" + person.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void setPersonOut(){
        if (iPersonManager == null){
            return;
        }
        try {
            Person person = new Person(32, "out");
            Log.d(TAG, " setPersonOut" + person.toString());
            iPersonManager.addPersonOut(person);
            Log.d(TAG, " setPersonOut-1" + person.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void setPersonInOut(){
        if (iPersonManager == null){
            return;
        }
//        try {
//            Person person = new Person(40, "inout");
//            Log.d(TAG, " setPersonInOut" + person.toString());
//            iPersonManager.addPersonInOut(person);
//            Log.d(TAG, " setPersonInOut-1" + person.toString());
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
    }

    private void getPerson(){
        if (iPersonManager == null){
            return;
        }
        try {
            List<Person> persons = iPersonManager.getPersonList();
            Log.d(TAG, " 获取的数据" + persons.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
