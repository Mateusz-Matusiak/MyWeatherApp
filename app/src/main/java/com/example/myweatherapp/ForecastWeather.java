package com.example.myweatherapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
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
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ForecastWeather extends Fragment {

    ListView listView;
    String city;
    String imageUrl = "https://openweathermap.org/img/wn/";
    ArrayList<SingleWeather> singleWeathers = new ArrayList<>();
    private JSONObject response;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forecast_weather, container, false);

        listView = view.findViewById(R.id.citiesList);
        ListAdapter listAdapter = new ListAdapter(getContext(), singleWeathers);
        listView.setAdapter(listAdapter);

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void updateWeather(JSONObject data) throws JSONException {
        singleWeathers.clear();
        JSONArray jsonArray = data.getJSONArray("list");
        JSONObject cityJson = data.getJSONObject("city");
        city = cityJson.getString("name");
        for (int i=0; i < jsonArray.length(); i++) {
            JSONObject temp = jsonArray.getJSONObject(i);
            String dateToParse = temp.getString("dt_txt");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(dateToParse, formatter);
            if(dateTime.getHour() == 15){
                JSONObject jsonMain = temp.getJSONObject("main");
                double _temp = jsonMain.getDouble("temp");
                JSONArray jsonWeatherArray = temp.getJSONArray("weather");
                JSONObject jsonWeatherObject = jsonWeatherArray.getJSONObject(0);
                String _description = jsonWeatherObject.getString("description");
                String _iconID = jsonWeatherObject.getString("icon");
                String date = String.format("%02d.%02d.%04d", dateTime.getDayOfMonth(), dateTime.getMonth().getValue(), dateTime.getYear());
                singleWeathers.add(new SingleWeather((int) Math.ceil(_temp - 273.15), city, _description, String.format("%s%s%s", imageUrl, _iconID, "@4x.png"), date));
            }
        }
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
