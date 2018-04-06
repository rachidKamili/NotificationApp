package me.kamili.rachid.notificationapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import me.kamili.rachid.notificationapp.adapter.CarAdapter;
import me.kamili.rachid.notificationapp.model.Car;

public class MainActivity extends AppCompatActivity {

    protected RecyclerView mRecyclerView;
    protected RecyclerView.Adapter mAdapter;
    protected List<Car> mCarList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindRecyclerView();
    }

    private void bindRecyclerView() {
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCarList.add(new Car("honda","accord","2004"));
        mCarList.add(new Car("honda","civic","2005"));
        mAdapter = new CarAdapter(mCarList);
        mRecyclerView.setAdapter(mAdapter);
    }
}
