package com.example.binderdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PersonAIDLService extends Service {
    ArrayList<Person> persons = new ArrayList<Person>();
    private ICallback callback;

    // AIDL的ICallback接口是在客户端实现的;
    // AIDL的IPersonInterface接口是在服务端实现的;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("PersonAIDLService", "onBind");
        return binder;
    }

    Binder binder = new IPersonInterface.Stub() {
        @Override
        public void addPersonIn(Person person) throws RemoteException {
            Log.d("PersonAIDLService", "addPersonIn: persons=" + persons.toString());
            if (callback != null){
                callback.onResponse("服务端调用addPerson " + person.toString());
            }
            person.id = person.id + 1;
            persons.add(person);
        }

        @Override
        public void addPersonOut(Person person) throws RemoteException {
            Log.d("PersonAIDLService", "addPersonOut: persons=" + persons.toString());
            if (callback != null){
                callback.onResponse("服务端调用addPersonOut " + person.toString());
            }
            person.id = person.id + 1;
            persons.add(person);
        }

        @Override
        public void addPersonInout(Person person) throws RemoteException {
            Log.d("PersonAIDLService", "addPersonInout: persons=" + persons.toString());
            if (callback != null){
                callback.onResponse("服务端调用addPersonInOut "+person.toString());
            }
            person.id = person.id + 1;
            person.name = "inout-1";
            persons.add(person);
        }

        @Override
        public List<Person> getPersonList() throws RemoteException {
            Log.d("PersonAIDLService", "getPersonList: "+persons.toString());
            return persons;
        }

        @Override
        public void addCallback(ICallback callback) throws RemoteException {
            PersonAIDLService.this.callback = callback;
        }

        @Override
        public void removeCallback() throws RemoteException {
            PersonAIDLService.this.callback = null;
        }

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    };
}
