package com.example.myweatherapp;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class CurrentWeather extends Fragment {

    EditText currentCity;
    TextView  pressure, temperature, description, humidity, wind, time;
    ImageView weatherIcon;
    private JSONObject response;

    String url = "https://api.openweathermap.org/data/2.5/weather";
    String appid = "66171ac9614ea64b50da3c73b1beafa2";
    String imageUrl = "https://openweathermap.org/img/wn/";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.current_weather, container, false);

        currentCity = view.findViewById(R.id.currentCityTV);
        pressure = view.findViewById(R.id.preassureTV);
        temperature = view.findViewById(R.id.temperatureTV);
        description = view.findViewById(R.id.descriptionTV);
        humidity = view.findViewById(R.id.humidityTV);
        wind = view.findViewById(R.id.windTV);
        time = view.findViewById(R.id.hourTextView);
        weatherIcon = view.findViewById(R.id.weatherIcon);
        currentCity.setText("Lodz");

        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateWeather(JSONObject data) throws JSONException {
        JSONArray jsonArray = data.getJSONArray("weather");
        JSONObject jsonObjectWeather = jsonArray.getJSONObject(0);
        JSONObject jsonObjectMain = data.getJSONObject("main");
        JSONObject jsonObjectWind = data.getJSONObject("wind");
        int timezone = data.getInt("timezone");
        double _temperature = jsonObjectMain.getDouble("temp") - 273.15;
        int _pressure = jsonObjectMain.getInt("pressure");
        String _description = jsonObjectWeather.getString("description");
        String imageID = jsonObjectWeather.getString("icon");
        int _humidity = jsonObjectMain.getInt("humidity");
        int _wind = jsonObjectWind.getInt("speed");

        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        now = now.plusSeconds(timezone);
        time.setText(String.format("%02d:%02d", now.getHour(), now.getMinute()));
        wind.setText(_wind + "km/h");
        humidity.setText(_humidity + "%");
        pressure.setText(_pressure + "hPa");
        temperature.setText(String.format("%.2f°C", _temperature));
        description.setText(_description);
        String temp = imageUrl + imageID + "@4x.png";
        Picasso.get().load(temp)
                .into(weatherIcon);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public JSONObject getApiResponse(String url){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            try{
                JSONObject jsonResponse = new JSONObject(response);
                this.response = jsonResponse;
                updateWeather(this.response);

            } catch (JSONException e){
                e.printStackTrace();
            }
        }, error -> Toast.makeText(getContext(), error.toString().trim(), Toast.LENGTH_SHORT).show());

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
        return this.response;
    }
}
