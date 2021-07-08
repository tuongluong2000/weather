package com.example.appweather;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Message;
import android.util.JsonReader;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.os.Handler;

import java.util.logging.LoggingMXBean;
import java.util.logging.SimpleFormatter;
import java.lang.String;

public class MainActivity extends AppCompatActivity {

    EditText editSearch;
    ImageButton btnSearch, btnLocation;
    Button btnChangeActivity, btnNextHours;
    TextView txtCity,txtCountry, txtTemp,txtStatus,txtHumidity,txtCloud,txtWind,txtDay;
    ImageView imgIcon;
    String Lat= "";
    String Lon="";
    LinearLayout linearLayoutMain;
    FusedLocationProviderClient fusedLocationProviderClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mapping();
        GetLocation();
        btnSearch.setOnClickListener(SearchOnClick());
        btnChangeActivity.setOnClickListener(DaysOnClick());
        btnNextHours.setOnClickListener(HoursOnClick());
        btnLocation.setOnClickListener(LocationOnClick());

    }

    private  View.OnClickListener LocationOnClick()
    {
        View.OnClickListener OnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetLocation();
            }
        };
        return OnClick;
    }

    private  View.OnClickListener HoursOnClick()
    {
        View.OnClickListener OnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NextHourActivity.class);
                intent.putExtra("lat", Lat);
                intent.putExtra("lon", Lon);
                startActivity(intent);
            }
        };
        return OnClick;
    }

    private View.OnClickListener DaysOnClick()
    {
        View.OnClickListener ChangeOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NextDayActivity.class);
                String datalat = Lat;
                intent.putExtra("datalat", datalat);
                String datalon = Lon;
                intent.putExtra("datalon",datalon);
                startActivity(intent);
            }
        };
        return ChangeOnClick;
    }

    private View.OnClickListener SearchOnClick()
    {
        View.OnClickListener SearchOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = "";
                address = editSearch.getText().toString();
                Log.d("address", address);
                if(address != "" && address != null )
                {
                    Log.d("ok","ok");
                    GeoLocation geoLocation = new GeoLocation();
                    geoLocation.getAddress(address, getApplicationContext(), new GeoHandler());
                    Log.d("lataaaaa1", Lat);
                    Log.d("lonnnnnn1", Lon);
                }

            }
        };
        return SearchOnClick;
    }



    public void GetCurrentWeatherData(final String dataLat, String dataLon)
    {

        Log.d("dat",dataLat);
        Log.d("lon",dataLon);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://api.weatherbit.io/v2.0/current?lat="+dataLat+"&lon="+dataLon+"&key=3be24643dedd42039b30e88b09bc5de8";
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ketqua", response);
                Log.d("status","ok");
                try {
                    DecimalFormat toTheFormat = new DecimalFormat("#.#");
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArrayData = jsonObject.getJSONArray("data");
                    JSONObject jsonObjectData = jsonArrayData.getJSONObject(0);

                    String dataTemp = jsonObjectData.getString("temp");
                    Log.d("temp", dataTemp);
                    txtTemp.setText(dataTemp+"°C");

                    String city = jsonObjectData.getString("city_name");
                    txtCity.setText("Tên Trạm Thu: "+city);

                    String dataDay = jsonObjectData.getString("ts");
                    Long l = Long.valueOf(dataDay);
                    Date date = new Date(l*1000L);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE , yyyy-MM-dd 'at' HH:mm:ss");
                    String Day = simpleDateFormat.format(date);
                    txtDay.setText(Day);

                    JSONObject jsonObjectDataWeather = jsonObjectData.getJSONObject("weather");

                    String icon = jsonObjectDataWeather.getString("icon");
                    Picasso.with(MainActivity.this).load("https://www.weatherbit.io/static/img/icons/"+icon+".png").into(imgIcon);

                    String status = jsonObjectDataWeather.getString("description");
                    txtStatus.setText(status);

                    String humidity = jsonObjectData.getString("rh");
                    Double Humidity = Double.valueOf(humidity);
                    humidity = String.valueOf(toTheFormat.format(Humidity));
                    txtHumidity.setText(humidity+"%");

                    String wind = jsonObjectData.getString("wind_spd");
                    Double Wind = Double.valueOf(wind);
                    wind = String.valueOf(toTheFormat.format(Wind));
                    txtWind.setText(wind+"m/s");

                    String clou = jsonObjectData.getString("clouds");
                    txtCloud.setText(clou+"%");

                    String country = jsonObjectData.getString("country_code");
                    txtCountry.setText("Tên Nước: "+country);

                    Log.d("ok" ,"ook");
                    txtTemp.setText(dataTemp+"°C");

                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("status","loi");
            }
        });
        queue.add(stringRequest);
    }

    private void Mapping()
    {
        editSearch = (EditText) findViewById(R.id.edittextsearch);
        btnChangeActivity =(Button) findViewById(R.id.buttonnextday);
        btnSearch = (ImageButton) findViewById(R.id.buttonsearch);
        txtCity =(TextView) findViewById(R.id.textviewname);
        txtCountry = (TextView) findViewById(R.id.textviewnamecountry);
        txtTemp = (TextView) findViewById(R.id.textviewtemp);
        txtStatus = (TextView) findViewById(R.id.textviewtrangthai);
        txtHumidity = (TextView) findViewById(R.id.textviewhumidity);
        txtCloud = (TextView) findViewById(R.id.textviewclou);
        txtWind = (TextView) findViewById(R.id.textviewwind);
        txtDay = (TextView) findViewById(R.id.textviewday);
        imgIcon = (ImageView) findViewById(R.id.imageicon);
        linearLayoutMain = (LinearLayout) findViewById(R.id.activity_main);
        btnNextHours = (Button) findViewById(R.id.buttonnexthour);
        btnLocation =(ImageButton) findViewById(R.id.buttonlocation);
    }


    private class GeoHandler extends Handler {
        @Override
        public void handleMessage (Message message)
        {
            String address;
            switch (message.what)
            {
                case 1:
                    Bundle bundle = message.getData();
                    Lat = bundle.getString("resultlat");
                    Lon = bundle.getString("resultlon");
                    GetCurrentWeatherData(Lat, Lon);
                    break;
                case 2:
                    Bundle bundle2 = message.getData();
                    address ="Can not find the address " + bundle2.getString("noaddress");
                    txtCity.setText(address);
                    break;
                default: address = "Error";
            }

        }
    }

    public void GetLocation()
    {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
        if(ActivityCompat.checkSelfPermission(MainActivity.this,
        Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    Location location = task.getResult();

                    if(location !=null)
                    {
                        Lat = String.valueOf(location.getLatitude());
                        Lon = String.valueOf(location.getLongitude());
                        GetCurrentWeatherData(Lat,Lon);
                        Log.d("lat lon", Lat +Lon);
                    }
                    else {
                        if(Lat == "" || Lon =="") {
                            Lat = "10.823098";
                            Lon = "106.629663";
                            GetCurrentWeatherData(Lat,Lon);
                        }
                    }

                    }
                });



        }
        else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            if(Lat == "" || Lon =="") {
                Lat = "10.823098";
                Lon = "106.629663";
                GetCurrentWeatherData(Lat,Lon);
            }
        }
    }
}