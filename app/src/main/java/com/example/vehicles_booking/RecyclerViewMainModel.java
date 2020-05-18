package com.example.vehicles_booking;

import androidx.recyclerview.widget.RecyclerView;

class RecyclerViewMainModel {
   //this variable For recive PIC
    Integer LangLogo;

    public RecyclerViewMainModel(Integer langlogo){

        LangLogo=langlogo;

    }
    public Integer getLangLogo(){

        return LangLogo;
    }
}
