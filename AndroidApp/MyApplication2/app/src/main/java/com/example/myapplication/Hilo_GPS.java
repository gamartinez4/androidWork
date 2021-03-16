package com.example.myapplication;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class Hilo_GPS implements Runnable{

    private String lat;
    private String lon;
    public String json;

    public Hilo_GPS(double lat,double lon){
        this.lat=Double.toString(lat);
        this.lon=Double.toString(lon);
    }

    public void run(){
        String palabra="";
        String cadena="";
        try{
            URL url=new URL("https://openweathermap.org/data/2.5/weather?lat="+lat+"&lon="+lon+"&appid=b6907d289e10d714a6e88b30761fae22");
            BufferedReader a=new BufferedReader(new InputStreamReader(url.openStream()));
            while(palabra!=null){
                cadena=cadena+a.readLine();
                palabra=a.readLine();
                }
            json=cadena;
            }
        catch(Exception e){
            Log.e("Conexion Hilo_GPS","Fallo");
        }
    }
}
