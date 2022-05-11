package com.example.myweatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<SingleWeather> {


    public ListAdapter(Context context, ArrayList<SingleWeather> weatherArrayList){
        super(context, R.layout.list_item, weatherArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        SingleWeather singleWeather = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        ImageView imageView = convertView.findViewById(R.id.singleWeatherIcon);
        TextView cityName = convertView.findViewById(R.id.cityName);
        TextView temperature = convertView.findViewById(R.id.temperature);
        TextView description = convertView.findViewById(R.id.description);
        TextView date = convertView.findViewById(R.id.dateTextView);

        Picasso.get()
                .load(singleWeather.imageID)
                .into(imageView);

        cityName.setText(singleWeather.city.toUpperCase());
        temperature.setText(String.format("%d°C", singleWeather.temperature));
        description.setText(singleWeather.description.substring(0, 1).toUpperCase() + singleWeather.description.substring(1));
        date.setText(singleWeather.date);

        return convertView;

    }
}
