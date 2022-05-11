package com.example.myweatherapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class MyCustomAdapter extends FragmentStateAdapter {


    public MyCustomAdapter(@NonNull FragmentActivity fa) {
        super(fa);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch(position){
            case 0:
                return new CurrentWeather();
            case 1:
                return new ForecastWeather();
            case 2:
                return new CitiesFragment();
        }
        return new CurrentWeather();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
