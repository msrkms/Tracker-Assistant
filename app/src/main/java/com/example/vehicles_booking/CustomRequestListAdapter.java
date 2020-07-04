package com.example.vehicles_booking;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomRequestListAdapter extends BaseAdapter {

    ArrayList<String> name=new ArrayList<>();
    ArrayList<String> ListVehiclenumber=new ArrayList<>();
    private LayoutInflater layoutInflater;
    int CountLength;
    Context context;

    //this method make for data recive from Vehicle List class
    public CustomRequestListAdapter(Context context , ArrayList<String> theList, ArrayList<String> listVehiclenumber,int CountLength) {

        this.CountLength=CountLength;
        this.context=context;
        name=theList;
        this.ListVehiclenumber=listVehiclenumber;

    }
    @Override
    public int getCount() {
        return CountLength;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {


        if (view==null){
            layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //Here list view is a Layout XML
            view=layoutInflater.inflate(R.layout.user_request_list_view,viewGroup,false);
        }

        TextView listname=(TextView) view.findViewById(R.id.RequestUserName);
        TextView mileage=(TextView) view.findViewById(R.id.RequestUserId);
        listname.setText(name.get(i));
        mileage.setText(ListVehiclenumber.get(i));

        return view;

    }


}

