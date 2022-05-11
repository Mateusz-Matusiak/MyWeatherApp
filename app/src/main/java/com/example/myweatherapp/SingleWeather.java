package com.example.myweatherapp;

public class SingleWeather {
    int temperature;
    String city, description, imageID, date;

    public SingleWeather(int temperature, String city, String description, String imageID, String date) {
        this.temperature = temperature;
        this.city = city;
        this.description = description;
        this.imageID = imageID;
        this.date = date;
    }
}
