package com.jack1i.ncatcher;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import static com.jack1i.ncatcher.NLService.INTENT_FILTER_MAIN;
import static com.jack1i.ncatcher.NLService.INTENT_FILTER_SERVICE;

public class MainActivity extends AppCompatActivity {

    private TextView txtView;
    private NotificationReceiver nReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtView = (TextView) findViewById(R.id.textView);
        nReceiver = new NotificationReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(INTENT_FILTER_MAIN);
        registerReceiver(nReceiver,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(nReceiver);
    }

    public void buttonClicked(View v) {

        if (v.getId() == R.id.btnCreateNotify) {
            NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Notification.Builder ncomp = new Notification.Builder(this);
            ncomp.setContentTitle("My Notification");
            ncomp.setContentText("Notification Listener Service Example");
            ncomp.setTicker("Notification Listener Service Example");
            ncomp.setSmallIcon(android.R.drawable.sym_def_app_icon);
            ncomp.setAutoCancel(true);
            nManager.notify((int) System.currentTimeMillis(), ncomp.build());
        } else if (v.getId() == R.id.btnClearNotify) {
            Log.i(this.getClass().getSimpleName(), "R.id.btnClearNotify");
            Intent i = new Intent(INTENT_FILTER_SERVICE);
            i.putExtra("command","clearall");
            sendBroadcast(i);
        } else if (v.getId() == R.id.btnListNotify) {
            Log.i(this.getClass().getSimpleName(), "R.id.btnListNotify");
            Intent i = new Intent(INTENT_FILTER_SERVICE);
            i.putExtra("command","list");
            sendBroadcast(i);
        }

    }

    class NotificationReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(this.getClass().getSimpleName(), "NotificationReceiver.onReceive");
            String temp = intent.getStringExtra("notification_event") + "n" + txtView.getText();
            txtView.setText(temp);
        }
    }

}