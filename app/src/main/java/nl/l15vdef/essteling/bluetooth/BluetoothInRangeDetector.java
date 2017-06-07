package nl.l15vdef.essteling.bluetooth;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by B3(Maarten Nieuwenhuize en Kevin Quist) on 18/05/2017.
 */

public class BluetoothInRangeDetector implements Runnable{

    private String TAG = "BluetoothInRangeDetect";

    private Map<String,Boolean> inRanges;
    private BluetoothAdapter mBluetoothAdapter;
    private List<BluetoothDevice> bluetoothDevices; //current bluetooth devices in range
    private BluetoothInRangeChanged listener;
    private Runnable bluetoothSearchLoop;
    private Handler bluetoothSearchHandler;
    private boolean running;
    private Activity activity;
    private int updateTime;
    private Handler permissionHandler;
    private BroadcastReceiver mReceiver;


    /**
     *
     *
     *
     * @param listener this activates when an check is done and gives back a Map with:
     *                 Strings - names you want to check if its in range.
     *                 Booleans - boolean if the device is in range.
     *
     * @param bluetoothNames list of names of bluetooth devices you want to check if they are in
     *                       range.
     * @param activity the activity in which you open this detector is required for permission checks.
     *
     * @param updateTime the period of time in milliseconds you want to check if the devices are in
     *                   range. (it takes time to detect a device, so preferably input an amount
     *                   greater then 5000
     * @throws BluetoothNotAvailableException is thrown when bluetooth is not enabled on the device
     * @throws LocationPermissionNotExceptedException is thrown when the user doesn't enable location
     *
     *
     */
    public BluetoothInRangeDetector(@NonNull final BluetoothInRangeChanged listener,
                                    @NonNull List<String> bluetoothNames, @NonNull Activity activity,
                                    final int updateTime)
            throws BluetoothNotAvailableException,
            LocationPermissionNotExceptedException {
        this.listener = listener;
        this.activity = activity;
        this.updateTime = updateTime;

        //init lists
        inRanges = new HashMap<>();
        for (String bluetoothName : bluetoothNames) {
            inRanges.put(bluetoothName,false);
        }
        bluetoothDevices = new ArrayList<>();

        //init bluetooth adapter
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        //if bluetooth is not available then throw BluetoothNotAvailableException
        if(mBluetoothAdapter == null){
            throw new BluetoothNotAvailableException();
        }

        //if bluetooth is not enabled then enable bluetooth
        if(!mBluetoothAdapter.isEnabled()){
            mBluetoothAdapter.enable();
        }

        //init boolean if the handler for searching should be running
        running = false;


        permissionHandler = new Handler();
        bluetoothSearchHandler = new Handler();

        //launches the receiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);

        //when an bluetooth device is detected this happens
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(BluetoothDevice.ACTION_FOUND.equals(action)){
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    //if an name == null then don't add it
                    if(device.getName() != null) {
                        bluetoothDevices.add(device);
                       // Log.d(TAG, "found " + device.getName());
                        for (String s : inRanges.keySet()) {
                            if(s.equals(device.getName())){
                                listener.bluetoothDeviceFoundandWasCheckedFor(device.getName());
                            }
                        }
                    }
                }
            }
        };

        activity.registerReceiver(mReceiver, filter);

        permissionHandler.post(this);

    }




    /**
     * This method stops the bluetooth search thread from running
     */
    public void stop(){
        Log.d("Bluetooth","bluetooth is stopped");
        try {
            activity.unregisterReceiver(mReceiver);
            running = false;
            mBluetoothAdapter.cancelDiscovery();
        }catch (Exception e){
            running = false;
            mBluetoothAdapter.cancelDiscovery();
            Log.e("bluetooth","errir");
        }

    }

    public void resume(){
        Log.d("Bluetooth","resume");
        running = true;
        bluetoothSearchHandler.post(bluetoothSearchLoop);
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        activity.registerReceiver(mReceiver, filter);
    }



    @Override
    public void run() {
        //checks if an permission is granted
        if(ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {



            //thread that loops every updateTime seconds
            //checks if the device name corresponds and sets inRanges to true if so
            //if not sets it to false

            bluetoothSearchLoop = new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG,"Checking bluetooth device in range.");
                    for (String s : inRanges.keySet()) {
                        inRanges.put(s,false);
                    }

                    //warning inRanges could be false when it actually is in range

                    for (BluetoothDevice bluetoothDevice : bluetoothDevices) {
                        String name = bluetoothDevice.getName();
                        for (String s : inRanges.keySet()) {
                            if(s.equals(name)){
                                inRanges.put(s,true);
                            }
                        }
                    }
                    //clear devices and research
                    bluetoothDevices.clear();
                    mBluetoothAdapter.cancelDiscovery();
                    if(running) {
                        mBluetoothAdapter.startDiscovery();
                        listener.bluetoothChecked(inRanges);
                        bluetoothSearchHandler.postDelayed(this, updateTime);
                    }
                }
            };
            running = true;
            bluetoothSearchHandler.post(bluetoothSearchLoop);

        }else {
            //opens an pop up when app first launches
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);
            if(Build.VERSION.SDK_INT >= 23){
                if(activity.shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)){
                    activity.finish();
                }
            }
            permissionHandler.postDelayed(this, 1000);
        }

    }
}







