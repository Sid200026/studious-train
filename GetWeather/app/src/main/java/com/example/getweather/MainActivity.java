package com.example.getweather;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.getweather.retrofit.WeatherAPI;
import com.example.getweather.models.Response;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;

import static com.example.getweather.retrofit.WeatherAPI.API_KEY;
import static com.example.getweather.retrofit.WeatherAPI.UNITS_METRIC;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("StaticFieldLeak")
    public class getWeather extends AsyncTask<Response, Void, Response> {

        @Override
        protected Response doInBackground(Response... responses) {
            return responses[0];
        }

        @Override
        protected void onPostExecute(Response weatherResponse) {
            super.onPostExecute(weatherResponse);

            CardView cardView = findViewById(R.id.cardView);
            TextView textView = findViewById(R.id.textView2);
            TextView timeView = findViewById(R.id.timeTextId);
            TextView tempView = findViewById(R.id.tempTextId);
            cardView.setVisibility(View.VISIBLE);

            if (weatherResponse!=null){
                timeView.setVisibility(View.VISIBLE);
                tempView.setVisibility(View.VISIBLE);
                timeView.setText(getDateCurrentTimeZone(weatherResponse.getDt()));
                String desc = weatherResponse.getWeather().get(0).getDescription();
                textView.setText(desc.substring(0,1).toUpperCase()+desc.substring(1));
                String temp = weatherResponse.getMain().getTemp() + " \u2103";
                tempView.setText(temp);
            }else {
                timeView.setVisibility(View.INVISIBLE);
                tempView.setVisibility(View.INVISIBLE);
                textView.setText(getResources().getString(R.string.no_such_city));
            }


        }
    }

    public void checkWeather( View view ) {
        EditText editText = findViewById(R.id.editText);
        String cityname = editText.getText().toString();
        String changecity = cityname.substring(0,1).toUpperCase() + cityname.substring(1).toLowerCase();

        WeatherAPI.Factory.getInstance().getWeather(changecity,API_KEY,UNITS_METRIC).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                new getWeather().execute(response.body());
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



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

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET},1);
        }
    }
}
