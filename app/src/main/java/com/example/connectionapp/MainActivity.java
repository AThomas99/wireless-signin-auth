package com.example.connectionapp;



import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.TextView;
import android.widget.Toast;
import android.net.wifi.WifiNetworkSuggestion;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    // Component Global variables
    TextView deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start Fetch Device Id
        deviceId = (TextView) findViewById(R.id.deviceID);
        @SuppressLint("HardwareIds")
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
            Toast.makeText(MainActivity.this, "Connected to configured network...!", Toast.LENGTH_LONG).show();

        }

    }


    public boolean isConnected() {
        // Working Code
         ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
         NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // return

        // Another Try - Connect to a specific network

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            final WifiNetworkSuggestion suggestion2 =
                    new WifiNetworkSuggestion.Builder()
                            .setSsid("egovridc-Vancho")
                            .setWpa2Passphrase("AjddKDZh")
                            .setIsAppInteractionRequired(true) // Optional (Needs location permission)
                            .build();

            final List<WifiNetworkSuggestion> suggestionsList =
                    new ArrayList<WifiNetworkSuggestion>();
            suggestionsList.add(suggestion2);

            WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
            final int status = wifiManager.addNetworkSuggestions(suggestionsList);
            if (status != WifiManager.STATUS_NETWORK_SUGGESTIONS_SUCCESS) {
                Toast.makeText(MainActivity.this, "Suggestion failed", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "Suggestion success", Toast.LENGTH_LONG).show();
            }

        }

        return networkInfo != null && networkInfo.isConnected();

    }


}