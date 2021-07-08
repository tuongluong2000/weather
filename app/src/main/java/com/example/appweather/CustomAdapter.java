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

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<Weather> arrayList;

    private class ViewHolder {
        TextView txtday;
        TextView txtstatus;
        TextView txtmaxtemp;
        TextView txtmintemp;
        ImageView imgstatus;
    }

    public CustomAdapter(Context context, ArrayList<Weather> arrayList) {
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
        Weather weather = arrayList.get(position);
        ViewHolder viewHolder;

        if (convertView ==null) {
            convertView  = inflater.inflate(R.layout.listview_days,null);
            viewHolder = new ViewHolder();
            viewHolder.txtday = (TextView) convertView.findViewById(R.id.textviewDay);
            viewHolder.txtstatus = (TextView) convertView.findViewById(R.id.textviewStatus);
            viewHolder.txtmaxtemp = (TextView) convertView.findViewById(R.id.textviewMaxTemp);
            viewHolder.txtmintemp = (TextView) convertView.findViewById(R.id.textviewMinTemp);
            viewHolder.imgstatus = (ImageView) convertView.findViewById(R.id.imageStatus);

            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtday.setText(weather.Day);
        viewHolder.txtstatus.setText(weather.Status);
        viewHolder.txtmaxtemp.setText(weather.MaxTemp+"°↓");
        viewHolder.txtmintemp.setText(weather.Mintemp+"°↑");

        Picasso.with(context).load("https://www.weatherbit.io/static/img/icons/"+weather.Image+".png").into(viewHolder.imgstatus);
        return convertView;
    }
}
