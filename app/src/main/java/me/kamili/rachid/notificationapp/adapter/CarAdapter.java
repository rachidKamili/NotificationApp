package me.kamili.rachid.notificationapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.kamili.rachid.notificationapp.R;
import me.kamili.rachid.notificationapp.listeners.OnCarClickListene;
import me.kamili.rachid.notificationapp.model.Car;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.ViewHolder> {

    private List<Car> mCarList;
    private final OnCarClickListene listener;

    public CarAdapter(List<Car> dataSet, OnCarClickListene listene ) {
        mCarList = dataSet;
        listener = listene;
    }

    @NonNull
    @Override
    public CarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.car_item_view, parent, false);

        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull CarAdapter.ViewHolder holder, int position) {
        Car car = mCarList.get(position);
        holder.tvModel.setText(car.getModel());
        holder.tvType.setText(car.getType());
        holder.tvYear.setText(car.getYear());
        holder.bind(mCarList.get(position), listener);
    }



    @Override
    public int getItemCount() {
        return mCarList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvModel;
        TextView tvType;
        TextView tvYear;
        public ViewHolder(View itemView) {
            super(itemView);
            tvModel = itemView.findViewById(R.id.tvModel);
            tvType = itemView.findViewById(R.id.tvType);
            tvYear = itemView.findViewById(R.id.tvYear);
        }

        public void bind(final Car item, final OnCarClickListene listener) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onCarClick(item);
                }
            });
        }
    }
}
