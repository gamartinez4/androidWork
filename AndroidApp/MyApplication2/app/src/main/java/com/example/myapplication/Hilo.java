package com.example.myapplication;

import android.util.Log;
import java.io.*;
import java.net.URL;

public class Hilo implements Runnable {

    public String json,ciudad,ciudad_ID;
    public boolean eleccion;
    public int posibilidad=0;

    public Hilo(String ciudad,boolean eleccion) {
        this.ciudad=ciudad;
        this.eleccion=eleccion;
    }

    public void run(){
        String palabra="";
        String cadena="";
        try{
            if(posibilidad==0){
                URL url=new URL("http://openweathermap.org/data/2.5/weather?q="+ciudad+"&appid=b6907d289e10d714a6e88b30761fae22");
                BufferedReader a=new BufferedReader(new InputStreamReader(url.openStream()));
                while(palabra!=null){
                    cadena=cadena+a.readLine();
                    palabra=a.readLine();
                }
                json=cadena;
                if(eleccion==false)posibilidad++;
            }
            if(posibilidad==2){
                URL url=new URL("https://openweathermap.org/data/2.5/forecast?id="+ciudad_ID+"&appid=b6907d289e10d714a6e88b30761fae22");
                BufferedReader a=new BufferedReader(new InputStreamReader(url.openStream()));
                while(palabra!=null){
                    cadena=cadena+a.readLine();
                    palabra=a.readLine();
                }
                json=cadena;
                posibilidad=0;
            }
            if(posibilidad==1)posibilidad++;
        }catch (Exception e){
            Log.e("conexion Hilo","Fallo");
        }
    }
}
