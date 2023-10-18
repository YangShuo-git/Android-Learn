package com.example.twoapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class ServiceApp extends Service {
    private String TAG = "ServiceApp";
    private String mStrData;
    private boolean mServiceRunning = true;

    public ServiceApp() {
    }

    IBinder mBinder = new ITwoAppInterface.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void setStringData(String strData) throws RemoteException {
            mStrData = strData;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind()");
        mServiceRunning = true;

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();

                while (mServiceRunning) {
                    try {
                        Thread.sleep(1000);
                        Log.i(TAG, "mStrData: " + mStrData);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        thread.start();
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG, "onUnbind()");
        mServiceRunning = false;
        return true;
    }
}