package com.example.myweatherapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CitiesListAdapter extends ArrayAdapter<SingleCity> {

    ArrayList<SingleCity> cities;

    public CitiesListAdapter(Context context, ArrayList<SingleCity> cities){
        super(context, R.layout.single_city, cities);
        this.cities = cities;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        SingleCity singleCity = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_city, parent, false);
        }

        TextView cityName = convertView.findViewById(R.id.cityName);
        Button removeCity = convertView.findViewById(R.id.removeCity);

        removeCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cities.remove(position);
                notifyDataSetChanged();
            }
        });

        cityName.setText(singleCity.cityName);

        return convertView;
    }
}
