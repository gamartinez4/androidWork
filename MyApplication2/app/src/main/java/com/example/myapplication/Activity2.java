package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class Activity2 extends AppCompatActivity {

    private ListView list;
    private TextView ciudad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);

        ciudad=(TextView)findViewById(R.id.Ciudad2);
        ciudad.setText(getIntent().getStringExtra("EXTRA_ciudad"));
        try{
            JSONObject inf_json=new JSONObject(getIntent().getStringExtra("EXTRA_json"));
            JSONArray lista=new JSONArray(inf_json.getString("list"));
            String [] hora=new String[13];
            String [] temperatura=new String[13];
            for(int i=0;i<13;i++){
                JSONObject main=new JSONObject(lista.getJSONObject(i).getString("main"));
                temperatura[i]=main.getString("temp");
                hora[i]=lista.getJSONObject(i).getString("dt_txt");
                Log.e("Cuenta",temperatura[i]);
            }
            ListView List=(ListView) findViewById(R.id.list);
            List.setAdapter(new Adaptador(Activity2.this,temperatura,hora));
        }
        catch(Exception e){
            Log.e("Activity2","Fallo");
        }
    }
}
