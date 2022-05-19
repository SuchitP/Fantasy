package com.example.suchitpeddireddyfinalprojectfantasy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    public static ArrayList<String> names = new ArrayList<>();
    public static ArrayList<String> positions = new ArrayList<>();
    URL url;
    InputStream inputStream;
    URLConnection urlConnection;
    String currentString;
    //JSONObject jsonObject;
    TextView textView;
    ArrayList<ArrayList> teams = new ArrayList<>();
    ArrayList<JSONObject> players = new ArrayList<>();
    Button teamButton;
    LinearLayout mainLayout;

    Button newLeagueButton;
    Spinner spinner;
    public static int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AsyncThread thread = new AsyncThread();
        thread.execute();

        teamButton = findViewById(R.id.id_teamButton);
        Log.d("TAG","TAG1");

        newLeagueButton = findViewById(R.id.id_newLeagueButton);
        spinner = findViewById(R.id.id_spinner);
        mainLayout = findViewById(R.id.id_mainLayout);
        //mainLayout.setBackgroundColor(Color.BLACK);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.numbers, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        newLeagueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DraftActivity.class);
                startActivity(intent);
            }
        });

        teamButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TeamActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        a = Integer.valueOf(text);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class AsyncThread extends AsyncTask<String, Void, Void> {
        //String string = "";
        @Override
        protected Void doInBackground(String... strings) {//https://www.fantasyfootballnerd.com/service/players/json/hd5vu9i376uk/
            Log.d("TAG","haha");
            try {
                url = new URL("https://www.fantasyfootballnerd.com/service/players/json/hd5vu9i376uk/");
                urlConnection = url.openConnection();
                URLConnection urlConnection = url.openConnection();
                inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                currentString = reader.readLine();
                JSONObject jsonObject = new JSONObject(currentString);
                Log.d("TAG","hello?");
                Log.d("TAG", jsonObject.getJSONArray("Players").getJSONObject(0).get("displayName").toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //Log.d("TAG","TAG111");
            try {
                Log.d("TAG","TAG111");
                JSONObject jsonObject = new JSONObject(currentString);
                String temp;

                for(int i = 0; i < 3000; i++){
                    names.add(jsonObject.getJSONArray("Players").getJSONObject(i).get("displayName").toString());//jsonObject.getJSONArray("Players").getJSONObject(0).get("displayName").toString();
                    positions.add(jsonObject.getJSONArray("Players").getJSONObject(i).get("position").toString());
                }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
