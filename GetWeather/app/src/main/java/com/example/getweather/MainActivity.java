package com.example.getweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    String apiKey = "&appid=c9737f0742612b80d89f965cd648c87a\n";

    public class getWeather extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            try{
                URL url = new URL(urls[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while(data!=-1)
                {
                    result += (char)data;
                    data = reader.read();
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            TextView textView = findViewById(R.id.textView2);
            try{
                JSONObject jsonObject = new JSONObject(s);
                String weather = jsonObject.getString("weather");
                JSONArray array = new JSONArray(weather);
                for(int i = 0; i<array.length(); i++) {
                    JSONObject description = array.getJSONObject(i);
                    String desc = description.getString("description");
                    textView.setText(desc.substring(0,1).toUpperCase()+desc.substring(1));
                }

            } catch(Exception e) {
                e.printStackTrace();
                textView.setText("No such city exists");
            }
        }
    }

    public void checkWeather( View view ) {
        EditText editText = findViewById(R.id.editText);
        String cityname = editText.getText().toString();
        String changecity = cityname.substring(0,1).toUpperCase() + cityname.substring(1).toLowerCase();
        try{
            getWeather weather = new getWeather();
            String url = "http://api.openweathermap.org/data/2.5/weather?q="+changecity+apiKey;
            weather.execute(url).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
