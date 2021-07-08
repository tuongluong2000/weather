package com.example.appweather;

import com.android.volley.toolbox.StringRequest;

public class Weather {
    public String Day;
    public String Status;
    public String Image;
    public String MaxTemp;
    public String Mintemp;

    public Weather(String day, String status, String image, String maxTemp, String mintemp) {
        Day = day;
        Status = status;
        Image = image;
        MaxTemp = maxTemp;
        Mintemp = mintemp;
    }
}
