package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.lang.Thread;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.view.View;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private double longitud=500;
    private double latitud;
    private EditText ciudad;
    private TextView Temp,Long,Lat,Temp_ubi;
    private boolean eleccion=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ciudad=(EditText) findViewById(R.id.Ciudad);
        Temp=(TextView) findViewById(R.id.Temp);
        Long=(TextView) findViewById(R.id.Longitud);
        Lat=(TextView) findViewById(R.id.Latitud);
        Temp_ubi=(TextView) findViewById(R.id.Temp_ubi);

        //----------------------------------------------------------ªª GPS ªª----------------------------------------------------------------------
       ///*
       LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
       LocationListener locationListener = new LocationListener(){
            public void onLocationChanged(Location location) {
                Long.setText("Longitud: " +location.getLongitude());
                Lat.setText("Latitud: " +location.getLatitude());
                longitud=location.getLongitude();
                latitud=location.getLatitude();
            }
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}
        };
        int  permissionCheck= ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        if(permissionCheck==PackageManager.PERMISSION_DENIED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION));
            else{ActivityCompat.requestPermissions(this,new String []{Manifest.permission.ACCESS_FINE_LOCATION},1);}
        }
        try{locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);}
        catch(Exception e){Log.e("Exception","Entro al catch " +e.toString());}
        try{locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);}
        catch(Exception e){Log.e("Exception","Entro al catch " +e.toString());}
        //*/
        //----------------------------------------------------------------------------------------------------------------------------------------
    }

    public void Ejecucion(View vista){
        //---------------------------------------------baja teclado---------------------------------------------
        InputMethodManager inm=(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        inm.hideSoftInputFromWindow(vista.getWindowToken(),0);
        //-----------------------------------------------------------------------------------------------------
        String ciudad= this.ciudad.getText().toString();
        Hilo hilo=new Hilo(ciudad,eleccion);
        Thread t=new Thread(hilo);
        t.start();
        try{t.join();}
        catch (Exception e){ Log.e("Excepcion hilo:","Fallo");}
        try{
            JSONObject inf_json = new JSONObject(hilo.json);
            if(eleccion){
                JSONObject main = new JSONObject(inf_json.getString("main"));
                String temp = main.getString("temp");
                Temp.setText("Temperatura: "+temp+" ºC");
                }
            else{
                String ciudad_ID=inf_json.getString("id");
                hilo.ciudad_ID=ciudad_ID;
                Thread h=new Thread(hilo);
                try{
                    h.start();
                    h.join();
                }
                catch (Exception e){Log.e("Excepcion en el Hilo2:","Fallo");}
                //------------------------------------------ Abre Activity2 --------------------------------------------
                Activity2 a=new Activity2();
                Intent intento=new Intent(this,Activity2.class);
                intento.putExtra("EXTRA_ciudad",ciudad);
                intento.putExtra("EXTRA_json",hilo.json);
                startActivity(intento);
                //------------------------------------------------------------------------------------------------------
                }
            }
        catch (Exception e){
            Temp.setText("Ciudad no encontrada");
            Log.e("Error","No funciono el programa");
        }
        eleccion=true;
    }

    public void Pronostico(View vista){
        eleccion=false;
        Ejecucion(vista);
    }

    public void Buscar(View vista){
        if(longitud!=500){
            Hilo_GPS hilo_gps=new Hilo_GPS(latitud,longitud);
            Thread t=new Thread(hilo_gps);
            t.start();
            try{
                t.join();
                JSONObject inf_json = new JSONObject(hilo_gps.json);
                JSONObject main = new JSONObject(inf_json.getString("main"));
                String temp = main.getString("temp");
                Temp_ubi.setText("Temperatura: "+temp+" ºC");
            }
            catch (Exception e){ Log.e("Excepcion hilo_gps:","Fallo");}
        }
    }
}

