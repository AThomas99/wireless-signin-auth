package com.example.connectionapp;

import static androidx.core.content.PackageManagerCompat.LOG_TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    // Component Global variables
    TextView deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start Fetch Device Id
        deviceId = (TextView) findViewById(R.id.deviceID);
        String id = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        String deviceIdentification = "Device ID: ";
        deviceId.setText(deviceIdentification + id);
        // End Fetch Device Id


        if (!isConnected()) { // If not connected
            // Will change the UI latter
            new AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Internet Connection Error")
                    .setMessage("Please Check Your Internet Connection\n  .Turn On WIFI\n  .Try Again Later")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
        } else {
            Toast.makeText(MainActivity.this, "Please Wait...!", Toast.LENGTH_LONG).show();

        }

    }


    public boolean isConnected() {
        // Working Code
        // ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // return networkInfo != null && networkInfo.isConnected();

        // Another Try - Connect to a specific network
        // Variables for Wireless connection
        String networkSSID = "egovridc-Vancho";
        String networkPass = "AjddKDZh";

        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = "\"" + networkSSID + "\"";

        conf.wepKeys[0] = "\"" + networkPass + "\"";
        conf.wepTxKeyIndex = 0;
        conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);

        conf.preSharedKey = "\""+ networkPass +"\"";

        conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);

        WifiManager wifiManager = (WifiManager)getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        wifiManager.addNetwork(conf);

        return true;
    }


}