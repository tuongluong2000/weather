package com.example.appweather;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NextHourActivity extends AppCompatActivity {

    String Lat ="";
    String Lon="";
    ImageView imgBack;
    TextView txtCity;
    ListView listView;
    CustomAdapterHour customAdapterHour;
    ArrayList<weatherhour> weatherhourArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_hour);
        Mapping();
        Intent intent = getIntent();
        Lat = intent.getStringExtra("lat");
        Lon =  intent.getStringExtra("lon");
        Log.d("ket qua hour ", Lat +" "+Lon);
        Get24HoursData(Lat, Lon);
        imgBack.setOnClickListener(BackOnClick());
    }

    private View.OnClickListener BackOnClick ()
    {
        View.OnClickListener back = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        };
        return back;
    }

    private void Get24HoursData(String lat, String lon)
    {
        String url = "https://api.weatherbit.io/v2.0/forecast/hourly?lat="+lat+"&lon="+lon+"&key=3be24643dedd42039b30e88b09bc5de8&hours=24";
        RequestQueue requestQueue = Volley.newRequestQueue(NextHourActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("requet hour ", "ok" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String namecity = jsonObject.getString("city_name");
                            txtCity.setText(namecity);
                            DecimalFormat toTheFormat = new DecimalFormat("#.#");
                            Double format;


                            JSONArray jsonArrayData = jsonObject.getJSONArray("data");
                            for (int i = 0; i<jsonArrayData.length(); i++) {

                                JSONObject jsonObjectData = jsonArrayData.getJSONObject(i);
                                String day = jsonObjectData.getString("ts");
                                Long l = Long.valueOf(day);
                                Date date = new Date(l*1000L);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
                                day = simpleDateFormat.format(date);

                                String temp= jsonObjectData.getString("temp");
                                String cloud = jsonObjectData.getString("clouds");
                                String precipitation = jsonObjectData.getString("pop");
                                String humidity = jsonObjectData.getString("rh");
                                String dew = jsonObjectData.getString("dewpt");

                                String uv = jsonObjectData.getString("uv");
                                format = Double.valueOf(uv);
                                uv =String.valueOf(toTheFormat.format(format));

                                String vision = jsonObjectData.getString("vis");
                                format = Double.valueOf(vision);
                                vision = String.valueOf(toTheFormat.format(format));

                                JSONObject jsonObjectDataWeather = jsonObjectData.getJSONObject("weather");
                                String status = jsonObjectDataWeather.getString("description");
                                String icon = jsonObjectDataWeather.getString("icon");

                                weatherhourArrayList.add(new weatherhour(day,status,icon,temp,precipitation,humidity,dew,cloud,uv,vision));

                            }
                            customAdapterHour.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void  onErrorResponse (VolleyError error)
                    {
                        Log.d("requet hour ", "loi");
                    }
                });
        requestQueue.add(stringRequest);
    }

    private  void Mapping()
    {
        imgBack = (ImageView) findViewById(R.id.imageviewBackHour);
        txtCity = (TextView) findViewById(R.id.textviewNameCityHour);
        listView = (ListView) findViewById(R.id.listviewHour);
        weatherhourArrayList = new ArrayList<weatherhour>();
        customAdapterHour = new CustomAdapterHour(NextHourActivity.this, weatherhourArrayList);
        listView.setAdapter(customAdapterHour);
    }
}