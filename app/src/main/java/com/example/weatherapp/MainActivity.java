package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText cityName;
    TextView Temp;
    TextView Humidity;
    TextView Description;
    ImageView searchButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cityName=findViewById(R.id.text_city);
        Temp=findViewById(R.id.text_temp);
        Humidity=findViewById(R.id.text_humidity);
        Description=findViewById(R.id.text_desc);
        searchButton=findViewById(R.id.img_search);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String API_KEY="253653e21ec8c87cab6aaa67d84b302e";
                String City_Name=cityName.getText().toString();
                String URL="https://api.openweathermap.org/data/2.5/weather?q="+City_Name+"&appid="+API_KEY;
                Log.d("myTag", URL);
                Log.d("success","search button working f9");
                RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            JSONObject obj=response.getJSONObject("main");
                            JSONArray obj2=response.getJSONArray("weather");
                            String temperature=obj.getString("temp");
                            Float tempincelcius=Float.parseFloat(temperature);
                            Float actualtempeerature=tempincelcius-273;
                            String t=String.valueOf(actualtempeerature);
                            String TemperatureMeasurementStr=t+ "\u2103";
                            String humidity=obj.getString("humidity");
                            JSONObject weather=obj2.getJSONObject(0);
                            String desc=weather.getString("description");
                            Temp.setText((TemperatureMeasurementStr) + "\u2109");
                            Humidity.setText("Humidity:"+humidity);
                            Description.setText(desc);
                            Log.d("1",desc);


                        }
                        catch (JSONException e){
                            Toast.makeText(MainActivity.this,e.toString(),Toast.LENGTH_SHORT).show();

                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();

                    }
                });
                queue.add(request);
            }
        });
    }

}
