package me.kamili.rachid.notificationapp.services;

import android.app.IntentService;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import me.kamili.rachid.notificationapp.model.Car;

public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            EventBus.getDefault().post(initData());
        }
    }

    public List<Car> initData(){
        List<Car> cars = new ArrayList<>();

        cars.add(new Car("honda","accord","2004"));
        cars.add(new Car("honda","civic","2005"));
        cars.add(new Car("honda","accord","2006"));
        cars.add(new Car("toyota","camry","2018"));
        cars.add(new Car("toyota","camry","2006"));

        return cars;
    }

}
