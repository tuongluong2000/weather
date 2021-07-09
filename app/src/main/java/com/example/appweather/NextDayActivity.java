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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NextDayActivity extends AppCompatActivity {

    String Lat ="";
    String Lon="";
    ImageView imgback;
    TextView txtcityname;
    ListView listViewDay;
    CustomAdapter customAdapter;
    ArrayList<Weather> weatherArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_day);
        Mapping();
        Intent intent = getIntent();
        Lat = intent.getStringExtra("datalat");
        Lon = intent.getStringExtra("datalon");
        Log.d("ket qua intent:" ,Lat +" - "+Lon);
        Get16DaysData(Lat,Lon);
        imgback.setOnClickListener(BackOnClick());

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

    public void Get16DaysData(String dataLat, String dataLon)
    {
        String url = "https://api.weatherbit.io/v2.0/forecast/daily?lat="+dataLat+"&lon="+dataLon+"&key=620ea23b751148bbaf16f3320d269e8b";
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("onResponse: ", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String namecity = jsonObject.getString("city_name");
                    txtcityname.setText(namecity);

                    JSONArray jsonArraylistData = jsonObject.getJSONArray("data");
                    for (int i = 0; i<jsonArraylistData.length();i++)
                    {
                        JSONObject jsonObjectList = jsonArraylistData.getJSONObject(i);
                        String day = jsonObjectList.getString("ts");

                        Long l = Long.valueOf(day);
                        Date date = new Date(l*1000L);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE , yyyy-MM-dd");
                        day = simpleDateFormat.format(date);


                        String maxtemp= jsonObjectList.getString("max_temp");
                        String mintemp= jsonObjectList.getString("min_temp");



                        JSONObject jsonObjectDataWeather = jsonObjectList.getJSONObject("weather");
                        String status = jsonObjectDataWeather.getString("description");
                        String icon = jsonObjectDataWeather.getString("icon");

                        weatherArrayList.add(new Weather(day,status,icon,maxtemp,mintemp));
                    }
                    customAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();;
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("status: ", "nextday");
            }
        });
        queue.add(stringRequest);
    }

    private void Mapping()
    {
        imgback = (ImageView) findViewById(R.id.imageviewBack);
        txtcityname = (TextView) findViewById(R.id.textviewNameCity);
        listViewDay = (ListView) findViewById(R.id.listviewDay);
        weatherArrayList = new ArrayList<Weather>();
        customAdapter = new CustomAdapter(NextDayActivity.this, weatherArrayList);
        listViewDay.setAdapter(customAdapter);
    }
}
