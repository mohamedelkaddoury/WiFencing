package com.himanshu.app.wifi;

import android.app.ListActivity;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    TextView[] txv = new TextView[10];
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lv=(ListView) findViewById(R.id.lv1);

        txv[0]=(TextView) findViewById(R.id.tv1);
       /* txv[1]=(TextView) findViewById(R.id.tv2);
        txv[2]=(TextView) findViewById(R.id.tv3);
        txv[3]=(TextView) findViewById(R.id.tv4);*/
       // txv5=(TextView) findViewById(R.id.tv5);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "List of available Wi-Fi", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                WifiManager wManager;
                List<ScanResult> wifiList;


                wManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);

                wifiList = wManager.getScanResults();
                txv[0].setText("");
                new IntentFilter(wManager.SCAN_RESULTS_AVAILABLE_ACTION);
                wManager.startScan();
                for (int i=0; i<wifiList.size(); i++){
                    ScanResult scanresult = wManager.getScanResults().get(i);
                    double exp = (27.55 - (20 * Math.log10(scanresult.frequency)) + Math.abs(scanresult.level)) / 20.0;

                    Math.pow(10.0, exp);

                    txv[0].append("RSSI: " + scanresult.level + "\n" + "Frequency: " + scanresult.frequency + "\n" + "BSSID: " + scanresult.BSSID + "\n" + "Capability: " + scanresult.capabilities + "\n" + scanresult.SSID + "\n" + scanresult.timestamp / 60000000 +  "\n"+"Approx Distance Of Access Point: "+Math.pow(10.0, exp)+" meters." +"\n\n");

                    try
                    {
                        File root = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
                        if (!root.exists()) {
                            root.mkdirs();
                        }
                        File gpxfile = new File(root, "safe.txt");
                        FileWriter writer = new FileWriter(gpxfile);
                        writer.append("AC");
                        writer.flush();
                        writer.close();
                    //    Toast.makeText( "Saved", Toast.LENGTH_SHORT).show();
                    }
                    catch(IOException e)
                    {
                        e.printStackTrace();

                    }


                }


            }
        });
    }


    }
