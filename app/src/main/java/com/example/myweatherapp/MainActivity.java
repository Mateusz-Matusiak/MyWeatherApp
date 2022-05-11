package com.example.myweatherapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements MainFragment.IRefreshListener, CitiesFragment.IChangeCity{

    private final String apiUrl = "https://api.openweathermap.org/data/2.5";
    private final String appid = "66171ac9614ea64b50da3c73b1beafa2";
    private String currentCity;
    private MainFragment mainFragment;
    private ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFragment = new MainFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.titleFragment, mainFragment)
                .commit();

        viewPager2 = findViewById(R.id.viewPager2);
        MyCustomAdapter myCustomAdapter = new MyCustomAdapter(this);
        viewPager2.setAdapter(myCustomAdapter);


        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onRefreshClicked() {
        Fragment f0 = getSupportFragmentManager().findFragmentByTag("f0");
        if(f0 instanceof CurrentWeather){
            CurrentWeather currentWeather = (CurrentWeather) f0;
            this.currentCity = currentWeather.currentCity.getText().toString();
            String currentWeatherUrl = apiUrl + "/weather" + "?q=" + this.currentCity + "&appid=" + appid;
            currentWeather.getApiResponse(currentWeatherUrl);
        }
        f0 = getSupportFragmentManager().findFragmentByTag("f1");
        if(f0 instanceof ForecastWeather){
            ForecastWeather forecastWeather = (ForecastWeather) f0;
            String currentWeatherUrl = apiUrl + "/forecast" + "?q=" + this.currentCity + "&appid=" + appid;
            forecastWeather.getApiResponse(currentWeatherUrl);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onChangeCity(String cityname) {
        Fragment f0 = getSupportFragmentManager().findFragmentByTag("f0");
        if(f0 instanceof CurrentWeather){
            CurrentWeather currentWeather = (CurrentWeather) f0;
            currentWeather.currentCity.setText(cityname);
            String currentWeatherUrl = apiUrl + "/weather" + "?q=" + currentWeather.currentCity.getText().toString() + "&appid=" + appid + "&lang=pl";
            currentWeather.getApiResponse(currentWeatherUrl);
            Fragment f1 = getSupportFragmentManager().findFragmentByTag("f1");
            if(f1 instanceof ForecastWeather){
                currentWeatherUrl = apiUrl + "/forecast" + "?q=" + currentWeather.currentCity.getText().toString() + "&appid=" + appid + "&lang=pl";
                ForecastWeather forecastWeather = (ForecastWeather) f1;
                ((ForecastWeather) f1).getApiResponse(currentWeatherUrl);
            }
        }
    }
}