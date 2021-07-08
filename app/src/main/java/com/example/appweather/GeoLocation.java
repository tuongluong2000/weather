package com.example.appweather;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.media.VolumeShaper;
import android.os.Bundle;
import android.os.Message;
import java.lang.Object;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import android.os.Handler;

public class GeoLocation {
    public static void getAddress (final String locationAddress, final Context context, final Handler handler)
    {
        Thread thread = new Thread() {
          @Override
          public  void  run()
          {
              Geocoder geocoder = new Geocoder(context, Locale.getDefault());
              String resultLat = null;
              String resultLon = null;
              String status = null;
              int i = 0;
              try {
                  List<Address> addressList = geocoder.getFromLocationName(locationAddress, 1);
                  if (addressList.size() ==0)
                      i =0;
                  while (addressList.size() ==0 && i <=10) {
                      addressList = geocoder.getFromLocationName(locationAddress, 1);
                      i++;
                  }
                  if (addressList.size() > 0)
                  {
                      Address address = (Address) addressList.get(0);
                      StringBuilder stringBuilderLat = new StringBuilder();
                      StringBuilder stringBuilderLon = new StringBuilder();
                      stringBuilderLat.append(address.getLatitude());
                      stringBuilderLon.append(address.getLongitude());
                      resultLat = stringBuilderLat.toString();
                      resultLon = stringBuilderLon.toString();

                  }
                  else
                  {
                      status =  locationAddress;
                  }
              } catch (IOException e) {
                  e.printStackTrace();
              } finally {
                  Message message = Message.obtain();
                  message.setTarget(handler);
                  if(resultLat != null && resultLon !=null)
                  {
                      message.what = 1;
                      Bundle bundle = new Bundle();
                      bundle.putString("resultlat", resultLat);
                      bundle.putString("resultlon", resultLon);
                      message.setData(bundle);

                  }
                  else {
                      message.what = 2;
                      Bundle bundle = new Bundle();
                      bundle.putString("noaddress", status);
                      message.setData(bundle);
                  }
                  message.sendToTarget();
              }
          }
        };
        thread.start();
    }
}
