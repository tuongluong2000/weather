package com.example.appweather;

public class weatherhour {
    public String Day;
    public String Status;
    public String Image;
    public String Temp;
    public String Precipitation;
    public String Humidity;
    public String Dew;
    public String Cloud;
    public String Uv;
    public String Vision;

    public weatherhour(String day, String status, String image, String temp, String precipitation, String humidity, String dew, String cloud, String uv, String vision) {
        Day = day;
        Status = status;
        Image = image;
        Temp = temp;
        Precipitation = precipitation;
        Humidity = humidity;
        Dew = dew;
        Cloud = cloud;
        Uv = uv;
        Vision = vision;
    }
}
