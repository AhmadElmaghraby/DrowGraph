package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    ArrayList<ColoringRESO> resoArrayList = new ArrayList<ColoringRESO>();
    ArrayList<ColoringRESP> respArrayList = new ArrayList<ColoringRESP>();
    ArrayList<ColoringSINR> sinrArrayList = new ArrayList<ColoringSINR>();
    TableLayout tableLayout;

    ArrayList<Result> data = new ArrayList<Result>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tableLayout = findViewById(R.id.tabel);
        parsejSonFile();
        addRow(0, 0, 0);
        Thread thread = new Thread() {
            OkHttpClient client = new OkHttpClient.Builder()
                    .build();

            final Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://51.195.89.92:6000")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build();
            Api service = retrofit.create(Api.class);

            @Override
            public void run() {
                while (true)
                    try {
                        service.getdata().enqueue(new Callback<Result>() {
                            @Override
                            public void onResponse(Call<Result> call, Response<Result> response) {
                                addRow(response.body().getRSRP(), response.body().getRSRQ(), response.body().getSINR());
                                data.add(new Result(response.body().getRSRP(), response.body().getRSRQ(), response.body().getSINR()));
                            }

                            @Override
                            public void onFailure(Call<Result> call, Throwable t) {
                            }
                        });
                        sleep(2000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
        };
        thread.start();
    }

    void parsejSonFile() {
        String json;

        try {
            InputStream inputStream = getAssets().open("Legend.json");

            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArrayRSRP = jsonObject.getJSONArray("RSRP");
            for (int i = 0; i < jsonArrayRSRP.length(); i++) {
                JSONObject object = jsonArrayRSRP.getJSONObject(i);
                ColoringRESP resp = new ColoringRESP();

                resp.setFrom(object.getInt("From"));
                resp.setTo(object.getInt("To"));
                resp.setColor(object.getString("Color"));
                respArrayList.add(resp);
            }
            JSONArray jsonArrayRSRO = jsonObject.getJSONArray("RSRQ");
            for (int i = 0; i < jsonArrayRSRO.length(); i++) {
                JSONObject object = jsonArrayRSRO.getJSONObject(i);
                ColoringRESO reso = new ColoringRESO();

                reso.setFrom(object.getInt("From"));
                reso.setTo(object.getInt("To"));
                reso.setColor(object.getString("Color"));
                resoArrayList.add(reso);
            }
            JSONArray jsonArraySINR = jsonObject.getJSONArray("SINR");
            for (int i = 0; i < jsonArraySINR.length(); i++) {
                JSONObject object = jsonArraySINR.getJSONObject(i);
                ColoringSINR sinr = new ColoringSINR();

                sinr.setFrom(object.getInt("From"));
                sinr.setTo(object.getInt("To"));
                sinr.setColor(object.getString("Color"));
                sinrArrayList.add(sinr);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

    }

    void addRow(int RSRP, int RSRQ, int SINR) {
        String RSRPcolor = "#000A00", RSRQcolor = "#000000", SINRcolor = "#000000";

        if (RSRP == 0) {
            TableRow row = new TableRow(this);
            row.setGravity(Gravity.CENTER);
            TextView tv1 = new TextView(this);
            tv1.setText("  RSRP  ");
            tv1.setTextSize(30);
            tv1.setTextColor(Color.WHITE);
            tv1.setGravity(Gravity.CENTER);
            tv1.setBackgroundColor(Color.parseColor(RSRPcolor));

            TextView tv2 = new TextView(this);
            tv2.setText("  RSRQ  ");
            tv2.setTextSize(30);

            tv2.setTextColor(Color.WHITE);
            tv2.setGravity(Gravity.CENTER);
            tv2.setBackgroundColor(Color.parseColor(RSRQcolor));

            TextView tv3 = new TextView(this);
            tv3.setText("  SINR  ");
            tv3.setTextSize(30);

            tv3.setTextColor(Color.WHITE);
            tv3.setGravity(Gravity.CENTER);
            tv3.setBackgroundColor(Color.parseColor(SINRcolor));


            row.addView(tv1);
            row.addView(tv2);
            row.addView(tv3);
            tableLayout.addView(row);
            return;
        }
        for (int i = 0; i < respArrayList.size(); i++) {
            if (RSRP >= respArrayList.get(i).getFrom()) {
                if (RSRP <= respArrayList.get(i).getTo()) {
                    RSRPcolor = respArrayList.get(i).getColor();
                    break;
                }
            }
        }
        for (int i = 0; i < resoArrayList.size(); i++) {
            if (RSRQ >= resoArrayList.get(i).getFrom()) {
                if (RSRQ <= resoArrayList.get(i).getTo()) {
                    RSRQcolor = resoArrayList.get(i).getColor();
                    break;
                }
            }
        }
        for (int i = 0; i < sinrArrayList.size(); i++) {
            if (SINR >= sinrArrayList.get(i).getFrom()) {
                if (SINR <= sinrArrayList.get(i).getTo()) {
                    SINRcolor = sinrArrayList.get(i).getColor();
                    break;
                }
            }
        }
        TableRow row = new TableRow(this);
        row.setGravity(Gravity.CENTER);
        TextView tv1 = new TextView(this);
        tv1.setText(RSRP + "");
        tv1.setTextSize(30);

        tv1.setTextColor(Color.WHITE);
        tv1.setGravity(Gravity.CENTER);
        tv1.setPadding(40, 20, 20, 0);
        tv1.setBackgroundColor(Color.parseColor(RSRPcolor));

        TextView tv2 = new TextView(this);
        tv2.setText(RSRQ + "");
        tv2.setTextSize(30);

        tv2.setTextColor(Color.WHITE);
        tv2.setGravity(Gravity.CENTER);
        tv2.setPadding(20, 20, 20, 0);
        tv2.setBackgroundColor(Color.parseColor(RSRQcolor));

        TextView tv3 = new TextView(this);
        tv3.setText(SINR + "");
        tv3.setTextSize(30);

        tv3.setTextColor(Color.WHITE);
        tv3.setGravity(Gravity.CENTER);
        tv3.setPadding(20, 20, 20, 0);
        tv3.setBackgroundColor(Color.parseColor(SINRcolor));


        row.addView(tv1);
        row.addView(tv2);
        row.addView(tv3);
        tableLayout.addView(row);
    }

    public void btnGraph(View view) {
        Intent intent = (new Intent(MainActivity.this, GraphKActivity.class));
        intent.putExtra("data", data);
        startActivity(intent);
    }
}
