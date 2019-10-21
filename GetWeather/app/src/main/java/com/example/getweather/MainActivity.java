package com.example.getweather;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    String apiKey = "&appid=c9737f0742612b80d89f965cd648c87a\n";

    @SuppressLint("StaticFieldLeak")
    public class getWeather extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            StringBuilder result = new StringBuilder();
            try{
                URL url = new URL(urls[0]);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader reader = new InputStreamReader(in);
                int data = reader.read();
                while(data!=-1)
                {
                    result.append((char) data);
                    data = reader.read();
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            TextView textView = findViewById(R.id.textView2);
            TextView timeView = findViewById(R.id.timeTextId);
            TextView tempView = findViewById(R.id.tempTextId);

            try{
                JSONObject jsonObject = new JSONObject(s);

                String dateTime = jsonObject.getString("dt");
                timeView.setText(getDateCurrentTimeZone(Long.parseLong(dateTime)));

                String weather = jsonObject.getString("weather");
                JSONArray array = new JSONArray(weather);
                for(int i = 0; i<array.length(); i++) {
                    JSONObject description = array.getJSONObject(i);
                    String desc = description.getString("description");
                    textView.setText(desc.substring(0,1).toUpperCase()+desc.substring(1));
                }


                JSONObject main  = jsonObject.getJSONObject("main");
                String temp = main.getString("temp");
                tempView.setText(temp  + " \u2109");


            } catch(Exception e) {
                e.printStackTrace();
                textView.setText(getResources().getString(R.string.no_such_city));
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


    public  String getDateCurrentTimeZone(long timestamp) {
        try{
            Calendar calendar = Calendar.getInstance();
            TimeZone tz = TimeZone.getDefault();
            calendar.setTimeInMillis(timestamp * 1000);
            calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currenTimeZone = calendar.getTime();
            return sdf.format(currenTimeZone);
        }catch (Exception ignored) {
        }
        return "";
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
