package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class Adaptador extends BaseAdapter {

    private static LayoutInflater inflater;
    private TextView temperatura,hora;
    private String [] temperaturas,horas;
    private int [] imagen={R.drawable.sol};

    public Adaptador(Context contexto, String [] temperaturas,String [] horas){
        inflater=(LayoutInflater)contexto.getSystemService((contexto.LAYOUT_INFLATER_SERVICE));
        this.temperaturas=temperaturas;
        this.horas=horas;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View vista=inflater.inflate(R.layout.layout,null);
        this.temperatura=(TextView)vista.findViewById(R.id.temperatura);
        this.hora=(TextView)vista.findViewById(R.id.hora);
        ImageView Imagen=(ImageView)vista.findViewById(R.id.jpg);
        Imagen.setImageResource(this.imagen[0]);
        temperatura.setText(temperaturas[i]+"ºC");
        hora.setText(horas[i]);
        return vista;
    }

    @Override
    public int getCount() {
        return 13;
    }

    @Override
    public Object getItem(int position) {
        return getItemId(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }
}
