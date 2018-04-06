package me.kamili.rachid.notificationapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import me.kamili.rachid.notificationapp.adapter.CarAdapter;
import me.kamili.rachid.notificationapp.listeners.OnCarClickListene;
import me.kamili.rachid.notificationapp.model.Car;
import me.kamili.rachid.notificationapp.services.MyIntentService;

public class MainActivity extends AppCompatActivity implements OnCarClickListene {

    protected RecyclerView mRecyclerView;
    protected RecyclerView.Adapter mAdapter;
    protected List<Car> mCarList = new ArrayList<>();

    NotificationManager mNotificationManager;
    NotificationCompat.Builder mBuilder;
    PendingIntent mResultPendingIntent;
    TaskStackBuilder mTaskStackBuilder;
    Intent mResultIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindRecyclerView();
        startIntentService();

        initiateNotification();
    }

    private void initiateNotification() {
        mBuilder = new NotificationCompat.Builder(this);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mResultIntent = new Intent(this, MainActivity.class);
        mTaskStackBuilder = TaskStackBuilder.create(this);
        mTaskStackBuilder.addParentStack(MainActivity.this);

        mTaskStackBuilder.addNextIntent(mResultIntent);
        mResultPendingIntent = mTaskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(mResultPendingIntent);

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    private void startNotification(String title, String content) {
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(content);
        mNotificationManager.notify(1, mBuilder.build());
    }

    private void startIntentService() {
        Intent intIntent = new Intent(this, MyIntentService.class);
        startService(intIntent);
    }

    private void bindRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CarAdapter(mCarList, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(List<Car> data) {
        mCarList.addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public void onCarClick(Car car) {
        startNotification("Stolen Car : ", car.getModel() + " " + car.getType() + " " + car.getYear());
    }
}
