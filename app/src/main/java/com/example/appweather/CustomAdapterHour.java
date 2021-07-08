package com.example.appweather;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapterHour extends BaseAdapter {

    Context context;
    ArrayList<weatherhour> arrayList;

    private class ViewHolder {
        TextView txtday;
        TextView txtstatus;
        TextView txttemp;
        TextView txtprecipitation;
        TextView txthumidity;
        TextView txtdew;
        TextView txtcloud;
        TextView txtuv;
        TextView txtvision;
        ImageView imgstatus;
    }

    public CustomAdapterHour(Context context, ArrayList<weatherhour> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        weatherhour weather = arrayList.get(position);
        ViewHolder viewHolder;

        if (convertView ==null) {
            convertView  = inflater.inflate(R.layout.listview_hours,null);
            viewHolder = new ViewHolder();
            viewHolder.txtday = (TextView) convertView.findViewById(R.id.textviewDayHour);
            viewHolder.txtstatus = (TextView) convertView.findViewById(R.id.textviewStatusHour);
            viewHolder.txttemp = (TextView) convertView.findViewById(R.id.textviewtempHour);
            viewHolder.txtcloud = (TextView) convertView.findViewById(R.id.textviewcloudHour);
            viewHolder.txtprecipitation = (TextView) convertView.findViewById(R.id.textviewprecipitationHour);
            viewHolder.txtdew = (TextView) convertView.findViewById(R.id.textviewdewHour);
            viewHolder.txtvision = (TextView) convertView.findViewById(R.id.textviewvisionHour);
            viewHolder.txtuv = (TextView) convertView.findViewById(R.id.textviewuvHour);
            viewHolder.txthumidity = (TextView) convertView.findViewById(R.id.textviewhumidityHour);
            viewHolder.imgstatus = (ImageView) convertView.findViewById(R.id.imageStatusHour);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtday.setText(weather.Day);
        viewHolder.txtstatus.setText(weather.Status);
        viewHolder.txttemp.setText(weather.Temp+"°C");
        viewHolder.txtprecipitation.setText(weather.Precipitation+"%");
        viewHolder.txthumidity.setText(weather.Humidity+"%");
        viewHolder.txtuv.setText(weather.Uv);
        viewHolder.txtvision.setText(weather.Vision+"km");
        viewHolder.txtdew.setText(weather.Dew+"°");
        viewHolder.txtcloud.setText(weather.Cloud+"%");

        Picasso.with(context).load("https://www.weatherbit.io/static/img/icons/"+weather.Image+".png").into(viewHolder.imgstatus);
        return convertView;
    }
}
