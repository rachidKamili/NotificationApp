package me.kamili.rachid.notificationapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
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
import me.kamili.rachid.notificationapp.services.MusicService;
import me.kamili.rachid.notificationapp.services.MyIntentService;

public class MainActivity extends AppCompatActivity implements OnCarClickListene {

    protected RecyclerView mRecyclerView;
    protected RecyclerView.Adapter mAdapter;
    protected List<Car> mCarList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindRecyclerView();
        startIntentService();
        startMusicService();
    }

    private void startMusicService() {
        Intent service = new Intent(this, MusicService.class);
        startService(service);
    }

    private void scheduleNotification(final Notification notification, final int delay) {

//        Intent notificationIntent = new Intent(this, MainActivity.class);
//        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
//        taskStackBuilder.addParentStack(MainActivity.this);
//        taskStackBuilder.addNextIntent(notificationIntent);
//        final PendingIntent pendingIntent = taskStackBuilder.getPendingIntent( 0, PendingIntent.FLAG_UPDATE_CURRENT);

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                long futureInMillis = SystemClock.elapsedRealtime() + delay;
//                AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//                alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);

                ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).notify(1, notification);
            }

        }, delay);
    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Stolen Car : ");
        builder.setContentText(content);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        //builder.setWhen(SystemClock.elapsedRealtime() + 5000);
        return builder.build();
    }

    private void startIntentService() {
        Intent musicIntent = new Intent(this, MyIntentService.class);
        startService(musicIntent);
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
        scheduleNotification(getNotification(car.getModel() + " " + car.getType() + " " + car.getYear()), 5000);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        stopService(intent);
    }
}
